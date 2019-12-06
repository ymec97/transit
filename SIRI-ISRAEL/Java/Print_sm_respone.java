package siri_sm_demo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Print_sm_respone {

    public static void print_sm_respone (ServiceDeliveryStructure Response) {
        try {
            FileOutputStream fs = new FileOutputStream ("siri_sm_track.txt");
            OutputStreamWriter LogFile = new OutputStreamWriter (fs,"UTF-8");

            LogFile.write("ResponseTimestamp:         "+Response.responseTimestamp+"\n");
            LogFile.write("ProducerRef:               "+Response.producerRef.getValue()+"\n");
            LogFile.write("ResponseMessageIdentifier: "+Response.responseMessageIdentifier.getValue()+"\n");
            LogFile.write("RequestMessageRef:         "+Response.requestMessageRef.getValue()+"\n");
            LogFile.write("Status:                    "+Response.status+"\n");

            if (Response.status == false) {
                LogFile.write("There was an error\n");
                LogFile.write("ErrorText:                 "+Response.errorCondition.otherError.errorText+"\n");
                LogFile.write("Description:               "+Response.errorCondition.description.value+"\n");
            }

            if (Response.status == true) {
                int smds_num = 0;
                int msvs_num = 0;
                for (StopMonitoringDeliveryStructure smds: Response.stopMonitoringDelivery) {
                    LogFile.write("stopMonitoringDelivery num "+smds_num+" start\n");
                    LogFile.write("    ResponseTimestamp:         "+smds.responseTimestamp+"\n");
                    LogFile.write("    Status:                    "+smds.status+"\n");
                    if (smds.status == false) {
                        LogFile.write("    Error description: "+smds.errorCondition.description.getValue()+"\n");
                        LogFile.write("stopMonitoringDelivery num "+smds_num+" end\n");
                        smds_num++;
                    }
                    if (smds.status == true) {
                        msvs_num = 0;
                        for (MonitoredStopVisitStructure msvs: smds.monitoredStopVisit) {
                            LogFile.write("    monitoredStopVisit sub "+msvs_num+" start\n");
                            LogFile.write("        RecordedAtTime: "+msvs.recordedAtTime+"\n");
                            LogFile.write("        ItemIdentifier: "+msvs.itemIdentifier+"\n");
                            LogFile.write("        MonitoringRef: "+msvs.monitoringRef.getValue()+"\n");
                            LogFile.write("        LineRef: "+msvs.monitoredVehicleJourney.lineRef.getValue()+"\n");
                            LogFile.write("        DirectionRef: "+msvs.monitoredVehicleJourney.directionRef.getValue()+"\n");
                            LogFile.write("        PublishedLineName: "+msvs.monitoredVehicleJourney.publishedLineName.getValue()+"\n");
                            if (msvs.monitoredVehicleJourney.vehicleRef != null) {
                               LogFile.write("        vehicleRef: "+msvs.monitoredVehicleJourney.vehicleRef.getValue()+"\n");
                            }   
                            LogFile.write("        OperatorRef: "+msvs.monitoredVehicleJourney.operatorRef.getValue()+"\n");
                            LogFile.write("        DestinationRef: "+msvs.monitoredVehicleJourney.destinationRef.getValue()+"\n");
                            if (msvs.monitoredVehicleJourney.vehicleLocation != null) {
                               LogFile.write("        VehicleLocation: "
                                    +msvs.monitoredVehicleJourney.vehicleLocation.latitude.toString() + ","
                                    +msvs.monitoredVehicleJourney.vehicleLocation.longitude.toString()+"\n");
                            }
                            LogFile.write("        StopPointRef: "+msvs.monitoredVehicleJourney.monitoredCall.stopPointRef.getValue()+"\n");
                            LogFile.write("        OriginAimedDepartureTime: "+msvs.monitoredVehicleJourney.originAimedDepartureTime+"\n");
                            LogFile.write("        AimedArrivalTime:         "+msvs.monitoredVehicleJourney.monitoredCall.aimedArrivalTime+"\n");
                            LogFile.write("        ExpectedArrivalTime:      "+msvs.monitoredVehicleJourney.monitoredCall.expectedArrivalTime+"\n");
                        
                            LogFile.write("    monitoredStopVisit sub "+msvs_num+" end\n");
                            msvs_num++;
                        }
                        LogFile.write("stopMonitoringDelivery num "+smds_num+" end\n");
                        smds_num++;
                    }
                }
            }
            LogFile.close();
        }
        catch (IOException ex) {System.out.println("IOException:"+ex);}
    }           
    
}
