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
    def page = new Page(room.createDocument())
    page.commands.justifyProse(80)
    renderPage(page)
    updateAppFrame()
    waitForEvents(page)
  }

  private void updateAppFrame() {
    appFrame.setHeaderText("${Adventure.current.name} - ${room.name}")
    appFrame.setModified(room.modified)
  }

  private void renderPage(Page page) {
    editorPanel.updateContents(new PageFormatter(page, room.modified).formattedLines, page.document.cursor)
  }

  private void waitForEvents(Page page) {
    def editor = new RoomEditor(page, room)
    editor.onDocumentChange {
      room.update(page.document)
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
