package no.advide.ui

import java.awt.Color
import java.awt.Graphics2D
import no.advide.FormatChange
import no.advide.FormattedLine

class LineRendererTest extends GroovyTestCase {

  def color
  def renderer

  void setUp() {
    renderer = new LineRenderer(new FormattedLine(), 0, 0, [setColor: { color = it }, getFontMetrics: { null }] as Graphics2D)
  }

  void test_should_stack_colors() {
    renderer.apply(new FormatChange(changeColor: Color.black))
    assert color == Color.black

    renderer.apply(new FormatChange(changeColor: Color.blue))
    assert color == Color.blue

    renderer.apply(new FormatChange(changeColor: Color.green))
    assert color == Color.green

    renderer.apply(new FormatChange(revertColorChange: true))
    assert color == Color.blue

    renderer.apply(new FormatChange(revertColorChange: true))
    assert color == Color.black
  }

}
