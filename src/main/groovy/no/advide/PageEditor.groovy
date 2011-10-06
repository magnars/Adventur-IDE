package no.advide

class PageEditor extends EventEmitter {

  Page page
  TextEditor textEditor

  def cursorMoves = [
      "tab":            { page.moveCursorTo page.nextRoomNumber },
      "shift+tab":      { page.moveCursorTo page.previousRoomNumber }
  ]

  def actions = [
      "cmd+F":          { page.nextFix?.fix() },
      "ctrl+alt+cmd+O": { page.changeToOldStyle() },
      "ctrl+alt+cmd+N": { page.changeToNewStyle() }
  ]

  PageEditor(Page page) {
    this.page = page
    textEditor = new TextEditor(page.document)
    textEditor.onChange { changed() }
    textEditor.onCursorMove { cursorMoved() }
  }

  def charTyped(c) {
    textEditor.charTyped(c)
  }

  def actionTyped(k) {
    def a = actions[k]
    def c = cursorMoves[k]
    if (a) {
      a.call()
      changed()
    } else if (c) {
      c.call()
      cursorMoved()
    } else {
      textEditor.actionTyped(k)
    }
  }

  def onChange(callback) {
    on('change', callback)
  }

  def changed() {
    emit('change')
  }

  def onCursorMove(callback) {
    on('cursorMove', callback)
  }

  def cursorMoved() {
    emit('cursorMove')
  }

}
