using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.NetworkInformation;
using System.Net.Sockets;
using NetTools;

namespace FileBeam
{
    public class Network
    {
        public static int PORT = 58715;
        public static int TIMEOUT_MS = 150;
        public static string DefaultDirectory = Environment.GetFolderPath(Environment.SpecialFolder.Desktop);

        public static List<NetworkInterfaceModel> GetPrimaryNetworkInterfaces()
        {
            var items = new List<NetworkInterfaceModel>();

            var interfaces = NetworkInterface.GetAllNetworkInterfaces();
            foreach (var adapter in interfaces.Where(x => x.OperationalStatus == OperationalStatus.Up))
            {
                if (adapter.Name.ToLower() == "ethernet" || adapter.Name.ToLower() == "wi-fi")
                {
                    var props = adapter.GetIPProperties();
                    var result = props.UnicastAddresses.FirstOrDefault(x => x.Address.AddressFamily == AddressFamily.InterNetwork);
                    if (result != null)
                    {
                        var item = new NetworkInterfaceModel();
                        item.Name = adapter.Name;
                        item.Mac = adapter.GetPhysicalAddress().ToString();
                        item.IpAddress = result.Address.ToString();
                        item.SubnetMask = result.IPv4Mask.ToString();
                        items.Add(item);
                    }
                }
            }

            return items;
        }

        public static List<string> GetIpRange(string ip, string subnet)
        {
            var items = new List<string>();

            var range = IPAddressRange.Parse(String.Format("{0}/{1}", ip, subnet));            
            foreach (var item in range)
            {
                if (item.ToString() == ip)
                {
                    continue;
                }

                items.Add(item.ToString());
            }

            return items;
        }
    }
}
