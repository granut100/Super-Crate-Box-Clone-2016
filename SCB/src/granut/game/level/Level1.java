package granut.game.level;

import granut.game.entitys.Bullet;
import granut.game.entitys.Crate;
import granut.game.entitys.End;
import granut.game.entitys.Entity;
import granut.game.entitys.Explosion;
import granut.game.entitys.Goblin;
import granut.game.entitys.Player;
import granut.game.tilemap.TileMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Level1 extends Level {

	TileMap map;
	Player player;
	Goblin goblin;
	End end;
	Crate crate;
	Bullet bullet;
	ArrayList<Entity>entitys;
	Explosion explosion;
	
	public static final int SCALE = 1;
	public static final int INV_SCALE = 3;
	
	long tStart;
	long tEnd;
	
	int wave1,wave2,wave3;
	
	@Override
	public void init() {
		map = new TileMap();
		tStart = System.currentTimeMillis();
		entitys = new ArrayList<Entity>();
		map.stream = TileMap.class.getClass().getResourceAsStream("/map.txt");
		map.reader = new InputStreamReader(map.stream);
		map.in = new BufferedReader(map.reader);
		String line = "";
		char a;
		int rowNum = 0;
		int colNum = 0;
			try {
				while((line = map.in.readLine()) != null && colNum < TileMap.maxColNum) {
					while(rowNum < TileMap.maxRowNum) {
						a = line.charAt(rowNum);
						if(a == '2') {
							player = new Player(rowNum*16,colNum*16,16,16,"PLAYER",map);
							entitys.add(player);
						}
						if(a == '3') {
							goblin = new Goblin(rowNum*16,colNum*16,16,16,"GOBLIN",map,false);
							entitys.add(goblin);
						}
						if(a == '4') {
							end = new End(rowNum*16,colNum*16,16,16,"END",map);
							entitys.add(end);
						}
						if(a == '5') {
							crate = new Crate(rowNum*16,colNum*16,16,16,"CRATE",map);
							entitys.add(crate);
						}
						rowNum++;
					}
					rowNum = 0;
					colNum++;
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.magenta);
		g2d.scale(SCALE, SCALE);
		g2d.scale(INV_SCALE,INV_SCALE);
		map.render(g2d);
		g2d.drawString("Crates: " + player.numCrates,20,15);
		g2d.drawString("Weapon:" + player.weaponType,20,25);
		for(int i = 0;i < entitys.size();i++) {
			if(entitys.get(i).getId() == "PLAYER") {
				entitys.get(i).render(g2d);
			}
			if(entitys.get(i).getId() == "GOBLIN") {
				entitys.get(i).render(g2d);
			}
			if(entitys.get(i).getId() == "END") {
				entitys.get(i).render(g2d);
			}
			if(entitys.get(i).getId() == "CRATE") {
				entitys.get(i).render(g2d);
			}
			if(entitys.get(i).getId() == "BULLET") {
				entitys.get(i).render(g2d);
			}
			if(entitys.get(i).getId() == "EXPLOSION") {
				entitys.get(i).render(g2d);
			}
		}
	}

	@Override
	public void update() {
		for(int i = 0;i < entitys.size();i++) {
			if(entitys.get(i).getId() == "PLAYER") {
				entitys.get(i).update(entitys);
			}
			if(entitys.get(i).getId() == "CRATE") {
				entitys.get(i).update(entitys);
			}
			if(entitys.get(i).getId() == "END") {
				entitys.get(i).update(entitys);
			}
			if(entitys.get(i).getId() == "GOBLIN") {
				entitys.get(i).update(entitys);
			}
		}
		for(int i = 0;i < entitys.size();i++) {
			if(entitys.get(i).getId() == "BULLET") {
				entitys.get(i).update(entitys);
			}
		}
		for(int i = 0;i < entitys.size();i++) {
			if(entitys.get(i).getId() == "EXPLOSION") {
				entitys.get(i).update(entitys);
			}
		}
		/*
		 * Spawn New Mobs on Time Interval.
		 */
		tEnd = System.currentTimeMillis();
		long tDelta = tEnd - tStart;
		double elapsedSeconds = tDelta / 1000.0;
		
		if(elapsedSeconds >= 5 && wave1 < 5) {
			System.out.println("wave1");
			goblin = new Goblin(7*16,1*16,16,16,"GOBLIN",map,false);
			entitys.add(goblin);
			wave1++;
			elapsedSeconds = 0;
			tStart = System.currentTimeMillis();
		}
		else if(elapsedSeconds >= 2 && wave2 < 10 && wave1 >= 5) {
			System.out.println("wave2");
			goblin = new Goblin(7*16,1*16,16,16,"GOBLIN",map,false);
			entitys.add(goblin);
			wave2++;
			elapsedSeconds = 0;
			tStart = System.currentTimeMillis();
		}
		else if(elapsedSeconds >= 1 && wave1 >= 5 && wave2 >= 10) {
			System.out.println("wave3");
			goblin = new Goblin(7*16,1*16,16,16,"GOBLIN",map,false);
			entitys.add(goblin);
			wave3++;
			elapsedSeconds = 0;
			tStart = System.currentTimeMillis();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
		int k = e.getKeyCode();
		if(k == KeyEvent.VK_ESCAPE)System.exit(-1);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
	}

}
