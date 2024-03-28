package snakegame;

import javax.swing.JFrame;

public class App {

	public static void main(String args[]) {
		int boardWidth=600;
		int boardHeight=boardWidth;
		JFrame frame1=new JFrame("Snake Game");
		frame1.setVisible(true);
		frame1.setSize(boardWidth,boardHeight);
		frame1.setLocationRelativeTo(null);
		frame1.setResizable(false);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SnakeGame snakeGame=new SnakeGame(boardWidth, boardHeight);
		frame1.add(snakeGame);
		frame1.pack();
		snakeGame.requestFocus();
		
	}
	
}
