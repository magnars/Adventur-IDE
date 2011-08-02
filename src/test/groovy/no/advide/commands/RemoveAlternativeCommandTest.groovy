package no.advide.commands

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

  void test_should_match_one_line() {
    assert RemoveAlternativeCommand.numMatchingLines(["", "#17", "#23"], 1) == 1
  }

  void test_should_return_room_number() {
    setUpCommand("#123")
    assert command.roomNumbers.size() == 1
    assert command.roomNumbers.first().number == 123
    assert command.roomNumbers.first().position == [x:1, y:0]
  }

}
