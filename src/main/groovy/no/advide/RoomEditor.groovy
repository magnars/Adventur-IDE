package no.advide

class RoomEditor extends EventEmitter {

  Page page
  Room room
  PageEditor pageEditor

  def actions = [
      "cmd+S":          { room.save() }
  ]

  RoomEditor(Page page, Room room) {
    this.page = page
    this.room = room
    pageEditor = new PageEditor(page)
    pageEditor.onChange { documentChanged() }
  }

  def charTyped(c) {
    if (isJump(c)) {
      if (page.targetRoomNumber) roomChanged(page.targetRoomNumber.number)
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
      roomChanged(room.number)
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

  def roomChanged(int number) {
    emit('roomChange', number)
  }

}
