package no.advide

import no.advide.ui.KeyInterpreter

class PageEditor {

  Page page
  TextEditor textEditor

  PageEditor(KeyInterpreter keys) {
    initTextEditor()
    keys.addListener(this)
  }

  void kickstart(Page startingPage, changeCallback) {
    setPage(startingPage)
    onChange(changeCallback)
    changeCallback.call(page)
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
    textEditor.actionTyped(k)
  }

  def changeCallbacks = []

  def onChange(callback) {
    changeCallbacks << callback
  }

  def changed() {
    changeCallbacks.each { it.call page }
  }

}
