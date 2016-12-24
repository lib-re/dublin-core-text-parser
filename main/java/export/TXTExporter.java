package export;

import main.Collection;
import main.Item;

/**
 * Simple default text printer outputing each
 */
public class TXTExporter extends main.Exporter {

    @Override
    protected String processItem(Item i) {
        return i.toString();
    }

    @Override
    public void processCollection(Collection c){
        super.processCollection(c);
    }

}
