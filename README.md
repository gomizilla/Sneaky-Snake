# Polyglottal Project

## Introduction
This project was made in Java during the polyglottal week for the Code Chrysalis Immersive Part Time bootcamp.

## Aim
To learn about a new programming language that we have no experience with and challenge ourselves to make something in a two week period.

## How To Play
#### Please make sure you have Java installed on your computer
- clone this repo into your local repository
- run the sneakysnake.jar file to play 😊

## Settings
#### Create grid lines to make the game a bit easier
- In the GamePanel.java file and uncomment lines 70-73 (remove the // from each line)
```
//	for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
//		g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
//		g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
//	}
```

#### Create a rainbow snake body
- In the GamePanel.java file, uncomment line 85 (remove the first set of //)
```
//	g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))); // makes snake body rainbow
```

#### Change game speed
- In the GamePanel.java file, change line 22 to change the speed. 
  - Lower number will result in a faster game.
  - Higher number will result in a slower game.
```
	static final int DELAY = 75;
```