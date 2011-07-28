package no.advide.commands

import no.advide.Cursor

class CommandParserTest extends GroovyTestCase {

  void test_should_convert_strings_to_command_list() {
    def commands = new CommandParser(["", "", ""], null).parse()
    assert commands.size() == 3
  }

  void test_should_parse_UnknownCommand() {
    def commands = new CommandParser([":h"], null).parse()
    assert commands.first().class == UnknownCommand.class
    assert commands.first().input == ":h"
  }

  void test_should_parse_RemoveAlternativeCommand() {
    def commands = new CommandParser(["#100"], null).parse()
    assert commands.first().class == RemoveAlternativeCommand.class
  }

  void test_should_parse_ProseCommand() {
    def commands = new CommandParser(["hei", "du"], null).parse()
    assert commands.first().class == ProseCommand.class
    assert commands.first().toNewScript() == ["hei du"]
  }

  void test_should_give_cursor_to_active_command() {
    def cursor = new Cursor(x: 0, y: 1)
    def commands = new CommandParser(["#100", "hei", "du", "der"], cursor).parse()
    assert !commands.first().active
    assert commands.last().active
    assert commands.last().localCursorY == 0
  }

}
