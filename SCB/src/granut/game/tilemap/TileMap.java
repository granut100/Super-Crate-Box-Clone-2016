package granut.game.tilemap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class TileMap {
	
	public static final int CLEAR = 0;
	
	public static final int BLOCKED = 1;
	
	public static final int maxRowNum = 15;
	
	public static final int maxColNum = 13;
	
	public static final int TILE_SIZE = 16;
	
	private int[][] data = new int[maxRowNum][maxColNum];
	
	public InputStream stream;
	public Reader reader;
	public BufferedReader in;
	
	public TileMap() {
		stream = TileMap.class.getClass().getResourceAsStream("/map.txt");
		reader = new InputStreamReader(stream);
		in = new BufferedReader(reader);
		String line = "";
		char a;
		int rowNum = 0;
		int colNum = 0;
			try {
				while((line = in.readLine()) != null && colNum < maxColNum) {
					while(rowNum < maxRowNum) {
						a = line.charAt(rowNum);
						if(a != '1') {
							getData()[rowNum][colNum] = CLEAR;
						}
						if(a == '1') {
							getData()[rowNum][colNum] = BLOCKED;
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
	
	public void render(Graphics2D g2d) {
		for(int x = 0;x < maxRowNum;x++) {
			for(int y = 0;y < maxColNum;y++) {
				if(getData()[x][y] == BLOCKED) {
					g2d.setColor(Color.BLACK);
					g2d.fillRect(x*TILE_SIZE,y*TILE_SIZE,TILE_SIZE,TILE_SIZE);
				}
				if(getData()[x][y] == CLEAR) {
					
				}
			}
		}
	}
	
	public boolean blocked(double x,double y) {
		return getData()[(int)x][(int)y] == BLOCKED;
	}
	
	public int[][] getData() {
		return data;
	}
	
	public void setData(int[][] data) {
		this.data = data;
	}
}
