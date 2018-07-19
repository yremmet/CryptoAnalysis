package crypto.preanalysis;

import crypto.preanalysis.TreeNode;
import crypto.preanalysis.TreeNodeData;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class dotDataForRuleTree {
    Map<String, String> nodes;
    Map<String, Map.Entry<String, String>> edges;

    public dotDataForRuleTree() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
    }

    public void getNodesAndEdges(TreeNode<TreeNodeData> root) {
        nodes.put(root.data.getSootClass().getName().replace(".", ""), root.data.getSootClass().getName().replace(".", ""));
        for (TreeNode<TreeNodeData> child : root.children) {
            nodes.put(child.data.getSootClass().getName().replace(".", ""), child.data.getSootClass().getName().replace(".", ""));
            edges.put(root.data.getSootClass().getName().replace(".", "") + child.data.getSootClass().getName().replace(".", ""), new AbstractMap.SimpleEntry<String, String>(root.data.getSootClass().getName().replace(".", ""), child.data.getSootClass().getName().replace(".", "")));

            getNodesAndEdges(child);
        }
    }

    public void createDotFile() {
        StringBuilder builder = new StringBuilder();
        builder.append("digraph G { \n");
        builder.append(" rankdir=LR;\n");
        builder.append(" node[shape=box];\n");

        for (String s : nodes.keySet()) {
            builder.append(s);
            builder.append(" ");
            builder.append("[label=\"");
            builder.append(nodes.get(s));
            builder.append("\"]");
            builder.append(";\n");
        }

        for (String s : edges.keySet()) {
            Map.Entry<String, String> single = edges.get(s);

            builder.append(single.getKey());
            builder.append(" -> ");
            builder.append(single.getValue());
            builder.append("[label=\"");
            builder.append(s);
            builder.append("\"]");
            builder.append(";\n");
        }


        builder.append("}");
        System.out.println(builder.toString());
    }


}
