package exceptionClasses;

public class InvalidDateException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidDateException(){
		super();
	}
	
	public InvalidDateException(String s){
		super(s);
	}

}
