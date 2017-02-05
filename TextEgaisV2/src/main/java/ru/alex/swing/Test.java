/*
 * Created by JFormDesigner on Sun Feb 05 16:26:31 MSK 2017
 */
package ru.alex.swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import ru.crystals.utils.FormDesignerUtils;

/**
 * @author ALEKSANDR YANOVSKY
 */
public class Test extends JFrame {
    public static void main(String[] args) {
        Test form = new Test();
        form.setSize(600, 400);
        form.setLocationRelativeTo(null);
        form.setVisible(true);
    }

    public Test() {
        initComponents();
        FormDesignerUtils.removeEvaluation(this);
    }

    private void createUIComponents() {
        comboBox1 = new JComboBox<>(new String[]{ "sdfsdfsdf", "asdasdas", "123123123" });
        comboBox1.setSelectedItem("123123123");

        textField1 = new JTextField();
    }

    private void comboBox1ActionPerformed(ActionEvent e) {
        textField1.setText(comboBox1.getSelectedItem().toString());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - ALEKSANDR YANOVSKY
        createUIComponents();

        testFrame = new JFrame();
        panel1 = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("\u041f\u0440\u043e\u0432\u0435\u0440\u043a\u0430");
        setFont(new Font("Dialog", Font.PLAIN, 12));
        setIconImage(((ImageIcon)UIManager.getIcon("FileView.computerIcon")).getImage());
        setName("testFrame");
        Container testFrameContentPane = getContentPane();
        testFrameContentPane.setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setBorder(new TitledBorder("Title"));
            panel1.setName("panel1");

            // JFormDesigner evaluation mark
            panel1.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), panel1.getBorder())); panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

            //---- label1 ----
            label1.setText("text");
            label1.setName("label1");
            panel1.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- textField1 ----
            textField1.setName("textField1");
            panel1.add(textField1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- label2 ----
            label2.setText("text");
            label2.setName("label2");
            panel1.add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- comboBox1 ----
            comboBox1.setName("comboBox1");
            comboBox1.addActionListener(e -> comboBox1ActionPerformed(e));
            panel1.add(comboBox1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        testFrameContentPane.add(panel1, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - ALEKSANDR YANOVSKY
    private JFrame testFrame;
    private JPanel panel1;
    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JComboBox comboBox1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
