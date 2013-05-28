package com.ac.filter

import com.googlecode.javacv.cpp.opencv_core.IplImage

abstract class Filter {
	def apply(image: IplImage): IplImage
}