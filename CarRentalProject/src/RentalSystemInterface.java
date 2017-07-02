import java.util.List;

public interface RentalSystemInterface {
	
	// Fields to store the number of cars.
	// Array collections to store the cars themselves.
	// Map to map driving licence's to cars that have been rented.
	
	// Return a singleton instance of the rental system.
	
	public int availableCars( String typeOfCar ); // Return the number of available cars of the given type.
	
	public List<CarInterface> getRentedCars(); // Return a list of all cars that are currently rented.
	
	public CarInterface getCar( DrivingLicence drivingLicence ); // Return the rented car associated with the given driving licence.
	
	public void issueCar( DrivingLicence drivingLicence, String typeOfCar ); // rent a car if licence is valid to do so and this car type
	// is available.
	
	public int terminateRental ( DrivingLicence drivingLicence ); // End a rental associated with the given driving licence.
	
}
