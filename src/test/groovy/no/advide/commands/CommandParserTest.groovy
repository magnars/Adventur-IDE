package no.advide.commands

class CommandParserTest extends GroovyTestCase {

  void test_should_convert_strings_to_command_list() {
    def commands = new CommandParser(["", "", ""]).parse()
    assertEquals 3, commands.size()
  }

  void test_should_parse_UnknownCommand() {
    def commands = new CommandParser([":h"]).parse()
    assertEquals UnknownCommand.class, commands.first().class
    assertEquals ":h", commands.first().input
  }

  void test_should_parse_RemoveAlternativeCommand() {
    def commands = new CommandParser(["#100"]).parse()
    assertEquals RemoveAlternativeCommand.class, commands.first().class
  }

}
