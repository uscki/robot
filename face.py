import EventBus
from EventBus import ImageEvent
from bot import Bot
import cv2

frontalface = cv2.CascadeClassifier("resources/haarcascade_frontalface_alt2.xml")     # frontal face pattern detection
flags = (cv2.cv.CV_HAAR_DO_CANNY_PRUNING + cv2.cv.CV_HAAR_FIND_BIGGEST_OBJECT + cv2.cv.CV_HAAR_DO_ROUGH_SEARCH)

class FaceEvent(): pass

class FaceDetector(Bot):
    def loop(self):
        imgevent = EventBus.await(ImageEvent)
        fface = frontalface.detectMultiScale(imgevent.image, 1.3, 4, flags, (60,60))
        if fface != ():
            fe = FaceEvent()
            fe.image = imgevent.image
            fe.faces = fface
            EventBus.trigger(fe)
