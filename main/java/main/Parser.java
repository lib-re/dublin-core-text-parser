package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Parser object which interprets a given text file and creates an interpreted 'Item' object
 */
public class Parser {

    //delimiters
    private static final String DELIM_MODESWITCH = "-";
    private static final String DELIM_NOTE = "//";
    public static String SPLIT_OPTION = "_";
    public static String SPLIT_HEADER = ",";

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
    public static String[] ALL_OPTIONS  =
            { AUDIENCE, COVERAGE, DATE, DESCRIPTION, FILENAME, FORMAT, IDENTIFIER, LANGUAGE,
                NOTE, PUBLISHER, RELATION, RIGHTS, RIGHTSHOLDER, SUBJECT, TITLE, TYPE };

    //modes
    public enum mode { CONTRIBUTORS, ARTICLES, SUBJECT, NULL }
    protected mode current_mode = mode.NULL;

    //critical elements
    private ArrayList<String> lsHeaderOptions;
    private Collection collection;
    private String current_qualifier;


    // configure logger
    private static Logger LOGGER = Logger.getLogger(Parser.class.getName());



    // - constructors - //

    /**
     * base constructor which initializes object state and sets defaults
     */
    public Parser(){

        //initialize header
        initHeader();

        //initialize collection
        collection = new Collection();

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

    /**
     * populate the list of shared values from a given array
     */
    public boolean setShared(List<String[]> shared){

        //iterate through list, adding
        ArrayList<String> lsSharedOptions = new ArrayList<String>();
        for(String[] line : shared){ //for each line in the shared file
            lsSharedOptions.add(line[0]); //option
            String[] toAdd = {line[0], line[1]};
            this.collection.lsShared.add(toAdd);

            LOGGER.info(String.format("Added shared value '%s:%s'", line[0],line[1]));
        }

        //validate the set of shared values. if not validated, clear
        if(!isValidOptions(lsSharedOptions)){
            this.collection.lsShared.clear();
            LOGGER.warning("Collection-level warning: 'shared.csv' file is not accurately formatted or is empty");
            return false;
        }

        //already added
        return true;
    }

    /** Process a single metadata file */
    public boolean processMetadataFile(List<String> lsFileLines, int id){

        //item to be added
        Item current_item = new Item(id);

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

                mode m = determineMode(line);

                //check mode is valid, then log modeswitch
                if(m.equals(mode.NULL)) {
                    String message = "'" +line + "' cannot be processed into an appropriate element.";
                    LOGGER.severe(message);
                    //TODO add to warnings
                }else {
                    LOGGER.info(String.format("Mode switched from %s to %s.", this.current_mode, m));
                    this.current_mode = m;
                }
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

                    LOGGER.info(String.format("Contributor type switched from '%s' to '%s'", this.current_qualifier, line));
                    this.current_qualifier = line;


                //add appropriate contributor qualifier type
                } else {
                    current_item.addContributor(this.current_qualifier, line);
                    LOGGER.info("Added new contributor: " + line);
                }

            }else if (current_mode == mode.SUBJECT) {

                //update the encoding for the subject
                if (isAllCaps(line)){
                    this.current_qualifier = line;
                    LOGGER.info("subject qualifier set to '"+ line + "'");
                }else{
                    current_item.addSubject(this.current_qualifier, line);
                    LOGGER.info(String.format("dc.subject.%s added: %s", current_qualifier, line));
                }

            }
        }//(end for loop through line of file)

        //add the shared element values to the item.
        for(String[] s2D : this.collection.lsShared){
            current_item.addElementType(s2D[0],s2D[1]);
        }

        //add current_item to lsItems
        addItem(current_item);

        return true;
    }


    // - helpers - //

    private void addItem(Item i){ this.collection.addItem(i); }

    private void initHeader(){
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
        lsHeaderOptions.add(RELATION + SPLIT_OPTION + "ISPARTOFSERIES");
        lsHeaderOptions.add(DATE + SPLIT_OPTION + "ISSUED");
        lsHeaderOptions.add(TITLE + SPLIT_OPTION + "ALTERNATIVE");
        lsHeaderOptions.add(FILENAME);
    }

    /**
     * check list of header options sent in to see if they are valid
     * @param lsOptions the set of custom options the user established for the header
     * @return whether or not the list of header options is valid.
     */
    private static boolean isValidOptions(List<String> lsOptions){

        List<String> lsAllOptions = Arrays.asList(ALL_OPTIONS);
        for(String o : lsOptions) {
            if(o.isEmpty()){ continue; }
            else if (!lsAllOptions.contains(o)) {
                LOGGER.warning(String.format("Option '%s' invalid", o));
                return false;
            }
        }
        return true;
    }

    private static boolean isAllCaps(String str){
        return str.equals(str.toUpperCase());
    }

    /**
     * determine whether or not the given string contains any of the target
     *   strings.
     * @param target array of strings to which you are matching the given
     * @param given any string which you want to determine
     * @return whether the given string contains any of the target strings
     */
    private static boolean containsAny(String[] target, String given){
        for(String s : target){ if(given.contains(s)){ return true; } }
        return false;
    }

    // - match functions - //

    /* - Mode Entry - */

    public static mode determineMode(String str){

        if(matchTableOfContents(str) ){ return mode.ARTICLES; }
        else if(matchContributor(str)){ return mode.CONTRIBUTORS; }
        else if(matchSubject(str)    ){ return mode.SUBJECT; }
        else{                           return mode.NULL; }

    }

    /** mode-switch */
    private static boolean matchModeSwitchString(String str){
        return str.startsWith(DELIM_MODESWITCH) && str.endsWith(DELIM_MODESWITCH);
    }

    /** dc.contributor */
    private static boolean matchContributor(String str){
        return str.contains("CONTRIB") || str.contains("COLLABORAT");
    }

    /** dc.description.tableOfContents */
    private static boolean matchTableOfContents(String str){
        String[] target_toc = { "INDEX", "CONTENTS", "ARTICLES" }; //expand
        return containsAny(target_toc, str);
    }

    /** dc.subject.* */
    private static boolean matchSubject(String str){
        String[] target_subject = { "SUBJECT", "TAG", "KEYWORD", "TOPIC" };
        return containsAny(target_subject, str);
    }


    /* - 'special' line - */

    private static boolean matchComment(String str){
        return str.startsWith(DELIM_NOTE);
    }


    /* - getters and setters - */
    public Collection getCollection(){
        return this.collection;
    }

}
