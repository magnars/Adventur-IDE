package no.advide

class AdventureTest extends GroovyTestCase {

  static void setUpCurrent() {
    def directory = new File(ClassLoader.getSystemResource('testeventyr').toURI())
    Adventure.current = new Adventure(directory)
  }

  def adventure

  void setUp() {
    setUpCurrent()
    adventure = Adventure.current
  }

  void test_should_know_if_room_exists() {
    assert adventure.roomExists(0)
    assert !adventure.roomExists(17)
  }

  void test_path_to_room_number() {
    assert adventure.pathTo(0).endsWith("/A00/A0.txt")
    assert adventure.pathTo(101).endsWith("/A01/A101.txt")
    assert adventure.pathTo(217).endsWith("/A02/A217.txt")
  }

}
