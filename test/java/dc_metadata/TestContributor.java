package dc_metadata;

import org.junit.Assert;
import org.junit.Test;
/**
 * Test that Contributor class properly sorts common roles
 */
public class TestContributor {

    String q = "";

    @Test
    public void testExecutiveEditor(){
        q = Contributor.EDITOR_EXECUTIVE;
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR IN CHIEF"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR-IN-CHIEF"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EXECUTIVE EDITOR"));
    }

    @Test
    public void testOtherEditors(){

        q = Contributor.EDITOR_ART;
        Assert.assertEquals(q, Contributor.determineContributorQualifier("ART EDITOR"));
        Assert.assertEquals(q,Contributor.determineContributorQualifier("EDITOR, ART"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR - ART"));
        Assert.assertEquals(q,Contributor.determineContributorQualifier("EDITOR, ARTS"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR - ARTS"));

        q = Contributor.EDITOR_CAMPUS;
        Assert.assertEquals(q, Contributor.determineContributorQualifier("CAMPUS EDITOR"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR, CAMPUS"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR - CAMPUS"));

        q = Contributor.EDITOR_COPY;
        Assert.assertEquals(q, Contributor.determineContributorQualifier("COPY EDITOR"));
        Assert.assertEquals(q,Contributor.determineContributorQualifier("EDITOR, COPY"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR - COPY"));

        q = Contributor.EDITOR_FEATURE;
        Assert.assertEquals(q, Contributor.determineContributorQualifier("FEATURE EDITOR"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("FEATURES EDITOR"));
        Assert.assertEquals(q,Contributor.determineContributorQualifier("EDITOR, FEATURES"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR - FEATURES"));
        Assert.assertEquals(q,Contributor.determineContributorQualifier("EDITOR, FEATURE"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR - FEATURE"));

        q = Contributor.EDITOR_GRAPHIC_ART;
        Assert.assertEquals(q,Contributor.determineContributorQualifier("GRAPHIC ART EDITOR"));
        Assert.assertEquals(q,Contributor.determineContributorQualifier("GRAPHIC ARTS EDITOR"));
        Assert.assertEquals(q,Contributor.determineContributorQualifier("GRAPHICS EDITOR"));
        Assert.assertEquals(q,Contributor.determineContributorQualifier("EDITOR, GRAPHICS"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR - GRAPHICS"));
        Assert.assertEquals(q,Contributor.determineContributorQualifier("EDITOR, GRAPHIC ARTS"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR - GRAPHIC ARTS"));

        q = Contributor.EDITOR_MANAGING;
        Assert.assertEquals(q, Contributor.determineContributorQualifier("MANAGING EDITOR"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR - MANAGING"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR, MANAGING"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR - MANAGER"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR, MANAGER"));

        q = Contributor.EDITOR_PHOTO;
        Assert.assertEquals(q, Contributor.determineContributorQualifier("PHOTO EDITOR"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("PHOTOGRAPHY EDITOR"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("PHOTOGRAPH EDITOR"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR OF PHOTOGRAPHY"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR - PHOTO"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR, PHOTO"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR - PHOTOGRAPHY"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR, PHOTOGRAPHY"));

        q = Contributor.EDITOR_PHOTO_ASST;
        Assert.assertEquals(q, Contributor.determineContributorQualifier("ASSISTANT EDITOR OF PHOTOGRAPHY"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("ASSISTANT TO THE EDITOR OF PHOTOGRAPHY"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("ASSISTANT PHOTOGRAPHY EDITOR"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("ASSISTANT PHOTO EDITOR"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("PHOTOGRAPHY EDITOR ASSISTANT"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("PHOTOGRAPHY EDITOR ASST"));

        q = Contributor.EDITOR_SPORTS;
        Assert.assertEquals(q, Contributor.determineContributorQualifier("SPORTS EDITOR"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR - SPORTS"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR, SPORTS"));

        q = Contributor.EDITOR_NEWS;
        Assert.assertEquals(q, Contributor.determineContributorQualifier("NEWS EDITOR"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR - NEWS"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR, NEWS"));

        q = Contributor.EDITOR_DEPUTY_MAN;
        Assert.assertEquals(q, Contributor.determineContributorQualifier("DEPUTY MANAGING EDITOR"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR - MANAGING ASSISTANT"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR, MANAGING ASSISTANT"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR - MANAGING DEPUTY"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("EDITOR, MANAGING DEPUTY"));
    }

    @Test
    public void testManaging(){
        q = Contributor.MANAGER_ADVERTIS;
        Assert.assertEquals(q, Contributor.determineContributorQualifier("ADVERTISING MANAGER"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("AD MANAGER"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("MANAGER - ADVERTISING"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("MANAGER, ADVERTISING"));
    }

    @Test
    public void testOther(){

        q = Contributor.AUTHOR;
        Assert.assertEquals(q, Contributor.determineContributorQualifier("WRITER"));
        Assert.assertEquals(q, Contributor.determineContributorQualifier("AUTHOR"));


    }


}
