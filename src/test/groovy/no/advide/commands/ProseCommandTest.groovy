package no.advide.commands

import java.awt.Color

class ProseCommandTest extends GroovyTestCase {

  void test_should_match_lines_starting_with_letters() {
    assert ProseCommand.matches(["", "", "Hei"], 2)
    assert !ProseCommand.matches(["", "#12", ""], 1)
  }

  void test_should_request_all_continuous_lines_of_prose() {
    assert ProseCommand.numMatchingLines(["", "Hei", "på", "deg", "!!!"], 1) == 3
    assert ProseCommand.numMatchingLines(["Hei", "du"], 0) == 2
  }

  void test_should_concatenate_lines() {
    def command = new ProseCommand(["Hei", "du"])
    assert command.toNewScript() == ["Hei du"]
  }

  void test_should_wrap_words() {
    def command = new ProseCommand(["Hei", "du,", "så fin", "hatt"])
    command.setWidth(10)
    assert command.toNewScript() == ["Hei du, så", "fin hatt"]
  }

  void test_should_get_formatted_lines() {
    def command = new ProseCommand(["Hei"])
    assert command.formattedLines.size() == 1
    assert command.formattedLines.first().text == "Hei"
    assert command.formattedLines.first().color == Color.black
  }

}
