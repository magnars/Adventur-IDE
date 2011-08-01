package no.advide

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
  KeyInterpreter keys
  PageEditor editor

  Application() {
    editorPanel = new EditorPanel()
    appFrame = new AppFrame(editorPanel)
    keys = new KeyInterpreter(editorPanel)
    editor = new PageEditor()
  }

  void open(Page startingPage) {
    editor.kickstart(startingPage) { page ->
      appFrame.setHeaderText("Master - ${page.name}")
      appFrame.setModified(page.isModified())
      editorPanel.updateContents(page.commands, page.document.cursor)
    }

    keys.addListener(editor)
    keys.onAction "cmd+enter", { appFrame.toggleFullScreen() }

    appFrame.show()
  }

}
