package no.advide

class RoomConverterTest extends GroovyTestCase {

  void test_should_strip_trailing_spaces_on_load() {
    assert ["Whitespace:  "], RoomConverter.toNewStyle(["Whitespace:  "])
  }

  void test_should_convert_to_new_style() {
    assert ["-- fortsett --"], RoomConverter.toNewStyle(["!!!"])
  }

  void test_should_convert_to_old_style() {
    assert ["!!!"], RoomConverter.toOldStyle(["-- fortsett --"])
  }

}
