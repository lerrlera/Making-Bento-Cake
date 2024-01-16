// CakeDecorator subclass of GeneralObject that implements CakeInterface
// created to manage various decorators on top of the Base Cake

package decorator;

import java.awt.Graphics2D;
import kitchen.GeneralObject;
import processing.core.PVector;

public class CakeDecorator extends GeneralObject implements CakeInterface {
	CakeInterface cake;

	public CakeDecorator(CakeInterface basic, PVector pos, String filename, double size) {
		super(pos, filename, size);
		cake = basic;
	}	
	
	@Override
	public void draw(Graphics2D g2) {
		cake.draw(g2);
		super.draw(g2);
	}
}
