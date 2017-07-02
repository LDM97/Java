import static org.junit.Assert.*;

import org.junit.Test;

public class CarTest {

	@Test
	public void testGetRegistrationNumber() {
		SmallCar test = new SmallCar( 45, false );
		RegistrationNumber result = test.getRegistrationNumber();
		assertEquals( "a1003", result.toString() ); // is 4th car implemented, so expect registration number of "a1003."
	}

	@Test
	public void testToString() {
		LargeCar test = new LargeCar( 65, false );
		String result = test.toString();
		assertEquals( "a1000", result ); // toString should just give registration number, as first implemented should be "a1000."
	}

	@Test
	public void testTankIsFull() {
		SmallCar test = new SmallCar( 45, false );
		boolean result = test.isTankFull();
		assertEquals( true, result ); // Small car has max capacity of 45, so should be full with constructor set to 45.
	}
	
	public void testTankNotFull() {
		LargeCar test = new LargeCar( 45, false );
		boolean result = test.isTankFull();
		assertEquals( false, result ); // Large car has max capacity of 65, so 45 in constructor should not make it full.
	}

	@Test
	public void testAddFuelNotRented() {
		SmallCar test = new SmallCar( 35, false );
		test.addFuel( 20 );								 // Rented set to false, so the extra 20 litres should not be added
		assertEquals( 35, test.getCurrentFuelLevel() );  // as is not rented, so should remain at constructor's 35 initial litres.
	}
	
	public void testAddTooMuchFuel(){
		LargeCar test = new LargeCar( 50, true );
		test.addFuel( 40 );								// Should not exceed tank capacity of 65, should only add the fuel needed,
		assertEquals( 65, test.getCurrentFuelLevel() ); // Thus adding extra fuel here should just set it to max capacity.
	}

}
