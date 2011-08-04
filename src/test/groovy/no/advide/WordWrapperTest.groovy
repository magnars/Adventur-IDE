package no.advide

class WordWrapperTest extends GroovyTestCase {

  Document document
  DocumentFragment fragment
  WordWrapper wrapper

  void setUpFormatter(List lines) {
    document = new Document(lines, [x:0, y:0])
    fragment = document.createFragment(0, lines.size())
    wrapper = new WordWrapper(fragment)
  }

  void test_should_concatenate_lines() {
    setUpFormatter(["Hei", "du"])
    wrapper.justify()
    assert document.lines == ["Hei du"]
  }

  void test_should_avoid_double_spaces() {
    setUpFormatter(["Hei ", "du"])
    wrapper.justify()
    assert document.lines == ["Hei du"]
  }

  void test_should_wrap_long_lines() {
    setUpFormatter(["En ganske lang linje gitt width"])
    wrapper.width = 15
    wrapper.justify()
    assert document.lines == ["En ganske lang", "linje gitt", "width"]
  }

  void test_should_allow_cursor_to_be_placed_at_end_of_wrapped_lines() {
    def lines = ["Det er noe snålt som skjer med cursoren når den når slutten av en linje som", "splittes"]
    setUpFormatter(lines)
    document.cursor = [x:75, y:0]
    wrapper.justify()
    assert document.lines == lines
    assert document.cursor == [x:75, y:0]
  }

  void test_should_place_cursor_correctly_when_word_unwraps() {
    setUpFormatter(["En ganske", "lang linje"])
    document.cursor = [x:9, y:0]
    wrapper.width = 15
    wrapper.justify()
    assert document.lines == ["En ganske lang", "linje"]
    assert document.cursor == [x:9, y:0]
  }

  void test_full_line_when_press_space_should_end_up_on_new_blank_line() {
    setUpFormatter(["En full linje"])
    wrapper.width = 13
    document.cursor = [x:13, y:0]
    document.insertAt(document.cursor.x, document.cursor.y, " ")
    document.cursor.right()
    wrapper.justify()
    assert document.lines == ["En full linje", ""]
    assert document.cursor == [x:0, y:1]
  }

  void test_trailing_space_before_cursor_shouldnt_be_used_as_newline() {
    setUpFormatter(["En overfull", "linje"])
    wrapper.width = 13
    document.cursor = [x:11, y:0]
    document.insertAt(document.cursor.x, document.cursor.y, " ")
    document.cursor.right()
    wrapper.justify()
    assert document.lines == ["En overfull ", "linje"]
    assert document.cursor == [x:12, y:0]
  }

}
