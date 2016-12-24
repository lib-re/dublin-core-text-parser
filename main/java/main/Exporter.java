package main;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.PrintStream;

/**
 * Abstract class for exporting into various formats
 */
public abstract class Exporter {

    protected PrintStream ps;

    public Exporter(){
        ps = System.out;
    }

    public Exporter(PrintStream printStream){
        this.ps = printStream;
    }

    protected String fileText;

    // parts of the exported document, possibly reused
    protected static String header;
    protected static String footer;

    /**
     * process the header, items, and footers for each item in the collection
     */
    public void processCollection(Collection c){

        //add header to start of ps
        processHeader();

        //progressively add each item to the list of
        for(Item i : c.getLsItems())
            ps.println( processItem(i) );

        // add footer to end of ps
        processFooter();
    }

    // process
    protected abstract String processItem(Item i);

    /**
     * write from print stream to file in a given directory
     */
    public File publish(File directory, String filename){
        throw new NotImplementedException();
    }

    /** defaulted empty placeholder for populating the header */
    protected void processHeader(){  }

    /** defaulted empty placeholder for populating the footer */
    protected void processFooter(){  }


}
