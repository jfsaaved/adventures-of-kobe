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
            int jump_bool_counter = 0;

            float try_time = 1000f;

            while(try_time >=0) {
                hero_test.jump();
                hero_test.update(0);
                hero_test.jump();
                hero_test.update(0);
                if(hero_test.isJumping() == true) {
                    jump_bool_counter++;
                }
                try_time--;
            }
            assertEquals("This should return 0 ",0,jump_bool_counter);
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