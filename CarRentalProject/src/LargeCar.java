
public class LargeCar extends Car {
	
	LargeCar( int fuelLevel, boolean rented ){
		super( rented );
		this.tankCapacity = 65; // Set tank capacity for large car.
		
		// Use this method as it requires tankCapacity to be set first, so cannot be done in super constructor.
		setInitialFuelLevel( fuelLevel );
	}
	
	public int drive( int driveDistance ){
		
		if( !isRented() || getCurrentFuelLevel() <= 0 ){ // Can the car be driven?
			System.out.format( "Car %s was unable to be driven.", this.toString() );
			return 0;
		}
		
		// Fuel consumption increases after 50km of journey for large car.
		final int CHANGE_IN_FUEL_CONSUMPTION = 50;
		final int FIRST_PART_OF_JOURNEY_CONSUMPTION = 15; // 1 litre per 15km.
		final int SECOND_PART_OF_JOURNEY_CONSUMPTION = 20; // 1 litre per 20km.
		
		if( driveDistance <= CHANGE_IN_FUEL_CONSUMPTION ){ // If < 50km, calculate with 1 liter per 15km.
			double litres = Math.ceil( ( double ) driveDistance / FIRST_PART_OF_JOURNEY_CONSUMPTION );
			int litresConsumed = (int) litres;
			this.currentFuelLevel -= litresConsumed;
			return litresConsumed; // Deduct fuel consumed from tank and return it.
		}
		else{ 
			// If > 50km, calculate fuel used w/ rate for first 50km, then calculate it for the
			// remaining km.
			
			// Calculate for first half of journey.
			double litres = Math.ceil( ( double ) CHANGE_IN_FUEL_CONSUMPTION / FIRST_PART_OF_JOURNEY_CONSUMPTION );
			int litresConsumed = (int) litres;
			driveDistance -= CHANGE_IN_FUEL_CONSUMPTION; // Take the first part drive distance away, leaving remaining distance.
			
			litres = Math.ceil( ( double ) driveDistance / SECOND_PART_OF_JOURNEY_CONSUMPTION );
			litresConsumed += (int) litres; // Calculate for remaining distance w/ different fuel consumption, and add it to first part.
			
			this.currentFuelLevel -= litresConsumed;
			return litresConsumed; // Deduct fuel consumed from tank and return it.
		}
	}
	
	private void setInitialFuelLevel( int fuelLevel ){
		if( fuelLevel > this.tankCapacity ){ // Cap the starting fuel level at the tank capacity.
			this.currentFuelLevel = this.tankCapacity;
		}
		else if( fuelLevel < 0 ){ // Fuel cannot be set in minus, only goes minus through drive function.
			this.currentFuelLevel = 0;
		}
		else{
			this.currentFuelLevel = fuelLevel;
		}
	}
	
}
