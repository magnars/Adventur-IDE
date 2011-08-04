package no.advide

class RoomTest extends GroovyTestCase {

  void setUp() {
    AdventureTest.setUpCurrent()
  }

  void test_should_save_file_in_old_style() {
    def file = new File("PageTest.tmp")
    file.setText(["-- fortsett --", "Hei på deg"].join("\n"), 'UTF-8')
    def room = new Room(1, file)
    room.save()
    assert file.getText('UTF-8') == ["!!!", "Hei på deg"].join("\n")
    file.delete()
  }

  void test_should_know_if_modified() {
    def room = Adventure.current.getRoom(0)
    assert !room.modified
    room.lines = ["endret"]
    assert room.modified
  }

  void test_should_undo_and_redo() {
    def room = Adventure.current.getRoom(0)
    assert room.lines == ["Dette er rom 0 med blåbærsyltetøy."]
    room.lines = ["endret"]
    room.lines = ["endret mer"]

    room.undo()
    assert room.lines == ["endret"]

    room.redo()
    assert room.lines == ["endret mer"]

    room.redo()
    assert room.lines == ["endret mer"]

    room.undo()
    room.undo()
    assert room.lines == ["Dette er rom 0 med blåbærsyltetøy."]

    room.undo()
    assert room.lines == ["Dette er rom 0 med blåbærsyltetøy."]

    room.redo()
    assert room.lines == ["endret"]

    room.lines = ["branch in history"]
    assert room.lines == ["branch in history"]

    room.redo()
    assert room.lines == ["branch in history"]
  }

}
