package no.advide

import no.advide.commands.CommandParser

class PageEditorTest extends GroovyTestCase {

  void setUp() {
    AdventureTest.setUpCurrent()
  }

  void test_cmd_S_should_save_page() {
    def called = false
    def room = [ save: { called = true } ] as Room
    def editor = new PageEditor(getPage(0), room)
    editor.actionTyped("cmd+S")
    assert called
  }

  Page getPage(int number) {
    def room = Adventure.current.loadRoom(number)
    def document = new Document(room.lines, room.cursor)
    def commands = new CommandParser(document).parse()
    new Page(document, commands)
  }

  void test_can_toggle_styles() {
    def page = getPage(2)
    assert page.document.lines == ["Side 1", "-- fortsett --", "Side 2"]
    def editor = new PageEditor(page, [] as Room)
    editor.actionTyped("ctrl+alt+cmd+O")
    assert page.document.lines == ["Side 1", "!!!", "Side 2"]
    editor.actionTyped("ctrl+alt+cmd+N")
    assert page.document.lines == ["Side 1", "-- fortsett --", "Side 2"]
  }

  void test_tabbing_between_room_numbers() {
    def page = getPage(3)
    assert page.document.lines == ["#1", "", "#2", "#3"]
    assert page.document.cursor == [x:0, y:0]
    def editor = new PageEditor(page, [] as Room)
    editor.actionTyped("tab")
    assert page.document.cursor == [x:1, y:2]
    editor.actionTyped("tab")
    assert page.document.cursor == [x:1, y:3]
    editor.actionTyped("tab")
    assert page.document.cursor == [x:1, y:3]
    editor.actionTyped("shift+tab")
    assert page.document.cursor == [x:1, y:2]
    editor.actionTyped("shift+tab")
    assert page.document.cursor == [x:1, y:0]
    editor.actionTyped("shift+tab")
    assert page.document.cursor == [x:1, y:0]
  }

  void test_jump() {
    def page = getPage(3) // ["#1", "", "#2", "#3"]
    def editor = new PageEditor(page, [] as Room)
    def changedTo = null
    editor.onRoomChange { number ->
      changedTo = number
    }
    editor.charTyped("'")
    assert changedTo == 1
  }

}
