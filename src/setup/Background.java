package setup;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.BentoCakePanel;
import main.BentoCakePanel.ProcessState;
import util.ImageLoader;

public class Background {
	private BufferedImage img;
	private double scale;
	
	public Background(String filename) {
		img = ImageLoader.loadImage(filename);
		scale = 1;
	}
		
	public void draw(Graphics2D g2, ProcessState state) {
		AffineTransform at = g2.getTransform();
		g2.translate(0, 0);
		g2.scale(scale, scale);
		if ((state != ProcessState.START && state!=ProcessState.RESTART) && state.ordinal() < ProcessState.DECORATE_ICING.ordinal()) {
			img = ImageLoader.loadImage("assets/kitchen.png");
			g2.drawImage(img, 0, 0, BentoCakePanel.W_WIDTH, BentoCakePanel.W_HEIGHT-70, null);
		} else if (state == ProcessState.RESTART) {
			img = ImageLoader.loadImage("assets/restart.png");
			g2.drawImage(img, 0, 0, BentoCakePanel.W_WIDTH, BentoCakePanel.W_HEIGHT, null);
		} else if (state.ordinal() > ProcessState.CAKE_READY.ordinal()) {
			img = ImageLoader.loadImage("assets/decorate.png");
			g2.drawImage(img, 0, 0, BentoCakePanel.W_WIDTH, BentoCakePanel.W_HEIGHT, null);
		}
		else {
			g2.drawImage(img, 0, 0, BentoCakePanel.W_WIDTH, BentoCakePanel.W_HEIGHT, null);
		}
		g2.setTransform(at);
	}
}
