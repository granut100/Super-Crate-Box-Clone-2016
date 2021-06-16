package granut.game.entitys;

import granut.game.tilemap.TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Bullet extends Entity {

	String dir;
	
	boolean right,left,down,up;
	
	double speed,stray;
	
	double size;
	
	BufferedImage bullet;
	
	int amount;
	
	public Bullet(double x, double y, int width, int height, String id,
			TileMap map,String dir,double speed,double stray) {
		super(x, y, width, height, id, map);
		this.dir = dir;
		this.speed = speed;
		this.stray = stray;
		loadImage("/spritesheet.png");
		bullet = image.getSubimage(16,16,16,16);
	}

	@Override
	public void render(Graphics2D g2d) {
		//g2d.setColor(Color.black);
		//g2d.fillRect((int)x-8,(int)y-2,width/2,height/2);
		if(dir == "LEFT" || dir == "RIGHT")g2d.drawImage(bullet,(int)x-8,(int)y-2,width,height,null);
	}

	@Override
	public void update(ArrayList<Entity> entitys) {
		moveX();
		moveY();
		if(dir == "RIGHT") {
			size = 0.1f;
			right = true;
		}
		else if(dir == "LEFT") {
			size = 0.3f;
			left = true;
		}
		if(stray > 0) {
			Random rand = new Random();
			float randY = rand.nextFloat();
			int neg = rand.nextInt(2);
			if(neg == 0)randY = -randY;
			if(amount < 10) {
				for(int b = 0;b < 5;b++) {
					Bullet b1 = new Bullet((int)x,(int)y,width,height,"BULLET",map,this.dir,this.speed,0);
					entitys.add(b1);
					b1.move(randY*5,randY*5);
					amount++;
				}
			}
		}
		for(int i = 0;i < entitys.size();i++) {
			if(entitys.get(i).getId() == "GOBLIN") {
				Goblin o = (Goblin)entitys.get(i);
				if(isColliding(this,entitys.get(i))) {
					//o.remove = true;
					o.isDead = true;
					remove = true;
				}
			}
		}
		for(int i = 0;i < entitys.size();i++) {
			if(entitys.get(i).getId() == "BULLET") {
				Bullet o = (Bullet)entitys.get(i);
				if(o.remove)entitys.remove(i);
			}
		}
	}
	
	public void moveX() {
		double dx = 0;
		if(right) {
			dx += speed;
		}
		if(left) {
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
			remove = true;
			return false;
		}
		if(map.blocked(nx + size, ny - size)) {
			remove = true;
			return false;
		}
		if(map.blocked(nx - size, ny + size)) {
			remove = true;
			return false;
		}
		if(map.blocked(nx + size, ny + size)) {
			remove = true;
			return false;
		}
		return true;
	}

}
