package granut.game.level;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public abstract class Level {
	
	public abstract void init();
	
	public abstract void render(Graphics2D g2d);
	
	public abstract void update();
	
	public abstract void keyPressed(KeyEvent e);
	
	public abstract void keyReleased(KeyEvent e);
	
}
