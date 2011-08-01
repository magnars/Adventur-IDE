package no.advide

class PageEditorTest extends GroovyTestCase {

  void test_cmd_s_should_save_page() {
    def called = false
    def page = [ save: { called = true } ] as Page
    def editor = new PageEditor(page: page)
    editor.actionTyped("cmd+S")
    assert called
  }

}
