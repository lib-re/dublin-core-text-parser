package dc_metadata;

/**
 * Representation for a Contributor element.
 */
public class Contributor extends Element {

    //static parsing characters
    public static final String DELIM_NONNAME = "*";

    // - dc.contributor.* || contributor role codes - //
    public static String ACTOR             = "actor";
    public static String ADVISOR           = "advisor";
    public static String ADVISOR_CHAIR     = "advisorChair";
    public static String ADVISOR_DEPT_CHAIR= "advisorDeptChair";
    public static String ARTIST            = "artist";
    public static String AUTHOR            = "author";
    public static String DESIGNER          = "designer";
    public static String DIRECTOR          = "director";
    public static String EDITOR            = "editor";
    public static String EDITOR_ART        = "ArtEditor";
    public static String EDITOR_CAMPUS     = "CampusEditor";
    public static String EDITOR_COPY       = "CopyEditor";
    public static String EDITOR_DEPUTY_MAN = "DeputyManagingEditor";
    public static String EDITOR_EXECUTIVE  = "ExecutiveEditor";
    public static String EDITOR_FEATURE    = "FeatureEditor";
    public static String EDITOR_GRAPHIC_ART= "GraphicArtsEditor";
    public static String EDITOR_MANAGING   = "ManagingEditor";
    public static String EDITOR_NEWS       = "NewsEditor";
    public static String EDITOR_PHOTO      = "PhotographyEditor";
    public static String EDITOR_PHOTO_ASST = "AsstPhotographyEditor";
    public static String EDITOR_SPORTS     = "SportsEditor";
    public static String ILLUSTRATOR       = "illustrator";
    public static String MANAGER_ADVERTIS  = "AdvManager";
    public static String MANAGER_BUSINESS  = "BusinessManager";
    public static String MANAGER_PHOTO     = "ManagerOfPhotography";
    public static String ORGANIZATION      = "organization";
    public static String OTHER             = "other";
    public static String PHOTOGRAPHER      = "photographer";
    public static String PRODUCER          = "producer";
    public static String REPORTER          = "reporter";

    /**
     * Set the dublin core standard information common to all Contributor elements
     */
    private Contributor(){
        uri = "http://purl.org/dc/elements/1.1/contributor";
        name = "contributor";
        label = "Contributor";
        definition = "An entity responsible for making contributions to the resource.";
    }

    /**
     * Set the qualifier and value information given the inputs
     */
    private Contributor(String qualifier, String value){
        this();
        this.value = value;
        this.qualifier = qualifier;
    }


    /**
     * Return a contributor object whose qualifier has been selected with care.
     * @param qualifierText the raw text from the metadata file
     * @param fullName TODO switch to pass a 'Person' object
     */
    public static Contributor createContributor(String qualifierText, String fullName) {
        String qualifier = determineContributorQualifier(qualifierText);
        String value = determineName(fullName);

        //System.out.println("CONTRIBUTOR-created | qualifier: " + qualifier + ", value: " + value);
        //TODO replace with Logger

        return new Contributor(qualifier, value);
    }

    /**
     * return a processed name...
     * TODO figure out what all should or can be done at this stage to make this better formatted.
     */
    private static String determineName(String valueText) {

        return processName(valueText);
    }

    // - helpers - //

    /**
     * process a contributor line into a formatted line
     *  e.g. "fName mName mInitial lName" into "lName, fName mName mInitial lName"
     */
    private static String processName(String line){

        //prefix anything that's not a name with a
        if(line.startsWith(DELIM_NONNAME))
            return line.replace("*","");

        //split names
        String[] names = line.split(" ");
        if(names.length == 1){
            //single word entries should not be appended with anything.
            return line.trim();
        }else{
            String lName = names[names.length-1];
            String fName = line.substring(0, line.length() - lName.length() );

            return lName + ", " + fName;
        }
    }

    // - matching - //

