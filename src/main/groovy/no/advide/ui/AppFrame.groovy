package no.advide.ui

import java.awt.GraphicsEnvironment
import javax.swing.JFrame

class AppFrame extends JFrame {

  def device = GraphicsEnvironment.localGraphicsEnvironment.defaultScreenDevice

  void toggleFullScreen() {
    if (device.fullScreenSupported) {
      this.dispose()
      if (this.undecorated) {
        this.undecorated = false
        device.fullScreenWindow = null
      } else {
        this.undecorated = true
        device.fullScreenWindow = this
      }
      this.show()
    }
  }
}
