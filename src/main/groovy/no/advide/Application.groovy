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

  Application() {
    editorPanel = new EditorPanel()
    appFrame = new AppFrame(editorPanel)
    keys = new KeyInterpreter(editorPanel)
  }

  void open(Page startingPage) {
    new PageEditor(keys).kickstart(startingPage) { page ->
      appFrame.setHeaderText("Master - ${page.name}")
      editorPanel.updateContents(page.commands, page.document.cursor)
    }

    keys.onAction "cmd+enter", { appFrame.toggleFullScreen() }

    appFrame.show()
  }

}
