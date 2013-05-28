package com.ac.filter

import com.googlecode.javacv.cpp.opencv_core.cvCreateImage
import com.googlecode.javacv.cpp.opencv_core.cvGetSize
import com.googlecode.javacv.cpp.opencv_core.IplImage
import com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor
import com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY

class ImageGreyscaleFilter extends Filter {
	def apply(image: IplImage): IplImage = {
		val img: IplImage = cvCreateImage(cvGetSize(image), 8, 1)
		cvCvtColor(image, img, CV_BGR2GRAY);
		img
	}
}