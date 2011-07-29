package no.advide.ui

import java.awt.Graphics2D
import no.advide.FormattedLine
import no.advide.FormatChange
import java.awt.Color
import java.awt.FontMetrics

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
    g.drawString(s, x, y)
    x += metrics.stringWidth(s)
  }

  void apply(FormatChange change) {
    if (change.changeColor) {
      colorStack.push(change.changeColor)
      g.setColor(change.changeColor)
    }
  }


}
