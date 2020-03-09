using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace FileBeam
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            var t1 = new Thread(() =>
            {
                var s = new FileServer();
                s.Start();
            });
            t1.IsBackground = true;
            t1.Start();

            var servers = FileClient.DiscoverServers();
            MessageBox.Show(JsonConvert.SerializeObject(servers));

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new FrameMain());
        }
    }
}
