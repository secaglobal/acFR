package com.ac

import com.googlecode.javacv.cpp.opencv_core._
import com.googlecode.javacv.cpp.opencv_highgui._
import java.awt.Cursor._
import javax.swing.ImageIcon
import scala.swing.Dialog.Message.Error
import scala.swing.FileChooser.Result.Approve
import scala.swing._
import com.ac.filter.Filter
import com.ac.filter.ImageGreyscaleFilter
import com.ac.filter.ImageThresholdFilter
import com.ac.filter.ColorDetectorFilter

object Viewer extends SimpleSwingApplication {
  private lazy val fileChooser = new FileChooser
  def top: Frame = new MainFrame {
    title = "Viewer"

    val filters = List(
      "Greyscale" -> classOf[ImageGreyscaleFilter],
      "Threshold" -> classOf[ImageThresholdFilter],
      "Detect color" -> classOf[ColorDetectorFilter]
    )

    var image: Option[IplImage] = None

    val actionsList = new ComboBox(filters.map((filter: (String, AnyRef)) => filter._1))

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

    val processImageAction = Action("Process") {
      val filter: Filter = filters(actionsList.selection.index)._2.newInstance()
      image = Some(filter(image.get))
      imageView.icon = new ImageIcon(image.get.getBufferedImage())
    }

    processImageAction.enabled = false

    val buttonPanel = new GridPanel(rows0 = 0, cols0 = 1) {
      contents += new Button(openImageAction)
      contents += actionsList
      contents += new Button(processImageAction)
    }

    val imageView = new Label

    contents = new BorderPanel() {
      add(new FlowPanel(buttonPanel), BorderPanel.Position.West)

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
    val newImage = cvLoadImage(path)

    if (newImage != null) {
      Some(newImage)
    } else {
      Dialog.showMessage(null, "Cannot open image file: " + path, top.title, Error)
      None
    }
  }
}
