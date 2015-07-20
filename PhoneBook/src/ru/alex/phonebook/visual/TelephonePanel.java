package ru.alex.phonebook.visual;

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

import ezvcard.parameter.TelephoneType;
import ezvcard.property.Telephone;

public class TelephonePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField edtPhoneNumber;
    private JComboBox<PhoneType> edtType;
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
                        panel.remove(TelephonePanel.this);
                        panel.updateUI();
                    }
                });
            }
        }
    };

    public TelephonePanel() {
        setLayout(new BorderLayout(0, 0));

        edtPhoneNumber = new JTextField();
        add(edtPhoneNumber, BorderLayout.CENTER);

        JButton btnNewButton = new JButton(actDelete);
        add(btnNewButton, BorderLayout.EAST);

        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        panel.setLayout(new BorderLayout(0, 0));

        edtType = new JComboBox<PhoneType>(PhoneType.values());
        panel.add(edtType, BorderLayout.WEST);

        edtMain = new JCheckBox("Основной");
        edtMain.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Font font = edtPhoneNumber.getFont().deriveFont(edtMain.isSelected() ? Font.BOLD : Font.PLAIN);
                edtPhoneNumber.setFont(font);
                edtType.setFont(font);
                edtMain.setFont(font);
            }
        });
        panel.add(edtMain, BorderLayout.EAST);
    }

    public void setTelephone(Telephone number) {
        edtPhoneNumber.setText(number.getText());
        for (TelephoneType type : number.getTypes()) {
            if (type == TelephoneType.PREF) {
                edtMain.setSelected(true);
            } else {
                edtType.setSelectedItem(PhoneType.valueOfType(type));
            }
        }
    }

    public Telephone getTelephone() {
        Telephone result = new Telephone(edtPhoneNumber.getText());
        result.getTypes().clear();
        if (edtMain.isSelected()) {
            result.addType(TelephoneType.PREF);
        }
        PhoneType t = (PhoneType) edtType.getSelectedItem();
        TelephoneType type = TelephoneType.get(t.name());
        result.addType(type);
        return result;
    }

    public AbstractButton getCheckBox() {
        return edtMain;
    }

}
