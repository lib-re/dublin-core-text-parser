import org.pmw.tinylog.Logger;

import java.io.*;
import java.lang.reflect.Array;
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


    // -- PROCESS -- //

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

    public int processFile(File f){
        return 0; //TODO
    }

    // -- CONFIG -- //


    // -- SHARED -- //

    /** parse through shared.csv file and return 2D list */
    private static ArrayList< String[][] > processShared(String filename){

        ArrayList< String[][] > lsShared = new ArrayList<String[][]>();

        String line = "";
        String error = "!! Error reading shared.csv file: ";
        BufferedReader br = null;

        try{
            br = new BufferedReader( new FileReader(filename) );

            int i = 0;
            String[][] data = new String[1][];
            while((line = br.readLine()) != null){
                String[] contents = line.split(",",2);
                data[i][0] = contents[1];
                data[i][1] = contents[2];
                lsShared.add(data);
                i++;
            }

        }catch(FileNotFoundException fnfe){
            Logger.error("File '{}' not found!", filename);
            fnfe.printStackTrace();
        }catch(IOException ioe){
            Logger.error("Error parsing file '{}'!", filename);
            ioe.printStackTrace();
        }

        return lsShared;
    }


    // -- EXPORT -- //


    /* single file */

    /** export collection to single CSV file */
    public File exportToCSV(String filename){
        return null; //TODO
    }

    /** export collection to giant xml */
    public File exportToXML(String filename){
        return null; //TODO
    }

    /** export collection to single json file */
    public File exportToJSON(String filename){
        return null; //TODO
    }

    /** export collection to single mark file */
    public File exportToMARK(String filename){
        return null; //TODO
    }

    /* multiple files */
    public File exportToXMLEach(String filename){
        return null; //TODO
    }

    /* multiple files */
    public File exportToJSONEach(String filename){
        return null; //TODO
    }


}
