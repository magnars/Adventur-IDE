package no.advide.commands

import no.advide.Cursor

class CommandParser {

  static List commandTypes = [
      ProseCommand.class,
      RemoveAlternativeCommand.class,
      UnknownCommand.class
  ]

  List<String> strings
  CommandList commands
  Cursor cursor
  int index

  CommandParser(List<String> strings, Cursor cursor) {
    this.strings = strings
    this.cursor = cursor
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
    def startingIndex = index
    Command command = type.newInstance(popStrings(type.numMatchingLines(strings, index)))
    if (cursor && index > cursor.y) {
      command.setCursor(cursor, cursor.y - startingIndex)
      cursor = null
    }
    return command
  }

  private List<String> popStrings(num) {
    (1..num).collect { strings[index++] }
  }

}
