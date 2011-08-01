package no.advide

class PageEditorTest extends GroovyTestCase {

  void test_cmd_S_should_save_page() {
    def called = false
    def page = [ save: { called = true } ] as Page
    def editor = new PageEditor(page: page)
    editor.actionTyped("cmd+S")
    assert called
  }

  void test_can_toggle_styles() {
    AdventureTest.setUpCurrent()
    def page = Adventure.current.loadRoom(2)
    assert page.document.lines == ["Side 1", "-- fortsett --", "Side 2"]
    def editor = new PageEditor(page: page)
    editor.actionTyped("ctrl+alt+cmd+O")
    assert page.document.lines == ["Side 1", "!!!", "Side 2"]
    editor.actionTyped("ctrl+alt+cmd+N")
    assert page.document.lines == ["Side 1", "-- fortsett --", "Side 2"]
  }

}
