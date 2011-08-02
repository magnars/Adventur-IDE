package no.advide.commands

import no.advide.Document

class CommandParserTest extends GroovyTestCase {

  def commands
  
  void setUpCommands(lines, cursor) {
    def document = new Document(lines, cursor)
    commands = new CommandParser(document).parse()
  }
  
  void test_should_convert_strings_to_command_list() {
    setUpCommands(["", "", ""], null)
    assert commands.size() == 3
  }

  void test_should_parse_UnknownCommand() {
    setUpCommands([":h"], null)
    assert commands.first().class == UnknownCommand.class
  }

  void test_should_parse_RemoveAlternativeCommand() {
    setUpCommands(["#100"], null)
    assert commands.first().class == RemoveAlternativeCommand.class
  }

  void test_should_parse_ProseCommand() {
    setUpCommands(["hei", "du"], null)
    assert commands.first().class == ProseCommand.class
  }

  void test_should_parse_ContinueCommand() {
    setUpCommands(["!!!"], null)
    assert commands.first().class == ContinueCommand.class
  }

  void test_should_parse_ReinstateAlternativeCommand() {
    setUpCommands(["*123"], null)
    assert commands.first().class == ReinstateAlternativeCommand.class
  }

}
