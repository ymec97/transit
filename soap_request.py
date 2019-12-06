#!/usr/bin/env python3

import requests
url="http://siri.motrealtime.co.il:8081/Siri/SiriServices"
#headers = {'content-type': 'application/soap+xml'}
headers = {'content-type': 'text/xml'}
with open("Request_example.xml") as f:
	body = f.read()

print(body)
response = requests.post(url,data=body,headers=headers)
with open("output.xml", "w") as f:
    f.write(str(response.content))

