package dc_metadata;


public class Identifier extends Element{

    private static String BIBLIO    = "bibliographiccitation";
    private static String CITATION  = "citation";

    private static String GOVDOC    = "govdoc";
    private static String ISBN      = "isbn";
    private static String ISBN10    = "isbn-10";
    private static String ISBN13    = "isbn-13";
    private static String ISSN      = "issn";
    private static String OTHER     = "other";
    private static String URI       = "uri";
    private static String URL       = "url";

    private static String ENCODING_URI = "uri";


    private Identifier(){
        uri="http://purl.org/dc/elements/1.1/identifier";
        name="identifier";
        label="Identifier";
        definition="An unambiguous reference to the resource within a given context.";
        //encoding="URI"
    }

    public static Identifier createIdentifier(String qualifierText, String value){
        Identifier a = new Identifier();
        a.value = value;
        a.qualifier = determineQualifier(qualifierText);
        return a;
    }

    private static String determineQualifier(String str) {
        str=str.toLowerCase();

        if(str.contains("citation")){
            return str.contains("biblio")? BIBLIO : CITATION;
        }else if(str.contains("govdoc")){ return GOVDOC; }
        else if(str.contains("isbn")){
            if(str.contains("10")){ return ISBN10; }
            else if(str.contains("13")){return ISBN13; }
            else{ return ISBN; }
        }else if(str.contains("issn")){ return ISSN; }
        else if(str.contains("uri")){ return URI; }
        else if(str.contains("url")){ return URL; }
        else{ return OTHER; }
    }

}
