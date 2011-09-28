import java.io.IOException;
import java.util.Arrays;
import org.w3c.dom.*;
import java.io.File;

import  org.w3c.dom.bootstrap.DOMImplementationRegistry;
import  org.w3c.dom.Document;
import  org.w3c.dom.ls.DOMImplementationLS;
import  org.w3c.dom.ls.LSParser;

public class ConfigProcessor {
	
	private static final String space_characters = "\u0020\u0009"; 
	private static final String[] dir_indicators = {"ltr","rtl","lro","rlo"}; 
	

	/**
	 * @param args 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws ClassCastException 
	 */
	public static void main(String[] args) throws ClassCastException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		ConfigProcessor cp = new ConfigProcessor(); 
		try {
			File file = new File("/Users/marcosc/github/widget-config-processor/tests/config.xml");
			cp.process(file);
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidWidgetPackageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void process(File file) throws DOMException, IOException, InvalidWidgetPackageException, ClassCastException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		
		//Let doc be the result of loading the widget config doc as a [DOM3Core] 
		//Document using an [XML] parser that is both [XMLNS]-aware and xml:lang aware.
		//If doc is not namespace well-formed [XML], 
		//then the user agent MUST terminate this algorithm and treat this widget 
		//package as an invalid widget package.
		Document document;
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		DOMImplementationLS impl = (DOMImplementationLS)registry.getDOMImplementation("LS");
		LSParser builder = impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
		document = builder.parseURI("tests/config.xml");
		
		//Let root element be the documentElement of doc.
		Element root = document.getDocumentElement(); 
		processWidgetElement(root); 
	
		NodeList nodes_i = document.getDocumentElement().getChildNodes();
		for (int i = 0; i < nodes_i.getLength(); i++) {
			
		}
		

	}
	
	/*
	 * This method is used to process widget elements
	 * @param e The wigdet element to be processed 
	 */
	public static void processWidgetElement(Element e) throws InvalidWidgetPackageException{
		//If the root element is not a widget element in the widget namespace, 
		if((e.getNamespaceURI() != "http://www.w3.org/ns/widgets") 
				|| (e.getLocalName() != "widget")) {
			   //then the user agent MUST terminate this algorithm and treat this widget package as an invalid widget package.
			String msg = "The root element is not a widget element in the widget namespace.";
			System.out.println(e.getNamespaceURI());
			System.out.println(e.getNodeName());
			throw new InvalidWidgetPackageException(msg);
		}
		//If the defaultlocale attribute is used, 
		if(e.hasAttribute("defaultlocale")){
			//then let default locale be the result of 
			//applying the rule for getting a single attribute value 
			//to the defaultlocale attribute:
			String defaultLocale = getSingleAttrValue(e.getAttributeNode("defaultlocale")); 
			
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
	public static String getSingleAttrValue(Attr attribute){
		String result; 
		
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
			result = value;  
		}else{
			//Otherwise, if and only if the attribute is a displayable-string attribute:
			
			//Let result be a localizable string that contains the value of value.
			result = value; 

			//Let element be the element that owns attribute.
			Element element = attribute.getOwnerElement(); 

			//Let direction be the result of applying the rule for determining directionality to element.
			String direction = getDirectionality(element);
			
			//Associate direction with result.

			//Let lang be the language tag derived from having processed the xml:lang attribute on either element, or in element's ancestor chain as per [XML]. If xml:lang was not used anywhere in the ancestor chain, then let lang be an empty string.

			//Associate lang with result.

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
				//return  "ltr" and terminate this algorithm. 
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
		String  direction = getSingleAttrValue(element.getAttributeNode("dir"));
		
		//If direction case-sensitively matches one of the valid directional indicators, return direction.
		if(Arrays.asList(dir_indicators).contains(direction)){
			return direction;
		}
		//If direction did not case-sensitively match one of the valid directional indicators, apply the rule for determining directionality to the direct parent element of element and return the result .		
		return direction;
	}

}


/*
 * The algorithm to process a configuration document is as follows:






If the default locale is in error or an empty string or already contained by the user agent locales list, then the user agent MUST ignore the defaultlocale attribute. 

If potential default locale is a valid language tag and the user agent locales does not contain the value of default locale, the user agent MUST prepend the value of potential default locale into the the user agent locales list as the second-last item (i.e., at position length - 1). 

For example, if the default locale is the value "fr", and the user agent locales contains the values "jp,us,*", then the user agent locales list becomes "jp,us,fr,*".

For example, if the default locale is the value "en", and the user agent locales only contains the value "*", then the user agent locales list becomes "en,*".

For example, if the default locale is the value "en", and the user agent locales already contains the values "en,*", then the user agent would ignore the default locale because it is already contained by the user agent locales list.

If the id attribute is used, then let id be the result of applying the rule for getting a single attribute value to the id attribute. If id is a valid IRI, then let widget id be the value of the id. If the id is in error, then the user agent MUST ignore the attribute.

If the version attribute is used, then let version value be the result of applying the rule for getting a single attribute value to the version attribute. If the version is an empty string, then the user agent MUST ignore the attribute; otherwise, let widget version be the value of version value.

If the height attribute is used, then let normalized height be the result of applying the rule for parsing a non-negative integer to the value of the attribute. If the normalized height is not in error and greater than 0, then let widget height be the value of normalized height. If the height attribute is in error, then the user agent MUST ignore the attribute.

If the width attribute is used, then let normalized width be the result of applying the rule for parsing a non-negative integer to the value of the attribute. If the normalized width is not in error and greater than 0, then let widget width be the value of normalized width. If the width attribute is in error, then the user agent MUST ignore the attribute.

If the viewmodes attribute is used, then the user agent MUST let viewmodes list be the result of applying the rule for getting a list of keywords from an attribute:

From the viewmode list, remove any unsupported items.

From the viewmode list, remove any duplicated items from right to left.

For example, viewmode list with a value of "windowed fullscreen windowed floating fullscreen windowed" would become "windowed fullscreen floating".

Let widget window modes be the value of viewmodes list.

If the widget element does not contain any child elements, then the user agent MUST terminate this algorithm and go to Step 8.

Otherwise, let element list be an empty list.

For each range in the user agent locales, starting from the first and moving to the last:

If the value of range is not "*", then retaining document order, let matching elements be the result of applying lookup to the child elements that are defined as being localizable via xml:lang that are direct descendents of the root element and whose xml:lang attribute matches the current range. Append matching elements to the element list.

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

A name element:
If this is not the first name element encountered by the user agent, then the user agent MUST ignore this element. 

If this is the first name element encountered by the user agent, then the user agent MUST:

Record that an attempt has been made by the user agent to process a name element.

Let widget name be the result of applying the rule for getting text content with normalized white space to this element.

If the short attribute is used, then let widget short name be the result of applying the rule for getting a single attribute value to the short attribute.

A description element:
If this is not the first description element encountered by the user agent, then the user agent MUST ignore this element.

If this is the first description element encountered by the user agent, then the user agent MUST: 

Record that an attempt has been made by the user agent to process a description element.

let widget description be the result of applying the rule for getting text content to this element.

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