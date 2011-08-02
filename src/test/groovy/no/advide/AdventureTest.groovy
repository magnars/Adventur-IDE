package no.advide

class AdventureTest extends GroovyTestCase {

  static void setUpCurrent() {
    def directory = new File(ClassLoader.getSystemResource('test_eventyr').toURI())
    Adventure.current = new Adventure(directory)
  }

  Adventure adventure

  void setUp() {
    setUpCurrent()
    adventure = Adventure.current
  }

  void test_should_know_if_room_exists() {
    assert adventure.roomExists(0)
    assert !adventure.roomExists(17)
  }

  void test_path_to_room_number() {
    assert adventure.pathTo(0).endsWith("/A00/A0.txt")
    assert adventure.pathTo(101).endsWith("/A01/A101.txt")
    assert adventure.pathTo(217).endsWith("/A02/A217.txt")
  }

  void test_should_load_room() {
    def room = adventure.loadRoom(0)
    assert room.name == "Rom 0"
    assert room.document.lines == ["Dette er rom 0 med blåbærsyltetøy."]
  }

  void test_should_load_notes() {
    def notes = adventure.loadNotes()
    assert notes.name == "Notatblokk"
    assert notes.document.lines == ["Notatblokka"]
  }

  void test_should_format_adventure_name() {
    assert adventure.name == "Test Eventyr"
  }

  void test_should_treat_pages_as_entities_and_not_give_out_copies() {
    assert adventure.loadRoom(2) == adventure.loadRoom(2)
  }

}
