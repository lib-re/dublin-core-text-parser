package dc_metadata;

/**
 * Created by admin on 6/22/16.
 */
public class Language extends Element {

    //alternative, article, series
    public static final String ISO = "iso";

    public static final String ENC_ISO_6392 = "ISO_639-2RFC_3066";

    private Language(){
        uri = "http://purl.org/dc/elements/1.1/title";
        name = "title";
        label = "Title";
        definition = "A name given to the resource.";
        //encoding = ENC_ISO_6392;
    }

    public static Language createLanguage(String qualifierText, String value){
        Language a = new Language();
        a.value = value;
        a.qualifier = determineQualifier(qualifierText);
        return a;
    }

    private static String determineQualifier(String str) {
        str=str.toLowerCase();
        if(str.contains("iso")){ return ISO; }
        else{ return ""; }
    }
}
