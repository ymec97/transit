#!/usr/bin/env python3
import urllib.request
import os

def GetFile(url, file, dest_folder=None, username=None, passwd=None):
	""" Get a file from a url"""
	if not dest_folder:
		# To make sure dest_folder can precede file
		dest_folder = ""

	urllib.request.urlretrieve(url, dest_folder + file)

def main():
	GTFS_URL = "ftp://gtfs.mot.gov.il/"
	GTFS_FILE_NAME = "israel-public-transportation.zip"
	DOWNLOAD_PATH = "folder/"

	GetFile(GTFS_URL, GTFS_FILE_NAME, dest_folder = DOWNLOAD_PATH)

	return 0

if __name__ == "__main__":
	exit(main())

