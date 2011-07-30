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

  void test_should_append_to_line() {
    fragment.appendTo(0, " ")
    assert fragment.lines == ["inside 1 ", "inside 2", "inside 3"]
  }

  void test_should_split_line() {
    fragment.splitAt(2, 0)
    assert fragment.lines == ["in", "side 1", "inside 2", "inside 3"]
  }

  void test_should_chop_line() {
    fragment.chop(2)
    assert fragment.lines == ["inside 1", "inside 2", "inside "]
  }

}
