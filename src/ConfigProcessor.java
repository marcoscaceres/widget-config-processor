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
	
	private class Feature{
		
		private class Option{
			private String name; 
			private String value; 
			
			public Option(String name, String value){
				this.name  = name; 
				this.value = value;  
			}
		}
		
		private ArrayList<Option> options;
		private String name; 
		
		public Feature(String name){
			this.name = name;
		}
		
		public vaid addOption(String name, String value){
			Option o = new Option(name, value);
		}
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
	 * A param element:
	 */
	public void processParamElement(){
		//If this param element is not a direct child of a feature element, 
		//then the user agent MUST ignore this param element.
		
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
	 * An icon element:
	 * 
	 */
	public void processIconElement(Element element){
		//If the src attribute of this icon element is absent, then the user agent MUST ignore this element.
		if(element.hasAttribute("src")){
			//Let path be the result of applying the rule for getting a single attribute value to 
			//the src attribute of this icon element. If path is not a valid path or is an empty 
			//string, then the user agent MUST ignore this element. 
			String path = getSingleAttrValue(element.getAttributeNode("src")); 
			
			//Let file be the result of applying the rule for finding a file within a widget package 
			//to path. 
			
			//If file is not a processable file, as determined by applying the rule for 
			//identifying the media type of a file, or already exists in the icons list, 
			//then the user agent MUST ignore this element. 
	
			//Otherwise,
	
			//If the height attribute is used, then let potential height be the result of applying the rule for parsing a non-negative integer to the attribute's value. If the potential height is not in error and greater than 0, then associate the potential height with file. Otherwise, the height attribute is in error and the user agent MUST ignore the attribute.
	
			//If the width attribute is used, then let potential width be the result of applying the rule for parsing a non-negative integer to the attribute's value. If the potential width is not in error and greater than 0, then associate the potential width with file. Otherwise, the width attribute is in error and the user agent MUST ignore the attribute.
	
			//Add file and any associated potential width and/or potential height to the list of icons.
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
	
	/*
	 * A feature element:
	 * The user agent MUST process a feature element in the following manner:
	 */
	private void processFeatureElement(Element e){
		// If the name attribute of this feature element is absent, 
		//then the user agent MUST ignore this element.
		if(e.hasAttribute("name")){
			boolean requiredFeature = true; 
			
			//If a required attribute is used, 
			if(e.hasAttribute("required")){
				//then let potentialy-required-feature be the 
				//result of applying the rule for getting 
				//a single attribute value to the required attribute. 
				String potentiallyRequiredFeature = getSingleAttrValue("required");
				
				//If the value of potentialy-required-feature is 
				//the value "false", 
				if(potentiallyRequiredFeature == "false"){
					//then let required-feature be the value 'false'. 
					requiredFeature = false; 
				}
			}

			//Let feature-name be the result of applying the rule for 
			//getting a single attribute value to the value of the name 
			//attribute.
			String featureName = getSingleAttrValue("name"); 
			//Note:
			//This specification allows feature elements with the same 
			//name attribute value to be declared more than once. 
			//Handling of duplicate feature requests is left up to 
			//the implementation or the specification that 
			//defines the feature.
			
			//If feature-name is not a valid IRI, and required-feature is 
			//true, then the user agent MUST treat this widget as 
			//an invalid widget package.
			try{
				URI featureName = new URI(featureName); 
			}catch(Exception exception){
				//If feature-name is not a valid IRI, 
				//and required-feature is false, 
				//then the user agent MUST ignore this element.
				return; 
			}
			
			//TODO: If feature-name is not supported by the user agent, 
			//and required-feature is true, then the user agent MUST treat 
			//this widget as an invalid widget package.
			if(UserAgent.isFeatureSupported(featureName)){
				
				
			} 
			//TODO: 	If feature-name is not supported by the user 
			//agent, and required-feature is false, then the user agent 
			//MUST ignore this element.
			//Associate the value of required-feature with feature-name.
			
			//If the feature element contains any param elements as direct descendants, then, for each child param element that is a direct descendent of this feature element, starting from the first moving to the last in document order:
			
			//If a value attribute is used, but the name attribute is absent, then this param element is in error and the user agent MUST ignore this element.
			
			//If a name attribute is used, but the value attribute is absent, then this param element is in error and the user agent MUST ignore this element.
			
			//Let param-name be the result of applying the rule for getting a single attribute value to the name attribute. If the param-name is an empty string, then this param element is in error and the user agent MUST ignore this element. 
			
			//If, and only if, param-name is not in error or an empty string, then let param-value be the result of applying the rule for getting a single attribute value to the value attribute.
			
			//Associate param-name and param-value with feature-name.
			
			//Append feature-name, and any associated required-feature, and associated parameters, to the feature list.


		}
	}
	
	
	/*
	 * Rule for Finding a File Within a Widget Package

The rule for finding a file within a widget package is given in the following algorithm. The algorithm returns either a processable file, null, or an error.

For the sake of comparison and matching, it is RECOMMENDED that a user agent treat all Zip relative paths as [UTF-8].

Note:
This specification does not define how links in documents other than the configuration document are to be dereferenced. For handling links in other documents, such as (X)HTML, CSS, SVG, etc., please refer to the [Widgets-URI] specification.

Let path be the path to the file entry being sought by the user agent.

If path is not a valid path, return an error and terminate this algorithm.

If the path starts with a U+002F SOLIDUS (e.g., "/style/master.css"), then remove the first U+002F SOLIDUS from path.

Let path-components be the result of splitting path at each occurrence of a U+002F SOLIDUS character, removing that U+002F SOLIDUS character in the process.

if the first item in path-components case-sensitively matches the string "locales", then:

If the path-components does not contain a second item, then return null.

If the second item in path-components is not a valid language-range, then return null and terminate this algorithm.

Otherwise, continue.

For each lang-range in the user agent locales:

Let path be the concatenation of the string "locales/", the lang-range, a U+002F SOLIDUS character, and the path (e.g., locales/en-us/cats.png, where "en-us" is the lang-range and "cats.png" is the path).

If path case-sensitively matches the file name field of a file entry within the widget package that is a folder, then return an error and terminate this algorithm.

If path case-sensitively matches the file name field of a file entry within the widget package that is a file, let file be the result of applying the rule for extracting file data from a file entry to path.

If file is a processable file, then return file and terminate this algorithm.

If the path points to a file entry that is not a processable file, then return an error and terminate this algorithm.

If every lang-range in the user agent locales have been searched, then search for a file entry whose file name field matches path from the root of the widget package:

If path points to a file entry within the widget package that is a folder, then return an error and terminate this algorithm.

If path points to a file entry within the widget package that is a file, let file be the result of applying the rule for extracting file data from a file entry to path.

If file is a processable file, then return file and terminate this algorithm.

If the path points to a file entry that is not a processable file, then return an error and terminate this algorithm.

Otherwise, return null.


	 */
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



An author element:
If this is not the first author element encountered by the user agent, then the user agent MUST ignore this element.

If this is the first author element used, then the user agent MUST: 

Record that an attempt has been made by the user agent to process a author element.

If the href attribute is used, then let href-value be the value of applying the rule for getting a single attribute value to the href attribute.

If href-value is a valid IRI, then let author href be the value of the href attribute. Otherwise, if href-value is not a valid IRI, then ignore the href attribute.

If the email attribute is used, then let author email be the result of applying the rule for getting a single attribute value to the email attribute.

Let author name be the result of applying the rule for getting text content with normalized white space to this element.



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

Note:
How a param element is to be processed when it is inside a feature element is defined below.


Any other type of element:
If the user agent supports the element, then the user agent MUST process it in accordance with whatever specification defines that element (if any). Otherwise, the user agent MUST ignore the element.
 * 
 * */