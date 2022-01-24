import ch.ethz.ssh2.SCPClient;
import java.io.InputStream;
import ch.ethz.ssh2.Session;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import ch.ethz.ssh2.Connection;
import javax.swing.JTextArea;

// 
// Decompiled by Procyon v0.5.36
// 

public class Ssh
{
    private String m_ip;
    private static final String password = "root";
    private JTextArea logArea;
    
    public Ssh(final String m_ip, final JTextArea logArea) {
        this.m_ip = m_ip;
        this.logArea = logArea;
    }
    
    public int executeSshCommand(final String command) {
        System.out.println("Executing ssh command: " + command);
        this.logArea.append("Executing ssh command: " + command + "\n");
        Connection conn = null;
        Session sess = null;
        try {
            conn = new Connection(this.m_ip);
            conn.connect();
            final boolean isAuth = conn.authenticateWithPassword("root", "root");
            if (!isAuth) {
                throw new IOException("Authentication failed.");
            }
            sess = conn.openSession();
            sess.execCommand(command);
            final InputStream inp = sess.getStdout();
            final InputStreamReader reader = new InputStreamReader(inp);
            final BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                this.logArea.append(line + "\n");
            }
            sess.close();
            conn.close();
        }
        catch (IOException ex) {
            System.out.println(ex.toString());
            if (conn != null) {
                conn.close();
            }
            if (sess != null) {
                sess.close();
            }
            return -1;
        }
        return 0;
    }
    
    public int executeScpPut(final String path, final String filename) {
        Connection conn = null;
        SCPClient scpc = null;
        try {
            conn = new Connection(this.m_ip);
            conn.connect();
            final boolean isAuth = conn.authenticateWithPassword("root", "root");
            if (!isAuth) {
                throw new IOException("Authentication failed.");
            }
            scpc = conn.createSCPClient();
            System.out.println(path + " " + filename);
            this.logArea.append(path + " " + filename + "\n");
            scpc.put(filename, path);
            conn.close();
        }
        catch (IOException ex) {
            System.out.println(ex.toString());
            if (conn != null) {
                conn.close();
                return -1;
            }
        }
        return 0;
    }
    
    public int executeScpPut(final String path, final String[] filenames) {
        Connection conn = null;
        SCPClient scpc = null;
        try {
            conn = new Connection(this.m_ip);
            conn.connect();
            final boolean isAuth = conn.authenticateWithPassword("root", "root");
            if (!isAuth) {
                throw new IOException("Authentication failed.");
            }
            scpc = conn.createSCPClient();
            scpc.put(filenames, path);
            conn.close();
        }
        catch (IOException ex) {
            System.out.println(ex.toString());
            if (conn != null) {
                conn.close();
                return -1;
            }
        }
        return 0;
    }
    
    public int executeScpGet(final String localPath, final String remotePath) {
        Connection conn = null;
        SCPClient scpc = null;
        try {
            conn = new Connection(this.m_ip);
            conn.connect();
            final boolean isAuth = conn.authenticateWithPassword("root", "root");
            if (!isAuth) {
                throw new IOException("Authentication failed.");
            }
            scpc = conn.createSCPClient();
            scpc.get(remotePath, localPath);
            conn.close();
        }
        catch (IOException ex) {
            System.out.println(ex.toString());
            if (conn != null) {
                conn.close();
                return -1;
            }
        }
        return 0;
    }
}
