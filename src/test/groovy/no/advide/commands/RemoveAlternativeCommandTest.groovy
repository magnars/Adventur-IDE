package no.advide.commands

import java.awt.Color
import no.advide.AdventureTest
import no.advide.Document

class RemoveAlternativeCommandTest extends GroovyTestCase {

  def command

  void setUp() {
    AdventureTest.setUpCurrent()
  }

  void setUpCommand(line) {
    def document = new Document([line], [x:0, y:0])
    command = new RemoveAlternativeCommand(document.createFragment(0, 1))
  }

  void test_should_match_starting_hash_with_numbers() {
    assert RemoveAlternativeCommand.matches(["", "#17", ""], 1)
  }

  void test_should_not_match_nonnumerical() {
    assert !RemoveAlternativeCommand.matches(["", "#SAVE#", ""], 1)
  }

  void test_should_return_formatted_line() {
    setUpCommand("#0")
    assert command.formattedLines.size() == 1
    assert command.formattedLines.first().text == "#0"
  }

  void test_should_color_red_if_does_not_exists() {
    setUpCommand("#17")
    assert command.formattedLines.first().changes[1].changeColor == Color.red
  }

  void test_should_convert_to_newScript() {
    setUpCommand("#23")
    assert command.toNewScript() == ["#23"]
  }

}
