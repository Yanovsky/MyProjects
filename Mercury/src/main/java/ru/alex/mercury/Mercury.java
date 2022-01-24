package ru.alex.mercury;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.time.format.DateTimeFormatter;

import ru.alex.mercury.commands.unsessions.GetDriverInfoRequest;
import ru.alex.mercury.commands.unsessions.GetDriverInfoResponse;
import ru.dreamkas.utils.time.Now;

public class Mercury {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM HH:mm:ss.SSS");

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        socket.setSoTimeout(30_000);
        socket.connect(new InetSocketAddress("localhost", 50009), 30_000);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        GetDriverInfoRequest request = new GetDriverInfoRequest();
        os.write(request.getBytes());
        debug(Mercury.class, "<<<<< " + request);
        long start = System.currentTimeMillis();
        byte[] inBytes = new byte[1024];
        while (System.currentTimeMillis() - start < 30_000) {
            if (is.available() > 0) {
                int size = is.read(inBytes);
                ByteBuffer buffer = ByteBuffer.wrap(inBytes, 0, size);
                byte[] dest = new byte[buffer.getInt()];
                buffer.get(dest);
                GetDriverInfoResponse response = GetDriverInfoResponse.fromBytes(dest, GetDriverInfoResponse.class);
                debug(Mercury.class, "<<<<< " + response);
                break;
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static void debug(Class<?> clazz, String text) {
        System.out.println(String.format("%s DEBUG [%s] %s", Now.localDateTime().format(DATE_TIME_FORMATTER), clazz.getSimpleName(), text));
    }
}
