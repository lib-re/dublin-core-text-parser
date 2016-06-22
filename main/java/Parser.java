import dc_metadata.Contributor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parser object which interprets a given text file and creates an interpreted 'Item' object
 */
public class Parser {

    //delimiters
    private static final String DELIM_MODESWITCH = "-";
    private static final String DELIM_NOTE = "//";
    public static String SPLIT_HEADER = ",";
    //private static String SPLIT_HEADER_TITLE = "|";

    //options (elements+filename+'note')
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
    private mode current_mode = mode.CONTRIBUTORS;

    //qualifier
    private String current_qualifier = Contributor.AUTHOR;

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
    public boolean setHeaderOptions(List<String> lsHeaderLines){

        //return false if the options listed are invalid
        if(!isValidOptions(lsHeaderLines))
            return false;

        //clear the list of existing strings
        this.lsHeaderOptions.clear();

        //add, in order, the options passed in.
        return this.lsHeaderOptions.addAll(lsHeaderLines);
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
    public boolean processMetadataFile(List<String> lsFileLines, int id){

        //item to be added
        current_item = new Item(id);

        int i = 0;
        for(String line : lsFileLines) {
            line = line.trim();

            //don't process empty lines, but still increment the line number
            if(line.isEmpty()) {
                i++; continue;
            }

            //process header according to set rules
            if (i >= 0 && i < this.lsHeaderOptions.size()) {
                current_item.addElementType(lsHeaderOptions.get(i), line);
                i++;
            }

            //catch any commented lines and add them as notes
            else if (matchComment(line)) {
                current_item.addNote(line.replace("//","").trim());
            }

            //change mode according to tags
            else if (matchModeSwitchString(line)) {

                System.out.print("MODE_SWITCH: " + this.current_mode + " -> ");

                //switch to adding contributors
                if (matchContributor(line)) {
                    this.current_mode = mode.CONTRIBUTORS;
                }

                //switch to adding articles
                if (matchTableOfContents(line)) {
                    this.current_mode = mode.ARTICLES;
                }

                System.out.println(current_mode);

            }

            // - process the body - //

            //if current mode is adding articles, just add the line
            else if (current_mode == mode.ARTICLES) {
                current_item.addArticle(line);
            }

            //if current mode is adding contributors...
            else if (current_mode == mode.CONTRIBUTORS) {

                //if we read an all caps field, switch contributor type
                if (isAllCaps(line)) {
                    System.out.print("PARSER: switch contributor type: '" + this.current_qualifier + "' to ");

                    this.current_qualifier = line;

                    System.out.println("'" + this.current_qualifier + "'");

                    //add appropriate contributor qualifier type
                } else {
                    current_item.addContributor(this.current_qualifier, line);
                }

            }
        }//(end for loop through line of file)

        //add the shared element values to the item.
        for(String[] s2D : this.lsShared){
            this.current_item.addElementType(s2D[0],s2D[1]);
        }

        //add current_item to lsItems
        lsItems.add(current_item);

        System.out.println(current_item);

        return true;
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
        return str.startsWith(DELIM_MODESWITCH) && str.endsWith(DELIM_MODESWITCH);
    }

    /** dc.contributor */
    private static boolean matchContributor(String str){
        return str.contains("CONTRIB") || str.contains("COLLABORATOR");
    }

    /** dc.description.tableOfContents */
    private static boolean matchTableOfContents(String str){
        return (str.contains("TABLE") && str.contains("OF") && str.contains("CONTENT") ) || str.contains("ARTICLE");
    }


    /* - 'special' line - */

    private static boolean matchComment(String str){
        return str.startsWith(DELIM_NOTE);
    }


    /* - getters and setters */
    public List<Item> getLsItems(){
        return this.lsItems;
    }

}
