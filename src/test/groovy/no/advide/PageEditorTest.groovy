package no.advide

class PageEditorTest extends GroovyTestCase {

  void setUp() {
    AdventureTest.setUpCurrent()
  }

  Page getPage(int number) {
    new Page(Adventure.current.getRoom(number).createDocument())
  }

  void test_can_toggle_styles() {
    def page = getPage(2)
    assert page.lines == ["Side 1", "-- fortsett --", "Side 2"]
    def editor = new PageEditor(page)
    editor.actionTyped("ctrl+alt+cmd+O")
    assert page.lines == ["Side 1", "!!!", "Side 2"]
    editor.actionTyped("ctrl+alt+cmd+N")
    assert page.lines == ["Side 1", "-- fortsett --", "Side 2"]
  }

  void test_tabbing_between_room_numbers() {
    def page = getPage(3)
    assert page.lines == ["#1", "", "#2", "#3"]
    assert page.cursor == [x:0, y:0]
    def editor = new PageEditor(page)
    editor.actionTyped("tab")
    assert page.cursor == [x:1, y:2]
    editor.actionTyped("tab")
    assert page.cursor == [x:1, y:3]
    editor.actionTyped("tab")
    assert page.cursor == [x:1, y:3]
    editor.actionTyped("shift+tab")
    assert page.cursor == [x:1, y:2]
    editor.actionTyped("shift+tab")
    assert page.cursor == [x:1, y:0]
    editor.actionTyped("shift+tab")
    assert page.cursor == [x:1, y:0]
  }

}
