package no.advide

class PageEditor extends EventEmitter {

  Page page
  TextEditor textEditor

  def actions = [
      "tab":            { if (page.nextRoomNumber) page.document.cursor = page.nextRoomNumber.position },
      "shift+tab":      { if (page.previousRoomNumber) page.document.cursor = page.previousRoomNumber.position },
      "ctrl+alt+cmd+O": { page.commands.each { it.replaceWithOldStyle() } },
      "ctrl+alt+cmd+N": { page.commands.each { it.replaceWithNewStyle() } }
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
