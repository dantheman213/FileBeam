package app;

import app.network.*;
import app.views.FrameMain;

import java.io.*;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        var f = new FrameMain();
        f.show();
//        System.out.println("FileBeam Demo");
//
//        var console = System.console();
//        var interfaces = Network.GetActiveInterfaces();
//
//        System.out.println("Getting list of adapters...");
//        for (var i = 0; i < interfaces.size(); i++) {
//            var item = interfaces.get(i);
//            System.out.println(String.format("%d. %s", i, item.getDisplayName()));
//        }
//
//        var adapterIndex = console.readLine("What network adapter would you like to use? ");
//
//        var receiver = new MulticastReceiver(interfaces.get(Integer.parseInt(adapterIndex)));
//        receiver.start();
//
//        var server = new Server();
//        server.start();
//
//        var publisher = new MulticastPublisher();
//        publisher.start();
//
//        // wait for a peer to show up in multicast group
//        while (receiver.phonebook.size() == 0) {
//            Thread.sleep(2000);
//        }
//
//        // peer is available, send a file
//        var tmpFilename = "cloud_bin";
//        var path = Paths.get(System.getProperty("user.home"), "Desktop", tmpFilename);
//        System.out.println(String.format("Looking for file at %s", path));
//        if (new File(path.toString()).exists()) {
//            var client = new Client(receiver.phonebook.keys().nextElement());
//            client.sendFile(path.toString());
//        }
//
//        System.out.println("COMPLETE");
    }
}
