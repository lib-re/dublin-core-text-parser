package dc_metadata;

public class Publisher extends Element {

    //alternative, article, series
    public static final String NAMEPUBLICATION = "namePublication";


    private Publisher(){
        definition = "An entity responsible for making the resource available.";
        name = "publisher";
        label = "Publisher";
        uri = "http://purl.org/dc/elements/1.1/publisher";
    }

    public static Publisher createPublisher(String qualifierText, String value){
        Publisher a = new Publisher();
        a.value = value;
        a.qualifier = determineQualifier(qualifierText);
        return a;
    }

    private static String determineQualifier(String str) {
        str=str.toLowerCase();
        if(str.contains("name")){ return NAMEPUBLICATION; }
        else{ return ""; }
    }
}
