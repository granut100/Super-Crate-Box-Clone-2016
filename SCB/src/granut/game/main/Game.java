package granut.game.main;

import javax.swing.JFrame;

public class Game {
	
	static JFrame frame;
	//908 and 770
	public static final int WIDTH = 728;
	public static final int HEIGHT = 610;
	
	Game() {
		frame = new JFrame();
		frame.setSize(WIDTH,HEIGHT);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Main());
	}
	
	public static void main(String args[]) {
		new Game();
	}
}
