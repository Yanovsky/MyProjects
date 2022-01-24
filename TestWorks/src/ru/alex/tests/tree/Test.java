package ru.alex.tests.tree;

public class Test {
    private final Tree tree;

    public Test() {
        tree = new Tree(2);
    }

    public static void main(String[] args) {
        Test test = new Test();
        System.out.println(test);
    }
}
