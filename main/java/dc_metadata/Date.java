package dc_metadata;

public class Date extends Element{

    private static String CREATED       = "created";
    private static String VALID         = "valid";
    private static String AVAILABLE     = "available";
    private static String ISSUED        = "issued";
    private static String MODIFIED      = "modified";
    private static String ACCEPTED      = "dateaccepted";
    private static String COPYRIGHTED   = "copyrighted";
    private static String SUBMITTED     = "submitted";

    private static String ACCESSIONED   = "accessioned";
    private static String DEFENSE       = "defense";
    private static String DIGITIZED     = "digitized";


    private Date(){
        uri="http://purl.org/dc/elements/1.1/date";
        name="date";
        label="Date";
        encoding="W3C-DTF";
        definition="A point or period of time associated with an event in the lifecycle of the resource.";
    }

    public static Date createDate(String qualifierText, String value){
        Date a = new Date();
        a.value = value;
        a.qualifier = determineQualifier(qualifierText);

        return a;
    }

    private static String determineQualifier(String str){

        str=str.toLowerCase().replace("_","");

        if      (str.contains("create"))    {   return CREATED;     }
        else if (str.contains("valid"))     {   return VALID;       }
        else if (str.contains("availab"))   {   return AVAILABLE;   }
        else if (str.contains("issue"))     {   return ISSUED;      }
        else if (str.contains("modifi"))    {   return MODIFIED;    }
        else if (str.contains("accept"))    {   return ACCEPTED;    }
        else if (str.contains("submit"))    {   return SUBMITTED;   }
        else if (str.contains("copy")
              && str.contains("right"))     {   return COPYRIGHTED; }
        else if (str.contains("access"))    {   return ACCESSIONED; }
        else if (str.contains("defense"))   {   return DEFENSE;     }
        else if (str.contains("digit"))     {   return DIGITIZED;   }
        else { return ""; }
    }

}
