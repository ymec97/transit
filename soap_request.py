#!/usr/bin/env python3.7

import requests
import sys

import env.constants as constants

from xml.dom.minidom import parse
from xml.dom.minidom import parseString
from xml.dom.minidom import Document as XMLDoc
import xml.dom.minidom

import datetime

def _UpdateField(dom: XMLDoc, field: str, value: str) -> bool:
	"""
	Get an xml handler object and update a specific field with a given value
	Note: If there are multiple fields with the same name, the same value is updated.

	dom(XMLDoc): Pay attention this is passed by refrence
	"""
	try:
		requests = dom.documentElement.getElementsByTagName(field)
		for request in requests:
			request_node = request.childNodes[0]

			if request_node.nodeType == request_node.TEXT_NODE:
				# We reached the actual value to change
				request_node.replaceWholeText(value)

	except Exception as error_msg:
		print("Failed updating xml\nError: {error_msg}".format(error_msg=error_msg))
		return False
	
	return True

def _GetCurrentTimeFormat() -> str:
	"""
	Get the current time according to MOT (siri-sm) defined format
	Note: No support for DST
	
	Returns:
	    str: current datetime (format: yyyy-mm-ddThh:mm:ss.ms) 
	"""
	# MOT protocol works with gmt time

	return datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S.%f+{timezone}".format(timezone=constants.DEFAULT_GMT_STRING))

def _CreateNewRequest(dom, stop_code, line_code, start_date_time):
	"""
	load default request file to memory and create a new request file with the new values
	Return the path to the new request file
	"""

	new_request_file = "{}{}-{}.xml".format(constants.REQUESTS_PATH, str(line_code), str(stop_code))

	current_datetime = _GetCurrentTimeFormat()
	if not _UpdateField(dom, constants.REQUEST_TIMESTAMP_STRING, current_datetime):
		print("Failed updating field [{field}] with value: [{value}]\nError: {error_msg}".format(field=constants.REQUEST_TIMESTAMP_STRING, value=current_datetime))
		return None
	if not _UpdateField(dom, constants.STOP_FIELD_STRING, stop_code):
		print("Failed updating field [{field}] with value: [{value}]\nError: {error_msg}".format(field=constants.STOP_FIELD_STRING, value=stop_code))
		return None
	if not _UpdateField(dom, constants.LINE_FIELD_STRING, line_code):
		print("Failed updating field [{field}] with value: [{value}]".format(field=constants.LINE_FIELD_STRING, value=line_code))
		return None
	if not _UpdateField(dom, constants.START_TIME_FIELD_STRING, start_date_time):
		print("Failed updating field [{field}] with value: [{value}]".format(field=constants.START_TIME_FIELD_STRING, value=start_date_time))
		return None


	with open(new_request_file, "w") as request_file:
		dom.writexml(request_file)


	# Return the new request file
	return new_request_file


def _GetDefaultRequestTime() -> datetime.datetime:
	"""
	Get the date of tomorrow with the default time
	
	Returns:
	    datetime.datetime
	"""

	request_date = datetime.date.today() + datetime.timedelta(days=1)
	new_datetime = datetime.datetime.combine(request_date, datetime.time(*constants.DEFAULT_REQUEST_TIME))

	return new_datetime.strftime("%Y-%m-%dT%H:%M:%S.%f+{timezone}".format(timezone=constants.DEFAULT_GMT_STRING))
	

def ParseOutput(source_file, format=None):
	"""
	Parse the output file according to the requested format
	(Currently only one basic format - defaulting to none)
	"""
	pass

def SendRequest(stop_code, line_code, start_date_time):
	output_file = "{}{}-{}.xml".format(constants.DEFAULT_REPLY_DEST, str(line_code), str(stop_code))
	
	parsed_xml = parse(constants.DEFAULT_REQUEST_FILE)
	new_request = _CreateNewRequest(parsed_xml, stop_code, line_code, start_date_time)
	
	headers = {'content-type': 'text/xml'}
	with open(new_request) as request:
		body = request.read()

	response = requests.post(constants.SIRI_URL,data=body,headers=headers)

	# Parse the response xml to print pretty xml to file
	parsed_xml = parseString(response.content)
	with open(output_file, "w") as file:
		file.write(parsed_xml.toprettyxml())



def main(argv, argc):
	if (argc < constants.MAX_ARGS_NUM):
		print("Error not enough arguments")
		return 1

	stop_code = argv[constants.STOP_CODE_ARG]
	line_code = argv[constants.LINE_CODE_ARG]

	# In the future should be accepted from the user
	start_date_time = _GetDefaultRequestTime()
	
	print("Showing information for:\nLine: {0}, Stop: {1}, Date: {2}".format(line_code, stop_code, start_date_time))

	ParseOutput(SendRequest(stop_code, line_code, start_date_time))

	return 0

if __name__ == '__main__':
	exit(main(sys.argv, len(sys.argv)))