package com.glavsoft.viewer.swing.gui;

import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.glavsoft.viewer.swing.ConnectionParams;

/**
 * @author dime at tightvnc.com
 */
public class HostnameComboboxRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        String stringValue = renderListItem((ConnectionParams)value);
        setText(stringValue);
        setFont(getFont().deriveFont(Font.PLAIN));
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;
    }

    public String renderListItem(ConnectionParams cp) {
        String s = "<html><b>" +cp.hostName + "</b>:" + cp.getPortNumber();
        return s + "</html>";
    }
}
