package app.network;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ServerWorker extends Thread {
    protected String clientIpAddress;

    private Socket socket;
    private BufferedInputStream bufferIn;
    private DataInputStream dataIn;

    public ServerWorker(Socket s) throws IOException {
        clientIpAddress = ((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress().toString().replaceAll("/", "");
        System.out.println(String.format("Client connect with IP %s", clientIpAddress));

        socket = s;
        bufferIn = new BufferedInputStream(socket.getInputStream());
        dataIn = new DataInputStream(bufferIn);
    }

    public void run() {
        while (true) {
            try {
                if(dataIn.available() > 0) {
                    // TODO: add intro handshake key
                    var filename = dataIn.readUTF();
                    var relativePath = dataIn.readUTF(); // TODO
                    var documentPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
                    var path = Paths.get(documentPath, filename);

                    System.out.println(String.format("Receiving file %s from IP %s", path.toString(), clientIpAddress));
                    Files.copy(dataIn, path);
                } else {
                    Thread.sleep(100);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (Exception ex2) { }
            }
        }
    }
}
