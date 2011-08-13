package no.advide.ui

import java.awt.FontMetrics
import java.awt.Graphics2D
import no.advide.Cursor
import no.advide.FormattedLine

class DocumentRenderer {

  private static final int LEFT_PADDING = 20

  def render() {
    int startIndex = cursor.calculateScrollTop(maxLines - 1)
    int numLinesToRender = maxLines + 1 // want the overflow line to show partially
    int stopIndex = startIndex + numLinesToRender
    for (int i = startIndex; i < stopIndex && i < lines.size(); i++) {
      renderLine(i, lines[i])
      y = y + fontHeight
    }
  }

  private int getMaxLines() {
    g.clipBounds.height / fontHeight
  }

  private def renderLine(i, FormattedLine line) {
    if (cursor.atLine(i))      renderCursorIndicatorBar()
    if (line.isEmbossedTop)    renderEmbossedTop()
    if (line.isEmbossed)       renderEmbossed()
    if (line.isEmbossedBottom) renderEmbossedBottom()
    if (line.hasSeparatorLine) renderSeparatorLine(line)
                               renderText(line)
    if (cursor.atLine(i))      renderCursor(line)
  }

  private def renderEmbossedTop() {
    g.setColor(Theme.embossedTop)
    g.drawLine(0, y, componentWidth, y)
  }

  private def renderEmbossed() {
    g.setColor(Theme.embossed)
    g.fillRect(0, y, componentWidth, fontHeight)
  }

  private def renderEmbossedBottom() {
    g.setColor(Theme.embossedBottom)
    g.drawLine(0, y + fontHeight, componentWidth, y + fontHeight)
  }

  private def renderSeparatorLine(line) {
    int centerY = 1 + y + fontHeight / 2
    int endOfText = LEFT_PADDING + fontMetrics.stringWidth(line.text) + prefixWidth(line)
    g.setColor(Theme.separatorLine)
    g.drawRect(endOfText, centerY, componentWidth - endOfText, 1)
  }

  private def renderCursor(FormattedLine line) {
    g.setColor(Theme.cursor)
    g.drawRect LEFT_PADDING + cursorX(line), y, 1, fontHeight - 1
  }

  private int prefixWidth(line) {
    return line.prefix ? fontMetrics.stringWidth(line.prefix) : 0
  }

  private int cursorX(FormattedLine line) {
    int prefix_shift = line.prefix && cursor.x >= line.prefixPosition ? prefixWidth(line) : 0
    return prefix_shift + fontMetrics.stringWidth(line.text.substring(0, (int)cursor.x))
  }

  private def renderText(FormattedLine line) {
    new LineRenderer(line, LEFT_PADDING, y, g).render()
  }

  private def renderCursorIndicatorBar() {
    g.setColor(Theme.cursorHighlight)
    g.fillRect 0, y, componentWidth, fontHeight - 1
  }

  def lines
  int componentWidth
  Cursor cursor
  Graphics2D g
  FontMetrics fontMetrics
  int ascent
  int fontHeight
  int y

  DocumentRenderer(layout, width, graphics) {
    g = graphics
    lines = layout.lines
    cursor = layout.cursor
    componentWidth = width
    y = 0
    fontMetrics = g.getFontMetrics()
    ascent = fontMetrics.getAscent()
    fontHeight = ascent + fontMetrics.getDescent()
  }

}
