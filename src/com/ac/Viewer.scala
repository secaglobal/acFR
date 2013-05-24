package com.ac

import com.googlecode.javacv.cpp.opencv_core._
import com.googlecode.javacv.cpp.opencv_highgui._
import com.googlecode.javacv.cpp.opencv_imgproc._
import java.awt.Cursor._
import javax.swing.ImageIcon
import scala.swing.Dialog.Message.Error
import scala.swing.FileChooser.Result.Approve
import scala.swing._


object Viewer extends SimpleSwingApplication {
	private lazy val fileChooser = new FileChooser
	def top: Frame = new MainFrame {
	  	title = "Viewer"
	  	
	  	var image: Option[IplImage] = None
	  		  
	  	val openImageAction = Action("Show image") {
		  cursor = getPredefinedCursor(WAIT_CURSOR)
		  try {
		    _openImage() match {
		      case Some(x) =>
		        image = Some(x)
		        imageView.icon = new ImageIcon(x.getBufferedImage())
		      case None => {}
		    }
		    
		    processImageAction.enabled = true
		  } finally {
		    cursor = getPredefinedCursor(DEFAULT_CURSOR)
		  }
		}
	  	
	  	val actionsList = new ComboBox(List("Grey"))
	  	
	  	val processImageAction = Action("Process") {
	  	  image = actionsList.selection.index match {
	  	    case 1 => _grayProcessor(image) 
	  	  } 
	  	  
	  	  imageView.icon = new ImageIcon(image.get.getBufferedImage())
	  	}
	  	
	  	processImageAction.enabled = false
	  	  
	  	val buttonPanel = new GridPanel(rows0 = 0, cols0 = 1) {
	  	  contents += new Button(openImageAction)
	  	  contents += actionsList
	  	  contents += new Button(processImageAction)
	  	}
	  	  
	  	val imageView = new Label
	  	
	  	contents = new BorderPanel () {
	  		add(new FlowPanel(buttonPanel),  BorderPanel.Position.West)
	  		
	  		val imageScrollPane = new ScrollPane(imageView) {
	  		  preferredSize = new Dimension(640, 480)
	  		}
	  		
	  		add(imageScrollPane, BorderPanel.Position.Center)
	  	}
	  	
		centerOnScreen()
	}
	
	private def _openImage(): Option[IplImage] = {
	  if (fileChooser.showOpenDialog(null) != Approve) {
	    return None
	  }
	  
	  val path = fileChooser.selectedFile.getAbsolutePath()
	  println(path)
	  val newImage = cvLoadImage(path)
	  if (newImage != null) {
	    Some(newImage)
	  } else {
	    Dialog.showMessage(null, "Cannot open image file: " + path, top.title, Error)
	    None
	  }
	}
	
	private def _grayProcessor(image: Option[IplImage]): Option[IplImage] = {
		if (image.isDefined) {
			val img: IplImage = image.get.clone()
			cvCvtColor(img, img, CV_BGR2GRAY);
			Some(img)
		}
		None
	}
	
	
		
}