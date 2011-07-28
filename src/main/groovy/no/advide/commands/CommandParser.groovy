package no.advide.commands

class CommandParser {

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
    if (RemoveAlternativeCommand.matches(strings, index)) { return new RemoveAlternativeCommand(popStrings(RemoveAlternativeCommand.numMatchingLines(strings, index))) }
    if (UnknownCommand.matches(strings, index)) { return new UnknownCommand(popStrings(UnknownCommand.numMatchingLines(strings, index))) }
    throw new IllegalStateException("no matching commands")
  }

  private List<String> popStrings(num) {
    (1..num).collect { strings[index++] }
  }

}
