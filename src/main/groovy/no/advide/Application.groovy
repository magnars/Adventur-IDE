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

  private static def openEditor(Page page) {
    def editorPanel = new EditorPanel()
    def appFrame = new AppFrame(editorPanel)

    def editor = new Editor(document: page.document)

    editor.onChange { document ->
      def commands = new CommandParser(document).parse()
      editor.updateLines commands.toNewScript() // <-- denne forsvinner når Commands bruker document selv
      editorPanel.textLayout = [lines: commands.formattedLines, cursor: document.cursor]
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
