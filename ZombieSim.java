package cs2113.zombies;

import cs2113.util.DotPanel;

import java.awt.*;
import java.awt.event.*;


import javax.swing.*;


/*
 * You must add a way to represent humans.  When there is not a zombie apocalypse occurring, humans
 * should follow these simple rules:
 * 		if (1 in 10 chance):
 * 			turn to face a random direction (up/down/left/right)
 * 		Move in the current direction one space if not blocked by a wall
 *
 * We will add additional rules for dealing with sighting or running into zombies later.
 */

public class ZombieSim extends JFrame {

	private static final long serialVersionUID = -5176170979783243427L;

	/** The Dot Panel object you will draw to */
	static DotPanel dp;

	/* Define constants using static final variables */
	private static final int MAX_X = 200;
	private static final int MAX_Y = 200;
	private static final int DOT_SIZE = 3;
	private static final int NUM_HUMANS = 100;
	private static final int NUM_BUILDINGS = 80;
	private static City world;


	/*
	 * This fills the frame with a "DotPanel", a type of drawing canvas that
	 * allows you to easily draw squares to the screen.
	 */
	private ZombieSim() {
		this.setSize(MAX_X * DOT_SIZE, MAX_Y * DOT_SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Apocalypse");

		/* Create and set the size of the panel */
		dp = new DotPanel(MAX_X, MAX_Y, DOT_SIZE);

		/* Add the panel to the frame */
		Container cPane = this.getContentPane();
		cPane.add(dp);

		/* Initialize the DotPanel canvas:
		 * You CANNOT draw to the panel BEFORE this code is called.
		 * You CANNOT add new widgets to the frame AFTER this is called.
		 */
		this.pack();
		dp.init();
		dp.clear();
		dp.setPenColor(Color.red);
		this.setVisible(true);


		//space bar
		KeyListener listener = new KeyListener()
		{
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					world = new City(MAX_X, MAX_Y, NUM_BUILDINGS, NUM_HUMANS); //rEsTaRt
				}

			}
			//extra key functions not useful
			@Override
			public void keyTyped(KeyEvent e) { }
			@Override
			public void keyReleased(KeyEvent e) { }
		};
		this.addKeyListener(listener);

		//mouse click gui
		MouseListener listener2 = new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				world.dropZombie(e.getX()/3, e.getY()/3); //frame adjustment
			}
			@Override
			public void mousePressed(MouseEvent e) { }
			@Override
			public void mouseReleased(MouseEvent e) { }
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) { }
		};
		this.addMouseListener(listener2);

		world = new City(MAX_X, MAX_Y, NUM_BUILDINGS, NUM_HUMANS);
			/* This is the Run Loop (aka "simulation loop" or "game loop")
			 * It will loop forever, first updating the state of the world
			 * (e.g., having humans take a single step) and then it will
			 * draw the newly updated simulation. Since we don't want
			 * the simulation to run too fast for us to see, it will sleep
			 * after repainting the screen. Currently it sleeps for
			 * 33 milliseconds, so the program will update at about 30 frames
			 * per second.
			 */

			while (true) {
				// Run update rules for world and everything in it
				world.update();
				// Draw to screen and then refresh
				world.draw();
				dp.repaintAndSleep(33);
				if (world.arrList.size() == 0){
					world = new City(MAX_X, MAX_Y, NUM_BUILDINGS, NUM_HUMANS); //rEsTaRt
				}

			}
	}


	public static void main(String[] args) {
		/* Create a new GUI window  */
			new ZombieSim();
	}

}
