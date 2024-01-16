package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import decorator.CakeInterface;
import factory.ConcreteObjectFactory;
import factory.ObjectFactory;
import kitchen.Cabinet;
import kitchen.ClickableObject;
import kitchen.OpenableObject;
import kitchen.Oven;
import kitchen.Snowflake;
import kitchen.Steam;
import kitchen.Sun;
import processing.core.PVector;
import setup.Background;
import setup.Text;
import util.MinimHelper;

public class BentoCakePanel extends JPanel implements ActionListener {

	// screen size
	public static int W_WIDTH = 1030;
	public static int W_HEIGHT = 800;
	
	// mouse positions
	private double mouseX;
	private double mouseY;
	
	// drag speed
	private static final double DRAG_SPEED = 1.5; 

	// frame
	private JFrame frame;
	
	// initial state
	private ProcessState state = ProcessState.START;

	// backgrounds
	private Background background;
	
	// factory
	private ObjectFactory factory;
	
	// objects
	private ArrayList<OpenableObject> openableObjects = new ArrayList<OpenableObject>();
	private Oven oven;
	private Cabinet cabinet1, cabinet2;
	private ClickableObject bowl, eggs, flour, milk, sugar, bakingDish;
	private ArrayList<ClickableObject> pipers = new ArrayList<ClickableObject>();
	private ArrayList<ClickableObject> jars = new ArrayList<ClickableObject>();
	private ArrayList<ClickableObject> decorations = new ArrayList<ClickableObject>();
	private Steam steam;
	private Snowflake snowflake;
	private Sun sun;
	private CakeInterface basicCake;
	private ArrayList<ClickableObject> cakeDecorations = new ArrayList<>();
		
	// buttons
	private ClickableObject startButton;
	private ClickableObject restartButton;
	private ClickableObject decorateButton;
	private ClickableObject doneButton;
	
	// TIMERS
	// ingredient timer
    private int ingredientTimer = 0;
    private static final int I_DELAY = 5; 
    // steam timer
 	private int steamTimer = 0;
    private static final int STEAM_DELAY = 20; 
 	// oven timer
 	private int bakeTimer = 0;
    private static final int CAKE_DELAY = 50; 
	
	// sound setup
	private Minim minim;
	private AudioPlayer kitchen, click, open, plate, eggCracking, ingredient, milkPour, ovenReady, decorSound, piperSound, sprinkleSound, success;

	private Timer timer;
	
	// for detecting collision on the cake in decorating state
	private Ellipse2D.Double cakeArea;
	
	// for piping bag and sprinkle clicking interactions
	private boolean isObjectClicked = false;
    private ClickableObject clickedObject; 
    
    // for bigger decorations clicking interactions
    private boolean isDecorClicked = false;
    private ClickableObject clickedDecor;
    
	// for more recognizable state names
	public enum ProcessState {
	    START, INTRO, BOWL, EGGS, SUGAR, MILK, FLOUR, BAKINGDISH, MIX_BAKINGDISH,OVEN, 
	    MIX_OVEN, CAKE_READY, BAKE_FINISH, DECORATE_BUTTON, DECORATE_ICING, DECORATE_SPRINKLES, RESTART;
	}

