package ru.alex.barcode.tools;

import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JFrame;

public class RetentiveFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private String configFile = "Frame.conf";
    protected Properties properties;
    protected Rectangle location;

    public RetentiveFrame() {
        this(null);
    }

    public RetentiveFrame(String configFile) {
        super();
        this.configFile = configFile != null ? configFile : this.getClass().getSimpleName() + ".conf";
        properties = new Properties();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                saveParameters();
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    protected void loadParameters() {
        File file = new File(configFile);
        if (file.exists()) {
            try {
                InputStream is = new FileInputStream(file);
                properties.load(is);
                is.close();

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
        if (location == null) {
            setSize(800, 600);
            setLocationRelativeTo(null);
        } else {
            setBounds(location);
        }
    }

    protected void saveParameters() {
        try {
            location = getBounds();
            properties.setProperty("locationX", Integer.toString(location.x));
            properties.setProperty("locationY", Integer.toString(location.y));
            properties.setProperty("locationW", Integer.toString(location.width));
            properties.setProperty("locationH", Integer.toString(location.height));

            OutputStream out = new FileOutputStream(configFile);
            properties.store(out, "Fiscal printer state");

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
