package no.advide

import no.advide.ui.AppFrame
import no.advide.ui.EditorPanel
import no.advide.ui.KeyInterpreter

import no.advide.commands.CommandParser

class Application {

  static main(args) {
    Adventure.choose()
    openEditor(Adventure.current.loadNotes())
  }

  private static def openEditor(page) {
    def editorPanel = new EditorPanel()
    def appFrame = new AppFrame(editorPanel)

    def editor = new Editor(lines: page.lines, cursor: new Cursor(x: 0, y: 0))

    editor.onChange { lines, cursor ->
      def commands = new CommandParser(lines, cursor).parse()
      editor.updateLines commands.toNewScript()
      editorPanel.textLayout = [lines: commands.formattedLines, cursor: cursor]
      editorPanel.repaint()
    }
    editor.changed()

    def keys = new KeyInterpreter(editorPanel)
    keys.addListener editor
    keys.onAction "cmd+enter", { appFrame.toggleFullScreen() }

    appFrame.setHeaderText("Master - ${page.name}")
    appFrame.show()
  }

}
