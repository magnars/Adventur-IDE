package no.advide.commands

class CommandParser {

  static List commandTypes = [
      RemoveAlternativeCommand.class,
      UnknownCommand.class
  ]

  List<String> strings
  CommandList commands
  int index

  CommandParser(List<String> strings) {
    this.strings = strings
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
      if (type.matches(strings, index)) { return type.newInstance(popStrings(type.numMatchingLines(strings, index))) }
    }
    throw new IllegalStateException("no matching commands")
  }

  private List<String> popStrings(num) {
    (1..num).collect { strings[index++] }
  }

}
