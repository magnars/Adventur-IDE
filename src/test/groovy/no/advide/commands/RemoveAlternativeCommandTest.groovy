package no.advide.commands

import no.advide.AdventureTest
import no.advide.Document
import no.advide.DocumentFragment

class RemoveAlternativeCommandTest extends GroovyTestCase {

  def command

  void setUp() {
    AdventureTest.setUpCurrent()
  }

  DocumentFragment createFragment(List lines) {
    new Document(lines, [x:0, y:0]).createFragment(0, lines.size())
  }

  void setUpCommand(line) {
    command = new RemoveAlternativeCommand(createFragment([line]))
  }

  void test_should_match_starting_hash_with_numbers() {
    assert RemoveAlternativeCommand.matches(createFragment(["#17", ""]))
  }

  void test_should_not_match_nonnumerical() {
    assert !RemoveAlternativeCommand.matches(createFragment(["#SAVE#", ""]))
  }

  void test_should_match_one_line() {
    assert RemoveAlternativeCommand.numMatchingLines(createFragment(["#17", "#23"])) == 1
  }

  void test_should_return_room_number() {
    setUpCommand("#123")
    assert command.roomNumbers.size() == 1
    assert command.roomNumbers.first().number == 123
    assert command.roomNumbers.first().position == [x:1, y:0]
  }

}
