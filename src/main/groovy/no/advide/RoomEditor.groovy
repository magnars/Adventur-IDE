package no.advide

class RoomEditor extends EventEmitter {

  Page page
  RoomHistory roomHistory
  PageEditor pageEditor

  def actions = [
      "jump":           { if (page.targetRoomNumber) roomHistory.push(page.targetRoomNumber.number) },
      "escape":         { if (room.modified) { room.restoreOriginal() } else { roomHistory.pop() } },
      "cmd+S":          { room.save() },
      "cmd+Z":          { room.undo() },
      "shift+cmd+Z":    { room.redo() }
  ]

  RoomEditor(Page page, RoomHistory history) {
    this.page = page
    this.roomHistory = history
    pageEditor = new PageEditor(page)
    pageEditor.onChange { documentChanged() }
    pageEditor.onCursorMove { cursorMoved() }
  }

  Room getRoom() {
    roomHistory.current
  }

  def charTyped(c) {
    if (isJump(c)) {
      actionTyped('jump')
    } else {
      pageEditor.charTyped(c)
    }
  }

  private boolean isJump(c) {
    return c == "'" && !room.modified
  }

  def actionTyped(k) {
    def a = actions[k]
    if (a) {
      a.call()
      roomChanged()
    } else {
      pageEditor.actionTyped(k)
    }
  }

  def onDocumentChange(callback) {
    on('documentChange', callback)
  }

  def documentChanged() {
    emit('documentChange')
  }

  def onRoomChange(callback) {
    on('roomChange', callback)
  }

  def roomChanged() {
    emit('roomChange')
  }

  def onCursorMove(callback) {
    on('cursorMove', callback)
  }

  def cursorMoved() {
    emit('cursorMove')
  }

}
