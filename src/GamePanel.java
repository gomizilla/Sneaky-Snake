import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import java.util.Random;
import javax.sound.sampled.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 3; // og = 6
	int pointsGot;
	int pointX;
	int pointY;
	char direction = 'R';
	boolean running = false;
	boolean retry = false; // not used
	Timer timer;
	Random random;
	JButton retryButton;
	JButton quitButton;
	JButton mainReturnButton;
	Music music = new Music();
	String songTrack;
	
	
	
	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}

	public void startGame() {
		songTrack = "23.wav";
		newPoint();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
		music.setFile(songTrack);
		music.play();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		if (running) {
			// Creates grid lines
//			for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
//				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
//				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
//			}
			
			// Creates red oval in random x/y
			g.setColor(Color.red);
			g.fillOval(pointX, pointY, UNIT_SIZE, UNIT_SIZE);
			
			for (int i = 0; i < bodyParts; i++) {
				if (i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(new Color(45, 180, 0));
//					g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))); // makes snake body rainbow
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.yellow);
			g.setFont(new Font("Verdana", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + pointsGot, (SCREEN_WIDTH - metrics.stringWidth("Score: " + pointsGot))/2, g.getFont().getSize());
		} else {
			gameOver(g);
		}
		
	}
	
	public void newPoint() {
		pointX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		pointY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void move() {
		// Attaches new body part to snake
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		
		switch(direction) {
		case 'U': 
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D': 
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L': 
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R': 
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
		
	}
	
	public void checkPoints() {
		String nom = "eat.wav";
		Music eat = new Music();
		if ((x[0] == pointX) && (y[0] == pointY)) {
			bodyParts++;
			pointsGot++;
			eat.setFile(nom);
			eat.playOnce();
			newPoint();
		}
	}
	
	public void checkCollisions() throws IOException {
		// Checks if head collides with body
		for (int i = bodyParts; i > 0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		
		// Checks if head touches left border
		if (x[0] < 0) {
			running = false;
		}
		
		// Checks if head touches right border
		if (x[0] > SCREEN_WIDTH) {
			running = false;
		}
		
		// CHecks if head touches top border
		if (y[0] < 0) {
			running = false;
		}
		
		// Checks if head touches bottom border
		if (y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if (!running) {
			timer.stop();
			music.setFile(null);
			music.stop();
		}
		
	}
	
	public void gameOver(Graphics g) {
		
		songTrack = "gameover.wav";
		music.setFile(songTrack);
		music.playOnce();
		
		// Displays score
		
		retryButton = new JButton("Retry?");
		quitButton = new JButton("Quit");
		mainReturnButton = new JButton("Main Menu");
	
		g.setColor(Color.yellow);
		g.setFont(new Font("Verdana", Font.BOLD, 40));
		FontMetrics scoreMetrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + pointsGot, (SCREEN_WIDTH - scoreMetrics.stringWidth("Score: " + pointsGot))/2, g.getFont().getSize());
		
		// Game over screen
		g.setColor(Color.red);
		g.setFont(new Font("Verdana", Font.BOLD, 75));
		FontMetrics gameoverMetrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - gameoverMetrics.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2); // Gives game over in center of screen-ish
	
		mainReturnButton.setBounds(120, 475, 100, 30);
		retryButton.setBounds(270, 475, 72, 30);
		quitButton.setBounds(385, 475, 60, 30);
		
		retryButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Component component = (Component)e.getSource();
				Window window = SwingUtilities.windowForComponent(component);
				window.dispose();
				new GameFrame();
			}
			
		});
		
		quitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		
		mainReturnButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Component component = (Component)e.getSource();
				Window window = SwingUtilities.windowForComponent(component);
				window.dispose();
				new Menu();
			}
			
		});
		
		this.add(retryButton);
		this.add(quitButton);
		this.add(mainReturnButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (running) {
			move();
			checkPoints();
			try {
				checkCollisions();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction = 'R';
					break;
				}
			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction = 'U';
					break;
				}
			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction = 'D';
					break;
				}
			}
		}
	}
	
	public class Music {
		Clip clip;
		AudioInputStream sound;
		public void setFile (String fileName) {
			try {
				File file = new File(fileName);
				sound = AudioSystem.getAudioInputStream(file);
				clip = AudioSystem.getClip();
				clip.open(sound);
			} catch(Exception e) {
				
			}
		}
		public void play() {
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		public void playOnce() {
			clip.start();
		}
		public void stop() throws IOException {
			sound.close();
			clip.close();
			clip.stop();
		}
	}
	
}
