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

  CommandParser(Document document) {
    this.document = document
    strings = document.lines
    commands = new CommandList()
    index = 0
    document.clearFragments()
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
    int numLines = type.numMatchingLines(strings, index)
    Command command = type.newInstance(document.createFragment(index, numLines))
    index += numLines
    return command
  }

}
