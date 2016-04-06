package dc_metadata;

/**
 * Abstract representation of a DublinCore Element
 */
public abstract class Element {

    //data elements pertaining to Dublin Core Documentation
    protected String uri = "";
    protected String name = "";
    protected String label = "";
    protected String definiton = "";

    //data elements relating to particular entry
    protected String qualifier = "";
    protected String value = "";

    @Override
    public String toString(){
        return "dc." + name + " | " + label + ": " + definiton;
    }

    /**
     * returns a line of xml that describes that element/qualifier/value pair
     */
    public String XMLExport(){
        String toReturn = "<dcvalue element=\"" + name + "\"";
        toReturn += (qualifier.isEmpty())? "" : "qualifier=\"" + qualifier + "\"";
        return toReturn + ">" + value + "</dcvalue>";
    }


    // - getters and setters - //

    /* - qualifier - */
    public String getQualifier() { return this.qualifier; }

    /* - value - */
    public String getValue() { return this.value; }

}
