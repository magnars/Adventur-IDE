package no.advide.commands

import java.awt.Color
import no.advide.Document
import no.advide.DocumentFragment

class ProseCommandTest extends GroovyTestCase {

  ProseCommand command

  DocumentFragment createFragment(List lines) {
    new Document(lines, [x:0, y:0]).createFragment([x:0, y:0], lines.size())
  }

  void setUpCommand(List lines) {
    command = new ProseCommand(createFragment(lines))
  }

  void test_should_match_lines_starting_with_letters() {
    assert ProseCommand.matches(createFragment(["Hei"]))
    assert !ProseCommand.matches(createFragment(["#12"]))
  }

  void test_should_match_lines_starting_with_quote() {
    assert ProseCommand.matches(createFragment(['"Hei']))
    assert !ProseCommand.matches(createFragment(['"']))
  }

  void test_should_match_lines_starting_with_numbers() {
    assert ProseCommand.matches(createFragment(['13 baller']))
    assert !ProseCommand.matches(createFragment(['13']))
  }

  void test_should_request_all_continuous_lines_of_prose() {
    assert ProseCommand.numMatchingLines(createFragment(["Hei", "på", "deg", "!!!"])) == 3
    assert ProseCommand.numMatchingLines(createFragment(["Hei", "du"])) == 2
  }

  void test_should_stop_when_cursor_on_next_line() {
    def fragment = createFragment(["Hei", "på", "deg", "!!!"])
    fragment.document.cursor = [x:1, y:2]
    assert ProseCommand.numMatchingLines(fragment) == 2
  }

  void test_should_color_lines_black() {
    setUpCommand(["Hei"])
    assert command.formattedLines.first().color == Color.black
  }

  void test_should_optimize_document() {
    setUpCommand(["Hei", "på du"])
    command.justifyProse(7)
    assert command.fragment.lines == ["Hei på", "du"]
  }

}
