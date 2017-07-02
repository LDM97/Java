import java.util.*;

public class Algorithms {
	
	public static int truckHeight = 50;
	public static int truckWidth = 100;
	
	public static void main(String[] args) {
		
		// Setup two truck lists for the two algorithms to use.
		List<Truck> trucks = new ArrayList<Truck>();
		List<Truck> trucks2 = new ArrayList<Truck>();
		
		// Setup box list for the two algorithms to use.
		List<Box> boxes = generateBoxes( 2000 );
		
		// Run first fit, timing its run.
		long firstFitStart = System.currentTimeMillis();
		firstFit( trucks, boxes );
		long firstFitEnd = System.currentTimeMillis();	
		
		// Run next fit, timing its run.
		long nextFitStart = System.currentTimeMillis();
		nextFit( trucks2, boxes );
		long nextFitEnd = System.currentTimeMillis();
		
		// Display the information of the two runs. Gives the number of trucks they used & their run time in milliseconds.
		System.out.format( "First Fit Trucks Used: %s, Time: %d\n", trucks.size(), ( firstFitEnd - firstFitStart ) );
		System.out.format( "Next Fit Trucks Used: %s, Time: %d\n", trucks2.size(), ( nextFitEnd - nextFitStart ) );
	}
	
	private static List<Box> generateBoxes( int numOfBoxes ){
		// Setup a list to put the boxes in.
		List<Box> boxes = new ArrayList<Box>();
		Random rand = new Random();
		
		// Loop to the number of boxes requested, creating randomly sized boxes. Size between max truck 1 & truck height, 1 & truck width.
		for( int i = 0; i < numOfBoxes; i++ ){
			boxes.add( new Box( rand.nextInt( truckHeight ) + 1, rand.nextInt( truckWidth ) + 1 ) );
		}
		
		return boxes;
	}
	
	public static void firstFit( List<Truck> trucks, List<Box> boxes ){
		
		// Loop through inserting each box.
		boxLoop:
		for( Box box : boxes ){
			
			// Loop through each truck to find the first fit.
			for( Truck truck : trucks ){
				
				// If L is given and adding this box will exceed L, won't fit so skip to next truck.
				if( truck.maxBoxesDefined() && ( truck.getCurrentNumOfBoxes() + 1 ) > truck.getMaxBoxes() ){
					continue;
				}
				
				// If the truck has no boxes yet (first pile is empty) then just add the box.
				if( truck.getPiles().get( 0 ).isEmpty() ){
					truck.getPiles().get( 0 ).add( box );
					truck.incrementBoxCount();
					continue boxLoop;
				}
				
				// Used later to see if it is possible to make a new pile in the truck for the box.
				int totalFloorSpaceTaken = 0;
				
				// Check each pile in that truck to see if it can fit there.
				for( List<Box> pile : truck.getPiles() ){
					
					// Add to the floor space taken, based on the pile's bottom element.
					totalFloorSpaceTaken += pile.get( 0 ).getWidth();
					
					// If the current box isn't slim enough to fit on the top of this pile, skip & check next pile.
					if( box.getWidth() > pile.get( pile.size() - 1 ).getWidth() ){
						continue;
					}
					
					// Slim enough for this pile, would adding the box to this pile exceed the truck height?
					if( box.getHeight() + getPileHeight( pile ) > truck.getHeight() ){
						continue;
					}
					
					// Slim and short enough, so add the box to this pile, and skip to add the next box.
					pile.add( box );
					truck.incrementBoxCount();
					continue boxLoop;
					
				}
				
				// Can't fit in any existing piles, can a new pile be made in this truck for the box?
				if( totalFloorSpaceTaken + box.getWidth() <= truck.getWidth() ){
					// Make a new pile in the truck, and place the box in this pile / at the bottom of this new pile.
					truck.getPiles().add( new ArrayList<Box>() );
					truck.getPiles().get( truck.getPiles().size() - 1 ).add( box );
					truck.incrementBoxCount();
					continue boxLoop;
				}
			}
			
			// Doesn't fit in any trucks, add a new truck for it and put it in the first pile of this new truck.
			trucks.add( new Truck( truckHeight, truckWidth ) );
			trucks.get( trucks.size() - 1 ).getPiles().get( 0 ).add( box );
			trucks.get( trucks.size() - 1 ).incrementBoxCount();
		
		}
		
	}
	
