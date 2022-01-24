import javax.swing.JTextArea;
import java.net.InetAddress;
import java.util.HashMap;
import java.io.IOException;
import java.util.Map;

// 
// Decompiled by Procyon v0.5.36
// 

public class ListIpThread extends Thread
{
    private int min;
    private int max;
    private String subnet;
    private Map<String, Boolean> mapIp;
    private Ssh ssh;
    
    public Map<String, Boolean> getMapIp() {
        return this.mapIp;
    }
    
    ListIpThread(final String subnet, final int min, final int max) {
        this.subnet = subnet;
        this.min = min;
        this.max = max;
    }
    
    @Override
    public void run() {
        try {
            this.checkHosts(this.subnet, this.min, this.max);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        super.run();
    }
    
    private void checkHosts(final String subnet, final int min, final int max) throws IOException {
        final int timeout = 1000;
        this.mapIp = new HashMap<String, Boolean>();
        for (int i = min; i < max; ++i) {
            final String host = subnet + "." + i;
            System.out.println(host);
            this.mapIp.put(host, false);
            if (InetAddress.getByName(host).isReachable(timeout)) {
                this.ssh = new Ssh(host, new JTextArea());
                if (this.ssh.executeSshCommand("ls -la >> /dev/null") == 0) {
                    this.ssh.executeSshCommand("rm -r /updateBackup >> /dev/null");
                    System.out.println("DREAMKAS DEVICE" + host);
                }
            }
        }
    }
}
