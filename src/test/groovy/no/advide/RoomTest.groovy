package no.advide

class RoomTest extends GroovyTestCase {

  def room

  void setUp() {
    room = new Room(number: 0, lines: [""])
  }

  void test_should_have_name() {
    assert room.name == "Rom 0"
  }

}
