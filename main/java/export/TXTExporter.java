package export;

import com.sun.deploy.util.StringUtils;
import dc_metadata.Element;
import main.Item;

/**
 * Simple default text printer outputing each
 */
public class TXTExporter extends main.Exporter {

    @Override
    protected String processItemHeader(Item item){
        return String.format("---- Item %d: %s ----", item.id, StringUtils.join(item.lsFilenames, ", "));
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
