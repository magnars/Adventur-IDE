package no.advide.commands

import no.advide.DocumentFragment

class ContinueCommandTest extends GroovyTestCase {

  void test_should_match_old_style() {
    assert ContinueCommand.matches(["!!!"], 0)
    assert !ContinueCommand.matches(["noe annet", "!!!"], 0)
  }

  void test_should_match_new_style() {
    assert ContinueCommand.matches(["noe annet", "-- fortsett --"], 1)
  }

  void test_should_match_one_line() {
    assert ContinueCommand.numMatchingLines(["", "!!!", "!!!"], 1) == 1
  }

  void test_should_render_with_separator_line() {
    def command = new ContinueCommand([ getLines: {["!!!"]} ] as DocumentFragment)
    assert command.formattedLines.first().hasSeparatorLine
  }

  void test_should_convert_to_old_script() {
    def command = new ContinueCommand([ getLines: {["-- fortsett --"]} ] as DocumentFragment)
    assert command.toOldScript() == ["!!!"]
  }

  void test_should_convert_to_new_script() {
    def command = new ContinueCommand([ getLines: {["!!!"]} ] as DocumentFragment)
    assert command.toNewScript() == ["-- fortsett --"]
  }

}
