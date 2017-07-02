import java.util.Date;
import java.util.Calendar;

public final class DrivingLicence {

	private final Date dateOfBirth;
	private final Date dateOfIssue;
	private final Name name;
	private final boolean isfullLicence;
	private final LicenceNumber licenceNumber;
	
	
	DrivingLicence( Name driverName, Date dateOfBirth, Date dateOfIssue, boolean fullLicence ){
		
		// get a calendar for current date to use in validation checks.
		Calendar currentDate = Calendar.getInstance();
		
		// Use Calendar to check date values, avoids deprecated methods.
		Calendar dob = Calendar.getInstance();
		dob.setTime( dateOfBirth );
		Calendar doi = Calendar.getInstance();
		doi.setTime( dateOfIssue );
		
		// Check that the date and month is valid, and that the year is before today (not in the future).
		if( dob.get( Calendar.DAY_OF_MONTH ) < 1 || dob.get(Calendar.DAY_OF_MONTH ) > 31 || 
				dob.get( Calendar.MONTH ) < 0 || dob.get( Calendar.MONTH ) > 11 || currentDate.before( dob ) ){
			throw new IllegalArgumentException( "Invalid Date Of Birth Entered." );
		}
		
		if( doi.get( Calendar.DAY_OF_MONTH ) < 1 || doi.get( Calendar.DAY_OF_MONTH ) > 31 || 
				doi.get( Calendar.MONTH ) < 0 || doi.get( Calendar.MONTH ) > 11 || currentDate.before( doi ) ){
			throw new IllegalArgumentException( "Invalid Date of Issue Entered." );
		}
		
		// Set the fields now all proved valid.
		this.dateOfBirth = dateOfBirth;
		this.dateOfIssue = dateOfIssue;
		this.isfullLicence = fullLicence;
		this.name = driverName;
		this.licenceNumber = new LicenceNumber( driverName, doi.get( Calendar.YEAR ) );
		
	}
	
	// Methods to provide access the private fields.
	
	public String getDriversName(){
		return this.name.getName();
	}
	
	public Date getDateOfBirth(){
		return this.dateOfBirth;
	}
	
	public Date getDateOfIssue(){
		return this.dateOfIssue;
	}
	
	public boolean isFullLicence(){
		return this.isfullLicence;
	}
	
	public LicenceNumber getLicenceNumber(){
		return this.licenceNumber;
	}
	
	public String toString(){
		return this.licenceNumber.toString(); // Call licenceNumber toString, as provides a compact overview of the driver.
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( dateOfBirth == null ) ? 0 : dateOfBirth.hashCode() );
		result = prime * result + ( ( dateOfIssue == null ) ? 0 : dateOfIssue.hashCode() );
		result = prime * result + ( ( licenceNumber == null ) ? 0 : licenceNumber.hashCode() );
		result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
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
		DrivingLicence other = ( DrivingLicence ) obj;
		if ( dateOfBirth == null ) {
			if ( other.dateOfBirth != null )
				return false;
		} else if ( !dateOfBirth.equals( other.dateOfBirth ) )
			return false;
		if ( dateOfIssue == null ) {
			if ( other.dateOfIssue != null )
				return false;
		} else if ( !dateOfIssue.equals( other.dateOfIssue ) )
			return false;
		if ( licenceNumber == null ) {
			if ( other.licenceNumber != null )
				return false;
		} else if ( !licenceNumber.equals(other.licenceNumber) )
			return false;
		if ( name == null ) {
			if ( other.name != null )
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
