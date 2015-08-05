import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;

public class Tester
{
        @Test
        public void thisAlwaysPasses()
        {
            assertTrue(true);
        }

        @Test
        @Ignore
        public void thisIsIgnored()
        {

        }
}