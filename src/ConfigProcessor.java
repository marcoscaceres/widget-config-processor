import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.w3c.dom.*;
import java.net.URI;
import java.net.URISyntaxException;

import  org.w3c.dom.Document;
import java.util.logging.Logger; 
import java.util.Locale;
import java.util.Iterator; 

import se.fishtank.css.selectors.*;
import se.fishtank.css.selectors.dom.DOMNodeSelector;
import java.util.LinkedHashSet;
import java.util.HashSet;


public class ConfigProcessor {
	public static void main(String[] args){
		String[] test = {"a", "c", "a", "d", "a"}; 
		List<String> a = Arrays.asList(test);
		//WidgetUserAgent wrt = new WidgetUserAgent(); 
		Set<String> s = new LinkedHashSet<String>(a);
		String[] y = s.toArray(new String[0]);

		
	}
	
	//@link http://dev.w3.org/2006/waf/widgets/#space-characters
	private static final String space_characters = "\u000C\u000B\u0020\u0009\n\r";
	
	//The Unicode white space characters are code points marked in the [Unicode] 
	//specification with the property "White_Space", including 
	//(but not limited to - see [Unicode] for the authoritive list):
	//@link http://dev.w3.org/2006/waf/widgets/#unicode-white-space-characters
	public static final char[] unicode_space_chars = (space_characters + 
													  "\u0085\u00A0\u1680\u180E\u2000\u2001\u2002" + 
													  "\u2003\u2004\u2005\u2006\u2007\u2008" +
													  "\u2009\u200A\u2028\u2029\u202F\u205F\u3000").toCharArray(); 

	private static final String number_characters = "\u0030\u0031\u0032\u0033\u0034\u0035" +
												    "\u0036\u0037\u0038\u0039";
	
	private static final String[] dir_indicators = {"ltr","rtl","lro","rlo"}; 
	
 
	
	private ConfigurationDefaults defaults = new ConfigurationDefaults(); 
	private Logger logger =  Logger.getLogger("ConfigParser");
	private Set<String> processedElements = new HashSet<String>();
	
	public ConfigProcessor(){
		
	}
	
	public void setConfigDefault(String name, Object value){
		
		
	}

	public ConfigurationDefaults process(Document document, ArrayList<Locale> UAlocales) throws DOMException, IOException, 
		InvalidWidgetPackageException, ClassCastException, ClassNotFoundException, 
		InstantiationException, IllegalAccessException{
		
		//Set up configuration defaults
		ConfigurationDefaults defaults = new ConfigurationDefaults(); 
		
		//Let root element be the documentElement of doc.
		Element root = document.getDocumentElement(); 
		processWidgetElement(root); 
		
		
		NodeList nodes = document.getDocumentElement().getChildNodes();
		//If the widget element does not contain any child elements, 
		//then the user agent MUST terminate this algorithm and go to Step 8.
		if(nodes.getLength() < 0){
			//we are going to do lookup using XPath (god help us!)

			
			//Otherwise, let element list be an empty list.
			List<Element> elementList = new ArrayList<Element>(); 
			
			//For each range in the user agent locales, starting from the first and moving to the last:
			Iterator<Locale> i = UAlocales.iterator(); 	
			if (i.hasNext() == true) {
				Locale range = i.next();
				//If the value of range is not "*", then retaining document order, 
				if(range.toLanguageTag() != "*"){
					//let matching elements be the result of applying lookup to the child elements 
					//that are defined as being localizable via xml:lang that are direct descendents 
					//of the root element and whose xml:lang attribute matches the current range. 
					//(name, description, license), 
		
					
					//Append matching elements to the element list.
				}
				/*
				 * 


Note:
In the context of this specification, the above conformance requirement is intended to match the name, description, and license elements. However, it is written in an abstract manner to provide a hook for future specifications that want to define elements that also support being localizable via xml:lang.

If the value of range is "*", retaining document order, let unlocalized elements be all child elements that are direct descendents of the root element that do not have an implicit or explicit xml:lang attribute (i.e., match default content). Append unlocalized elements to the element list.
For example, consider the following configuration document.

<widget xmlns="http://www.w3.org/ns/widgets">
<name>El Widget!</name>
<name xml:lang="fr">Le Widget</name>
<name xml:lang="en">The Widget</name>
</widget> 
For a use agent whose user agent locales contains "en,fr,*", the matching elements would be in the following order:

<name xml:lang="en">The Widget</name>
<name xml:lang="fr">Le Widget</name>
<name>El Widget!</name>
For a use agent whose user agent locales contains "en,*", the matching elements would be in the following order:

<name xml:lang="en">The Widget</name>
<name>El Widget!</name>
For a use agent whose user agent locales contains "jp,*", the matching elements would be in the following order:

<name>El Widget!</name>
For each element in the elements list, if the element is one of the following:
				 */
			}
		}
		return defaults; 
	}
	
	
	private void processDefaultLocale(Attr attr){
		//If the defaultlocale attribute is used, 
		//then let default locale be the result of 
		//applying the rule for getting a single attribute value 
		//to the defaultlocale attribute:
		String languageTag = getSingleAttrValue(attr).toString();
		Locale defaultLocale = Locale.forLanguageTag(languageTag); 
			
		//TODO: If potential default locale is a valid language tag and the user agent locales does 
		//not contain the value of default locale, the user agent MUST prepend the value of potential 
		//default locale into the the user agent locales list as the second-last item (i.e., at position length - 1). 
	}
		

