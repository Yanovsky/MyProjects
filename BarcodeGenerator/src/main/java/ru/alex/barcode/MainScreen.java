package ru.alex.barcode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;

public class MainScreen extends JFrame {
    private JTextField edtFileInput;
    private JButton btnLoadInput;
    private JTextField edtFileOutput;
    private JButton btnLoadOutput;
    private JComboBox<String> encoding;
    private JComboBox<BarcodeFormat> barcodeType;
    private JButton btnBegin;
    private JPanel pnlMain;
    private JComboBox<Character> delimiter;
    private JTable tblData;

    public MainScreen() {
        $$$setupUI$$$();
        setContentPane(pnlMain);
        setSize(new Dimension(600, 400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        encoding.setModel(new DefaultComboBoxModel<>(new String[]{ StandardCharsets.UTF_8.name(), "cp1251", "cp866" }));
        encoding.setSelectedIndex(0);
        barcodeType.setModel(new DefaultComboBoxModel<>(BarcodeFormat.values()));
        barcodeType.setSelectedItem(BarcodeFormat.PDF_417);
        delimiter.setModel(new DefaultComboBoxModel<>(new Character[]{ '~', '^', '|', ';' }));
        delimiter.setSelectedIndex(0);

        btnLoadInput.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            File file = StringUtils.isNoneBlank(edtFileInput.getText()) ? FileUtils.getFile(edtFileInput.getText()) : new File(".");
            fileChooser.setCurrentDirectory(file);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Текстовые файлы", "txt");
            fileChooser.setFileFilter(filter);
            if (fileChooser.showOpenDialog(pnlMain) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                edtFileInput.setText(selectedFile.toString());
                try {
                    List<String> lines = FileUtils.readLines(selectedFile, Charset.forName(encoding.getSelectedItem().toString()));
                    tblData.setModel(new DefaultTableModel() {
                        @Override
                        public int getColumnCount() {
                            return 2;
                        }

                        @Override
                        public int getRowCount() {
                            return lines.size();
                        }

                        @Override
                        public Object getValueAt(int row, int column) {
                            return super.getValueAt(row, column);
                        }
                    });
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnLoadOutput.addActionListener(e -> {

        });
        btnBegin.addActionListener(e -> {

        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        pnlMain = new JPanel();
        pnlMain.setLayout(new GridBagLayout());
        pnlMain.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), null));
        final JScrollPane scrollPane1 = new JScrollPane();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        pnlMain.add(scrollPane1, gbc);
        tblData = new JTable();
        scrollPane1.setViewportView(tblData);
        edtFileInput = new JTextField();
        edtFileInput.setBackground(new Color(-1));
        edtFileInput.setEditable(false);
        edtFileInput.setEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        pnlMain.add(edtFileInput, gbc);
        edtFileOutput = new JTextField();
        edtFileOutput.setBackground(new Color(-1));
        edtFileOutput.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        pnlMain.add(edtFileOutput, gbc);
        encoding = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        encoding.setModel(defaultComboBoxModel1);
        encoding.setName("");
        encoding.putClientProperty("html.disable", Boolean.FALSE);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlMain.add(encoding, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Файл для чтения");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        pnlMain.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Файл для сохранения");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        pnlMain.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Кодировка");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        pnlMain.add(label3, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("Тип штрих-кода");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        pnlMain.add(label4, gbc);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.BOTH;
        pnlMain.add(panel1, gbc);
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0), null));
        btnBegin = new JButton();
        btnBegin.setText("Начать");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(btnBegin, gbc);
        barcodeType = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlMain.add(barcodeType, gbc);
        final JLabel label5 = new JLabel();
        label5.setHorizontalTextPosition(4);
        label5.setText("Разделитель");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        pnlMain.add(label5, gbc);
        btnLoadInput = new JButton();
        btnLoadInput.setHideActionText(false);
        btnLoadInput.setIcon(new ImageIcon(getClass().getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
        btnLoadInput.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlMain.add(btnLoadInput, gbc);
        btnLoadOutput = new JButton();
        btnLoadOutput.setIcon(new ImageIcon(getClass().getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
        btnLoadOutput.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlMain.add(btnLoadOutput, gbc);
        delimiter = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlMain.add(delimiter, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlMain.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        pnlMain.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        pnlMain.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.VERTICAL;
        pnlMain.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.VERTICAL;
        pnlMain.add(spacer5, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() { return pnlMain; }
}
