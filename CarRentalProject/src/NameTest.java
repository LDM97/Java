
import static org.junit.Assert.*;

import org.junit.Test;

public class NameTest {

	@Test ( expected = Exception.class )
	public void testEmptyName() {
		Name name = new Name( "", "Mortimer" ); // Haven't entered a full name, so should throw exception.
	}
	
	@Test
	public void testValidName() { // check stores the valid name and returns it correctly.
		Name name = new Name( "Liam", "Mortimer" );
		assertEquals( "Liam Mortimer", name.getName() );
	}

}
