package granut.game.entitys;

import granut.game.tilemap.TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public abstract class Entity {
	
    double x,y;
	double velX,velY;
	int width,height;
	String id;
	TileMap map;
	public boolean remove;
	BufferedImage image;
	
	public Entity(double x,double y,int width,int height,String id,TileMap map) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		this.map = map;
	}
	
	public void loadImage(String s) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void render(Graphics2D g2d);
	
	
	public abstract void update(ArrayList<Entity>entitys);

	public boolean isColliding(Entity a, Entity b) {
		if((a.x+a.width-6) >= (b.x) && (a.x) <= (b.x + b.width-6) &&
				(a.y+a.height) >= (b.y) && (a.y) <= (b.y+b.height)) {
			return true;
		} else {
			return false;
		}
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getVelX() {
		return velX;
	}

	public void setVelX(double velX) {
		this.velX = velX;
	}

	public double getVelY() {
		return velY;
	}

	public void setVelY(double velY) {
		this.velY = velY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
