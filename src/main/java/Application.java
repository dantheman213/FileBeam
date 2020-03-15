import network.*;
import views.FrameMain;

import java.util.ArrayList;

public class Application {
    public static void main(String[] args) throws Exception {
        var interfaces = Network.GetActiveInterfaces();

        var receivers = new ArrayList<MulticastSubscriber>();
        for (var nic : interfaces) {
            var receiver = new MulticastSubscriber(nic);
            receiver.start();
            receivers.add(receiver);
        }

        var server = new Server();
        server.start();

        var f = new FrameMain();
        f.show();

        var publishers = new ArrayList<MulticastPublisher>();
        for (var nic : interfaces) {
            var publisher = new MulticastPublisher(nic);
            publisher.start();
            publishers.add(publisher);
        }

        // block until jframe exits
        while (true) {
            Thread.sleep(1000);
        }
    }
}
