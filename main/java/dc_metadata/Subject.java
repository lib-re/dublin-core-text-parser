package dc_metadata;

public class Subject extends Element {

    //alternative, article, series
    public static final String LCSH     = "LCSH";   //library of congress
    public static final String MESH     = "MeSH";
    public static final String DDC      = "DCC";
    public static final String UDC      = "UDC";
    public static final String LCC      = "lcc";
    public static final String KEYWORD  = "keyword";
    public static final String LCNAF    = "lcnaf";
    public static final String OTHER    = "other";

    private Subject(){
        definition = "The topic of the resource.";
        name = "subject";
        label = "Subject";
        uri = "http://purl.org/dc/elements/1.1/subject";
    }

    public static Subject createSubject(String encodingText, String value){
        Subject a = new Subject();
        a.value = value;
        a.qualifier = determineEncoding(encodingText);
        return a;
    }

    private static String determineEncoding(String str) {
        str=str.toLowerCase();
        if(str.isEmpty()){ return ""; }
        else if(str.contains("lcsh")){  return LCSH;    }
        else if(str.contains("mesh")){  return MESH;    }
        else if(str.contains("ddc")){   return DDC;     }
        else if(str.contains("lcc")){   return LCC;     }
        else if(str.contains("udc")){   return UDC;     }
        else if(str.contains("lcnaf")){ return LCNAF;   }
        else if(str.contains("keyword") ||
                str.contains("tag")){   return KEYWORD; }
        else{ return OTHER; }
    }

}
