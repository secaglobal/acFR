package com.ac

import com.googlecode.javacv.CanvasFrame
import com.googlecode.javacv.cpp.opencv_highgui._
import com.googlecode.javacv.cpp.opencv_core.CvMat
import com.googlecode.javacv.cpp.opencv_core.cvFlip
import com.googlecode.javacv.cpp.opencv_legacy.CvFace


object Main {

  def main(args: Array[String]): Unit = {
    val image: CvMat = cvLoadImageM("images/badoink.jpg", CV_LOAD_IMAGE_GRAYSCALE)
    
    val original = new CanvasFrame("Original", 1)
    val output = new CanvasFrame("Result", 1)
  	original.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE)
    output.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE)
    
    original.showImage(image.asIplImage())
    
    cvFlip(image, image, 0)
    val face  = new CvFace(image)
    println(face.MouthRect())
    output.showImage(image.asIplImage())
  }

}