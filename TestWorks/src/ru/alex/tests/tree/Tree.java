package ru.alex.tests.tree;

public class Tree {
    private Node root = new Node(-1);
    private int depth;
    private int nowDepth;

    public Tree(int depth) {
        this.nowDepth = 1;
        this.depth = depth;
        for (int i = 0; i < 10; i++) {
            root.getChildren().add(createNode((int) (i + Math.pow(10, nowDepth))));
        }
    }

    private Node createNode(int id) {
        Node node = new Node(id);
        if (nowDepth < depth) {
            nowDepth++;
            for (int i = 0; i < 10; i++) {
                node.getChildren().add(createNode((int) (i + Math.pow(10, nowDepth))));
            }
        }
        return node;
    }
}
