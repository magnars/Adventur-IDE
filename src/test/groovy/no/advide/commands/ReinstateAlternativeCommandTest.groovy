package no.advide.commands

import no.advide.AdventureTest
import no.advide.Document
import no.advide.DocumentFragment

class ReinstateAlternativeCommandTest extends GroovyTestCase {

  Command command

  void setUp() {
    AdventureTest.setUpCurrent()
  }

  void setUpCommand(line) {
    command = new ReinstateAlternativeCommand(createFragment([line]))
  }

  DocumentFragment createFragment(List lines) {
    new Document(lines, [x:0, y:0]).createFragment([x:0, y:0], lines.size())
  }

  void test_should_match_old_form() {
    assert ReinstateAlternativeCommand.matches(createFragment(["*17", ""]))
  }

  void test_should_not_match_nonnumerical() {
    assert !ReinstateAlternativeCommand.matches(createFragment(["* En punktliste", ""]))
  }

  void test_should_match_one_line() {
    assert ReinstateAlternativeCommand.numMatchingLines(createFragment(["*17", "*23"])) == 1
  }

  void test_should_return_room_number_in_old_form() {
    setUpCommand("*123")
    assert command.roomNumbers.size() == 1
    assert command.roomNumbers.first().number == 123
    assert command.roomNumbers.first().position == [x:1, y:0]
  }

}
