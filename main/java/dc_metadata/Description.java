package dc_metadata;

/**
 * Representation of the dc.description element with qualifiers
 */
public class Description extends Element {

    public enum qualifiers{
        ABSTRACT,
        APPROVAL,
        AUDIENCE,
        AUTHOR,
        CAMPUS,
        COLLEGE,
        DEFENSE,
        DEPARTMENT,
        EXTENT,
        NOTE,
        OTHER,
        PROGRAM,
        PROVENANCE,
        SCHOOL,
        SPONSORSHIP,
        STATEMENT_OF_RESPONSIBILITY,
        TABLE_OF_CONTENTS,
        URI
    }

    private Description(){
        uri = "http://purl.org/dc/elements/1.1/description";
        name = "description";
        label = "Description";
        definiton = "An account of the resource.";
    }

    public Description(String description){
        this();
        this.value = description;
    }


    public Description(qualifiers q, String description){
        this(description);
        this.qualifier = q.toString().replace(" ", "");
    }

}
