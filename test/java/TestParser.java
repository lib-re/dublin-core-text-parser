import org.junit.Test;
import org.junit.Assert;

/**
 * Ensure proper functioning of the Parser,
 */
public class TestParser {

    Parser.mode m = Parser.mode.NULL;

    @Test
    public void testModeSwitches(){

        m = Parser.mode.ARTICLES;
        Assert.assertEquals(m,Parser.determineMode("-TABLE OF CONTENTS-"));
        Assert.assertEquals(m,Parser.determineMode("-TABLE_OF_CONTENTS-"));
        Assert.assertEquals(m,Parser.determineMode("-CONTENTS-"));
        Assert.assertEquals(m,Parser.determineMode("-ARTICLES-"));
        Assert.assertEquals(m,Parser.determineMode("-INDEX-"));

        m = Parser.mode.CONTRIBUTORS;
        Assert.assertEquals(m,Parser.determineMode("-CONTRIBUTORS-"));
        Assert.assertEquals(m,Parser.determineMode("-COLLABORATORS-"));
        Assert.assertEquals(m,Parser.determineMode("-CONTRIBUTING-"));

        m = Parser.mode.SUBJECT;
        Assert.assertEquals(m,Parser.determineMode("-SUBJECT-"));
        Assert.assertEquals(m,Parser.determineMode("-TAGS-"));
        Assert.assertEquals(m,Parser.determineMode("-KEYWORDS-"));
        Assert.assertEquals(m,Parser.determineMode("-TOPICS-"));

    }

}
