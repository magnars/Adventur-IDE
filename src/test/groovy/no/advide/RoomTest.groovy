package no.advide

class RoomTest extends GroovyTestCase {

  void test_should_save_file_in_old_style() {
    def file = new File("PageTest.tmp")
    file.text = ["-- fortsett --", "Hei på deg"].join("\n")
    def room = new Room(1, file)
    room.save()
    assert file.text == ["!!!", "Hei på deg"].join("\n")
    file.delete()
  }

  void test_should_know_if_modified() {
    AdventureTest.setUpCurrent()
    def room = Adventure.current.loadRoom(0)
    assert !room.modified
    room.lines = ["endret"]
    assert room.modified
  }

}
