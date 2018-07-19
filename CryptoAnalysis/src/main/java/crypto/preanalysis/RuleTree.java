package crypto.preanalysis;

import crypto.Utils;
import crypto.analysis.ClassSpecification;
import soot.Scene;
import soot.SootClass;

import java.util.*;

public class RuleTree {

    private TreeNode<TreeNodeData> objectNode; // A single root node expected
    private Map<TreeNode<TreeNodeData>, Integer> listOfSuperClasses; // Map<Node, Depth>

    /**
     * Create a rule tree from the given ClassSpecifications
     * @param specifications The specifications read from the CryptoScanner
     * @return The rule tree
     */
    public TreeNode<TreeNodeData> createTree(List<ClassSpecification> specifications) {
        if (specifications.size() > 0) {
            List<TreeNode<TreeNodeData>> listOfTreeNodes = new ArrayList<>();

            // This loop is needed to id the Object rule to avoid multiple apexes for the tree
            for (ClassSpecification specification : specifications) {
                // Create TreeNodeData with the class and specification for each specification.
                SootClass classFromClassSpecification = Scene.v().forceResolve(Utils.getFullyQualifiedName(specification.getRule()), SootClass.HIERARCHY);
                listOfTreeNodes.add(new TreeNode<>(new TreeNodeData(classFromClassSpecification, specification)));
                if (Utils.getFullyQualifiedName(specification.getRule()).equals("java.lang.Object")) {
                    // set the pseudo rule for the class Object to be used as a root node for the rule tree.
                    objectNode = new TreeNode<>(new TreeNodeData(classFromClassSpecification, specification));
                }
            }

            if (objectNode != null) {
                for (TreeNode<TreeNodeData> treeNode : listOfTreeNodes) {
                    insertNode(treeNode, objectNode);
                }
            }
            return objectNode;
        }
        return null;
    }

    /**
     * Recursive insertion of a given rule node in the rule tree
     * @param nodeUnderConsideration
     * @param rootNode The current root node.
     * @return Whether the insertion was successful
     */
    private boolean insertNode(TreeNode<TreeNodeData> nodeUnderConsideration, TreeNode<TreeNodeData> rootNode) {
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
                TreeNode<TreeNodeData> parent = rootNode.parent;
                parent.children.add(nodeUnderConsideration);
                parent.children.remove(rootNode);

                // check the former siblings of the exchanged sub class, if they are sub classes to the new super class, add them as children.
                for (TreeNode<TreeNodeData> child : parent.children) {
                    if (child.data.getSootClass() != nodeUnderConsideration.data.getSootClass()) {
                        if (Scene.v().getOrMakeFastHierarchy().isSubclass(child.data.getSootClass(), nodeUnderConsideration.data.getSootClass())) {
                            parent.children.remove(child);
                            insertNode(child, nodeUnderConsideration);
                        }
                    }

                }


                // In case the node under consideration has children.
                for (TreeNode<TreeNodeData> child : nodeUnderConsideration.children) {
                    if (insertNode(child, nodeUnderConsideration)) {
                        return true; // the recursion must break here.
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

    /**
     * Return the correct rule for the given soot class
     * @param sootClass
     * @return Correct rule
     */
    public ClassSpecification getRule(SootClass sootClass) {
        listOfSuperClasses = new HashMap<>();

        // Begin search from the root node.
        getSuperClasses(objectNode, sootClass);

        // Temp storage to hold the valid rule.
        Map.Entry<TreeNode<TreeNodeData>, Integer> validRule = new AbstractMap.SimpleEntry<TreeNode<TreeNodeData>, Integer>(null, -1);

        for (Map.Entry<TreeNode<TreeNodeData>, Integer> treeNodeStringEntry : listOfSuperClasses.entrySet()) {
            if (validRule.getValue() < treeNodeStringEntry.getValue()) {
                validRule = treeNodeStringEntry;
            }
        }

        return validRule.getKey().data.getClassSpecification();
    }

    /**
     * Recursive traversal of the rule tree to id the correct rule for the given soot class
     * @param node The current root node
     * @param sootClass
     */
    private void getSuperClasses(TreeNode<TreeNodeData> node, SootClass sootClass) {
        // If the given soot class is the same as the one in the current node, this is the rule we are looking for.
        // This should also mean this addition to the listOfSuperClasses should yield the deepest depth.
        if (sootClass.equals(node.data.getSootClass())) {
            listOfSuperClasses.put(node, node.getDepth());
        } // If not, check if the given soot class is a sub class. If yes add to the listOfSuperClasses along with the depth, and delve further into the tree.
        else if (Scene.v().getOrMakeFastHierarchy().isSubclass(sootClass, node.data.getSootClass())) {
            listOfSuperClasses.put(node, node.getDepth());
            for (TreeNode<TreeNodeData> child : node.children) {
                getSuperClasses(child, sootClass);
            }
        }

    }

}