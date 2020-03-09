using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FileBeam
{
    public class NetworkInterfaceModel
    {
        public string Name { get; set; }
        public string Mac { get; set; }
        public string IpAddress { get; set; }
        public string SubnetMask { get; set; }
    }
}
