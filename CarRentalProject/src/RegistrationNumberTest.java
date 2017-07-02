import static org.junit.Assert.*;

import org.junit.Test;

public class RegistrationNumberTest {

	@Test
	public void testToString() { // Test the toString method is working and returning the unique registration number.
		RegistrationNumber r = new RegistrationNumber();
		assertEquals( "a1001", r.toString() ); // Second made, so should be a1001.
	}

	@Test
	public void testGetComponentOutOfBOunds() { // Test error for component search being out of bounds.
		RegistrationNumber r = new RegistrationNumber();
		assertEquals( ' ', r.getComponentAt( 5 ) ); // returns blank char / space if invalid input.
	}
	
	@Test
	public void testGetComponentValid() { // Test returns valid component. First reg made, so 0 should be the last char.
		RegistrationNumber r = new RegistrationNumber();
		assertEquals( '0', r.getComponentAt( 4 ) );
	}

	@Test
	public void testGetLetter() { // Test returns the letter appropriately.
		RegistrationNumber r = new RegistrationNumber();
		assertEquals( 'a', r.getLetter() );
	}

	@Test
	public void testGetSerialNumber() { // Test serial number is returned appropriately.
		RegistrationNumber r = new RegistrationNumber();
		assertEquals( "1004", r.getSerialNumber() ); // 5th reg implemented, so last digit should have incremented to 4.
	}

}
