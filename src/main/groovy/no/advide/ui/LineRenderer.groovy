package no.advide.ui

import java.awt.Color
import java.awt.FontMetrics
import java.awt.Graphics2D
import no.advide.FormatChange
import no.advide.FormattedLine

class LineRenderer {
  int x, y
  Graphics2D g
  String text
  Map<Integer, FormatChange> changes
  FontMetrics metrics

  LineRenderer(FormattedLine line, int x, int y, Graphics2D g) {
    changes = line.changes
    text = line.text
    this.x = x
    this.y = y
    this.g = g
    metrics = g.getFontMetrics()
  }

  int index
  List<Color> colorStack = []

  void render() {
    index = 0
    renderFromIndex()
  }

  void renderFromIndex() {
    apply(changes[index])
    if (atLastChange()) {
      drawRemaining()
    } else {
      drawUpToNextChange()
      index = nextChangeIndex()
      renderFromIndex()
    }
  }

  private def drawRemaining() {
    draw(text.substring(index))
  }

  void drawUpToNextChange() {
    draw(text.substring(index, nextChangeIndex()))
  }

  boolean atLastChange() {
    index == changes.keySet().max()
  }

  int nextChangeIndex() {
    changes.keySet().findAll { it > index }.min()
  }

  void draw(String s) {
    g.drawString(s, x, y + metrics.ascent)
    x += metrics.stringWidth(s)
  }

  void apply(FormatChange change) {
    if (change.revertColorChange) { colorStack.pop() }
    if (change.changeColor)       { colorStack.push(change.changeColor) }
    if (change.highlight)         { highlightNextChars(change.highlight.length, change.highlight.color) }
    g.setColor(colorStack.last())
  }

  private def highlightNextChars(length, color) {
    g.setColor(color)
    g.fillRect(x, y, metrics.stringWidth(nextChars(length)), metrics.height)
  }

  private String nextChars(length) {
    return text.substring(index, (int) index + length)
  }


}
