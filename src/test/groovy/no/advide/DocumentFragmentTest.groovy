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
  }

  void test_should_combine_lines() {
    fragment.mergeLineWithPrevious(1)
    assert fragment.lines == ["inside 1inside 2", "inside 3"]
  }


}
