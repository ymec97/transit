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
DEFAULT_REQUEST_FILE = "Requests/Request_example.xml" # Path to the template file for requests
NEW_REQUEST_FILE_FORMAT = "{}-{}.xml"

def _UpdateRequest(stop_code, line_code):
	"""
	Update the default request file with the given parameters
	"""
	DOMTree = xml.dom.minidom.parse(DEFAULT_REQUEST_FILE)
	collection = DOMTree.documentElement
	print(collection)

def ParseOutput(source_file, format=None):
	"""
	Parse the output file according to the requested format
	(Currently only one basic format - defaulting to none)
	"""
	pass

def SendRequest(stop_code, line_code):
	output_file = "{}{}-{}.xml".format(DEFAULT_REPLY_DEST, str(line_code), str(stop_code))
	print("this is the output {}".format(output_file))

	_UpdateRequest(stop_code, line_code)
	return 12

	headers = {'content-type': 'text/xml'}
	with open("Request_example.xml") as f:
		body = f.read()

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