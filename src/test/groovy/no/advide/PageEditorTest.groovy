package no.advide

class PageEditorTest extends GroovyTestCase {

  void setUp() {
    AdventureTest.setUpCurrent()
  }

  void test_cmd_S_should_save_page() {
    def called = false
    def page = [ save: { called = true } ] as Page
    def editor = new PageEditor(page: page)
    editor.actionTyped("cmd+S")
    assert called
  }

  void test_can_toggle_styles() {
    def page = Adventure.current.loadRoom(2)
    assert page.document.lines == ["Side 1", "-- fortsett --", "Side 2"]
    def editor = new PageEditor(page: page)
    editor.actionTyped("ctrl+alt+cmd+O")
    assert page.document.lines == ["Side 1", "!!!", "Side 2"]
    editor.actionTyped("ctrl+alt+cmd+N")
    assert page.document.lines == ["Side 1", "-- fortsett --", "Side 2"]
  }

  void test_tabbing_between_room_numbers() {
    def page = Adventure.current.loadRoom(3)
    assert page.document.lines == ["#1", "", "#2", "#3"]
    assert page.document.cursor == [x:0, y:0]
    def editor = new PageEditor(page: page)
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
    def page = Adventure.current.loadRoom(3) // ["#1", "", "#2", "#3"]
    def editor = new PageEditor(page: page)
    editor.charTyped("'")
    assert editor.page == Adventure.current.loadRoom(1)
  }

}
