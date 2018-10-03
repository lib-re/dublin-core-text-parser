package export;

import com.sun.deploy.util.StringUtils;
import dc_metadata.Element;
import main.Item;

import java.io.File;

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
        return StringUtils.join(this.lsUniqueElementNames,",") + "\n";
    }

    @Override
    protected String processItemHeader(Item item){
        String filenames = StringUtils.join(item.lsFilenames, "||");
        return String.format("%d,\"%s\",", item.id, filenames);

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
