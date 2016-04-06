package dc_metadata;

/**
 * Abstract class for a Type element.
 */
public class Type extends Element{

    //acceptable values
    public enum value {
        ABSTRACT, ANIMATION, ARTICLE,
        BLOG, BOOK, BOOK_CHAPTER, BOOK_REVIEW,
        CAPSTONE, CASE_STUDY,
        DATASET, DISSERTATION,
        FINAL_PAPER,
        IMAGE, IMAGE_3D,
        JOURNAL,
        LEARNING_OBJECT,
        MACROMEDIA_FLASH, MAGAZINE, MAP, MASTERS_PROJECT, MINUTES, MONOGRAPH, MUSICAL_SCORE,
        NEWSLETTER, NEWSPAPER,
        PLAN_OR_BLUEPRINT, PODCAST, POLICY, POSTPRINT, PREPRINT, PRESENTATION, PROCEEDINGS,
        RECORDING_ACOUSTICAL, RECORDING_MUSICAL, RECORDING_ORAL, REPORT,
        SENIOR_PROJECT, SOFTWARE,
        TECHNICAL_REPORT, THESIS,
        VIDEO,
        WEBSITE, WORKING_PAPER
    }

    /**
     * private constructor used to set the type header's
     */
    private Type(){

        uri = "http://purl.org/dc/elements/1.1/type";
        name = "type";
        label = "Type";
        definiton = "The nature or genre of the content of the resource";
    }


    /**
     * Create a 'Type' Element of
     */
    public Type(value v){
        this();

        switch (v){
            case MAGAZINE: this.qualifier = "Magazine"; break;
            default:
                this.qualifier = (v.toString()).replace(" ","");
        }

    }
}
