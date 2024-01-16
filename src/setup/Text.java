package setup;

import main.BentoCakePanel.ProcessState;

public class Text {

	public static String setText(ProcessState state) {
		String text = "Hello world!";
		if (state == ProcessState.INTRO) {
			text = new String("Welcome to the Kitchen! Hover on the objects to open them. Click on this bar to start.");
		} else if (state == ProcessState.BOWL) {
			text = new String("Let's go! Start by picking up a cute mixing bowl and placing it on the kitchen counter.");
		} else if (state == ProcessState.EGGS) {
			text = new String("Now, add eggs to the bowl.");
		} else if (state == ProcessState.SUGAR) {
			text = new String("It's time for sugar!");
		} else if (state == ProcessState.MILK) {
			text = new String("Pour some milk to the mix.");
		} else if (state == ProcessState.FLOUR) {
			text = new String("And the last ingredient! Flour!");
		} else if (state == ProcessState.BAKINGDISH) {
			text = new String("Done with the mix! Now, pick up a baking dish and place it on the counter.");
		} else if (state == ProcessState.MIX_BAKINGDISH) {
			text = new String("Pour the mix into the baking dish!");
		}  else if (state == ProcessState.OVEN) {
			text = new String("Let's bake now. Put the baking dish into the oven!");
		} else if (state == ProcessState.MIX_OVEN) {
			text = new String("Baking now! Please wait...");
		}  else if (state == ProcessState.CAKE_READY) {
			text = new String("The cake is ready! Wait for it to cool down first.");
		}  else if (state == ProcessState.BAKE_FINISH) {
			text = new String("Great, now pick up the cake and place it on the counter.");
		} else if (state == ProcessState.DECORATE_BUTTON) {
			text = new String("");
		} else if (state == ProcessState.DECORATE_ICING) {
			text = new String("Click to select an icing piper, move it closer to the cake and click again to decorate (double-click = unselect).");
		} else if (state == ProcessState.DECORATE_SPRINKLES) {
			String line = new String("1.Add sprinkles (click to select, click to add, double-click to unselect).");
			String line2 = new String("2.Add bigger decorations (click to select, click to add).");
			text = new String(line + "\n" + line2);

		}
		
		return text;
	}
}
