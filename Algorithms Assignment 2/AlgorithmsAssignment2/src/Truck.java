import java.util.*;

public class Truck {
	
	// Store basic truck fields, set in constructor.
	private int height;
	private int width;
	private int currentNumOfBoxes;
	
	private static int maxNumBoxes;
	private static boolean maxBoxesDefined = false;
	
	// List of piles, each pile is a list of boxes within that pile.
	private List<List<Box>> piles = new ArrayList<List<Box>>();
	
	// Constructor for a truck where only height and width is defined.
	Truck( int height, int width ){
		initialiseTruck( height, width );
	}
	
	// Constructor for a truck where height, width & l (max number of boxes in a truck) is defined.
	Truck( int height, int width, int maxBoxes ){
		maxNumBoxes = maxBoxes;
		maxBoxesDefined = true;
		initialiseTruck( height, width );
	}
	
	// Used for to run shared code between the to constructors.
	private void initialiseTruck( int height, int width ){
		// Set the initial values for the new truck.
		this.height = height;
		this.width = width;
		
		// Initialise the first pile in the truck. Contains no boxes at this point.
		this.piles.add( new ArrayList<Box>() );
		
	}
	
	// Basic get methods below.
	public int getHeight(){
		return this.height;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getMaxBoxes(){
		return maxNumBoxes;
	}
	
	public List<List<Box>> getPiles(){
		return this.piles;
	}
	
	public int getCurrentNumOfBoxes(){
		return this.currentNumOfBoxes;
	}
	
	public void incrementBoxCount(){
		this.currentNumOfBoxes ++;
	}
	
	// Return the boolean to check if L (a maximum number of boxes for a truck) has been defined.
	public boolean maxBoxesDefined(){
		return maxBoxesDefined;
	}
	
	// Called to display to console the contents of the truck / information about the stored boxes & the truck.
	public void displayContents(){
		// Display initial truck information.
		System.out.format( "Truck Height: %s, Truck Width: %s, Number of Piles: %s\n", this.height, this.width, this.piles.size() );
		
		for( int i = 0; i < piles.size(); i++ ){
			// Loop through each pile, printing out information about the given pile.
			System.out.format( "\tPile %s has %s boxe(s)\n", ( i + 1 ), piles.get( i ).size() );
			
			for( int j = piles.get( i ).size() - 1; j != -1; j-- ){
				// Reverse loop through the pile itself, printing out information about the boxes in that pile.
				// (reverse loop for a more readable / user friendly console output).
				System.out.format( "\t\tBox Height: %s, Box Width: %s\n", piles.get( i ).get( j ).getHeight(), piles.get( i ).get( j ).getWidth() );
			}
			
		}
	}
	
}
