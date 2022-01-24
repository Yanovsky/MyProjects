package ru.crystals.viki.touch.scenarios.settings.sale_rules.agents;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Mercury {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        socket.setSoTimeout(30_000);
        socket.connect(new InetSocketAddress("localhost", 50009), 30_000);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        String outString = "{\"command\": \"GetDriverInfo1\"}";
        byte[] outData = outString.getBytes(StandardCharsets.UTF_8);
        byte[] outBytes = ByteBuffer.allocate(4 + outData.length).putInt(outData.length).put(outData).array();
        os.write(outBytes);
        long start = System.currentTimeMillis();
        byte[] inBytes = new byte[1024];
        while (System.currentTimeMillis() - start < 30_000) {
            if (is.available() > 0) {
                int size = is.read(inBytes);
                ByteBuffer buffer = ByteBuffer.wrap(inBytes, 0, size);
                byte[] dest = new byte[buffer.getInt()];
                buffer.get(dest);
                System.out.println(new String(dest, StandardCharsets.UTF_8));
                break;
            }
        }
    }
}
