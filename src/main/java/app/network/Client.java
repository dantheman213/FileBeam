package app.network;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static app.network.Server.SERVER_PORT;

public class Client {
    private Socket socket;
    private String address;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    public Client(String ipAddress) throws Exception {
        address = ipAddress.replaceAll("/", "");
        System.out.println(String.format("Connecting to server IP %s", address));
        socket = new Socket(address, SERVER_PORT);
    }

    public void sendFile(String path) throws Exception {
        var out = new BufferedOutputStream(socket.getOutputStream());
        try (var d = new DataOutputStream(out)) {
            var p = Paths.get(path);
            var filename = p.getFileName().toString();

            System.out.println(String.format("Sending file %s to %s", filename, address));
            d.writeUTF(filename);
            d.writeUTF("abc123"); // TODO: relative path
            Files.copy(p, d);
        }
    }
}
