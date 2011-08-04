package no.advide.ui

import java.awt.Color
import java.awt.FontMetrics
import java.awt.Graphics2D
import no.advide.FormattedLine

class TextRenderer {

  private static final int LEFT_PADDING = 20
  private static final Color CURSOR_HIGHLIGHT = new Color(255, 255, 255, 50)
  private static final Color SEPARATOR_LINE_COLOR = new Color(60, 60, 200, 30)

  def render() {
    textLayout.lines.eachWithIndex { FormattedLine line, i ->
      renderLine(i, line)
      y = y + fontHeight
    }
  }

  private def renderLine(i, FormattedLine line) {
    if (cursor.atLine(i)) renderCursorIndicatorBar()
    if (line.hasSeparatorLine) renderSeparatorLine(line.text)
    renderText(line)
    if (cursor.atLine(i)) renderCursor(line.text)
  }

  private def renderSeparatorLine(text) {
    int centerY = 1 + y + fontHeight / 2
    int endOfText = LEFT_PADDING + fontMetrics.stringWidth(text)
    g.setColor(SEPARATOR_LINE_COLOR)
    g.drawRect(endOfText, centerY, componentWidth - endOfText, 1)
  }

  private def renderCursor(String line) {
    g.setColor(Color.black)
    g.drawRect LEFT_PADDING + cursorX(line), y, 1, fontHeight - 1
  }

  private int cursorX(String line) {
    return fontMetrics.stringWidth(line.substring(0, (int)cursor.x))
  }

  private def renderText(FormattedLine line) {
    new LineRenderer(line, LEFT_PADDING, y, g).render()
  }

  private def renderCursorIndicatorBar() {
    g.setColor(CURSOR_HIGHLIGHT)
    g.fillRect 0, y, componentWidth, fontHeight - 1
  }

  def textLayout
  int componentWidth
  def cursor
  Graphics2D g
  FontMetrics fontMetrics
  int ascent
  int fontHeight
  int y

  TextRenderer(layout, width, graphics) {
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
