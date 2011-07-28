package no.advide.commands

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

}
