package no.advide.ui

import groovy.swing.SwingBuilder
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GraphicsDevice
import java.awt.GraphicsEnvironment
import java.awt.Toolkit
import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.WindowConstants
import no.advide.Application

class AppFrame extends JFrame {

  EditorPanel editorPanel
  GraphicsDevice device
  Dimension screenSize
  JLabel header

  AppFrame(edPanel) {
    editorPanel = edPanel
    device = GraphicsEnvironment.localGraphicsEnvironment.defaultScreenDevice
    screenSize = Toolkit.getDefaultToolkit().getScreenSize()
    setupUI()
  }

  void setModified(modified) {
    rootPane.putClientProperty("Window.documentModified", modified)
  }

  void toggleFullScreen() {
    if (device.fullScreenSupported) {
      this.dispose()
      if (isFullScreen()) { exitFullScreen() } else { enterFullScreen() }
      this.show()
    }
  }

  void setHeaderText(text) {
    header.text = text 
  }

  private void setupUI() {
    new SwingBuilder().edt {
      frame(this, title: Application.NAME, size: frameSize(), location: [50, 30], show: false, defaultCloseOperation: WindowConstants.DISPOSE_ON_CLOSE) {
        borderLayout()
        panel(Theme.panel, constraints: BorderLayout.CENTER, border: BorderFactory.createEmptyBorder(5, 10, 5, 10)) {
          boxLayout(axis: BoxLayout.Y_AXIS)
          panel(maximumSize: [1000, 150], minimumSize: [0, 150], opaque: false) {
            header = label(foreground: Theme.headerText)
          }
          panel(editorPanel, maximumSize: [1000, 2000], focusable: true, opaque: false)
        }
      }
    }
  }

  private def frameSize() {
    return [1120, screenSize.height.intValue() - 100]
  }

  private def enterFullScreen() {
    this.undecorated = true
    device.fullScreenWindow = this
  }

  private def exitFullScreen() {
    this.undecorated = false
    device.fullScreenWindow = null
  }

  private boolean isFullScreen() {
    return undecorated
  }
}
