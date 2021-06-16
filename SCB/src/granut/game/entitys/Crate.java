package granut.game.entitys;

import granut.game.tilemap.TileMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Crate extends Mob {

	Random rand;
	int oldPos;
	BufferedImage crate;
	
	public Crate(double x, double y, int width, int height, String id,
			TileMap map) {
		super(x, y, width, height, id, map);
		loadImage("/spritesheet.png");
		crate = image.getSubimage(96,16,16,16);
	}
	
	public void render(Graphics2D g2d) {
		//g2d.setColor(Color.blue);
		//g2d.drawRect((int)x-8,(int)y-8,width,height);
		g2d.drawImage(crate,(int)x-8,(int)y-8,width,height,null);
	}
	
	public void update(ArrayList<Entity>entitys) {
		super.update(entitys);
		
		for(int i = 0;i < entitys.size();i++) {
			if(entitys.get(i).getId() == "PLAYER") {
				Player o = (Player)entitys.get(i);
				if(isColliding(this,entitys.get(i))) {
					rand = new Random();
					int newPosX = 0,newPosY = 0;
					int newPos = rand.nextInt(6);
					switch(newPos) {
					case 0:
						newPosX = 11;
						newPosY = 4;
						break;
					case 1:
						newPosX = 2;
						newPosY = 1;
						break;
					case 2:
						newPosX = 2;
						newPosY = 9;
						break;
					case 3:
						newPosX = 5;
						newPosY = 1;
						break;
					case 4:
						newPosX = 10;
						newPosY = 1;
						break;
					case 5:
						newPosX = 12;
						newPosY = 9;
						break;
					case 6:
						newPosX = 3;
						newPosY = 7;
					}
					if(newPos == oldPos) {
						return;
					}
					newPos = oldPos;
					System.out.println(newPos);
					x = newPosX*16;
					y = newPosY*16;
					o.numCrates += 1;
					rand = new Random();
					o.weaponType = rand.nextInt(5);
					if(o.weaponType == 0)o.weaponType = 1;
				}
			}
		}
	}

}
