package no.advide

class DocumentFragmentTest extends GroovyTestCase {

  static CURSOR = [x: 0, y: 0]

  void test_should_get_lines_from_document() {
    def document = new Document(["outside", "outside", "inside 1", "inside 2", "inside 3", "outside"], CURSOR)
    def fragment = document.createFragment(2, 3)
    assert fragment.lines == ["inside 1", "inside 2", "inside 3"]
  }



}