	public BentoCakePanel(JFrame frame) {
		this.frame = frame;
		this.setBackground(Color.white);
		setPreferredSize(new Dimension(W_WIDTH, W_HEIGHT));
				
		// factory
		factory = new ConcreteObjectFactory();
		
		// snowflake sticker on a fridge
		snowflake = factory.createSnowflake();
		
		// sun
		sun = factory.createSun();

		// buttons
		startButton = factory.createButton("start");
		restartButton = factory.createButton("restart");
		decorateButton = factory.createButton("decorate");
		doneButton = factory.createButton("done");

		// set background
		background = new Background("assets/start.png");
		
		// oven
		oven = factory.createOven();
		
		// lower cabinets with counters
		cabinet1 = factory.createCabinet("cabinet1");
		cabinet2 = factory.createCabinet("cabinet2");

		// list for openable objects
		// Case 1: Inclusion Polymorphism
		// Arraylist of openable objects with openableo= objects, oven (subclass) and cabinets (subclass)
		openableObjects.add(factory.createOpenableObject("fridge"));
		openableObjects.add(factory.createOpenableObject("topcupboard"));
		openableObjects.add(factory.createOpenableObject("sinkcabinet"));
		openableObjects.add(cabinet2);
		openableObjects.add(cabinet1);
		openableObjects.add(oven);
		
		// kitchen objects
		bowl = factory.createIngredient("bowl");
		eggs = factory.createIngredient("eggs");
		flour = factory.createIngredient("flour");
		milk = factory.createIngredient("milk");
		sugar = factory.createIngredient("sugar");
		bakingDish = factory.createIngredient("bakingdish");
		
		// cake
		basicCake = factory.createBasicCake();
		cakeArea = new Ellipse2D.Double(590,307,250,200);
		
		// icing piping bags
		pipers.add(factory.createPiper("bluepiper"));
		pipers.add(factory.createPiper("pinkpiper"));
		pipers.add(factory.createPiper("greenpiper"));
		
		// sprinkle jars
		jars.add(factory.createSprinkleJar("bluesprinkle"));
		jars.add(factory.createSprinkleJar("treesprinkle"));
		jars.add(factory.createSprinkleJar("yellowsprinkle"));
		
		// sprinkle jars
		decorations.add(factory.createDecoration("deer"));
		decorations.add(factory.createDecoration("candycane"));
		decorations.add(factory.createDecoration("branch"));
		
		// sounds
		minim = new Minim(new MinimHelper());
		kitchen = minim.loadFile("kitchen.mp3");
		click = minim.loadFile("click.mp3");
		open = minim.loadFile("open.mp3");
		plate = minim.loadFile("plate.mp3");
		eggCracking = minim.loadFile("eggs.mp3");
		milkPour = minim.loadFile("milkpour.mp3");
		ingredient = minim.loadFile("ingredient.mp3");
		ovenReady = minim.loadFile("ready.mp3");
		decorSound = minim.loadFile("pick.mp3");
		piperSound = minim.loadFile("putback.mp3");
		sprinkleSound = minim.loadFile("sprinkle.mp3");
		success = minim.loadFile("success.mp3");

		MyMouseListener ml = new MyMouseListener();
		addMouseListener(ml);
		MyMouseMotionListener mml = new MyMouseMotionListener();
		addMouseMotionListener(mml);

		timer = new Timer(33, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		background.draw(g2, state);
		
		// only draw kitchen setup if state not START or RESTART & up to decoration
		if ((state!= ProcessState.START && state!= ProcessState.RESTART) && state.ordinal() < ProcessState.DECORATE_ICING.ordinal()) {
			drawKitchen(g2);
		}
		
		// start screen with a button
		if (state == ProcessState.START) {
			startButton.draw(g2);
		} 
	
		else if (state == ProcessState.BOWL) {
			if (bowl.isDragged()) {
				bowl.draw(g2);
			}
		}
		else if (state == ProcessState.EGGS) {
			bowl.draw(g2);
			if (eggs.isDragged()) {
				eggs.draw(g2);
				handleIngredient(eggs,bowl,ProcessState.SUGAR);
			}
		}
		else if (state == ProcessState.SUGAR) {
			bowl.setImg("assets/eggsbowl.png");
			bowl.draw(g2);
			if (sugar.isDragged()) {
				sugar.draw(g2);
				handleIngredient(sugar,bowl,ProcessState.MILK);
			}
		}
		else if (state == ProcessState.MILK) {
			bowl.draw(g2);
			if (milk.isDragged()) {
				milk.draw(g2);
				handleIngredient(milk,bowl,ProcessState.FLOUR);
			}
		} 
		else if (state == ProcessState.FLOUR) {
			bowl.setImg("assets/halfbowl.png");
			bowl.draw(g2);
			if (flour.isDragged()) {
				flour.draw(g2);
				handleIngredient(flour,bowl,ProcessState.BAKINGDISH);
			}
		} 
		else if (state == ProcessState.BAKINGDISH) {
			bowl.setImg("assets/fullbowl.png");
			bowl.draw(g2);
			if (bakingDish.isDragged()) {
				bakingDish.draw(g2);
			}
		} 
		else if (state == ProcessState.MIX_BAKINGDISH) {
			bakingDish.draw(g2);
			bowl.draw(g2);
			handleIngredient(bowl,bakingDish,ProcessState.OVEN);
		} 
		else if (state == ProcessState.OVEN) {
			bakingDish.setImg("assets/fullbakingdish.png");
			bakingDish.draw(g2);
		} 
		else if (state == ProcessState.MIX_OVEN) {
			oven.setImg("assets/cakeoven.png");
		} 
		else if (state == ProcessState.CAKE_READY) {
			oven.setImg("assets/openoven.png");
			bakingDish.draw(g2);
			steam = factory.createSteam();
			steam.draw(g2);
			steamTimer();
		} 
		else if (state == ProcessState.BAKE_FINISH) {
			bakingDish.draw(g2);
		} 
		else if (state == ProcessState.DECORATE_BUTTON) {
			bakingDish.draw(g2);
			decorateButton.draw(g2);
		} 
		else if (state == ProcessState.DECORATE_ICING) {
			drawTextBar(g2);
			basicCake.draw(g2);
			for (ClickableObject p: pipers) {
				p.draw(g2);
			}

		} else if (state == ProcessState.DECORATE_SPRINKLES) {
			drawTextBar(g2);
			basicCake.draw(g2);
			doneButton.draw(g2);
			for (ClickableObject d: decorations) {
				d.draw(g2);
			}
			for (ClickableObject j: jars) {
				j.draw(g2);
			}
		
		}
		else if (state == ProcessState.RESTART) {
			basicCake.draw(g2); 
			for (ClickableObject d: cakeDecorations) {
				if (d!=null) {
					d.draw(g2);
				}
			}
			restartButton.draw(g2);
		}
	}
	
	// handle steam timer
	private void steamTimer() {
		if (steamTimer < STEAM_DELAY) {
			steamTimer++;
		} else {
			state = ProcessState.BAKE_FINISH;
			steamTimer = 0;
		}
	}
	
	// handle bowl ingredients
	private void handleIngredient(ClickableObject ingredient, ClickableObject object, ProcessState nextState) {
		if (ingredient.detectCollision(object)) {
			if (state == ProcessState.FLOUR) {
				ingredient.rotate(Math.PI/3);
			} else {
				ingredient.rotate();
			}
			changeIngredientState(nextState);
		}
	}

	// method to draw initial kitchen setup
	private void drawKitchen(Graphics2D g2) {
		for (OpenableObject object: openableObjects) {
			object.draw(g2);
			if (!object.isClosed()) {
				if (object.getType() == "topcupboard") {
					if (state.ordinal() < ProcessState.MIX_BAKINGDISH.ordinal()) {
						bowl.draw(g2);
					}
				}
				if (object.getType() == "fridge") {
					if (state.ordinal() < ProcessState.SUGAR.ordinal()) {
						eggs.draw(g2);
					}
					if (state.ordinal() < ProcessState.FLOUR.ordinal()) {
						milk.draw(g2);
					}
				}
				if (object == cabinet2) {
					if (state.ordinal() < ProcessState.BAKINGDISH.ordinal()) {
						flour.draw(g2);
					}
				}
				if (object == cabinet1) {
					if (state != ProcessState.MIX_OVEN) {
						bakingDish.draw(g2);
					}
				}
				if (object.getType() == "sinkcabinet") {
					if (state.ordinal() < ProcessState.MILK.ordinal()) {
					sugar.draw(g2);
					}
				}
			} 
		}
		snowflake.draw(g2);
		drawTextBar(g2);
		sun.draw(g2);
	}
	
	// helper for setting a state with a slight delay
	private void changeIngredientState(ProcessState s) {
		if (ingredientTimer < I_DELAY) {
            ingredientTimer++;
        } else {
        	state = s;
            ingredientTimer = 0;
        }
	}
	
	// helper for ingredient sound handling used in ingredientSound()
	private void soundHandling(ClickableObject o,AudioPlayer a) {
		if (o.isCollided()) {
			if (!o.isSoundPlayed()) {
				a.play(0);
				o.setSoundPlayed(true);
			}
		}
	}
	
	// setting up sounds for ingredients
	private void ingredientSound() {
		if (state == ProcessState.EGGS) {
			soundHandling(eggs,eggCracking);
		}
		if (state == ProcessState.SUGAR) {
			soundHandling(sugar,ingredient);
		}
		if (state == ProcessState.MILK) {
			soundHandling(milk,milkPour);
		}
		if (state == ProcessState.FLOUR) {
			soundHandling(flour,ingredient);
		}
		if (state == ProcessState.MIX_BAKINGDISH) {
			soundHandling(bowl,ingredient);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// set mixing bowl at the counter if collides with a cabinet counter
		if (state == ProcessState.BOWL) {
			if (cabinet1.detectCollision(bowl)) {
				bowl.setLocationX(320);
				bowl.setLocationY(430);
				plate.play(0);
				state = ProcessState.EGGS;
			}	
		}
		// set baking dish at the counter if collides with a cabinet counter
		if (state == ProcessState.BAKINGDISH) {
			if (cabinet2.detectCollision(bakingDish)) {
				bakingDish.setLocationX(160);
				bakingDish.setLocationY(430);
				plate.play(0);
				state = ProcessState.MIX_BAKINGDISH;		
			}
		} 
		// if baking dish collides with oven door -> play sound and change state
		if (state == ProcessState.OVEN) {
			if (oven.detectCollision(bakingDish)) {
				open.play(0);
				state = ProcessState.MIX_OVEN;
			}
		}
		// create a delay when mix is in the oven
		// after delay oven is opened (state CAKE_READY)
		if (state == ProcessState.MIX_OVEN) {
			if (bakeTimer < CAKE_DELAY) {
				bakeTimer++;
			} else {
				state = ProcessState.CAKE_READY;
				ovenReady.play(0);
				bakingDish.setImg("assets/cakebakingdish.png");
				bakingDish.setLocationX(oven.getXPos());
				bakingDish.setLocationY(oven.getYPos()+32);
				bakeTimer = 0;
			}
		}
		// if baking dish with a cake collides with a cabinet -> place it on the counter and display "decorate" button
		if (state == ProcessState.BAKE_FINISH) {
			if (cabinet1.detectCollision(bakingDish)) {
				bakingDish.setLocationX(320);
				bakingDish.setLocationY(430);
				plate.play(0);
				oven.setImg("assets/closedoven.png");
				state = ProcessState.DECORATE_BUTTON;
			}
		}
		ingredientSound();
		repaint();
	}
	
	// draws a bar with different text depending on the level (text is generated in Text class)
	private void drawTextBar(Graphics2D g) {
	    Font f = new Font("Chalkboard", Font.BOLD, 17);
	    g.setFont(f);
	    g.setColor(Color.WHITE);
	    g.setStroke(new BasicStroke(6));
	    g.drawRect(2, 660, 1025, 250);
	    g.setColor(new Color(183,103,86));
	    g.fillRect(2, 660, 1025, 250);
	    g.setColor(Color.WHITE);
	    if (state == ProcessState.DECORATE_ICING) {
	    	g.drawString(Text.setText(state), 50, 720);
	    } else if (state == ProcessState.DECORATE_SPRINKLES) {
	    	int yOffset = 705;
	    	String[] lines = Text.setText(state).split("\n");
	        for (String line : lines) {
	            g.drawString(line, 50, yOffset);
	            yOffset += g.getFontMetrics().getHeight();
	        }
	    }
	    else {
		    g.drawString(Text.setText(state), 100, 720);
	    }
	}
	
	// helper to check if object is within cake area radius
	private boolean isWithinCakeArea(float x, float y) {
		   Ellipse2D.Double ellipse = (Ellipse2D.Double) cakeArea;
		   return ellipse.contains(x, y);
	}
	
	public class MyMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			
			if (state == ProcessState.START && startButton.clicked(mouseX, mouseY)) {
				//state = ProcessState.DECORATE_ICING;
				state = ProcessState.INTRO;
				click.play(0);
				kitchen.loop();
			} 
			if (state == ProcessState.INTRO) {
	            if (mouseX >= 0 && mouseX <= W_WIDTH && mouseY >= 660 && mouseY <= W_HEIGHT) {
					state = ProcessState.BOWL;
					click.play(0);
	            }
			}
			if (state == ProcessState.DECORATE_BUTTON && decorateButton.clicked(mouseX, mouseY)) {
				state = ProcessState.DECORATE_ICING;
				click.play(0);
			}
			// ensure that only one piper at a time can be selected 
			if (state == ProcessState.DECORATE_ICING) {
                	for (ClickableObject p: pipers) {
                		if (!isObjectClicked) {
                			if (p.clicked(mouseX, mouseY)) {
                				piperSound.play(0);
                				clickedObject = p;
                				clickedObject.rotate();
                				isObjectClicked = true;
                			}
                		} else {
                			// if piper collides with a cake area, create an appropriate icing decorator on top
                			if (p.detectCollision(cakeArea) && p.clicked(mouseX, mouseY)) {
                				basicCake = factory.createIcing(p.getType(), basicCake);
                				decorSound.play(1);
                				isObjectClicked = false;
                		        clickedObject = null;
                				state = ProcessState.DECORATE_SPRINKLES;
                				return;
                			}
                		}
                	}
                	handleObjectDoubleClick(e);                
			}
			// only one sprinkle har can be selected at a time
			if (state == ProcessState.DECORATE_SPRINKLES) {
            	for (ClickableObject j: jars) {
            		if (!isObjectClicked) {
            			if (j.clicked(mouseX, mouseY)) {
            				sprinkleSound.play(0);
            				clickedObject = j;
            				clickedObject.rotate(Math.PI/3);
            				isObjectClicked = true;
            			}
            		} else {
            			// if sprinkle jar collides with a cake area -> add new sprinkle on click within that area
            			if (j.detectCollision(cakeArea) && j.clicked(mouseX, mouseY)) {
            				float sprinkleX = (float) mouseX + (float) j.getWidth();
            			    float sprinkleY = (float) mouseY - (float) j.getHeight()/3;
            			    if (isWithinCakeArea(sprinkleX, sprinkleY)) {
            			        basicCake = factory.createSprinkle(j.getType(), basicCake, new PVector(sprinkleX, sprinkleY));
                				decorSound.play(1);
            			    }
            			}
            		}
            	}
            	handleObjectDoubleClick(e); 
            	// only one decoration at a time can be clicked
            	for (ClickableObject d: decorations) {
            		if (!isDecorClicked) {
            			if (d.clicked(mouseX, mouseY)) {
            				decorSound.play(0);
            				clickedDecor = d;
            				isDecorClicked = true;
            			}
            		} else {
            			// if decoration collides with a cake area -> set decoration position within that area
            			if (d.detectCollision(cakeArea) && d.clicked(mouseX, mouseY)) {
            				if (isWithinCakeArea((float)mouseX,(float)mouseY)) {
            					d.setLocationX(mouseX);
            					d.setLocationY(mouseY);
            					cakeDecorations.add(d);
                				decorSound.play(1);
            					isDecorClicked = false;
                		        clickedDecor = null;
                		        return;
            				}
            			}
            		}
            	}
            	if (doneButton.clicked(mouseX, mouseY)) {
            		state = ProcessState.RESTART;
        			kitchen.pause();
    				click.play(0);
    				success.play(0);
            	}
			}
			if (state == ProcessState.RESTART && restartButton.clicked(mouseX, mouseY)) {
				click.play(0);
            	frame.dispose();
            	BentoCakeApp bentoCakeApp = new BentoCakeApp("Making a Bento Cake");
            	frame = bentoCakeApp;
            }
		}
		
		// put object back if double clicked
		private void handleObjectDoubleClick(MouseEvent e) {
		    if (e.getClickCount() == 2 && clickedObject!=null) {
		        clickedObject.rotate(0);
		        resetObjectPosition();
		        if (pipers.contains(clickedObject)) {
					piperSound.play(0);
		        }
		        else if (jars.contains(clickedObject)) {
					sprinkleSound.play(0);
		        }
		        isObjectClicked = false;
		        clickedObject = null;
		    }
		}
		
	}
	