	private void processWidgetId(Attr attr){
		//If the id attribute is used, 
		//then let id be the result of applying the rule for getting a single 
		//attribute value to the id attribute. 
		String id = getSingleAttrValue(attr).toString();  
		
		try{
			//TODO: If id is a valid IRI, then let widget id be the value of the id.
			URI uri = new URI(id); 
			
		}catch(URISyntaxException urie){
			//If the id is in error,
			//then the user agent MUST ignore the attribute.
			logger.warning("The id attribute was not a valid URI. It was ignored by the parser.");
		}
		
	}
	
	private void processWidgetVersion(Attr attr){
		//If the version attribute is used, then let version value be the result of applying the 
		//rule for getting a single attribute value to the version attribute. 
		
		String versionValue = getSingleAttrValue(attr).toString();
		
		//If the version is an
		//empty string, then the user agent MUST ignore the attribute; 
		if(versionValue.length() > 0){
			//TODO: otherwise, let widget version be the value of version value.
			
		}
	}
	
	
	private void processWidgetHeight(Attr attr){
		//If the height attribute is used, then let normalized height be the result of applying the
		//rule for parsing a non-negative integer to the value of the attribute.
		try{
			int normalHeight = parseNonNegInteger(attr.getValue());
			//If the normalized height is not in error and greater than 0, then let widget height 
			//be the value of normalized height.
			if(normalHeight > 0){
				//TODO: then let widget width be the value of normalized width. 
			}
		}catch(Exception e){
			//If the height attribute is in error, then the user agent MUST ignore the attribute.
			logger.warning("The height attribute was in error and was not processed. Its value is '"
					+ attr.getValue() + "'");
		}
	}
	
	private void processWidgetWidth(Attr attr){
		//If the width attribute is used, then let normalized width be the result of applying the 
		//rule for parsing a non-negative integer to the value of the attribute. 
		try{
			int normalWidth = parseNonNegInteger(attr.getValue());
			//If the normalized width is not in error and greater than 0, 
			if(normalWidth > 0){
				//TODO: then let widget width be the value of normalized width. 
			}
		}catch(Exception e){
			//If the width attribute is in error, then the user agent MUST ignore the attribute.
			logger.warning("The width attribute was in error and was not processed. Its value is '"
					+ attr.getValue() + "'");
		}
	}
	
