package no.advide.ui

import javax.swing.JPanel
import no.advide.Cursor
import java.awt.*

class EditorPanel extends JPanel {

  def textLayout

  EditorPanel() {
    setFocusTraversalKeysEnabled(false)
  }

  @Override
  void paintComponent(Graphics graphics) {
    super.paintComponent(graphics)
    Graphics2D g = (Graphics2D) graphics
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

    g.setFont(new Font("Verdana", Font.PLAIN, 14))

    def y = 0
    def fm = g.getFontMetrics()
    def ascent = fm.getAscent()
    def fh = ascent + fm.getDescent()

    Cursor cursor = textLayout.cursor

    textLayout.lines.eachWithIndex { String line, i ->
      g.drawString(line, 0, y + ascent)
      if (cursor.y == i) { g.drawRect fm.stringWidth(line.substring(0, cursor.x)), y, 1, fh - 1 }

      y = y + fh
    }
  }

}
