package no.advide

class PageEditor {

  Page page
  TextEditor textEditor

  def actions = [
      "cmd+S":          { page.save() },
      "ctrl+alt+cmd+O": { page.commands.each { it.replaceWithOldStyle() } },
      "ctrl+alt+cmd+N": { page.commands.each { it.replaceWithNewStyle() } }
  ]

  PageEditor() {
    initTextEditor()
  }

  void kickstart(Page startingPage, changeCallback) {
    setPage(startingPage)
    onChange(changeCallback)
    changeCallback.call(startingPage)
  }

  private def initTextEditor() {
    textEditor = new TextEditor()
    textEditor.onChange {
      page.updateCommands()
      changed()
    }
  }

  void setPage(Page page) {
    this.page = page
    textEditor.setDocument(page.document)
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

  def changeCallbacks = []

  def onChange(callback) {
    changeCallbacks << callback
  }

  def changed() {
    changeCallbacks.each { it.call page }
  }

}