	/*
	 * 
	 */
	private void processWidgetViewmodes(Attr attr){
		//If the viewmodes attribute is used, then the user agent MUST let viewmodes list be 
		//the result of applying the rule for getting a list of keywords from an attribute:
		ArrayList<String> viewmodes = getKeywordList(attr); 
		
		//From the viewmode list, remove any unsupported items.
		//TODO: Define supported view mdoes 
		
		//From the viewmode list, remove any duplicated items from right to left.
		Set<String> cleanSet = new LinkedHashSet<String>(viewmodes);
		
		//Let widget window modes be the value of viewmodes list.
		//TODO: ConfigurationDefaults.setViewmodes =  Arrays.asList(cleanSet).toString();
	}
	
	
	/*
	 * This method is used to process widget elements
	 * @param e The widget element to be processed 
	 */
	public void processWidgetElement(Element e) throws InvalidWidgetPackageException{
		//If the root element is not a widget element in the widget namespace, 
		if((e.getNamespaceURI() != "http://www.w3.org/ns/widgets") 
				|| (e.getLocalName() != "widget")) {
			   //then the user agent MUST terminate this algorithm and treat this widget package as an invalid widget package.
			String msg = "The root element is not a widget element in the widget namespace.";
			System.out.println(e.getNamespaceURI());
			System.out.println(e.getNodeName());
			throw new InvalidWidgetPackageException(msg);
		}
		
		//Itearate the attributes of the widget element, and process each one as needed. 
		//We ignore the ones we don't know
		NamedNodeMap map = e.getAttributes(); 
		for (int i = 0; i < map.getLength(); i++) {
			Attr attr = (Attr) map.item(i); 
			switch(attr.getName()){
				case "defaultlocale":
					processDefaultLocale(attr); 
					break;
				case "id":
					processWidgetId(attr); 
					break;					
				case "version": 
					processWidgetVersion(attr);
					break; 
				case "viewmodes": 
					processWidgetViewmodes(attr);
					break;
				case "width": 
					processWidgetWidth(attr);
					break;
				case "height": 
					processWidgetHeight(attr);
					break;

			}
		}
		
	}
	
	/*
	 * checks if an attribute is a "displayable attribute" 
	 * @param attr the attribute to be checked.
	 */
	public static boolean isDisplayStringAttribute(Attr attr){
		String name = attr.getLocalName(); 
		if((name == "short"   && attr.getOwnerElement().getLocalName() == "name") ||
		   (name == "version" && attr.getOwnerElement().getLocalName() == "widget")){
			return true;
		}
		return false; 
	}
	
	/*
	 * @param attribute the attribute to be processed.
	 */
	public static LocalizableString getSingleAttrValue(Attr attribute){
		LocalizableString result; 
		//Let value be the value of the attribute to be processed.
		String value = attribute.getValue();

		//In value, replace any sequences of space characters (in any order)
		//with a single U+0020 SPACE character.
		CharSequence replacement= "\u0020";
		value.replace(space_characters, replacement);
		
		//In value, remove any leading or trailing U+0020 SPACE characters.
		value = value.trim(); 

		//If the attribute is not a displayable-string attribute, 
		if(isDisplayStringAttribute(attribute) == false){
			//then let result be a string that contains the value of value.
			result = new LocalizableString(value);  
		}else{
			//Otherwise, if and only if the attribute is a displayable-string attribute:
			
			//Let result be a localizable string that contains the value of value.
			result = new LocalizableString(value); 

			//Let element be the element that owns attribute.
			Element element = attribute.getOwnerElement(); 

			//Let direction be the result of applying the rule for determining directionality to element.
			String direction = getDirectionality(element);
			
			//Associate direction with result.
			result.setDir(direction);
			
			//Let lang be the language tag derived from having processed the xml:lang attribute on either element, 
			//or in element's ancestor chain as per [XML]. If xml:lang was not used anywhere in the ancestor chain, then let lang be an empty string.
			String lang = getXMLLang(element);
			
			//Associate lang with result.
			result.setLang(lang);
			//Return result.
		}
		return result; 
	}
	
	
	/*
	 * The rule for determining directionality is given in the following algorithm. 
	 * The algorithm always returns one of the valid directional indicators as a string.
	 * @param e the element whose directionality will be determined. 
	 */
	private static String getDirectionality(Element element){
		//If element is the root element of the configuration document:
		if(element.getOwnerDocument().getDocumentElement() == element){	
			//If it does not contain a dir attribute, return "ltr" and terminate this algorithm.
			if(element.hasAttribute("dir") == false){
				return "ltr"; 
			}else{
				//If it does contain a dir attribute, 
				//let value the the value of the dir attribute of element. 
				String value = element.getAttribute("dir");
				
				//Remove any leading or trailing U+0020 SPACE characters from value. 
				value = value.trim();
				//If value of the attribute case-sensitively matches one of the valid directional
				//indicators,
				if(Arrays.asList(dir_indicators).contains(value)){
					//return the value of the attribute and terminate this algorithm.
					return value; 
				}
				//if the value does not case-sensitively match one of the valid directional indicators, 
				//return "ltr" and terminate this algorithm. 
				return "ltr"; 	
			}
		}
		//If element does not contain a dir attribute, 
		if(element.hasAttribute("dir") == false){
			//recursively apply rule for determining directionality to the direct 
			//parent element of element and return the result.
			return getDirectionality( (Element) element.getParentNode()); 
		}
		
		//If element contains a dir attribute, let direction be the result of 
		//applying the rule for getting a single attribute value to the dir attribute of element:
		String  direction = getSingleAttrValue(element.getAttributeNode("dir")).toString();
		
		//If direction case-sensitively matches one of the valid directional indicators, return direction.
		if(Arrays.asList(dir_indicators).contains(direction)){
			return direction;
		}
		//If direction did not case-sensitively match one of the valid directional indicators, apply the rule for determining directionality to the direct parent element of element and return the result .		
		return direction;
	}

