import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

public class RentalSystem implements RentalSystemInterface {
	
	private static final RentalSystem RENTAL_SYSTEM = new RentalSystem(); // Singleton instance of the system.
	
	private final int SMALL_CAR_TANK_CAPACITY = 45; // Used to start every car with a full tank.
	private final int LARGE_CAR_TANK_CAPACITY = 65; // ...
	private final int NUMBER_OF_SMALL_CARS = 20;
	private final int NUMBER_OF_LARGE_CARS = 10;
	private final int NUMBER_OF_CARS = NUMBER_OF_SMALL_CARS + NUMBER_OF_LARGE_CARS;
	
	// Link rented cars to a driving licence which has rented them.
	private  Map<DrivingLicence, CarInterface> rentedCars = new HashMap<DrivingLicence, CarInterface>();
	
	// Store all cars. Also split into small and large for easier searching later.
	private CarInterface[] cars = new CarInterface[ NUMBER_OF_CARS ];
	private CarInterface[] smallCars = new CarInterface[ NUMBER_OF_SMALL_CARS ];
	private CarInterface[] largeCars = new CarInterface[ NUMBER_OF_LARGE_CARS ];
	
	private RentalSystem(){
		
		for( int i = 0; i < NUMBER_OF_SMALL_CARS; i++ ){
			// Create small cars and store them in the system.
			CarInterface newCar = CarFactory.getInstance( SMALL_CAR_TANK_CAPACITY, false, "small" );
			this.cars[i] = newCar;
			this.smallCars[i] = newCar;
		}
		
		int j = NUMBER_OF_SMALL_CARS; // Add large cars to the end of the small cars in the cars array.
		for( int i = 0; i < NUMBER_OF_LARGE_CARS; i++ ){
			// Create large cars and store them in the system.
			CarInterface newCar = CarFactory.getInstance( LARGE_CAR_TANK_CAPACITY, false, "large" );
			this.cars[j] = newCar;
			this.largeCars[i] = newCar;
			j++;
		}
	}
	
	public static RentalSystem getInstance(){ // singleton
		return RENTAL_SYSTEM;
	}
	
	public int availableCars( String typeOfCar ){ // valid parameters: "small" or "large"
		typeOfCar.toLowerCase(); // Avoid capital letters resulting in validation failure.
		
		if( !typeOfCar.equals( "small" ) && !typeOfCar.equals( "large" ) ){ // check input.
			throw new IllegalArgumentException( "Invalid Type Of Car Input.\n" );
		}
		
		int availableCars = 0;
		
		if( typeOfCar.equals( "small" ) ){ // if small, loop through small cars & ++ available if not rented.
			for( CarInterface car : this.smallCars ){
				if( !car.isRented() ){
					availableCars ++;
				}
			}
		}
		else{
			for( CarInterface car : this.largeCars ){ // if large, loop through large cars & ++ available if not rented.
				if( !car.isRented() ){
					availableCars ++;
				}
			}
		}
		
		return availableCars;
	}
	
	public List<CarInterface> getRentedCars(){
		List<CarInterface> rentedCars = new ArrayList<CarInterface>();
		
		for( CarInterface car : this.rentedCars.values() ){ // Get all rentedCars and put them in a list to be returned.
			rentedCars.add( car );
		}
		return rentedCars;
	}
	
	public CarInterface getCar( DrivingLicence drivingLicence ){ 
		
		if( !this.rentedCars.containsKey( drivingLicence ) ){ // If the licence has no rented car attributed to it.
			System.out.format( "Driving licence %s has no rented cars attributed to it.\n", drivingLicence );
			return null;
		}
		return this.rentedCars.get( drivingLicence );
	}
	
	public void issueCar( DrivingLicence drivingLicence, String typeOfCar ){
		typeOfCar.toLowerCase(); // avoid capitals failing validation.
		
		if( !typeOfCar.equals( "small" ) && !typeOfCar.equals( "large" ) ){ // check input.
			throw new IllegalArgumentException( "Invalid Type Of Car Input.\n" );
		}
		
		// get current date for comparisons.
		Calendar currentDate = Calendar.getInstance();
		
		// get DOB & DOI
		Calendar dob = Calendar.getInstance(); 
		dob.setTime( drivingLicence.getDateOfBirth() );
		Calendar doi = Calendar.getInstance();
		doi.setTime( drivingLicence.getDateOfIssue() );
		
		// type of car available && do they have full licence? && not already renting a car 
		if( availableCars( typeOfCar ) > 0 && drivingLicence.isFullLicence() && !this.rentedCars.containsKey( drivingLicence ) ){
			
			// small car && 21 years old && held licence for 1 year.
			if( typeOfCar.equals( "small" ) && currentDate.get( Calendar.YEAR ) - dob.get( Calendar.YEAR ) >= 21 && 
				currentDate.get( Calendar.YEAR ) - doi.get( Calendar.YEAR ) >= 1 ){
				
				for( CarInterface car : this.smallCars ){
					// find a SmallCar that is not rented and has full tank.
					if( !car.isRented() && car.isTankFull() ){
						// rent the car and set the car to rented.
						rentedCars.put( drivingLicence, car );
						car.setRented( true );
						return; // Successfully issued, so return.
					}
				}
				
			}
			
			// large car && 25 years old && held licence for 5 years.
			if( typeOfCar.equals( "large" ) && currentDate.get( Calendar.YEAR ) - dob.get( Calendar.YEAR ) >= 25 &&
				currentDate.get( Calendar.YEAR ) - doi.get( Calendar.YEAR ) >= 5 ){
				
				for( CarInterface car : this.largeCars ){
					// find a LargeCar that is not rented and has full tank.
					if( !car.isRented() && car.isTankFull() ){
						// rent the car and set the car to rented.
						rentedCars.put( drivingLicence, car );
						car.setRented( true );
						return; // Successfully issued, so return.
					}
				}
			}
		
		}
		
		// returns after successful issue. If code gets here, unable to issue car to driver for some reason.
		System.out.format( "Unable to issue a car to driver %s\n", drivingLicence );
		return;
		
	}
	
	public int terminateRental ( DrivingLicence drivingLicence ){
		
		// return 0 if the licence has no rented car associated with it.
		if( !this.rentedCars.containsKey( drivingLicence ) ){
			return 0;
		}
		
		CarInterface car = this.rentedCars.get( drivingLicence );
		// Remove the record of the rented car to the licence and set the car to not be rented.
		this.rentedCars.remove( drivingLicence );
		car.setRented( false );

		// return the amount of litres needed to fill the tank.
		return car.getTankCapacity() - car.getCurrentFuelLevel();
		
	}
	
}
