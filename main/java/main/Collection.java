package main;

import java.io.*;
import java.util.ArrayList;

/**
 * Representative of an entire collection (given number of
 */
public class Collection {

    //object specific states
    private File configFile;
    private ArrayList<Item> lsItems;
    public ArrayList< String[] > lsShared;

    //todo implement lib-name-parser functionality
    //private LibNameParser libNameParser


    // -- CONSTRUCTORS -- //

    /** create basic collection with default config file, no shared, new items list*/
    public Collection(){
        this.configFile = new File(Main.DIRECTORY_PATH_CONF + "config.txt");
        this.lsItems = new ArrayList<Item>();
        this.lsShared = new ArrayList<String[]>();
    }

    public void addItem(Item i){ lsItems.add(i); }

    public ArrayList<Item> getLsItems(){
        return this.lsItems;
    }

}
