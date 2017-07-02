/*
 * Author: Liam Mortimer
 * 
 * Class to create and run a game of battleships.
 * 
 * Defaults can be changed by altering the fields (boardSize changed in the main).
 * 
 * Defaults:
 * 	4 ships of size 3 length.
 * 	board of size 8.
 * 
 */

// Board Size must be < 10 or breaks y axis labels.

// Consider making the computer smarter (atm just randomly shoots).

import java.util.*;

public class Battleships {
	
	// Initialise a Scanner class as Scanner is used throughout, as well as random which is used in
	// the getRandomXY method.
	private static Scanner k = new Scanner( System.in );
	private static Random random = new Random();
	
	// Initialise the boards and letter conversion maps. Boards given data later.
	private static char[][] playersBoard;
	private static char[][] oppsBoard;
	private static char[][] oppsBoardPlayersView;
	private static Map<String,Integer> letterCoordToNum;
	private static Map<Integer,String> numToLetterCoord;
	
	// Initialise fields that determine how the game will play.
	private static int shipSize = 3;
	private static int numOfShips = 4;
	private static int boardSize;
	private static int numOfShipSegments;
	
	public static void main(String[] args) {
		
		// Setup the required stuff based on variables, and place the ships automatically.
		int enteredBoardSize = 8;
		
		setBoardSize ( enteredBoardSize );
		setLetterCoordConversions();
		setupBoards();
		setNumOfShipSegments();
		
		placeShips( "placeForPlayer" );
		placeShips( "placeForOpp" );
		
		// Welcoming message, tells the user the game and the rules.
		System.out.print( "Welcome to Battleships!\nThe aim is to fire missiles and destroy all segments"
				+ " of your opponent's ships before they destroy yours!\nYour ships will be automatically"
				+ " placed for you.\nYou will take turns with your computer opponent to fire missiles" 
				+ " at each other's ships.\nYou will be prompted for X and Y coordinates where you want to"
				+ " try and fire a missile next.\nThis will continue until either all of your ships"
				+ " have been destroyed, or you have destroyed all of your opponent's ships.\n\n"
				+ "GOOD LUCK!\n");
		pressEnterToContinue();
		lineBreak();
		
		// Shows the user where their ships have been automatically placed.
		boardKeys();
		printPlayersBoard();
		System.out.println( "\nYour ships have been placed for you (see your board above).");
		pressEnterToContinue();
		lineBreak();
		
		// Initialise variables used to track the game.
		String shotMessage = "";
		String winner = "";
		boolean win = false;
		
		// Do while loops until checkWin method finds a winner.
		do {
			
		// Call methods to run the player's shot.
		boardKeys();
		printOppsBoard();
		shotMessage = fireMissile( "player" );
		System.out.println( shotMessage );
		pressEnterToContinue();
		
		// Check if shot wins the game for the player.
		if ( checkWin().equals( "player" ) ){
			win = true;
			winner = "player";
			break;
		}
		
		lineBreak();
		
		// Call methods to run the opponent's shot.
		boardKeys();
		System.out.println( "\nYour opponent fires a missile...");
		pressEnterToContinue();
		shotMessage = fireMissile( "opponent" );
		printPlayersBoard();
		System.out.println( shotMessage );
		pressEnterToContinue();
		
		// Check if shot wins the game for the opponent.
		if ( checkWin().equals( "opponent" ) ){
			win = true;
			winner = "opponent";
			break;
		}
		
		lineBreak();
		
		} while ( win == false );
		
		// Code to run the final standings after the game has been won.
		// Shows the player the state of their board and the opponents.
		// Notifies of who won.
		lineBreak();
		boardKeys();
		printOppsActualBoard();
		printPlayersBoard();
		winnerMessage ( winner );
	}
	
	private static void setBoardSize ( int enteredBoardSize ){
		// BoardSize is +1 to account for the axis that will be added.
		boardSize = ( enteredBoardSize + 1 );
	}
	
	private static void setNumOfShipSegments (){
		// Set the number of ship segments used to track if win later.
		numOfShipSegments = ( shipSize * numOfShips );
	}
	
