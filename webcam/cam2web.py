#!/usr/bin/python2

import cv2.cv as cv
import time
import os

capture = cv.CreateCameraCapture(-1)

cv.SetCaptureProperty(capture,cv.CV_CAP_PROP_FRAME_WIDTH, 640)
cv.SetCaptureProperty(capture,cv.CV_CAP_PROP_FRAME_HEIGHT, 480);

t_prev = time.clock()
delay = 1.0

while True:
	
	if capture is not None:
		img = cv.QueryFrame(capture)
		if img is not None:
			cv.SaveImage("/home/mennov1/webcam/webcam.jpg",img)
			os.system("curl -k -F \"uploadedfile=@/home/mennov1/webcam/webcam.jpg\" https://robot.uscki.nl/webcam.php")
		else:
			print "No image"
	else:
		print "No capture, trying to re-init"
		capture = cv.CreateCameraCapture(-1)

	t = time.clock()
	while t < (t_prev + delay):
		t = time.clock()
	t_prev = time.clock()
