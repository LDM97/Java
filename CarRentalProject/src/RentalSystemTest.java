import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class RentalSystemTest {
	
	private RentalSystem rs = RentalSystem.getInstance();

	@Test
	public void testAvailiableCars() {
		int result = rs.availableCars( "small" );
		assertEquals( 20, result ); // No cars rented, so all 20 small cars should be available.
	}

	@Test
	public void testGetRentedCars() {
		Calendar dobc = Calendar.getInstance();
		dobc.set( 1990, 6, 17 );
		Date dob = dobc.getTime();
		Calendar doic = Calendar.getInstance();
		doic.set( 2008, 6, 17 );
		Date doi = doic.getTime();
		Name name = new Name( "Liam", "Mortimer" );
		// Above to create a valid licence.
		
		DrivingLicence dl = new DrivingLicence( name, dob, doi, true );
		rs.issueCar( dl, "small" ); // rent out a small car to the valid licence.
		
		List<CarInterface> result = rs.getRentedCars();
		rs.terminateRental( dl );
		assertEquals( 1, result.size() ); // One small car rented out, so result should be 1.
	}

	@Test
	public void testGetCar() {
		Calendar dobc = Calendar.getInstance();
		dobc.set( 1990, 6, 17 );
		Date dob = dobc.getTime();
		Calendar doic = Calendar.getInstance();
		doic.set( 2008, 6, 17 );
		Date doi = doic.getTime();
		Name name = new Name( "Liam", "Mortimer" );
		// Above to create a valid licence.
		
		DrivingLicence dl = new DrivingLicence( name, dob, doi, true );
		
		rs.issueCar( dl, "small" ); // issue a small car to the licence.
		CarInterface result = rs.getCar( dl );
		rs.terminateRental( dl );
		assertTrue( result instanceof SmallCar ); // is the returned car the expected small car that was issued?
		
	}

	@Test
	public void testIssueCar() {
		Calendar dobc = Calendar.getInstance();
		dobc.set( 1997, 6, 17 ); // Needs to be 21 year old to rent small car, so here (18) should not rent the car.
		Date dob = dobc.getTime();
		Calendar doic = Calendar.getInstance();
		doic.set( 2008, 6, 17 );
		Date doi = doic.getTime();
		Name name = new Name( "Liam", "Mortimer" );
		// Above to create a valid licence.
		
		DrivingLicence dl = new DrivingLicence( name, dob, doi, true );
		
		rs.issueCar( dl, "small" ); // Should fail to issue, as driver not old enough.
		int result = rs.getRentedCars().size();
		assertEquals( 0, result ); // no cars should be rented out as driver is not old enough to rent a small car.
	}

	@Test
	public void testTerminateRental() {
		Calendar dobc = Calendar.getInstance();
		dobc.set( 1990, 6, 17 );
		Date dob = dobc.getTime();
		Calendar doic = Calendar.getInstance();
		doic.set( 2008, 6, 17 );
		Date doi = doic.getTime();
		Name name = new Name( "Liam", "Mortimer" );
		// Above to create a valid licence.
		
		DrivingLicence dl = new DrivingLicence( name, dob, doi, true );
		
		rs.issueCar( dl, "large" ); // Car issued...
		rs.terminateRental( dl ); // Then rental terminated...
		assertEquals( 0, rs.getRentedCars().size() ); // so should be no cars rented == terminate rental has removed the rental record.
		
	}

}
