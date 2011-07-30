package no.advide.commands

import java.awt.Color
import no.advide.Cursor

class ProseCommandTest extends GroovyTestCase {

  void test_should_match_lines_starting_with_letters() {
    assert ProseCommand.matches(["", "", "Hei"], 2)
    assert !ProseCommand.matches(["", "#12", ""], 1)
  }

  void test_should_request_all_continuous_lines_of_prose() {
    assert ProseCommand.numMatchingLines(["", "Hei", "på", "deg", "!!!"], 1) == 3
    assert ProseCommand.numMatchingLines(["Hei", "du"], 0) == 2
  }

  void test_should_concatenate_lines() {
    def command = new ProseCommand(["Hei", "du"])
    assert command.toNewScript() == ["Hei du"]
  }

  void test_should_wrap_words() {
    def command = new ProseCommand(["Hei", "du,", "så fin", "hatt"])
    command.setWidth(10)
    assert command.toNewScript() == ["Hei du, så", "fin hatt"]
  }

  void test_should_not_wrap_line_with_cursor_at_start() {
    def command = new ProseCommand(["Hei", "du"])
    command.setCursor(new Cursor(x: 0, y: 1), 1)
    assert command.toNewScript() == ["Hei", "du"]
  }

  void test_should_get_formatted_lines() {
    def command = new ProseCommand(["Hei"])
    assert command.formattedLines.size() == 1
    assert command.formattedLines.first().text == "Hei"
    assert command.formattedLines.first().color == Color.black
  }

  void test_should_not_change_cursor_position_when_nothings_changed() {
    def command = new ProseCommand((["Hei du"]))
    def cursor = new Cursor(x: 0, y: 0)
    command.setCursor(cursor, 0)
    assert cursor == new Cursor(x: 0, y: 0)
  }

  void test_should_preserve_y_even_when_local_y_is_different() {
    def command = new ProseCommand((["Hei du"]))
    def cursor = new Cursor(x: 0, y: 5)
    command.setCursor(cursor, 0)
    assert cursor == new Cursor(x: 0, y: 5)
  }

  void test_should_preserve_cursor_position_when_at_end() {
    def command = new ProseCommand((["Hei", "du", "der"]))
    def cursor = new Cursor(x: 3, y: 2)
    command.setCursor(cursor, 2)
    assert cursor == new Cursor(x: 10, y: 0)
  }

  void test_should_remove_trailing_space_when_line_wraps() {
    def command = new ProseCommand(["Hei", "du,", "så fin", "hatt"])
    command.setWidth(10) // ["Hei du, så", "fin hatt"]
    def cursor = new Cursor(x: 4, y: 3)
    command.setCursor(cursor, 3)
    assert cursor == new Cursor(x: 8, y: 1)
  }

  void test_should_create_new_line_when_space_is_in_wrapping_position() {
    def s = "Dette er ikke gøy... den krasjer jo bare ved enkelte sjeldne anledninger og ikke "
    def command = new ProseCommand([s])
    def cursor = new Cursor(x: s.size(), y: 0)
    command.setCursor(cursor, 0)
    assert "" == command.formattedLines.last().text
  }

  void test_total_length_of_no_preceeding_lines() {
    def command = new ProseCommand(["Hei", "du"])
    command.localCursor = new Cursor(x: 0, y: 0)
    assert command.totalLengthOfPreceedingLines() == 0
  }

  void test_total_length_of_preceeding_lines() {
    def command = new ProseCommand(["Hei", "du", "der"])
    command.localCursor = new Cursor(x: 0, y: 2)
    assert command.totalLengthOfPreceedingLines() == 7 // includes space after last too: "Hei du "
  }

}
