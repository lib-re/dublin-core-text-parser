import dc_metadata.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;


/**
 * Object representing an item from a given collection
 */
public class Item {

    //identification number in series
    public int id;

    //name of the file in the same directory
    public String filename = "";

    //list of all the data Elements
    private ArrayList<Element> lsElements;

    /**
     * Create an item of a given ID and no elements.
     */
    public Item(int i){
        this.id = i;
        this.lsElements = new ArrayList<Element>();
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
        String header = "---- Item " + id + ": " + filename + " ----";
        String toReturn = header;
        toReturn += "\n";
        for (Element e: lsElements ) {
            toReturn += "-> " + e.toString() + "\n";
        }

        toReturn += "\n";

        return toReturn;
    }

    // - add elements to the item - //

    /* - determine element type by option - */
    public boolean addElementType(String optionText, String line){

        boolean toReturn = true;

        String[] options = optionText.split("_");
        String e = options[0];

        String q = "";
        if(options.length>1)
            q = options[1];

        if(e.startsWith(Parser.AUDIENCE)) {
            this.lsElements.add(Audience.createAudience(q,line));
        }else if(e.startsWith(Parser.COVERAGE)){
            this.lsElements.add(Coverage.createCoverage(q,line));
        }else if(e.startsWith(Parser.DATE)){
            this.lsElements.add(Date.createDate(q,line));
        }else if(e.startsWith(Parser.DESCRIPTION)){
            this.lsElements.add(Description.createDescription(q,line));
        }else if(e.startsWith(Parser.FILENAME)){
            addFilename(line);
        }else if(e.startsWith(Parser.FORMAT)){
            this.lsElements.add(Format.createFormat(q,line));
        }else if(e.startsWith(Parser.IDENTIFIER)){
            this.lsElements.add(Identifier.createIdentifier(q,line));
        }else if(e.startsWith(Parser.LANGUAGE)){
            this.lsElements.add(Language.createLanguage(q,line));
        }else if(e.startsWith(Parser.NOTE)){
            this.lsElements.add(Description.createDescription("note",line));
        }else if(e.startsWith(Parser.PUBLISHER)){
            this.lsElements.add(Publisher.createPublisher(q,line));
        }else if(e.startsWith(Parser.RELATION)) {
            for(String s : line.split(Parser.SPLIT_HEADER)){
                this.lsElements.add(Relation.createRelation(q,line));
            }
        }else if(e.startsWith(Parser.RIGHTS)){
            this.lsElements.add(Rights.createRights(q,line));
        }else if(e.startsWith(Parser.RIGHTSHOLDER)){
            throw new NotImplementedException();
        }else if(e.startsWith(Parser.SUBJECT)){
            this.lsElements.add(Subject.createSubject(q,line)); //TODO remove in place of body?
        }else if(e.startsWith(Parser.TITLE)){
            this.lsElements.add(Title.createTitle(q,line));
        }else if(e.startsWith(Parser.TYPE)){
            this.lsElements.add(new Type(line));
        }else{
            toReturn = false;

            //TODO flag line with option as a warning/error including option and raw line
            System.err.println("!!! ERROR: '" + optionText + "' not recognized by parser!!!");
        }

        //TODO add logger line

        //return whether or not it was added
        return toReturn;
    }

    /**
     * add the name of the file associated with the file
     * TODO verify the file is valid, flag if it is not.
     */
    public void addFilename(String line) {
        this.filename = line.trim();
    }

    /** add an article to the item*/
    public void addArticle(String articleTitle){
        lsElements.add( Description.createDescription("table_of_contents", articleTitle));
    }
    /* - dc.contributor - */
    public void addContributor(String qualifier, String value) {
        lsElements.add(Contributor.createContributor(qualifier, value));
    }

    public void addNote(String line){
        this.addElementType(Parser.DESCRIPTION + "_" + "NOTE",line);
    }
}
