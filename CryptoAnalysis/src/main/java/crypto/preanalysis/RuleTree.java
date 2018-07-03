package crypto.preanalysis;

import crypto.Utils;
import crypto.analysis.ClassSpecification;
import soot.Scene;
import soot.SootClass;

import java.util.*;

public class RuleTree {

    private List<TreeNode<ClassSpecification>> treeNodes;
    private TreeNode<ClassSpecification>objectNode;

    private List<TreeNode<TreeNodeData>> listOfTreeNodes;
    private TreeNode<TreeNodeData> newObjectNode;

    private Map<TreeNode<TreeNodeData>,TreeNode<TreeNodeData>> toBeSwitched; // <tobeRemoved, toBeAdded>, use the parent of toBeRemoved.
    private Map<TreeNode<TreeNodeData>,TreeNode<TreeNodeData>> toBeInserted; // <parent, child>


    public RuleTree(List<ClassSpecification> specifications, boolean psuedo) {

        listOfTreeNodes = new ArrayList<>();
        toBeSwitched = new HashMap<>();
        toBeInserted = new HashMap<>();
        for (ClassSpecification specification : specifications) {
            //SootClass classFromClassSpecification = Scene.v().getSootClass(Utils.getFullyQualifiedName(specification.getRule()));
            SootClass classFromClassSpecification = Scene.v().forceResolve(Utils.getFullyQualifiedName(specification.getRule()),SootClass.HIERARCHY);
            listOfTreeNodes.add(new TreeNode<>(new TreeNodeData(classFromClassSpecification,specification)));
            if (Utils.getFullyQualifiedName(specification.getRule()).equals("java.lang.Object")){
                // set the pseudo rule for the class Object to be used as a root node for the rule tree.
                newObjectNode = new TreeNode<>(new TreeNodeData(classFromClassSpecification,specification));
            }
        }
    }


    public TreeNode<TreeNodeData> newCreateTree(){

        // temp node as a root for the tree.
        TreeNode<TreeNodeData> newRuleHeir = newObjectNode;

        for (TreeNode<TreeNodeData> treeNode : listOfTreeNodes) {
            newInsertNode(treeNode,newRuleHeir);
            //  The nodes that need to be switched. The iterative call to the insertNode method is readonly.
            postInsertion();
        }

        // change the return statement;
        return newRuleHeir;
    }

    public void postInsertion(){
        if (toBeSwitched != null){
            for (Map.Entry<TreeNode<TreeNodeData>, TreeNode<TreeNodeData>> treeNodeTreeNodeEntry : toBeSwitched.entrySet()) {
                treeNodeTreeNodeEntry.getKey().parent.addChild(treeNodeTreeNodeEntry.getValue().data);
                treeNodeTreeNodeEntry.getKey().parent.children.remove(treeNodeTreeNodeEntry.getKey());
            }
            toBeSwitched.clear();
        }
        if (toBeInserted != null){
            for (Map.Entry<TreeNode<TreeNodeData>, TreeNode<TreeNodeData>> treeNodeTreeNodeEntry : toBeInserted.entrySet()) {
                treeNodeTreeNodeEntry.getKey().addChild(treeNodeTreeNodeEntry.getValue().data);
            }
            toBeInserted.clear();
        }
    }

    //recursive
    public boolean newInsertNode(TreeNode<TreeNodeData> nodeUnderConsideration, TreeNode<TreeNodeData> rootNode){
        boolean successfulInsertion = false; // to stop duplicates after tree parsing if one of the children has a successful insert.

        if (!nodeUnderConsideration.data.getSootClass().equals(rootNode.data.getSootClass())){
                System.out.println(nodeUnderConsideration.data.getSootClass().getName());
            System.out.println(rootNode.data.getSootClass().getName() + "\n");
                if (Scene.v().getOrMakeFastHierarchy().isSubclass(nodeUnderConsideration.data.getSootClass(), rootNode.data.getSootClass())){

                    /*for(Iterator<TreeNode<TreeNodeData>> iterator = rootNode.children.iterator(); iterator.hasNext();){
                        TreeNode<TreeNodeData> child = iterator.next();
                        successfulInsertion = newInsertNode(nodeUnderConsideration, child);
                    }*/
                    for (TreeNode<TreeNodeData> child : rootNode.children) {
                        successfulInsertion = newInsertNode(nodeUnderConsideration, child) | successfulInsertion;
                    }

                    // after traversal through the children, if insertion is unsuccessful, or if there are no children, add the node as a child.
                    if(!successfulInsertion){
                        if (!rootNode.children.contains(nodeUnderConsideration.data)){
                            rootNode.addChild(nodeUnderConsideration.data);
                            successfulInsertion = true;
                        }

                    }

                }// if the above check is not true, check if the hierarchy is the other way around.
                else if(Scene.v().getOrMakeFastHierarchy().isSubclass(rootNode.data.getSootClass(), nodeUnderConsideration.data.getSootClass())){
                    // Update the parent of the switched node.

                    toBeSwitched.put(rootNode,nodeUnderConsideration);
//                    rootNode.parent.addChild(nodeUnderConsideration.data);
//                    rootNode.parent.children.remove(rootNode);

                    /*for(Iterator<TreeNode<TreeNodeData>> iterator = nodeUnderConsideration.children.iterator();iterator.hasNext();){
                        TreeNode<TreeNodeData> child = iterator.next();
                        successfulInsertion = newInsertNode(child, nodeUnderConsideration);
                    }*/

                    for (TreeNode<TreeNodeData> child : nodeUnderConsideration.children) {
                        successfulInsertion = newInsertNode(child, nodeUnderConsideration) | successfulInsertion;
                    }
                    if(!successfulInsertion){
                        if (!nodeUnderConsideration.children.contains(rootNode.data)){
                            //nodeUnderConsideration.addChild(rootNode.data);
                            toBeInserted.put(nodeUnderConsideration,rootNode);
                            successfulInsertion = true;
                        }

                    }

                } else {
                    return false;
                }
        }

        return successfulInsertion;
    }

}