package no.advide.commands

class CommandListTest extends GroovyTestCase {

  void test_should_be_list() {
    def l = new CommandList()
    l << new UnknownCommand([""])
    assertEquals 1, l.size()
  }

  void test_should_collect_lines() {
    def l = new CommandList()
    l << new UnknownCommand([""])
    l << new UnknownCommand([""])
    assertEquals 2, l.getFormattedLines().size()
  }

  void test_should_convert_to_newScript() {
    def l = new CommandList()
    l << new UnknownCommand([""])
    l << new UnknownCommand([""])
    assertEquals 2, l.toNewScript().size()
  }

}
