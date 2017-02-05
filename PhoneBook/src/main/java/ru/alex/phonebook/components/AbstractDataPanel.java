package ru.alex.phonebook.components;

import javax.swing.AbstractButton;
import javax.swing.JPanel;

import ezvcard.property.VCardProperty;

public abstract class AbstractDataPanel<T extends VCardProperty> extends JPanel {
    private static final long serialVersionUID = 1L;

    public abstract void setData(T data);

    public abstract T getData();

    public abstract AbstractButton getCheckBox();

}
