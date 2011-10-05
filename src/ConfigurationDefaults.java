import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

import org.w3c.dom.Document;

public class ConfigurationDefaults {
	//The file that is the configuration document for the widget package.
	private Document configdoc;  
	
	//The value of the name element's short attribute in the configuration document (if any).
	private LocalizableString widgetshortname;  
	
	//The value of the widget element's version attribute in the configuration document (if any).
	private LocalizableString widgetversion;
	
	//The text content of the name element in the configuration document (if any).
	private LocalizableString widgetName = new LocalizableString(""); 
	
	//The value of the author element's email attribute (if any).
	private String authorEmail = "";
	
	//The text content of the author element (if any).
	private String authorName  = ""; 
	
	//The text content of the description element in the configuration document.
	private String description = "";  
	
	//The value of the author element's href attribute (if any).
	private URI authorhref;          
	
	//A list of features that correspond to features that were requested via 
	//feature elements (if any). Each item in the list corresponds to a feature
	//element's name attribute, whether it is required, and any associated 
	//parameters (if any).
	private HashMap<URI,Feature> features;
	
	////The icons of the widget as they correspond to the default icons and to
	//the occurrence of custom icons that are supported by the widget package 
	//(if any).
	private HashMap<String,File> icons;  
	
	 //The value of the widget element's height attribute in the configuration 
	//document (if any).
	private int height;	
	
	// The value of the widget element's width attribute in the configuration 
	//document (if any).
	private int width;	 
	
	//The value of the widget element's id attribute in the configuration document (if any).
	private URI widgetid; 
	
	//The text content of the license element in the configuration document (if any).
	private String licensetxt = "";
	//A file derived if the value of the license element's href is a Zip relative path 
	//to a file within the widget package.
	private File   licenseFile;	 
	//The value of the license element's href attribute in the configuration document (if any).
	private URI    licenseHref;	 
	
	//The widget's preferences, corresponding to the preference elements in the configuration 
	//document (if any).Unless an end-user explicitly requests that these values be reverted to the 
	//values as declared in the configuration document, a user agent MUST NOT reset the value of 
	//the widget preferences variable on subsequent initializations of the widget.
	private HashMap<String, String> preferences; 

	//The value of the widget element's viewmodes attribute in the configuration document (if any).
	private String[] windowmodes;
	
	//The start file for the widget package, corresponding to either one of the default start files
	//table or the file identified by the content element's src attribute.
	private StartFile startfile; 
	
	//A list of language tags.
	private String[] UAlocales; 
	
	
	
}
