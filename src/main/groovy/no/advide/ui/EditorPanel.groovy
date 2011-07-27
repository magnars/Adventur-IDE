package no.advide.ui

import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JPanel
import no.advide.Cursor

class EditorPanel extends JPanel {

  def textLayout

  EditorPanel() {
    setFocusTraversalKeysEnabled(false)
  }

  @Override
  void paintComponent(Graphics graphics) {
    super.paintComponent(graphics)
    Graphics2D g = (Graphics2D) graphics

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
