package rich.player;

import org.junit.Before;
import org.junit.Test;
import rich.Player;
import rich.environment.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerRollToMineTest {
    private Map map;
    private Dice dice;
    private Land minePoint;

    private static final int LOW_LIMIT_FOR_POINT = TollHouse.LOW_LIMIT;
    private static final int START_POINTS = 100;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        minePoint = mock(Mine.class);
        map = new GameMap(minePoint, minePoint);

        when(dice.next()).thenReturn(1);
    }

    @Test
    public void should_end_turn_when_roll_to_mine() throws Exception {
        Player player = new Player(1, map, dice);

        player.roll();

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }
}
