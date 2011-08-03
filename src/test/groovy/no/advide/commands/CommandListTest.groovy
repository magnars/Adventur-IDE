package no.advide.commands

class CommandListTest extends GroovyTestCase {

  CommandList list

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

  void test_should_get_room_numbers() {
    assert list.getRoomNumbers().size() == 0
  }

  void test_should_get_commands_of_particular_type() {
    list << null
    assert list.getAll(UnknownCommand).size() == 2
  }

}
