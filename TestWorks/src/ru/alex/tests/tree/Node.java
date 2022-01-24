package ru.alex.tests.tree;

import java.util.ArrayList;
import java.util.Collection;

public class Node {
    private int id;
    private Collection<Node> children = new ArrayList<>();

    public Node(int id) {
        this.id = id;
    }

    public Collection<Node> getChildren() {
        return children;
    }

    public int getId() {
        return id;
    }
}
