
public final class RegistrationNumber {
	
	// Statics used to create unique registration numbers.
	private static int serialNumberGenerator = 1000;
	private static char uniqueLetterGenerator = 'a';
	private String registrationNumber = "";
	
	RegistrationNumber(){
		// Assemble the registration number.
		this.registrationNumber += uniqueLetterGenerator;
		this.registrationNumber += serialNumberGenerator;
		
		if( serialNumberGenerator == 9999 ){ 
			if( uniqueLetterGenerator == 'z' ){
				// If gone through all 26 letters with 1000 - 9999 for each letter, exhausted all possible unique combinations
				// which fit the given registration number format.
				throw new IllegalStateException( "All Possible Unique Registration Numbers Have Been Used." );
			}
			// if serial number going to exceed 4 digits, increment letter and reset the number to the lowest 4 digit number.
			uniqueLetterGenerator ++;
			serialNumberGenerator = 1000;
		}
		else{
			serialNumberGenerator ++; // else just continue through the unique numbers.
		}
	}
	
	public String toString(){
		return this.registrationNumber;
	}
	
	public char getComponentAt( int index ){ // Provides access to the char at registration number's given index.
		
		if( index > ( registrationNumber.length() - 1 ) || index < 0 ){ // Check index entered is valid.
			System.out.println( "Search Index Given is Out Of Bounds" );
			return ' ';
		}
		
		return this.registrationNumber.charAt( index ); // Is valid, get charAt( index ).
	}
	
	public char getLetter(){ // Return the unique letter component of registration number.
		return this.registrationNumber.charAt( 0 );
	}
	
	public String getSerialNumber(){ // Return the serial number component of the registration number.
		return this.registrationNumber.substring( 1, 5 );
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( registrationNumber == null ) ? 0 : registrationNumber.hashCode());
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
		RegistrationNumber other = ( RegistrationNumber ) obj;
		if ( registrationNumber == null ) {
			if ( other.registrationNumber != null )
				return false;
		} else if ( !registrationNumber.equals(other.registrationNumber ) )
			return false;
		return true;
	}
}
