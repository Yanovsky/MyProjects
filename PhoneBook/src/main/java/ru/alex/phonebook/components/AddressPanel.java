package ru.alex.phonebook.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ezvcard.parameter.AddressType;
import ezvcard.property.Address;

public class AddressPanel extends AbstractDataPanel<Address> {
    private static final long serialVersionUID = 1L;
    private JComboBox<ru.alex.phonebook.classes.AddressType> edtType;
    private JCheckBox edtMain;
    private JTextField edtCountry;
    private JTextField edtPostalCode;
    private JTextField edtLocality;
    private JTextField edtRegion;
    private JTextField edtStreetAddress;
    private JTextField edtExtendedAddress;
    private JPanel pnlData;

    private Action actDelete = new AbstractAction("удалить") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (JOptionPane.showConfirmDialog(getParent(), "Удалить?", "Вопрос", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                Executors.newCachedThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        JPanel panel = (JPanel) getParent();
                        panel.remove(AddressPanel.this);
                        panel.updateUI();
                    }
                });
            }
        }
    };

    public AddressPanel() {
        setPreferredSize(new Dimension(430, 135));
        setMinimumSize(new Dimension(430, 135));
        setLayout(new BorderLayout(0, 5));

        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        panel.setLayout(new BorderLayout(0, 0));

        edtType = new JComboBox<ru.alex.phonebook.classes.AddressType>(ru.alex.phonebook.classes.AddressType.values());
        panel.add(edtType, BorderLayout.WEST);

        edtMain = new JCheckBox("Основной");
        edtMain.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Font font = pnlData.getFont().deriveFont(edtMain.isSelected() ? Font.BOLD : Font.PLAIN);
                pnlData.setFont(font);
                edtType.setFont(font);
                edtMain.setFont(font);
                for (Component c : pnlData.getComponents()) {
                    c.setFont(font);
                }
            }
        });
        panel.add(edtMain, BorderLayout.EAST);

        pnlData = new JPanel();
        add(pnlData, BorderLayout.CENTER);
        GridBagLayout gbl_pnlData = new GridBagLayout();
        gbl_pnlData.columnWidths = new int[] {0, 0, 0, 0, 0};
        gbl_pnlData.rowHeights = new int[] {0, 0, 0, 0, 0};
        gbl_pnlData.columnWeights = new double[] {0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_pnlData.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        pnlData.setLayout(gbl_pnlData);

        JLabel lblNewLabel = new JLabel("Страна");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        pnlData.add(lblNewLabel, gbc_lblNewLabel);

        edtCountry = new JTextField();
        GridBagConstraints gbc_edtCountry = new GridBagConstraints();
        gbc_edtCountry.insets = new Insets(0, 0, 5, 5);
        gbc_edtCountry.fill = GridBagConstraints.HORIZONTAL;
        gbc_edtCountry.gridx = 1;
        gbc_edtCountry.gridy = 0;
        pnlData.add(edtCountry, gbc_edtCountry);
        edtCountry.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("Регион");
        GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_3.gridx = 2;
        gbc_lblNewLabel_3.gridy = 0;
        pnlData.add(lblNewLabel_3, gbc_lblNewLabel_3);

        edtRegion = new JTextField();
        GridBagConstraints gbc_edtRegion = new GridBagConstraints();
        gbc_edtRegion.insets = new Insets(0, 0, 5, 0);
        gbc_edtRegion.fill = GridBagConstraints.HORIZONTAL;
        gbc_edtRegion.gridx = 3;
        gbc_edtRegion.gridy = 0;
        pnlData.add(edtRegion, gbc_edtRegion);
        edtRegion.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Индекс");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 1;
        pnlData.add(lblNewLabel_1, gbc_lblNewLabel_1);

        edtPostalCode = new JTextField();
        GridBagConstraints gbc_edtPostalCode = new GridBagConstraints();
        gbc_edtPostalCode.insets = new Insets(0, 0, 5, 5);
        gbc_edtPostalCode.fill = GridBagConstraints.HORIZONTAL;
        gbc_edtPostalCode.gridx = 1;
        gbc_edtPostalCode.gridy = 1;
        pnlData.add(edtPostalCode, gbc_edtPostalCode);
        edtPostalCode.setColumns(6);

        JLabel lblNewLabel_2 = new JLabel("Город");
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_2.gridx = 2;
        gbc_lblNewLabel_2.gridy = 1;
        pnlData.add(lblNewLabel_2, gbc_lblNewLabel_2);

        edtLocality = new JTextField();
        GridBagConstraints gbc_edtLocality = new GridBagConstraints();
        gbc_edtLocality.insets = new Insets(0, 0, 5, 0);
        gbc_edtLocality.fill = GridBagConstraints.HORIZONTAL;
        gbc_edtLocality.gridx = 3;
        gbc_edtLocality.gridy = 1;
        pnlData.add(edtLocality, gbc_edtLocality);
        edtLocality.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("Улица");
        GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
        gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_4.gridx = 0;
        gbc_lblNewLabel_4.gridy = 2;
        pnlData.add(lblNewLabel_4, gbc_lblNewLabel_4);

        edtStreetAddress = new JTextField();
        GridBagConstraints gbc_edtStreetAddress = new GridBagConstraints();
        gbc_edtStreetAddress.gridwidth = 3;
        gbc_edtStreetAddress.insets = new Insets(0, 0, 5, 0);
        gbc_edtStreetAddress.fill = GridBagConstraints.HORIZONTAL;
        gbc_edtStreetAddress.gridx = 1;
        gbc_edtStreetAddress.gridy = 2;
        pnlData.add(edtStreetAddress, gbc_edtStreetAddress);
        edtStreetAddress.setColumns(10);

        JLabel lblNewLabel_5 = new JLabel("Доп.");
        GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
        gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_5.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel_5.gridx = 0;
        gbc_lblNewLabel_5.gridy = 3;
        pnlData.add(lblNewLabel_5, gbc_lblNewLabel_5);

        edtExtendedAddress = new JTextField();
        GridBagConstraints gbc_edtExtendedAddress = new GridBagConstraints();
        gbc_edtExtendedAddress.gridwidth = 3;
        gbc_edtExtendedAddress.fill = GridBagConstraints.HORIZONTAL;
        gbc_edtExtendedAddress.gridx = 1;
        gbc_edtExtendedAddress.gridy = 3;
        pnlData.add(edtExtendedAddress, gbc_edtExtendedAddress);
        edtExtendedAddress.setColumns(10);

        JPanel panel_2 = new JPanel();
        add(panel_2, BorderLayout.EAST);

        JButton btnNewButton = new JButton(actDelete);
        panel_2.add(btnNewButton);
    }

    @Override
    public void setData(Address data) {
        edtCountry.setText(data.getCountry());
        edtRegion.setText(data.getRegion());
        edtPostalCode.setText(data.getPostalCode());
        edtLocality.setText(data.getLocality());
        edtStreetAddress.setText(data.getStreetAddress());
        edtExtendedAddress.setText(data.getExtendedAddress());
        for (AddressType type : data.getTypes()) {
            if (type == AddressType.PREF) {
                edtMain.setSelected(true);
            } else {
                edtType.setSelectedItem(ru.alex.phonebook.classes.AddressType.valueOfType(type));
            }
        }
    }

    @Override
    public Address getData() {
        Address result = new Address();
        result.setCountry(edtCountry.getText());
        result.setRegion(edtRegion.getText());
        result.setPostalCode(edtPostalCode.getText());
        result.setLocality(edtLocality.getText());
        result.setStreetAddress(edtStreetAddress.getText());
        result.setExtendedAddress(edtExtendedAddress.getText());
        result.getTypes().clear();
        if (edtMain.isSelected()) {
            result.addType(AddressType.PREF);
        }
        ru.alex.phonebook.classes.AddressType t = (ru.alex.phonebook.classes.AddressType) edtType.getSelectedItem();
        AddressType type = AddressType.get(t.name());
        result.addType(type);
        return result;
    }

    @Override
    public AbstractButton getCheckBox() {
        return edtMain;
    }

}