	/*
	 * Method used to work out the language of an Element
	 */
	public static String getXMLLang(Element element){
		  String xmlns = "http://www.w3.org/XML/1998/namespace";
		  String value = element.getAttributeNS(xmlns,"lang");
		  //check if we are at the root
		  if(element == element.getOwnerDocument().getDocumentElement()){
		      //no xml:lang?
		      if(!element.hasAttributeNS(xmlns,"lang")){
		          return "";
		       }
		       //we have it, so return it.
		       return value;
		   }

		   //this is an element in the tree
		   if(!element.hasAttributeNS(xmlns,"lang")){
		       //no xml:lang? recurse upwards
			    return getXMLLang((Element) element.getParentNode());
		   }
		  //we have a value, so return it
		   return value;
		}
	
	/*
	 * The rule for getting a list of keywords from an attribute is given by the following algorithm. 
	 * The algorithm takes a string as input, and returns a list of strings which can be empty.
	 */
	public static ArrayList<String> getKeywordList(Attr attribute) {
		//Let result be the value of the attribute to be processed.
		LocalizableString result  = new LocalizableString(attribute.getValue()); 
		
		//In result, replace any sequences of unicode space characters (in any order) with a single U+0020 SPACE character.
		//In result, remove any leading or trailing U+0020 SPACE character.
		result.normalizeSpaces(); 
		String newResult = result.toString(); 
		
		//In result, split the string at each occurrence of a U+0020 character, removing that U+0020 character in the process.
		String[] splitResult = newResult.split("\\s"); 
		//Return result.
		return (ArrayList<String>) Arrays.asList(splitResult); 
	}	
	
	/*
	 * The rule for parsing a non-negative integer is given in the following algorithm. 
	 * This algorithm returns the number zero, a positive integer, or an error.
	 * @param input the string being parsed.
	 */

	public static int parseNonNegInteger(String input) throws Exception{
		//Let result have the value 0.
		int result = 0; 
		
		//If the length of input is 0, return an error.
		if(input.length() == 0){
			throw new Exception("The number had no length"); 
		}
		
		//Let position be a pointer into input, initially pointing at first character of the string.
		for(int i = 0; i < input.length(); i++ ){
			//Let nextchar be the character in input at position.
			CharSequence nextchar =  Character.toString(input.charAt(i)); 
			//If the nextchar is one of the space characters, increment position. If position is past the end of input, return an error and terminate this algorithm. Otherwise, go to step 5 in this algorithm.
			if(space_characters.contains(nextchar)){
				continue; 
			}
			//If the nextchar is not one of U+0030 (0) .. U+0039 (9), then return result.
			//If the nextchar is one of U+0030 (0) .. U+0039 (9):
			if(number_characters.contains(nextchar) == true){
				//Multiply result by ten.
				result = result * 10; 
				//Add the value of the nextchar to result.
				result = Integer.valueOf((String)nextchar);
				//Increment position.
				//If position is not past the end of input, go to 5 in this algorithm.
			}
		
		}
		//Return result.
		return result; 
	}
	
