package no.advide

class PageEditor extends EventEmitter {

  Page page
  TextEditor textEditor

  def actions = [
      "tab":            { page.moveCursorTo page.nextRoomNumber },
      "shift+tab":      { page.moveCursorTo page.previousRoomNumber },
      "cmd+F":          { page.nextFix?.fix() },
      "ctrl+alt+cmd+O": { page.changeToOldStyle() },
      "ctrl+alt+cmd+N": { page.changeToNewStyle() }
  ]

  PageEditor(Page page) {
    this.page = page
    textEditor = new TextEditor(page.document)
    textEditor.onChange { changed() }
  }

  def charTyped(c) {
    textEditor.charTyped(c)
  }

  def actionTyped(k) {
    def a = actions[k]
    if (a) {
      a.call()
      changed()
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

}
