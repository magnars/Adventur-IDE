package no.advide.ui

import java.awt.GraphicsEnvironment
import javax.swing.JFrame

class AppFrame extends JFrame {

  def device = GraphicsEnvironment.localGraphicsEnvironment.defaultScreenDevice

  void toggleFullScreen() {
    if (device.fullScreenSupported) {
      this.dispose()
      if (isFullScreen()) {
        exitFullScreen()
      } else {
        enterFullScreen()
      }
      this.show()
    }
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
