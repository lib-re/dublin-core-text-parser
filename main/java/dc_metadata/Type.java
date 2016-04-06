package dc_metadata;

/**
 * Created by admin on 4/5/16.
 */
public abstract class Type extends Element{

    //acceptable element types
    public enum qualifier {
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

    public Type(){

        uri = "http://purl.org/dc/elements/1.1/type";
        name = "type";
        label = "Type";
        definiton = "The nature or genre of the content of the resource";
        comment = "Recommended best practice is to use a controlled vocabulary " +
                "such as the DCMI Type Vocabulary [DCMITYPE]. To describe the " +
                "file format, physical medium, or dimensions of the resource, " +
                "use the Format element.";
    }

    public Type(qualifier q){
        this();
        value = q.toString();
    }
}