	public class MyMouseMotionListener extends MouseMotionAdapter {
		public void mouseMoved(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			
			// if an openable object is hovered -> open
			for (OpenableObject object : openableObjects) {
		        if (state.ordinal() < ProcessState.MIX_OVEN.ordinal() && state != ProcessState.START && object.isHovered(mouseX, mouseY)) {
		            if (!object.isSoundPlayed()) {
		                open.play(0);
		                object.setSoundPlayed(true);
		            }
		            object.openObject();
		        } else if (state.ordinal() < ProcessState.MIX_OVEN.ordinal()) {
		            object.closeObject();
		            object.setSoundPlayed(false); // reset soundPlayed when not hovered
		        }
		    }
			// if piper or jar is clicked follow mouse location
			if (state == ProcessState.DECORATE_ICING || state == ProcessState.DECORATE_SPRINKLES) {
				 if (isObjectClicked) {
					 clickedObject.setLocationX(mouseX);
		             clickedObject.setLocationY(mouseY);
		             repaint();

				 }
			}
			// if decoration is clicked follow mouse location
			if (state == ProcessState.DECORATE_SPRINKLES) {
				if (isDecorClicked) {
					clickedDecor.setLocationX(mouseX);
					clickedDecor.setLocationY(mouseY);
					repaint();
				}
			}
		}
		
