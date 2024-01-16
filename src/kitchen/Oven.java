// Oven subclass of OpenableObject
// created to set up an oven door area for better collision

package kitchen;

import java.awt.geom.Rectangle2D;
import processing.core.PVector;

public class Oven extends OpenableObject {
	
	private Rectangle2D.Double ovenDoor;

	public Oven(PVector pos, String filename) {
		super(pos, filename,0.28, "assets/openoven.png","oven");
		
		ovenDoor = new Rectangle2D.Double(390,505,165,100);
	}
	
	@Override
	public boolean detectCollision(GeneralObject object) {
		boolean hit = false;
		if (ovenDoor.getBounds2D().intersects(object.getBoundary().getBounds2D()) &&
			object.getBoundary().intersects(ovenDoor.getBounds2D())) {
			hit = true;
		}
		return hit;
	}

	

}
