 

/************************************************/
/*** Purpose: Search algorithm implementation ***/
/***          for testing.                    ***/
/***                                          ***/
/*** Author: Liam Mortimer                    ***/
/*** Student Number: 150228550                ***/
/***                                          ***/
/*** Note: Based on skeleton code provided by ***/
/*** Jason Steggles 23/09/2016                ***/
/************************************************/

import java.io.*;
import java.text.*;
import java.util.*;

public class Search {

	/** Global variables for counting comparisons **/
	public int compSeq=0;
	public int compBin=0;
	public int compHash=0;
	
	/** Array of values to be searched and size **/
	private int[] A;
	private int size;
	
	/** Hash array and size **/
	private int[] H;
	private int hSize;
	
	/** Constructor **/
	Search(int n, int hn)
	{
	    /** set size of array **/
	    size = n;
	    hSize = hn;
	
	    /** Create arrays **/
	    A = new int[size];
	    H = new int[hSize];
	
	    /** Initialize hash array **/
	    /** Assume -1 indicates a location is empty **/
	    for (int i=0; i<hSize; i++)
	    {
	        H[i] = -1;
	    }
	}
	
	// Sequential Search ===================================================================================
	
	public int seqSearch( int key ){ // Sequential search method.
		
		for( int i = 0; i < A.length; i++ ){ // Loop through the array.
			
			int currentItem = A[ i ]; // Pick out the current element looping through in array.
			
			compSeq ++; // for equality comparison.
			if( currentItem == key ){ // Return position if found.
				return i;
			}
			compSeq ++; // for > comparison.
			if( currentItem > key ){ 
				// Ascending sorted so key not in array if reach a value > key.
				return -1; // Return fail, not in array.
			}
		}
		return -1; // Gone through whole array and not found, so return fail.
	}
	
	// Binary Search ======================================================================================
	
	public int binSearch( int key, int left, int right ){ // Binary search method.
		
		if( right < left ){ // Run out of elements to search, so key not found.
			return -1; // Return fail.
		}
		
		// Find middle index of remaining search area, round up with Math.ceil for consistency.
		int midPoint = ( int ) Math.ceil( ( right + left ) / 2 );
		
		compBin ++; // For equality comparison.
		if( key == A[ midPoint ] ){ // If the midPoint is the key, element found, return the index.
			return midPoint;
		}
		
		compBin ++; // for > comparison.
		if( key > A[ midPoint ] ){ // If key is potentially in the RHS of the remaining search space.
			return binSearch( key, ( midPoint + 1 ), right ); // Recursive call using mid to right as search space.
		}
		else{
			return binSearch( key, left, ( midPoint - 1 ) ); // Recursive call using left to mid as search space.
		}
		
	}
	
	// Hashing with Linear Probing ==============================================================================
	
	private void addToHash( int value ){ // Insert an element in to the hash data.
		
		int index = hash( value ); // Track the current index for the loop.

		boolean spaceFound = false; // Loop until a space is found.
		
		for( int i = 0; !spaceFound; i++ ){
			
			if( i > 0 && index == hash( value ) ){ 
				// Excludes the first loop, if index equals the hash again then gone through the whole array and
				// not found an empty space, so give notification that it can't be added and return.
				System.out.format( "Failed to add element %s to the hash array. Array is full.", value );
				return;
			}
			
			if( H[ index ] == -1 ){ // If empty space, add it in.
				H[ index ] = value;
				return; // Success so return.
			}
			
			if( index == ( H.length - 1 ) ){ // If reach end of array, loop back around to the start (linear probing).
				index = 0;
			}
			else{ // Else keep searching for a free space.
				index ++;
			}
		
		}
	}
		
	public void readIntoHash( String file ){ // Read a file in and split them for individual insertion.
		
		try {
		FileReader reader = new FileReader( file ); // Call the file using it's name in the String.
		@SuppressWarnings("resource")
		Scanner s = new Scanner( reader );
		
		while( s.hasNextInt() ){ // Loop through getting each value.
			addToHash( s.nextInt() ); // Pull each element & add it to the hash array.
		}
		}
		catch ( Exception e ){ // Catch any reading errors and notify of them.
			System.out.format( "Error processing file %s", file );
		}
		
	}
	
	public int hashSearch( int key ){ // Given a key, use hashing w/ linear probing to see if it's in the hash array.
		
		int index = hash( key ); // Track the current index for the loop.

		boolean found = false; // Loop until found.
		
		for( int i = 0; !found; i++ ){
			
			if( i > 0 && index == hash( key ) ){
				// Excludes first loop, if the index equals the hash again then have looped through the whole
				// array and element not found, so break to return the fail flag.
				break;
			}
			
			compHash ++; // Empty space comparison.
			if( H[ index ] == -1 ){ // Found an empty space, meaning the element is not there.
				break; // Not found, break so can return fail flag.
			}
			
			compHash ++; // equality comparison.
			if( H[ index ] == key ){ // Element found, so return its index.
				return index;
			}
			
			// Increment the index for the next loop or loop it back to 0 if reached the end of the array.
			if( index == ( H.length - 1 ) ){
				index = 0;
			}
			else{
				index ++;
			}
		
		}
		return -1; // Broke from loop, element not found, so return the fail flag.
		
	}

	/********************************************/
	/*** Read a file of numbers into an array ***/
	/********************************************/
	public void readFileIn(String file)
	{
	   try
	   {
	       /** Set up file for reading **/
	       FileReader reader = new FileReader(file);
	       @SuppressWarnings("resource")
	       Scanner in = new Scanner(reader);
	
	       /** Loop round reading in data **/
	       for (int i=0;i<size;i++)
	       {
	          /** Get net value **/
	          A[i] = in.nextInt();
	       }
	    }
	    catch (IOException e)
	    {
	       System.out.println("Error processing file " + file);
	    }
	}
	
	
	/*********************/
	/*** Hash Function ***/
	/*********************/
	public int hash(int key)
	{
	    return key%hSize;
	}
	
	
	/*****************************/
	/*** Display array of data ***/
	/*****************************/
	public void displayData(int line, String header)
	{
	    /* ** Integer Formatter ** */
	    NumberFormat FI = NumberFormat.getInstance();
	    FI.setMinimumIntegerDigits(3);
	
	    /** Print header string **/
	    System.out.print("\n\n"+header);
	
	    /** Display array data **/
	    for (int i=0;i<size;i++)
	    {
	        /** New line? **/
	        if (i%line == 0) 
	        { 
	             System.out.println(); 
	        }
	   
	        /** Display value **/     
	        System.out.print(FI.format(A[i])+" ");
	    }
	}
	
	/**************************/
	/*** Display hash array ***/
	/**************************/
	public void displayHash(int line)
	{
	    /** Integer Formatter **/
	    NumberFormat FI = NumberFormat.getInstance();
	    FI.setMinimumIntegerDigits(3);
	
	    /** Print header string **/
	    System.out.print("\n\nHash Array of size " + hSize);
	
	    /** Display array data **/
	    for (int i=0;i<hSize;i++)
	    {
	        /** New line? **/
	        if (i%line == 0) 
	        { 
	             System.out.println(); 
	        }
	   
	        /** Display value **/     
	        System.out.print(FI.format(H[i])+" ");
	    } 
	}


} /*** End of class Search ***/