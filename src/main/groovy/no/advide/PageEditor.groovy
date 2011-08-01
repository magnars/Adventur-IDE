package no.advide

import no.advide.commands.CommandParser
import no.advide.commands.CommandList
import no.advide.commands.ProseCommand

class PageEditor {

  Page page
  TextEditor textEditor

  PageEditor() {
    textEditor = new TextEditor()
    textEditor.onChange {
      page.parseCommands()
      justifyWordsInProse()
      changed()
    }
  }

  void setPage(Page page) {
    this.page = page
    textEditor.setDocument(page.document)
  }

  void justifyWordsInProse() {
    page.commands.getAll(ProseCommand).each { new WordWrapper(it.fragment).justify() }
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
