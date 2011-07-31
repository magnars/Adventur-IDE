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
    new Application().open(Adventure.current.loadNotes())
  }

  EditorPanel editorPanel
  AppFrame appFrame
  Editor editor
  KeyInterpreter keys

  Application() {
    editorPanel = new EditorPanel()
    appFrame = new AppFrame(editorPanel)
    keys = new KeyInterpreter(editorPanel)
    editor = new Editor()
  }

  void open(Page page) {
    hookUpEditorEvents()
    editor.document = page.document
    editor.changed()

    keys.onAction "cmd+enter", { appFrame.toggleFullScreen() }
    appFrame.setHeaderText("Master - ${page.name}")

    appFrame.show()
  }

  void hookUpEditorEvents() {
    keys.addListener editor
    editor.onChange { document ->
      def commands = new CommandParser(document).parse()
      justifyWordsInProse(commands)
      updateEditorPanel(commands, document.cursor)
    }
  }

  void updateEditorPanel(CommandList commands, cursor) {
    editorPanel.textLayout = [lines: commands.formattedLines, cursor: cursor]
    editorPanel.repaint()
  }

  void justifyWordsInProse(CommandList commands) {
    commands.getAll(ProseCommand).each { new WordWrapper(it.fragment).justify() }
  }

}
