# Not an executable, a simple constants python file

# SIRI API related consts
SIRI_URL 						= "http://siri.motrealtime.co.il:8081/Siri/SiriServices"
STOP_FIELD_STRING 				= "siri:MonitoringRef"
LINE_FIELD_STRING 				= "siri:LineRef"
REQUEST_TIMESTAMP_STRING 		= "siri:RequestTimestamp"


# CMD Related consts
STOP_CODE_ARG 					= 1
LINE_CODE_ARG 					= 2
MAX_ARGS_NUM 					= 3

# Environment related consts
DEFAULT_REPLY_DEST 				= "Output/"
REQUESTS_PATH 					= "Requests/"
DEFAULT_REQUEST_FILE 			= REQUESTS_PATH + "Request_template.xml" # Path to the template file for requests
NEW_REQUEST_FILE_FORMAT 		= "{}-{}.xml"

# Script logic related consts
DEFAULT_REQUEST_TIME 			= (8, 0, 0) # Default time we would request to view a bus (08:00:00)
START_TIME_FIELD_STRING 		= "siri:StartTime"
DEFAULT_GMT_STRING				= "02:00"