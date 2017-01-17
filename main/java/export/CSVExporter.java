package export;

import dc_metadata.Element;
import main.Item;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Export to CSV file for use with `DSpace/SAFBuildier`
 */
public class CSVExporter extends main.Exporter{

    // - Constructors - //

    public CSVExporter(){
        super();
        delim = ",";
    }

    // - Helpers - //

    @Override
    protected String processHeader(){

        ArrayList<String> lsElements = new ArrayList<String>();
        lsElements.addAll(this.lsUniqueElementNames);

        String toReturn = ""; int i;
        for (i = 0; i < lsElements.size() - 1; i++)
            toReturn += lsElements.get(i) + ",";

        return toReturn + lsElements.get(i) + "\n";
    }

    @Override
    protected String processItemHeader(Item item){
        String toReturn = "\"" + item.id + "\",";

        List<String> filenames = item.lsFilenames; int i;
        toReturn += "\"";
        for(i = 0; i < filenames.size()-1; i++)
            toReturn += filenames.get(i) + "||";

        return toReturn + filenames.get(i) + "\",";

    }

    @Override
    protected String processElement(Element e, Boolean isLast){
        String toReturn = "";

        if(isLast == null) {
            toReturn += "\"";
            isLast = false;
        }
        toReturn += e == null? "" : e.getValue();
        return isLast? toReturn + "\"," : toReturn + "||";
    }

    @Override
    public File publish(File directory, String filename) {
        return null;
    }
}
