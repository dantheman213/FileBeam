package app.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;

public class Server extends Thread {
    protected static final int SERVER_PORT = 30001;
    private ServerSocket serverSocket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    public Server() {

    }

    public void run() {
        System.out.println("Starting server...");

        try {
            serverSocket = new ServerSocket(SERVER_PORT);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        while (true) {
            try {
                var socket = serverSocket.accept();
                var worker = new ServerWorker(socket);
                worker.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
