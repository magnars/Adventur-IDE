package no.advide.commands

import java.awt.Color
import no.advide.Document

class ProseCommandTest extends GroovyTestCase {

  Document document
  ProseCommand command

  void setUpCommand(List lines) {
    document = new Document(lines, [x:0, y:0])
    command = new ProseCommand(document.createFragment(0, lines.size()))
  }

  void test_should_match_lines_starting_with_letters() {
    assert ProseCommand.matches(["", "", "Hei"], 2)
    assert !ProseCommand.matches(["", "#12", ""], 1)
  }

  void test_should_request_all_continuous_lines_of_prose() {
    assert ProseCommand.numMatchingLines(["", "Hei", "på", "deg", "!!!"], 1) == 3
    assert ProseCommand.numMatchingLines(["Hei", "du"], 0) == 2
  }

  void test_should_get_formatted_lines() {
    setUpCommand(["Hei"])
    assert command.formattedLines.size() == 1
    assert command.formattedLines.first().text == "Hei"
    assert command.formattedLines.first().color == Color.black
  }

  void test_updateDocument_should_concatenate_lines() {
    setUpCommand(["Hei", "du"])
    command.updateDocument()
    assert document.lines == ["Hei du"]
  }

  void test_updateDocument_should_avoid_double_spaces() {
    setUpCommand(["Hei ", "du"])
    command.updateDocument()
    assert document.lines == ["Hei du"]
  }

  void test_updateDocument_should_not_concatenate_lines_with_cursor_at_start() {
    setUpCommand(["Hei", "du", "der", "borte"])
    document.cursor = [x:0, y:2]
    command.updateDocument()
    assert document.lines == ["Hei du", "der borte"]
    assert document.cursor == [x:0, y:1]
  }

  void test_updateDocument_should_wrap_long_lines() {
    setUpCommand(["En ganske lang linje gitt width"])
    command.width = 15
    command.updateDocument()
    assert document.lines == ["En ganske lang", "linje gitt", "width"]
  }

  void test_updateDocument_should_wrap_long_lines_also_when_cursor_is_interfering() {
    setUpCommand(["En ganske", "lang linje gitt width"])
    document.cursor = [x:0, y:1]
    command.width = 15
    command.updateDocument()
    assert document.lines == ["En ganske", "lang linje gitt", "width"]
  }

  void test_updateDocument_should_allow_cursor_to_be_placed_at_end_of_wrapped_lines() {
    def lines = ["Det er noe snålt som skjer med cursoren når den når slutten av en linje som", "splittes"]
    setUpCommand(lines)
    document.cursor = [x:75, y:0]
    command.updateDocument()
    assert document.lines == lines
    assert document.cursor == [x:75, y:0]
  }

  void test_updateDocument_should_place_cursor_correctly_when_word_unwraps() {
    setUpCommand(["En ganske", "lang linje"])
    document.cursor = [x:9, y:0]
    command.width = 15
    command.updateDocument()
    assert document.lines == ["En ganske lang", "linje"]
    assert document.cursor == [x:9, y:0]
  }

  void test_updateDocument_full_line_when_press_space_should_end_up_on_new_blank_line() {
    setUpCommand(["En full linje"])
    command.width = 13
    document.cursor = [x:13, y:0]
    document.insertAt(document.cursor.x, document.cursor.y, " ")
    document.moveCursorRight()
    command.updateDocument()
    assert document.lines == ["En full linje", ""]
    assert document.cursor == [x:0, y:1]
  }

  void test_updateDocument_trailing_space_before_cursor_shouldnt_be_used_as_newline() {
    setUpCommand(["En overfull", "linje"])
    command.width = 13
    document.cursor = [x:11, y:0]
    document.insertAt(document.cursor.x, document.cursor.y, " ")
    document.moveCursorRight()
    command.updateDocument()
    assert document.lines == ["En overfull ", "linje"]
    assert document.cursor == [x:12, y:0]
  }

}
