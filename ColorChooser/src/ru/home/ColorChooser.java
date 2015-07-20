package ru.home;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

public class ColorChooser extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField tfR;
    private JTextField tfG;
    private JTextField tfB;
    private JLabel lblHex;
    private JTextField tfHex;
    private Color color;
    private Robot robot;
    private Point point;
    private JButton panel;
    
    private AtomicBoolean findMode;
    private JToggleButton tglbtnNewToggleButton;
    private JButton pnlDD;
    private JButton pnlD;
    private JButton pnlB;
    private JButton pnlBB;

    private Properties properties;
    private Rectangle location;

    private static final String confFile = "colorChooser.conf";

    public ColorChooser() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(ColorChooser.class.getResource("/ru/img/main.png")));
        setAlwaysOnTop(false);
        setResizable(false);
        setFocusable(true);
        properties = new Properties();


        try {
            robot = new Robot();
        } catch (AWTException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 311, 169);
        setTitle("Color Chooser 2.0    © SCO");
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        findMode = new AtomicBoolean(false);

        Action action = new AbstractAction("ESCAPE") {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                findMode.set(false);
                tglbtnNewToggleButton.setSelected(false);
                tglbtnNewToggleButton.setText("Start");
                
                doSaveToClipBoard();
            }
        };

        KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");

        contentPane.getActionMap().put("ESCAPE", action);
        contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(stroke, "ESCAPE");

        panel = new JButton();
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                doSaveToClipBoard(panel.getBackground());
            }
        });

        MouseObserver mo = new MouseObserver(this);
        mo.addMouseMotionListener(new MouseMotionListener() {

            public void mouseMoved(MouseEvent e) {
                if (findMode.get()) {
                    point = e.getPoint();
                    color = robot.getPixelColor(point.x, point.y);
                    updateColorPanels(e);
                    updateRGBHex(e);
                }
            }

            public void mouseDragged(MouseEvent e) {
                System.out.println("mouse dragged: " + e.getPoint());
            }
        });

        mo.start();

        panel.setBounds(93, 44, 103, 85);
        contentPane.add(panel);

        tfR = new JTextField();
        tfR.setBackground(Color.WHITE);
        tfR.setEditable(false);
        tfR.setBounds(51, 11, 38, 20);
        contentPane.add(tfR);
        tfR.setColumns(10);

        JLabel lblNewLabel = new JLabel("R:");
        lblNewLabel.setBounds(35, 14, 16, 14);
        contentPane.add(lblNewLabel);

        tfG = new JTextField();
        tfG.setBackground(Color.WHITE);
        tfG.setEditable(false);
        tfG.setColumns(10);
        tfG.setBounds(51, 33, 38, 20);
        contentPane.add(tfG);

        JLabel lblG = new JLabel("G:");
        lblG.setBounds(35, 36, 16, 14);
        contentPane.add(lblG);

        tfB = new JTextField();
        tfB.setBackground(Color.WHITE);
        tfB.setEditable(false);
        tfB.setColumns(10);
        tfB.setBounds(51, 56, 38, 20);
        contentPane.add(tfB);

        JLabel lblB = new JLabel("B:");
        lblB.setBounds(35, 59, 16, 14);
        contentPane.add(lblB);

        lblHex = new JLabel("hex:");
        lblHex.setBounds(95, 14, 35, 14);
        contentPane.add(lblHex);

        tfHex = new JTextField();
        tfHex.setBackground(Color.WHITE);
        tfHex.setEditable(false);
        tfHex.setBounds(125, 11, 71, 20);
        contentPane.add(tfHex);
        tfHex.setColumns(10);

        
        tglbtnNewToggleButton = new JToggleButton("Start");



        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                if (selected) {
                    findMode.set(true);
                    tglbtnNewToggleButton.setText("Stop");
                } else {
                    findMode.set(false);
                    tglbtnNewToggleButton.setText("Start");
                }
            }
        };
        // Attach Listeners
        tglbtnNewToggleButton.addActionListener(actionListener);
        tglbtnNewToggleButton.setBounds(203, 11, 80, 63);
        contentPane.add(tglbtnNewToggleButton);

        pnlDD = new JButton();
        pnlDD.setBounds(10, 80, 37, 49);
        contentPane.add(pnlDD);


        pnlD = new JButton();
        pnlD.setBounds(52, 80, 37, 49);
        contentPane.add(pnlD);

        pnlB = new JButton();
        pnlB.setBounds(204, 80, 36, 49);
        contentPane.add(pnlB);

        pnlBB = new JButton();
        pnlBB.setBounds(247, 80, 36, 49);
        contentPane.add(pnlBB);

        pnlDD.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                doSaveToClipBoard(pnlDD.getBackground());
            }
        });

        pnlD.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                doSaveToClipBoard(pnlD.getBackground());
            }
        });

        pnlB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                doSaveToClipBoard(pnlB.getBackground());
            }
        });

        pnlBB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                doSaveToClipBoard(pnlBB.getBackground());
            }
        });

        loadParameters();
        if (location == null) {
            setLocationRelativeTo(null);
        } else {
            setLocation(location.x, location.y);
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                saveParameters();
            }
        });



    }
    
    protected void doSaveToClipBoard() {
        String newColor = "new Color(%d,%d,%d)";
        StringSelection stringSelection = new StringSelection(String.format(newColor, color.getRed(), color.getGreen(), color.getBlue()));
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);

    }

    protected void doSaveToClipBoard(Color color) {
        String newColor = "new Color(%d,%d,%d)";
        StringSelection stringSelection = new StringSelection(String.format(newColor, color.getRed(), color.getGreen(), color.getBlue()));
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);

    }

    protected void updateRGBHex(MouseEvent e) {
        tfR.setText(String.valueOf(color.getRed()));
        tfG.setText(String.valueOf(color.getGreen()));
        tfB.setText(String.valueOf(color.getBlue()));
        tfHex.setText(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));

    }

    protected void updateColorPanels(MouseEvent e) {
        panel.setBackground(color);
        pnlB.setBackground(color.brighter());
        pnlBB.setBackground(color.brighter().brighter());
        pnlD.setBackground(color.darker());
        pnlDD.setBackground(color.darker().darker());

    }
    
    private void loadParameters() {
        File file = new File(confFile);
        if (file.exists()) {
            try {
                InputStream is = new FileInputStream(file);
                properties.load(is);
                is.close();

                if (properties.containsKey("locationX")) {
                    location = new Rectangle();
                    location.x = Integer.valueOf(properties.getProperty("locationX", "100"));
                    location.y = Integer.valueOf(properties.getProperty("locationY", "100"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveParameters() {
        try {
            location = getBounds();
            properties.setProperty("locationX", Integer.toString(location.x));
            properties.setProperty("locationY", Integer.toString(location.y));

            OutputStream out = new FileOutputStream(confFile);
            properties.store(out, "ColorChooserEmulatorState");

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ColorChooser frame = new ColorChooser();
                    frame.setVisible( true );

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
