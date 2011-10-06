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
  RoomHistory roomHistory

  Application() {
    editorPanel = new EditorPanel()
    appFrame = new AppFrame(editorPanel)
    keys = new KeyInterpreter(editorPanel)
  }

  void open(Room startingRoom) {
    roomHistory = new RoomHistory(startingRoom)
    roomChanged()
    keys.onAction "cmd+enter", { appFrame.toggleFullScreen() }
    appFrame.show()
  }

  Room getRoom() {
    roomHistory.current
  }

  void roomChanged() {
    def page = new Page(room.createDocument())
    page.justifyProse()
    renderPage(page)
    updateAppFrame()
    waitForEvents(page)
  }

  private void updateAppFrame() {
    appFrame.setHeaderText("${Adventure.current.name} - ${room.name}${room.modified ? ' (endret)' : ''}")
    appFrame.setModified(room.modified)
  }

  private void renderPage(Page page) {
    editorPanel.updateContents(new PageFormatter(page, room.modified).formattedLines, page.cursor)
  }

  private void waitForEvents(Page page) {
    def editor = new RoomEditor(page, roomHistory)
    editor.onCursorMove {
      renderPage(page)
      updateAppFrame()
      waitForEvents(page)
    }
    editor.onDocumentChange {
      room.update(page.document)
      roomChanged()
    }
    editor.onRoomChange {
      if (roomHistory.empty()) System.exit(0)
      roomChanged()
    }
    keys.setListener(editor)
  }

  private static def macify() {
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", NAME)
  }

}
