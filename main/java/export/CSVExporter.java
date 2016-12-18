package export;

import main.Collection;
import main.Item;
import java.io.File;

/**
 * Export to CSV file for use with `DSpace/SAFBuildier`
 */
public class CSVExporter extends main.Exporter{

    @Override
    protected String processItem(Item i) {
        return null;
    }

    @Override
    public File publish(File directory, String filename) {
        return null;
    }
}
