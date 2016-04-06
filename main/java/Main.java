import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.cli.*;

/**
 * Created by admin on 4/5/16.
 */
public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        CommandLineParser parser = new PosixParser();

        Options options = new Options();

        //extra files
        options.addOption("h", "help", false, "Display the Help");
        options.addOption("c", "config", true, "collection");
        options.addOption("s", "shared", true, "file location of the shared.txt file containing the shared fields");


        //export options
        options.addOption("j", "json", false, "Create a json object for each .txt file");
        options.addOption("x", "xml",  false, "Create an xml object for each .txt file");

        options.addOption("z", "zip", false, "(optional) ZIP the output");

    }

}
