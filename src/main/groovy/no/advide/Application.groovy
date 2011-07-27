package no.advide

import groovy.swing.SwingBuilder
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Toolkit
import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.WindowConstants
import no.advide.ui.AppFrame
import no.advide.ui.EditorPanel
import no.advide.ui.KeyInterpreter
import no.advide.ui.BackgroundPanel

class Application {

  static def lines = [
      "Det er middelalderen, sånn rundt te-tid. Du har vært på reisefot lenge, og",
      "lengre enn langt. Med deg har du et dyrebart langsverd, en gave fra din far.",
      "I beltet henger en liten lærpose som du fant på veien. I den ligger det tjue",
      "blanke sølvmynter - hele din samlede formue.",
      "#171"
  ]

  static main(args) {
    def appFrame = new AppFrame()
    def editorPanel = new EditorPanel()
    def editor = new Editor(lines: lines, cursor: new Cursor(x: 0, y: 0))

    editorPanel.textLayout = new TextLayout(lines: editor.lines, cursor: editor.cursor)

    editor.onChange { lines, cursor ->
      editorPanel.textLayout = new TextLayout(lines: lines, cursor: cursor)
      editorPanel.repaint()
    }

    def keys = new KeyInterpreter(editorPanel)
    keys.addListener editor
    keys.onAction "cmd+enter", { appFrame.toggleFullScreen() }


    new SwingBuilder().edt {
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      frame(appFrame, title: 'ide-adv', size: [1120, screenSize.height.intValue() - 100], location: [50, 30],  show: true, defaultCloseOperation: WindowConstants.DISPOSE_ON_CLOSE) {
        borderLayout()
        panel(new BackgroundPanel(), constraints: BorderLayout.CENTER, border: BorderFactory.createEmptyBorder(5, 10, 5, 10)) {
          boxLayout(axis:BoxLayout.Y_AXIS)
          panel(maximumSize: [1000, 150], minimumSize: [0, 150], opaque: false) {
            label(text: "Master - Rom 0", foreground: new Color(150, 150, 140))
          }
          panel(editorPanel, maximumSize: [1000, 2000], focusable: true, opaque: false)
        }
      }
    }

  }

}
