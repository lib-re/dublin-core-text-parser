import dc_metadata.Contributor;
import dc_metadata.Element;
import dc_metadata.Title;
import dc_metadata.Type;

import java.util.ArrayList;


/**
 * Object representing an item from a given collection
 */
public class Item {

    //identification number in series
    public int id;

    //name of the file in the same directory
    private String filename = "";

    //list of all the data Elements
    private ArrayList<Element> lsElements;

    /**
     * Create an item of a given ID with no elements.
     */
    public Item(int i){
        this.id = i;
        this.lsElements = new ArrayList<Element>();
    }

    // - add elements to the file - //

    /**
     * add the name of the file associated with the file
     * TODO verify the file is valid, flag if it is not.
     */
    public void addFilename(String line) {
        this.filename = line;
    }


    /* - dc.title - */

    public void addTitle(String title){
        lsElements.add(new Title(title));
    }

    public void addAlternativeTitle(String altTitle){
        lsElements.add(new Title(Title.qualifier.ALTERNATIVE, altTitle));
    }

    /* - dc.type - */
    public void addType(Type.value value){
        lsElements.add( new Type(value) );
    }

    /* - dc.description - */

    /** add an article to the item*/
    public void addArticle(String articleTitle){
        //TODO
    }

    /** add cataloguer's note regarding the item */
    public void addNote(String line) {
        //TODO
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
        //TODO
    }

    public void addPublisher(String publisher) {
        //TODO
    }

}
