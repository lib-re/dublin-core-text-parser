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

        String[] options = (optionText.toLowerCase()).split("_");
        String e = options[0];

        String q = "";
        if(options.length>1)
            q = options[1];

        if(e.startsWith(Parser.AUDIENCE)) {

        }else if(e.startsWith(Parser.COVERAGE)){

        }else if(e.startsWith(Parser.DATE)){
            addDateIssued(line);
        }else if(e.startsWith(Parser.DESCRIPTION)){
            addDesciption(line);
        }else if(e.startsWith(Parser.FILENAME)){
            addFilename(line);
        }else if(e.startsWith(Parser.FORMAT)){

        }else if(e.startsWith(Parser.IDENTIFIER)){

        }else if(e.startsWith(Parser.LANGUAGE)){

        }else if(e.startsWith(Parser.NOTE)){

        }else if(e.startsWith(Parser.PUBLISHER)){

        }else if(e.startsWith(Parser.RELATION)) {
            for(String s : line.split(Parser.SPLIT_HEADER)){
                addSeries(s);
            }
        }else if(e.startsWith(Parser.RIGHTS)){

        }else if(e.startsWith(Parser.RIGHTSHOLDER)){

        }else if(e.startsWith(Parser.SUBJECT)){

        }else if(e.startsWith(Parser.TITLE)){
            if(q.isEmpty()){ addTitle(line); } else{ addAltTitle(line); }
        }else if(e.startsWith(Parser.TYPE)){

        }else{
            toReturn = false;
            //TODO flag line with option as a warning/error including option and raw line
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


    /* - dc.title - */

    public void addTitle(String title){
        lsElements.add(new Title(title));

        System.out.println("+ dc.description.title: " + title);
    }

    public void addAltTitle(String altTitle){
        lsElements.add(new Title(Title.qualifier.ALTERNATIVE, altTitle));
    }

    /* - dc.type - */
    public void addType(String type){
        lsElements.add( new Type(type) );
    }

    /* - dc.description - */

    /** add new basic description to the item*/
    private void addDesciption(String line) {
        lsElements.add( new Description(line) );
    }

    /** add an article to the item*/
    public void addArticle(String articleTitle){
        lsElements.add( new Description(Description.qualifiers.TABLE_OF_CONTENTS, articleTitle));
    }

    /** add cataloguer's note regarding the item */
    public void addNote(String line) {
        lsElements.add( new Description(Description.qualifiers.NOTE, line));
    }

    /* - dc.date - */

    public void addDateIssued(String line) {
        //TODO
        throw new NotImplementedException();
    }

    /* - dc.contributor - */
    public void addContributor(String qualifier, String value){
        lsElements.add( Contributor.createContributor(qualifier, value));
    }

    /* - other - */

    /** add dc.relation.isPartOfSeries element */
    public void addSeries(String part) {
        lsElements.add(new Relation(Relation.qualifier.IS_PART_OF, part));
    }

    public void addPublisher(String publisher) {
        //TODO
        throw new NotImplementedException();
    }

}
