package no.advide

class PageEditor extends EventEmitter {

  Page page
  Room room
  TextEditor textEditor

  def actions = [
      "cmd+S":          { room.save() },
      "tab":            { if (page.nextRoomNumber) page.document.cursor = page.nextRoomNumber.position },
      "shift+tab":      { if (page.previousRoomNumber) page.document.cursor = page.previousRoomNumber.position },
      "ctrl+alt+cmd+O": { page.commands.each { it.replaceWithOldStyle() } },
      "ctrl+alt+cmd+N": { page.commands.each { it.replaceWithNewStyle() } }
  ]

  PageEditor(Page page, Room room) {
    this.page = page
    this.room = room
    textEditor = new TextEditor(page.document)
    textEditor.onChange { documentChanged() }
  }

  def charTyped(c) {
    if (isJump(c)) {
      if (page.targetRoomNumber) roomChanged(page.targetRoomNumber.number)
    } else {
      textEditor.charTyped(c)
    }
  }

  private boolean isJump(c) {
    return c == "'" && !room.modified
  }

  def actionTyped(k) {
    def a = actions[k]
    if (a) {
      a.call()
      documentChanged()
    } else {
      textEditor.actionTyped(k)
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
