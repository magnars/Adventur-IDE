package no.advide

class DocumentTest extends GroovyTestCase {

  Document document

  void setUp() {
    document = new Document(["abc", "def", "ghi", "jkl"], [x:0, y:0])
  }

  void test_merge_line_with_previous() {
    document.mergeLineWithPrevious(2)
    assert document.lines == ["abc", "defghi", "jkl"]
  }

  void test_merge_line_with_previous_preserves_cursor_position() {
    document.cursor = [x:0, y:3]
    document.mergeLineWithPrevious(2)
    assert document.cursor == [x:0, y:2]
  }

  void test_merge_line_with_previous_preserves_cursor_position_even_when_in_merging_lines() {
    document.cursor = [x:2, y:2]
    document.mergeLineWithPrevious(2)
    assert document.cursor == [x:5, y:1]
  }

}
