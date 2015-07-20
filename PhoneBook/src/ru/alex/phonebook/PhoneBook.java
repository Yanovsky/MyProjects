package ru.alex.phonebook;

import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import ru.alex.phonebook.visual.PhoneBookFrame;

public class PhoneBook {

    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
                PhoneBookFrame frame = new PhoneBookFrame("PhoneBook.conf");
                frame.setVisible(true);
            }
        });
    }

}
