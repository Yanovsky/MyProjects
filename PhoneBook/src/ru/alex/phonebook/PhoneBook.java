package ru.alex.phonebook;

import java.awt.EventQueue;
import java.util.stream.Stream;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.commons.lang.StringUtils;

import ru.alex.phonebook.fx.PhoneBookLauncher;
import ru.alex.phonebook.visual.PhoneBookFrame;

public class PhoneBook {

    public static void main(final String[] args) {
        if (Stream.of(args).anyMatch(arg -> StringUtils.equalsIgnoreCase(arg, "-fx"))) {
            System.out.println("FX");
            PhoneBookLauncher.start(args);
        } else {
            EventQueue.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
                PhoneBookFrame frame = new PhoneBookFrame("PhoneBook.conf");
                frame.setVisible(true);
            });
        }
    }

}
