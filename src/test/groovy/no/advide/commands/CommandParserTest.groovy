package no.advide.commands

class CommandParserTest extends GroovyTestCase {

  void test_should_convert_strings_to_command_list() {
    def commands = new CommandParser(["", "", ""]).parse()
    assert commands.size() == 3
  }

  void test_should_parse_UnknownCommand() {
    def commands = new CommandParser([":h"]).parse()
    assert commands.first().class == UnknownCommand.class
    assert commands.first().input == ":h"
  }

  void test_should_parse_RemoveAlternativeCommand() {
    def commands = new CommandParser(["#100"]).parse()
    assert commands.first().class == RemoveAlternativeCommand.class
  }

  void test_should_parse_ProseCommand() {
    def commands = new CommandParser(["hei", "du"]).parse()
    assert commands.first().class == ProseCommand.class
  }

}
