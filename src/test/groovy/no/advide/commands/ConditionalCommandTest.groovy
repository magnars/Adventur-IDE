package no.advide.commands

import java.awt.Color
import no.advide.Document
import no.advide.DocumentFragment

class ConditionalCommandTest extends GroovyTestCase {

  DocumentFragment createFragment(List lines) {
    new Document(lines, [x:0, y:0]).createFragment([x:0, y:0], lines.size())
  }

  void test_should_match_old_form() {
    assert ConditionalCommand.matches(createFragment(["[!]KRAV", "abc", "def"]))
    assert !ConditionalCommand.matches(createFragment(["[X!]4", ""]))
  }

  void test_should_match_two_lines_without_brackets() {
    assert ConditionalCommand.numMatchingLines(createFragment(["[!]KRAV", "abc", "def"])) == 2
  }

  void test_should_match_entire_block_with_brackets() {
    assert ConditionalCommand.numMatchingLines(createFragment(["[!]KRAV", "{", "abc", "def", "}", "outside"])) == 5
  }

  void test_should_not_get_confused_by_nested_blocks() {
    assert ConditionalCommand.numMatchingLines(createFragment(["[!]KRAV", "{", "[!]TMP", "{", "abc", "}", "def", "}", "outside"])) == 8
  }

  void test_should_get_room_numbers_from_subcommands() {
    def command = new ConditionalCommand(createFragment(["[!]KRAV", "#123"]))
    assert command.roomNumbers.size() == 1
  }

  void test_should_color_brackets_gray() {
    def command = new ConditionalCommand(createFragment(["[!]KRAV", "{", "abc", "}"]))
    assert command.formattedLines[1].text == "{"
    assert command.formattedLines[1].color == Color.gray
    assert command.formattedLines[3].text == "}"
    assert command.formattedLines[3].color == Color.gray
  }

  void test_should_emboss_entire_block() {
    def command = new ConditionalCommand(createFragment(["[!]KRAV", "{", "abc", "}"]))
    assert command.formattedLines.first().isEmbossedTop
    assert command.formattedLines[0].isEmbossed
    assert command.formattedLines[1].isEmbossed
    assert command.formattedLines[2].isEmbossed
    assert command.formattedLines[3].isEmbossed
    assert command.formattedLines.last().isEmbossedBottom
  }

  // New form //////////////////////////////////////////////////////////////////////////////////////////////////////////

  void test_should_match_new_form() {
    assert ConditionalCommand.matches(createFragment(["? KRAV", "  abc", "def"]))
    assert !ConditionalCommand.matches(createFragment(["@12 ? KRAV", ""]))
  }

  void test_should_match_indented_lines() {
    assert ConditionalCommand.numMatchingLines(createFragment(["? KRAV", "  abc", "def"])) == 2
    assert ConditionalCommand.numMatchingLines(createFragment(["? KRAV", "  abc", "  def"])) == 3
  }

  void test_should_get_room_numbers_from_subcommands_in_new_form_too() {
    def command = new ConditionalCommand(createFragment(["? KRAV", "  #123"]))
    assert command.roomNumbers.size() == 1
    assert command.roomNumbers.first().number == 123
    assert command.roomNumbers.first().position == [x:3, y:1]
  }

  void test_should_keep_indentation_in_new_form() {
    def command = new ConditionalCommand(createFragment(["? KRAV", "  abc", "  #123"]))
    assert command.formattedLines.first().text == "? KRAV"
    assert command.formattedLines.last().text == "  #123"
  }

  void test_should_accept_new_form_without_commands() {
    def command = new ConditionalCommand(createFragment(["? KRAV"]))
    assert command.commands.size() == 0
  }

  // Switching ////////////

  void test_should_convert_to_new_style() {
    def command = new ConditionalCommand(createFragment(["[!]KRAV", "{", "abc", "def", "*123", "}"]))
    assert command.toNewStyle() == ["? KRAV", "  abc", "  def", "  - #123"]
  }

  void test_should_convert_to_old_style() {
    def command = new ConditionalCommand(createFragment(["? KRAV", "  abc", "  - #123"]))
    assert command.toOldStyle() == ["[!]KRAV", "{", "abc", "*123", "}"]
  }

  void test_should_avoid_unneccessary_braces_in_old_style() {
    def command = new ConditionalCommand(createFragment(["? KRAV", "  - #123"]))
    assert command.toOldStyle() == ["[!]KRAV", "*123"]
  }

}
