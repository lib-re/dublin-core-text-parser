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
        definiton = "An entity responsible for making contributions to the resource.";
    }

    /**
     * Set the qualifier and value information given the inputs
     */
    public Contributor(qualifier q, String value){
        this();
        this.value = value;

        switch(q){
            case ACTOR:              this.qualifier = "actor";            break;
            case ADVISOR:            this.qualifier = "advisor";          break;
            case ADVISOR_CHAIR:      this.qualifier = "advisorChair";     break;
            case ADVISOR_DEPT_CHAIR: this.qualifier = "advisorDeptChair"; break;
            case ARTIST:             this.qualifier = "artist";           break;
            case AUTHOR:             this.qualifier = "author";           break;
            case DESIGNER:           this.qualifier = "designer";         break;
            case EDITOR:             this.qualifier = "editor";           break;
            case EDITOR_ART:         this.qualifier = "ArtEditor";        break;
            case EDITOR_COPY:        this.qualifier = "CopyEditor";       break;
            case EDITOR_EXECUTIVE:
            case EDITOR_MANAGING:    this.qualifier = "ManagingEditor";   break;
            case EDITOR_FEATURE:     this.qualifier = "FeatureEditor";    break;
            case EDITOR_NEWS:        this.qualifier = "NewsEditor";       break;
            case EDITOR_SPORTS:      this.qualifier = "SportsEditor";     break;
            case ILLUSTRATOR:        this.qualifier = "illustrator";      break;
            case MANAGER_BUSINESS:   this.qualifier = "BusinessManager";  break;
            case PHOTOGRAPHER:       this.qualifier = "photographer";     break;
            default:
                this.qualifier = "other";
        }

    }

}
