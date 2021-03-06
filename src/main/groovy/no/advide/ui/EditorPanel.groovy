package no.advide.ui

import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JPanel
import no.advide.FormattedLine

class EditorPanel extends JPanel {

  def textLayout
  def defaultFont

  EditorPanel() {
    setFocusTraversalKeysEnabled(false)
    defaultFont = new Font("Monaco", Font.PLAIN, 20)
  }

  @Override
  void paintComponent(Graphics graphics) {
    super.paintComponent(graphics)
    Graphics2D g = (Graphics2D) graphics
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
    g.setFont(defaultFont)
    new DocumentRenderer(textLayout, getWidth(), g).render()
  }

  void updateContents(List<FormattedLine> lines, cursor) {
    textLayout = [lines: lines, cursor: cursor]
    repaint()
  }
}
