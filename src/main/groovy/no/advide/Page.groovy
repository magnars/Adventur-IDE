package no.advide

import no.advide.commands.CommandList
import no.advide.commands.CommandParser
import no.advide.commands.ProseCommand

class Page {
  String name
  File file
  Document document
  CommandList commands

  Page() {}

  Page(String name, File file) {
    this.name = name
    this.file = file
    document = new Document(file.readLines('UTF-8'), [x: 0, y: 0])
    document.stripTrailingSpaces()
    updateCommands()
  }

  void updateCommands() {
    commands = new CommandParser(document).parse()
    justifyWordsInProse() // er denne riktig her? den har blitt flyttet mye, ser ok ut her enn så lenge
  }

  void justifyWordsInProse() {
    commands.getAll(ProseCommand).each { new WordWrapper(it.fragment).justify() }
  }

  void save() {
    file.setText(commands.toOldScript().join("\n"), 'UTF-8')
  }

}
