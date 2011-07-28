package no.advide.commands

import java.awt.Color

class UnknownCommandTest extends GroovyTestCase {

  Command command

  void setUp() {
    command = new UnknownCommand(":hm")
  }

  void test_line_should_match_input() {
    def lines = command.getLines()
    assertEquals 1, lines.size()
    assertEquals ":hm", lines.first().text
  }

  void test_should_always_match() {
    assert UnknownCommand.matches([""], 0)
  }

  void test_should_match_one_line() {
    assertEquals 1, UnknownCommand.numMatchingLines(["", ""], 0)
  }

  void test_should_format_lines_black() {
    assertEquals Color.black, command.lines.first().color
  }

}
