package dc_metadata;

/**
 * Class for a Title element
 */
public class Title extends Element {

    //refinements
    private static final String ALTERNATIVE = "alternative";
    private static final String ARTICLE     = "article";
    private static final String SERIES      = "series";


    /**
     * Create an empty title object populated with dublin core metadata standards info
     */
    private Title(){
        uri = "http://purl.org/dc/elements/1.1/title";
        name = "title";
        label = "Title";
        definition = "A name given to the resource.";
    }

    public static Title createTitle(String qualifierText, String value){
        Title t = new Title();
        t.value = value;
        t.qualifier = determineQualifier(qualifierText);
        return t;
    }

    private static String determineQualifier(String str) {
        str=str.toLowerCase();
        if(str.contains("alternative")){  return ALTERNATIVE; }
        else if(str.contains("article")){ return ARTICLE;     }
        else if(str.contains("series")){  return SERIES;      }
        else{                             return "";          }
    }

}
