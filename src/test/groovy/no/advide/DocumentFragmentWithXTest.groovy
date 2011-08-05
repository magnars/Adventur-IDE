package no.advide

class DocumentFragmentWithXTest extends GroovyTestCase {

  Document document
  DocumentFragment fragment

  void setUp() {
    document = new Document(["outside", "outside", "  inside 1", "  inside 2", "  inside 3", "outside"], [x:0, y:0])
    fragment = document.createFragment([x:2, y:2], 3)
  }

  void test_should_get_lines_from_document() {
    assert fragment.lines == ["inside 1", "inside 2", "inside 3"]
    document.lines[2] = "  changed"
    assert fragment.lines == ["changed", "inside 2", "inside 3"]
  }

  void test_should_combine_lines() {
    fragment.mergeLineWithPrevious(1)
    assert fragment.lines == ["inside 1inside 2", "inside 3"]
    assert document.lines == ["outside", "outside", "  inside 1inside 2", "  inside 3", "outside"]
  }

  void test_should_update_y_when_outside_contracts() {
    document.mergeLineWithPrevious(1)
    assert fragment.lines == ["inside 1", "inside 2", "inside 3"]
  }

  void test_should_append_to_line() {
    fragment.appendTo(0, "x")
    assert fragment.lines == ["inside 1x", "inside 2", "inside 3"]
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
    document.cursor = [x:2, y:3]
    assert fragment.cursor == [x:0, y:1]
    document.cursor.up()
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

  void test_should_replace_with_fewer_lines() {
    document.cursor = [x:2, y:4]
    fragment.replaceWith(["new"])
    assert document.lines == ["outside", "outside", "  new", "outside"]
    assert fragment.lines == ["new"]
    assert document.cursor == [x:2, y:2]
  }

  void test_should_replace_with_more_lines() {
    document.cursor = [x:2, y:4]
    fragment.replaceWith(["1", "2", "3", "4"])
    assert document.lines == ["outside", "outside", "  1", "  2", "  3", "  4", "outside"]
    assert fragment.lines == ["1", "2", "3", "4"]
    assert document.cursor == [x:3, y:4] // this can be fixed with TranslatedCursor
  }

  void test_should_translate_cursor() {
    assert fragment.translate([x:2, y:2]) == [x:4, y:4]
  }

}
