package granut.game.animate;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
	
	ArrayList<BufferedImage> frames;
	
	public BufferedImage sprite;
	
	private volatile boolean running = false;
	
	private long previousTime,speed;
	
	private int frameAtPause,currentFrame;
	
	public boolean isPlaying;
	
	public Animation(ArrayList<BufferedImage> frames) {
		this.frames = frames;
	}
	
	public void setSpeed(long speed) {
		this.speed = speed;
	}
	
	public void update(long time) {
		if(running) {
			if(time - previousTime >= speed) {
				currentFrame++;
				try {
					sprite = frames.get(currentFrame);
				} catch(IndexOutOfBoundsException e) {
					currentFrame = 0;
					sprite = frames.get(currentFrame);
				}
				previousTime = time;
			}
		}
	}
	
	public void play() {
		isPlaying = true;
		running = true;
		previousTime = 0;
		frameAtPause = 0;
		currentFrame = 0;
	}
	
	public void stop() {
		isPlaying = false;
		running = false;
		previousTime = 0;
		frameAtPause = 0;
		currentFrame = 0;
	}
	
	public void pause() {
		isPlaying = false;
		frameAtPause = currentFrame;
		running = false;
	}
	
	public void resume() {
		isPlaying = true;
		currentFrame = frameAtPause;
		running = true;
	}

}
