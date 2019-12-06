package siri_sm_demo;

public class Gtfs_D {

    public static class Cordinates {
        double lat;
        double lon;
   
        public Cordinates (double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }
    }    

    public static class Cordinates_generic {
        double lat;
        double lon;
        String text;
        Integer size;
        String color;
   
        public Cordinates_generic (double lat, double lon, String text, Integer size, String color) {
            this.lat = lat;
            this.lon = lon;
            this.text = text;
            this.size = size;
            this.color = color;
        }
    }    

    // Class type for codes_share.txt	
    public static class CodesSharesT {
        Integer CodeShareId;
        String  CodeShareName;	
        String  Operators;	
        String  Comment;	
        public CodesSharesT	(Integer CodeShareId,String CodeShareName, String Operators, String Comment) {
            this.CodeShareId   = CodeShareId;  
            this.CodeShareName = CodeShareName;  
            this.Operators     = Operators;
            this.Comment       = Comment;	
        }
    }
    
    // Class type for ClusterToLine.txt	
    public static class ClusterT {
        String  OperatorName;
        Integer OfficeLineId;
        Integer OperatorLineId;	
        String  ClusterName;	
        String  FromDate;	
        String  ToDate;	
        Integer ClusterId;	
        Integer LineType;	
        String  LineTypeDesc;	
        String  ClusterSubDesc;	
        public ClusterT	(String OperatorName,Integer OfficeLineId,Integer OperatorLineId,String ClusterName,
                         String FromDate,String ToDate,Integer ClusterId,Integer LineType,String LineTypeDesc,String ClusterSubDesc)	{
            this.OperatorName   = OperatorName;  
            this.OfficeLineId   = OfficeLineId;  
            this.OperatorLineId	= OperatorLineId;
            this.ClusterName	= ClusterName;	
            this.FromDate       = FromDate;	   
            this.ToDate	        = ToDate;	       
            this.ClusterId      = ClusterId;	   
            this.LineType       = LineType;      	
            this.LineTypeDesc   = LineTypeDesc;  	
            this.ClusterSubDesc	= ClusterSubDesc;
        }
    }
	
    // Class type for agency.txt
    public static class AgencyT {
        Integer agency_id;
        String agency_name;
        public AgencyT (Integer agency_id, String agency_name) {
            this.agency_id = agency_id;
            this.agency_name = agency_name;
        }
    }

    // Class type for routes.txt
    public static class RoutesT {
        Integer route_id;
        Integer agency_id;
        String  route_short_name;
        String  route_long_name;
        String  route_desc;
        Integer route_type;
        public RoutesT (Integer route_id,Integer agency_id,String route_short_name,String route_long_name,String route_desc,Integer route_type) {
            this.route_id          = route_id;         
            this.agency_id         = agency_id;         
            this.route_short_name  = route_short_name;  
            this.route_long_name   = route_long_name;   
            this.route_desc        = route_desc;        
            this.route_type        = route_type;        
        }
    }

    // Class type for locations raw data
    public static class LocationsT {
        String   location_start_time;
		String   location_current_time;
        double   location_lat;
        double   location_lon;
        public LocationsT (String location_start_time, String location_current_time, double location_lat, double location_lon) { 
            this.location_start_time    = location_start_time;
            this.location_current_time  = location_current_time;
            this.location_lat           = location_lat;
            this.location_lon           = location_lon;  
    	}
    }
	
    // Class type for routes.txt
    public static class ShapesT {
        Integer  shape_id;
	double   shape_pt_lat;
	double   shape_pt_lon;
	Integer  shape_pt_sequence;
	public ShapesT (Integer shape_id,double shape_pt_lat,double shape_pt_lon,Integer shape_pt_sequence) {
            this.shape_id          = shape_id;
            this.shape_pt_lat      = shape_pt_lat;
            this.shape_pt_lon      = shape_pt_lon;
            this.shape_pt_sequence = shape_pt_sequence;  
    	}
    }
	
    // Class type for stops.txt
    public static class StopsT {
	Integer  stop_id;
        Integer  stop_code;
        String   stop_name;
        String   stop_desc;
	double   stop_lat;
	double   stop_lon;
	Integer  location_type;
	Integer  parent_station;	
	public StopsT (Integer stop_id,Integer stop_code, String stop_name, String stop_desc, double stop_lat, double stop_lon, Integer location_type, Integer parent_station) {
            this.stop_id        = stop_id;      
            this.stop_code      = stop_code;     
            this.stop_name      = stop_name;    
            this.stop_desc      = stop_desc;   
            this.stop_lat       = stop_lat;     
            this.stop_lon       = stop_lon;    
            this.location_type  = location_type;
            this.parent_station = parent_station;	
    	}
    }

    // Class type for trips.txt
    public static class TripsT {
        Integer  route_id;
        Integer  service_id;
        String   trip_id;
        Integer  direction_id;
        Integer  shape_id;
	public TripsT (Integer route_id, Integer service_id, String trip_id, Integer direction_id, Integer shape_id) {
           this.route_id     = route_id;    
           this.service_id   = service_id; 
           this.trip_id      = trip_id;  
           this.direction_id = direction_id;
           this.shape_id     = shape_id;
    	}
    }

    // Class type for stop_times.txt
    public static class StopTimesT {
        // arrival_time and departure_time are NOT in this class as we use the short stop_times.txt 
        String   trip_id;
        Integer  stop_id;
        Integer  stop_sequence;
        Byte     pickup_type;
        Byte     drop_off_type;
        public StopTimesT (String trip_id, Integer  stop_id, Integer stop_sequence, Byte pickup_type, Byte drop_off_type) {
            this.trip_id       = trip_id;      
            this.stop_id       = stop_id;      
            this.stop_sequence = stop_sequence;
            this.pickup_type   = pickup_type;  
            this.drop_off_type = drop_off_type;
    	}
    }
	
    public static class Sm_reqT {
        String MonitoringRef;
        String lineRef;
        Integer PreviewInterval; // PreviewInterval in minutes
        Integer StartTime;       // The delta, in minutes from current time, for the StartTime value
        String Label;
        
        public Sm_reqT (String MonitoringRef, String lineRef, Integer PreviewInterval, Integer StartTime, String Label) {
            this.MonitoringRef   = MonitoringRef;
            this.lineRef         = lineRef;
            this.PreviewInterval = PreviewInterval;
            this.StartTime       = StartTime;
            this.Label           = Label;
        }
    }    
    
}