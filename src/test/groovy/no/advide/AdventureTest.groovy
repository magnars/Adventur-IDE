package no.advide

class AdventureTest extends GroovyTestCase {

  static void setUpPath() {
    Adventure.directoryPath = new File(ClassLoader.getSystemResource('testeventyr').toURI()).absolutePath
  }

  void setUp() {
    setUpPath()
  }

  void test_should_know_if_room_exists() {
    assert Adventure.roomExists(0)
    assert !Adventure.roomExists(17)
  }

  void test_path_to_room_number() {
    assert Adventure.pathTo(0).endsWith("/A00/A0.txt")
    assert Adventure.pathTo(101).endsWith("/A01/A101.txt")
    assert Adventure.pathTo(217).endsWith("/A02/A217.txt")
  }

}
