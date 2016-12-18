package main;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;

/**
 * Abstract class for exporting to various formats
 */
public abstract class Exporter {

    protected String fileText;

    // parts of the exported document, possibly reused
    protected static String header;
    protected static String footer;

    //
    public String processCollection(Collection c){
        throw new NotImplementedException();
    }

    // process
    protected abstract String processItem(Item i);

    public abstract File publish(File directory, String filename);
}