	public static void nextFit( List<Truck> trucks, List<Box> boxes ){
		// Cannot backtrack through piles or trucks.
		// Used to track which truck the last box was placed in & which pile within that truck it was placed in.
		// Overall, the two track the position the last box was placed.
		int lastTruck = 0;
		int lastPile = 0;
		
		boxLoop:
		for( Box box : boxes ){
			for( int i = lastTruck; i < trucks.size(); i++ ){
				Truck truck = trucks.get( i );
				
				// If L is given and adding this box will exceed L, won't fit so skip to next truck.
				if( truck.maxBoxesDefined() && ( truck.getCurrentNumOfBoxes() + 1 ) > truck.getMaxBoxes() ){
					continue;
				}
				
				// If the truck has no boxes yet (first pile is empty) then just add the box.
				if( truck.getPiles().get( 0 ).isEmpty() ){
					truck.getPiles().get( 0 ).add( box );
					truck.incrementBoxCount();
					// Update the tracking variables.
					lastTruck = i;
					lastPile = 0;
					continue boxLoop;
				}
				
				// Check each pile in that truck to see if it can fit there.
				for( int j = lastPile; j < truck.getPiles().size(); j++ ){
					List<Box> pile = truck.getPiles().get( j );
					
					// If the current box isn't slim enough to fit on the top of this pile, skip & check next pile.
					if( box.getWidth() > pile.get( pile.size() - 1 ).getWidth() ){
						continue;
					}
					
					// Slim enough for this pile, would adding the box to this pile exceed the truck height?
					if( box.getHeight() + getPileHeight( pile ) > truck.getHeight() ){
						continue;
					}
					
					// Slim and short enough, so add the box to this pile, and skip to add the next box.
					pile.add( box );
					truck.incrementBoxCount();
					// Update the tracking values.
					lastTruck = i;
					lastPile = j;
					continue boxLoop;
				}
				
				// Calculate the total amount of floor space taken in the truck to see if a new pile can be made for
				// this box.
				int totalFloorSpaceTaken = 0;
				for( List<Box> pile : truck.getPiles() ){
					totalFloorSpaceTaken += pile.get( 0 ).getWidth();
				}
				
				// Can't fit in any existing piles, can a new pile be made in this truck for the box?		
				if( totalFloorSpaceTaken + box.getWidth() <= truck.getWidth() ){
					// Make a new pile in the truck, and place the box in this pile / at the bottom of this new pile.
					truck.getPiles().add( new ArrayList<Box>() );
					truck.getPiles().get( truck.getPiles().size() - 1 ).add( box );
					truck.incrementBoxCount();
					// Update the tracking values.
					lastTruck = i;
					lastPile = truck.getPiles().size() - 1;
					continue boxLoop;
				}
				
			}
			
			// Doesn't fit in any trucks, add a new truck for it and put it in the first pile of this new truck.
			trucks.add( new Truck( truckHeight, truckWidth ) );
			trucks.get( trucks.size() - 1 ).getPiles().get( 0 ).add( box );
			trucks.get( trucks.size() - 1 ).incrementBoxCount();
			// Update the tracking values.
			lastTruck = trucks.size() - 1;
			lastPile = 0;
		}
		
		
	}
	
	private static int getPileHeight( List<Box> pile ){ 
		// Used across both algorithms to check the total height of a given pile.
		int pileHeight = 0;
		for( Box box : pile ){
			pileHeight += box.getHeight();
		}
		return pileHeight;
	}

}
