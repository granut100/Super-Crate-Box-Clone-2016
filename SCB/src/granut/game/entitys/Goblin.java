package granut.game.entitys;

import granut.game.animate.Animation;
import granut.game.tilemap.TileMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Goblin extends Mob {
	
	boolean feral;
	
	Random rand;
	
	boolean bad;
	
	BufferedImage goblin1,goblinWalk1,goblinWalk2,goblinWalk3,goblinWalk4,goblinWalk5,goblinWalk6,goblinWalk7;
	BufferedImage goblinFeral,goblinFeral2,goblinFeral3,goblinFeral4,goblinFeral5,goblinFeral6,goblinFeral7;
	
	Animation anim,anim2;
	
	public Goblin(double x, double y, int width, int height, String id,
			TileMap map,boolean feral) {
		super(x, y, width, height, id, map);
		this.feral = feral;
		speed = 0.75;
		rand = new Random();
		changeDir = rand.nextInt(2);
		loadImage("/spritesheet.png");
		goblin1 = image.getSubimage(0,32,width,height);
		goblinWalk1 = image.getSubimage(0,32,width,height);
		goblinWalk2 = image.getSubimage(16,32,width,height);
		goblinWalk3 = image.getSubimage(32,32,width,height);
		goblinWalk4 = image.getSubimage(48,32,width,height);
		goblinWalk5 = image.getSubimage(64,32,width,height);
		goblinWalk6 = image.getSubimage(96,32,width,height);
		goblinWalk7 = image.getSubimage(112,32,width,height);
		goblinFeral = image.getSubimage(0,48,width,height);
		goblinFeral2 = image.getSubimage(16,48,width,height);
		goblinFeral3 = image.getSubimage(32,48,width,height);
		goblinFeral4 = image.getSubimage(48,48,width,height);
		goblinFeral5 = image.getSubimage(64,48,width,height);
		goblinFeral6 = image.getSubimage(96,48,width,height);
		goblinFeral7 = image.getSubimage(112,48,width,height);
		ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
		sprites.add(goblinWalk1);
		sprites.add(goblinWalk2);
		sprites.add(goblinWalk3);
		sprites.add(goblinWalk4);
		sprites.add(goblinWalk5);
		sprites.add(goblinWalk6);
		sprites.add(goblinWalk7);
		ArrayList<BufferedImage> sprites2 = new ArrayList<BufferedImage>();
		sprites2.add(goblinFeral);
		sprites2.add(goblinFeral2);
		sprites2.add(goblinFeral3);
		sprites2.add(goblinFeral4);
		sprites2.add(goblinFeral5);
		sprites2.add(goblinFeral6);
		sprites2.add(goblinFeral7);
		anim = new Animation(sprites);
		anim2 = new Animation(sprites2);
		anim.setSpeed(60);
		anim2.setSpeed(60);
	}

	@Override
	public void render(Graphics2D g2d) {
		super.render(g2d);
		if(!feral)g2d.setColor(Color.green);
		if(feral)g2d.setColor(Color.red);
		if(facingRight && !right && !feral)g2d.drawImage(goblin1,(int)x-8,(int)y-8,width,height,null);
		if(facingLeft && !left && !feral)g2d.drawImage(goblin1,(int)x-8+width,(int)y-8,-width,height,null);
		if(right || left && !feral)anim.update(System.currentTimeMillis());
		if(right && !feral)g2d.drawImage(anim.sprite,(int)x-8,(int)y-8,width,height,null);
		if(left && !feral)g2d.drawImage(anim.sprite,(int)x-8+width,(int)y-8,-width,height,null);
		
		if(facingRight && !right && feral)g2d.drawImage(goblinFeral,(int)x-8,(int)y-8,width,height,null);
		if(facingLeft && !left && feral)g2d.drawImage(goblinFeral,(int)x-8+width,(int)y-8,-width,height,null);
		if(right || left && feral)anim2.update(System.currentTimeMillis());
		if(right && feral)g2d.drawImage(anim2.sprite,(int)x-8,(int)y-8,width,height,null);
		if(left && feral)g2d.drawImage(anim2.sprite,(int)x-8+width,(int)y-8,-width,height,null);
		if(isDead)g2d.setTransform(tx);
	}

	@Override
	public void update(ArrayList<Entity> entitys) {
		super.update(entitys);
		if(changeDir == 0) {
			right = false;
			left = true;
			if(!anim.isPlaying && !feral)anim.play();
			if(!anim2.isPlaying && feral)anim2.play();
		}
		if(changeDir == 1) {
			left = false;
			right = true;
			if(!anim.isPlaying && !feral)anim.play();
			if(!anim2.isPlaying && feral)anim2.play();	
		}
		
		if(feral) {
			speed = 1.25;
		}
		
		for(int i = 0;i < entitys.size();i++) {
			if(entitys.get(i).remove && entitys.get(i).getId() == "GOBLIN") {
				Goblin o = (Goblin)entitys.get(i);
				if(o.bad) {
					Goblin goblin = new Goblin(7*16,1*16,16,16,"GOBLIN",map,true);
					entitys.add(goblin);
				}
				entitys.remove(i);
			}
		}
	}

}
