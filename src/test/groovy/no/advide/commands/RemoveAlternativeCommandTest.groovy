package no.advide.commands

import no.advide.AdventureTest
import java.awt.Color

class RemoveAlternativeCommandTest extends GroovyTestCase {

  void setUp() {
    AdventureTest.setUpPath()
  }

  void test_should_match_starting_hash_with_numbers() {
    assert RemoveAlternativeCommand.matches(["", "#17", ""], 1)
  }

  void test_should_not_match_nonnumerical() {
    assert !RemoveAlternativeCommand.matches(["", "#SAVE#", ""], 1)
  }

  void test_should_return_formatted_line() {
    def lines = new RemoveAlternativeCommand(["#0"]).formattedLines
    assert lines.size() == 1
    assert lines.first().text == "#0"
  }

  void test_should_color_red_if_does_not_exists() {
    def lines = new RemoveAlternativeCommand(["#17"]).formattedLines
    assert lines.first().color == Color.red
  }

  void test_should_convert_to_newScript() {
    assert new RemoveAlternativeCommand(["#23"]).toNewScript() == ["#23"]
  }

}
