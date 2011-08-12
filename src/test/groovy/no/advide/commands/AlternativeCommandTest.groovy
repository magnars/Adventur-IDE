package no.advide.commands

import no.advide.Document
import no.advide.DocumentFragment

class AlternativeCommandTest extends GroovyTestCase {

  DocumentFragment createFragment(List lines) {
    new Document(lines, [x:0, y:0]).createFragment([x:0, y:0], lines.size())
  }

  void test_should_match_old_form() {
    assert AlternativeCommand.matches(createFragment(["-", "2", "Alt 1", "13", "Alt 2", "14"]))
    assert AlternativeCommand.matches(createFragment(["+", "2", "Alt 1", "13", "-", "Alt 2", "14", "KRAV"]))
    assert !AlternativeCommand.matches(createFragment(["---", "en slemming"]))
  }

  void test_should_only_match_old_form_with_number_specified() {
    assert !AlternativeCommand.matches(createFragment(["-", ""]))
    assert !AlternativeCommand.matches(createFragment(["-", "2 kaniner står og preiker"]))
  }

  void test_should_request_rest_of_lines() {
    assert AlternativeCommand.numMatchingLines(createFragment(["-", "1", "", "", "", ""])) == 6
    assert AlternativeCommand.numMatchingLines(createFragment(["+", "2", "Alt 1", "13", "-", "Alt 2", "14", "KRAV"])) == 8
  }

  void test_should_return_roomNumbers_for_old_form() {
    def command = new AlternativeCommand(createFragment(["-", "2", "Alt 1", "13", "Alt 2", "14"]))
    assert command.roomNumbers.size() == 2
    assert command.roomNumbers.first().number == 13
    assert command.roomNumbers.first().position == [x:0, y:3]
    assert command.roomNumbers.last().number == 14
    assert command.roomNumbers.last().position == [x:0, y:5]
  }

  void test_should_not_stumble_over_non_numbers() {
    def command = new AlternativeCommand(createFragment(["-", "2", "Alt 1", "Safran", "Alt 2", "14"]))
    assert command.roomNumbers.size() == 1
    assert command.roomNumbers.first().number == 14
    assert command.roomNumbers.first().position == [x:0, y:5]
  }

  void test_should_not_stumble_over_unfinished_alternatives() {
    def command = new AlternativeCommand(createFragment(["-", "2", "Alt 1"]))
    assert command.roomNumbers.size() == 0
    assert command.formattedLines.size() == 3
  }

  void test_should_return_roomNumbers_for_old_form_with_requirements() {
    def command = new AlternativeCommand(createFragment(["+", "2", "Alt 1", "16", "-", "Alt 2", "17", "KRAV"]))
    assert command.roomNumbers.size() == 2
    assert command.roomNumbers.first().number == 16
    assert command.roomNumbers.first().position == [x:0, y:3]
    assert command.roomNumbers.last().number == 17
    assert command.roomNumbers.last().position == [x:0, y:6]
  }

  void test_should_format_old_alternatives_correctly() {
    def command = new AlternativeCommand(createFragment(["+", "2", "Alt 1", "16", "-", "Alt 2", "17", "KRAV"]))
    assert command.formattedLines.size() == 8
    assert command.formattedLines[1].prefix == "Antall alternativer: "
    assert command.formattedLines[2].prefix == "1. "
    assert command.formattedLines[3].prefix == "Rom#: "
    assert command.formattedLines[4].prefix == "Krav: "
    assert command.formattedLines[5].prefix == "2. "
    assert command.formattedLines[6].prefix == "Rom#: "
    assert command.formattedLines[7].prefix == "Krav: "
  }

}
