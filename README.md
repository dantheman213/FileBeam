# FileBeam

Cross-platform desktop app to quickly share files with devices on the same network.

## Demo Example

Demo with server and client currently works.

### Server

```
C:\Users\server>java -jar FileBeam.jar
FileBeam Demo
Getting list of adapters...
0. Killer(R) Wi-Fi 6 AX1650x 160MHz Wireless Network Adapter (200NGW)
1. Hyper-V Virtual Ethernet Adapter
2. Hyper-V Virtual Ethernet Adapter #2
What network adapter would you like to use? 0
Starting multicast server...
Broadcasting to group for discovery...
Starting server...
Client connect with IP /192.168.1.40
Receiving file C:\Users\server\Documents\cloud_bin from IP /192.168.1.40
```

### Client

```
C:\Users\client>java -jar FileBeam.jar
FileBeam Demo
Getting list of adapters...
0. VirtualBox Host-Only Ethernet Adapter
1. Intel(R) I211 Gigabit Network Connection
2. Hyper-V Virtual Ethernet Adapter
3. Hyper-V Virtual Ethernet Adapter #2
What network adapter would you like to use? 1
Starting multicast server...
Starting server...
Broadcasting to group for discovery...
Client [/192.168.1.53] Message: FILE_BEAM_PING
Found IP /192.168.1.53 and adding into phonebook directory.
Looking for file at C:\Users\client\Desktop\cloud_bin
Connecting to server IP 192.168.1.53
Sending file cloud_bin to 192.168.1.53
COMPLETE
```