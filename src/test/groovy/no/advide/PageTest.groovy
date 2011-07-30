package no.advide

class PageTest extends GroovyTestCase {

  def room

  void setUp() {
    AdventureTest.setUpCurrent()
    room = Adventure.current.loadRoom(0)
  }

  void test_should_create_document() {
    assert room.document.lines == ["Dette er rom 0 med blåbærsyltetøy."]
    assert room.document.cursor == [x: 0, y: 0]
  }

}
