// ClickableObject subclass of GeneralObject
// includes all clickable objects in this program: bowl, ingredients, baking dish, buttons

package kitchen;

import processing.core.PVector;

public class ClickableObject extends GeneralObject {
	
	private boolean isDragged;
	private double originalX;
	private double originalY;
    private boolean soundPlayed = false;
	private String type;
	
	public ClickableObject(PVector pos,String filename, double size, String type) {
		super(pos,filename,size);
		isDragged = false;
		originalX = pos.x;
		originalY = pos.y;
		this.type = type;
	}
	
	// used for all draggable objects for easeier dragging action
	public boolean clickedRadius(double x, double y) {
	    boolean clicked = false;
	    
	    // calculate the radius around the object
	    double radius = 10.0; 
	    
	    // check if the click is within the enlarged area around the object
	    if (x >= (pos.x - ((double) img.getWidth()) / 2 * scale * 2 - radius) &&
	        x <= (pos.x + ((double) img.getWidth()) / 2 * scale * 2 + radius) &&
	        y >= (pos.y - ((double) img.getHeight()) / 2 * scale * 2 - radius) &&
	        y <= (pos.y + ((double) img.getHeight()) / 2 * scale * 2 + radius)) {
	        clicked = true;
	    }
	    return clicked;
	}
	
	// used for buttons and other clickable objects
	public boolean clicked(double x, double y){
		boolean clicked = false;
		
		if (x > (pos.x - ((double) img.getWidth()) / 2 * scale) && x < (pos.x + ((double) img.getWidth())/2*scale) 
				&& y > (pos.y - ((double) img.getHeight())/2*scale) && y < (pos.y + ((double) img.getHeight())/2*scale)) 
			clicked = true;
		
		return clicked;
	}
	
	public boolean isDragged() {
		return isDragged;
	}
	
	public void setDragged(boolean value) {
		isDragged = value;
	}

	public void setLocationX(double mouseX) {
		this.pos.x = (float) mouseX;
	}
	
	public void setLocationY(double mouseY) {
		this.pos.y = (float) mouseY;
	}
	
	public void setOriginalPosition(double x, double y) {
        this.originalX = x;
        this.originalY = y;
    }

    public double getOriginalX() {
        return originalX;
    }

    public double getOriginalY() {
        return originalY;
    }
    
    public boolean isSoundPlayed() {
        return soundPlayed;
    }

    public void setSoundPlayed(boolean soundPlayed) {
        this.soundPlayed = soundPlayed;
    }
    
    public String getType() {
		return type;
	}
}
