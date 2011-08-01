package no.advide

class DocumentFragmentTest extends GroovyTestCase {

  static CURSOR = [x: 0, y: 0]

  Document document
  DocumentFragment fragment

  void setUp() {
    document = new Document(["outside", "outside", "inside 1", "inside 2", "inside 3", "outside"], CURSOR)
    fragment = document.createFragment(2, 3)
  }

  void test_should_get_lines_from_document() {
    assert fragment.lines == ["inside 1", "inside 2", "inside 3"]
    document.lines[2] = "changed"
    assert fragment.lines == ["changed", "inside 2", "inside 3"]
  }

  void test_should_combine_lines() {
    fragment.mergeLineWithPrevious(1)
    assert fragment.lines == ["inside 1inside 2", "inside 3"]
    assert document.lines == ["outside", "outside", "inside 1inside 2", "inside 3", "outside"]
  }

  void test_should_update_y_when_outside_contracts() {
    document.mergeLineWithPrevious(1)
    assert fragment.lines == ["inside 1", "inside 2", "inside 3"]
  }

  void test_should_append_to_line() {
    fragment.appendTo(0, " ")
    assert fragment.lines == ["inside 1 ", "inside 2", "inside 3"]
  }

  void test_should_split_line() {
    fragment.splitAt(2, 0)
    assert fragment.lines == ["in", "side 1", "inside 2", "inside 3"]
  }

  void test_should_update_y_when_outside_expands() {
    document.splitAt(2, 0)
    assert fragment.lines == ["inside 1", "inside 2", "inside 3"]
  }

  void test_should_chop_line() {
    fragment.chop(2)
    assert fragment.lines == ["inside 1", "inside 2", "inside "]
  }

  void test_should_have_relative_cursor() {
    document.cursor = [x:0, y:3]
    assert fragment.cursor == [x:0, y:1]
    document.cursor.y -= 1
    assert fragment.cursor == [x:0, y:0]
  }

  void test_should_have_no_cursor_when_outside() {
    assert fragment.cursor == null
  }

  void test_inside() {
    assert !fragment.inside(1)
    assert fragment.inside(2)
    assert fragment.inside(4)
    assert !fragment.inside(5)
  }

  void test_should_replace_entire_fragment() {
    document.cursor = [x:0, y:4]
    fragment.replaceWith(["new"])
    assert document.lines == ["outside", "outside", "new", "outside"]
    assert fragment.lines == ["new"]
    assert document.cursor == [x:0, y:2]
  }

}
