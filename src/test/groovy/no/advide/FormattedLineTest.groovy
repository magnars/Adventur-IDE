package no.advide

import java.awt.Color

class FormattedLineTest extends GroovyTestCase {
  FormattedLine line

  void setUp() {
    line = new FormattedLine(text: "Hei på deg")
  }

  void test_should_have_shortcut_for_setting_starting_color() {
    line.color = Color.red
    assert line.changes[0].changeColor == Color.red
  }

  void test_should_have_shortcut_for_getting_starting_color() {
    line.changes[0].changeColor = Color.blue
    assert line.color == Color.blue
  }

  void test_should_start_with_garish_default_color() {
    assert line.color == Color.pink
  }

  void test_should_set_substring_color() {
    line.formatSubstring(4, 2, Color.yellow)
    assert line.changes[4].changeColor == Color.yellow
    assert line.changes[6].revertColorChange
  }
}
