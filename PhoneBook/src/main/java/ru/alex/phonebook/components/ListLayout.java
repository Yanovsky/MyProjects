package ru.alex.phonebook.components;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class ListLayout implements LayoutManager {

    private int gap = 5;

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            int height = 0;
            int width = 0;
            for (Component item : parent.getComponents()) {
                height += item.getPreferredSize().height + gap;
                width = Math.max(width, item.getPreferredSize().width);
            }
            return new Dimension(width, height);
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            int height = 0;
            int width = 0;
            for (Component item : parent.getComponents()) {
                height += item.getMinimumSize().height + gap;
                width = Math.max(width, item.getMinimumSize().width);
            }
            return new Dimension(width, height);
        }
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            int y = 0;
            for (Component item : parent.getComponents()) {
                item.setBounds(0, y, parent.getWidth(), item.getPreferredSize().height);
                y += item.getPreferredSize().height + gap;
            }
        }
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

}
