package ru.alex.barcode;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;

import ru.alex.barcode.tools.RetentiveFrame;
import ru.alex.barcode.visual.TableModel;

public class MainScreen extends RetentiveFrame {
    private List<String> lines = Collections.emptyList();
    private final TableModel model = new TableModel();
    private Path fileOutput;
    private Path fileInput;
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

    @Override
    protected void saveParameters() {
        properties.setProperty("fileInput", fileInput.toString());
        properties.setProperty("fileOutput", fileOutput.toString());
        properties.setProperty("delimiter", Objects.requireNonNull(delimiter.getSelectedItem()).toString());
        properties.setProperty("encoding", Objects.requireNonNull(encoding.getSelectedItem()).toString());
        properties.setProperty("barcodeType", Objects.requireNonNull(barcodeType.getSelectedItem()).toString());
        super.saveParameters();
    }

    @Override
    protected void loadParameters() {
        super.loadParameters();
        if (properties.containsKey("fileInput")) {
            fileInput = Paths.get(properties.getProperty("fileInput"));
            if (Files.exists(fileInput)) {
                edtFileInput.setText(fileInput.toString());
            } else {
                fileInput = null;
            }
        }
        if (properties.containsKey("fileOutput")) {
            fileOutput = Paths.get(properties.getProperty("fileOutput"));
            if (Files.exists(fileOutput.getParent())) {
                edtFileOutput.setText(fileOutput.toString());
            } else {
                fileOutput = null;
            }
        }
        if (properties.containsKey("encoding")) {
            encoding.setSelectedItem(Charset.forName(properties.getProperty("encoding")));
        }
        if (properties.containsKey("barcodeType")) {
            barcodeType.setSelectedItem(BarcodeFormat.valueOf(properties.getProperty("barcodeType")));
        }
        delimiter.setSelectedItem(properties.getProperty("delimiter").charAt(0));
        if (fileInput != null) {
            SwingUtilities.invokeLater(() -> {
                try {
                    loadData();
                } catch (Exception ignored) {
                }
            });
        }
    }

    MainScreen(String configFile) {
        super(configFile);
        $$$setupUI$$$();
        setTitle("Экспорт штрихкодов в MS Word файл");
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
        tblData.setModel(model);

        btnLoadInput.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            File file = StringUtils.isNoneBlank(edtFileInput.getText()) ? FileUtils.getFile(edtFileInput.getText()) : new File(".");
            fileChooser.setCurrentDirectory(file);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Текстовые файлы", "txt");
            fileChooser.setFileFilter(filter);
            if (fileChooser.showOpenDialog(pnlMain) == JFileChooser.APPROVE_OPTION) {
                fileInput = Paths.get(fileChooser.getSelectedFile().toURI());
                edtFileInput.setText(fileInput.toString());
                try {
                    loadData();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(pnlMain, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnLoadOutput.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            File file = StringUtils.isNoneBlank(edtFileOutput.getText()) ? FileUtils.getFile(edtFileOutput.getText()) : new File(".");
            fileChooser.setCurrentDirectory(file);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Файлы MS Word", "doc");
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            if (fileChooser.showSaveDialog(pnlMain) == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                if (!fileChooser.getFileFilter().accept(file)) {
                    file = new File(file.toString() + "." + filter.getExtensions()[0]);
                }
                fileOutput = Paths.get(file.toURI());
                edtFileOutput.setText(fileOutput.toString());
            }
        });
        delimiter.addActionListener(e -> {
            if (fileInput != null) {
                model.setDelimiter(Objects.requireNonNull(delimiter.getSelectedItem()).toString());
                resizeColumnWidth(tblData);
            }
        });
        btnBegin.addActionListener(e -> {
            String separator = Objects.requireNonNull(delimiter.getSelectedItem()).toString();
            try {
                if (lines.stream().anyMatch(s -> StringUtils.isBlank(StringUtils.substringAfter(s, separator)))) {
                    throw new Exception("Список акцизных марок пуст.\nПроверьте правильно ли выбран разделитель");
                }

                Map<String, List<String>> excises = lines.stream()
                    .collect(
                        Collectors.groupingBy(
                            s -> StringUtils.substringBefore(s, separator),
                            Collectors.mapping(n -> StringUtils.trimToNull(StringUtils.substringAfter(n, separator)), Collectors.toList())
                        )
                    );
                if (excises.isEmpty()) {
                    throw new Exception("Список пуст");
                }

                Generator.generate((BarcodeFormat) barcodeType.getSelectedItem(), excises, fileOutput);
                if (JOptionPane.showInternalConfirmDialog(
                    pnlMain,
                    String.format("Файл %s сформирован\nОткрыть в MS Word?", fileOutput.toString()),
                    "Готово",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(fileOutput.toFile());
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(pnlMain, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        loadParameters();
    }

    private void loadData() throws IOException {
        lines = FileUtils.readLines(fileInput.toFile(), Charset.forName(Objects.requireNonNull(encoding.getSelectedItem()).toString()));
        model.setLines(lines, Objects.requireNonNull(delimiter.getSelectedItem()).toString());
        resizeColumnWidth(tblData);
    }

    private void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
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
        scrollPane1.setHorizontalScrollBarPolicy(32);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 8;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        pnlMain.add(scrollPane1, gbc);
        tblData = new JTable();
        tblData.setAutoResizeMode(0);
        tblData.setFillsViewportHeight(true);
        scrollPane1.setViewportView(tblData);
        edtFileInput = new JTextField();
        edtFileInput.setBackground(new Color(-1));
        edtFileInput.setEditable(false);
        edtFileInput.setEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        pnlMain.add(edtFileInput, gbc);
        edtFileOutput = new JTextField();
        edtFileOutput.setBackground(new Color(-1));
        edtFileOutput.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        pnlMain.add(edtFileOutput, gbc);
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 8;
        gbc.fill = GridBagConstraints.BOTH;
        pnlMain.add(panel1, gbc);
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0), null));
        btnBegin = new JButton();
        btnBegin.setText("Начать");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(btnBegin, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        pnlMain.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        pnlMain.add(spacer2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Тип штрих-кода");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        pnlMain.add(label3, gbc);
        barcodeType = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlMain.add(barcodeType, gbc);
        final JLabel label4 = new JLabel();
        label4.setHorizontalTextPosition(4);
        label4.setText("Разделитель");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        pnlMain.add(label4, gbc);
        delimiter = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlMain.add(delimiter, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.VERTICAL;
        pnlMain.add(spacer3, gbc);
        btnLoadOutput = new JButton();
        btnLoadOutput.setIcon(new ImageIcon(getClass().getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
        btnLoadOutput.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        pnlMain.add(btnLoadOutput, gbc);
        btnLoadInput = new JButton();
        btnLoadInput.setHideActionText(false);
        btnLoadInput.setIcon(new ImageIcon(getClass().getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
        btnLoadInput.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        pnlMain.add(btnLoadInput, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("Кодировка");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlMain.add(label5, gbc);
        encoding = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        encoding.setModel(defaultComboBoxModel1);
        encoding.setName("");
        encoding.putClientProperty("html.disable", Boolean.FALSE);
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlMain.add(encoding, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlMain.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlMain.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlMain.add(spacer6, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() { return pnlMain; }
}
