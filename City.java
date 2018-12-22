package cs2113.zombies;

import cs2113.util.Helper;

import java.awt.Color;
import java.util.ArrayList;


class City {

	/** walls is a 2D array with an entry for each space in the city.
	 *  If walls[x][y] is true, that means there is a wall in the space.
	 *  else the space is free. Humans should never go into spaces that
	 *  have a wall.
	 */
	private boolean walls[][];
	private int width, height;
	ArrayList<Human> arrList; //main human array list
	ArrayList<Zombie> arrList2; //main zombie array list


	/**
	 * Create a new City and fill it with buildings and people.
	 * @param w width of city
	 * @param h height of city
	 * @param numB number of buildings
	 * @param numP number of people
	 */
	City(int w, int h, int numB, int numP) {
		width = w;
		height = h;
		walls = new boolean[w][h];
		randomBuildings(numB);
		arrList = new ArrayList<>(numP);
		arrList2 = new ArrayList<>();
		populate(numP);

	}


	/**
	 * Generates numPeople random people distributed throughout the city.
	 * People must not be placed inside walls!
	 *
	 * @param numPeople the number of people to generate
	 */
	private void populate(int numPeople)
	{
		ZombieSim.dp.setPenColor(Color.WHITE);// Generate numPeople new humans randomly placed around the city.
		int i=0;
		while(i<numPeople){
			int y = Helper.nextInt(height);
			int x = Helper.nextInt(width);
			//no wall
			if (wallCheck(x, y) == 1) {
				Human human = new Human(x, y, this);
				arrList.add(human);
				i++;
			}
		}
		oneZombie();
	}
	//creates exactly one zombie
	private void oneZombie(){
		int rand=1;
		while(rand==1) {
			int y = Helper.nextInt(height);
			int x = Helper.nextInt(width);
			if (wallCheck(x, y) == 1) {
				Zombie zombie = new Zombie(x, y, this);
				arrList2.add(zombie);
				rand = 0;
			}
		}

	}


	/**
	 * Generates a random set of numB buildings.
	 *
	 * @param numB the number of buildings to generate
	 */
	private void randomBuildings(int numB) {
		/* Create buildings of a reasonable size for this map */
		int bldgMaxSize = width/6;
		int bldgMinSize = width/50;

		/* Produce a bunch of random rectangles and fill in the walls array */
		for(int i=0; i < numB; i++) {
			int tx, ty, tw, th;
			tx = Helper.nextInt(width);
			ty = Helper.nextInt(height);
			tw = Helper.nextInt(bldgMaxSize) + bldgMinSize;
			th = Helper.nextInt(bldgMaxSize) + bldgMinSize;

			for(int r = ty; r < ty + th; r++) {
				if(r >= height)
					continue;
				for(int c = tx; c < tx + tw; c++) {
					if(c >= width)
						break;
					walls[c][r] = true;
				}
			}
		}
	}
	//needed for the click add of a new zombie
	void dropZombie(int x, int y)
	{
		if(wallCheck(x,y) ==1 && checkDimensions(x,y)==1)
		{
			arrList2.add(new Zombie(x, y, this));
		}
	}

	/**
	 * Updates the state of the city for a time step.
	 */
	void update() {
		ArrayList<Human> removeArrList = new ArrayList<>();
		ArrayList<Zombie> arrListTemp = new ArrayList<>();

		//move humans & place into temporary human array list if necessary
		for (Human human : arrList){
			boolean bool = human.update();
			if (bool){
				removeArrList.add(human);
			}
		}
		//remove appropraite humans from main human array list
		arrList.removeAll(removeArrList);

		//create zombies using removed humans
		for (Human human : removeArrList) {
			Zombie zombie = new Zombie(human.x, human.y, this, human.getDirection());
			arrListTemp.add(zombie);
		}
		//add newly created zombies to main zombie array list
		arrList2.addAll(arrListTemp);

		//move zombies
		for (Zombie tempz : arrList2) {
			tempz.update();
		}

	}


	/**
	 * Draw the buildings and all humans.
	 */
	void draw(){
		/* Clear the screen */
		ZombieSim.dp.clear(Color.black);
		drawWalls();
		drawHumans();
		drawZombie();

	}

	/**
	 * Draw the buildings.
	 * First set the color for drawing, then draw a dot at each space
	 * where there is a wall.
	 */
	private void drawWalls() {
		ZombieSim.dp.setPenColor(Color.DARK_GRAY);
		for(int r = 0; r < height; r++) {
			for(int c = 0; c < width; c++) {
				if(walls[c][r]) {
					ZombieSim.dp.drawDot(c, r);
				}
			}
		}
	}
	//draw humans
	private void drawHumans(){
		ZombieSim.dp.setPenColor(Color.white);
		for (Human currHuman : arrList) {
			ZombieSim.dp.drawDot(currHuman.x, currHuman.y);
		}
	}
	//draw zombies
	private void drawZombie(){
		ZombieSim.dp.setPenColor(Color.RED);
		for (Zombie currZombie : arrList2) {
			ZombieSim.dp.drawDot(currZombie.x, currZombie.y);
		}
	}
	//checks for presence/absence of a wall
	int wallCheck(int x, int y){
		if (walls[x][y]){  //there's a wall
			return -1;
		}
		else{
			return 1;
		}
	}
	//checks that coordinates fit dimensions
	int checkDimensions(int x, int y){
		if(x<width && y<height && x >= 0 && y >= 0){
			return 1;
		}
		else{
			return -1;
		}
	}

}
