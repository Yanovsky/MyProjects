package ru.alex.phonebook.components;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.concurrent.Executors;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ru.alex.phonebook.classes.EMailType;
import ezvcard.parameter.EmailType;
import ezvcard.property.Email;

public class EMailPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField edtEmail;
    private JComboBox<EMailType> edtType;
    private JCheckBox edtMain;

    private Action actDelete = new AbstractAction("удалить") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (JOptionPane.showConfirmDialog(getParent(), "Удалить?", "Вопрос", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                Executors.newCachedThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        JPanel panel = (JPanel) getParent();
                        panel.remove(EMailPanel.this);
                        panel.updateUI();
                    }
                });
            }
        }
    };

    public EMailPanel() {
        setLayout(new BorderLayout(0, 0));

        edtEmail = new JTextField();
        add(edtEmail, BorderLayout.CENTER);

        JButton btnNewButton = new JButton(actDelete);
        add(btnNewButton, BorderLayout.EAST);

        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        panel.setLayout(new BorderLayout(0, 0));

        edtType = new JComboBox<EMailType>(EMailType.values());
        panel.add(edtType, BorderLayout.WEST);

        edtMain = new JCheckBox("Основной");
        edtMain.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Font font = edtEmail.getFont().deriveFont(edtMain.isSelected() ? Font.BOLD : Font.PLAIN);
                edtEmail.setFont(font);
                edtType.setFont(font);
                edtMain.setFont(font);
            }
        });
        panel.add(edtMain, BorderLayout.EAST);
    }

    public void setEmail(Email email) {
        edtEmail.setText(email.getValue());
        for (EmailType type : email.getTypes()) {
            if (type == EmailType.PREF) {
                edtMain.setSelected(true);
            } else {
                edtType.setSelectedItem(EMailType.valueOfType(type));
            }
        }
    }

    public Email getEmail() {
        Email result = new Email(edtEmail.getText());
        result.getTypes().clear();
        if (edtMain.isSelected()) {
            result.addType(EmailType.PREF);
        }
        EMailType t = (EMailType) edtType.getSelectedItem();
        EmailType type = EmailType.get(t.name());
        result.addType(type);
        return result;
    }

    public AbstractButton getCheckBox() {
        return edtMain;
    }

}
