package dc_metadata;

/**
 * Abstract representation of a DublinCore Element
 */
public abstract class Element {

    //data elements pertaining to Dublin Core Documentation
    protected String uri = "";
    protected String name = "";
    protected String label = "";
    protected String definition = "";

    //data elements relating to particular entry
    protected String qualifier = "";
    protected String value = "";

    /** print out 'dc.{element_name}.{qualifier_name}: {value}' */
    @Override
    public final String toString(){

        String toReturn = "dc." + name;
        toReturn += (qualifier.isEmpty())? "" : "." + qualifier;
        return toReturn + ": " + value;

    }

    /** print out helper text explaining the element (no qualifier) */
    public final String helpText(){
        return label + " | " + "dc." + name + " | " + definition;
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
