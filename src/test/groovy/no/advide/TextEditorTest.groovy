package no.advide

class TextEditorTest extends GroovyTestCase {

  TextEditor editor
  Document document
  
  def setUpEditor(lines, cursor) {
    document = new Document(lines, cursor)
    editor = new TextEditor(document)
  }
  
  void test_when_empty_arrows_does_nothing() {
    setUpEditor([""], [x:0, y:0])

    editor.actionTyped("up")
    assert document.cursor == [x:0, y:0]
    editor.actionTyped("right")
    assert document.cursor == [x:0, y:0]
    editor.actionTyped("down")
    assert document.cursor == [x:0, y:0]
    editor.actionTyped("left")
    assert document.cursor == [x:0, y:0]
  }

  void test_right_arrow_moves_right_until_end() {
    setUpEditor(["yo"], [x:0, y:0])

    editor.actionTyped("right")
    assert document.cursor == [x:1, y:0]
    editor.actionTyped("right")
    assert document.cursor == [x:2, y:0]
    editor.actionTyped("right")
    assert document.cursor == [x:2, y:0]
  }

  void test_right_arrow_goes_down_on_end() {
    setUpEditor(["", ""], [x:0, y:0])

    editor.actionTyped("right")
    assert document.cursor == [x:0, y:1]
  }

  void test_left_arrow_moves_left_until_start() {
    setUpEditor(["yo"], [x:2, y:0])

    editor.actionTyped("left")
    assert document.cursor == [x:1, y:0]
    editor.actionTyped("left")
    assert document.cursor == [x:0, y:0]
    editor.actionTyped("left")
    assert document.cursor == [x:0, y:0]
  }

  void test_left_arrow_moves_up_on_start() {
    setUpEditor(["yo", ""], [x:0, y:1])

    editor.actionTyped("left")
    assert document.cursor == [x:2, y:0]
  }

  void test_up_arrow_moves_up_until_start() {
    setUpEditor(["", "", ""], [x:0, y:2])

    editor.actionTyped("up")
    assert document.cursor == [x:0, y:1]
    editor.actionTyped("up")
    assert document.cursor == [x:0, y:0]
    editor.actionTyped("up")
    assert document.cursor == [x:0, y:0]
  }

  void test_up_arrow_moves_left_on_start() {
    setUpEditor(["yo"], [x:2, y:0])

    editor.actionTyped("up")
    assert document.cursor == [x:0, y:0]
  }

  void test_up_arrow_adjust_x_for_length() {
    setUpEditor(["yo", "dude"], [x:4, y:1])

    editor.actionTyped("up")
    assert document.cursor == [x:2, y:0]
  }

  void test_down_arrow_moves_down_until_end() {
    setUpEditor(["", "", ""], [x:0, y:0])

    editor.actionTyped("down")
    assert document.cursor == [x:0, y:1]
    editor.actionTyped("down")
    assert document.cursor == [x:0, y:2]
    editor.actionTyped("down")
    assert document.cursor == [x:0, y:2]
  }

  void test_down_arrow_moves_right_on_end() {
    setUpEditor(["yo"], [x:0, y:0])

    editor.actionTyped("down")
    assert document.cursor == [x:2, y:0]
  }

  void test_down_arrow_adjust_x_for_length() {
    setUpEditor(["hei", "du"], [x:3, y:0])

    editor.actionTyped("down")
    assert document.cursor == [x:2, y:1]
  }

  void test_char_typed_start() {
    setUpEditor(["ei"], [x:0, y:0])
    editor.charTyped("H")
    assert document.lines == ["Hei"]
    assert document.cursor == [x:1, y:0]
  }

  void test_char_typed_middle() {
    setUpEditor(["Hi"], [x:1, y:0])
    editor.charTyped("e")
    assert document.lines == ["Hei"]
    assert document.cursor == [x:2, y:0]
  }

  void test_char_typed_end() {
    setUpEditor(["He"], [x:2, y:0])
    editor.charTyped("i")
    assert document.lines == ["Hei"]
    assert document.cursor == [x:3, y:0]
  }

  void test_enter_splits_line() {
    setUpEditor(["Føretter"], [x:3, y:0])
    editor.actionTyped("enter")
    assert document.lines == ["Før", "etter"]
    assert document.cursor == [x:0, y:1]
  }

  void test_enter_knows_about_new_style_indentation_starters() {
    setUpEditor(["? KRAV"], [x:6, y:0])
    editor.actionTyped("enter")
    assert document.lines == ["? KRAV", "  "]
    assert document.cursor == [x:2, y:1]
  }

  void test_enter_keeps_indentation() {
    setUpEditor(["  Føretter"], [x:5, y:0])
    editor.actionTyped("enter")
    assert document.lines == ["  Før", "  etter"]
    assert document.cursor == [x:2, y:1]
  }

  void test_enter_keeps_deep_indentation() {
    setUpEditor(["    Føretter"], [x:7, y:0])
    editor.actionTyped("enter")
    assert document.lines == ["    Før", "    etter"]
    assert document.cursor == [x:4, y:1]
  }

  void test_backspace_removes_char() {
    setUpEditor(["Hei"], [x:2, y:0])
    editor.actionTyped("backspace")
    assert document.lines, ["Hi"]
    assert document.cursor == [x:1, y:0]
  }

  void test_backspace_joins_line() {
    setUpEditor(["H", "ei"], [x:0, y:1])
    editor.actionTyped("backspace")
    assert document.lines == ["Hei"]
    assert document.cursor == [x:1, y:0]
  }

  void test_should_callback_when_changed() {
    setUpEditor([""], [x:0, y:0])
    def called = false
    def passed = null
    editor.onChange { doc ->
      called = true
      passed = doc
    }
    editor.changed()
    assert called
    assert passed == document
  }

  void test_should_anchor_cursor_before_changing_document_to_avoid_strange_jumps_when_hidden_x_is_different_from_apparent_x() {
    setUpEditor(["abc", "", ""], [x:3, y:0])
    document.cursor.down()
    editor.charTyped("b")
    assert document.lines == ["abc", "b", ""]
    assert document.cursor == [x:1, y:1]
  }

}