	/* 
	 * A name element:
	 */
	public void processNameElement(Element element){
			 //If this is not the first name element encountered by the user agent, 
			 //then the user agent MUST ignore this element.
		 if(processedElements.contains("name") == false){
			
			 // If this is the first name element encountered by the user agent, then the user agent MUST:	
			 //Record that an attempt has been made by the user agent to process a name element.
			 processedElements.add("name"); 
			
			 //Let widget name be the result of applying the rule for getting text
			 //content with normalized white space to this element.
			
			 LocalizableString widgetName =  getTextContentWithNormalizedWhiteSpace(element);
			 
			 
			 //If the short attribute is used, 
			 NamedNodeMap map = element.getAttributes(); 
			 for (int i = 0; i < map.getLength(); i++) {
					Attr attr = (Attr) map.item(i); 
					switch(attr.getName()){
						case "short":
							processWidgetShortName(attr); 
							break;
					}
				}
			 
	
		 }
	}
	
	/*
	 * A description element:
	 */
	public void processDescriptionElement(Element element){
		//If this is not the first description element encountered by the user agent, then the user agent MUST ignore this element.
		if(processedElements.contains("description") == false){
			//If this is the first description element encountered by the user agent, then the user agent MUST: 
			//Record that an attempt has been made by the user agent to process a description element.
			processedElements.add("description");
			//let widget description be the result of applying the rule for getting text content to this element.
			LocalizableString description = getTextContent(element); 
			
			//TODO: Add to Configuration defaults
		}
	}
	
	
	private void processWidgetShortName(Attr attr){
		 //let widget short name be the result of applying the rule for getting a single attribute value 
		 //to the short attribute.
		LocalizableString shortName = getSingleAttrValue(attr); 
		//TODO: set configuration default
	}
	
	/*
	 * The rule for getting text content with normalized white space 
	 * is given in the following algorithm. The algorithm always returns 
	 * a localizableString, which can be empty.
	 * @param input The Element to be processed.
	 */
	private static LocalizableString getTextContentWithNormalizedWhiteSpace(Element input){
		//Let result be the result of applying the rule for getting text content to input.
		LocalizableString result = getTextContent(input); 
		
		//In result, convert any sequence of one or more Unicode white space characters into a single U+0020 SPACE.
		//In result, remove any leading or trailing U+0020 SPACE character.
		result.normalizeSpaces(); 
		
		//Return result.
		return result; 
	}
	
	
	/*
	* The rule for getting text content is given in the following algorithm. 
	* The algorithm always returns a list of localizable strings, which can be empty.
	* @param input the Element to be processed.
	*/
	private static LocalizableString getTextContent(Element input){
		//Let bidi-text be an empty localizable string
		LocalizableString bidiText = new LocalizableString(""); 
		
		//If input has no child nodes, return an bidi-text and terminate this algorithm.
		if(input.hasChildNodes()){
			//Let langStrings be an empty list (it will hold localizable strings)  
			ArrayList<LocalizableString> langStrings = new ArrayList<LocalizableString>();  
			
			//Let lang be the language tag derived from having processed the xml:lang 
			//attribute on either input, or in input's ancestor chain as per [XML].
			//If xml:lang was not used anywhere in the ancestor chain, then let lang be an empty 
			//string.
			String lang = getXMLLang(input); 
			
			//Let direction be the result of applying the rule for determining 
			//directionality to input.
			String direction = getDirectionality(input); 
			
			//Associate lang and direction with bidi-text.
			//bidiText.setDir(direction);
			//bidiText.setLang(lang);
			
			//For each child node of input: 
			NodeList nodes = input.getChildNodes(); 
			for(int i = 0; i < nodes.getLength(); i++){
				Node currentNode = nodes.item(i); 
				LocalizableString lstring; 
 				//If the current child node is an Element,
				if(currentNode.getNodeType() == Node.ELEMENT_NODE){
					//let lstring be the result of recursively applying the rule for 
					//getting text content using this current child element as the argument.
					lstring = getTextContent((Element) currentNode); 
				}else					//If the current child node is a text node,
					if(currentNode.getNodeType() == Node.TEXT_NODE){
					  //Then create a new localizable string called lstring, 
					  //using the text content of this node as the value,
					  //direction as the direction, and lang as the language
					  lstring = new LocalizableString(currentNode.getTextContent());
					  lstring.setDir(direction);
					  lstring.setLang(lang);
				}else{
					continue; 
				}
				//append lstring to langString list
				langStrings.add(lstring);
			}
			bidiText.setTextNodes(langStrings); 
			
		}	
		//return bidi-text.
		return bidiText; 
	}
}





