package granut.game.entitys;

import granut.game.tilemap.TileMap;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Mob extends Entity {

	double size = 0.40f;
	
	float gravity = 1.6f;
	
	boolean right,left,down,up;
	
	double speed;
	
	int changeDir;
	
	boolean facingRight,facingLeft;
	
	public boolean isDead;
	
	float rot;
	
	AffineTransform tx;
	
	public Mob(double x, double y, int width, int height, String id, TileMap map) {
		super(x, y, width, height, id, map);
	}

	@Override
	public void render(Graphics2D g2d) {
		if(isDead) {
			tx = g2d.getTransform();
			g2d.rotate(rot,x,y);
		}
	}

	@Override
	public void update(ArrayList<Entity> entitys) {
		if(!isDead) {
			moveX();
			moveY();
		}
		if(isDead) {
			rot += 0.2;
			if(rot >= 6)rot = 0;
			y += 2;
		}
	}
	
	public void moveX() {
		double dx = 0;
		if(right) {
			dx += speed;
			facingRight = true;
			facingLeft = false;
		}
		if(left) {
			facingLeft = true;
			facingRight = false;
			dx -= speed;
		}
		if(dx != 0) {
			move(dx,0);
		}
	}
	
	public void moveY() {
		double dy = 0;
		if(up) {
			dy -= speed;
		}
		dy += gravity;
		if(dy != 0) {
			move(0,dy);
		}
	}
	
	public boolean move(double dx,double dy) {
		double nx = x + dx;
		double ny = y + dy;
		if(validLocation(nx/TileMap.TILE_SIZE,ny/TileMap.TILE_SIZE)) {
			x = nx;
			y = ny;
		}
		return false;
	}
	
	public boolean validLocation(double nx,double ny) {
		if(map.blocked(nx - size, ny - size)) {
			changeDir = 1;
			return false;
		}
		if(map.blocked(nx + size, ny - size)) {
			changeDir = 0;
			return false;
		}
		if(map.blocked(nx - size, ny + size)) {
			return false;
		}
		if(map.blocked(nx + size, ny + size)) {
			return false;
		}
		return true;
	}

}
