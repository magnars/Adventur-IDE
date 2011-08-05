package no.advide.commands

import java.awt.Color
import no.advide.Document
import no.advide.DocumentFragment

class UnknownCommandTest extends GroovyTestCase {

  Command command

  static Command createTestCommand() {
    def document = new Document([":hm"], [x:0, y:0])
    return new UnknownCommand(document.createFragment([x:0, y:0], 1))
  }

  void setUp() {
    command = createTestCommand()
  }

  DocumentFragment createFragment(List lines) {
    new Document(lines, [x:0, y:0]).createFragment([x:0, y:0], lines.size())
  }

  void test_line_should_match_input() {
    def lines = command.getFormattedLines()
    assert lines.size() == 1
    assert lines.first().text == ":hm"
  }

  void test_should_always_match() {
    assert UnknownCommand.matches(createFragment([""]))
  }

  void test_should_match_one_line() {
    assert UnknownCommand.numMatchingLines(createFragment(["", ""])) == 1
  }

  void test_should_format_lines_grey() {
    assert command.formattedLines.first().color == Color.gray
  }

}
