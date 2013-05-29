package com.ac.filter

import com.googlecode.javacv.cpp.opencv_core.cvCreateImage
import com.googlecode.javacv.cpp.opencv_core.cvGetSize
import com.googlecode.javacv.cpp.opencv_core.IplImage
import com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor
import com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY

class ColorDetectorFilter extends Filter {
	def apply(image: IplImage): IplImage = {
    val target3Ch = List(100, 100, 100)
    val result = cvCreateImage(cvGetSize(image), 8, 1)
    val dataPtr = image.imageData()
    val resultPtr = result.imageData()
    var x = 0
    var y = 0
    var y_offset = 0
    var offset = 0
    var dist = 0

    while (y < image.height) {
      y_offset = y * image.widthStep

      while (x < image.width()) {
        offset = y_offset + x * image.nChannels

        dist = colorDistance(dataPtr.get(offset), dataPtr.get(offset + 1), dataPtr.get(offset + 2), target3Ch)

        if (dist < 300) {
          resultPtr.put(y * result.widthStep + x, 255.toByte)
        }

        x +=1
      }

      y += 1
    }

    result
	}

  def colorDistance(b: Int, g: Int, r: Int, target: List[Int]) : Int  = target(0) - b + target(1) - g + target(2) - r
}