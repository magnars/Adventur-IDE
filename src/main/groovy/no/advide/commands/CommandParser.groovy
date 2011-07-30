package no.advide.commands

import no.advide.Cursor
import no.advide.Document

class CommandParser {

  static List commandTypes = [
      ProseCommand.class,
      RemoveAlternativeCommand.class,
      UnknownCommand.class
  ]

  List<String> strings
  CommandList commands
  Document document
  int index

  CommandParser(Document document) {
    this.strings = document.lines
    this.document = document
    commands = new CommandList()
    index = 0
  }

  CommandList parse() {
    while (index < strings.size()) {
      commands << findMatchingCommand()
    }
    return commands
  }

  private Command findMatchingCommand() {
    for (type in commandTypes) {
      if (type.matches(strings, index)) {
        return createCommand(type)
      }
    }
    throw new IllegalStateException("no matching commands")
  }

  private Command createCommand(type) {
    def numLines = type.numMatchingLines(strings, index)
    Command command = type.newInstance(nextLines(numLines))
    index += numLines
    return command
  }

  private List<String> nextLines(numLines) {
    return strings[index..(index + numLines - 1)]
  }

}
