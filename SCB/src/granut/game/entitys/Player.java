package granut.game.entitys;

import granut.game.animate.Animation;
import granut.game.tilemap.TileMap;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Player extends Entity {

	float gravity = 1.6f;
	
	double size = .40f;
	
	boolean right,left,up;
	
	double speed;
	
	boolean jumping,grounded,ceiling;
	
	double maxJumpHeight,jumpTime;
	
	TileMap map;
	
	boolean shootLeft,shootRight;
	
	public int numCrates;
	
	public int weaponType;
	
	int reloadCounter,reloadMax,bulletSpeed,bulletStray;
	
	boolean facingRight,facingLeft;
	
	Animation anim;
	
	BufferedImage spriteIdle,spriteRun1,spriteRun2,spriteRun3,spriteRun4,spriteRun5,spriteRun6,spriteRun7;
	BufferedImage weapon1,weapon2,weapon3,weapon4;
	
	AffineTransform tx;
	
	double rotLeft,rotRight;
	
	public Player(double x, double y, int width, int height,String id,TileMap map) {
		super(x, y, width, height,id,map);
		this.map = map;
		maxJumpHeight = 6;
		reloadMax = 100;
		reloadCounter = reloadMax;
		speed = 1;
		loadImage("/spritesheet.png");
		spriteIdle = image.getSubimage(0,0,16,16);
		spriteRun1 = image.getSubimage(16,0,16,16);
		spriteRun2 = image.getSubimage(32,0,16,16);
		spriteRun3 = image.getSubimage(48,0,16,16);
		spriteRun4 = image.getSubimage(64,0,16,16);
		spriteRun5 = image.getSubimage(80,0,16,16);
		spriteRun6 = image.getSubimage(96,0,16,16);
		spriteRun7 = image.getSubimage(112,0,16,16);
		weapon1 = image.getSubimage(0,16,16,16);
		weapon2 = image.getSubimage(32,16,16,16);	
		weapon3 = image.getSubimage(48,16,16,16);
		weapon4 = image.getSubimage(64,16,32,16);
		ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
		sprites.add(spriteRun1);
		sprites.add(spriteRun2);
		sprites.add(spriteRun3);
		sprites.add(spriteRun4);
		sprites.add(spriteRun5);
		sprites.add(spriteRun6);
		sprites.add(spriteRun7);
		anim = new Animation(sprites);
		anim.setSpeed(40);
		facingRight = true;
	}

	@Override
	public void render(Graphics2D g2d) {
		//g2d.setColor(Color.red);
		//g2d.drawRect((int)x-8,(int)y-8,width,height);
		anim.update(System.currentTimeMillis());
		if(facingLeft && !left)g2d.drawImage(spriteIdle,(int)x-8,(int)y-8,width,height,null);
		if(facingRight && !right)g2d.drawImage(spriteIdle,(int)x-8+width,(int)y-8,-width,height,null);
		if(right && anim.isPlaying)g2d.drawImage(anim.sprite,(int)x-8+width,(int)y-8,-width,height,null);
		if(left && anim.isPlaying)g2d.drawImage(anim.sprite,(int)x-8,(int)y-8,width,height,null);
		if(weaponType == 1 && facingLeft)g2d.drawImage(weapon1,(int)x-12,(int)y-6,width,height,null);
		if(weaponType == 1 && facingRight)g2d.drawImage(weapon1,(int)x-6+width,(int)y-6,-width,height,null);
		if(weaponType == 3 && facingLeft) {
			tx = g2d.getTransform();
			if(shootLeft) {
				g2d.rotate(rotLeft,x,y);
				rotLeft += .2;
				if(rotLeft >= .4) {
					rotLeft = 0;
				}
			}
			g2d.drawImage(weapon2,(int)x-12,(int)y-6,width,height,null);
			g2d.setTransform(tx);
		}
		if(weaponType == 3 && facingRight) {
			tx = g2d.getTransform();
			if(shootRight) {
				//double rot = -.25;
				g2d.rotate(rotRight,x,y);
				//rot = 0;
				rotRight -= .2;
				if(rotRight <= -.4) {
					rotRight = 0;
				}
			}
			g2d.drawImage(weapon2,(int)x-6+width,(int)y-6,-width,height,null);
			g2d.setTransform(tx);
		}
		if(weaponType == 2 && facingLeft)g2d.drawImage(weapon3,(int)x-12,(int)y-6,width,height,null);
		if(weaponType == 2 && facingRight)g2d.drawImage(weapon3,(int)x-6+width,(int)y-6,-width,height,null);
		if(weaponType == 4 && facingLeft) {
			tx = g2d.getTransform();
			if(shootLeft) {
				g2d.rotate(rotLeft,x,y);
				rotLeft += .2;
				if(rotLeft >= .8) {
					rotLeft = 0;
				}
			}
			g2d.drawImage(weapon4,(int)x-12-width,(int)y-6,width*2,height,null);
			g2d.setTransform(tx);
		}
		if(weaponType == 4 && facingRight) {
			tx = g2d.getTransform();
			if(shootRight) {
				//double rot = -.25;
				g2d.rotate(rotRight,x,y);
				//rot = 0;
				rotRight -= .2;
				if(rotRight <= -.8) {
					rotRight = 0;
				}
			}
			g2d.drawImage(weapon4,(int)x-6+width*2,(int)y-6,-width*2,height,null);
			g2d.setTransform(tx);
		}
	}

	@Override
	public void update(ArrayList<Entity> entitys) {
		moveX();
		moveY();
		
		/*
		 * Weapon Types
		 * 0 = Nothing
		 * 1 = Pistol
		 * 2 = Shotgun
		 * 3 = Machine gun
		 * 4 = Minigun
		 */
		if(weaponType == 1) {
			reloadMax = 40;
			bulletSpeed = 2;
			bulletStray = 0;
		}
		else if(weaponType == 2) {
			reloadMax = 70;
			bulletSpeed = 2;
			bulletStray = 1;
		}
		else if(weaponType == 3) {
			reloadMax = 5;
			bulletSpeed = 2;
			bulletStray = 0;
		}
		else if(weaponType == 4) {
			reloadMax = 5;
			bulletSpeed = 2;
			bulletStray = 1;
		}
		if(reloadCounter < reloadMax) {
			reloadCounter++;
		}
		if(reloadCounter >= reloadMax)reloadCounter = reloadMax;
		if(reloadCounter >= reloadMax && weaponType != 0) {
			if(shootRight) {
				if(reloadCounter >= reloadMax) {
					Bullet bullet = new Bullet((int)x+12,(int)y,8,8,"BULLET",map,"RIGHT",bulletSpeed,bulletStray);
					entitys.add(bullet);
					reloadCounter = 0;
				}
				if(reloadMax > 10)shootRight = false;
			}
			if(shootLeft) {
				if(reloadCounter >= reloadMax) {
					Bullet bullet = new Bullet((int)x-4,(int)y,8,8,"BULLET",map,"LEFT",bulletSpeed,bulletStray);
					entitys.add(bullet);
					reloadCounter = 0;
				}
				if(reloadMax > 10)shootLeft = false;
			}
		}
		for(int i = 0;i < entitys.size();i++) {
			if(entitys.get(i).getId() == "GOBLIN") {
				Goblin o = (Goblin)entitys.get(i);
				if(isColliding(this,entitys.get(i)) && !o.isDead) {
					remove = true;
				}
			}
			if(entitys.get(i).getId() == "PLAYER") {
				if(remove)entitys.remove(i);
			}
		}
	}
	
	public void moveX() {
		double dx = 0;
		if(right) {
			facingRight = true;
			facingLeft = false;
			shootLeft = false;
			if(!anim.isPlaying) {
				anim.play();
			}
			dx += speed;
		}
		if(left) {
			facingLeft = true;
			facingRight = false;
			shootRight = false;
			if(!anim.isPlaying)anim.play();
			dx -= speed;
		}
		if(shootRight && facingRight && weaponType >= 3) {
			if(weaponType == 3)dx -= speed+.2;
			if(weaponType == 4)dx -= speed+.8;
		}
		else if(shootLeft && facingLeft && weaponType >= 3) {
			if(weaponType == 3)dx += speed+.2;
			if(weaponType == 4)dx += speed+.8;
		}
		if(dx != 0) {
			move(dx,0);
		}
	}
	
	public void moveY() {
		double dy = 0;
		if(up) {
			if(!jumping && grounded)jumping = true;
		}
		if(jumping) {
			grounded = false;
			dy -= maxJumpHeight - jumpTime;
			jumpTime += 0.2;
			if(jumpTime > maxJumpHeight) {
				jumpTime = 0;
				jumping = false;
			}
		}
		if(ceiling) {
			dy = 0;
			jumping = false;
			jumpTime = 0;
			ceiling = false;
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
		try {
			if(map.blocked(nx - size, ny - size)) {
				ceiling = true;
				return false;
			}
			if(map.blocked(nx + size, ny - size)) {
				return false;
			}
			if(map.blocked(nx - size, ny + size)) {
				grounded = true;
				return false;
			}
			if(map.blocked(nx + size, ny + size)) {
				return false;
			}
		} catch(Exception e) {
			//Move past borders
			e.printStackTrace();
		}
		return true;
	}
	
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		if(k == KeyEvent.VK_RIGHT) {
			right = true;
		}
		if(k == KeyEvent.VK_LEFT) {
			left = true;
		}
		if(k == KeyEvent.VK_UP) {
			up = true;
		}
		if(k == KeyEvent.VK_X) {
			if(facingRight)shootRight = true;
			if(facingLeft)shootLeft = true;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		if(k == KeyEvent.VK_RIGHT) {
			right = false;
		}
		if(k == KeyEvent.VK_LEFT) {
			left = false;
		}
		if(k == KeyEvent.VK_UP) {
			up = false;
		}
		if(k == KeyEvent.VK_X) {
			if(facingRight)shootRight = false;
			if(facingLeft)shootLeft = false;
		}
	}
}
