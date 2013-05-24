package com.ac

import com.googlecode.javacv.CanvasFrame
import com.googlecode.javacv.cpp.opencv_highgui._
import com.googlecode.javacv.cpp.opencv_core._
import com.googlecode.javacv.cpp.opencv_legacy._
import com.googlecode.javacv.cpp.opencv_objdetect._

object Recognizer {

    val image: CvMat = cvLoadImageM("images/face.jpg", CV_LOAD_IMAGE_GRAYSCALE)

    val original = new CanvasFrame("Original", 1)
    //val output = new CanvasFrame("Result", 1)
    original.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE)
    //output.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE)

    //cvFlip(image, image, 0)
    //val face  = new CvFace(image)
    //println(face.MouthRect())
    //output.showImage(image.asIplImage())

    val storage: CvMemStorage = CvMemStorage.create();
    val cascade: CvHaarClassifierCascade = new CvHaarClassifierCascade(cvLoad("resources/haarcascade_frontalface_alt.xml"))
    val faces = cvHaarDetectObjects(image, cascade, storage, 1.1, 0, 1)

    //println(faces.first().)
    cvRectangle(image, cvPoint(100, 100), cvPoint(300, 300), CV_RGB(255, 0, 0), 3, 8, 0);

    original.showImage(image.asIplImage())

    //for (Some(i) <- faces) println(face)

}