import static org.junit.Assert.*;

import org.junit.Test;

public class LargeCarTest {

	@Test
	public void testShortDrive() {
		LargeCar test = new LargeCar( 65, true );
		int result = test.drive( 35 ); // < 50 km, so consumes 1 litre per 15km, rounded up, so expect 3 litres consumed.
		assertEquals( 3, result );  
	}
	
	@Test
	public void testLongDrive() {
		LargeCar test = new LargeCar( 65, true );
		int result = test.drive( 60 ); // 1 litre per 15km for first 50, then 1 litre per 20km for rest.
		assertEquals( 5, result ); // first 50 / 15 rounds up to 4 litres, 10 / 20 rounds up to 1 litre, expect 5 litres consumed.
	}
	

}
