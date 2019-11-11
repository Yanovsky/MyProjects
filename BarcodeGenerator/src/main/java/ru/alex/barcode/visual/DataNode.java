package ru.alex.barcode.visual;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.TreeNode;

public class DataNode implements TreeNode {
    private List<DataNode> children = new ArrayList<>();
    private TreeNode parent;
    private String description;

    DataNode(DataNode parent, String description) {
        this.parent = parent;
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    void addNode(DataNode node) {
        children.add(node);
    }

    @Override
    public DataNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return children.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public Enumeration children() {
        Iterator<DataNode> iterator = children.iterator();
        return new Enumeration() {
            @Override
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            @Override
            public Object nextElement() {
                return iterator.next();
            }
        };
    }

    public String getDescription() {
        return description;
    }

    public List<DataNode> getChildren() {
        return children;
    }
}
