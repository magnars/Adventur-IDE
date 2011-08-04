package no.advide.commands

import no.advide.Document
import no.advide.DocumentFragment

class ConditionalCommandTest extends GroovyTestCase {

  DocumentFragment createFragment(List lines) {
    new Document(lines, [x:0, y:0]).createFragment(0, lines.size())
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

  void test_should_emboss_entire_block() {
    def command = new ConditionalCommand(createFragment(["[!]KRAV", "{", "abc", "}"]))
    assert command.formattedLines.first().isEmbossedTop
    assert command.formattedLines[0].isEmbossed
    assert command.formattedLines[1].isEmbossed
    assert command.formattedLines[2].isEmbossed
    assert command.formattedLines[3].isEmbossed
    assert command.formattedLines.last().isEmbossedBottom
  }

}
