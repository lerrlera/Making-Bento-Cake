// OpenableObject subclass of ClickableObject
// includes all openable objects in this program (except oven and two left cabinets)

package kitchen;

import processing.core.PVector;
import util.ImageLoader;

public class OpenableObject extends ClickableObject {
	
	protected String openFilename;
	protected String closedFilename;
	private boolean closed;

	public OpenableObject(PVector pos, String closedFilename, double size, String openFilename, String type) {
		super(pos, closedFilename, size, type);
		this.openFilename = openFilename;
		this.closedFilename = closedFilename;
		this.closed = true;
	}
	
	public boolean isHovered(double x, double y) {
		return x > (pos.x - ((double) img.getWidth()) / 2 * scale) &&
			   x < (pos.x + ((double) img.getWidth()) / 2 * scale) &&
			   y > (pos.y - ((double) img.getHeight()) / 2 * scale) &&
	           y < (pos.y + ((double) img.getHeight()) / 2 * scale);
	}
	
	public void openObject() {
		closed = false;
		super.img = ImageLoader.loadImage(openFilename);
	} 
	
	public void closeObject() {
		closed = true;
		super.img = ImageLoader.loadImage(closedFilename);
	}
	
	public boolean isClosed() {
		return closed;
	}
	
 
}
