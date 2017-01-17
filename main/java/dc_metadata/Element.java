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
    protected Object value = "";

    /** print out 'dc.{element_name}[.{qualifier_name}]: {value}' */
    @Override
    public final String toString(){
        return this.getQualifiedName() + ": " + this.getValue();
    }

    // - getters and setters - //

    public String getQualifier() { return this.qualifier; }

    public String getQualifiedName() {
        String toReturn = "dc." + name;
        toReturn += (qualifier.isEmpty())? "" : "." + qualifier;
        return toReturn;
    }

    public String getName(){ return this.name; }

    public String getValue(){ return this.value.toString(); }


    // - helpers - //

    /** compareTo function for use in sorting.
     *    [sort order: E.name -> E.qualifier -> E.value]
     */
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
                return (this.getValue()).compareTo(x3);
            }
        }
    }
}
