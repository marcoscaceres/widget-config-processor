public class InvalidWidgetPackageException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -413787614349522803L;
	public String message; 

	public InvalidWidgetPackageException(String issue){
		super(); 
		message = issue; 
	}
	
}