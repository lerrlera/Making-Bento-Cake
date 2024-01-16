// ConcreteObjectFactory subclass of ObjectFactory
// created to hold all object initializations in one place

package factory;

import decorator.BasicCake;
import decorator.CakeDecorator;
import decorator.CakeInterface;
import kitchen.Cabinet;
import kitchen.ClickableObject;
import kitchen.OpenableObject;
import kitchen.Oven;
import kitchen.Snowflake;
import kitchen.Steam;
import kitchen.Sun;
import processing.core.PVector;

public class ConcreteObjectFactory extends ObjectFactory {
	
	// create buttons
	public ClickableObject createButton(String type) {
		ClickableObject object = null;
		
		if (type == "start") {
			object = new ClickableObject(new PVector(235,590),"assets/startbutton.png",0.4,type);
		} else if (type == "restart") {
			object = new ClickableObject(new PVector(235,500),"assets/restartbutton.png",0.4,type);
		} else if (type == "decorate") {
			object = new ClickableObject(new PVector(480,711),"assets/decoratebutton.png",0.4,type);
		}
		else if (type == "done") {
			object = new ClickableObject(new PVector(860,711),"assets/donebutton.png",0.4,type);
		}
		return object;
	}

	// create openable objects
	public OpenableObject createOpenableObject(String type) {
		OpenableObject object = null;
		
		if (type == "fridge") {
			object = new OpenableObject(new PVector(840,380),"assets/closedfridge.png",0.33, "assets/openfridge.png",type);
		} else if (type == "topcupboard") {
			object = new OpenableObject(new PVector(470,190),"assets/closedtop.png",0.17,"assets/opentop.png",type);
		} else if (type == "sinkcabinet") {
			object = new OpenableObject(new PVector(640,500),"assets/closedsink.png",0.35,"assets/opensink.png",type);
		}
		return object;
	}
	
	public Oven createOven() {
		Oven object = null;
		object = new Oven(new PVector(475,523),"assets/closedoven.png");

		return object;
	}
	
	// create lower cabinets with counters
	public Cabinet createCabinet(String type) {
		Cabinet object = null;
		if (type == "cabinet1") {
			object = new Cabinet(new PVector(310,528),"assets/closedcupboard1.png",0.33,"assets/opencupboard1.png");
		} else if (type == "cabinet2") {
			object = new Cabinet(new PVector(160,527),"assets/closedcupboard2.png",0.15,"assets/opencupboard2.png");
		}
		return object;
	}
	
	
	// create all clickable ingredients
	public ClickableObject createIngredient(String type) {
		ClickableObject object = null;
		
		if (type == "bowl") {
			object = new ClickableObject(new PVector(475,260),"assets/emptybowl.png",0.07,type);
		}
		else if (type == "eggs") {
			object = new ClickableObject(new PVector(860,460),"assets/eggs.png",0.05,type);
		}
		else if (type == "flour") {
			object = new ClickableObject(new PVector(130,582),"assets/flour.png",0.05,type);
		}
		else if (type == "milk") {
			object = new ClickableObject(new PVector(820,535),"assets/milk.png",0.06,type);
		}
		else if (type == "sugar") {
			object = new ClickableObject(new PVector(640,505),"assets/sugar.png",0.05,type);
		}
		else if (type == "bakingdish") {
			object = new ClickableObject(new PVector(320,520),"assets/bakingdish.png",0.06,type);
		}
		return object;
	}

	// create a snowflake (recursive fractal)
	public Snowflake createSnowflake() {
		Snowflake object = null;
		object = new Snowflake(900,300, 25, 6);
		return object;
	}
	
	// create steam
	public Steam createSteam() {
		Steam object = null;
		object = new Steam(450, 500, 50,35);
		return object;
	}

	// create a plain basic cake 
	public CakeInterface createBasicCake() {
		BasicCake object = null;
		object = new BasicCake(new PVector(720,340),"assets/plaincake.png",0.3);
		return object;
	}

	// create piping bags
	public ClickableObject createPiper(String type) {
		ClickableObject object = null;
		if (type == "greenpiper") {
			object = new ClickableObject(new PVector(140,220),"assets/greenpiper.png",0.25,type);
		}
		else if (type == "bluepiper") {
			object = new ClickableObject(new PVector(400,220),"assets/bluepiper.png",0.25,type);
		}
		else if (type == "pinkpiper") {
			object = new ClickableObject(new PVector(275,480),"assets/pinkpiper.png",0.25,type);
		}
		return object;
	}

	// create icing on top of the cake
	public CakeDecorator createIcing(String type, CakeInterface basic) {
		CakeDecorator object = null;
		if (type == "bluepiper") {
			object = new CakeDecorator(basic,new PVector(720,340),"assets/blueicing.png",0.3);
		}
		else if (type == "greenpiper") {
			object = new CakeDecorator(basic,new PVector(720,340),"assets/greenicing.png",0.3);
		}
		else if (type == "pinkpiper") {
			object = new CakeDecorator(basic,new PVector(720,340),"assets/pinkicing.png",0.3);
		}
		return object;
	}
	
	// create sprinkle jars
	public ClickableObject createSprinkleJar(String type) {
		ClickableObject object = null;
		if (type == "yellowsprinkle") {
			object = new ClickableObject(new PVector(140,220),"assets/yellowjar.png",0.2,type);
		}
		else if (type == "treesprinkle") {
			object = new ClickableObject(new PVector(280,220),"assets/treejar.png",0.2,type);
		}
		else if (type == "bluesprinkle") {
			object = new ClickableObject(new PVector(420,220),"assets/bluejar.png",0.2,type);
		}

		return object;
	}
	
	// create icing on top of the cake
		public CakeDecorator createSprinkle(String type, CakeInterface basic, PVector pos) {
			CakeDecorator object = null;
			if (type == "yellowsprinkle") {
				object = new CakeDecorator(basic,pos,"assets/yellowsprinkle.png",0.025);
			}
			else if (type == "treesprinkle") {
				object = new CakeDecorator(basic,pos,"assets/treesprinkle.png",0.07);
			}
			else if (type == "bluesprinkle") {
				object = new CakeDecorator(basic,pos,"assets/bluesprinkle.png",0.06);
			}
			return object;
		}


	// create sun
	public Sun createSun() {
		Sun object = null;
		object = new Sun(new PVector(15,260),0.01);
		return object;
	}

	@Override
	public ClickableObject createDecoration(String type) {
		ClickableObject object = null;
		if (type == "branch") {
			object = new ClickableObject(new PVector(140,480),"assets/branch.png",0.18,type);
		} else if (type == "candycane") {
			object = new ClickableObject(new PVector(250,480),"assets/candycane.png",0.12,type);
		} else if (type == "deer") {
			object = new ClickableObject(new PVector(400,480),"assets/deer.png",0.12,type);
		}
		return object;
	}
}
