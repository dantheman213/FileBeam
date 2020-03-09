using Newtonsoft.Json;
using System;
using System.IO;
using System.Net;
using System.Text;

namespace FileBeam
{
    public class FileServer
    {
        private HttpListener http;

        public FileServer()
        {
            http = new HttpListener();
            http.Prefixes.Add(String.Format("http://*:{0}/", Network.PORT));
        }

        ~FileServer()
        {
            http.Prefixes.Clear();
            http.Stop();
            http.Close();
        }

        public void ProcessRoute(HttpListenerContext context)
        {
            var req = context.Request;
            var path = req.Url.AbsolutePath;
            Console.WriteLine($"Received request for route {path}");

            switch (path)
            {
                case "/beam/profile/identify":
                    RouteIdentify(context);
                    break;
                case "/beam/data/send":
                    RouteSend(context);
                    break;
                default:
                    RouteNotFound(context);
                    break;

            }
        }

        private void RouteNotFound(HttpListenerContext context)
        {
            var response = context.Response;
            response.StatusCode = 404;
            response.Close();
        }

        private void RouteIdentify(HttpListenerContext context)
        {
            var model = new IdentifyModel();
            model.DeviceName = FrameMain.DeviceName;

            var response = context.Response;
            response.StatusCode = 200;
            using (var os = response.OutputStream)
            {
                using (var sw = new StreamWriter(os, Encoding.UTF8))
                {
                    sw.Write(JsonConvert.SerializeObject(model));
                }
            }

            response.Close();
        }

        private void RouteSend(HttpListenerContext context)
        {
            var req = context.Request;
            var filename = req.Headers["X-File-Name"];
            var filePath = Path.Combine(Network.DefaultDirectory, filename);

            Console.WriteLine("Writing to " + filename);
            
            using (var ips = req.InputStream)
            {
                using (var ofs = new FileStream(filePath, FileMode.Create))
                {
                    ips.CopyTo(ofs);
                }
            }

            var response = context.Response;
            response.StatusCode = 200;
            response.Close();
        }

        public void Start()
        {
            http.Start();

            while (true)
            {
                var context = http.GetContext(); // blocking
                ProcessRoute(context);
            }
        }
    }
}
