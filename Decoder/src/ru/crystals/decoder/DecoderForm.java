package ru.crystals.decoder;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang.StringUtils;

public class DecoderForm extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Action actCopy = new AbstractAction("Копировать") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            JTextField edit = getTextField(event);
            Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection stringSelection = new StringSelection(edit.getSelectedText());
            clpbrd.setContents(stringSelection, null);
        }
    };
    private Action actCut = new AbstractAction("Вырезать") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            JTextField edit = getTextField(event);
            Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection stringSelection = new StringSelection(edit.getSelectedText());
            String text = edit.getText();
            text = text.substring(0, edit.getSelectionStart()) + text.substring(edit.getSelectionEnd());
            edit.setText(text);
            clpbrd.setContents(stringSelection, null);
        }
    };
    private Action actPaste = new AbstractAction("Вставить") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            JTextField edit = getTextField(event);
            edit.setText(getClipboardContents());
        }
    };

    public String getClipboardContents() {
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //odd: the Object param of getContents is not currently used
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
            try {
                result = (String) contents.getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DecoderForm frame = new DecoderForm();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected JTextField getTextField(ActionEvent event) {
        JMenuItem item = (JMenuItem) event.getSource();
        JPopupMenu menu = (JPopupMenu) item.getParent();
        return (JTextField) menu.getInvoker();
    }

    /**
     * Create the frame.
     */
    public DecoderForm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Java properties helper");
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPane);

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem(actCopy));
        popupMenu.add(new JMenuItem(actCut));
        popupMenu.add(new JMenuItem(actPaste));

        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[] {0, 0, 0};
        gbl_contentPane.rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[] {0.0, 1.0, 1.0};
        gbl_contentPane.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        JLabel lblNewLabel = new JLabel("Строка");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        contentPane.add(lblNewLabel, gbc_lblNewLabel);

        final JTextField edtString = new JTextField();
        GridBagConstraints gbc_edtString = new GridBagConstraints();
        gbc_edtString.gridwidth = 2;
        gbc_edtString.insets = new Insets(0, 0, 5, 0);
        gbc_edtString.fill = GridBagConstraints.HORIZONTAL;
        gbc_edtString.gridx = 1;
        gbc_edtString.gridy = 0;
        contentPane.add(edtString, gbc_edtString);
        edtString.setColumns(10);
        edtString.add(popupMenu);
        edtString.setComponentPopupMenu(popupMenu);

        final JTextField edtUnicode = new JTextField();
        GridBagConstraints gbc_edtUnicode = new GridBagConstraints();
        gbc_edtUnicode.gridwidth = 2;
        gbc_edtUnicode.insets = new Insets(0, 0, 5, 0);
        gbc_edtUnicode.fill = GridBagConstraints.HORIZONTAL;
        gbc_edtUnicode.gridx = 1;
        gbc_edtUnicode.gridy = 2;
        contentPane.add(edtUnicode, gbc_edtUnicode);
        edtUnicode.setColumns(10);
        edtUnicode.add(popupMenu);
        edtUnicode.setComponentPopupMenu(popupMenu);

        JButton btnUnicode2Normal = new JButton("");
        btnUnicode2Normal.setMargin(new Insets(2, 2, 2, 2));
        btnUnicode2Normal.setIcon(new ImageIcon("img/arrow_up.png"));
        btnUnicode2Normal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                edtString.setText(convertToString(edtUnicode.getText()));
                StringSelection selection = new StringSelection(edtString.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
            }
        });

        JButton btnNormal2Unicode = new JButton("");
        btnNormal2Unicode.setMargin(new Insets(2, 2, 2, 2));
        btnNormal2Unicode.setIcon(new ImageIcon("img/arrow_dn.png"));
        btnNormal2Unicode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                edtUnicode.setText(convertToProperties(edtString.getText()));
                StringSelection selection = new StringSelection(edtUnicode.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
            }
        });
        GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
        gbc_btnNewButton_1.anchor = GridBagConstraints.EAST;
        gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_1.gridx = 1;
        gbc_btnNewButton_1.gridy = 1;
        contentPane.add(btnNormal2Unicode, gbc_btnNewButton_1);

        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
        gbc_btnNewButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnNewButton.gridx = 2;
        gbc_btnNewButton.gridy = 1;
        contentPane.add(btnUnicode2Normal, gbc_btnNewButton);

        JLabel lblNewLabel_1 = new JLabel("Unicode");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 2;
        contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);

        try {
            String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(lookAndFeel);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
        }
        setLocationRelativeTo(null);
    }

    public static String convertToProperties(String value) {
        StringBuilder sb = new StringBuilder();
        for (Character ch : value.toCharArray()) {
            if (ch < 128) {
                sb.append(ch);
            } else {
                sb.append("\\u");
                sb.append(StringUtils.leftPad(Integer.toHexString(ch), 4, "0").toUpperCase());
            }
        }
        return sb.toString();
    }

    public static String convertToString(String value) {
        StringBuilder sb = new StringBuilder();
        for (String str : value.split("\\\\u")) {
            if (str.length() > 0) {
                String suffix = str.length() > 4 ? str.substring(4, str.length()) : "";
                String s = str.substring(0, 4);
                try {
                    char ch = (char) Integer.parseInt(String.valueOf(s), 16);
                    sb.append(ch);
                } catch (NumberFormatException e) {
                    sb.append(s);
                }
                sb.append(suffix);
            }
        }
        return sb.toString();
    }
}
