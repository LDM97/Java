
public class CarFactory {
	
	public static Car getInstance( int fuelLevel, boolean rented, String type ){
		
		type.toLowerCase(); // no mistakes if string is in capitals or first capital.
		
		if( type.equals( "small" ) ){ // return a new SmallCar if it's requested.
			return new SmallCar( fuelLevel, rented );
		}
		else if( type.equals( "large" ) ){ // return a new LargeCar if it's requested.
			return new LargeCar( fuelLevel, rented );
		}
		else{ // If doesn't match small or large, invalid input.
			throw new IllegalArgumentException( "Invalid Car Type Entered." );
		}
		
	}
	
}
