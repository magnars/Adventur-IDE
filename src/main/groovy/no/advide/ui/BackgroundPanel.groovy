package no.advide.ui

import java.awt.Graphics
import java.awt.Image
import java.awt.Rectangle
import javax.swing.ImageIcon
import javax.swing.JPanel

class BackgroundPanel extends JPanel {

  Image img
  int imgWidth, imgHeight;

  BackgroundPanel() {
    img = new ImageIcon(ClassLoader.getSystemResource('background.jpg')).image
    imgWidth = img.getHeight(this);
    imgHeight = img.getWidth(this);
  }

  @Override
  protected void paintComponent(Graphics g) {
		int x, y;

		Rectangle clip = g.getClipBounds();

		if (imgWidth > 0 && imgHeight > 0) {
			for (x = clip.x; x < (clip.x + clip.width) ; x += imgWidth) {
				for (y = clip.y; y < (clip.y + clip.height) ; y += imgHeight) {
					g.drawImage(img, x, y, this);
				}
			}
		}
  }

}
