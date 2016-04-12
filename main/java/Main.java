import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.cli.*;

/**
 * Main user-facing class. Directs all actions.
 */
public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        // - get input from the user command line - //

        CommandLineParser posixParser = new PosixParser();
        Options options = new Options();

        //print-outs for help executing the program
        options.addOption("d", "dublin-core", false, "display help for dublin core fields");
        options.addOption("h", "help", false, "Display the Help");

        //extra files for custom execution
        options.addOption("c", "config", true, "Reference to a file containing alternative header arrangements");
        options.addOption("s", "shared", true, "file location of the shared.txt file containing the shared fields");


        //export options

        /* one file for entire collection */
        options.addOption("C", "csv",  false, "Create a single .csv  file containing metadata of each item");
        options.addOption("M", "mrk",  false, "Create a single .mrk  file containing metadata of each item");
        options.addOption("J", "json", false, "Create a single .json file containing metadata of each item");
        options.addOption("X", "xml",  false, "Create a single .xml  file containing metadata of each item");

        /* one file for each item */
        options.addOption("j", "json-each", false, "Create a separate json object for each .txt file");
        options.addOption("x", "xml-each",  false, "Create a separate xml object for each .txt file");
        options.addOption("z", "zip", false, "(optional) ZIP the output");


        //print for help
        

        //process this input


        //export according to

    }



}
