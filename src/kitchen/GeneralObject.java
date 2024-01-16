// Main superclass for all objects (except Snowflake and Steam)

package kitchen;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import processing.core.PVector;
import util.ImageLoader;

public class GeneralObject {
	
	protected PVector pos;
	protected double scale, angle;
	protected BufferedImage img;
	protected Area bBox;
	protected Rectangle2D.Double imgSize;
	protected boolean isCollided = false;
	
	public GeneralObject(PVector pos,String filename, double size) {
		this.pos = pos;
		this.scale = size;
		img = ImageLoader.loadImage(filename);
		
		imgSize = new Rectangle2D.Double(-img.getWidth()/2,-img.getHeight()/2,img.getWidth(),img.getHeight());
		bBox = new Area(imgSize);
	}
	
	public void draw(Graphics2D g2) {
		AffineTransform transform = g2.getTransform();
		g2.translate(pos.x, pos.y);
		g2.scale(scale, scale);
		g2.rotate(angle);
		g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);
		g2.setTransform(transform);
	}
	
	protected Shape getBoundary() {
		AffineTransform af = new AffineTransform();
		af.translate(pos.x,pos.y);
		af.rotate(angle);
		af.scale(scale, scale);
		return af.createTransformedShape(bBox);
	}

	public void rotate() {
		angle = -Math.PI/3;
	}
	
	public void rotate(double a) {
		angle = a;
	}
	
	public boolean detectCollision(GeneralObject object) {
		boolean hit = false;
		if (getBoundary().intersects(object.getBoundary().getBounds2D()) &&
			object.getBoundary().intersects(getBoundary().getBounds2D())) {
			hit = true;
			isCollided = true;
		}
		return hit;
	}
	
	public boolean isCollided() {
		return isCollided;
	}
	
	public void setCollision(boolean value) {
		isCollided = value;
	}
	
	public boolean detectCollision(Ellipse2D.Double area) {
		boolean hit = false;
		if (getBoundary().intersects(area.getBounds2D()) &&
			area.getBounds2D().intersects(getBoundary().getBounds2D())) {
			hit = true;
		}
		return hit;
	}

	public void setImg(String filename) {
		img = ImageLoader.loadImage(filename);  
	}
	
	public double getWidth() {
		return img.getWidth()*scale;
	}
	
	public double getHeight() {
		return img.getHeight()*scale;
	}
	
	
	public double getXPos(){
		return pos.x;
	}
	
	public double getYPos(){
		return pos.y;
	}
	
}