    /** given the all caps line, return which Contributor.qualifier type it represents, using help functions */
    private static String determineContributorQualifier(String line) {

        String q = OTHER;

        //AUTHOR/WRITER
        if(matchAuthor(line)               ){ q = AUTHOR;           }

        //EDITOR
        else if (matchEditor(line)) {
            if( matchExecutive(line)       ){ q = EDITOR_EXECUTIVE; }
            else if( matchArt(line)        ){ q = EDITOR_ART;       }
            else if( matchFeature(line)    ){ q = EDITOR_FEATURE;   }
            else if( matchManaging(line)   ){ q = EDITOR_MANAGING;  }
            else if( matchCopy(line)       ){ q = EDITOR_COPY;      }
            else if( matchPhoto(line)      ){ q = matchAsst(line)? EDITOR_PHOTO_ASST : EDITOR_PHOTO;     }
            else if( matchSports(line)     ){ q = EDITOR_SPORTS;    }
            else if( matchNews(line)       ){ q = EDITOR_NEWS;      }
            else if( matchCampus(line)     ){ q = EDITOR_CAMPUS;    }
            else if( matchDeputy(line)     ){ q = EDITOR_DEPUTY_MAN;}
            else if( matchGraphic(line)    ){ q =EDITOR_GRAPHIC_ART;}
            else                            { q = EDITOR;           }
        }

        //ADVISOR, ARTIST, DESIGNER, ILLUSTRATOR, PHOTOGRAPHER, MANAGING(_BUSINESS)
        else if( matchAdvisor(line)){
             if( matchChair(line)){ q = matchDept(line)? ADVISOR_DEPT_CHAIR : ADVISOR_CHAIR;    }
             else{                            q = ADVISOR;          }
        }
        else if( matchActor(line)          ){ q = ACTOR;            }
        else if( matchArtist(line)         ){ q = ARTIST;           }
        else if( matchDesigner(line)       ){ q = DESIGNER;         }
        else if( matchDirector(line)       ){ q = DIRECTOR;         }
        else if( matchIllustrator(line)    ){ q = ILLUSTRATOR;      }
        else if( matchOrganization(line)   ){ q = ORGANIZATION;     }
        else if( matchPhotographer(line)   ){ q = PHOTOGRAPHER;     }
        else if( matchProducer(line)       ){ q = PRODUCER;         }
        else if( matchReporter(line)       ){ q = REPORTER;         }


        else if( matchManaging(line)){
             if( matchAdvertising(line)    ){ q = MANAGER_ADVERTIS; }
             if( matchBusiness(line)       ){ q = MANAGER_BUSINESS; }
             if( matchPhoto(line)          ){ q = MANAGER_PHOTO;    }
        }

        //OTHER
        else{ q = OTHER; }

        System.out.println("dc: " + line + " -> " + /*"dc.contributor." + */ q); //TODO convert to Logger

        return q;
    }



    /* - Editors - */

    /* EDITOR */
    public static boolean matchEditor(String str ){
        return str.contains("EDITOR");
    }

    /* EXECUTIVE */
    public static boolean matchExecutive( String str ){
        return str.contains("EXECUTIVE");
    }

    /* ART */
    public static boolean matchArt( String str ){
        return str.contains("ART");
    }

    /* COPY */
    public static boolean matchCopy( String str  ){
        return str.contains("COPY");
    }

    /* FEATURE */
    public static boolean matchFeature( String str){
        return str.contains("FEATURE");
    }

    /* SPORTS */
    public static boolean matchSports( String str ){
        return str.contains("SPORTS");
    }

    /* NEWS */
    public static boolean matchNews( String str ){
        return str.contains("NEWS");
    }

    /* PHOTO */
    public static boolean matchPhoto( String str ){
        return str.contains("PHOTO");
    }

    /* MANAGING */
    public static boolean matchManaging( String str ){
        return str.contains("MANAG");
    }


    /* - Other contributor qualifiers - */

    /* ADVISOR */
    public static boolean matchAdvisor( String str ){
        return str.contains("ADVISOR");
    }

    /* DESIGNER */
    public static boolean matchDesigner( String str ){
        return str.contains("DESIGNER");
    }

    /* ILLUSTRATOR */
    public static boolean matchIllustrator( String str ){
        return str.contains("ILLUSTRATOR");
    }

    /* PHOTOGRAPHER */
    public static boolean matchPhotographer( String str ){
        return str.contains("PHOTOGRAPHER");
    }

    /* ARTIST */
    public static boolean matchArtist( String str ){
        return str.contains("ARTIST");
    }

    /* BUSINESS */
    public static boolean matchBusiness( String str ){
        return str.contains("BUSINESS");
    }

    /* WRITER/AUTHOR */
    public static boolean matchAuthor( String str ){
        return str.contains("AUTHOR") || str.contains("WRITER");
    }

    /* ACTOR */
    private static boolean matchActor(String str) { return str.contains("ACTOR"); }

    /* Director */
    private static boolean matchDirector(String str) { return str.contains("DIRECTOR"); }

    /* ORGANIZATION */
    private static boolean matchOrganization(String str) { return str.contains("ORGANIZ"); }

    /* GRAPHIC */
    private static boolean matchGraphic(String str) { return str.contains("GRAPHIC"); }

    /* PRODUCER */
    private static boolean matchProducer(String str) { return str.contains("PRODUCE"); }

    /* ADVERTISING */
    public static boolean matchAdvertising(String str){ return str.contains("ADVERT"); }

    /* REPORTER */
    public static boolean matchReporter(String str){ return str.contains("REPORTER"); }

    /* .CHAIR */
    public static boolean matchChair(String str) { return str.contains("CHAIR"); }

    /* .DEPT */
    private static boolean matchDept(String str) { return str.contains("DEPT"); }

    /* .DEPUTY */
    private static boolean matchDeputy(String str) { return str.contains("DEPUTY"); }

    /* .CAMPUS */
    private static boolean matchCampus(String str) { return str.contains("CAMPUS"); }

    /* .ASST */
    private static boolean matchAsst(String str) {
        return str.contains("ASST") || str.contains("ASSIST");
    }

}
