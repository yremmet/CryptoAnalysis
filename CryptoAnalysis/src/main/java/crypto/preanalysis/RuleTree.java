package crypto.preanalysis;

import crypto.Utils;
import crypto.analysis.ClassSpecification;
import soot.Scene;
import soot.SootClass;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RuleTree {
    private List<TreeNode<ClassSpecification>> treeNodes;
    private TreeNode<ClassSpecification>objectNode;

    public RuleTree(List<ClassSpecification> specifications) {
        //TODO the tree nodes need to hold the class as well as its specification.
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

            /*if (ruleHeir.children.size()==0){
                ruleHeir.addChild(treeNode.data);
            } else{
                for (TreeNode<ClassSpecification> node : ruleHeir.children) {
                    if(Scene.v().getFastHierarchy().isSubclass(Scene.v().getSootClass(node.data.getRule().getClassName()), Scene.v().getSootClass(treeNode.data.getRule().getClassName()))   ){

                    }
                }
            }*/

            insertNode(treeNode, ruleHeir);

            //insertNodeNonRec(treeNode);

            // part of initial phase where I was adding all the nodes on the same level.
            //ruleHeir.addChild(treeNode.data);


        }


        // change the return statement;
        return ruleHeir;
    }

    public void insertNodeNonRec(TreeNode<ClassSpecification> treeNodeParent){
        for (TreeNode<ClassSpecification> treeNode : treeNodes) {
            if (Scene.v().getFastHierarchy().isSubclass(Scene.v().getSootClass(treeNode.data.getRule().getClassName()),Scene.v().getSootClass(treeNodeParent.data.getRule().getClassName()))){
                treeNodeParent.addChild(treeNode.data);
            }

        }
    }

    // recursive
    public boolean insertNode(TreeNode<ClassSpecification> nodeUnderConsideration, TreeNode<ClassSpecification> rootNode){
        boolean successfulInsertion = false; // to stop duplicates after tree parsing if one of the children has a successful insert.
        String nodeUnderConsiderationClassFullyQualifiedName = Utils.getFullyQualifiedName(nodeUnderConsideration.data.getRule());
        String rootNodeClassFullyQualifiedName = Utils.getFullyQualifiedName(rootNode.data.getRule());


        if (!nodeUnderConsiderationClassFullyQualifiedName.equals(rootNodeClassFullyQualifiedName)){
            //TODO for some reason, when nodeUnderConsiderationClassFullyQualifiedName.equals("java.security.Signature") && rootNodeClassFullyQualifiedName.equals("java.lang.Object") the scene.fastHeirarchy nodeUnderConsideration is lost.
            if (!(nodeUnderConsiderationClassFullyQualifiedName.equals("java.security.Signature") && rootNodeClassFullyQualifiedName.equals("java.lang.Object"))
                    && !(nodeUnderConsiderationClassFullyQualifiedName.equals("javax.crypto.spec.PBEParameterSpec") && rootNodeClassFullyQualifiedName.equals("java.lang.Object"))){
                // recursive, if the current class specification is the sub class of the current node then go through the children.
                if (Scene.v().getFastHierarchy().isSubclass(Scene.v().getSootClass(nodeUnderConsiderationClassFullyQualifiedName),Scene.v().getSootClass(rootNodeClassFullyQualifiedName))){
                    for (TreeNode<ClassSpecification> child : rootNode.children) {
                        successfulInsertion = insertNode(nodeUnderConsideration, child);
                    }
                    // after traversal through the children, if insertion is unsuccessful, or if there are no children, add the node as a child.
                    if(!successfulInsertion){
                        rootNode.addChild(nodeUnderConsideration.data);
                        successfulInsertion = true;
                    }


                }// if the above check is not true, check if the hierarchy is the other way around.
                else if(Scene.v().getFastHierarchy().isSubclass(Scene.v().getSootClass(rootNodeClassFullyQualifiedName), Scene.v().getSootClass(nodeUnderConsiderationClassFullyQualifiedName))){
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
        }



        return successfulInsertion;


        // get the subclasses of the rule of the node under consideration on the tree.
        /*Collection<SootClass> subclasses = Scene.v().getFastHierarchy().getSubclassesOf(Scene.v().getSootClass(rootNode.nodeUnderConsideration.getRule().getClassName()));

        subclasses = Scene.v().getActiveHierarchy().getSubclassesOf(Scene.v().getSootClass(rootNode.nodeUnderConsideration.getRule().getClassName()));
        nodeUnderConsideration.getRule().getClassName();
        rootNode.nodeUnderConsideration.getRule();
*/

    }
}