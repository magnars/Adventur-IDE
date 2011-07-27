package no.advide

import no.advide.ui.AppFrame
import no.advide.ui.EditorPanel
import no.advide.ui.KeyInterpreter

class Application {

  static def lines = [
      "Det er middelalderen, sånn rundt te-tid. Du har vært på reisefot lenge, og",
      "lengre enn langt. Med deg har du et dyrebart langsverd, en gave fra din far.",
      "I beltet henger en liten lærpose som du fant på veien. I den ligger det tjue",
      "blanke sølvmynter - hele din samlede formue.",
      "#171"
  ]

  static main(args) {
    def editorPanel = new EditorPanel()
    def appFrame = new AppFrame(editorPanel)

    def editor = new Editor(lines: lines, cursor: new Cursor(x: 0, y: 0))

    editorPanel.textLayout = new TextLayout(lines: editor.lines, cursor: editor.cursor)

    editor.onChange { lines, cursor ->
      editorPanel.textLayout = new TextLayout(lines: lines, cursor: cursor)
      editorPanel.repaint()
    }

    def keys = new KeyInterpreter(editorPanel)
    keys.addListener editor
    keys.onAction "cmd+enter", { appFrame.toggleFullScreen() }

    appFrame.setHeaderText("Master - Rom 0")
    appFrame.show()
  }

}
