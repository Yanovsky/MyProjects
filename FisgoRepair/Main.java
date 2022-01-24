import javax.swing.JComponent;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.Container;
import java.awt.Component;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JDialog;

// 
// Decompiled by Procyon v0.5.36
// 

public class Main extends JDialog
{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField ipField;
    private JLabel logLabel;
    private JTextArea logArea;
    private ExecutorService executorService;
    
    public Main() {
        this.executorService = Executors.newSingleThreadExecutor();
        this.$$$setupUI$$$();
        this.setPreferredSize(new Dimension(400, 300));
        this.buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Main.this.executorService.submit(() -> Main.this.onOK());
            }
        });
        final JScrollPane scroll = new JScrollPane(this.contentPane, 22, 32);
        this.setContentPane(scroll);
        this.setModal(true);
    }
    
    private void onOK() {
        this.buttonOK.setEnabled(false);
        final String ip = this.ipField.getText();
        final Ssh ssh = new Ssh(ip, this.logArea);
        int success = -1;
        this.logArea.append("==========DREAMKAS-F SEARCHING========== \n");
        for (int j = 0; j < 20; ++j) {
            success = ssh.executeSshCommand("rm -r /updateBackup");
            if (success == 0) {
                this.logArea.append("==========FIND DREAMKAS-F " + ip + "========== \n");
                System.out.println("==========RESTORE SYSTEM START SUCCESS==========");
                this.logArea.append("==========RESTORE SYSTEM START SUCCESS========== \n");
                ssh.executeSshCommand("killall fiscat");
                ssh.executeSshCommand("rm /FisGo/fiscat");
                ssh.executeSshCommand("sync");
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (success == 0) {
            this.logArea.append("==========COPY PATCH==========\n");
            if (ssh.executeScpPut("/", "dirPatch.tar.gz") != 0) {
                System.out.println("==========PATCH SAVE FAILED!!!==========");
                this.logArea.append("==========PATCH SAVE FAILED!!!==========\n");
                JOptionPane.showMessageDialog(null, "FAILED!!!");
                return;
            }
            System.out.println("==========PATCH SAVE SUCCESS==========");
            this.logArea.append("==========PATCH SAVE SUCCESS==========\n");
            this.logArea.append("==========UNPACKING PATCH==========\n");
            if (ssh.executeSshCommand("gunzip /dirPatch.tar.gz && tar xvf /dirPatch.tar -C /") != 0) {
                System.out.println("==========UNPACKING PATCH FAILED!!!==========");
                this.logArea.append("==========UNPACKING PATCH FAILED!!!==========\n");
                JOptionPane.showMessageDialog(null, "FAILED!!!");
                return;
            }
            System.out.println("==========UNPACKING PATCH SUCCESS==========");
            this.logArea.append("==========UNPACKING PATCH SUCCESS==========\n");
            ssh.executeSshCommand("sync");
            ssh.executeSshCommand("sync");
            ssh.executeSshCommand("rm /dirPatch.tar");
            System.out.println("==========REBOOT SYSTEM==========");
            this.logArea.append("==========REBOOT SYSTEM==========\n");
            ssh.executeSshCommand("/sbin/reboot");
            System.out.println("==========RESTORE SYSTEM SUCCESS==========");
            this.logArea.append("==========RESTORE SYSTEM SUCCESS==========\n");
            JOptionPane.showMessageDialog(null, "Success");
        }
        else {
            System.out.println("==========RESTORE FAILED!!!==========");
            this.logArea.append("==========RESTORE FAILED!!!==========\n");
            JOptionPane.showMessageDialog(null, "FAILED!!!");
        }
        this.dispose();
    }
    
    public void printLogString(final String logString) {
    }
    
    private void onCancel() {
        this.dispose();
    }
    
    public static void main(final String[] args) {
        final Main dialog = new Main();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
    
    public ArrayList<String> getIPAddressList() {
        final ArrayList<String> addresses = new ArrayList<String>();
        try {
            final Runtime rt = Runtime.getRuntime();
            final Process pr = rt.exec("nmap -sn -oG ip.txt 13.150.23.1-255");
            final BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = null;
            while ((line = input.readLine()) != null) {
                System.out.println(line);
                if (line.contains("Nmap scan report for")) {
                    final String[] elements = line.split(" ");
                    final int end = elements.length - 1;
                    final String ip_address = elements[end];
                    final String line2 = input.readLine();
                    if (!line2.contains("Host is up")) {
                        continue;
                    }
                    addresses.add(ip_address);
                }
            }
            final int exitVal = pr.waitFor();
            System.out.println("Exited with error code " + exitVal);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return addresses;
    }
    
    private void createUIComponents() {
    }
    
    private void $$$setupUI$$$() {
        (this.contentPane = new JPanel()).setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        this.contentPane.add(panel1, "North");
        final JLabel label1 = new JLabel();
        label1.setText("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 ip \u043a\u0430\u0441\u0441\u044b");
        panel1.add(label1, "North");
        panel1.add(this.ipField = new JTextField(), "Center");
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        this.contentPane.add(panel2, "Center");
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel2.add(panel3, "North");
        (this.buttonOK = new JButton()).setText("OK");
        panel3.add(this.buttonOK, "Center");
        panel2.add(this.logArea = new JTextArea(), "Center");
    }
    
    public JComponent $$$getRootComponent$$$() {
        return this.contentPane;
    }
}
