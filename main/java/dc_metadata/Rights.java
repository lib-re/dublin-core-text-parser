package dc_metadata;

public class Rights extends Element {

    //alternative, article, series
    public static final String ACCESSRIGHTS = "accessrights";
    public static final String LICENCSE     = "license";
    public static final String AVAILABILITY = "availability";
    public static final String URI          = "uri";


    private Rights(){
        definition = "Information about rights held in and over the resource.";
        name = "rights";
        label = "Rights";
        uri = "http://purl.org/dc/elements/1.1/publisher";
        //encoding = "uri";
    }

    public static Rights createRights(String qualifierText, String value){
        Rights a = new Rights();
        a.value = value;
        a.qualifier = determineQualifier(qualifierText);
        return a;
    }

    private static String determineQualifier(String str) {
        str=str.toLowerCase();
        if(str.contains("access")){ return ACCESSRIGHTS; }
        else if(str.contains("license")){ return LICENCSE; }
        else if(str.contains("availa")){ return AVAILABILITY; }
        else if(str.contains("uri")){ return URI; }
        else{ return ""; }
    }

}
