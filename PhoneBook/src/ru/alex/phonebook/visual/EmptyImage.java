package ru.alex.phonebook.visual;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class EmptyImage implements Icon {

    private int width;
    private int height;

    public EmptyImage() {
        this(0, 0);
    }

    public EmptyImage(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getIconHeight() {
        return height;
    }

    public int getIconWidth() {
        return width;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
    }

}
