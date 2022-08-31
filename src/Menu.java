import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Menu extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton playButton;
	JButton quitButton;
	String menuSong = "mainmenu.wav";
	Music music = new Music();
	
	Menu() {
		
		
		// adds background to menu
		
		ImageIcon menu = new ImageIcon("sneakysnake.jpg");
		JLabel label = new JLabel(menu);
		
		//creates jbuttons
		playButton = new JButton("Play");
		quitButton = new JButton("Quit");
		
		//sets position of buttons
		playButton.setBounds(230, 475, 60, 30);
		quitButton.setBounds(350, 475, 60, 30);
		
		// adds action listener to jbuttons
		playButton.addActionListener(this);
		quitButton.addActionListener(this);
		
		
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Snake");
		this.add(playButton);
		this.add(quitButton);
		this.add(label);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		music.setFile(menuSong);
		music.playOnce();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playButton) {
			this.dispose();
			new GameFrame();
		} else if (e.getSource() == quitButton) {
//			this.dispose();
			System.exit(0);
		}
	}
	
}
