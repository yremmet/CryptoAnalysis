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


    public RuleTree(List<ClassSpecification> specifications) {
        treeNodes = new ArrayList<>();
        for (ClassSpecification specification : specifications) {
            treeNodes.add(new TreeNode(specification));
            if (Utils.getFullyQualifiedName(specification.getRule()).equals("java.lang.Object")){
                // set the pseudo rule for the class Object to be used as a root node for the rule tree.
                objectNode = new TreeNode(specification);
            }
        }
    }


    public TreeNode<ClassSpecification> createTree(){
        // temp node as a root for the tree.
        TreeNode<ClassSpecification> ruleHeir = objectNode;

        for (TreeNode<ClassSpecification> treeNode : treeNodes) {
            insertNode(treeNode, ruleHeir);
        }

        // change the return statement;
        return ruleHeir;
    }

    // recursive
    public boolean insertNode(TreeNode<ClassSpecification> nodeUnderConsideration, TreeNode<ClassSpecification> rootNode){
        boolean successfulInsertion = false; // to stop duplicates after tree parsing if one of the children has a successful insert.
        String nodeUnderConsiderationClassFullyQualifiedName = Utils.getFullyQualifiedName(nodeUnderConsideration.data.getRule());
        String rootNodeClassFullyQualifiedName = Utils.getFullyQualifiedName(rootNode.data.getRule());


        if (!nodeUnderConsiderationClassFullyQualifiedName.equals(rootNodeClassFullyQualifiedName)){
                if (Scene.v().getOrMakeFastHierarchy().isSubclass(Scene.v().getSootClass(nodeUnderConsiderationClassFullyQualifiedName),Scene.v().getSootClass(rootNodeClassFullyQualifiedName))){
                    for (TreeNode<ClassSpecification> child : rootNode.children) {
                        successfulInsertion = insertNode(nodeUnderConsideration, child);
                    }
                    // after traversal through the children, if insertion is unsuccessful, or if there are no children, add the node as a child.
                    if(!successfulInsertion){
                        rootNode.addChild(nodeUnderConsideration.data);
                        successfulInsertion = true;
                    }


                }// if the above check is not true, check if the hierarchy is the other way around.
                else if(Scene.v().getOrMakeFastHierarchy().isSubclass(Scene.v().getSootClass(rootNodeClassFullyQualifiedName), Scene.v().getSootClass(nodeUnderConsiderationClassFullyQualifiedName))){
                    // Update the parent of the switched node.
                    rootNode.parent.addChild(nodeUnderConsideration.data);
                    rootNode.parent.children.remove(rootNode);
                    // If the switched node has children, go through them the same way as the forward scenario.
                    for (TreeNode<ClassSpecification> child : nodeUnderConsideration.children) {
                        successfulInsertion = insertNode(child, nodeUnderConsideration);
                    }
                    if(!successfulInsertion){
                        nodeUnderConsideration.addChild(rootNode.data);
                        successfulInsertion = true;
                    }

                } else {
                    return false;
                }
        }

        return successfulInsertion;

    }


    /**
     * *****************************************************************************************************************
     * based on the new class
     *
     */

    public RuleTree(List<ClassSpecification> specifications, boolean psuedo) {

        listOfTreeNodes = new ArrayList<>();
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
        }

        // change the return statement;
        return newRuleHeir;
    }

    //recursive
    public boolean newInsertNode(TreeNode<TreeNodeData> nodeUnderConsideration, TreeNode<TreeNodeData> rootNode){
        boolean successfulInsertion = false; // to stop duplicates after tree parsing if one of the children has a successful insert.

        if (!nodeUnderConsideration.data.getSootClass().equals(rootNode.data.getSootClass())){
                System.out.println(nodeUnderConsideration.data.getSootClass().getName());
            System.out.println(rootNode.data.getSootClass().getName() + "\n");
                if (Scene.v().getOrMakeFastHierarchy().isSubclass(nodeUnderConsideration.data.getSootClass(), rootNode.data.getSootClass())){

                    for (TreeNode<TreeNodeData> child : rootNode.children) {
                        successfulInsertion = newInsertNode(nodeUnderConsideration, child);
                    }
                    // after traversal through the children, if insertion is unsuccessful, or if there are no children, add the node as a child.
                    if(!successfulInsertion){
                        rootNode.addChild(nodeUnderConsideration.data);
                        successfulInsertion = true;
                    }

                }// if the above check is not true, check if the hierarchy is the other way around.
                else if(Scene.v().getOrMakeFastHierarchy().isSubclass(rootNode.data.getSootClass(), nodeUnderConsideration.data.getSootClass())){
                    // Update the parent of the switched node.
                    rootNode.parent.addChild(nodeUnderConsideration.data);
                    rootNode.parent.children.remove(rootNode);

                    for (TreeNode<TreeNodeData> child : nodeUnderConsideration.children) {
                        successfulInsertion = newInsertNode(child, nodeUnderConsideration);
                    }
                    if(!successfulInsertion){
                        nodeUnderConsideration.addChild(rootNode.data);
                        successfulInsertion = true;
                    }

                } else {
                    return false;
                }
        }

        return successfulInsertion;
    }

}