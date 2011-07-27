package no.advide.ui

import groovy.swing.SwingBuilder
import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JFrame
import javax.swing.WindowConstants
import java.awt.*
import javax.swing.JLabel

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
      frame(this, title: 'ide-adv', size: frameSize(), location: [50, 30], show: false, defaultCloseOperation: WindowConstants.DISPOSE_ON_CLOSE) {
        borderLayout()
        panel(new BackgroundPanel(), constraints: BorderLayout.CENTER, border: BorderFactory.createEmptyBorder(5, 10, 5, 10)) {
          boxLayout(axis: BoxLayout.Y_AXIS)
          panel(maximumSize: [1000, 150], minimumSize: [0, 150], opaque: false) {
            header = label(foreground: new Color(150, 150, 140))
          }
          panel(editorPanel, maximumSize: [1000, 2000], focusable: true, opaque: false)
        }
      }
    }
  }

  private ArrayList<Integer> frameSize() {
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
