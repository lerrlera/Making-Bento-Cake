// Main abstract factory for all objects

package factory;

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

public abstract class ObjectFactory {
	
	public abstract ClickableObject createButton(String type);
	
	public abstract OpenableObject createOpenableObject(String type);
	
	public abstract Oven createOven();
	
	public abstract Cabinet createCabinet(String type);

	public abstract ClickableObject createIngredient(String type);

	public abstract Snowflake createSnowflake();
	
	public abstract Steam createSteam();
	
	public abstract CakeInterface createBasicCake();
	
	public abstract ClickableObject createPiper(String type);
	
	public abstract ClickableObject createDecoration(String type);
	
	public abstract ClickableObject createSprinkleJar(String type);
	
	public abstract CakeDecorator createIcing(String type, CakeInterface basic);
	
	public abstract CakeDecorator createSprinkle(String type, CakeInterface basic, PVector pos);
	
	public abstract Sun createSun();


}
