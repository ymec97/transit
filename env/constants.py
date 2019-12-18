# Not an executable, a simple constants python file

# This consts should be moved (and will be moved) to a better location in the future 
SIRI_URL="http://siri.motrealtime.co.il:8081/Siri/SiriServices"
STOP_CODE_ARG = 1
LINE_CODE_ARG = 2
MAX_ARGS_NUM = 3
DEFAULT_REPLY_DEST = "Output/"
REQUESTS_PATH = "Requests/"
DEFAULT_REQUEST_FILE = REQUESTS_PATH + "Request_template.xml" # Path to the template file for requests
NEW_REQUEST_FILE_FORMAT = "{}-{}.xml"
STOP_FIELD_STRING = "siri:MonitoringRef"
LINE_FIELD_STRING = "siri:LineRef"
REQUEST_TIMESTAMP_STRING = "siri:RequestTimestamp"
