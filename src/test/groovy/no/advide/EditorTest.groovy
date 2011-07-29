package no.advide

class EditorTest extends GroovyTestCase {

  void test_when_empty_arrows_does_nothing() {
    def editor = new Editor(lines: [""], cursor: new Cursor(x: 0, y: 0))

    editor.actionTyped("up")
    assert editor.cursor == new Cursor(x: 0, y: 0)
    editor.actionTyped("right")
    assert editor.cursor == new Cursor(x: 0, y: 0)
    editor.actionTyped("down")
    assert editor.cursor == new Cursor(x: 0, y: 0)
    editor.actionTyped("left")
    assert editor.cursor == new Cursor(x: 0, y: 0)
  }

  void test_right_arrow_moves_right_until_end() {
    def editor = new Editor(lines: ["yo"], cursor: new Cursor(x: 0, y: 0))

    editor.actionTyped("right")
    assert editor.cursor == new Cursor(x: 1, y: 0)
    editor.actionTyped("right")
    assert editor.cursor == new Cursor(x: 2, y: 0)
    editor.actionTyped("right")
    assert editor.cursor == new Cursor(x: 2, y: 0)
  }

  void test_right_arrow_goes_down_on_end() {
    def editor = new Editor(lines: ["", ""], cursor: new Cursor(x: 0, y: 0))

    editor.actionTyped("right")
    assert editor.cursor == new Cursor(x: 0, y: 1)
  }

  void test_left_arrow_moves_left_until_start() {
    def editor = new Editor(lines: ["yo"], cursor: new Cursor(x: 2, y: 0))

    editor.actionTyped("left")
    assert editor.cursor == new Cursor(x: 1, y: 0)
    editor.actionTyped("left")
    assert editor.cursor == new Cursor(x: 0, y: 0)
    editor.actionTyped("left")
    assert editor.cursor == new Cursor(x: 0, y: 0)
  }

  void test_left_arrow_moves_up_on_start() {
    def editor = new Editor(lines: ["yo", ""], cursor: new Cursor(x: 0, y: 1))

    editor.actionTyped("left")
    assert editor.cursor == new Cursor(x: 2, y: 0)
  }

  void test_up_arrow_moves_up_until_start() {
    def editor = new Editor(lines: ["", "", ""], cursor: new Cursor(x: 0, y: 2))

    editor.actionTyped("up")
    assert editor.cursor == new Cursor(x: 0, y: 1)
    editor.actionTyped("up")
    assert editor.cursor == new Cursor(x: 0, y: 0)
    editor.actionTyped("up")
    assert editor.cursor == new Cursor(x: 0, y: 0)
  }

  void test_up_arrow_moves_left_on_start() {
    def editor = new Editor(lines: ["yo"], cursor: new Cursor(x: 2, y: 0))

    editor.actionTyped("up")
    assert editor.cursor == new Cursor(x: 0, y: 0)
  }

  void test_up_arrow_adjust_x_for_length() {
    def editor = new Editor(lines: ["yo", "dude"], cursor: new Cursor(x: 4, y: 1))

    editor.actionTyped("up")
    assert editor.cursor == new Cursor(x: 2, y: 0)
  }

  void test_down_arrow_moves_down_until_end() {
    def editor = new Editor(lines: ["", "", ""], cursor: new Cursor(x: 0, y: 0))

    editor.actionTyped("down")
    assert editor.cursor == new Cursor(x: 0, y: 1)
    editor.actionTyped("down")
    assert editor.cursor == new Cursor(x: 0, y: 2)
    editor.actionTyped("down")
    assert editor.cursor == new Cursor(x: 0, y: 2)
  }

  void test_down_arrow_moves_right_on_end() {
    def editor = new Editor(lines: ["yo"], cursor: new Cursor(x: 0, y: 0))

    editor.actionTyped("down")
    assert editor.cursor == new Cursor(x: 2, y: 0)
  }

  void test_down_arrow_adjust_x_for_length() {
    def editor = new Editor(lines: ["hei", "du"], cursor: new Cursor(x: 3, y: 0))

    editor.actionTyped("down")
    assert editor.cursor == new Cursor(x: 2, y: 1)
  }

  void test_char_typed_start() {
    def editor = new Editor(lines: ["ei"], cursor: new Cursor(x: 0, y: 0))
    editor.charTyped("H")
    assert editor.lines == ["Hei"]
    assert editor.cursor == new Cursor(x: 1, y: 0)
  }

  void test_char_typed_middle() {
    def editor = new Editor(lines: ["Hi"], cursor: new Cursor(x: 1, y: 0))
    editor.charTyped("e")
    assert editor.lines == ["Hei"]
    assert editor.cursor == new Cursor(x: 2, y: 0)
  }

  void test_char_typed_end() {
    def editor = new Editor(lines: ["He"], cursor: new Cursor(x: 2, y: 0))
    editor.charTyped("i")
    assert editor.lines == ["Hei"]
    assert editor.cursor == new Cursor(x: 3, y: 0)
  }

  void test_enter_splits_line() {
    def editor = new Editor(lines: ["Føretter"], cursor: new Cursor(x: 3, y: 0))
    editor.actionTyped("enter")
    assert editor.lines == ["Før", "etter"]
    assert editor.cursor == new Cursor(x: 0, y: 1)
  }

  void test_backspace_removes_char() {
    def editor = new Editor(lines: ["Hei"], cursor: new Cursor(x: 2, y: 0))
    editor.actionTyped("backspace")
    assert editor.lines, ["Hi"]
    assert editor.cursor == new Cursor(x: 1, y: 0)
  }

  void test_backspace_joins_line() {
    def editor = new Editor(lines: ["H", "ei"], cursor: new Cursor(x: 0, y: 1))
    editor.actionTyped("backspace")
    assert editor.lines == ["Hei"]
    assert editor.cursor == new Cursor(x: 1, y: 0)
  }

  void test_should_update_lines() {
    def editor = new Editor(lines: ["abc", "def"], cursor: new Cursor(x: 0, y: 0))
    editor.updateLines(["abc def"])
    assert editor.lines == ["abc def"]
  }

  void test_should_check_if_text_was_moved_up_from_under_cursor() {
    def editor = new Editor(lines: ["abc", "def", ""], cursor: new Cursor(x: 0, y: 1))
    editor.cursor.y = 2
    editor.updateLines(["abc def", ""])
    assert editor.cursor.y == 1
  }

  void test_should_check_if_text_was_moved_down_from_under_cursor() {
    def editor = new Editor(lines: ["abc def", ""], cursor: new Cursor(x: 0, y: 1))
    editor.updateLines(["abc", "def", ""])
    assert editor.cursor.y == 2
  }

}
