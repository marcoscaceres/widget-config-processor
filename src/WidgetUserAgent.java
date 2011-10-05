import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


/*
 * A Widget User Agent that conforms to the W3C Widget packaging and configuration specification.
 * 
 * A user agent is an implementation of this specification that also supports [XML], [XMLNS], 
 * [UTF-8], [Unicode], [DOM3CORE], [SNIFF], and [ZIP] (see optional aspects of the Zip specification).
 * 
 * In this implementation, the implementations above are:
 * 		[XML]      - Xerces http://xerces.apache.org/xerces-j/
 *      [DOM3CORE] - Xerces http://xerces.apache.org/xerces-j/
 * 		[Unicode]  - Java 7+. 
 * 		[UTF-8]    - Java 7+.
 * 		[ZIP]      - Truezip http://truezip.java.net/
 * 		[SNIFF]    - Custom 
 */
public class WidgetUserAgent{
	private static final String space_characters = "\u000C\u000B\u0020\u0009\n\r";
	private List<Locale> locales = Collections.synchronizedList(new ArrayList<Locale>()); 
	private Widget[] widgets; 
	
	private List<String> supportedViewmodes = new ArrayList<String>(); 
	
	private ConfigProcessor processor = new ConfigProcessor(); 
	
	public WidgetUserAgent(){
		deriveUserAgentLocale(); 
	}
	
	/*
	 * The purpose of processing the configuration document is to override the values in the table 
	 * of configuration defaults (ConfigurationDefaults.java), which are used during 
	 * initialization and at runtime, and to select the appropriate localized content (if any) to 
	 * be presented to the end user.
	 */
	private void processConfigurationDocument(){
		
	}
	
	
	/*
	 * 
	 */
	public ArrayList<Locale> getLocales(){
		ArrayList<Locale> localesCopy = new ArrayList<Locale>();
		Collections.copy(localesCopy, locales);
		return localesCopy; 
	}
	
	public boolean isViewModeSupported(String view){
		return supportedViewmodes.contains(view);
	}
	
	
	public static void main(String[] args){
		WidgetUserAgent wrt = new WidgetUserAgent(); 
		
	}
	
	/*
	 * 
	 */
	public void acquireZipArchive(){}
	
	
	public void verifyZipArchive(){}
	
	
	/*The end-user's language ranges represents the end-user's preferred languages and regional 
	 * settings, which are derived from the operating system or directly from the user agent. 
	 * 
	 * During the rule for deriving the user agent locales defined below, the user agent will need 
	 * to construct a list unprocessed locales. Each item in the unprocessed locales is a string in
	 * lowercase form, that conforms to the production of a Language-Tag, as defined in the 
	 * [BCP47] specification. A string that conforms to the production of a Language-Tag is 
	 * referred to as a language range [BCP47] (e.g. 'en-au', which is the range of 
	 * English as spoken in Australia, and 'fr-ca', which is the range of French as 
	 * spoken in Canada, etc.). A language range is composed of one or more subtags that are 
	 * delimited by a U+002D HYPHEN-MINUS ("-").
	 * 
	 * The first item of the unprocessed locales represents the user's most preferred language 
	 * range (i.e., the language/region combination the user agent assumes the end-user most wants 
	 * to see content in), followed by the next most preferred language range, and so forth.
	 * 
	 * For example, in an unprocessed locales list that contains 'en-us,en,fr,es', 
	 * English as spoken in the United States is preferred over English, and English is preferred 
	 * over French, and French is preferred over Spanish, and Spanish is preferred over default
	 * content.
	 * 
	 * For example, the end-user may have specified her preferred languages and regional settings 
	 * at install time by selecting a preferred language, or languages from a list, or a list of 
	 * preferred languages and regional settings could have been dynamically derived from the 
	 * end-user's geographical location, etc.
	 * 
	 * In Step 5, the user agent MUST apply the rule for deriving the user agent locales.
	 * 
	 * The rule for deriving the user agent locales is as follows:
*/
	private void deriveUserAgentLocale(){
		//Let unprocessed locales list be a list that contains the end-user's language ranges.
		Locale[] available = Locale.getAvailableLocales(); 
		List<Locale> unprocessedLocales = new ArrayList<Locale>(available.length+1);
		unprocessedLocales.add(Locale.getDefault());
		List<Locale> availLocales = Arrays.asList(available); 
		unprocessedLocales.addAll(availLocales);
		
		//For each range in the unprocessed locales list:
		Iterator<Locale> i = unprocessedLocales.iterator(); 
		while(i.hasNext()){
			Locale next = (Locale) i.next(); 
			if(next == null){
				continue; 
			}
			String[] range = (next).toLanguageTag().split("\u002D"); 
			//If this range begins with the subtag '*' (e.g. "*-us" or just "*"), 
			//TODO: or contains any space characters
			String subtag = range[0];
			if ((subtag == "*" )){
				 //skip all the steps in this algorithm below, and move onto the next range.
				break;
			}
			
			//If this range begins with the subtag "i" 
			//TODO: or the range is marked as "deprecated" in the IANA Language Subtag Registry, 
			//skip all the steps in this algorithm below, and move onto the next range.
			if ((subtag != "i") ){
				List<String> subtagList = (List<String>) Arrays.asList(range);
				//If this range contains any subtag '*', remove the '*' 
				//and its preceding hyphen (U+002F) (e.g., 'en-*-us' becomes 'en-us').
				while(subtagList.contains("*")){
					subtagList.remove(subtagList.indexOf("*")); 
				} 	
				
				List<String> ranges = new ArrayList<String>(); 
				Collections.addAll(ranges, range); 
				int size = ranges.size();
				while(!ranges.isEmpty()){
					//Add the value of the range to the user agent locales.
					//String cleanRange = arrayToString((String[]) myList.toArray(), "-"); 
					String[] langtag = Arrays.copyOf(ranges.toArray(), ranges.toArray().length, String[].class);
					String cleanRange = arrayToString(langtag, "\u002D");
					Locale parsedLocale = Locale.forLanguageTag(cleanRange); 
					locales.add(parsedLocale);
					
					//Remove the right most subtag from range and append the resulting value to user agent locales. 
					//Continue removing the right most subtag and adding the result to user agent locales until there are no subtags left in range.
					ranges.remove(--size); 
					//Move onto the next range and go to step 1 in this algorithm.			
				}
			}
		}
		//Append the value "*" to the end of user agent locales.
		locales.add(Locale.forLanguageTag("*")); 
	}
	
	private static String arrayToString(String[] a, String separator) {
	    StringBuffer result = new StringBuffer();
	    if (a.length > 0) {
	        result.append(a[0]);
	        for (int i=1; i<a.length; i++) {
	            result.append(separator);
	            result.append(a[i]);
	        }
	    }
	    return result.toString();
	}
}
