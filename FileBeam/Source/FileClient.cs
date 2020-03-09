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
        public Dictionary<string, IdentifyModel> Phonebook;

        public void DiscoverServers()
        {
            if (Phonebook == null)
            {
                Phonebook = new Dictionary<string, IdentifyModel>();
            }

            var interfaces = Network.GetPrimaryNetworkInterfaces();
            var net = interfaces[0];
            var range = Network.GetIpRange(net.IpAddress, net.SubnetMask);

            // TODO: Remove entries that were found earlier but not found now
            foreach (var address in range)
            {
                var result = ScanIpForDiscovery(address);
                if (result != null)
                {
                    if (Phonebook.ContainsKey(address))
                    {
                        Phonebook[address] = result;
                    }
                    else
                    {
                        Phonebook.Add(address, result);
                    }

                    // TODO: Remove
                    var filePath = Path.Combine(Network.DefaultDirectory, "file");
                    if (File.Exists(filePath))
                    {
                        Console.WriteLine("Sending file " + filePath);
                        SendFile(address, filePath);
                    }
                }
            }
        }

        private static IdentifyModel ScanIpForDiscovery(string ipAddress)
        {
            try
            {
                var url = String.Format("http://{0}:{1}/beam/profile/identify", ipAddress, Network.PORT);
                Console.WriteLine("[Debug] Trying " + url);

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

        public void SendDirectory(string ipAddress, string dirPath, string relativePath = "")
        {
            // TODO
        }

        public void SendFile(string ipAddress, string filePath, string relativePath = "")
        {
            try
            {
                var url = String.Format("http://{0}:{1}/beam/data/send", ipAddress, Network.PORT);
                Console.WriteLine("[Debug] Send file " + url);

                var req = (HttpWebRequest)WebRequest.Create(url);
                req.Method = "POST";
                req.ContentType = "application/octet-stream";
                req.Timeout = 2000;
                req.Headers["X-File-Relative-Path"] = relativePath;
                req.Headers["X-File-Name"] = Path.GetFileName(filePath);
                req.AutomaticDecompression = DecompressionMethods.GZip;

                using (var stream = req.GetRequestStream())
                using (var fs = new FileStream(filePath, FileMode.Open, FileAccess.Read))
                {
                    byte[] buffer = new byte[1 * 1024 * 1024]; // 1MB
                    int bytesRead = fs.Read(buffer, 0, buffer.Length);

                    while (bytesRead > 0)
                    {
                        stream.Write(buffer, 0, bytesRead);
                        bytesRead = fs.Read(buffer, 0, buffer.Length);
                    }
                }

                using (var res = (HttpWebResponse)req.GetResponse())
                {
                    if (res.StatusCode == HttpStatusCode.OK)
                    {
                        // TODO
                    }

                    res.Close();
                }
            }
            catch (Exception ex)
            {

            }
        }
    }
}
