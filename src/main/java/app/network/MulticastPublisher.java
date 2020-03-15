package app.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static app.network.MulticastReceiver.BROADCAST_MESSAGE;

public class MulticastPublisher extends Thread {
    private static final int BROADCAST_INTERVAL_MS = 10000;

    private DatagramSocket socket;
    private InetAddress group;
    private byte[] buf;

    public void run() {
        try {
            socket = new DatagramSocket();
            group = InetAddress.getByName(MulticastReceiver.BROADCAST_ADDRESS);
            buf = BROADCAST_MESSAGE.getBytes();

            System.out.println("Broadcasting to group for discovery...");

            var packet = new DatagramPacket(buf, buf.length, group, MulticastReceiver.BROADCAST_PORT);
            socket.send(packet);

            Thread.sleep(BROADCAST_INTERVAL_MS);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception ex) {}
        }
    }
}
