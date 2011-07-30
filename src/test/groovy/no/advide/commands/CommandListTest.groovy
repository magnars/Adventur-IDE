package no.advide.commands

class CommandListTest extends GroovyTestCase {

  def list

  void setUp() {
    list = new CommandList()
    list << UnknownCommandTest.createTestCommand()
    list << UnknownCommandTest.createTestCommand()
  }

  void test_should_be_list() {
    assert list instanceof List
  }

  void test_should_collect_lines() {
    assert list.getFormattedLines().size() == 2
  }

  void test_should_convert_to_newScript() {
    assert list.toNewScript().size() == 2
  }

  void test_should_update_document() {
    def called = false
    def l = new CommandList()
    def c = [ updateDocument: { called = true } ] as Command
    l << c

    l.updateDocument()
    assert called
  }

}
