package main;

import dc_metadata.*;
import org.pmw.tinylog.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;
import java.util.Collection;


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
    protected Map<String, List<Element>> dctElements;
    //private List<Element> lsElements;

    //list of all the warnings
    private List<String> lsWarnings;

    /**
     * Create an item of a given ID and no elements.
     */
    public Item(int i){
        this.id = i;
        //this.lsElements     = new ArrayList<Element>();
        this.lsFilenames    = new ArrayList<String>();
        this.lsWarnings     = new ArrayList<String>();

        this.dctElements    = new HashMap<String, List<Element>>();
    }

    // - print/export functions - //

    /**
     * Return string of printout listing Item ID, Filename, followed by a printout
     *   of all elements:
     *
     *   todo: remove and place into TXTExporter
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

        String toReturn = "";
        toReturn += "\n";

        //sort the items for printing according to setting [ alphabetically by E.name->E.qualifier->E.value ]
        //if(SETTING_TOSORT)
        //    Collections.sort(dctElementsElements);

        for (Collection<Element> colElem: this.dctElements.values())
            for(Element e : colElem)


        toReturn += "\n";

        toReturn += "----------\n " + this.dctElements.toString();

        return toReturn;
    }

    // - add elements to the item - //

    private void addElement(Element e){

        String key = e.getQualifiedName();

        List<Element> lsValues = this.dctElements.get(key);
        if(lsValues == null)
            lsValues = new ArrayList<Element>();

        lsValues.add(e);
        this.dctElements.put(key, lsValues);
    }

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
            this.addElement(DCDate.createDate(q, line));
        }else if(e.startsWith(Parser.TITLE)){
            for (String value : line.split("\\|",-1)) {
                this.addElement(Title.createTitle(q, value.trim()));
            }
        } else if (e.startsWith(Parser.PUBLISHER)) {
            for (String value : line.split("\\|",-1)) {
                this.addElement(Publisher.createPublisher(q, value.trim()));
            }
        }

        //handle multiple values in one header line:
        for(String s : line.split(Parser.SPLIT_HEADER)) {
            s = s.trim();

            if (e.startsWith(Parser.FILENAME)) {
                addFilename(s);
            } else if (e.startsWith(Parser.AUDIENCE)) {
                this.addElement(Audience.createAudience(q, s));
            } else if (e.startsWith(Parser.COVERAGE)) {
                this.addElement(Coverage.createCoverage(q, s));
            } else if (e.startsWith(Parser.DESCRIPTION)) {
                this.addElement(Description.createDescription(q, s));
            } else if (e.startsWith(Parser.FORMAT)) {
                this.addElement(Format.createFormat(q, s));
            } else if (e.startsWith(Parser.IDENTIFIER)) {
                this.addElement(Identifier.createIdentifier(q, s));
            } else if (e.startsWith(Parser.LANGUAGE)) {
                this.addElement(Language.createLanguage(q, s));
            } else if (e.startsWith(Parser.NOTE)) {
                this.addElement(Description.createDescription("note", s));
            } else if (e.startsWith(Parser.RELATION)) {
                this.addElement(Relation.createRelation(q, s));
            } else if (e.startsWith(Parser.RIGHTS)) {
                this.addElement(Rights.createRights(q, s));
            } else if (e.startsWith(Parser.RIGHTSHOLDER)) {
                throw new NotImplementedException();
            } else if (e.startsWith(Parser.SUBJECT)) {
                this.addElement(Subject.createSubject(q, s)); //TODO remove in place of body?
            } else if (e.startsWith(Parser.TYPE)) {
                this.addElement(new Type(s));
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

    public void addArticle(String articleTitle){
        this.addElement( Description.createDescription("table_of_contents", articleTitle));
    }

    public void addContributor(String qualifier, String value) {
        this.addElement(Contributor.createContributor(qualifier, value));
    }

    /** add a note : DESCRIPTION_NOTE -> dc.description.note */
    public void addNote(String line){
        this.addElement(Description.createDescription("_NOTE",line));
    }

    public void addSubject(String qualifier, String line){
        this.addElement(Subject.createSubject(qualifier, line));
    }


    // - helpers - //

    public Set<String> getUniqueKeys(){
        return this.dctElements.keySet();
    }

    public List<Element> getElementsOfType(String key){
        return this.dctElements.get(key);
    }

}
