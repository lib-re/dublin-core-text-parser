import dc_metadata.Contributor;
import dc_metadata.Type;

import java.io.File;

/**
 * Parser object which interprets a given text file and creates an interpreted 'Item' object
 */
public class Parser {

    public static int num_header_lines = 5;

    //modes
    private enum mode { CONTRIBUTORS, ARTICLES };
    private mode currentMode = mode.CONTRIBUTORS;

    //qualifier
    private Contributor.qualifier currentQualifier = Contributor.qualifier.AUTHOR;

    //item to be added
    private Item item = new Item(0);

    /**
     * Process a given file
     * @param file
     * @return
     */
    public int process(File file){

        int i = 0;
        String line = "".trim();

        //foreach line in the file:

            //process header according to set rules (see processHeaderLine)
            if(i>=0 && i<num_header_lines){
                processHeaderLine(item, line, i);
            }

            //catch any commented lines and add them as notes
            if(line.startsWith("//")){
                item.addNote(line);
            }

            //change mode according to tags
            if(line.startsWith("-") && line.endsWith("-")){

                //switch to adding contributors
                if(line.contains("CONTRIBUTOR") || line.contains("COLLABORATOR")){
                    this.currentMode = mode.CONTRIBUTORS;
                }

                //switch to adding articles
                if(line.contains("ARTICLE")){
                    this.currentMode = mode.ARTICLES;
                }

            }

            // - process the body - //

            //if current mode is adding articles, just add the line
            if(currentMode == mode.ARTICLES){
                item.addArticle(line);
            }

            //if current mode is adding contributors...
            else if(currentMode == mode.CONTRIBUTORS) {

                //if we read an all caps field, switch contributor type
                if(line.toUpperCase() == line){
                    this.currentQualifier = determineContributorQualifier(line);

                //add appropriate contributor qualifier type
                }else{

                    //change "fName mName lName" -> "lName, fName mName"
                    String[] names = line.split(" ");
                    String lName = names[names.length-1];
                    String fName = line.substring(0, line.length() - lName.length() - 1);
                    String name = lName + ", " + fName;

                    //add contributor with the current qualifer.
                    item.addContributor(this.currentQualifier, name);
                }

            }

        return 1;
    }

    /** pull the first n lines of the header file and process according */
    private static int processHeaderLine(Item item, String line, int num){

        /* HEADER
         * 0: title.alternative         |   'Reporter'
         * 1: relation.isPartOfSeries   |   'Volume 73, Number 18"
         * 2: date.issued               |   '1993-03-26'
         * 3: title.alternative         |   'Mr. All American'
         * 4: filename                  |   'RITReporter1993_Vol73No18.pdf'
         */

        switch(num){
            case 0:
                item.addAlternativeTitle(line);
                break;
            case 1:
                String volume = line.split(",")[0];
                String number = line.split(",")[1];
                item.addSeries(volume);
                item.addSeries(number);
                break;
            case 2:
                item.addDateIssued(line);
                break;
            case 3:
                item.addAlternativeTitle(line);
                break;
            case 4:
                item.addFilename(line);
                break;
        }

        return 1;
    }

    /** process any fields that are shared for all items */
    private int processShared(){
        item.addTitle("Reporter");
        item.addPublisher("Rochester Institute of Technology");
        item.addType(Type.value.MAGAZINE);

        return 1;
    }

    // - match functions - //

    /** given the all caps line, return which Contributor.qualifier type it represents, using help functions */
    private static Contributor.qualifier determineContributorQualifier(String line) {

        //AUTHOR/WRITER
        if(matchAuthor(line)               ){ return Contributor.qualifier.AUTHOR;           }

        //EDITOR
        else if (matchEditor(line)) {
            if( matchExecutive(line)       ){ return Contributor.qualifier.EDITOR_EXECUTIVE; }
            else if( matchArt(line)        ){ return Contributor.qualifier.EDITOR_ART;       }
            else if( matchFeature(line)    ){ return Contributor.qualifier.EDITOR_FEATURE;   }
            else if( matchManaging(line)   ){ return Contributor.qualifier.EDITOR_MANAGING;  }
            else if( matchCopy(line)       ){ return Contributor.qualifier.EDITOR_COPY;      }
            else if( matchPhoto(line)      ){ return Contributor.qualifier.EDITOR_PHOTO;     }
            else if( matchSports(line)     ){ return Contributor.qualifier.EDITOR_SPORTS;    }
            else if( matchNews(line)       ){ return Contributor.qualifier.EDITOR_NEWS;      }
            else                            { return Contributor.qualifier.EDITOR;           }
        }

        //ADVISOR, ARTIST, DESIGNER, ILLUSTRATOR, PHOTOGRAPHER, MANAGING(_BUSINESS)
        else if( matchAdvisor(line)        ){ return Contributor.qualifier.ADVISOR;          }
        else if( matchArtist(line)         ){ return Contributor.qualifier.ARTIST;           }
        else if( matchDesigner(line)       ){ return Contributor.qualifier.DESIGNER;         }
        else if( matchIllustrator(line)    ){ return Contributor.qualifier.ILLUSTRATOR;      }
        else if( matchPhoto(line)          ){ return Contributor.qualifier.PHOTOGRAPHER;     }
        else if( matchManaging(line)){
            return matchBusiness(line)? Contributor.qualifier.MANAGER_BUSINESS : Contributor.qualifier.MANAGER;
        }

        //OTHER
        else{ return Contributor.qualifier.OTHER; }
    }


    /* - Editors - */

    /* EDITOR */
    private static boolean matchEditor( String str ){
        return str.contains("EDITOR");
    }

    /* EXECUTIVE */
    private static boolean matchExecutive( String str ){
        return str.contains("EXECUTIVE");
    }

    /* ART */
    private static boolean matchArt( String str ){
        return str.contains("ART");
    }

    /* COPY */
    private static boolean matchCopy( String str  ){
        return str.contains("COPY");
    }

    /* FEATURE */
    private static boolean matchFeature( String str){
        return str.contains("FEATURE");
    }

    /* SPORTS */
    private static boolean matchSports( String str ){
        return str.contains("SPORTS");
    }

    /* NEWS */
    private static boolean matchNews( String str ){
        return str.contains("NEWS");
    }

    /* PHOTO */
    private static boolean matchPhoto( String str ){
        return str.contains("PHOTO");
    }

    /* MANAGING */
    private static boolean matchManaging( String str ){
        return str.contains("MANAG");
    }


    /* - Other contributor qualifiers - */

    /* ADVISOR */
    private static boolean matchAdvisor( String str ){
        return str.contains("ADVISOR");
    }

    /* DESIGNER */
    private static boolean matchDesigner( String str ){
        return str.contains("DESIGNER");
    }

    /* ILLUSTRATOR */
    private static boolean matchIllustrator( String str ){
        return str.contains("ILLUSTRATOR");
    }

    /* PHOTOGRAPHER */
    private static boolean matchPhotographer( String str ){
        return str.contains("PHOTOGRAPHER");
    }

    /* ARTIST */
    private static boolean matchArtist( String str ){
        return str.contains("ARTIST");
    }

    /* BUSINESS */
    private static boolean matchBusiness( String str ){
        return str.contains("BUSINESS");
    }

    /* WRITER/AUTHOR */
    private static boolean matchAuthor( String str ){
        return str.contains("AUTHOR") || str.contains("WRITER");
    }


}
