import com.mygdx.runrunrun.sprites.Hero;
import com.mygdx.runrunrun.Main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class UnitTests
{
        Hero hero_test;

        @Before
        public void setUp(){
            hero_test = new Hero(0,0,null);

        }

        @Test
        public void checkHealth(){
           assertEquals("This should return 3",3,hero_test.getHealth_counter());
        }

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