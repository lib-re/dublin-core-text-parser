package dc_metadata;

public class Audience extends Element{

    private static String EDUCATIONLEVEL = "educationLevel";
    private static String MEDIATOR       = "mediator";

    private Audience(){
        uri="http://dublincore.org/documents/usageguide/elements.shtml#audience";
        name="audience";
        label="Audience";
        definition="A class of entity for whom the resource is intended or useful.";
    }

    public static Audience createAudience(String qualifierText, String value){
        Audience a = new Audience();
        a.value = value;
        a.qualifier = determineQualifier(qualifierText);

        return a;
    }

    private static String determineQualifier(String str){
        str=str.toLowerCase();
        if(str.contains("mediat")){ return MEDIATOR; }
        else if(str.contains("education") && str.contains("level")){ return EDUCATIONLEVEL; }
        else { return ""; }
    }


}