/*
 *
A license element:
If this is not the first license element encountered by the user agent, then the user agent MUST ignore this element.

If this is the first license element used, then the user agent MUST:

Record that an attempt has been made by the user agent to process a license element.

Let license text be the result of applying the rule for getting text content to this element. Associate license text with widget license.

If the href attribute is used, then let potential license href be the result of applying the rule for getting a single attribute value to the href attribute.

If potential license href is not a valid IRI or a valid path, then the href attribute is in error and the user agent MUST ignore the attribute.

If potential license href is a valid IRI, then let widget license href be the value of potential license href.

If license href is a valid path, then let file be the result of applying the rule for finding a file within a widget package to license href.

If file is not a processable file, as determined by applying the rule for identifying the media type of a file, then ignore this element.

Otherwise, let widget license file be the value of file.

An icon element:
If the src attribute of this icon element is absent, then the user agent MUST ignore this element.

Let path be the result of applying the rule for getting a single attribute value to the src attribute of this icon element. If path is not a valid path or is an empty string, then the user agent MUST ignore this element. 

Let file be the result of applying the rule for finding a file within a widget package to path. If file is not a processable file, as determined by applying the rule for identifying the media type of a file, or already exists in the icons list, then the user agent MUST ignore this element. 

Otherwise,

If the height attribute is used, then let potential height be the result of applying the rule for parsing a non-negative integer to the attribute's value. If the potential height is not in error and greater than 0, then associate the potential height with file. Otherwise, the height attribute is in error and the user agent MUST ignore the attribute.

If the width attribute is used, then let potential width be the result of applying the rule for parsing a non-negative integer to the attribute's value. If the potential width is not in error and greater than 0, then associate the potential width with file. Otherwise, the width attribute is in error and the user agent MUST ignore the attribute.

Add file and any associated potential width and/or potential height to the list of icons.

An author element:
If this is not the first author element encountered by the user agent, then the user agent MUST ignore this element.

If this is the first author element used, then the user agent MUST: 

Record that an attempt has been made by the user agent to process a author element.

If the href attribute is used, then let href-value be the value of applying the rule for getting a single attribute value to the href attribute.

If href-value is a valid IRI, then let author href be the value of the href attribute. Otherwise, if href-value is not a valid IRI, then ignore the href attribute.

If the email attribute is used, then let author email be the result of applying the rule for getting a single attribute value to the email attribute.

Let author name be the result of applying the rule for getting text content with normalized white space to this element.

A preference element:
If a value attribute of the preference element is used, but the name attribute is absent, then this preference element is in error and the user agent MUST ignore this element. Otherwise, the user agent MUST: 

Let name be the result of applying the rule for getting a single attribute value to the name attribute.

If the name is an empty string, then this element is in error; ignore this element.

If widget preferences already contains a preference whose name case-sensitively matches the value of name, then this element is in error; ignore this element.

If name was not in error, let preference be an empty object.

Associate name with preference.

Let value be the result of applying the rule for getting a single attribute value to the value attribute.

Associate value with preference.

If a readonly attribute is used, then let readonly be the result of applying the rule for getting a single attribute value to the readonly attribute. If readonly is not a valid boolean value, then let the value of readonly be the value 'false'.

Associate readonly with the preference.

Add the preference and the associated name, value and readonly variables the list of widget preferences.

A content element:
If this is not the first content element encountered by the user agent, then the user agent MUST ignore this element.

If this is the first content element, then the user agent MUST:

Record that an attempt has been made by the user agent to process a content element.

If the src attribute of the content element is absent or an empty string, then the user agent MUST ignore this element.

Let path be the result of applying the rule for getting a single attribute value to the value of the src attribute.

If path is not a valid path, then the user agent MUST ignore this element.

If path is a valid path, then let file be the result of applying the rule for finding a file within a widget package to path. If file is null or in error, then the user agent MUST ignore this element.

If the type attribute of the content element is absent, then check if file is supported by the user agent by applying the rule for identifying the media type of a file. If the file is supported, then let the widget start file be the file referenced by the src attribute and let start file content-type be the supported media type as was derived by applying the rule for identifying the media type of a file.

If the encoding attribute is used, then let content-encoding be the result of applying the rule for getting a single attribute value to the value of the encoding attribute. If the character encoding represented by the value of content-encoding is supported by the user agent, then let the start file encoding be the value of content-encoding. If content-encoding is an empty string or unsupported by the user agent, then a user agent MUST ignore the encoding attribute.

If the type attribute is used, then let content-type be the result of applying the rule for getting a single attribute value to the value of the type attribute. If the value of content-type is a media type supported by the user agent, then let the start file content-type be the value of content type. If value of content-type is invalid or unsupported by the user agent, then a user agent MUST treat the widget package as an invalid widget package. 

If the start file encoding was set in step 7 of this algorithm as a result of processing a valid and supported value for the content element's encoding attribute, then the user agent MUST skip this step in this algorithm. Otherwise, if the value of content-type is a media type supported by the user agent and if content-type contains one or more [MIME] parameter components whose purpose is to declare the character encoding of the start file (e.g., the value "text/html;charset=Windows-1252", where charset is a parameter component whose purpose is to declare the character encoding of the start file), then let start file encoding be the value of the last supported parameter components whose purpose is to declare the character encoding of the start file. 

In the following example, the user agent would set the start file encoding to ISO-8859-1 and would ignore the charset parameter used in the type attribute.

<widget xmlns="http://www.w3.org/ns/widgets"> 
  <content src      = "start.php" 
           type     = "text/html;charset=Windows-1252" 
           encoding = "ISO-8859-1" />
</widget>
In the following example the user agent would set the start file encoding to Windows-1252, if the user agent supports that character encoding.

<widget xmlns="http://www.w3.org/ns/widgets"> 
  <content src  = "start.php" 
           type = "text/html;charset=Windows-1252"/>
</widget>
A param element:
If this param element is not a direct child of a feature element, then the user agent MUST ignore this param element.

Note:
How a param element is to be processed when it is inside a feature element is defined below.

A feature element:
The user agent MUST process a feature element in the following manner: 

If the name attribute of this feature element is absent, then the user agent MUST ignore this element.

Let feature-name be the result of applying the rule for getting a single attribute value to the value of the name attribute.

Note:
This specification allows feature elements with the same name attribute value to be declared more than once. Handling of duplicate feature requests is left up to the implementation or the specification that defines the feature.

If a required attribute is used, then let required-feature be the result of applying the rule for getting a single attribute value to the required attribute. If the required attribute is not used or if required-feature is not a valid boolean value, then let the value of required-feature be the value 'true'.

If feature-name is not a valid IRI, and required-feature is true, then the user agent MUST treat this widget as an invalid widget package. 

If feature-name is not a valid IRI, and required-feature is false, then the user agent MUST ignore this element. 

If feature-name is not supported by the user agent, and required-feature is true, then the user agent MUST treat this widget as an invalid widget package.

If feature-name is not supported by the user agent, and required-feature is false, then the user agent MUST ignore this element.

Associate the value of required-feature with feature-name.

If the feature element contains any param elements as direct descendants, then, for each child param element that is a direct descendent of this feature element, starting from the first moving to the last in document order:

If a value attribute is used, but the name attribute is absent, then this param element is in error and the user agent MUST ignore this element.

If a name attribute is used, but the value attribute is absent, then this param element is in error and the user agent MUST ignore this element.

Let param-name be the result of applying the rule for getting a single attribute value to the name attribute. If the param-name is an empty string, then this param element is in error and the user agent MUST ignore this element. 

If, and only if, param-name is not in error or an empty string, then let param-value be the result of applying the rule for getting a single attribute value to the value attribute.

Associate param-name and param-value with feature-name.

Append feature-name, and any associated required-feature, and associated parameters, to the feature list.

Any other type of element:
If the user agent supports the element, then the user agent MUST process it in accordance with whatever specification defines that element (if any). Otherwise, the user agent MUST ignore the element.
 * 
 * */