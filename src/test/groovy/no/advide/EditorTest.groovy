package no.advide

class EditorTest extends GroovyTestCase {

  void test_when_empty_arrows_does_nothing() {
    def editor = new Editor(lines: [""], cursor: new Cursor(x: 0, y: 0))

    editor.actionTyped("up")
    assertEquals new Cursor(x: 0, y: 0), editor.cursor
    editor.actionTyped("right")
    assertEquals new Cursor(x: 0, y: 0), editor.cursor
    editor.actionTyped("down")
    assertEquals new Cursor(x: 0, y: 0), editor.cursor
    editor.actionTyped("left")
    assertEquals new Cursor(x: 0, y: 0), editor.cursor
  }

  void test_right_arrow_moves_right_until_end() {
    def editor = new Editor(lines: ["yo"], cursor: new Cursor(x: 0, y: 0))

    editor.actionTyped("right")
    assertEquals new Cursor(x: 1, y: 0), editor.cursor
    editor.actionTyped("right")
    assertEquals new Cursor(x: 2, y: 0), editor.cursor
    editor.actionTyped("right")
    assertEquals new Cursor(x: 2, y: 0), editor.cursor
  }

  void test_right_arrow_goes_down_on_end() {
    def editor = new Editor(lines: ["", ""], cursor: new Cursor(x: 0, y: 0))

    editor.actionTyped("right")
    assertEquals new Cursor(x: 0, y: 1), editor.cursor
  }

  void test_left_arrow_moves_left_until_start() {
    def editor = new Editor(lines: ["yo"], cursor: new Cursor(x: 2, y: 0))

    editor.actionTyped("left")
    assertEquals new Cursor(x: 1, y: 0), editor.cursor
    editor.actionTyped("left")
    assertEquals new Cursor(x: 0, y: 0), editor.cursor
    editor.actionTyped("left")
    assertEquals new Cursor(x: 0, y: 0), editor.cursor
  }

  void test_left_arrow_moves_up_on_start() {
    def editor = new Editor(lines: ["yo", ""], cursor: new Cursor(x: 0, y: 1))

    editor.actionTyped("left")
    assertEquals new Cursor(x: 2, y: 0), editor.cursor
  }

  void test_up_arrow_moves_up_until_start() {
    def editor = new Editor(lines: ["", "", ""], cursor: new Cursor(x: 0, y: 2))

    editor.actionTyped("up")
    assertEquals new Cursor(x: 0, y: 1), editor.cursor
    editor.actionTyped("up")
    assertEquals new Cursor(x: 0, y: 0), editor.cursor
    editor.actionTyped("up")
    assertEquals new Cursor(x: 0, y: 0), editor.cursor
  }

  void test_up_arrow_moves_left_on_start() {
    def editor = new Editor(lines: ["yo"], cursor: new Cursor(x: 2, y: 0))

    editor.actionTyped("up")
    assertEquals new Cursor(x: 0, y: 0), editor.cursor
  }

  void test_up_arrow_adjust_x_for_length() {
    def editor = new Editor(lines: ["yo", "dude"], cursor: new Cursor(x: 4, y: 1))

    editor.actionTyped("up")
    assertEquals new Cursor(x: 2, y: 0), editor.cursor
  }

  void test_down_arrow_moves_down_until_end() {
    def editor = new Editor(lines: ["", "", ""], cursor: new Cursor(x: 0, y: 0))

    editor.actionTyped("down")
    assertEquals new Cursor(x: 0, y: 1), editor.cursor
    editor.actionTyped("down")
    assertEquals new Cursor(x: 0, y: 2), editor.cursor
    editor.actionTyped("down")
    assertEquals new Cursor(x: 0, y: 2), editor.cursor
  }

  void test_down_arrow_moves_right_on_end() {
    def editor = new Editor(lines: ["yo"], cursor: new Cursor(x: 0, y: 0))

    editor.actionTyped("down")
    assertEquals new Cursor(x: 2, y: 0), editor.cursor
  }

  void test_down_arrow_adjust_x_for_length() {
    def editor = new Editor(lines: ["hei", "du"], cursor: new Cursor(x: 3, y: 0))

    editor.actionTyped("down")
    assertEquals new Cursor(x: 2, y: 1), editor.cursor
  }

  void test_char_typed_start() {
    def editor = new Editor(lines: ["ei"], cursor: new Cursor(x: 0, y: 0))
    editor.charTyped("H")
    assertEquals(["Hei"], editor.lines)
    assertEquals(new Cursor(x: 1, y: 0), editor.cursor)
  }

  void test_char_typed_middle() {
    def editor = new Editor(lines: ["Hi"], cursor: new Cursor(x: 1, y: 0))
    editor.charTyped("e")
    assertEquals(["Hei"], editor.lines)
    assertEquals(new Cursor(x: 2, y: 0), editor.cursor)
  }

  void test_char_typed_end() {
    def editor = new Editor(lines: ["He"], cursor: new Cursor(x: 2, y: 0))
    editor.charTyped("i")
    assertEquals(["Hei"], editor.lines)
    assertEquals(new Cursor(x: 3, y: 0), editor.cursor)
  }

  void test_enter_splits_line() {
    def editor = new Editor(lines: ["Føretter"], cursor: new Cursor(x: 3, y: 0))
    editor.actionTyped("enter")
    assertEquals(["Før", "etter"], editor.lines)
    assertEquals(new Cursor(x: 0, y: 1), editor.cursor)
  }

}
