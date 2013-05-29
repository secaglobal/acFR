package com.ac.filter

import com.googlecode.javacv.cpp.opencv_core.cvCreateImage
import com.googlecode.javacv.cpp.opencv_core.cvGetSize
import com.googlecode.javacv.cpp.opencv_core.IplImage
import com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor
import com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY

class ImageThresholdFilter extends Filter {
  val thresholds = List[Byte](100, 100, 100)

  def apply(image: IplImage): IplImage = {
    val img: IplImage = cvCreateImage(cvGetSize(image), 8, 1)
    var x = 0
    var y = 0
    var pixelPosition = 0;
    val channelsNr = image.nChannels()
    var dataPtr = image.imageData()
    var resultPtr = img.imageData()

    while (y < image.height) {
      x = 0
      while (x < image.width) {
        pixelPosition = y * image.widthStep + x * channelsNr

        for (i <- 0 to channelsNr - 1) {
          if (dataPtr.get(pixelPosition + i) > thresholds(i)) {
            resultPtr.put(y * img.widthStep + x, 255.toByte)
          }
        }

        x += 1
      }
      y += 1
    }

    img
  }
}