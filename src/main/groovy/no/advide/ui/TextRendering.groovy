package no.advide.ui

import java.awt.Color
import java.awt.Graphics2D

import no.advide.TextLayout
import java.awt.FontMetrics
import no.advide.Cursor

class TextRendering {

  private static final int LEFT_PADDING = 20
  private static final Color SOFT_HIGHLIGHT = new Color(255, 255, 255, 50)

  def render() {
    textLayout.lines.eachWithIndex { String line, i ->
      renderLine(i, line)
      y = y + fontHeight
    }
  }

  private def renderLine(i, String line) {
    if (atCursorLine(i)) renderCursorIndicatorBar()
    renderText(line)
    if (atCursorLine(i)) renderCursor(line)
  }

  private def renderCursor(String line) {
    g.drawRect LEFT_PADDING + cursorX(line), y, 1, fontHeight - 1
  }

  private int cursorX(String line) {
    return fontMetrics.stringWidth(line.substring(0, cursor.x))
  }

  private def renderText(String line) {
    g.drawString(line, LEFT_PADDING, y + ascent)
  }

  private def renderCursorIndicatorBar() {
    g.setColor(SOFT_HIGHLIGHT)
    g.fillRect 0, y, componentWidth, fontHeight - 1
    g.setColor(Color.black)
  }

  private boolean atCursorLine(i) {
    cursor.y == i
  }

  TextLayout textLayout
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
