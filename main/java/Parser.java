import dc_metadata.Contributor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Parser object which interprets a given text file and creates an interpreted 'Item' object
 */
public class Parser {

    //delimiters
    private static final String DELIM_MATCHSWITCH = "-";
    private static final String DELIM_NOTE = "//";
    //private static String SPLIT_HEADER = ",";
    //private static String SPLIT_HEADER_TITLE = "|";

    //options (elements)
    public static String AUDIENCE       = "AUDIENCE";
    public static String COVERAGE       = "COVERAGE";
    public static String DATE           = "DATE";
    public static String DESCRIPTION    = "DESCRIPTION";
    public static String FILENAME       = "FILENAME";
    public static String FORMAT         = "FORMAT";
    public static String IDENTIFIER     = "IDENTIFIER";
    public static String LANGUAGE       = "LANGUAGE";
    public static String NOTE           = "NOTE";
    public static String PUBLISHER      = "PUBLISHER";
    public static String RELATION       = "RELATION";
    public static String RIGHTS         = "RIGHTS";
    public static String RIGHTSHOLDER   = "RIGHTSHOLDER";
    public static String SUBJECT        = "SUBJECT";
    public static String TITLE          = "TITLE";
    public static String TYPE           = "TYPE";
    private static String[] ALL_OPTIONS =
            { AUDIENCE, COVERAGE, DATE, DESCRIPTION, FILENAME, FORMAT, IDENTIFIER, LANGUAGE,
                NOTE, PUBLISHER, RELATION, RIGHTS, RIGHTSHOLDER, SUBJECT, TITLE, TYPE };

    //modes
    private enum mode { CONTRIBUTORS, ARTICLES }
    private mode currentMode = mode.CONTRIBUTORS;

    //qualifier
    private String currentQualifier = Contributor.AUTHOR;

    //critical elements
    ArrayList<String> lsHeaderOptions;
    ArrayList<String[]> lsShared;
    ArrayList<Item> lsItems;
    Item current_item;


    // - constructors - //

    /**
     * base constructor which initializes object state and sets defaults
     */
    public Parser(){

        //initialize header options
        lsHeaderOptions = new ArrayList<String>();

        /* set to default header:
         * 0: title                     |   'Title of the Issue'
         * 1: relation.isPartOfSeries   |   'Volume ##, Number ##, "
         * 2: date.issued               |   '1993-03-26'
         * 3: title.alternative         |   ''
         * 4: filename                  |   'filename.pdf'
         */
        lsHeaderOptions.add(TITLE);
        lsHeaderOptions.add(RELATION + "_" + "ISPARTOFSERIES");
        lsHeaderOptions.add(DATE + "_" + "ISSUED");
        lsHeaderOptions.add(TITLE + "_" + "ALTERNATIVE");
        lsHeaderOptions.add(FILENAME);

        //initialize list of shared values
        lsShared = new ArrayList<String[]>();

        //initialize list of items
        lsItems = new ArrayList<Item>();
    }


    // - methods - //

    /**
     * check validity of the list of header lines, clear, replace
     * @param lsHeaderLines the list of perspective 'options' (from config file)
     * @return whether or not the header configuration was accurately set.
     */
    public boolean setHeaderOptions(String[] lsHeaderLines){

        //return false if the options listed are invalid
        if(!isValidOptions(Arrays.asList(lsHeaderLines)))
            return false;

        //clear the list of existing strings
        this.lsHeaderOptions.clear();

        //add, in order, the options passed in.
        return Collections.addAll(lsHeaderOptions, lsHeaderLines);

    }

    public boolean setShared(List<String[]> shared){

        //iterate through list, adding
        ArrayList<String> lsSharedOptions = new ArrayList<String>();
        for(String[] line : shared){ //for each line in the
            lsSharedOptions.add(line[0]);
            this.lsShared.add(line);
        }

        //validate the set of shared values. if not validated, clear
        if(!isValidOptions(lsSharedOptions)){
            this.lsShared.clear();
            return false;
        }

        //already added
        return true;
    }

    /** Process a single metadata file */
    public int processMetadataFile(File file, int i){

        //item to be added
        current_item = new Item(i);

        //foreach line in the file:
            String line = "".trim();

            //process header according to set rules
            if(i>=0 && i<this.lsHeaderOptions.size()){
                current_item.addElementType(lsHeaderOptions.get(i), line);
            }

            //catch any commented lines and add them as notes
            if(matchComment(line)){
                current_item.addNote(line);
            }

            //change mode according to tags
            if(matchModeSwitchString(line)){

                //switch to adding contributors
                if(matchContributor(line)){ this.currentMode = mode.CONTRIBUTORS; }

                //switch to adding articles
                if(matchTableOfContents(line)){ this.currentMode = mode.ARTICLES; }

            }

            // - process the body - //

            //if current mode is adding articles, just add the line
            if(currentMode == mode.ARTICLES){
                current_item.addArticle(line);
            }

            //if current mode is adding contributors...
            else if(currentMode == mode.CONTRIBUTORS) {

                //if we read an all caps field, switch contributor type
                if(isAllCaps(line)){
                    this.currentQualifier = Contributor.determineContributorQualifier(line);

                //add appropriate contributor qualifier type
                }else{
                    current_item.addContributor(this.currentQualifier, line);
                }

            }
        //(end for loop through line of file)

        //add the shared element values to the item.
        for(String[] s2D : this.lsShared){
            this.current_item.addElementType(s2D[0],s2D[1]);
        }

        //add current_item to lsItems
        lsItems.add(current_item);

        return 1;
    }


    // - helpers - //

    /**
     * check list of header options sent in to see if they are valid
     * @param lsOptions the set of custom options the user established for the header
     * @return whether or not the list of header options is valid.
     */
    private static boolean isValidOptions(List<String> lsOptions){

        List<String> lsAllOptions = Arrays.asList(ALL_OPTIONS);
        for(String o : lsOptions) {
            if (!lsAllOptions.contains(o))
                return false;
        }
        return true;
    }

    private static boolean isAllCaps(String str){
        return str.equals(str.toUpperCase());
    }

    // - match functions - //

    /* - Mode Entry - */

    /** mode-switch */
    private static boolean matchModeSwitchString(String str){
        return str.startsWith(DELIM_MATCHSWITCH) && str.endsWith(DELIM_MATCHSWITCH);
    }

    /** dc.contributor */
    private static boolean matchContributor(String str){
        return str.contains("CONTRIB") || str.contains("COLLABORATOR");
    }

    /** dc.description.tableOfContents */
    private static boolean matchTableOfContents(String str){
        return str.contains("TABLE OF") || str.contains("ARTICLE");
    }


    /* - 'special' line - */

    private static boolean matchComment(String str){
        return str.startsWith(DELIM_NOTE);
    }


}
