package no.advide

import no.advide.ui.AppFrame
import no.advide.ui.EditorPanel
import no.advide.ui.KeyInterpreter

class Application {

  public static final String NAME = "Adventur IDE"

  static main(args) {
    macify()
    Adventure.choose()
    new Application().open(Adventure.current.notes)
  }

  EditorPanel editorPanel
  AppFrame appFrame
  KeyInterpreter keys
  Room room

  Application() {
    editorPanel = new EditorPanel()
    appFrame = new AppFrame(editorPanel)
    keys = new KeyInterpreter(editorPanel)
  }

  void open(Room startingRoom) {
    room = startingRoom
    roomChanged()
    keys.onAction "cmd+enter", { appFrame.toggleFullScreen() }
    appFrame.show()
  }

  void roomChanged() {
    def page = new Page(room)
    page.commands.optimizeDocument()
    renderPage(page)
    updateAppFrame()
    waitForEvents(page)
  }

  private void updateAppFrame() {
    appFrame.setHeaderText("${Adventure.current.name} - ${room.name}")
    appFrame.setModified(room.modified)
  }

  private void renderPage(Page page) {
    editorPanel.updateContents(new PageFormatter(page, room.modified).formattedLines, room.cursor)
  }

  private void waitForEvents(Page page) {
    def editor = new PageEditor(page, room)
    editor.onDocumentChange {
      room.lines = page.document.lines
      room.cursor = page.document.cursor
      roomChanged()
    }
    editor.onRoomChange { int number ->
      room = Adventure.current.getRoom(number)
      roomChanged()
    }
    keys.setListener(editor)
  }

  private static def macify() {
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", NAME)
  }

}
