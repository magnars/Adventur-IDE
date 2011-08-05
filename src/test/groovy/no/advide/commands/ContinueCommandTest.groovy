package no.advide.commands

import no.advide.Document
import no.advide.DocumentFragment

class ContinueCommandTest extends GroovyTestCase {

  DocumentFragment createFragment(List lines) {
    new Document(lines, [x:0, y:0]).createFragment([x:0, y:0], lines.size())
  }

  void test_should_match_old_style() {
    assert ContinueCommand.matches(createFragment(["!!!"]))
    assert !ContinueCommand.matches(createFragment(["noe annet", "!!!"]))
  }

  void test_should_match_new_style() {
    assert ContinueCommand.matches(createFragment(["-- fortsett --"]))
  }

  void test_should_match_one_line() {
    assert ContinueCommand.numMatchingLines(createFragment(["!!!", "!!!"])) == 1
  }

  void test_should_render_with_separator_line() {
    def command = new ContinueCommand(createFragment(["!!!"]))
    assert command.formattedLines.first().hasSeparatorLine
  }

  void test_should_convert_to_old_script() {
    def command = new ContinueCommand(createFragment(["-- fortsett --"]))
    assert command.toOldStyle() == ["!!!"]
  }

  void test_should_convert_to_new_script() {
    def command = new ContinueCommand(createFragment(["!!!"]))
    assert command.toNewStyle() == ["-- fortsett --"]
  }

}
