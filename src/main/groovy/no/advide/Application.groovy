package no.advide

import no.advide.commands.CommandList
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
  PageEditor editor
  KeyInterpreter keys

  Application() {
    editorPanel = new EditorPanel()
    appFrame = new AppFrame(editorPanel)
    keys = new KeyInterpreter(editorPanel)
    editor = new PageEditor()
  }

  void open(Page startingPage) {
    hookUpEditorEvents()
    editor.page = startingPage
    editor.changed()

    keys.onAction "cmd+enter", { appFrame.toggleFullScreen() }

    appFrame.show()
  }

  void hookUpEditorEvents() {
    keys.addListener editor
    editor.onChange { page ->
      appFrame.setHeaderText("Master - ${page.name}")
      updateEditorPanel(page.commands, page.document.cursor)
    }
  }

  void updateEditorPanel(CommandList commands, cursor) {
    editorPanel.textLayout = [lines: commands.formattedLines, cursor: cursor]
    editorPanel.repaint()
  }

}
