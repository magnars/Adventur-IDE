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

  void test_get_alternatives() {
    def command = new AlternativeCommand(createFragment(["-", "2", "Alt 1", "16", "Alt 2", "17"]))
    assert command.alternatives.size() == 2

    assert command.alternatives.first().index == 2
    assert command.alternatives.first().number == 1
    assert command.alternatives.first().text == "Alt 1"
    assert command.alternatives.first().room == "16"
    assert command.alternatives.first().requirement == "-"

    assert command.alternatives.last().index == 4
    assert command.alternatives.last().number == 2
    assert command.alternatives.last().text == "Alt 2"
    assert command.alternatives.last().room == "17"
    assert command.alternatives.last().requirement == "-"
  }

  void test_get_alternatives_with_requirements() {
    def command = new AlternativeCommand(createFragment(["+", "2", "Alt 1", "16", "-", "Alt 2", "17", "KRAV"]))
    assert command.alternatives.size() == 2

    assert command.alternatives.first().index == 2
    assert command.alternatives.first().number == 1
    assert command.alternatives.first().text == "Alt 1"
    assert command.alternatives.first().room == "16"
    assert command.alternatives.first().requirement == "-"

    assert command.alternatives.last().index == 5
    assert command.alternatives.last().number == 2
    assert command.alternatives.last().text == "Alt 2"
    assert command.alternatives.last().room == "17"
    assert command.alternatives.last().requirement == "KRAV"
  }

  // ---------------- new style ---------------------------------------------

  void test_should_convert_to_new_style() {
    def command = new AlternativeCommand(createFragment(["-", "2", "Alt 1", "13", "Alt 2", "14"]))
    assert command.toNewStyle() == ["--", "Alt 1", "13", "Alt 2", "14"]
  }

  void test_should_convert_to_old_style() {
    def command = new AlternativeCommand(createFragment(["--", "Alt 1", "13", "Alt 2", "14"]))
    assert command.toOldStyle() == ["-", "2", "Alt 1", "13", "Alt 2", "14"]
  }

  void test_should_convert_to_new_style_with_requirements() {
    def command = new AlternativeCommand(createFragment(["+", "2", "Alt 1", "13", "-", "Alt 2", "14", "KRAV"]))
    assert command.toNewStyle() == ["--", "Alt 1", "13", "Alt 2", "14 ? KRAV"]
  }

  void test_should_convert_to_old_style_with_requirements() {
    def command = new AlternativeCommand(createFragment(["--", "Alt 1", "13", "Alt 2", "14 ? KRAV"]))
    assert command.toOldStyle() == ["+", "2", "Alt 1", "13", "-", "Alt 2", "14", "KRAV"]
  }

  void test_should_match_new_style() {
    assert AlternativeCommand.matches(createFragment(["--"]))
    assert !AlternativeCommand.matches(createFragment(["---"]))
  }

  void test_should_get_alternatives_new_style() {
    def command = new AlternativeCommand(createFragment(["--", "Alt 1", "16", "Alt 2", "17 ? KRAV"]))
    assert command.alternatives.size() == 2

    assert command.alternatives.first().index == 1
    assert command.alternatives.first().number == 1
    assert command.alternatives.first().text == "Alt 1"
    assert command.alternatives.first().room == "16"
    assert command.alternatives.first().requirement == "-"

    assert command.alternatives.last().index == 3
    assert command.alternatives.last().number == 2
    assert command.alternatives.last().text == "Alt 2"
    assert command.alternatives.last().room == "17"
    assert command.alternatives.last().requirement == "KRAV"
  }

  void test_should_format_new_style_properly() {
    def command = new AlternativeCommand(createFragment(["--", "Alt 1", "16", "Alt 2", "17 ? KRAV"]))
    assert command.formattedLines.size() == 5
    assert command.formattedLines[1].prefix == "1. "
    assert command.formattedLines[2].prefix == "--> "
    assert command.formattedLines[3].prefix == "2. "
    assert command.formattedLines[4].prefix == "--> "
  }

  void test_should_indent_numbers_if_more_than_9() {
    def command = new AlternativeCommand(createFragment(["--",
        "Alt 1", "1",
        "Alt 2", "2",
        "Alt 3", "3",
        "Alt 4", "4",
        "Alt 5", "5",
        "Alt 6", "6",
        "Alt 7", "7",
        "Alt 8", "8",
        "Alt 9", "9",
        "Alt 10", "10"
    ]))
    assert command.formattedLines.size() == 21
    assert command.formattedLines[1].prefix  == " 1. "
    assert command.formattedLines[2].prefix  == "--> "
    assert command.formattedLines[3].prefix  == " 2. "
    assert command.formattedLines[4].prefix  == "--> "
    assert command.formattedLines[19].prefix == "10. "
    assert command.formattedLines[20].prefix == "--> "
  }

}
