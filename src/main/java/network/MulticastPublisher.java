package network;

import java.net.*;

import static network.MulticastSubscriber.BROADCAST_MESSAGE;

public class MulticastPublisher extends Thread {
    private static final int BROADCAST_INTERVAL_MS = 5000;

    private NetworkInterface nic;
    private DatagramSocket socket;
    private InetAddress group;
    private byte[] buf;

    public MulticastPublisher(NetworkInterface n) throws Exception {
        nic = n;
        socket = new DatagramSocket(new InetSocketAddress(nic.getInterfaceAddresses().get(0).getAddress(), 0));
    }

    public void run() {
        try {
            group = InetAddress.getByName(MulticastSubscriber.BROADCAST_ADDRESS);
            buf = BROADCAST_MESSAGE.getBytes();

            System.out.println(String.format("Broadcasting to multicast group for discovery on nic %s every %d seconds...", nic.getDisplayName(), BROADCAST_INTERVAL_MS / 1000));
            while(true) {
                var packet = new DatagramPacket(buf, buf.length, group, MulticastSubscriber.BROADCAST_PORT);
                socket.send(packet);

                Thread.sleep(BROADCAST_INTERVAL_MS);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception ex) {}
        }
    }
}