	private static void setLetterCoordConversions(){
		// sets up letterCoordToNum map converter.
		letterCoordToNum = new HashMap<String,Integer>();
		numToLetterCoord = new HashMap<Integer,String>();
		char letter = 'A';
		Integer value = 1;
		for ( int i = 1; i < boardSize; i++ ){
			letterCoordToNum.put( Character.toString( letter ), value );
			numToLetterCoord.put( value, Character.toString( letter ) );
			letter ++;
			value ++;
		}
	}
	
	private static void setupBoards(){
		
		String[] boardIdentifier = { "playersBoard", "oppsBoard", "oppsBoardPlayersView" };
		
		// Identify which board is being initialised, so the code is applied appropriately.
		char[][] board = new char [boardSize][boardSize];
		for ( int boardTracker = 0; boardTracker < boardIdentifier.length; boardTracker++){
			String currentBoard = boardIdentifier[boardTracker];
			switch ( currentBoard ){
				case "playersBoard":
					playersBoard = new char [boardSize][boardSize];
					board = playersBoard;
					break;
				case "oppsBoard":
					oppsBoard = new char [boardSize][boardSize];
					board = oppsBoard;
					break;
				case "oppsBoardPlayersView":
					oppsBoardPlayersView = new char [boardSize][boardSize];
					board = oppsBoardPlayersView;
					break;
			}
			
			// Set the blank spaces.
			for ( int x = 0; x < boardSize; x++ ){
				for ( int y = 0; y < boardSize; y++ ){
					if ( x == 0 && y == 0 ){
						board[x][y] = ' ';
					}
					else{
						board[x][y] = '~';
					}
				}
			}
			
			// Label the axis of the board.
			char xAxisLabel = 'A';
			char yAxisLabel = '1';
			for ( int i = 1; i < boardSize ; i++ ){
				board[0][i] = xAxisLabel;
				xAxisLabel ++;
				board[i][0] = yAxisLabel;
				yAxisLabel ++;
			}
		}
		
	}

	private static String getLetterCoordPrompt (){
		// Creates the prompt for the X coordinate based on the board size.
		String prompt = "\nEnter the X coordinate you wish to fire the missile (A - ";
		String endLetter = numToLetterCoord.get( ( boardSize - 1 ) ) + "): ";
		return ( prompt += endLetter );
	}

	private static String getNumberCoordPrompt (){
		// Creates the prompt for the Y coordinate based on the board size.
		String prompt = "Enter the Y coordinate you wish to fire the missile (1 - ";
		String endLetter = ( Integer.toString( boardSize - 1 ) + "): " );
		return ( prompt += endLetter );
	}
	
	private static int[] getRandomXY (){
		// Method to generate random x and y, used for opps placing ships.
		int[] returns = new int[2];
		int x = random.nextInt( boardSize );
		int y = random.nextInt( boardSize );
		if ( x == 0 ){
			x += 1;
		}
		if ( y == 0 ){
			y += 1;
		}
		returns[0] = x;
		returns[1] = y;
		return returns;
	}
	
	private static void pressEnterToContinue(){
		// Function to make the program wait for user to press enter.
		// Allows to make program stop to give the user information.
		System.out.print( "\nPress Enter to continue..." );
		k.nextLine();
	}
	
	private static void boardKeys(){
		// Method to print out the keys for the board, to inform the user.
		System.out.println( "\nKEY: S = Ship | X = Hit | ~ = Unidentified Waters");
	}
	
	private static void lineBreak(){
		// Line break to separate prompts, 60 equals long.
		System.out.println("\n|====================================================================|");
	}
	
	private static void winnerMessage( String winner ){
		// Method to print out the winner message depending on the winner.
		if ( winner.equals( "player" ) ){
			System.out.println( "\nCongratulations, you won!" );
		}
		else if ( winner.equals( "opponent" ) ){
			System.out.println( "\nThe computer wins!" );
		}
		pressEnterToContinue();
	}
	
	private static void printPlayersBoard(){
		// Nested for loop to go through each element and print it out line by line.
		System.out.println( "\nPLAYER'S BOARD\n" );
		for ( int x = 0; x < boardSize ; x++ ){
			for ( int y = 0; y < boardSize; y++ ){
				System.out.print( playersBoard[x][y] );
			}
			System.out.print( "\n" );
		}
	}
	
	private static void printOppsBoard(){
		// Nested for loop to go through each element and print it out line by line.
		System.out.println( "\nOPPONENT'S BOARD\n" );
		for ( int x = 0; x < boardSize ; x++ ){
			for ( int y = 0; y < boardSize; y++ ){
				System.out.print( oppsBoardPlayersView[x][y] );
			}
			System.out.print( "\n" );
		}
	}
	
