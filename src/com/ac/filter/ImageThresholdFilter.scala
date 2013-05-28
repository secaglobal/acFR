package com.ac.filter

import com.googlecode.javacv.cpp.opencv_core.cvCreateImage
import com.googlecode.javacv.cpp.opencv_core.cvGetSize
import com.googlecode.javacv.cpp.opencv_core.IplImage
import com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor
import com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY

class ImageThresholdFilter extends Filter {
  val thresholds = List(100, 0, 0)

  def apply(image: IplImage): IplImage = {
    val size = cvGetSize(image)
    val img: IplImage = cvCreateImage(size, 16, 1)
    val width = size.width()
    val height = size.height()
    var x = 0
    var y = 0
    var pixelPosition = 0;
    val channelsNr = image.nChannels()
    var dataPtr = image.imageData()
    var resultPtr = img.imageData()
    var changed: Boolean = false

    while (y < height) {
      x = 0
      while (x < width) {
        pixelPosition = y * x * channelsNr + x * channelsNr
        changed = false

        for (i <- 0 to channelsNr - 1) {
          if (dataPtr.get(pixelPosition + i) > thresholds(i)) {
            resultPtr.put(y * x + x, 127)
            changed = true
          }
        }

        x += 1
      }
      y += 1
    }

    img
  }
}