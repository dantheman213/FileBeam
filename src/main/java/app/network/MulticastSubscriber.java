package app.network;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.concurrent.ConcurrentHashMap;

public class MulticastSubscriber extends Thread {
    protected static final String BROADCAST_ADDRESS = "230.0.0.0";
    protected static final int BROADCAST_PORT = 30000;
    protected static final String BROADCAST_MESSAGE = "FILE_BEAM_PING";

    private NetworkInterface networkInterface;
    private InetAddress localAddress;
    private MulticastSocket socket = null;
    private byte[] buf = new byte[256];

    public ConcurrentHashMap<String, String> phonebook;

    public MulticastSubscriber(NetworkInterface network) {
        phonebook = new ConcurrentHashMap<String, String>();
        networkInterface = network;
        localAddress = networkInterface.getInterfaceAddresses().get(0).getAddress();
    }

    public void run() {
        try {
            System.out.println(String.format("Starting multicast subscriber on network interface: %s...", networkInterface.getDisplayName()));

            socket = new MulticastSocket(BROADCAST_PORT);
            socket.setInterface(localAddress);

            var group = InetAddress.getByName(BROADCAST_ADDRESS);
            socket.joinGroup(group);

            while (true) {
                var packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                var received = new String(packet.getData(), 0, packet.getLength());
                var publisherIp = packet.getAddress();

                // skip submissions sent from this device
                if (publisherIp.equals(localAddress)) {
                    continue;
                }

                System.out.println(String.format("Client [%s] Message: %s", packet.getAddress(), received));
                if (received.equals(BROADCAST_MESSAGE)) {
                    if (!phonebook.containsKey(publisherIp.toString())) {
                        System.out.println(String.format("Found IP %s and adding into phonebook directory.", publisherIp.toString()));
                        phonebook.put(publisherIp.toString().replaceAll("/", ""), publisherIp.toString()); // TODO: add a device name
                    }
                }
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
