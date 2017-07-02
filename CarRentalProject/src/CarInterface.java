
public interface CarInterface {
	
	public RegistrationNumber getRegistrationNumber(); // method to get the registration number.
	
	public String toString(); // Appropriately overwrite the toString method.
	
	public int getTankCapacity(); // return the car's tank capacity.
	
	public int getCurrentFuelLevel(); // return the car's current fuel level.
	
	public boolean isTankFull(); // is the tank full?
	
	public boolean isRented(); // is the car rented or not?
	
	public void setRented( boolean rented ); // Change if it's rented or not.
	
	public void addFuel( int litresAdded ); // add the designated number of litres to the cars fuel tank.
	
	public int drive( int driveDistance ); // drive the car the specified distance, consuming fuel appropriately.
	
}
