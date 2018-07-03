package crypto.preanalysis;

import crypto.Utils;
import crypto.analysis.ClassSpecification;
import soot.Scene;
import soot.SootClass;

import java.util.*;

public class RuleTree {

    private List<TreeNode<TreeNodeData>> listOfTreeNodes;
    private TreeNode<TreeNodeData> objectNode;

    public RuleTree(List<ClassSpecification> specifications) {

        listOfTreeNodes = new ArrayList<>();
        for (ClassSpecification specification : specifications) {
            // Create TreeNodeData with the class and specification for each specification.
            SootClass classFromClassSpecification = Scene.v().forceResolve(Utils.getFullyQualifiedName(specification.getRule()), SootClass.HIERARCHY);
            listOfTreeNodes.add(new TreeNode<>(new TreeNodeData(classFromClassSpecification, specification)));
            if (Utils.getFullyQualifiedName(specification.getRule()).equals("java.lang.Object")) {
                // set the pseudo rule for the class Object to be used as a root node for the rule tree.
                objectNode = new TreeNode<>(new TreeNodeData(classFromClassSpecification, specification));
            }
        }
    }


    public TreeNode<TreeNodeData> createTree() {
        // temp node as a root for the tree.
        TreeNode<TreeNodeData> newRuleHeir = objectNode;
        for (TreeNode<TreeNodeData> treeNode : listOfTreeNodes) {
            insertNode(treeNode, newRuleHeir);
        }
        return newRuleHeir;
    }

    public boolean insertNode(TreeNode<TreeNodeData> nodeUnderConsideration, TreeNode<TreeNodeData> rootNode) {
        // Skip if the node under consideration and root node are the same.
        if (!nodeUnderConsideration.data.getSootClass().equals(rootNode.data.getSootClass())) {
            // Check if the node under consideration is a sub class of the current root node.
            if (Scene.v().getOrMakeFastHierarchy().isSubclass(nodeUnderConsideration.data.getSootClass(), rootNode.data.getSootClass())) {
                // If it is go deeper into the current tree.
                for (TreeNode<TreeNodeData> child : rootNode.children) {
                    // The current root node is now the child of the earlier parent node. Break the recursion if the insertion was successful.
                    if (insertNode(nodeUnderConsideration, child)) {
                        return true;
                    }
                }

                // After traversal through the children, if insertion is unsuccessful, or if there are no children, add the node as a child.
                if (!rootNode.children.contains(nodeUnderConsideration)) {
                    rootNode.addChild(nodeUnderConsideration.data);
                    return true;
                }

            }// If the above check is not true, check if the hierarchy is the other way around.
            else if (Scene.v().getOrMakeFastHierarchy().isSubclass(rootNode.data.getSootClass(), nodeUnderConsideration.data.getSootClass())) {
                // Add the node under consideration as a child to the root node. And remove the root node from the children of its parent.
                rootNode.parent.children.add(nodeUnderConsideration);
                rootNode.parent.children.remove(rootNode);

                // In case the node under consideration has children.
                for (TreeNode<TreeNodeData> child : nodeUnderConsideration.children) {
                    if (insertNode(child, nodeUnderConsideration)) {
                        return true;
                    }
                }

                if (!nodeUnderConsideration.children.contains(rootNode)) {
                    nodeUnderConsideration.addChild(rootNode.data);
                    return true;
                }

            } // If both the above conditions fail, the higher node will get the node under consideration as a child.
            else {
                return false;
            }
        } else {
            return false;
        }

        // Should be unreachable.
        return false;
    }

}