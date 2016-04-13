package dc_metadata;

/**
 * Class for a Title element
 */
public class Title extends Element {

    //alternative, article, series
    public enum qualifier {
        ALTERNATIVE,
        ARTICLE,
        SERIES
    }


    /**
     * Create an empty title object populated with dublin core metadata standards info
     */
    private Title(){
        uri = "http://purl.org/dc/elements/1.1/title";
        name = "title";
        label = "Title";
        definition = "A name given to the resource.";
    }

    /**
     * Create a title with a given passed in value.
     */
    public Title(String title){
        this();
        this.value = title;
    }

    /**
     * Create Title with qualifier
     */
    public Title(qualifier q, String altTitle){
        this(altTitle);

        switch (q){
            case ALTERNATIVE: this.qualifier = "alternative"; break;
            case ARTICLE:     this.qualifier = "article";     break;
            case SERIES:      this.qualifier = "series";      break;
            default:          this.qualifier = "";
        }
    }

}
