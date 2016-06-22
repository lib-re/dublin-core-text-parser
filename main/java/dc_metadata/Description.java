package dc_metadata;

/**
 * Representation of the dc.description element with qualifiers
 */
public class Description extends Element {

    private static String ABSTRACT          = "abstract";
    private static String TABLEOFCONTENTS   = "tableofcontents";

    private static String APPROVAL          = "approval";
    private static String AUDIENCE          = "audience";
    private static String AUTHOR            = "author";
    private static String CAMPUS            = "campus";
    private static String COLLEGE           = "college";
    private static String DEFENSE           = "defense";
    private static String DEPARTMENT        = "department";
    private static String EXTENT            = "extent";
    private static String NOTE              = "note";
    private static String OTHER             = "other";
    private static String PROGRAM           = "program";
    private static String PROVENANCE        = "provenance";
    private static String SCHOOL            = "school";
    private static String SPONSORSHIP       = "sponsorship";
    private static String URI               = "uri";
    private static String STATEMENTOFRESPONSIBILITY = "statementOfResponsibility";


    private Description(){
        uri = "http://purl.org/dc/elements/1.1/description";
        name = "description";
        label = "Description";
        definition = "An account of the resource.";
    }

    private Description(String qualifier, String value){
        this();
        this.qualifier = qualifier;
        this.value = value;
    }

    public static Description createDescription(String qualifierText, String description){
        Description a = new Description();
        a.value = description;
        a.qualifier = determineQualifier(qualifierText);

        return a;
    }

    private static String determineQualifier(String str){

        str=str.toLowerCase().replace("_","");

        if(str.isEmpty())                       {   return "";              }
        else if (str.contains("abstract"))      {   return ABSTRACT;        }
        else if (str.contains("table")
            &&   str.contains("contents"))      {   return TABLEOFCONTENTS; }
        else if (str.contains("approval"))      {   return APPROVAL;        }
        else if (str.contains("audience"))      {   return AUDIENCE;        }
        else if (str.contains("author"))        {   return AUTHOR;          }
        else if (str.contains("campus"))        {   return CAMPUS;          }
        else if (str.contains("college"))       {   return COLLEGE;         }
        else if (str.contains("statement")
              && str.contains("responsibility")){   return STATEMENTOFRESPONSIBILITY; }
        else if (str.contains("department"))    {   return DEPARTMENT;      }
        else if (str.contains("defense"))       {   return DEFENSE;         }
        else if (str.contains("note"))          {   return NOTE;            }
        else if (str.contains("program"))       {   return PROGRAM;         }
        else if (str.contains("provenance"))    {   return PROVENANCE;      }
        else if (str.contains("school"))        {   return SCHOOL;          }
        else if (str.contains("sponsorship"))   {   return SPONSORSHIP;     }
        else if (str.contains("uri"))           {   return URI;             }
        else                                    {   return OTHER;           }
    }

}
