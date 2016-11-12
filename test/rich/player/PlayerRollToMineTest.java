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

    private static final int START_POINTS = 100;
    private static final int MINE_POINTS = 20;
    private Land startPoint;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        startPoint = mock(Land.class);
        minePoint = mock(Mine.class);
        map = new GameMap(startPoint, minePoint);

        when(dice.next()).thenReturn(1);
    }

    @Test
    public void should_end_turn_after_roll_to_mine() throws Exception {
        Player player = Player.createPlayerWithStart(1,map,dice,startPoint);

        player.roll();

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_add_points_when_roll_to_mine() throws Exception {
        minePoint = new Mine(MINE_POINTS);
        map = new GameMap(startPoint, minePoint);
        Player player = Player.createPlayerWithPoint(1, map, dice ,startPoint, START_POINTS);

        player.roll();

        assertThat(player.getPoints(), is(START_POINTS + MINE_POINTS));

    }
}
