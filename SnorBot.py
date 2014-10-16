import EventBus
import cv2
from game import Game
from face import FaceEvent
import os.path

class SnorBot(Game):

    def play(self):

        fe = EventBus.await(FaceEvent)
        img1, faces = fe.image, fe.faces

        img2 = cv2.imread(os.path.join("resources","snor.png"), -1)

        for (x, y, w, h) in faces:
            x_offset = x + 0.25 * w
            y_offset = y + 0.65 * h
            snorheight, snorwidth = img2.shape[0:2]
            sx = (0.5 * w) / snorwidth
            sy = (0.25 * h) / snorheight
            resized_snor = cv2.resize(img2, (0,0), fx=sx, fy=sy) 
            for c in range(0,3):
                img1[y_offset:y_offset+resized_snor.shape[0], x_offset:x_offset+resized_snor.shape[1], c] = resized_snor[:,:,c] * (resized_snor[:,:,3]/255.0) +  img1[y_offset:y_offset+resized_snor.shape[0], x_offset:x_offset+resized_snor.shape[1], c] * (1.0 - resized_snor[:,:,3]/255.0)

        # Display the resulting frame
        cv2.imwrite("snor.jpeg", img1)


