import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runners.JUnit4;

public class SmallCarTest {

	@Test
	public void testValidDrive() {
		SmallCar test = new SmallCar( 45, true );
		int result = test.drive( 52 );
		assertEquals( 3, result ); // Consumes 1 litre per up to and including 25km, so 52km should consume 3 litres.
	}
	
	@Test
	public void testDriveNoFuel(){
		SmallCar test = new SmallCar( 0, true ); // Set with 0 initial fuel.
		int result = test.drive( 3 );
		assertEquals( 0, result); // Expect 0 as no fuel so cannot be driven.
	}
	
	@Test
	public void testDriveNotRented(){
		SmallCar test = new SmallCar( 45, false ); // not rented, so should not be able to be driven.
		int result = test.drive( 4 );
		assertEquals( 0, result ); // Expect 0 as not rented, so cannot be driven.
	}
	

}
