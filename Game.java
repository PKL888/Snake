package snakeGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements KeyListener, ActionListener{

	// Auto-generated serial Version UID
	private static final long serialVersionUID = 4595047240824696372L;

	// Snake lengths
	private int[] snakeXLength = new int[750];
	private int[] snakeYLength = new int[750];

	Rectangle apple_rect;
	Rectangle snake_rect;

	// Direction booleans
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;

	// ImageIcon of the title bar
	private ImageIcon titleImage;

	// ImageIcons of the different parts of the snake
	private ImageIcon headLeft;
	private ImageIcon headRight;
	private ImageIcon headUp;
	private ImageIcon headDown;
	private ImageIcon body;

	// The length of the snake
	private int snakeLength = 3;

	// Timer class used to manage the speed of the snake
	private Timer timer;
	private int snakeDelay = 100;

	boolean game = true;

	// Amount of moves for the snake
	private int snakeMoves = 0;

	// ImageIcon for the fruit
	private ImageIcon apple = new ImageIcon("apple.png");

	// Fruit position (x, y)
	int fruitX = 100;
	int fruitY = 100;

	// Score
	int score = -1;

	// Crash boolean
	private boolean snakeCrash;

	// More things for the game window
	public Game() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		// Sets the Game Over panel visible if appropriate 
		if (snakeCrash) {
			setBackground(Color.GREEN);
		}

		// The speed for the snake
		timer = new Timer(snakeDelay, this);
		timer.start();
	}

	// Creating the playing area
	public void paint(Graphics g) {

		// Graphics2D g2D = (Graphics2D) g;

		// Checks if game has started; if affirmative --> then positions the snake accordingly
		if(game) {
			if (snakeMoves == 0) {

				// Snake X positions
				snakeXLength[2] = 50;
				snakeXLength[1] = 75;
				snakeXLength[0] = 100;

				// Snake Y positions
				snakeYLength[2] = 100;
				snakeYLength[1] = 100;
				snakeYLength[0] = 100;
			}

			// Draw the title image border
			g.setColor(Color.blue);
			g.drawRect(24, 10, 851, 55);

			// Draw the title images
			titleImage = new ImageIcon("title.png");
			titleImage.paintIcon(this, g, 25, 7);

			// Draw the border for the playing area
			g.setColor(Color.WHITE);
			g.drawRect(24, 74, 851, 577);

			// Draw background for the game
			g.setColor(Color.BLACK);
			g.fillRect(25, 75, 850, 575);

			// Draw scoreboard
			g.setColor(Color.BLUE);
			g.fillRect(768, 18, 100, 50);
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 15));
			g.drawString("Score: " + (score==-1?0:score), 775, 50);

			// Draw the snake itself
			headRight = new ImageIcon("head_right.png");
			headRight.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);

			// Manoeuvring the snake 
			for (int a = 0; a < snakeLength; a++) { 

				// Moving right
				if (a == 0 && right) {
					headRight = new ImageIcon("head_right.png");
					headRight.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
				}

				// Moving left
				if (a == 0 && left) {
					headLeft = new ImageIcon("head_left.png");
					headLeft.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
				}

				// Moving up
				if (a == 0 && up) {
					headUp = new ImageIcon("head_up.png");
					headUp.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
				}

				// Moving down
				if (a == 0 && down) {
					headDown = new ImageIcon("head_down.png");
					headDown.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
				}

				// Body of the snake
				if (a != 0) {
					body = new ImageIcon("body.png");
					body.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
				}
			}


			apple_rect = new Rectangle(fruitX, fruitY, apple.getIconWidth(), apple.getIconHeight());
			snake_rect = new Rectangle(snakeXLength[0], snakeYLength[0], body.getIconWidth(), body.getIconHeight());

			// Creating the fruit-apple (apple 1)
			if(apple_rect.intersects(snake_rect)) {
				fruitX = (int)(Math.random()*(850-apple.getIconWidth())) + 25;
				fruitY = (int)(Math.random()*(575-apple.getIconHeight())) + 75;
				snakeLength++;
				score++;
				snakeDelay--;
				timer.setDelay(snakeDelay);
			}

			apple.paintIcon(this, g, fruitX, fruitY);

			// Checks if the snake has crashed into itself
			for (int c = 1; c <= snakeLength; c++) {
				if (snakeXLength[0] == snakeXLength[c] && snakeYLength[0] == snakeYLength[c]) {
					snakeCrash = true;
				}
			}

		}

		if(snakeDelay == 1) {
			game = false;
			g.setColor(Color.magenta);
			g.setFont(new Font("Arial Rounded MT", Font.BOLD, 100));
			g.drawString("Game Over", 200, 300);
			g.setColor(new Color(142, 68, 173));
			g.setFont(new Font("Arial Rounded MT", Font.BOLD, 30));
			g.drawString("Human beats computer - good job!", 225, 400);
		}
		
		g.dispose();
	}

	// Actually moves the snake itself!
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();

		if (right) {

			// Snake Y position
			for (int r = snakeLength - 1; r >= 0; r--) {
				snakeYLength[r + 1] = snakeYLength[r];
			}

			// Snake X position
			for (int r = snakeLength; r >= 0; r--) {

				if (r == 0) {
					snakeXLength[r] = snakeXLength[r] + 25;
				} else {
					snakeXLength[r] = snakeXLength[r - 1];
				}

				if (snakeXLength[r] > 850) {
					snakeXLength[r] = 25;
				}
			}

			repaint();

		}

		if (left) {

			// Snake Y position
			for (int r = snakeLength - 1; r >= 0; r--) {
				snakeYLength[r + 1] = snakeYLength[r];
			}

			// Snake X position
			for (int r = snakeLength; r >= 0; r--) {

				if (r == 0) {
					snakeXLength[r] = snakeXLength[r] - 25;
				} else {
					snakeXLength[r] = snakeXLength[r - 1];
				}

				if (snakeXLength[r] < 25) {
					snakeXLength[r] = 850;
				}
			}

			repaint();

		}

		if (up) {

			// Snake Y position
			for (int r = snakeLength; r >= 0; r--) { 

				if (r == 0) {
					snakeYLength[r] = snakeYLength[r] - 25;
				} else {
					snakeYLength[r] = snakeYLength[r - 1];
				}

				if (snakeYLength[r] < 75) {
					snakeYLength[r] = 600;
				}
			}

			// Snake X position
			for (int r = snakeLength - 1; r >= 0; r--) {
				snakeXLength[r + 1] = snakeXLength[r];
			}

			repaint();

		}

		if (down) {

			// Snake X position
			for (int r = snakeLength; r >= 0; r--) {

				if (r == 0) {
					snakeYLength[r] = snakeYLength[r] + 25;
				} else {
					snakeYLength[r] = snakeYLength[r - 1];
				}

				if (snakeYLength[r] > 600) {
					snakeYLength[r] = 75;
				}
			}

			// Snake Y position
			for (int r = snakeLength - 1; r >= 0; r--) {
				snakeXLength[r + 1] = snakeXLength[r];
			}

			repaint();

		}
	}

	// Checks which key was pressed
	@Override
	public void keyPressed(KeyEvent e) {

		// Checks if right key was pressed and goes accordingly
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			snakeMoves++;

			right = true;
			if (!left) {
				right = true;
			} else {
				right = false;
				left = true;
			}

			up = false;
			down = false;
		}

		// Checks if left key was pressed and goes accordingly
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			snakeMoves++;

			left = true;
			if (!right) {
				left = true;
			} else {
				left = false;
				right = true;
			}

			up = false;
			down = false;
		}

		// Checks if up key was pressed and goes accordingly
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			snakeMoves++;

			up = true;
			if (!down) {
				up = true;
			} else {
				up = false;
				down = true;
			}

			right = false;
			left = false;
		}

		// Checks if down key was pressed and goes accordingly
		if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			snakeMoves++;

			down = true;
			if (!up) {
				down = true;
			} else {
				down = false;
				up = true;
			}

			right = false;
			left = false;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
