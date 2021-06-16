package granut.game.entitys;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import granut.game.tilemap.TileMap;

public class Explosion extends Entity {

	Color color;

	public Explosion(double x, double y, int width, int height, String id,
			TileMap map) {
		super(x, y, width, height, id, map);
		color = new Color(0,160,10);
	}

	@Override
	public void render(Graphics2D g2d) {
		
	}

	@Override
	public void update(ArrayList<Entity> entitys) {
		
	}

}
