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

        #capture = cv.CreateCameraCapture(-1)   
        img = cv.QueryFrame(capture)

        if img is not None:

                cv.SaveImage("/home/mennov1/webcam/webcam.jpg",img)
                os.system("curl -k -F \"uploadedfile=@/home/mennov1/webcam/webcam.jpg\" https://www.uscki.nl/~kruit/zebra/modules/Webcam/upload.php >> /dev/null")
        else:
                print "Something went wrong.."
                os.system("echo \"Webcam is dood!\" | curl -d @- https://robot.uscki.nl/log/log.php")

        t = time.clock()
        while t < (t_prev + delay):
                t = time.clock()
        t_prev = time.clock()
        
        #capture = None
