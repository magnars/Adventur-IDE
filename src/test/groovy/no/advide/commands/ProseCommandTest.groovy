package no.advide.commands

import java.awt.Color
import no.advide.Document

class ProseCommandTest extends GroovyTestCase {

  def command

  void setUpCommand(List lines) {
    def document = new Document(lines, [x:0, y:0])
    command = new ProseCommand(document.createFragment(0, lines.size()))
  }

  void test_should_match_lines_starting_with_letters() {
    assert ProseCommand.matches(["", "", "Hei"], 2)
    assert !ProseCommand.matches(["", "#12", ""], 1)
  }

  void test_should_request_all_continuous_lines_of_prose() {
    assert ProseCommand.numMatchingLines(["", "Hei", "på", "deg", "!!!"], 1) == 3
    assert ProseCommand.numMatchingLines(["Hei", "du"], 0) == 2
  }

  void test_should_concatenate_lines() {
    setUpCommand(["Hei", "du"])
    assert command.toNewScript() == ["Hei du"]
  }

  void test_should_wrap_words() {
    setUpCommand(["Hei", "du,", "så fin", "hatt"])
    command.setWidth(10)
    assert command.toNewScript() == ["Hei du, så", "fin hatt"]
  }

  void test_should_get_formatted_lines() {
    setUpCommand(["Hei"])
    assert command.formattedLines.size() == 1
    assert command.formattedLines.first().text == "Hei"
    assert command.formattedLines.first().color == Color.black
  }

}
