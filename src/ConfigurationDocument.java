import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSParser;

/*
 * A configuration document is an [XML] document that has a widget element at its root that is
 * in the widget namespace. A widget package has exactly one configuration document 
 * located at the root of the widget package.
 */
public class ConfigurationDocument {	
	private Document doc; 
	private static ConfigProcessor cp = new ConfigProcessor(); 
	LSParser parser;

	public ConfigurationDocument(String path) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException{
		//Let doc be the result of loading the widget config doc as a [DOM3Core] 
		//Document using an [XML] parser that is both [XMLNS]-aware and xml:lang aware.
		//If doc is not namespace well-formed [XML], 
		//then the user agent MUST terminate this algorithm and treat this widget 
		//package as an invalid widget package.	
		parser = makeParser(); 
		doc = parser.parseURI(path);
	}
	
	private static LSParser makeParser () throws ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException{
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		DOMImplementationLS impl = (DOMImplementationLS)registry.getDOMImplementation("LS");
		return impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
	}
	
	
	public void process(ArrayList<Locale> locales) throws ClassCastException, ClassNotFoundException, InstantiationException, IllegalAccessException, DOMException, IOException, InvalidWidgetPackageException{
		ConfigurationDefaults defaults = cp.process(doc,locales);
	}
	
	
	public static void main(String[] args) throws DOMException, IOException, InvalidWidgetPackageException{
		WidgetUserAgent wrt = new WidgetUserAgent(); 
		
		try {
			ConfigurationDocument cd = new ConfigurationDocument("tests/config.xml");
			cd.process(wrt.getLocales());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
