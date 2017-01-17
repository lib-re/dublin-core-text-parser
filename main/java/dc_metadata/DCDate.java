package dc_metadata;

import java.util.Date;

public class DCDate extends Element{

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

    // todo: update value into java.util.Date object.
    // unique values
    // private Date value;

    private DCDate(){
        uri="http://purl.org/dc/elements/1.1/date";
        name="date";
        label="DCDate";
        encoding="W3C-DTF";
        definition="A point or period of time associated with an event in the lifecycle of the resource.";
    }

    public static DCDate createDate(String qualifierText, String value){

        DCDate a = new DCDate();

        // todo: implement smart Date creation based on encoding
        // if(encoding == null)
        //    encoding = "yyyy-mm-dd";
        // a.encoding = encoding;

        a.value = /*new Date(*/ value;
        a.qualifier = determineQualifier(qualifierText);

        return a;
    }

    private static String determineQualifier(String str){

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
