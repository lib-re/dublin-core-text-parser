package dc_metadata;

/**
 * Representation for a Relation
 */
public class Relation extends Element {

    //alternative, article, series
    private static final String URI          = "uri";
    private static final String CONFORMSTO   = "conformsTo";
    private static final String ISFORMATOF   = "isFormatOf";
    private static final String HASFORMAT    = "hasFormat";
    private static final String ISPARTSERIES = "isPartOfSeries";
    private static final String ISPARTOF     = "isPartOf";
    private static final String HASPART      = "hasPart";
    private static final String REFERENCEDBY = "isReferencedBy";
    private static final String REFERENCES   = "references";
    private static final String ISREQUIREDBY = "isRequriedBy";
    private static final String REQUIRES     = "requires";
    private static final String ISREPLACEDBY = "isReplacedBy";
    private static final String REPLACES     = "replaces";
    private static final String ISVERSIONOF  = "isVersionOf";
    private static final String HASVERSION   = "hasVersion";

    private Relation(){
        uri = "http://purl.org/dc/elements/1.1/relation";
        name = "relation";
        label = "Relation";
        definition = "A related resource.";
    }

    public static Relation createRelation(String qualifierText, String value){
        Relation r = new Relation();
        r.value = value;
        r.qualifier = determineQualifier(qualifierText);
        return r;
    }

    private static String determineQualifier(String str){
        str = str.trim().replace("_","").toLowerCase();

        if(str.isEmpty()){ return ""; }
        else if(str.contains("version")){
            return str.contains("has")? HASVERSION : ISVERSIONOF;
        }else if(str.contains("replace")){
            return str.contains("dby")? ISREPLACEDBY : REPLACES;
        }else if(str.contains("require")){
            return str.contains("dby")? ISREQUIREDBY : REQUIRES;
        }else if(str.contains("reference")){
            return str.contains("dby")? REFERENCEDBY : REFERENCES;
        }else if(str.contains("part")){
            if(str.contains("partof")){
                return str.contains("series")? ISPARTSERIES : ISPARTOF;
            }else{
                return HASPART;
            }
        }else if(str.contains("format")){
            return str.contains("has")? HASFORMAT : ISFORMATOF;
        }else if(str.contains("conform")){ return CONFORMSTO; }
        else if(str.contains("uri")){ return URI; }
        else{ return ""; }
    }

}
