
public class Box {
	
	// Simple box fields.
	private int height;
	private int width;
	
	Box( int height, int width ){
		// Set the fields to the desired in the constructor.
		this.height = height;
		this.width = width;
	}
	
	// Basic get methods, to access the fields, below.
	public int getHeight(){
		return this.height;
	}
	
	public int getWidth(){
		return this.width;
	}
	
}
