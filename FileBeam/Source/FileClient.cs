using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace FileBeam
{
    public class FileClient
    {
        public static Dictionary<string, IdentifyModel> DiscoverServers()
        {
            var phonebook = new Dictionary<string, IdentifyModel>();

            var interfaces = Network.GetPrimaryNetworkInterfaces();
            var net = interfaces[0];
            var range = Network.GetIpRange(net.IpAddress, net.SubnetMask);
            foreach (var address in range)
            {
                Console.WriteLine("[Debug] Trying " + address);
                var result = ScanIpForDiscovery(address);
                if (result != null)
                {
                    phonebook.Add(address, result);
                }
            }

            return phonebook;
        }

        private static IdentifyModel ScanIpForDiscovery(string ipAddress)
        {
            try
            {
                var url = String.Format("http://{0}:{1}/beam/profile/identify", ipAddress, Network.PORT);
                var request = (HttpWebRequest)WebRequest.Create(url);
                request.Timeout = Network.TIMEOUT_MS;
                request.AutomaticDecompression = DecompressionMethods.GZip;

                using (var response = (HttpWebResponse)request.GetResponse())
                using (var stream = response.GetResponseStream())
                using (var reader = new StreamReader(stream))
                {
                    if (response.StatusCode == HttpStatusCode.OK)
                    {
                        var result = reader.ReadToEnd();
                        if (result.Length > 0)
                        {
                            var model = JsonConvert.DeserializeObject<IdentifyModel>(result);
                            return model;
                        }
                    }
                }
            }
            catch (Exception ex)
            {

            }

            return null;
        }
    }
}
