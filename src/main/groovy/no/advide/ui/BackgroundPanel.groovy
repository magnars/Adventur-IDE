package no.advide.ui

import java.awt.Graphics
import java.awt.Image
import java.awt.Rectangle
import javax.swing.ImageIcon
import javax.swing.JPanel

class BackgroundPanel extends JPanel {

  Image img

  BackgroundPanel() {
    img = new ImageIcon(ClassLoader.getSystemResource('background.jpg')).image
  }

  @Override
  protected void paintComponent(Graphics g) {
		int x, y;
		int width, height;

		Rectangle clip = g.getClipBounds();

		width = img.getHeight(this);
		height = img.getWidth(this);

		if(width > 0 && height > 0) {
			for(x = clip.x; x < (clip.x + clip.width) ; x += width) {
				for(y = clip.y; y < (clip.y + clip.height) ; y += height) {
					g.drawImage(img,x,y,this);
				}
			}
		}
  }

}
