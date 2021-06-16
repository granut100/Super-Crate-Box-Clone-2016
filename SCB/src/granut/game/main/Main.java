package granut.game.main;

import granut.game.level.Level1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JPanel;

public class Main extends JPanel implements Runnable, KeyListener {
	
	Thread thread;
	
	boolean running;
	
	BufferStrategy buffer;
	
	int frames,updates;
	
	Level1 level1;
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			requestFocus();
			thread.start();
		}
	}
	
	public void init() {
		level1 = new Level1();
		level1.init();
		running = true;
	}

	@Override
	public void run() {
		init();
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60.0;
		long lastTime1 = System.currentTimeMillis();
		boolean shouldRender = false;
		while(running) {
			long now = System.nanoTime();
			unprocessed += (now-lastTime) / nsPerTick;
			lastTime = now;
			while(unprocessed >= 1) {
				updates++;
				update();
				unprocessed -= 1;
				shouldRender = true;
			}
			if(shouldRender) {
				render();
				frames++;
			}
			try {
				Thread.sleep(2);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			if(System.currentTimeMillis() - lastTime1 > 1000) {
				lastTime1 += 1000;
				System.out.println("Frames: " + frames + " Updates: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}
	
	public void render() {
		buffer = Game.frame.getBufferStrategy();
		if(buffer == null) {
			Game.frame.createBufferStrategy(3);
			return;
		}
		Graphics2D g2d = (Graphics2D) buffer.getDrawGraphics();
		g2d.translate(3,26);
		g2d.setColor(Color.darkGray);
		g2d.fillRect(0,0,Game.WIDTH,Game.HEIGHT);
		level1.render(g2d);
		g2d.dispose();
		buffer.show();
	}
	
	public void update() {
		level1.update();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		level1.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		level1.keyReleased(e);
	}

}
