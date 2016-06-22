package dc_metadata;

public class Coverage extends Element{

    private static String SPATIAL   = "spatial";
    private static String TEMPORAL  = "temporal";

    private static String EVENT     = "event";
    private static String OTHER     = "other";
    private static String PERIOD    = "period";
    private static String PERIODNAME= "periodName";
    private static String PLACE     = "place";
    private static String PLACENAME = "placeName";


    private Coverage(){
        uri="http://purl.org/dc/elements/1.1/coverage";
        name="coverage";
        label="Coverage";
        definition="The spatial or temporal topic of the resource, the spatial applicability of the resource, or the " +
                "jurisdiction under which the resource is relevant.";
    }

    public static Coverage createCoverage(String qualifierText, String value){
        Coverage a = new Coverage();
        a.value = value;
        a.qualifier = determineQualifier(qualifierText);

        return a;
    }

    private static String determineQualifier(String str){

        str=str.toLowerCase();

        if(str.isEmpty())                   {   return "";       }
        if      (str.contains("spatial"))   {   return SPATIAL;  }
        else if (str.contains("temporal"))  {   return TEMPORAL; }
        else if (str.contains("event"))     {   return EVENT;    }
        else if (str.contains("other"))     {   return OTHER;    }
        else if (str.contains("period")){
            return str.contains("name")? PERIOD : PERIODNAME;
        }else if (str.contains("place")){
            return str.contains("name")? PLACE : PLACENAME;
        }else { return OTHER; }
    }

}
