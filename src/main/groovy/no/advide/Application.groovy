package no.advide

import no.advide.ui.AppFrame
import no.advide.ui.EditorPanel
import no.advide.ui.KeyInterpreter

import no.advide.commands.CommandParser

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

    editor.onChange { lines, cursor ->
      def commands = new CommandParser(lines).parse()
      editor.lines = commands.toNewScript()
      editorPanel.textLayout = [lines: commands.formattedLines, cursor: cursor]
      editorPanel.repaint()
    }
    editor.changed()

    def keys = new KeyInterpreter(editorPanel)
    keys.addListener editor
    keys.onAction "cmd+enter", { appFrame.toggleFullScreen() }

    appFrame.setHeaderText("Master - Rom 0")
    appFrame.show()
  }

}
