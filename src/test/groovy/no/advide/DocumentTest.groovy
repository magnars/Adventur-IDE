package no.advide

class DocumentTest extends GroovyTestCase {

  Document document

  void setUp() {
    document = new Document(["abc", "def", "ghi", "jkl"], [x:0, y:0])
  }

  void test_mergeLineWithPrevious_preserves_cursor_position() {
    document.cursor = [x:0, y:3]
    document.mergeLineWithPrevious(2)
    assert document.cursor == [x:0, y:2]
  }

  void test_mergeLineWithPrevious_preserves_cursor_position_even_when_in_merging_lines() {
    document.cursor = [x:2, y:2]
    document.mergeLineWithPrevious(2)
    assert document.cursor == [x:5, y:1]
  }

  void test_insertAt() {
    document.insertAt(1, 0, "in")
    assert document.lines[0] == "ainbc"
    assert document.cursor == [x:0, y:0]
  }

  void test_insertAt_moves_cursor_if_after() {
    document.insertAt(0, 0, "in")
    assert document.lines[0] == "inabc"
    assert document.cursor == [x:2, y:0]
  }

  void test_splitAt_moves_cursor_down_when_on_line_below() {
    document.cursor = [x:0, y:2]
    document.splitAt(1, 0)
    assert document.lines == ["a", "bc", "def", "ghi", "jkl"]
    assert document.cursor == [x:0, y:3]
  }

  void test_splitAt_preserves_cursor_position_when_after_split_in_the_line() {
    document.cursor = [x:2, y:1]
    document.splitAt(2, 1)
    assert document.lines == ["abc", "de", "f", "ghi", "jkl"]
    assert document.cursor == [x:0, y:2]
  }

  void test_splitAt_holds_cursor_when_before_split_in_the_line() {
    document.cursor = [x:1, y:1]
    document.splitAt(2, 1)
    assert document.lines == ["abc", "de", "f", "ghi", "jkl"]
    assert document.cursor == [x:1, y:1]
  }

  void test_removeCharBefore_cursor_follows_left_when_after() {
    document.cursor = [x:2, y:3]
    document.removeCharBefore(2, 3)
    assert document.lines == ["abc", "def", "ghi", "jl"]
    assert document.cursor == [x:1, y:3]
  }

  void test_removeCharBefore_cursor_stays_put_when_before() {
    document.cursor = [x:1, y:3]
    document.removeCharBefore(2, 3)
    assert document.lines == ["abc", "def", "ghi", "jl"]
    assert document.cursor == [x:1, y:3]
  }

  void test_removeLine_moves_cursor_to_x_0_if_on_same_line() {
    document.cursor = [x:2, y:2]
    document.removeLine(2)
    assert document.lines == ["abc", "def", "jkl"]
    assert document.cursor == [x:0, y:2]
  }

  void test_removeLine_ensures_cursor_y_is_inside() {
    document.cursor = [x:2, y:3]
    document.removeLine(3)
    assert document.lines == ["abc", "def", "ghi"]
    assert document.cursor == [x:0, y:2]
  }

  void test_removeLine_moves_cursor_up_if_after() {
    document.cursor = [x:2, y:3]
    document.removeLine(1)
    assert document.lines == ["abc", "ghi", "jkl"]
    assert document.cursor == [x:2, y:2]
  }

  void test_replaceLine_moves_cursor_to_end() {
    document.cursor = [x:2, y:2]
    document.replaceLine(2, "heisann")
    assert document.lines == ["abc", "def", "heisann", "jkl"]
    assert document.cursor == [x:7, y:2]
  }

  void test_replaceLine_keeps_cursor_at_start() {
    document.cursor = [x:0, y:2]
    document.replaceLine(2, "heisann")
    assert document.lines == ["abc", "def", "heisann", "jkl"]
    assert document.cursor == [x:0, y:2]
  }


}
