package app.network;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server extends Thread {
    protected static final int SERVER_PORT = 30001;
    private ServerSocket serverSocket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    public Server() {

    }

    public void run() {
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Starting server...");

            while (true) {
                // TODO: consider multithreading approach
                socket = serverSocket.accept();
                var clientIp = ((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress().toString();
                System.out.println(String.format("Client connect with IP %s", clientIp));

                var bufferIn = new BufferedInputStream(socket.getInputStream());
                try(var dataIn = new DataInputStream(bufferIn)) {
                    var filename = dataIn.readUTF();
                    var relativePath = dataIn.readUTF(); // TODO
                    var documentPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
                    var path = Paths.get(documentPath, filename);

                    System.out.println(String.format("Receiving file %s from IP %s", path.toString(), clientIp));
                    Files.copy(dataIn, path);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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
