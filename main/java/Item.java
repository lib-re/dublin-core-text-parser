import dc_metadata.*;

import java.util.ArrayList;


/**
 * Object representing an item from a given collection
 */
public class Item {

    //store option strings
    public static String DATE_ISSUED = "DATE_ISSUED";
    public static String DESCRIPTION = "DESCRIPTION";
    public static String FILENAME    = "FILENAME";
    public static String FORMAT      = "FORMAT";
    public static String IDENTIFIER  = "IDENTIFIER";
    public static String LANGUAGE    = "LANGUAGE";
    public static String LICENSE     = "LICENSE";
    public static String PUBLISHER   = "PUBLISHER";
    public static String SERIES      = "SERIES";
    public static String SUBJECT     = "SUBJECT";
    public static String TITLE       = "TITLE";
    public static String TITLE_ALT   = "TITLE_ALTERNATIVE";
    public static String TYPE        = "TYPE";

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

    // - print functions - //
    @Override
    public final String toString(){
        String header = "---- Item " + id + ": " + filename + " ----";
        String toReturn = header;
        toReturn += "\n\n";
        for (Element e: lsElements ) {
            toReturn += "-> " + e.toString() + "\n";
        }

        toReturn += "\n" + header + "\n";

        return toReturn;
    }

    // - add elements to the item - //

    /**
     *
     * @param option String containing reference for
     * @param line
     * @return
     */
    public boolean addOption(String option, String line){

        boolean validOption = true;

        if(option == DATE_ISSUED){      this.addDateIssued(line); }
        else if(option == DESCRIPTION){ this.addDesciption(line); }
        else if(option == FILENAME){    this.filename = line;     }
        else if(option == PUBLISHER){   this.addPublisher(line);  }
        else if(option == TITLE){       this.addTitle(line);      }
        else if(option == TITLE_ALT){   this.addAltTitle(line);   }
        else if(option == TYPE){        this.addType(line);       }
        else { validOption = false; }

        return validOption;
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
    }

    public void addAltTitle(String altTitle){
        lsElements.add(new Title(Title.qualifier.ALTERNATIVE, altTitle));
    }

    /* - dc.type - */
    public void addType(String type)/* throws IllegalArgumentException*/{
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
    }

    /* - dc.contributor - */
    public void addContributor(Contributor.qualifier q, String value){
        lsElements.add( new Contributor(q, value));
    }

    /* - other - */

    /** add dc.relation.isPartOfSeries element */
    public void addSeries(String part) {
        lsElements.add(new Relation(Relation.qualifier.IS_PART_OF, part));
    }

    public void addPublisher(String publisher) {
        //TODO
    }

}
