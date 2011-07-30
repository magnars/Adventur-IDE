package no.advide.commands

import java.awt.Color
import no.advide.Document

class UnknownCommandTest extends GroovyTestCase {

  Command command

  static Command createTestCommand() {
    def document = new Document([":hm"], [x: 0, y: 0])
    return new UnknownCommand(document.createFragment(0, 1))
  }

  void setUp() {
    command = createTestCommand()
  }

  void test_line_should_match_input() {
    def lines = command.getFormattedLines()
    assert lines.size() == 1
    assert lines.first().text == ":hm"
  }

  void test_should_always_match() {
    assert UnknownCommand.matches([""], 0)
  }

  void test_should_match_one_line() {
    assert UnknownCommand.numMatchingLines(["", ""], 0) == 1
  }

  void test_should_format_lines_black() {
    assert command.formattedLines.first().color == Color.black
  }

  void test_should_convert_to_newScript() {
    assert command.toNewScript() == [":hm"]
  }

}
