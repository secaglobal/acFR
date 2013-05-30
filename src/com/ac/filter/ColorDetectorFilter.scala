package com.ac.filter

import com.googlecode.javacv.cpp.opencv_core.cvCreateImage
import com.googlecode.javacv.cpp.opencv_core.cvGetSize
import com.googlecode.javacv.cpp.opencv_core.IplImage

class ColorDetectorFilter extends Filter {
	def apply(image: IplImage): IplImage = {
    val target3Ch = List(100, 130, 200)
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
      x = 0

      while (x < image.width) {
        offset = y_offset + (x * image.nChannels)

        dist = colorDistance(0xff & dataPtr.get(offset), 0xff & dataPtr.get(offset + 1), 0xff & dataPtr.get(offset + 2), target3Ch)

        if (dist <= 100) {
          resultPtr.put(y * result.widthStep() + x, 255.toByte)
        } else {
          resultPtr.put(y * result.widthStep() + x, 0)
        }

        x +=1
      }

      y += 1
    }

    result
	}

  def colorDistance(b: Int, g: Int, r: Int, target: List[Int]) : Int  = {
    val res = Math.abs(target(0) - b) + Math.abs(target(1) - g) + Math.abs(target(2) - r)
    if (res < 100) {
      println("|%d - %d| + |%d - %d| + |%d - %d| = %d".format(b, target(0), target(1), g, target(2), r, res))
    }

    res
  }
}