	private static void printOppsActualBoard(){
		// Nested for loop to go through each element and print it out line by line.
		System.out.println( "\nOPPONENT'S REVEALED BOARD\n" );
		for ( int x = 0; x < boardSize ; x++ ){
			for ( int y = 0; y < boardSize; y++ ){
				System.out.print( oppsBoard[x][y] );
			}
			System.out.print( "\n" );
		}
	}
	
	private static void placeShips ( String whoseShip ){
		
		String[] randomOrientation = { "north", "east", "south", "west" };
		
		char[][] boardToBeAltered = new char[boardSize][boardSize];
		for ( int i = 0; i < numOfShips; i++ ){
			
			// Switch determines which board is being changed, so same code can be run for 
			// randomly placing for player and on opps board.
			switch ( whoseShip ){
				case "placeForPlayer":
					boardToBeAltered = playersBoard;
					break;
				case "placeForOpp":
					boardToBeAltered = oppsBoard;
					break;
			}
			
			// Randomly places the number of ships on the grid for the player.
			Random random = new Random();
			int x, y;
			String orientation;
			do {
				// get x & y.
				x = getRandomXY()[0];
				y = getRandomXY()[1];
				orientation = randomOrientation[ random.nextInt( randomOrientation.length ) ];
			} while ( shipPlacementValid ( x, y, orientation, boardToBeAltered ) == false );
			// Place the confirmed valid x & y ship.
			placeValidShip( x, y, orientation, boardToBeAltered );
		}
	}
	
	private static void placeValidShip ( int x, int y, String orientation, char[][] board ){
		
		// Loops through placing the ship at the appropriate locations.
		// Increase or decrease the x or y, depending on orientation, in each loop.
		for ( int i = 0; i < shipSize; i++ ){
			board[x][y] = 'S';
			
			switch ( orientation ){
				case "north":
					x--;
					break;
				case "east":
					y++;
					break;
				case "south":
					x++;
					break;
				case "west":
					y--;
					break;
			}
		}	
	}
	
	private static boolean validInputs ( String x, String y ){
		
		boolean valid = true;
		
		// Checks if the inputs are actually ints (stops pricks).
		// Checks if x is a valid letter coord, if no match found, valid is false and returned.
		boolean match = false;
		for ( String validLetterCoord : letterCoordToNum.keySet() ){
			if ( validLetterCoord.equals( x ) ){
				match = true;
			}
		}
		if ( match == false ){
			valid = false;
			System.out.println( "\nPlease enter a valid X coordinate!" );
			k.nextLine();
			return valid;
		}
		
		// Checks if y input is actually an integer (stops mofos).
		try {
			Integer.parseInt( y );
		}
		catch ( NumberFormatException e ){
			valid = false;
			System.out.println( "\nPlease enter a valid Y coordinate!" ) ;
			k.nextLine();
			return valid;
		}
		// If get here, x and y are valid, convert them then check if shot has already been placed here
		// previously. If it has, not valid, get another.
		int convertedX = Integer.parseInt( y );
		int convertedY = letterCoordToNum.get( x );
		if ( oppsBoardPlayersView[convertedX][convertedY] == 'X' || oppsBoardPlayersView[convertedX][convertedY] == ' '){
			System.out.println( "\nYou have already fired a missile there!" );
			k.nextLine();
			valid = false;
			return valid;
		}
		// valid is true if code makes it here.
		return valid;
	}
	
	private static boolean shipPlacementValid ( int x, int y, String orientation, char[][] board ){
		
		boolean valid = true;
		
		// Loop to check all of the spots from the start location to the length of the ship
		// that will be placed.
		loop:
		for ( int i = 0; i < shipSize; i++ ){
			
			if ( board[x][y] == 'S' ){
				valid = false;
				break loop;
			}
			
			// for each orientation, 1) checks if the ship will go off of the board, 2) will check 
			// that it does not collide / overwrite any existing ships.
			switch ( orientation ){
				
				case "north":
					if ( ( x - shipSize ) < 1 ){
						valid = false;
						break loop;
					}
					x--;
					break;
				
				case "east":
					if ( ( y + shipSize ) > boardSize ){
						valid = false;
						break loop;
					}
					y++;
					break;
					
				case "south":
					if ( ( x + shipSize ) > boardSize ){
						valid = false;
						break loop;
					}
					x++;
					break;
					
				case "west":
					if ( ( y - shipSize ) < 0 ){
						valid = false;
						break loop;
					}
					y--;
					break;
			}
		}
		return valid;
		
	}
	
