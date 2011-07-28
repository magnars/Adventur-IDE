package no.advide.ui

import java.awt.Color
import java.awt.Graphics2D

import java.awt.FontMetrics
import no.advide.Cursor
import no.advide.FormattedLine

class TextRendering {

  private static final int LEFT_PADDING = 20
  private static final Color SOFT_HIGHLIGHT = new Color(255, 255, 255, 50)

  def render() {
    textLayout.lines.eachWithIndex { FormattedLine line, i ->
      renderLine(i, line)
      y = y + fontHeight
    }
  }

  private def renderLine(i, FormattedLine line) {
    if (atCursorLine(i)) renderCursorIndicatorBar()
    renderText(line)
    if (atCursorLine(i)) renderCursor(line.text)
  }

  private def renderCursor(String line) {
    g.setColor(Color.black)
    g.drawRect LEFT_PADDING + cursorX(line), y, 1, fontHeight - 1
  }

  private int cursorX(String line) {
    return fontMetrics.stringWidth(line.substring(0, cursor.x))
  }

  private def renderText(FormattedLine line) {
    g.setColor(line.color)
    g.drawString(line.text, LEFT_PADDING, y + ascent)
  }

  private def renderCursorIndicatorBar() {
    g.setColor(SOFT_HIGHLIGHT)
    g.fillRect 0, y, componentWidth, fontHeight - 1
  }

  private boolean atCursorLine(i) {
    cursor.y == i
  }

  def textLayout
  int componentWidth
  Cursor cursor
  Graphics2D g
  FontMetrics fontMetrics
  int ascent
  int fontHeight
  int y

  TextRendering(layout, width, graphics) {
    g = graphics
    textLayout = layout
    componentWidth = width
    y = 0
    fontMetrics = g.getFontMetrics()
    ascent = fontMetrics.getAscent()
    fontHeight = ascent + fontMetrics.getDescent()
    cursor = textLayout.cursor
  }

}
