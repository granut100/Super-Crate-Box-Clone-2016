package granut.game.entitys;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import granut.game.tilemap.TileMap;

public class End extends Entity {

	public End(double x, double y, int width, int height, String id, TileMap map) {
		super(x, y, width, height, id, map);
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.magenta);
		g2d.drawRect((int)x,(int)y,width,height);
	}

	@Override
	public void update(ArrayList<Entity> entitys) {
		for(int i = 0;i < entitys.size();i++) {
			if(entitys.get(i).getId() == "GOBLIN") {
				Goblin o = (Goblin)entitys.get(i);
				if(isColliding(this,o)) {
					o.remove = true;
					if(!o.isDead)o.bad = true;
				}
			}
		}
	}

}
