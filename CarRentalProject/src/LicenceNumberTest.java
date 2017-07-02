import static org.junit.Assert.*;

import org.junit.Test;

public class LicenceNumberTest {

	@Test
	public void testToString() { // Test Name and dateOfIssue to see if concatenated properly in toString.
		Name name = new Name( "Liam", "Mortimer" );
		LicenceNumber l = new LicenceNumber( name, 2015 );
		assertEquals( "LM-2015-10", l.toString() ); // First, so expect serial number 10 combined w/ initials and date of issue.
	}

}
