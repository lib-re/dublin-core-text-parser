package dc_metadata;

/**
 * Abstract representation of a DublinCore Element
 */
public abstract class Element {

    public String uri = "";
    public String name = "";
    public String label = "";
    public String definiton = "";
    public String comment = "";

    public String value = "";

    @Override
    public String toString(){
        return "dc." + name + " | " + label + ": " + definiton;
    }

    public String XMLExport(){
        return "<dcvalue element=\"" + name + "\">" + value + "</dcvalue>";
    }


}
