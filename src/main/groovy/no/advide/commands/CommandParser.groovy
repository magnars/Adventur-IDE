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
    if (RemoveAlternativeCommand.matches(strings, index)) { return new RemoveAlternativeCommand([strings[index++]]) }
    new UnknownCommand(strings[index++])
  }

}
