
public abstract class Car implements CarInterface {
	
	RegistrationNumber registrationNumber;
	int tankCapacity; // 45 for small car, 65 for large car.
	int currentFuelLevel;
	boolean rented;

	Car( boolean rented ){
		this.rented = rented;
		this.registrationNumber = new RegistrationNumber();
	}
	
	//Get registration number method.
	public RegistrationNumber getRegistrationNumber(){
		return this.registrationNumber;
	}
	
	public String toString(){ // Registration number provides a good compact overview of the car.
	 	return this.registrationNumber.toString();
	}
	
	//Get capacity.
	public int getTankCapacity(){
		return this.tankCapacity;
	}
	
	//Get the amount of litres currently in the tank.
	public int getCurrentFuelLevel(){
		return this.currentFuelLevel;
	}
	
	//Car tank is full or not.
	public boolean isTankFull(){
		if( this.currentFuelLevel == this.tankCapacity ){
			return true;
		}
		else{
			return false;
		}
	}
	
	//Car is rented or not.
	public boolean isRented(){
		return this.rented;
	}
	
	public void setRented( boolean rented ){ // Change rented state.
		this.rented = rented;
	}
	
	//Method to add a given number of litres of fuel to the tank.
	public void addFuel( int litresAdded ){
		if( !isRented() || isTankFull() ){ // If cannot be filled at this point.
			System.out.format( "Added 0 Litres of Fuel to %s.\n", this.toString() );
			return;
		}
		
		else if( litresAdded + getCurrentFuelLevel() > getTankCapacity() ){ // If amount to be added > tank's capacity, just add what's needed.
			System.out.format( "Added %s Litres of Fuel to %s.\n", 
					( getTankCapacity() - getCurrentFuelLevel() ), this.toString() );
			this.currentFuelLevel = getTankCapacity();
			return;
		}
		else{ // not unable to be filled and not too much, just add the fuel.
			System.out.format( "Added %s Litres of Fuel to %s.\n", litresAdded, this.toString() );
			this.currentFuelLevel += litresAdded;
			return;
		}
	}
	
	// Drive method, varies depending on car size.
	public abstract int drive( int driveDistance );
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( registrationNumber == null ) ? 0 : registrationNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		Car other = (Car) obj;
		if ( registrationNumber == null ) {
			if ( other.registrationNumber != null )
				return false;
		} else if ( !registrationNumber.equals( other.registrationNumber ) )
			return false;
		return true;
	}

}
