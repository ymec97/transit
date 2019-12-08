#!/usr/bin/env python3

import requests
import sys

from xml.dom.minidom import parse
import xml.dom.minidom

# This consts should be moved (and will be moved) to a better location in the future 
SIRI_URL="http://siri.motrealtime.co.il:8081/Siri/SiriServices"
STOP_CODE_ARG = 1
LINE_CODE_ARG = 2
MAX_ARGS_NUM = 3
DEFAULT_REPLY_DEST = "Output/"
REQUESTS_PATH = "Requests/"
DEFAULT_REQUEST_FILE = REQUESTS_PATH + "Request_example.xml" # Path to the template file for requests
NEW_REQUEST_FILE_FORMAT = "{}-{}.xml"

def _CreateNewRequest(dom, stop_code, line_code):
	"""
	load default request file to memory and create a new request file with the new values
	Return the path to the new request file
	"""
	new_request_file = "{}{}-{}.xml".format(REQUESTS_PATH, str(line_code), str(stop_code))
	try:

		phase_code = 0
		collection = dom.documentElement
		
		phase_code = 1
		request = collection.getElementsByTagName("siri:MonitoringRef")[0]
		request_node = request.childNodes[0]

		phase_code = 2
		if request_node.nodeType == request_node.TEXT_NODE:
			# We reached the actual value to change
			request_node.replaceWholeText(stop_code)

		with open(new_request_file, "w") as request_file:
			dom.writexml(request_file)

	except Exception as error_msg:
		print("Failed parsing ", error_msg)

	# Return the new request file
	return new_request_file


def ParseOutput(source_file, format=None):
	"""
	Parse the output file according to the requested format
	(Currently only one basic format - defaulting to none)
	"""
	pass

def SendRequest(stop_code, line_code):
	output_file = "{}{}-{}.xml".format(DEFAULT_REPLY_DEST, str(line_code), str(stop_code))
	
	DOMTree = xml.dom.minidom.parse(DEFAULT_REQUEST_FILE)
	new_request = _CreateNewRequest(DOMTree, stop_code, line_code)
	
	headers = {'content-type': 'text/xml'}
	with open(new_request) as request:
		body = request.read()

	response = requests.post(SIRI_URL,data=body,headers=headers)
	with open(output_file, "w") as file:
	    file.write(str(response.content))

def main(argv, argc):
	if (argc < MAX_ARGS_NUM):
		print("Error not enough arguments")
		return 1

	stop_code = argv[STOP_CODE_ARG]
	line_code = argv[LINE_CODE_ARG]

	print("Showing information for:\nLine: {0}, Stop: {1}".format(line_code, stop_code))

	ParseOutput(SendRequest(stop_code, line_code))

	return 0

if __name__ == '__main__':
	exit(main(sys.argv, len(sys.argv)))