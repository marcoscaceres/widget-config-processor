import java.util.Locale;

/*
 * 
Lookup is used to select the single language tag that best matches
   the language priority list for a given request.  When performing
   lookup, each language range in the language priority list is
   considered in turn, according to priority.  By contrast with
   filtering, each language range represents the most specific tag that
   is an acceptable match.  The first matching tag found, according to
   the user's priority, is considered the closest match and is the item
   returned.  For example, if the language range is "de-ch", a lookup
   operation can produce content with the tags "de" or "de-CH" but never
   content with the tag "de-CH-1996".  If no language tag matches the
   request, the "default" value is returned.

   For example, if an application inserts some dynamic content into a
   document, returning an empty string if there is no exact match is not
   an option.  Instead, the application "falls back" until it finds a
   matching language tag associated with a suitable piece of content to
   insert.  Some applications of lookup include:

   o  Selection of a template containing the text for an automated email
      response.

   o  Selection of an item containing some text for inclusion in a
      particular Web page.

   o  Selection of a string of text for inclusion in an error log.

   o  Selection of an audio file to play as a prompt in a phone system.

   In the lookup scheme, the language range is progressively truncated
   from the end until a matching language tag is located.  Single letter
   or digit subtags (including both the letter 'x', which introduces
   private-use sequences, and the subtags that introduce extensions) are
   removed at the same time as their closest trailing subtag.  For
   example, starting with the range "zh-Hant-CN-x-private1-private2"
   (Chinese, Traditional script, China, two private-use tags) the lookup
   progressively searches for content as shown below:

   Example of a Lookup Fallback Pattern

   Range to match: zh-Hant-CN-x-private1-private2
   1. zh-Hant-CN-x-private1-private2
   2. zh-Hant-CN-x-private1
   3. zh-Hant-CN
   4. zh-Hant
   5. zh
   6. (default)

 */
public class Lookup {

	public static void main(String args[]){
		Locale l = Locale.getDefault(); 
	
	}

}