		public void mouseDragged(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			if (state == ProcessState.BOWL || state == ProcessState.MIX_BAKINGDISH) {
				dragObject(bowl);
			} else if (state == ProcessState.EGGS) {
				dragObject(eggs);
			} else if (state == ProcessState.SUGAR) {
				dragObject(sugar);
			} else if (state == ProcessState.MILK) {
				dragObject(milk);
			} else if (state == ProcessState.FLOUR) {
				dragObject(flour);
			} else if (state == ProcessState.BAKINGDISH || state == ProcessState.OVEN || state == ProcessState.BAKE_FINISH) {
				dragObject(bakingDish);
			} 
		}
	}
	
	// dragging method to avoid repetition in mouseDragged
	private void dragObject(ClickableObject object) {
	    if (object.clickedRadius(mouseX, mouseY)) {
	        object.setDragged(true);
	        double dx = mouseX - object.getXPos();
	        double dy = mouseY - object.getYPos();
	        object.setLocationX(object.getXPos() + DRAG_SPEED * dx);
	        object.setLocationY(object.getYPos() + DRAG_SPEED * dy);
	    }
	}
	
	// reset clicked object to original location
	private void resetObjectPosition() {
		if (clickedObject != null) {
			clickedObject.setLocationX(clickedObject.getOriginalX());
	        clickedObject.setLocationY(clickedObject.getOriginalY());
	     }
	}
}
