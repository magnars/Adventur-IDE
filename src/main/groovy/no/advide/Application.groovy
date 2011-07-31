package no.advide

import no.advide.commands.CommandList
import no.advide.commands.CommandParser
import no.advide.commands.ProseCommand
import no.advide.ui.AppFrame
import no.advide.ui.EditorPanel
import no.advide.ui.KeyInterpreter

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
      justifyWordsInProse(commands)
      updateEditorPanel(commands, document, editorPanel)
    }
    editor.changed()

    def keys = new KeyInterpreter(editorPanel)
    keys.addListener editor
    keys.onAction "cmd+enter", { appFrame.toggleFullScreen() }

    appFrame.setHeaderText("Master - ${page.name}")
    appFrame.show()
  }

  private static def updateEditorPanel(CommandList commands, document, EditorPanel editorPanel) {
    editorPanel.textLayout = [lines: commands.formattedLines, cursor: document.cursor]
    editorPanel.repaint()
  }

  private static def justifyWordsInProse(CommandList commands) {
    commands.getAll(ProseCommand).each { new WordWrapper(it.fragment).justify() }
  }

}
