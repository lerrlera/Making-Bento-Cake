// BasicCake subclass of GeneralObject that implements CakeInterface
// serves as a Base Cake object

package decorator;

import kitchen.GeneralObject;
import processing.core.PVector;

public class BasicCake extends GeneralObject implements CakeInterface {
	
	public BasicCake(PVector pos, String filename, double size) {
		super(pos, filename, size);
	}

}
