package crypto.preanalysis;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {

    public T data;
    public TreeNode<T> parent;
    public List<TreeNode<T>> children;

    public boolean isRoot() {
        return parent == null;
    }

    public TreeNode(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    /**
     * @param child
     */
    public void addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
    }

    /**
     * To id the correct rule to be returned
     * @return
     */
    public int getDepth() {
        if (this.isRoot()) {
            return 0;
        } else {
            return parent.getDepth() + 1;
        }

    }

}