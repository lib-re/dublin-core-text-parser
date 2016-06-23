package dc_metadata;

/**
 * Abstract representation of a DublinCore Element
 */
public abstract class Element implements Comparable{

    //data elements pertaining to Dublin Core Documentation
    protected String uri = "";
    protected String name = "";
    protected String label = "";
    protected String definition = "";
    protected String encoding = "";

    //data elements relating to particular entry
    protected String qualifier = "";
    protected String value = "";

    /** print out 'dc.{element_name}[.{qualifier_name}]: {value}' */
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
        toReturn += (encoding.isEmpty()? "" : " scheme=\"dcterms:"+encoding+"\" "); //TODO check

        return toReturn + ">" + value + "</dcvalue>";
    }


    // - getters and setters - //

    /* - qualifier - */
    public String getQualifier() { return this.qualifier; }

    public String getName(){ return this.name; }

    public String getValue(){ return this.value; }

    public int compareTo(Object o) {

        String x1 = ((Element) o).getName();
        int sComp = (name.toLowerCase()).compareTo(x1.toLowerCase());

        if (sComp != 0) {
            return sComp;
        } else {
            String x2 = ((Element) o).getQualifier();
            sComp = (qualifier.toLowerCase()).compareTo(x2.toLowerCase());

            if(sComp != 0){
                return sComp;
            }else{
                String x3 = ((Element) o).getValue();
                return (value).compareTo(x3);
            }
        }
    }
}
