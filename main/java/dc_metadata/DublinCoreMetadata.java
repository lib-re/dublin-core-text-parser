package dc_metadata;

/**
 * Contains classifications, interfaces, and enums for dublin core metadata elements
 */
public class DublinCoreMetadata {

    // - dc.type = * - //
    public enum TYPE {
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

    // - dc.language = *  || Dublin core langauge codes

    // - dc.contributor.* || contributor role codes - //
    public enum CONTRIBUTOR_ROLES {
        ADIVSOR,
        ARTIST,
        AUTHOR,
        DESIGNER,
        EDITOR,
        EDITOR_ART,
        EDITOR_COPY,
        EDITOR_EXECUTIVE,
        EDITOR_FEATURE,
        EDITOR_NEWS,
        EDITOR_SPORTS,
        EDITOR_MANAGING,
        EDITOR_PHOTO,
        ILLUSTRATOR,
        MANAGER,
        MANAGER_BUSINESS,
        PHOTOGRAPHER
    }
}
