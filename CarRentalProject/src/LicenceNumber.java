
public final class LicenceNumber {

	private final String initials;
	private final int dateOfIssue;
	private final int serialNumber;
	
	// static int incremented after each creation in constructor, allows unique serial numbers.
	private static int serialNumGenerator = 10;
	
	
	LicenceNumber( Name driverName, int dateOfIssue ){
		// set initials field to be capital representation of the name's initials entered.
		String initials = "";
		initials += driverName.getFirstName().charAt( 0 ); 
		initials += driverName.getSurname().charAt( 0 );
		this.initials = initials;
		this.initials.toUpperCase();
		
		this.dateOfIssue = dateOfIssue;
		this.serialNumber = serialNumGenerator; // set the unique serialNumber
		serialNumGenerator ++; // Increment the generator so the next LicenceNumber has a unique number.
	}
	
	public String toString(){
		return getLicenceNumber(); // licenceNumber returns a good string representation, so used here.
	}
	
	public String getLicenceNumber() {
		// assemble and return the licence number in form specified.
		return this.initials + "-" + this.dateOfIssue + "-" + serialNumber;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dateOfIssue;
		result = prime * result + ((initials == null) ? 0 : initials.hashCode());
		result = prime * result + serialNumber;
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		LicenceNumber other = ( LicenceNumber ) obj;
		if ( dateOfIssue != other.dateOfIssue )
			return false;
		if ( initials == null ) {
			if ( other.initials != null )
				return false; 
		} else if ( !initials.equals( other.initials ) )
			return false;
		if ( serialNumber != other.serialNumber )
			return false;
		return true;
	}
	
}
