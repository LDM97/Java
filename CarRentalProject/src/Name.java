
public final class Name {

	private final String firstName;
	private final String surname;
	
	Name( String firstName, String surname ){
		
		if( firstName.equals( "" ) || surname.equals( "" ) ){ // If empty strings, full name not entered.
			throw new IllegalArgumentException( "A Full Name Has Not Been Entered." );
		}
		
		// else just set valid inputs.
		this.firstName = firstName;
		this.surname = surname;
	}

	public String getName(){ // returns the full name.
		return this.firstName + " " + this.surname;
	}
	
	public String getFirstName(){ // returns the first name.
		return this.firstName;
	}
	
	public String getSurname(){ // returns the surname.
		return this.surname;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Name other = (Name) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}
	
}
