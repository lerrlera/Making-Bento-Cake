// Cabinet subclass of OpenableObject
// created to set up a counter area for object collision (when object placed on top)
// used for two cabinets on the left (excluding sink cabinet and top cupboard)

package kitchen;

import java.awt.geom.Rectangle2D;

import processing.core.PVector;

public class Cabinet extends OpenableObject {
	
	private Rectangle2D.Double cabinetCounter;


	public Cabinet(PVector pos, String closedFilename, double size, String openFilename) {
		super(pos, closedFilename, size, openFilename, "");
		
		cabinetCounter = new Rectangle2D.Double(pos.x-80,pos.y-100,150,50);
	}
	
	@Override
	public boolean detectCollision(GeneralObject object) {
		boolean hit = false;
		if (cabinetCounter.getBounds2D().intersects(object.getBoundary().getBounds2D()) &&
			object.getBoundary().intersects(cabinetCounter.getBounds2D())) {
			hit = true;
			isCollided = true;
		}
		return hit;
	}
}
