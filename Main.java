package snakeGame;

import java.awt.Color;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		// Creating the JFrame and adding the Game JPanel to it
		JFrame frame = new JFrame("Snake Game");
		Game board = new Game();
		
		// Setting up the window
		frame.setBounds(10, 10, 905, 700);
		frame.setBackground(Color.cyan);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(board);
		
	}

}
