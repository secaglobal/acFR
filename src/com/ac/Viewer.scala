package com.ac

object Viewer extends App{
	def and1(a: Boolean, b: String) = if (a) b else false
	def and2(a: Boolean, b: => String) = if (a) b else false
	def print(a: String) = {
	  println(a)
	  a
	}
}