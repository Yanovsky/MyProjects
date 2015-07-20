package ru.alex.phonebook.visual;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.property.StructuredName;

public class PhoneBookFrame extends RetentiveFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField edtPhoneBookFile;
    private JTable tblBook;
    private PhoneBookModel model = new PhoneBookModel();
    private Action actOpen = new AbstractAction("Открыть") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            UIManager.put("FileChooser.readOnly", Boolean.TRUE);
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setMultiSelectionEnabled(false);
            fc.setSelectedFile(model.getPhoneBookFile());
            fc.setFileFilter(new FileNameExtensionFilter("Файлы записных книг (*.vcf)", "vcf"));
            if (fc.showOpenDialog(PhoneBookFrame.this) == JFileChooser.APPROVE_OPTION) {
                File phoneBookFile = fc.getSelectedFile();
                setPhoneBookFile(phoneBookFile);
            }
        }
    };
    private Action actEditRecord = new AbstractAction("Редактировать") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            VCard card = model.getCardAt(tblBook.getSelectedRow());
            CardDialog cardDialog = new CardDialog(card, PhoneBookFrame.this);
            if (cardDialog.showDialog() == CardDialog.APPROVE_OPTION) {

            }
        }
    };
    private Action actAddRecord = new AbstractAction("Создать запись") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            VCard card = new VCard();
            card.setStructuredName(new StructuredName());
            CardDialog cardDialog = new CardDialog(card, PhoneBookFrame.this);
            if (cardDialog.showDialog() == CardDialog.APPROVE_OPTION) {
                final int inserted = model.getAddCard(card);
                tblBook.getSelectionModel().setSelectionInterval(inserted, inserted);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        tblBook.scrollRectToVisible(tblBook.getCellRect(inserted, inserted, true));
                    }
                });
            }
        }
    };
    private Action actDeleteRecord = new AbstractAction("Удалить запись") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {

            int i = tblBook.getSelectedRow();
            if (i > -1 &&
                JOptionPane.showConfirmDialog(PhoneBookFrame.this, "Удалить запись?", "Вопрос", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                model.removeCard(i);
                if (i > tblBook.getRowCount() - 1) {
                    i = tblBook.getRowCount() - 1;
                }
                tblBook.getSelectionModel().setSelectionInterval(i, i);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        tblBook.scrollRectToVisible(tblBook.getCellRect(tblBook.getSelectedRow(), tblBook.getSelectedColumn(), true));
                    }
                });
            }
        }
    };
    private Action actNewBook = new AbstractAction("Новая книга") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("NEW BOOK");
        }
    };
    private Action actSave = new AbstractAction("Сохранить книгу") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            UIManager.put("FileChooser.readOnly", Boolean.TRUE);
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setMultiSelectionEnabled(false);
            fc.setSelectedFile(model.getPhoneBookFile());
            fc.setFileFilter(new FileNameExtensionFilter("Файлы записных книг (*.vcf)", "vcf"));
            if (fc.showSaveDialog(PhoneBookFrame.this) == JFileChooser.APPROVE_OPTION) {
                File phoneBookFile = fc.getSelectedFile();
                if (StringUtils.isEmpty(FilenameUtils.getExtension(phoneBookFile.getName()))) {
                    phoneBookFile = new File(fc.getSelectedFile().getPath() + ".vcf");
                }
                try {
                    int i = tblBook.getSelectedRow();
                    Ezvcard.write(model.getPhoneBook()).version(VCardVersion.V3_0).go(phoneBookFile);
                    setPhoneBookFile(phoneBookFile);
                    tblBook.getSelectionModel().setSelectionInterval(i, i);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            tblBook.scrollRectToVisible(tblBook.getCellRect(tblBook.getSelectedRow(), tblBook.getSelectedColumn(), true));
                        }
                    });
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    };

    protected void setPhoneBookFile(File phoneBookFile) {
        try {
            model.setPhoneBookFile(phoneBookFile);
            edtPhoneBookFile.setText(model.getPhoneBookFile().getPath());
            if (tblBook.getRowCount() > 0) {
                tblBook.getSelectionModel().setSelectionInterval(0, 0);
            }
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    tblBook.scrollRectToVisible(tblBook.getCellRect(tblBook.getSelectedRow(), tblBook.getSelectedColumn(), true));
                }
            });
        } catch (IOException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, e1.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void loadParameters() {
        super.loadParameters();
        String phoneBookFilePath = properties.getProperty("phoneBookFile", "");
        if (phoneBookFilePath != null) {
            File f = new File(phoneBookFilePath);
            if (f != null && f.exists()) {
                setPhoneBookFile(f);
            }
        }
    }

    @Override
    protected void saveParameters() {
        properties.setProperty("phoneBookFile", edtPhoneBookFile.getText());
        super.saveParameters();
    }

    public PhoneBookFrame(String configFile) {
        super(configFile);
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/ru/alex/phonebook/img/main.png")));
        setTitle("Телефонная книга");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        panel.setLayout(new BorderLayout(0, 0));

        edtPhoneBookFile = new JTextField();
        edtPhoneBookFile.setEditable(false);
        panel.add(edtPhoneBookFile);
        edtPhoneBookFile.setColumns(10);

        JLabel label = new JLabel("Файл");
        panel.add(label, BorderLayout.WEST);

        JButton btnNewButton = new JButton(actOpen);
        panel.add(btnNewButton, BorderLayout.EAST);

        JPanel panel_1 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
        flowLayout.setVgap(0);
        flowLayout.setHgap(0);
        flowLayout.setAlignment(FlowLayout.LEFT);
        contentPane.add(panel_1, BorderLayout.SOUTH);

        JButton btnEditRecord = new JButton(actEditRecord);
        panel_1.add(btnEditRecord);
        JButton btnDeleteRecord = new JButton(actDeleteRecord);
        panel_1.add(btnDeleteRecord);
        JButton btnAddRecord = new JButton(actAddRecord);
        panel_1.add(btnAddRecord);
        JButton btnSave = new JButton(actSave);
        panel_1.add(btnSave);
        JButton btnNewBook = new JButton(actNewBook);
        panel_1.add(btnNewBook);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        tblBook = new JTable(model);
        scrollPane.setViewportView(tblBook);
        formatTable();

        loadParameters();
    }

    private void formatTable() {
        tblBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    actEditRecord.actionPerformed(null);
                }
            }
        });
        tblBook.setFillsViewportHeight(true);
        tblBook.setCellSelectionEnabled(false);
        tblBook.setRowSelectionAllowed(true);
        tblBook.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblBook.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                table.setRowHeight(row, Math.max(table.getRowHeight(row), lbl.getPreferredSize().height));
                return lbl;
            }
        });
        TableColumn column = tblBook.getColumnModel().getColumn(2);
        column.setMinWidth(100);
        column.setMaxWidth(100);
        column.setCellRenderer(new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null) {
                    lbl.setText("");
                    lbl.setIcon((Icon) value);
                    if (lbl.getIcon().getIconHeight() > 0) {
                        table.setRowHeight(row, Math.max(table.getRowHeight(row), lbl.getIcon().getIconHeight()));
                    }
                }
                return lbl;
            }
        });
        TableCellRenderer rendererFromHeader = tblBook.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
    }

}
