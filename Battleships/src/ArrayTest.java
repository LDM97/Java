import java.util.Scanner;

public class ArrayTest {

	// XXXXXXXXXXXXXXXXXXXXXX GET VALID COORDS XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// Code to get the user to place the ships themselves.
				else if ( whoseShip.equals( "playerSelfPlace" ) ){
					@SuppressWarnings("resource")
					Scanner k = new Scanner( System.in );
					String stringX, stringY, orientation;
					int x, y;
					
					// Nested do while that will run until valid ship details are entered.
					do {
						do {
							// code that runs until inputs are valid (are numbers and orientation).
							System.out.print( "\nEnter the X coordinate you wish to place the ship: ");
							stringX = k.nextLine();
							System.out.print( "\nEnter the Y coordinate you wish to place the ship: ");
							stringY = k.nextLine();
							System.out.print( "\nEnter the orientation of the ship (north, east, south or west): ");
							orientation = k.nextLine();
						} while ( validInputs( stringX, stringY, orientation ) == false );
						// x and y are ints if gets here, so convert them. | swap x and y around.
						x = Integer.parseInt( stringY );
						y = Integer.parseInt( stringX );
					} while ( shipPlacementValid ( x, y, orientation, boardToBeAltered ) == false );
					
					// if escapes nested do while loop, all entered info is valid and the ship is placed.
					placeValidShip ( x, y, orientation, boardToBeAltered );
				}
				
				// XXXXXXXXXXXXXXXXXXXXXXXX VALIDINPUTS METHOD XXXXXXXXXXXXXXXXXXXXXXXXX
				private static boolean validInputs ( String x, String y, String orientation ){
					boolean valid = true;
					
					Scanner k = new Scanner( System.in );
					
					try {
						Integer.parseInt( x );
					}
					catch ( NumberFormatException e ){
						valid = false;
						System.out.println( "\nPlease enter a valid X coordinate!");
						k.nextLine();
						return valid;
					}
					try {
						Integer.parseInt( y );
					}
					catch ( NumberFormatException e ){
						valid = false;
						System.out.println( "\nPlease enter a valid Y coordinate!" ) ;
						return valid;
					}
					switch ( orientation ){
						case "north":
						case "east":
						case "south":
						case "west":
							break;
						default:
							valid = false;
							System.out.println( "\nPlease enter a valid orientation!" );
							k.nextLine();
							return valid;
					}
					return valid;
				}

}
