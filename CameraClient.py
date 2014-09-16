import time
import cv2
from bot import Bot
import EventBus
from EventBus import ImageEvent

class CameraClient(Bot):
    def __init__(self):
        self.webcam = cv2.VideoCapture(0)
        self.webcam.set(cv2.cv.CV_CAP_PROP_FRAME_WIDTH, 320)
        self.webcam.set(cv2.cv.CV_CAP_PROP_FRAME_HEIGHT, 240)

    def loop(self):
        val, image = self.webcam.read()
        if self.running:
            ev = ImageEvent()
            ev.image = image
            EventBus.trigger(ev)
            time.sleep(2)