package main;

import dc_metadata.Element;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for exporting into various formats
 */
public abstract class Exporter {

    // parts of the exported document, possibly reused
    protected PrintStream ps;

    // data members
    protected List<String> lsUniqueElementNames;
    protected String delim = null;

    /**
     * create default Exporter with System.out as its PrintStream
     */
    public Exporter(){
        lsUniqueElementNames = new ArrayList<String>();
        lsUniqueElementNames.add("id");
        lsUniqueElementNames.add("filename");
        delim = "";
        ps = System.out;
    }

    /**
     * create Exporter with a particular PrintStream
     */
    public Exporter(PrintStream printStream){
        this();
        this.ps = printStream;
    }


    /**
     * process the header, items, and footers for each item in the collection
     */
    public void processCollection(Collection c){

        /* - FIRST PASS - */
        for(Item i : c.getLsItems()){

            //establish list of unique element names
            for(String k : i.getUniqueKeys()){
                if( !this.lsUniqueElementNames.contains(k) )
                    this.lsUniqueElementNames.add(k);
            }

        }

        /* - prepare header - */
        //add header to start of ps
        ps.print( processHeader() );


        /* - SECOND PASS - */

        //progressively add each item to the list of
        for(Item item : c.getLsItems()) {
            ps.print(processItemHeader(item));
            for (String elementType : lsUniqueElementNames) {

                // prepare list of elements for given element type
                List<Element> lsElements = item.getElementsOfType(elementType);

                // handle case where no elements
                if(lsElements == null || lsElements.size() == 0) {
                    ps.print("");
                }else {

                    int size = lsElements.size();
                    if(size == 1) {
                        ps.print("\"" + processElement(lsElements.get(0), true));
                        continue;
                    }

                    int i; Boolean isLast = null;
                    for (i = 0; i < size - 1; i++) {
                        ps.print(processElement(lsElements.get(i),isLast));
                        isLast = false;
                    }
                    ps.print(processElement(lsElements.get(i),true));
                }
            }
            ps.print(processItemFooter(item));
        }

        // add footer to end of ps
        processFooter();
    }


    // - helpers - //

    /** determine and return the export string for a given item */
    protected String processItemHeader(Item i){ return ""; }

    /** determine and return the export string for a given item */
    protected String processItemFooter(Item i){ return "\n"; }

    /** determine and return export string for a given element */
    protected abstract String processElement(Element e, Boolean isLast);

    /** defaulted empty placeholder for populating the header */
    protected String processHeader(){ return ""; }

    /** defaulted empty placeholder for populating the footer */
    protected String processFooter(){ return ""; }


    // - publishing - //

    /** write from stream to file in a given directory */
    public File publish(File directory, String filename){
        throw new NotImplementedException();
    }

}
