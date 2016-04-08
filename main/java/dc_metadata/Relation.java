package dc_metadata;

/**
 * Representation for a Relation
 */
public class Relation extends Element {

    public enum qualifier{
        CONFORMS_TO,
        HAS_FORMAT, HAS_PART, HAS_VERSION,
        IS_FORMAT_OF, IS_PART_OF, IS_REFERENCED_BY, IS_REPLACED_BY, IS_REQUIRED_BY, IS_VERSION_OF,
        REFERENCES,
        REPLACES,
        REQUIRES,
        SOURCE,
    }

    private Relation(){
        uri = "http://purl.org/dc/elements/1.1/relation";
        name = "relation";
        label = "Relation";
        definiton = "A related resource.";
    }

    public Relation(String value){
        this();
        this.value = value;
    }

    public Relation(qualifier q, String value){
        this(value);
        switch(q){
            case IS_PART_OF: this.qualifier = "ispartofseries"; break;
            default:
                this.qualifier = q.toString().replace(" ","");
        }
    }

}
