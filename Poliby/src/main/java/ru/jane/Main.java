package ru.jane;

import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        Cypher cypher = new Cypher();
        String value = JOptionPane.showInputDialog("Введите ФИО");
        if (value != null && !value.isEmpty()) {
            try {
                JOptionPane.showMessageDialog(null, cypher.encrypt1(value), "Результат", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
