package no.advide

import no.advide.ui.AppFrame
import no.advide.ui.EditorPanel
import no.advide.ui.KeyInterpreter

class Application {

  public static final String NAME = "Adventur IDE"

  static main(args) {
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", NAME);
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
    editor.kickstart(startingPage) { Page page ->
      appFrame.setHeaderText("${Adventure.current.name} - ${page.name}")
      appFrame.setModified(page.isModified())
      editorPanel.updateContents(page.formattedLines, page.document.cursor)
    }

    keys.addListener(editor)
    keys.onAction "cmd+enter", { appFrame.toggleFullScreen() }

    appFrame.show()
  }

}
