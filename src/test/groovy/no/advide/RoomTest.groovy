package no.advide

class RoomTest extends GroovyTestCase {

  void test_should_save_file_in_old_style() {
    def file = new File("PageTest.tmp")
    file.setText(["-- fortsett --", "Hei på deg"].join("\n"), 'UTF-8')
    def room = new Room(1, file)
    room.save()
    assert file.getText('UTF-8') == ["!!!", "Hei på deg"].join("\n")
    file.delete()
  }

  void test_should_know_if_modified() {
    AdventureTest.setUpCurrent()
    def room = Adventure.current.getRoom(0)
    assert !room.modified
    room.lines = ["endret"]
    assert room.modified
  }

}
