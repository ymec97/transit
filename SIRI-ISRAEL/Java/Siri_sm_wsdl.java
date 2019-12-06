
package siri_sm_demo;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

public class Siri_sm_wsdl {

    public static boolean wsdl_debug_l1 = true;
    public static boolean wsdl_debug_l2 = false;
    public static boolean wsdl_debug_l3 = true;
    
    public static ServiceDeliveryStructure Send_siri_sm_req (List<Gtfs_D.Sm_reqT> Siri_sm_req) {

        // Set here the username that you got from Ministry Of Transportation
        String RequestorRef = "AC456009"; 

        ServiceDeliveryStructure Response = null;
        
        // Lets check first that you have connection to the MOT server
        try{
            InetAddress address = InetAddress.getByName("siri.motrealtime.co.il");
            boolean reachable = address.isReachable(10000);
            if (reachable == true) {
                System.out.println("  Ping to MOT server is OK");
            }
            else {
                System.out.println("Can't connect to MOT server");
                System.out.println("Try to enter the following adderss with a browser");
                System.out.println("   http://siri.motrealtime.co.il:8081/Siri/SiriServices");
                System.out.println("This program now exits");
                System.exit(0);
            }
        } 
        catch (Exception ex) {
            System.out.println("Exception:"+ex);
            System.out.println("Can't connect to MOT server");
            System.out.println("Try to enter the following adderss with a browser");
            System.out.println("   http://siri.motrealtime.co.il:8081/Siri/SiriServices");
            System.out.println("This program now exits");
            System.exit(0);
        }
    
        if (wsdl_debug_l1 == true) {
            // Enable tracing HTTP transaction to the console
            System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
            System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "999999");
        }
        
        try {
            int MaximumStopVisits = 100;
            int i;
            ServiceRequestStructure Request = new ServiceRequestStructure();
            Calendar cal1 = Calendar.getInstance();
            long date2d;
            java.util.Date date1 = cal1.getTime();
            java.util.Date date2 = cal1.getTime();
            GregorianCalendar c1 = new GregorianCalendar();
            GregorianCalendar c2 = new GregorianCalendar();
            c1.setTime(date1);
            XMLGregorianCalendar date1_g = DatatypeFactory.newInstance().newXMLGregorianCalendar(c1);
            XMLGregorianCalendar StartTime;
            Request.setRequestTimestamp(date1_g); 
            
            ParticipantRefStructure p = new ParticipantRefStructure();
            p.setValue(RequestorRef);
            Request.setRequestorRef(p);
        
            MessageQualifierStructure mi = new MessageQualifierStructure();
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd:HHmmss:SSS");
            mi.setValue("AC:"+sdf2.format(date1));
            Request.setMessageIdentifier(mi);

            Duration pi;
            LineRefStructure lrs = new LineRefStructure();
            BigInteger masv = BigInteger.valueOf(MaximumStopVisits);
            List<StopMonitoringRequestStructure>  smr_a  = new ArrayList<>(); 
            StopMonitoringRequestStructure[] smr  = new StopMonitoringRequestStructure[100];
            MonitoringRefStructure[]         mr   = new MonitoringRefStructure[100];
            MessageQualifierStructure[]      mi_a = new MessageQualifierStructure[100];
            for (i=0; i<100; i++) {
                smr[i]  = new StopMonitoringRequestStructure();
                mr[i]   = new MonitoringRefStructure();
                mi_a[i] = new MessageQualifierStructure();
            }
            i=0;
            for (Gtfs_D.Sm_reqT ssr: Siri_sm_req) {
                System.out.println("  Send_siri_sm_req ssr: MonitoringRef="
                    +ssr.MonitoringRef+" lineRef="+ssr.lineRef+
                    " PreviewInterval="+ssr.PreviewInterval+
                    "  StartTime="+ssr.StartTime);
                smr[i].setVersion("2.7");
                smr[i].setRequestTimestamp(date1_g); 
                pi=DatatypeFactory.newInstance().newDuration("PT"+ssr.PreviewInterval+"M");
                smr[i].setPreviewInterval(pi);
                mr[i].setValue(ssr.MonitoringRef);
                smr[i].setMonitoringRef(mr[i]);
                
                date2d = date1.getTime() + 60*1000*ssr.StartTime;
                date2.setTime(date2d);
                
                c2.setTime(date2);
                StartTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(c2);        
                smr[i].setStartTime(StartTime);
                mi_a[i].setValue(ssr.Label+ssr.MonitoringRef);
                smr[i].setMessageIdentifier(mi_a[i]);
                
                if (ssr.lineRef != null) {
                    lrs.setValue(ssr.lineRef);
                    smr[i].setLineRef(lrs);
                }
                smr[i].setMaximumStopVisits(masv);
                smr_a.add(smr[i]);
                i++;
            }
            Request.stopMonitoringRequest = smr_a;
            
            SiriServices service = new SiriServices();
            SOAPPort port = service.getSiriWSPort();
            
            if (wsdl_debug_l1 == true) {
                PrintStream ps_console = System.out;
                FileOutputStream fs = new FileOutputStream ("siri_sm_log.xml");
                BufferedOutputStream bos = new BufferedOutputStream(fs);
                PrintStream ps = new PrintStream(bos);
                // Redirect console output to iri_sm_log.xml 
                System.setOut(ps);
                Response = port.getStopMonitoringService(Request);
                ps.close();
                // Restore console to normal
                System.setOut(ps_console);
            }
            else {
                Response = port.getStopMonitoringService(Request);
            }

            System.out.println("  Recieved SIRI SM response");
            
            if (wsdl_debug_l2 == true) {
               Debug_sm_response.debug_sm_respone(Response);
            }
            if (wsdl_debug_l3 == true) {
               Print_sm_respone.print_sm_respone(Response);
            }
	}
        catch (DatatypeConfigurationException ex1)  {System.out.println("DatatypeConfigurationException:"+ex1);}
        catch (FileNotFoundException ex2) {System.out.println("FileNotFoundException:"+ex2);}
        
        return Response;
    }

}
