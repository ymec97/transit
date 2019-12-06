using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Siri_sm_demo.WebReference;

namespace Siri_sm_demo
{
    class Program
    {
        static void Main(string[] args)
        {
            ServiceRequestStructure Request = new ServiceRequestStructure();
            Request.RequestorRef = new ParticipantRefStructure();
            Request.RequestorRef.Value = "AC456009";
            Request.RequestTimestamp = DateTime.Now;
            Request.MessageIdentifier = new MessageQualifierStructure();
            Request.MessageIdentifier.Value = "11111700:12223351669188:4684";

            StopMonitoringRequestStructure smrs = new StopMonitoringRequestStructure();
            smrs.version = "IL2.7";
            smrs.RequestTimestamp = DateTime.Now;
            smrs.Language = "he";
            smrs.MessageIdentifier = new MessageQualifierStructure();
            smrs.MessageIdentifier.Value = "55";
            smrs.MonitoringRef = new MonitoringRefStructure();
            smrs.MonitoringRef.Value = "32902"; 
            smrs.MaximumStopVisits = "30";
            smrs.PreviewInterval = "PT60M";
            smrs.MinimumStopVisitsPerLine = "1";
            Request.StopMonitoringRequest = new StopMonitoringRequestStructure[1];
            Request.StopMonitoringRequest[0] = smrs;

            SiriServices SS = new SiriServices();
            ServiceDeliveryStructure Response = new ServiceDeliveryStructure();
            Response = SS.GetStopMonitoringService(Request);

            int bp = 0;

            Print_sm_response.print_sm_respone(Response);

        }
    }
}
