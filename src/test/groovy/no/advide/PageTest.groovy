package no.advide

class PageTest extends GroovyTestCase {

  def room

  void setUp() {
    AdventureTest.setUpCurrent()
  }

  void test_should_create_document() {
    room = Adventure.current.loadRoom(0)
    assert room.document.lines == ["Dette er rom 0 med blåbærsyltetøy."]
    assert room.document.cursor == [x: 0, y: 0]
  }

  void test_should_strip_trailing_spaces_on_load() {
    room = Adventure.current.loadRoom(1)
    assert room.document.lines == ["Et rom med trailing whitespace."]
  }

}
