// Snowflake object

package kitchen;

import java.awt.Color;
import java.awt.Graphics2D;

public class Snowflake {
	private int x;
    private int y;
    private int size;
    private int sides;
    
    public Snowflake(int x, int y, int size, int sides) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.sides = sides;
     }

    public void draw(Graphics2D g) {
        drawRecursiveSnowflake(g, x, y, size, 0);
    }

    private void drawRecursiveSnowflake(Graphics2D g, int x, int y, int size, int depth) {
        if (depth == 4) {
            return; 
        }
     
        double angleIncrement = 2 * Math.PI/sides;

        for (int i = 0; i < sides; i++) {
            double angle = i * angleIncrement;
            int endX = (int) (x + size * Math.cos(angle));
            int endY = (int) (y + size * Math.sin(angle));

            g.setColor(new Color(200,230,244));
            g.drawLine(x, y, endX, endY);
            drawRecursiveSnowflake(g, endX, endY, size/4, depth + 1);
        }
    }
}
