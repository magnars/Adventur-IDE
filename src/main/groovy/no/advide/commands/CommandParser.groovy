package no.advide.commands

class CommandParser {
  static CommandList parse(List<String> strings) {
    def commands = new CommandList();
    strings.each {s -> commands << new UnknownCommand(s)}
    return commands
  }
}
