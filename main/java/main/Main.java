package main;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.*;
import java.util.logging.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * main.Main user-facing class. Directs all actions.
 */
public class Main {

    //delimiters
    public static String DELIM_SHARED = "|";
    public static String DIRECTORY_PATH_IN  = "./main/sample_data/"; //TODO allow custom path
    public static String DIRECTORY_PATH_CONF  = "./main/config/";    //TODO allow custom path
    public static String DIRECTORY_PATH_OUT   = "../output/";        //TODO allow custom path
    public String DIRECTORY_PATH_LOG   = "./main/logs/";
    private String EXPORT_FILENAME     = "export"; //name of each exported file in 'output/' (collection name)

    // configure logger
    private static Logger LOGGER = Logger.getLogger(Main.class.getName());


    // - public facing back end

    /**
     * Main function responsible for taking in user input at the command line,
     *   handling flags, and directing overall behavior of the program.
     * @param args list of files (?)
     * @throws IOException
     * @throws ParseException
     */
    public static void main(String[] args) throws IOException, ParseException {

        // - get input from the user command line - //
        String header = "A cataloguing tool for converting specially formatted text files containing dublin " +
                "core metadata into various formats\n";
        String footer = "";
        CommandLineParser posixParser = new PosixParser();
        Options options = new Options();

        //print-outs for help executing the program
        //options.addOption("d", "dublin-core", false, "Display help for dublin core fields");
        options.addOption("h", "help", false, "Display the help information");
        options.addOption("o", "output", true, "Name the output file");
        //options.addOption("i", "interactive", false, "Make the execution interactive");
        //options.addOption("q", "quiet", false, "Suppress logging information");

        //extra files for custom execution
        options.addOption("c", "config", true, "Reference to a file containing alternative header arrangements");
        options.addOption("s", "shared", true, "file location of the shared.csv file containing the shared fields");


        //export options

        /* one file for entire collection */
        options.addOption("C", "csv",  false, "Create a single .csv  file containing metadata of each item");
        options.addOption("M", "mrk",  false, "Create a single .mrk  file containing metadata of each item");
        options.addOption("J", "json", false, "Create a single .json file containing metadata of each item");
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
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("dublin-core-text-parser\n", header + "\n\n\n", options, footer, false);
            System.exit(0);
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
                LOGGER.severe("Invalid configuration file! Please check spelling and documentation.");
                System.exit(1);
            }
        }

        //set the shared
        if (true){ //commandLine.hasOption('s')) {
            List<String> lsSharedLines = processFileIntoStringArray(DIRECTORY_PATH_CONF + "shared.csv");
            //commandLine.getOptionValue('s').trim()); TODO set option to specify other file

            ArrayList<String[]> lsShared2D = new ArrayList<String[]>();
            for(String line : lsSharedLines ) {
                lsShared2D.add(line.split(",",2));
            }

            //export according to
            p.setShared(lsShared2D); //TODO check return value
        }

        //go through each of the files,

        int id = 1;
        //foreach file in the directory...

            String waldo = "sample-metadata-file.txt";
            String sample= "1990-v123n04.txt";
            String[] lsFiles = { waldo, sample };

            for(String filename : lsFiles) {
                String path = DIRECTORY_PATH_IN + filename;

                //todo: harvest metadata from file and use it to populate provenance information
                p.processMetadataFile(processFileIntoStringArray(DIRECTORY_PATH_IN + filename), id);
                id++;
            }
        //end foreach file in directory...


        // - export in desired formats - //

        String exportFilename = "export";

        // get desired format/s from command line
        boolean exp_csv = commandLine.hasOption('C');
        boolean exp_XML = commandLine.hasOption('X');   boolean exp_xml = commandLine.hasOption('x');
        boolean exp_MRK = commandLine.hasOption('M');   boolean exp_mrk = commandLine.hasOption('m');
        boolean exp_JSN = commandLine.hasOption('J');   boolean exp_jsn = commandLine.hasOption('j');

        //
        List<Exporter> lsExporters = new ArrayList<Exporter>();

        lsExporters.add( new export.TXTExporter() );
        if(exp_csv){ lsExporters.add( new export.CSVExporter() ); }
        //if(exp_XML){ lsExporters.add( new XMLExporter() ); }
        //if(exp_JSN){ lsExporters.add( new JSNExporter() ); }
        //if(exp_bib){ lsExporters.add( new BibExporter() ); }

        for(Exporter e : lsExporters){
            e.processCollection(p.getCollection());
            //e.publish(new File(DIRECTORY_PATH_OUT), exportFilename);
        }

    }


    // - helpers - //

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
            System.err.println("!!! Error file: " + filename + " not found !!!"); //TODO LOGGER.error
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.err.println("!!! Error opening and/or using the file: " + filename + " !!!"); //TODO LOGGER.error
        }

        return lsLines;

    }


}
