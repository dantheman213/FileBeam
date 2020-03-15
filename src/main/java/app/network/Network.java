package app.network;

import java.net.NetworkInterface;
import java.util.*;

public class Network {
    public static ArrayList<NetworkInterface> GetActiveInterfaces() throws Exception {
        var interfaces = NetworkInterface.getNetworkInterfaces();
        var list = new ArrayList<NetworkInterface>();

        while (interfaces.hasMoreElements()) {
            var networkInterface = interfaces.nextElement();
            if (networkInterface.isLoopback() || !networkInterface.isUp() || networkInterface.isVirtual()) {
                continue;
            }

            list.add(networkInterface);
        }

        return list;
    }
}
