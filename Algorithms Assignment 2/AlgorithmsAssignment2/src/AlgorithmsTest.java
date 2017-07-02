import static org.junit.Assert.*;

import org.junit.Test;
import java.util.*;

public class AlgorithmsTest {
	
	List<Truck> trucks = new ArrayList<Truck>();
	List<Box> boxes = new ArrayList<Box>();
	
	@Test
	public void testNextFit() {
		
		boxes.add( new Box( 5, 10 ) );
		boxes.add( new Box( 50, 10 ) );
		boxes.add( new Box( 10, 10 ) );
		
		Algorithms.nextFit( trucks, boxes );
		
		// The 3 boxes should have their own piles in the truck. Though box 3 can fit on top of box 1, is ignored as the search for 3 starts
		// from box 2.
		assertEquals( 3, trucks.get( 0 ).getPiles().size() );
	}
	
	@Test
	public void testFirstFit() {
		
		boxes.add( new Box( 5, 10 ) );
		boxes.add( new Box( 50, 10 ) );
		boxes.add( new Box( 10, 10 ) );
		
		Algorithms.firstFit( trucks, boxes );
		
		// 3 boxes should go across 2 piles. First fit will find that box 3 fits on top of box 1 with its search starting from beginning.
		assertEquals( 2, trucks.get( 0 ).getPiles().size() );
	}
	
	@Test
	public void testMaxBoxes(){
		// Test that if max number of boxes per truck is given, that it is adhered to.
		boxes.add( new Box( 5, 5 ) );
		boxes.add( new Box( 5, 5 ) );
		boxes.add( new Box( 5, 5 ) );
		boxes.add( new Box( 5, 5 ) );
		boxes.add( new Box( 5, 5 ) );
		boxes.add( new Box( 5, 5 ) );
		
		// Define trucks to have max 3 boxes.
		trucks.add( new Truck( 50, 100, 3 ) );
		Algorithms.firstFit( trucks, boxes );
		
		// All boxes can fit in 1 truck, but will be split across 3 as max boxes per truck is defined as 3.
		assertEquals( 2, trucks.size() );
	}
	
	@Test
	public void boxStackTest(){
		boxes.add( new Box( 5, 10 ) );
		boxes.add( new Box( 4, 10 ) );
		boxes.add( new Box( 6, 8 ) );
		// Last box too wide to top the pile (12 > 8) so will make a new pile, creating 2 in the truck.
		boxes.add( new Box( 5, 12 ) );
		
		Algorithms.firstFit( trucks, boxes );
		
		// Prove that boxes only top a pile if they are <= width of last box. > results in new pile or truck.
		assertEquals( 2, trucks.get( 0 ).getPiles().size() );
	}
	
	@Test
	public void maxPileHeight(){
		Algorithms.truckHeight = 50;
		Algorithms.truckWidth = 100;
		
		// First 2 boxes pile ontop perfectly meeting the truck's height.
		boxes.add( new Box( 25, 10 ) );
		boxes.add( new Box( 25, 10 ) );
		// Box 3 forced onto a new pile, despite being slim enough to top the pile, as will exceed the truck height.
		boxes.add( new Box( 5, 8 ) );
		
		Algorithms.firstFit( trucks,  boxes );
		
		// 2 piles, first two boxes stack, box 3 will be forced onto another pile so as not to exceed the truck height.
		assertEquals( 2, trucks.get( 0 ).getPiles().size() );
	}

}
