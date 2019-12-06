package siri_sm_demo;

public class Debug_sm_response {

    public static void debug_sm_respone (ServiceDeliveryStructure Response) {
            System.out.println("ResponseTimestamp:         "+Response.responseTimestamp);
            System.out.println("ProducerRef:               "+Response.producerRef.getValue());
            System.out.println("ResponseMessageIdentifier: "+Response.responseMessageIdentifier.getValue());
            System.out.println("RequestMessageRef:         "+Response.requestMessageRef.getValue());
            System.out.println("Status:                    "+Response.status);

            if (Response.status == false) {
                System.out.println("There was an error");
                System.out.println("ErrorText:                 "+Response.errorCondition.otherError.errorText);
                System.out.println("Description:               "+Response.errorCondition.description.value);
            }

            if (Response.status == true) {
                int smds_num = 0;
                int msvs_num = 0;
                for (StopMonitoringDeliveryStructure smds: Response.stopMonitoringDelivery) {
                    System.out.println("stopMonitoringDelivery num "+smds_num+" start");
                    System.out.println("    ResponseTimestamp:         "+smds.responseTimestamp);
                    System.out.println("    Status:                    "+smds.status);
                    if (smds.status == false) {
                        System.out.println("    Error description: "+smds.errorCondition.description.getValue());
                        System.out.println("stopMonitoringDelivery num "+smds_num+" end");
                        smds_num++;
                    }
                    if (smds.status == true) {
                        msvs_num = 0;
                        for (MonitoredStopVisitStructure msvs: smds.monitoredStopVisit) {
                            System.out.println("    monitoredStopVisit sub "+msvs_num+" start");
                            System.out.println("        RecordedAtTime: "+msvs.recordedAtTime);
                            System.out.println("        ItemIdentifier: "+msvs.itemIdentifier);
                            System.out.println("        MonitoringRef: "+msvs.monitoringRef.getValue());
                            System.out.println("        LineRef: "+msvs.monitoredVehicleJourney.lineRef.getValue());
                            System.out.println("        DirectionRef: "+msvs.monitoredVehicleJourney.directionRef.getValue());
                            System.out.println("        PublishedLineName: "+msvs.monitoredVehicleJourney.publishedLineName.getValue());
                            if (msvs.monitoredVehicleJourney.vehicleRef != null) {
                               System.out.println("        vehicleRef: "+msvs.monitoredVehicleJourney.vehicleRef.getValue());
                            }   
                            System.out.println("        OperatorRef: "+msvs.monitoredVehicleJourney.operatorRef.getValue());
                            System.out.println("        DestinationRef: "+msvs.monitoredVehicleJourney.destinationRef.getValue());
                            if (msvs.monitoredVehicleJourney.vehicleLocation != null) {
                               System.out.println("        VehicleLocation: "
                                    +msvs.monitoredVehicleJourney.vehicleLocation.latitude.toString() + ","
                                    +msvs.monitoredVehicleJourney.vehicleLocation.longitude.toString());
                            }
                            System.out.println("        StopPointRef: "+msvs.monitoredVehicleJourney.monitoredCall.stopPointRef.getValue());
                            System.out.println("        OriginAimedDepartureTime: "+msvs.monitoredVehicleJourney.originAimedDepartureTime);
                            System.out.println("        AimedArrivalTime:         "+msvs.monitoredVehicleJourney.monitoredCall.aimedArrivalTime);
                            System.out.println("        ExpectedArrivalTime:      "+msvs.monitoredVehicleJourney.monitoredCall.expectedArrivalTime);
                        
                            System.out.println("    monitoredStopVisit sub "+msvs_num+" end");
                            msvs_num++;
                        }
                        System.out.println("stopMonitoringDelivery num "+smds_num+" end");
                        smds_num++;
                    }
                }
            }
        }
}
