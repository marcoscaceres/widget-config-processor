import java.util.ArrayList;

public class LocalizableString{
	public  String dir; 
	public  String lang; 
	private String value; 
	public boolean normalized = false; 
	
	public  ArrayList<LocalizableString> textNodes = new ArrayList<LocalizableString>(); 
	
	LocalizableString(String arg){
		this.value = arg; 
	}

	LocalizableString(){
		this.value = ""; 
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
	
	public void setTextNodes(ArrayList<LocalizableString> strings){
		this.textNodes.addAll(strings); 	
	}
	
	public void normalizeSpaces(){
		if(this.normalized == true){
			return;
		}
		this.value = removeSpaces(this.value);
		java.util.Iterator<LocalizableString> i =  this.textNodes.iterator(); 

		while(i.hasNext()){
			i.next().normalizeSpaces();
		}
		this.normalized = true; 
		
	}
	
	public static final String removeSpaces(String string){
		char spacechar = '\u0020'; 
		for(int i = 0; i < ConfigProcessor.unicode_space_chars.length; i++){
			char uncodespace = ConfigProcessor.unicode_space_chars[i]; 
			if(string.indexOf(uncodespace) > -1){
				string = string.replace(uncodespace, spacechar);
			} 
		}
		string = string.replaceAll(new String("\\s++"), new String("\u0020"));
		return string.trim();
	}
	
	public static void main(String[] args){
		LocalizableString s = new LocalizableString( new String(ConfigProcessor.unicode_space_chars) + "			te  st	" +
	new String(ConfigProcessor.unicode_space_chars) + "	te  st			" + new String(ConfigProcessor.unicode_space_chars) );
		s.textNodes.add(new LocalizableString("          ////         8888   " +  new String(ConfigProcessor.unicode_space_chars))); 
		s.normalizeSpaces(); 
		
	}
}
