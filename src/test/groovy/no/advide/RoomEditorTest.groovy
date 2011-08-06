package no.advide

class RoomEditorTest extends GroovyTestCase {

  Page page

  void setUp() {
    AdventureTest.setUpCurrent()
    page = getPage(3) // ["#1", "", "#2", "#3"]
  }

  Page getPage(int number) {
    new Page(Adventure.current.getRoom(number).createDocument())
  }

  void test_cmd_S_should_save_room() {
    def called = false
    def editor = new RoomEditor(page, new RoomHistory([ save: { called = true } ] as Room))

    editor.actionTyped("cmd+S")
    assert called
  }

  void test_cmd_Z_should_undo() {
    def called = false
    def editor = new RoomEditor(page, new RoomHistory([ undo: { called = true } ] as Room))

    editor.actionTyped("cmd+Z")
    assert called
  }

  void test_shift_cmd_Z_should_undo() {
    def called = false
    def editor = new RoomEditor(page, new RoomHistory([ redo: { called = true } ] as Room))

    editor.actionTyped("shift+cmd+Z")
    assert called
  }

  void test_jump() {
    def history = new RoomHistory([isModified: { false }] as Room)
    def editor = new RoomEditor(page, history)
    def called = false
    editor.onRoomChange {
      called = true
    }
    editor.charTyped("'")
    assert called
    assert history.current.number == 1
  }

}
