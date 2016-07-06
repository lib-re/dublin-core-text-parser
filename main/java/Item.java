import dc_metadata.*;
import org.pmw.tinylog.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Object representing an item from a given collection
 */
public class Item {

    //settings
    private boolean SETTING_TOSORT = true;

    //identification number in series
    public int id;

    //name of the file in the same directory
    public List<String> lsFilenames;

    //list of all the data Elements
    private List<Element> lsElements;

    //list of all the warnings
    private List<String> lsWarnings;

    /**
     * Create an item of a given ID and no elements.
     */
    public Item(int i){
        this.id = i;
        this.lsElements = new ArrayList<Element>();
        this.lsFilenames = new ArrayList<String>();
        this.lsWarnings = new ArrayList<String>();
    }

    // - print/export functions - //

    /**
     * Return string of printout listing Item ID, Filename, followed by a printout
     *   of all elements:
     *
     *   ```
     *   ---- Item ##: filename.pdf ----
     *   -> dc.title: The Seducer's Diary
     *   -> dc.contributor.author: Johannes Climacus
     *   -> dc.publisher: Soren Kierkegaard
     *   -> dc.type: book
     *
     *   ```
     */
    @Override
    public final String toString(){
        String header = "---- Item " + id + ": ";

            //list filenames
            int i = 0;
            for(String s: this.lsFilenames) {
                if (lsFilenames.size() > 1 && i >= 1) { header += ", "; }
                header += s; i++;
            }
        header+= " ----";

        String toReturn = header;
        toReturn += "\n";

        //sort the items for printing according to setting [ alphabetically by E.name->E.qualifier->E.value ]
        if(SETTING_TOSORT)
            Collections.sort(lsElements);

        for (Element e: lsElements){
            toReturn += "-> " + e.toString() + "\n";
        }

        toReturn += "\n";

        return toReturn;
    }

    // - add elements to the item - //

    /* - determine element type by option - */
    public boolean addElementType(String optionText, String line){

        //whether or not the element was successfully added.
        boolean added = true;
        line = line.trim();

        //determine Element (e) and Qualifier (q) options
        String[] options = optionText.split(Parser.SPLIT_OPTION);
        String e = options[0];

        String q = "";
        if(options.length>1)
            q = options[1];


        //add once or using different split character
        if(e.startsWith(Parser.DATE)) {
            this.lsElements.add(Date.createDate(q, line));
        }else if(e.startsWith(Parser.TITLE)){
            for (String value : line.split("\\|",-1)) {
                this.lsElements.add(Title.createTitle(q, value.trim()));
            }
        } else if (e.startsWith(Parser.PUBLISHER)) {
            for (String value : line.split("\\|",-1)) {
                this.lsElements.add(Publisher.createPublisher(q, value.trim()));
            }
        }

        //handle multiple values in one header line:
        for(String s : line.split(Parser.SPLIT_HEADER)) {
            s = s.trim();

            if (e.startsWith(Parser.FILENAME)) {
                addFilename(s);
            } else if (e.startsWith(Parser.AUDIENCE)) {
                this.lsElements.add(Audience.createAudience(q, s));
            } else if (e.startsWith(Parser.COVERAGE)) {
                this.lsElements.add(Coverage.createCoverage(q, s));
            } else if (e.startsWith(Parser.DESCRIPTION)) {
                this.lsElements.add(Description.createDescription(q, s));
            } else if (e.startsWith(Parser.FORMAT)) {
                this.lsElements.add(Format.createFormat(q, s));
            } else if (e.startsWith(Parser.IDENTIFIER)) {
                this.lsElements.add(Identifier.createIdentifier(q, s));
            } else if (e.startsWith(Parser.LANGUAGE)) {
                this.lsElements.add(Language.createLanguage(q, s));
            } else if (e.startsWith(Parser.NOTE)) {
                this.lsElements.add(Description.createDescription("note", s));
            } else if (e.startsWith(Parser.RELATION)) {
                this.lsElements.add(Relation.createRelation(q, s));
            } else if (e.startsWith(Parser.RIGHTS)) {
                this.lsElements.add(Rights.createRights(q, s));
            } else if (e.startsWith(Parser.RIGHTSHOLDER)) {
                throw new NotImplementedException();
            } else if (e.startsWith(Parser.SUBJECT)) {
                this.lsElements.add(Subject.createSubject(q, s)); //TODO remove in place of body?
            } else if (e.startsWith(Parser.TYPE)) {
                this.lsElements.add(new Type(s));
            }
        }

        if(!Arrays.asList( Parser.ALL_OPTIONS ).contains(e)){
            added = false;

            //
            String warnText = String.format("'{}:{}' not added to item {}.",optionText,line,this.id);
            this.lsWarnings.add(warnText);

            Logger.warn("{} not recognized by option parser!", optionText);
            Logger.warn(warnText);
        }

        //return whether or not it was added
        return added;
    }


    // - Adding functions - //

    /* - special - */

    /**
     * add the name of the file associated with the file
     * TODO verify the file is valid, flag if it is not.
     */
    public void addFilename(String line) {
        this.lsFilenames.add(line.trim());
    }

    /** add an article to the item*/
    public void addArticle(String articleTitle){
        lsElements.add( Description.createDescription("table_of_contents", articleTitle));
    }

    /** add a contributor - */
    public void addContributor(String qualifier, String value) {
        lsElements.add(Contributor.createContributor(qualifier, value));
    }

    /** add a note : DESCRIPTION_NOTE -> dc.description.note */
    public void addNote(String line){
        lsElements.add(Description.createDescription("_NOTE",line));
    }

    /** add a subject */
    public void addSubject(String qualifier, String line){
        lsElements.add(Subject.createSubject(qualifier, line));
    }


}
