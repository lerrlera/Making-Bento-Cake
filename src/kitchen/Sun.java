// Subclass of GeneralObject
// Sun animation with additional fields and overriden draw method

package kitchen;

import java.awt.Graphics2D;
import processing.core.PVector;

public class Sun extends GeneralObject {
	
	private double minScale = 0.03; 
	private double maxScale = 0.04; 
	private boolean increasing = true;

	public Sun(PVector pos, double size) {
		super(pos, "assets/sun.png", size);

		scale = minScale;
	}
	
	@Override
	public void draw(Graphics2D g2) {
       super.draw(g2);

       if (increasing) {
           scale += 0.001;  
           if (scale >= maxScale) {
               increasing = false;
           }
       } else {
           scale -= 0.001;  
           if (scale <= minScale) {
               increasing = true;
           }
       }
    }
}
