package dc_metadata;

/**
 * Representation for a Contributor element.
 */
public class Contributor extends Element {

    // - dc.contributor.* || contributor role codes - //
    public enum qualifier {
        ACTOR,
        ADVISOR, ADVISOR_CHAIR, ADVISOR_DEPT_CHAIR,
        ARTIST,
        AUTHOR,
        DESIGNER,
        DIRECTOR,
        EDITOR, EDITOR_ART, EDITOR_CAMPUS, EDITOR_COPY, EDITOR_DEPUTY_MANAGING, EDITOR_EXECUTIVE,
          EDITOR_FEATURE, EDITOR_GRAPHIC_ARTS, EDITOR_NEWS, EDITOR_SPORTS, EDITOR_MANAGING,
          EDITOR_PHOTO, EDITOR_PHOTO_ASSISTANT,
        ILLUSTRATOR,
        MANAGER, MANAGER_ADVERTISING, MANAGER_BUSINESS, MANAGER_PHOTO,
        ORGANIZATION,
        OTHER,
        PERFORMER,
        PHOTOGRAPHER,
        PRODUCER,
        REPORTER
    }

    /**
     * Set the dublin core standard information common to all Contributor elements
     */
    private Contributor(){
        uri = "http://purl.org/dc/elements/1.1/contributor";
        name = "contributor";
        label = "Contributor";
        definition = "An entity responsible for making contributions to the resource.";
    }

    /**
     * Set the qualifier and value information given the inputs
     */
    public Contributor(qualifier q, String value){
        this();
        this.value = value;
        this.qualifier = getQualifierName(q);
    }

    private static String getQualifierName(qualifier q){

        switch(q){
            case ACTOR:              return "actor";
            case ADVISOR:            return "advisor";
            case ADVISOR_CHAIR:      return "advisorChair";
            case ADVISOR_DEPT_CHAIR: return "advisorDeptChair";
            case ARTIST:             return "artist";
            case AUTHOR:             return "author";
            case DESIGNER:           return "designer";
            case EDITOR:             return "editor";
            case EDITOR_ART:         return "ArtEditor";
            case EDITOR_COPY:        return "CopyEditor";
            case EDITOR_EXECUTIVE:
            case EDITOR_MANAGING:    return "ManagingEditor";
            case EDITOR_FEATURE:     return "FeatureEditor";
            case EDITOR_NEWS:        return "NewsEditor";
            case EDITOR_SPORTS:      return "SportsEditor";
            case ILLUSTRATOR:        return "illustrator";
            case MANAGER_BUSINESS:   return "BusinessManager";
            case PHOTOGRAPHER:       return "photographer";
            default:
                return "other";
        }
    }

}
