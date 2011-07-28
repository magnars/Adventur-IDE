package no.advide.commands

class CommandListTest extends GroovyTestCase {

  void test_should_be_list() {
    def l = new CommandList()
    l << new UnknownCommand([""])
    assert l.size() == 1
  }

  void test_should_collect_lines() {
    def l = new CommandList()
    l << new UnknownCommand([""])
    l << new UnknownCommand([""])
    assert l.getFormattedLines().size() == 2
  }

  void test_should_convert_to_newScript() {
    def l = new CommandList()
    l << new UnknownCommand([""])
    l << new UnknownCommand([""])
    assert l.toNewScript().size() == 2
  }

}
