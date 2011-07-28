package no.advide.commands

class CommandParserTest extends GroovyTestCase {

  void test_should_convert_strings_to_command_list() {
    def commands = CommandParser.parse(["", "", ""])
    assertEquals 3, commands.size()
  }

}
