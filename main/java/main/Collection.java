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
    private ArrayList< String[][] > lsShared;


    // -- CONSTRUCTORS -- //

    /** create basic collection with default config file, no shared, new items list*/
    public Collection(){
        this.configFile = new File(Main.DIRECTORY_PATH_CONF + "config.txt");
        this.lsItems = new ArrayList<Item>();
    }


    /**
     * Process all of the appropriate files in the given directory
     * @param directoryPath string for absolute path of directory
     */
    public int processDirectory(String directoryPath) {

        File directory;

        try{
            directory = new File(directoryPath);

            if(directory.isDirectory()){
                File[] lsFiles = directory.listFiles();

                if(lsFiles != null){
                    int i = 1;
                    for (File file : lsFiles) {
                        Item item = new Item(i);

                        //

                        this.lsItems.add(item);
                    }
                }

            }
        }catch(Exception e){
            return 0;
        }

        return 0; //TODO
    }

    public void addItem(Item i){ lsItems.add(i); }

    public ArrayList<Item> getLsItems(){
        return this.lsItems;
    }

}
