import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Main user-facing class. Directs all actions.
 */
public class Main {

    public static String DELIM_SHARED = "|";
    public static String DIRECTORY_PATH_IN  = "/Users/admin/Desktop/";
    public static String DIRECTORY_PATH_OUT = "/Users/admin/Desktop/Out";

    public static void main(String[] args) throws IOException, ParseException {

        // - get input from the user command line - //

        CommandLineParser posixParser = new PosixParser();
        Options options = new Options();

        //print-outs for help executing the program
        options.addOption("d", "dublin-core", false, "Display help for dublin core fields");
        options.addOption("h", "help", false, "Display the Help");
        //options.addOption("i", "interactive", false, "Make the execution interactive");
        //options.addOption("q", "quiet", false, "Suppress logging information");

        //extra files for custom execution
        options.addOption("c", "config", true, "Reference to a file containing alternative header arrangements");
        options.addOption("s", "shared", true, "file location of the shared.txt file containing the shared fields");


        //export options

        /* one file for entire collection */
        options.addOption("C", "csv",  false, "Create a single .csv  file containing metadata of each item");
        //options.addOption("M", "mrk",  false, "Create a single .mrk  file containing metadata of each item");
        //options.addOption("J", "json", false, "Create a single .json file containing metadata of each item");
        options.addOption("X", "xml",  false, "Create a single .xml  file containing metadata of each item");

        /* one file for each item */
        //options.addOption("j", "json-each", false, "Create a separate json object for each .txt file");
        //options.addOption("x", "xml-each",  false, "Create a separate xml object for each .txt file");
        //options.addOption("z", "zip", false, "(optional) ZIP the output");


        CommandLine commandLine = null;
        try{
            commandLine = posixParser.parse(options, args);
        }catch(org.apache.commons.cli.ParseException e){
            System.err.println(e);
            System.err.println("\n --FATAL ERROR PARSING COMMAND LINE INPUT-- \n");
        }


        // -- process this input -- //


        /* - display information about the program if prompted, then exit - */

        //display help information for program
        if(commandLine.hasOption('h')) {
            throw new NotImplementedException();
            //System.exit(0);
        }

        //display help information for dublin core
        if(commandLine.hasOption('d')) {
            throw new NotImplementedException();
            //System.exit(0);
        }


        /* - run the program in the absence of these tags */

        //instantiate a new parser
        Parser p = new Parser();

        //alter header with given the contents of config file
        if (commandLine.hasOption('c')) {
            List<String> lsOptions = processFileIntoStringArray(commandLine.getOptionValue('c').trim());

            if( !p.setHeaderOptions(lsOptions) ){
                System.out.println("!!! Error: Invalid config file !!! "); //TODO replace with logger.error
                System.exit(1);
            }
        }

        //set the shared
        if (commandLine.hasOption('s')) {
            List<String> lsSharedLines = processFileIntoStringArray(commandLine.getOptionValue('s').trim());

            ArrayList<String[]> lsShared2D = new ArrayList<String[]>();
            for(String line : lsSharedLines )
                lsShared2D.add(line.split(DELIM_SHARED));

            //export according to
            p.setShared(lsShared2D);
        }

        //go through each of the files,

        int id = 1000;
        //foreach file in the directory...

            String focus = "/NF-1969-02.txt";
            String waldo = "/sample-metadata-file.txt";
            String report= "/1990-v123n04.txt";
            String[] lsFiles = {focus, waldo, report };

            for(String filename : lsFiles) {
                p.processMetadataFile(processFileIntoStringArray(DIRECTORY_PATH_IN + filename), id);
                id++;
            }
        //end foreach file in directory...


        // - export in desired formats - //

        boolean exp_csv = commandLine.hasOption('C');
        boolean exp_XML = commandLine.hasOption('X');   boolean exp_xml = commandLine.hasOption('x');
        boolean exp_MRK = commandLine.hasOption('M');   boolean exp_mrk = commandLine.hasOption('m');
        boolean exp_JSN = commandLine.hasOption('J');   boolean exp_jsn = commandLine.hasOption('j');




        for(Item i : p.getLsItems()){ System.out.println(i); }
    }

    /**
     * Read all lines of a file, return list of strings.
     * @param filename
     * @return
     */
    public static List<String> processFileIntoStringArray(String filename) {

        List<String> lsLines = new ArrayList<String>();

        try {

            //open file
            File file = new File(filename);

            //create file and buffered reader
            BufferedReader br = new BufferedReader(new FileReader(file));

            int i = 0;
            for (String line; (line = br.readLine()) != null; ) {
                lsLines.add(i,line); i++;
            }
            // line is not visible here.
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            System.out.println("!!! Error file: " + filename + " not found !!!"); //TODO LOGGER.error
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("!!! Error opening and/or using the file: " + filename + " !!!"); //TODO LOGGER.error
        }

        return lsLines;

    }


}
