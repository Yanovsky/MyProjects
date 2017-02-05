package ru.crystals.xquery;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.basex.core.Context;
import org.basex.core.cmd.XQuery;

public class XQueryForm extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final String confFile = "settings.conf";
    private Properties properties = new Properties();
    private JPanel contentPane;
    private JTextField edtScriptFile;
    private JTextArea edtScriptContent;
    private JTextField edtHomeDir;
    private JTextArea edtExecuteResult;
    private Rectangle location;
    private JSplitPane splitPane;

    private Action actExecute = new AbstractAction("Execute") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                if (!new File(edtHomeDir.getText()).exists()) {
                    throw new Exception("Home folder not assign or assign incorrect");
                }
                if (!StringUtils.isEmpty(edtScriptContent.getText())) {
                    edtExecuteResult.setText("");
                    runScriptXQ(edtHomeDir.getText(), edtScriptContent.getText());
                    edtExecuteResult.setText("Successfully executed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                edtExecuteResult.setText(e.getLocalizedMessage());
                JOptionPane.showMessageDialog(XQueryForm.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    };

    private Action actOpenFile = new AbstractAction("...") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            JFileChooser chooser = new JFileChooser(new File("").getAbsolutePath());
            try {
                File f = new File(edtScriptFile.getText());
                if (f.exists())
                    chooser.setSelectedFile(f);
            } catch (Exception e) {
            }

            FileFilter filter = new FileNameExtensionFilter("XQuery files", "xq");
            chooser.setFileFilter(filter);
            chooser.setMultiSelectionEnabled(false);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);
            chooser.setFileSystemView(FileSystemView.getFileSystemView());
            chooser.setDialogTitle("Choose the script file");
            if (chooser.showDialog(XQueryForm.this, "Select") == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                edtScriptFile.setText(file.getAbsolutePath());
                try {
                    edtScriptContent.setText(FileUtils.readFileToString(file, "utf8"));
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(XQueryForm.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    };

    private Action actSelectHomeDir = new AbstractAction("...") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            JFileChooser chooser = new JFileChooser(new File("").getAbsolutePath());
            try {
                File f = new File(edtHomeDir.getText());
                if (f.exists())
                    chooser.setSelectedFile(f);
            } catch (Exception e) {
            }
            chooser.setMultiSelectionEnabled(false);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);
            chooser.setDialogTitle("Choose the home folder");
            if (chooser.showDialog(XQueryForm.this, "Select") == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                edtHomeDir.setText(file.getAbsolutePath());
            }
        }
    };

    private Action actCopy = new AbstractAction("Copy") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            edtScriptContent.copy();
        }
    };
    private Action actCut = new AbstractAction("Cut") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            edtScriptContent.cut();
        }
    };
    private Action actPaste = new AbstractAction("Paste") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            edtScriptContent.paste();
        }
    };
    private Action actSelectAll = new AbstractAction("SelectAll") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            edtScriptContent.selectAll();
        }
    };

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    XQueryForm frame = new XQueryForm();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void runScriptXQ(String homeDir, String scriptContent) throws Exception {
        String currentDir = System.getProperty("user.dir");
        try {
            System.setProperty("user.dir", new File(homeDir).getAbsolutePath());
            Context context = new Context();
            new org.basex.core.cmd.Set("EXPORTER", "method=xml, version=1.0, omit-xml-declaration=no, indents=8").execute(context);
            new org.basex.core.cmd.Set("INTPARSE", "true").execute(context);
            new org.basex.core.cmd.Set("WRITEBACK", "true").execute(context);
            new XQuery(scriptContent).execute(context);
        } catch (Exception e) {
            throw e;
        } finally {
            System.setProperty("user.dir", currentDir);
        }
    }

    /**
     * Create the frame.
     */
    public XQueryForm() {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                if (event.getID() == KeyEvent.KEY_RELEASED) {
                    if (event.getKeyCode() == KeyEvent.VK_F5) {
                        actExecute.actionPerformed(null);
                    }
                }
                return false;
            }
        });

        try {
            UIManager.put("FileChooser.readOnly", Boolean.TRUE);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                saveParameters();
            }
        });

        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/ru/crystals/img/main.png")));
        setTitle("XQuery executor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 640, 480);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[] {0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[] {0, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[] {0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[] {0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        JLabel lblNewLabel = new JLabel("Script file");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        contentPane.add(lblNewLabel, gbc_lblNewLabel);

        edtScriptFile = new JTextField();
        edtScriptFile.setEditable(false);
        GridBagConstraints gbc_edtScriptFile = new GridBagConstraints();
        gbc_edtScriptFile.insets = new Insets(0, 0, 5, 5);
        gbc_edtScriptFile.fill = GridBagConstraints.HORIZONTAL;
        gbc_edtScriptFile.gridx = 1;
        gbc_edtScriptFile.gridy = 0;
        contentPane.add(edtScriptFile, gbc_edtScriptFile);
        edtScriptFile.setColumns(10);

        JButton button = new JButton(actOpenFile);
        button.setMargin(new Insets(0, 2, 0, 2));
        GridBagConstraints gbc_button = new GridBagConstraints();
        gbc_button.insets = new Insets(0, 0, 5, 0);
        gbc_button.fill = GridBagConstraints.BOTH;
        gbc_button.gridx = 2;
        gbc_button.gridy = 0;
        contentPane.add(button, gbc_button);

        JLabel lblNewLabel_1 = new JLabel("Home folder");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 1;
        contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);

        edtHomeDir = new JTextField();
        edtHomeDir.setEditable(false);
        GridBagConstraints gbc_edtHomeDir = new GridBagConstraints();
        gbc_edtHomeDir.insets = new Insets(0, 0, 5, 5);
        gbc_edtHomeDir.fill = GridBagConstraints.HORIZONTAL;
        gbc_edtHomeDir.gridx = 1;
        gbc_edtHomeDir.gridy = 1;
        contentPane.add(edtHomeDir, gbc_edtHomeDir);
        edtHomeDir.setColumns(10);

        JButton button_1 = new JButton(actSelectHomeDir);
        button_1.setMargin(new Insets(0, 2, 0, 2));
        GridBagConstraints gbc_button_1 = new GridBagConstraints();
        gbc_button_1.insets = new Insets(0, 0, 5, 0);
        gbc_button_1.gridx = 2;
        gbc_button_1.gridy = 1;
        contentPane.add(button_1, gbc_button_1);

        splitPane = new JSplitPane();
        splitPane.setDividerSize(10);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.8);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        GridBagConstraints gbc_splitPane = new GridBagConstraints();
        gbc_splitPane.insets = new Insets(0, 0, 5, 0);
        gbc_splitPane.gridwidth = 4;
        gbc_splitPane.fill = GridBagConstraints.BOTH;
        gbc_splitPane.gridx = 0;
        gbc_splitPane.gridy = 2;
        contentPane.add(splitPane, gbc_splitPane);

        JPanel panel = new JPanel();
        splitPane.setLeftComponent(panel);
        panel.setBorder(new TitledBorder(null, "XQuery script", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane, BorderLayout.CENTER);

        final JPopupMenu menu = new JPopupMenu();
        {
            JMenuItem mnu = new JMenuItem(actExecute);
            mnu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
            menu.add(mnu);
        }
        menu.addSeparator();
        {
            JMenuItem mnu = new JMenuItem(actCopy);
            mnu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
            menu.add(mnu);
        }
        {
            JMenuItem mnu = new JMenuItem(actCut);
            mnu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
            menu.add(mnu);
        }
        {
            JMenuItem mnu = new JMenuItem(actPaste);
            mnu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
            menu.add(mnu);
        }
        menu.addSeparator();
        {
            JMenuItem mnu = new JMenuItem(actSelectAll);
            mnu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
            menu.add(mnu);
        }

        edtScriptContent = new JTextArea();
        edtScriptContent.setFont(new Font("Monospaced", Font.PLAIN, 12));
        edtScriptContent.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger())
                    doPop(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger())
                    doPop(e);
            }

            private void doPop(MouseEvent e) {
                JTextArea textField = (JTextArea) e.getSource();
                if (textField.getSelectedText() == null) {
                    actCopy.setEnabled(false);
                    actCut.setEnabled(false);
                } else {
                    actCopy.setEnabled(true);
                    actCut.setEnabled(true);
                }

                actSelectAll.setEnabled(!StringUtils.isEmpty(textField.getText()));

                actExecute.setEnabled(!StringUtils.isEmpty(edtHomeDir.getText()) && !StringUtils.isEmpty(edtScriptContent.getText()));

                menu.show(textField, e.getX(), e.getY());
            }
        });

        TextLineNumber tln = new TextLineNumber(edtScriptContent);
        scrollPane.setRowHeaderView(tln);

        scrollPane.setViewportView(edtScriptContent);

        JPanel panel_1 = new JPanel();
        splitPane.setRightComponent(panel_1);
        panel_1.setBorder(new TitledBorder(null, "Executing result", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane_1 = new JScrollPane();
        panel_1.add(scrollPane_1, BorderLayout.CENTER);

        edtExecuteResult = new JTextArea();
        edtExecuteResult.setEditable(false);
        edtExecuteResult.setFont(new Font("Monospaced", Font.PLAIN, 12));
        scrollPane_1.setViewportView(edtExecuteResult);

        JButton btnNewButton = new JButton(actExecute);
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.gridwidth = 3;
        gbc_btnNewButton.gridx = 0;
        gbc_btnNewButton.gridy = 3;
        contentPane.add(btnNewButton, gbc_btnNewButton);

        loadParameters();
        if (location == null) {
            setLocationRelativeTo(null);
        } else {
            setBounds(location);
        }
    }

    private void loadParameters() {
        File file = new File(confFile);
        if (file.exists()) {
            try {
                InputStream is = new FileInputStream(file);
                properties.load(is);
                is.close();

                edtHomeDir.setText(properties.getProperty("edtHomeDir", ""));
                edtScriptFile.setText(properties.getProperty("edtScriptFile", ""));
                if (!StringUtils.isEmpty(edtScriptFile.getText())) {
                    File scriptFile = new File(edtScriptFile.getText());
                    if (scriptFile.exists()) {
                        edtScriptContent.setText(FileUtils.readFileToString(scriptFile, "utf8"));
                    } else {
                        edtScriptFile.setText("");
                    }
                }
                if (properties.containsKey("locationX")) {
                    location = new Rectangle();
                    location.x = Integer.valueOf(properties.getProperty("locationX", "100"));
                    location.y = Integer.valueOf(properties.getProperty("locationY", "100"));
                    location.width = Integer.valueOf(properties.getProperty("locationW", "420"));
                    location.height = Integer.valueOf(properties.getProperty("locationH", "424"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveParameters() {
        try {
            properties.setProperty("edtHomeDir", edtHomeDir.getText());
            properties.setProperty("edtScriptFile", edtScriptFile.getText());

            location = getBounds();
            properties.setProperty("locationX", Integer.toString(location.x));
            properties.setProperty("locationY", Integer.toString(location.y));
            properties.setProperty("locationW", Integer.toString(location.width));
            properties.setProperty("locationH", Integer.toString(location.height));

            OutputStream out = new FileOutputStream(confFile);
            properties.store(out, "");

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
