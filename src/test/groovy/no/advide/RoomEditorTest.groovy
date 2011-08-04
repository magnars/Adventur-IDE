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
    def editor = new RoomEditor([] as Page, [ save: { called = true } ] as Room)

    editor.actionTyped("cmd+S")
    assert called
  }

  void test_cmd_Z_should_undo() {
    def called = false
    def editor = new RoomEditor([] as Page, [ undo: { called = true } ] as Room)

    editor.actionTyped("cmd+Z")
    assert called
  }

  void test_shift_cmd_Z_should_undo() {
    def called = false
    def editor = new RoomEditor([] as Page, [ redo: { called = true } ] as Room)

    editor.actionTyped("shift+cmd+Z")
    assert called
  }

  void test_jump() {
    def page = getPage(3) // ["#1", "", "#2", "#3"]
    def editor = new RoomEditor(page, [ isModified: { false } ] as Room)
    def changedTo = null
    editor.onRoomChange { number ->
      changedTo = number
    }

    editor.charTyped("'")
    assert changedTo == 1
  }

}
