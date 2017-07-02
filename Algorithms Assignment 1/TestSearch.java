
/******************************************************/
/*** Purpose: Test class to illustrate Search class ***/
/***                                                ***/
/*** Author: L. J. Steggles                         ***/
/*** Date: 23/09/2016   	                        ***/
/******************************************************/

/*
 * Edited by Liam Mortimer
 * Student Number: 150228550
 */

import java.io.*;
import java.util.Scanner;

public class TestSearch
{
	
	final static int SEARCH_SIZE = 10; // Both search data sets are size 10, so irrelevant whichever is used.
	final static int NUM_OF_SEARCHES = 2; // Used to avoid random numbers during output calculation.
	
	// Given the search file, read in the search data and return it in an array.
	private static int[] readInSearchData( String searchFile ){ 

		int[] searchData = new int[ SEARCH_SIZE ];
		
		try
		   {
		       // Setup search data for reading.
		       FileReader reader = new FileReader( searchFile );
		       @SuppressWarnings("resource")
		       Scanner s = new Scanner( reader );
		
		       for ( int i = 0; s.hasNextInt(); i++ ) // Loop reading in search data.
		       {
		          searchData[ i ] = s.nextInt(); // Store the current value.
		       }
		    }
		    catch (IOException e)
		    {
		       System.out.println("Error processing file " + searchFile ); // Deal with potential reading errors.
		    }
		return searchData; // return the search data now it has been processed.
		
	}
	
	// Given the test data, search data, arraySize & hashSize required, returns the total comparisons for each search 
	// type on the given data sets.
	private static int[] search( String dataFile, String searchFile, int arraySize, int hashSize ){
		
		// Create a Search object and set it up with the data entered.
		Search s = new Search( arraySize, hashSize );
		s.readFileIn( dataFile );
		s.readIntoHash( dataFile );
		
		// Assemble the search data & run the searches using the Search class & data.
		int[] searchData = readInSearchData( searchFile );
		
		for( int key : searchData ){ // Run the searches for each search element.
			s.seqSearch( key ); // Run sequential Search.
	    	s.binSearch( key, 0, ( arraySize - 1 ) ); // Run binary search.
	    	s.hashSearch( key ); // Run hash search.
	    }
		
		// Record the total comparison results and return them.
		int[] comparisonResults = { s.compSeq, s.compBin, s.compHash };
		return  comparisonResults; // Return the comparison results for sequential, binary & hash search.
	}
	
	// Given the comparison results array from search method above, and a given name for the search, print out the results 
	// in an easy to read format.
	private static void printResults( int[] comparisonResults, String searchName ){
		
		// Print out the total number of comparisons for each search.
		System.out.format( "Search %s Total Comparisons\n\nSequential Search: %s\nBinary Search: %s\nHash Search: %s\n\n", 
				searchName, comparisonResults[ 0 ], comparisonResults[ 1 ], comparisonResults[ 2 ] );
		
		// Print out the average number of comparisons for each search.
		System.out.format( "Search %s Average Comparisons\n\nSequential Search: %s\nBinary Search: %s\nHash Search: %s\n",
				searchName, comparisonResults[ 0 ] / SEARCH_SIZE, comparisonResults[ 1 ] / SEARCH_SIZE, comparisonResults[ 2 ] / SEARCH_SIZE );
		
		// Print a line to split from the next print output.
		System.out.println( "================================================\n" );
	}
		
	public static void main(String[] args) {
		
		// Run the searches using search one & search two data, recording the total comparison results.
		int[] s1 = search( "data1.txt", "search1.txt", 100, 151 );
		int[] s2 = search( "data2.txt", "search2.txt", 1000, 1499 );
		
		// Print out the results neatly. Requires search method's returned results array and a name for the search.
		printResults( s1, "One" );
		printResults( s2, "Two" );
		
		// Print out the average comparisons between the two data sets for each search.
		System.out.format( "Total Average\n\nSequential Search: %s\nBinary Search: %s\n"
				+ "Hash Search: %s\n", ( s1[ 0 ] + s2[ 0 ] ) / ( SEARCH_SIZE * NUM_OF_SEARCHES ), ( s1[ 1 ] + s2[ 1 ] ) / 
				( SEARCH_SIZE * NUM_OF_SEARCHES ), ( s1[ 2 ] + s2[ 2 ] ) / ( SEARCH_SIZE * NUM_OF_SEARCHES ) );
	
	}
   
}