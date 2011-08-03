package no.advide

class RoomEditorTest extends GroovyTestCase {

  void setUp() {
    AdventureTest.setUpCurrent()
  }

  Page getPage(int number) {
    new Page(Adventure.current.getRoom(number))
  }

  void test_cmd_S_should_save_room() {
    def called = false
    def room = [ save: { called = true } ] as Room
    def editor = new RoomEditor(getPage(0), room)
    editor.actionTyped("cmd+S")
    assert called
  }

  void test_jump() {
    def page = getPage(3) // ["#1", "", "#2", "#3"]
    def editor = new RoomEditor(page, [] as Room)
    def changedTo = null
    editor.onRoomChange { number ->
      changedTo = number
    }
    editor.charTyped("'")
    assert changedTo == 1
  }

}
