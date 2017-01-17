package export;

import dc_metadata.Element;
import main.Item;

/**
 * Simple default text printer outputing each
 */
public class TXTExporter extends main.Exporter {

    @Override
    protected String processItemHeader(Item item){
        String toReturn = "---- Item " + item.id + ": ";

        //list filenames
        int i = 0;
        for(i = 0; i < item.lsFilenames.size()-1; i++){
            toReturn += item.lsFilenames.get(i) + ", ";
        } toReturn += item.lsFilenames.get(i);

        return toReturn + " ----\n";
    }

    @Override
    protected String processItemFooter(Item item){
        return "---- End of Item " + item.id + " ----\n\n";
    }

    @Override
    protected String processElement(Element e, Boolean isLast) {
        return "-> " + e.toString() + "\n";
    }

}
