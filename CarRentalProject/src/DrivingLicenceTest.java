import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class DrivingLicenceTest {

	@Test ( expected = Exception.class )
	public void testDrivingLicenceInFuture() {
		
		Calendar dobc = Calendar.getInstance();
		dobc.set( 2025, 18, 17 ); // Date of birth set in the future, so should throw IllegalArgumentException.
		Date dob = dobc.getTime();
		
		Calendar doic = Calendar.getInstance();
		doic.set( 2014, 6, 17 );
		Date doi = doic.getTime();
		
		Name name = new Name( "Liam", "Mortimer" );
		
		DrivingLicence dl = new DrivingLicence( name, dob, doi, true );
	}

}
