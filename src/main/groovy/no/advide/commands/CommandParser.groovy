package no.advide.commands

import no.advide.Document

class CommandParser {

  static List commandTypes = [
      ReinstateAlternativeCommand,
      ContinueCommand,
      ProseCommand,
      RemoveAlternativeCommand,
      UnknownCommand
  ]

  List<String> strings
  CommandList commands
  Document document
  int index

  CommandParser(Document doc) {
    document = doc
    strings = document.lines
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
    def fragment = document.createFragment(index, strings.size() - index)
    for (type in commandTypes) {
      if (type.matches(fragment)) {
        return createCommand(type, fragment)
      }
    }
    throw new IllegalStateException("no matching commands")
  }

  private Command createCommand(type, fragment) {
    int numLines = type.numMatchingLines(fragment)
    fragment.length = numLines
    Command command = type.newInstance(fragment)
    index += numLines
    return command
  }

}
