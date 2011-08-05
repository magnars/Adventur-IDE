package no.advide.commands

import no.advide.Document
import no.advide.DocumentFragment

class CommandParser {

  static List commandTypes = [
      ConditionalCommand,
      ReinstateAlternativeCommand,
      ContinueCommand,
      ProseCommand,
      RemoveAlternativeCommand,
      UnknownCommand
  ]

  List<String> strings
  CommandList commands
  DocumentFragment document
  int index

  CommandParser(Document doc) {
    this(doc.createFragment([x:0, y:0], doc.lines.size()))
  }

  CommandParser(DocumentFragment doc) {
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
    def fragment = document.createFragment([x:0, y:index], strings.size() - index)
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
