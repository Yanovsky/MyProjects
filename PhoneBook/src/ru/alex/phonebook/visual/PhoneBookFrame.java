package ru.alex.phonebook.visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.KeyEventPostProcessor;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import ru.alex.phonebook.components.RetentiveFrame;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.property.StructuredName;

public class PhoneBookFrame extends RetentiveFrame {
    private static final long serialVersionUID = 1L;
    private static final Font font = new Font("Verdana", Font.PLAIN, 12);
    public static File lastImageFolder;

    private Rectangle cardDialogBounds = null;
    private JPanel contentPane;
    private JTextField edtPhoneBookFile;
    private JTable tblBook;
    private JTextField edtSearch;
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
    private Action actEditRecord = new AbstractAction("Просмотреть/изменить") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            VCard card = model.getCardAt(tblBook.getSelectedRow());
            CardDialog cardDialog = new CardDialog(card);
            applyDialogBounds(cardDialog, getCardDialogBounds());
            cardDialog.showDialog();
            model.fireTableRowsUpdated(tblBook.getSelectedRow(), tblBook.getSelectedRow());
            setCardDialogBounds(cardDialog.getBounds());
        }
    };
    private Action actAddRecord = new AbstractAction("Новый контакт") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            VCard card = new VCard();
            card.setStructuredName(new StructuredName());
            CardDialog cardDialog = new CardDialog(card);
            applyDialogBounds(cardDialog, getCardDialogBounds());
            if (cardDialog.showDialog() == CardDialog.APPROVE_OPTION) {
                final int inserted = model.addCard(card);
                tblBook.getSelectionModel().setSelectionInterval(inserted, inserted);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        tblBook.scrollRectToVisible(tblBook.getCellRect(inserted, inserted, true));
                    }
                });
            }
            setCardDialogBounds(cardDialog.getBounds());
        }
    };
    private Action actDeleteRecord = new AbstractAction("Удалить") {
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
    private Action actSaveAs = new AbstractAction("Сохранить как") {
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
                saveBook(phoneBookFile);
            }
        }
    };

    private Action actSave = new AbstractAction("Сохранить") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            saveBook(model.getPhoneBookFile());
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

    protected void saveBook(File phoneBookFile) {
        try {
            int i = tblBook.getSelectedRow();
            Ezvcard.write(model.getPhoneBook()).version(VCardVersion.V2_1).go(phoneBookFile);
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

    protected void applyDialogBounds(Window dialog, Rectangle bounds) {
        if (bounds == null) {
            dialog.setSize(new Dimension(653, 800));
            dialog.setLocationRelativeTo(null);
        } else {
            dialog.setBounds(bounds);
        }
    }

    protected void setCardDialogBounds(Rectangle cardDialogBounds) {
        this.cardDialogBounds = cardDialogBounds;
    }

    protected Rectangle getCardDialogBounds() {
        return cardDialogBounds;
    }

    @Override
    protected void loadParameters() {
        super.loadParameters();
        lastImageFolder = FileUtils.getFile(properties.getProperty("lastImageFolder", "."));
        if (properties.containsKey("cardDialogBoundsW")) {
            cardDialogBounds = new Rectangle();
            cardDialogBounds.x = Integer.valueOf(properties.getProperty("cardDialogBoundsX", "100"));
            cardDialogBounds.y = Integer.valueOf(properties.getProperty("cardDialogBoundsY", "100"));
            cardDialogBounds.width = Integer.valueOf(properties.getProperty("cardDialogBoundsW", "653"));
            cardDialogBounds.height = Integer.valueOf(properties.getProperty("cardDialogBoundsH", "800"));
        }

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
        properties.setProperty("lastImageFolder", lastImageFolder.getAbsolutePath());
        properties.setProperty("cardDialogBoundsX", Integer.toString(cardDialogBounds.x));
        properties.setProperty("cardDialogBoundsY", Integer.toString(cardDialogBounds.y));
        properties.setProperty("cardDialogBoundsW", Integer.toString(cardDialogBounds.width));
        properties.setProperty("cardDialogBoundsH", Integer.toString(cardDialogBounds.height));

        super.saveParameters();
    }

    public PhoneBookFrame(String configFile) {
        super(configFile);

        DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(new KeyEventPostProcessor() {
            @Override
            public boolean postProcessKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_TYPED) {
                    if (PhoneBookFrame.this.isActive()) {
                        String text = edtSearch.getText();
                        if (StringUtils.isAlphanumericSpace(String.valueOf(e.getKeyChar()))) {
                            edtSearch.setText(text + e.getKeyChar());
                        }
                        if (e.getKeyChar() == '\b' && text.length() > 0) {
                            edtSearch.setText(text.substring(0, text.length() - 1));
                        }
                    }
                    if (e.getKeyChar() == 27) {
                        edtSearch.setText("");
                    }
                } else if (CardDialog.showing() && e.getKeyChar() == 27) {
                    CardDialog.close();
                }
                return false;
            }
        });

        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/ru/alex/phonebook/img/main.png")));
        setTitle("Телефонная книга");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 0));

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 0, 5, 0));
        contentPane.add(panel, BorderLayout.NORTH);
        panel.setLayout(new BorderLayout(5, 0));

        edtPhoneBookFile = new JTextField();
        edtPhoneBookFile.setFont(font);
        edtPhoneBookFile.setEditable(false);
        panel.add(edtPhoneBookFile);
        edtPhoneBookFile.setColumns(10);

        JLabel label = new JLabel("Файл");
        label.setFont(font);
        panel.add(label, BorderLayout.WEST);

        JButton btnNewButton = new JButton(actOpen);
        btnNewButton.setFont(font);
        panel.add(btnNewButton, BorderLayout.EAST);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        tblBook = new JTable(model);
        tblBook.setFont(font);
        scrollPane.setViewportView(tblBook);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new EmptyBorder(5, 0, 0, 0));
        contentPane.add(panel_2, BorderLayout.SOUTH);
        panel_2.setLayout(new BorderLayout(0, 0));

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new EmptyBorder(5, 0, 0, 0));
        panel_2.add(panel_1);
        panel_1.setLayout(new GridLayout(2, 6, 5, 5));

        JButton btnEditRecord = new JButton(actEditRecord);
        btnEditRecord.setText("Изменить");
        btnEditRecord.setFont(font);
        panel_1.add(btnEditRecord);
        JButton btnDeleteRecord = new JButton(actDeleteRecord);
        btnDeleteRecord.setFont(font);
        panel_1.add(btnDeleteRecord);
        JButton btnAddRecord = new JButton(actAddRecord);
        btnAddRecord.setFont(font);
        panel_1.add(btnAddRecord);
        JButton btnSave = new JButton(actSave);
        btnSave.setFont(font);
        panel_1.add(btnSave);
        JButton btnSaveAs = new JButton(actSaveAs);
        btnSaveAs.setFont(font);
        panel_1.add(btnSaveAs);
        JButton btnNewBook = new JButton(actNewBook);
        btnNewBook.setFont(font);
        panel_1.add(btnNewBook);

        JPanel panel_3 = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
        flowLayout_1.setAlignment(FlowLayout.LEFT);
        flowLayout_1.setVgap(0);
        panel_3.setBorder(null);
        panel_2.add(panel_3, BorderLayout.NORTH);

        JLabel lblSearch = new JLabel("Поиск");
        lblSearch.setFont(font);
        panel_3.add(lblSearch);

        edtSearch = new JTextField();
        edtSearch.setFont(font.deriveFont(Font.ITALIC | Font.BOLD, 13));
        edtSearch.setBackground(Color.WHITE);
        edtSearch.setEditable(false);
        edtSearch.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                model.setFilter(Optional.ofNullable(edtSearch.getText()), true);
            }
        });
        panel_3.add(edtSearch);
        edtSearch.setColumns(30);

        JButton btnNewButton_1 = new JButton("");
        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edtSearch.setText("");
            }
        });
        btnNewButton_1.setIcon(new ImageIcon(PhoneBookFrame.class.getResource("/ru/alex/phonebook/img/delete.png")));
        btnNewButton_1.setMargin(new Insets(4, 4, 2, 2));
        panel_3.add(btnNewButton_1);
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
                }
                table.setRowHeight(row, Math.max(table.getRowHeight(row), lbl.getIcon().getIconHeight()));
                return lbl;
            }
        });
        TableCellRenderer rendererFromHeader = tblBook.getTableHeader().getDefaultRenderer();
        tblBook.getTableHeader().setFont(font);
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
    }

}
