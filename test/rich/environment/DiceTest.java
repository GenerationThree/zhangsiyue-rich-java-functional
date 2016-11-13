package rich.environment;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DiceTest {
    @Test
    public void should_generate_int_between_one_and_six() throws Exception {
        Dice dice = new GameDice();

        int num = dice.next();
        assertThat(num >=1 && num <=6, is(true));

    }
}
