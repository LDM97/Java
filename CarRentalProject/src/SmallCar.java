
public class SmallCar extends Car{
	
	SmallCar( int fuelLevel, boolean rented ){
		super( rented );
		this.tankCapacity = 45; // Set tank capacity for small car.
		setInitialFuelLevel( fuelLevel ); // Used as requires tankCapacity to be set, so can't be done in super constructor.
	}
	
	public int drive( int driveDistance ){
		
		if( !isRented() || getCurrentFuelLevel() <= 0 ){ // Can the car be driven?
			System.out.format( "Car %s was unable to be driven.\n", this.toString() );
			return 0;
		}
		
		final int LITRES_CONSUMED_PER_KM = 25; // Small car's fuel consumption rate, 1 litre per 25 km.
		double litres = Math.ceil( ( double ) driveDistance / LITRES_CONSUMED_PER_KM );
		int litresConsumed = (int) litres;
		// Deduct fuel used from tank and return it.
		this.currentFuelLevel -= litresConsumed;
		return litresConsumed;
	}
	
	private void setInitialFuelLevel( int fuelLevel ){
		if( fuelLevel > this.tankCapacity ){ // Cap the starting fuel level at the tank capacity.
			this.currentFuelLevel = this.tankCapacity;
		}
		else if( fuelLevel < 0 ){ // Cannot be set to less than 0 initially.
			this.currentFuelLevel = 0;
		}
		else{
			this.currentFuelLevel = fuelLevel;
		}
	}
	
}
