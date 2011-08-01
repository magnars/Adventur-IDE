package no.advide

import no.advide.commands.CommandList
import no.advide.commands.CommandParser

class Page {
  String name
  File file
  Document document
  CommandList commands

  Page(String name, File file) {
    this.name = name
    this.file = file
    document = new Document(file.readLines('UTF-8'), [x: 0, y: 0])
    document.stripTrailingSpaces()
    parseCommands()
  }

  void parseCommands() {
    commands = new CommandParser(document).parse()
  }


}
