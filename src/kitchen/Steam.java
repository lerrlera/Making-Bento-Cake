// Steam object

package kitchen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import processing.core.PApplet;
import util.Util;

public class Steam {

    private float xStart;
    private float xSeed;
    private float ySeed;
    private double width;
    private double height;
    private PApplet pa;

    public Steam(float startX, float startY, int width, int height) {
        xStart = startX;
        xSeed = xStart;
        ySeed = startY;
        this.width = width;
        this.height = height;
        pa = new PApplet();
    }

    public void draw(Graphics2D g2) {
        float noiseFactor;
        for (int y = 0; y <= height; y += 5) {
            ySeed += 0.1;
            xSeed = xStart; 

            for (int x = 0; x <= width; x += 5) {
                xSeed += 0.1;
                noiseFactor = pa.noise(xSeed, xSeed);
                AffineTransform at = g2.getTransform();                
                g2.translate(xStart + x, ySeed + y); 
                g2.rotate(noiseFactor * Util.radians(540));
                float diameter = noiseFactor * 35;
                int grey = (int) (150 + (noiseFactor * 105));
                int alph = (int) (150 + (noiseFactor * 105));
                g2.setColor(new Color(grey, grey, grey, alph));
                g2.fill(new Ellipse2D.Float(-diameter / 2, -diameter / 4, diameter, diameter / 2));
                g2.setTransform(at);
            }
        }
    }

	public void setWidth(int i) {
		this.width = i;
	}

	public void setHeight(int i) {
		this.height = i;
	}
}