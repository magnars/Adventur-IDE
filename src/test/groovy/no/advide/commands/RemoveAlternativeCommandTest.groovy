package no.advide.commands

class RemoveAlternativeCommandTest extends GroovyTestCase {

  void test_should_match_starting_hash_with_numbers() {
    assert RemoveAlternativeCommand.matches(["", "#17", ""], 1)
  }

  void test_should_not_match_nonnumerical() {
    assert !RemoveAlternativeCommand.matches(["", "#SAVE#", ""], 1)
  }

  void test_should_return_formatted_line() {
    def lines = new RemoveAlternativeCommand(["#17"]).formattedLines
    assertEquals 1, lines.size()
    assertEquals "#17", lines.first().text
  }

  void test_should_convert_to_newScript() {
    assert ["#23"] == new RemoveAlternativeCommand(["#23"]).toNewScript()
  }

}
