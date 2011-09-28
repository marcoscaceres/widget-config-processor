public class LocalizableString{
	public String dir; 
	public String lang; 
	private String value; 
	
	LocalizableString(String arg){
		value = arg; 
	}
	
	public String toString(){
		return value; 
	}
	
	public void setDir(String dir){
		
		this.dir = dir; 
	}	
	
	public void setLang(String lang){
		this.lang = lang; 

	}
}