	private static String fireMissile( String whoseShot ){
		
		// Get inputs and gets coord prompts.
		int x, y;
		String shotMessage = "";
		if ( whoseShot.equals( "player" ) ){
			String stringX, stringY;
			String xCoordPrompt = getLetterCoordPrompt();
			String yCoordPrompt = getNumberCoordPrompt();
			do {
				// code that runs until inputs are valid (are numbers / letter coords).
				System.out.print( xCoordPrompt );
				stringX = k.nextLine();
				System.out.print( yCoordPrompt );
				stringY = k.nextLine();
				stringX = stringX.toUpperCase();
			} while ( validInputs( stringX, stringY ) == false );
			// x and y are valid if code gets here, so convert them. | swap x and y around.
			x = Integer.parseInt( stringY );
			y = letterCoordToNum.get( stringX );
			
			// fire missile for player.
			boolean isHit = missileHit( x, y, whoseShot );
			shotMessage = hitOrMissMessage( whoseShot, isHit );
		}
		else if ( whoseShot.equals( "opponent" ) ){
			// Randomly gets x and y for opponents shot.
			x = getRandomXY()[0];
			y = getRandomXY()[1];
			
			// fire missile for opponent.
			boolean isHit = missileHit( x, y, whoseShot );
			shotMessage = hitOrMissMessage( whoseShot, isHit );
		}
		return shotMessage;
	}
	
	private static boolean missileHit ( int x, int y, String whoseShot ){
		boolean hit = false;
		// Set boardToBeChecked to be the opposing board of whoever's shot it is.
		char[][] boardToBeChecked = new char[boardSize][boardSize];
		if ( whoseShot.equals( "player" ) ){
			boardToBeChecked = oppsBoard;
		}
		else if ( whoseShot.equals ( "opponent" ) ){
			boardToBeChecked = playersBoard;
		}
		
		if ( boardToBeChecked[x][y] == 'S' ){
			hit = true;
			boardToBeChecked[x][y] = 'X';
			// If players shot, change their view of opps board to refelect this.
			if ( whoseShot == "player" ){
				oppsBoardPlayersView[x][y] = 'X';
			}
		}
		else if ( boardToBeChecked[x][y] == '~' ){
			hit = false;
			// If players shot, change their view of opps board to show the miss.
			if ( whoseShot == "player" ){
				oppsBoardPlayersView[x][y] = ' ';
			}
		}
		return hit;
	}
	
	private static String hitOrMissMessage ( String whoseShot, boolean isHit ){
		// Generates message to say whether shot fired was a hit or a miss.
		String hitOrMissMessage = "";
		switch ( whoseShot ){
			case "player":
				if ( isHit == true ){
					hitOrMissMessage = "\nYou HIT your opponent's ship!";
				}
				else if ( isHit == false ){
					hitOrMissMessage = "\nYou MISSED your opponent's ships.";
				}
				break;
			case "opponent":
				if ( isHit == true ){
					hitOrMissMessage = "\n...and HIT your ship!";
				}
				else if ( isHit == false ){
					hitOrMissMessage = "\n...and MISSED your ships.";
				}
				break;
		}
		return hitOrMissMessage;
	}
	
	private static String checkWin(){
		int deadOppsShips = 0;
		int deadPlayerShips = 0;
		String winner = "";
		
		// Loop through and see how many dead ship segments there are on each board.
		for ( int x = 1; x < boardSize; x++ ){
			for ( int y = 1; y < boardSize; y++ ){
				if ( playersBoard[x][y] == 'X' ){
					deadPlayerShips += 1;
				}
				else if ( oppsBoard[x][y] == 'X' ){
					deadOppsShips += 1;
				}
			}
		}
		
		// Check if the number of dead ship segments is equal to number of segments, 
		// all their ships are dead & the other player wins.
		if ( deadOppsShips == numOfShipSegments ){
			winner = "player";
		}
		else if ( deadPlayerShips == numOfShipSegments ){
			winner = "opponent";
		}
		return winner;
	}
	
}
