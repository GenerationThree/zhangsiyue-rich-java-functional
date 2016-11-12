package rich.player;

import org.junit.Before;
import org.junit.Test;
import rich.Player;
import rich.environment.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerRollToPrisonTest {
    private Map map;
    private Dice dice;
    private Land startPoint;
    private Land endPoint;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        startPoint = mock(Land.class);
        endPoint = new Prison();

        when(dice.next()).thenReturn(1);
    }

    @Test
    public void should_end_turn_and_wait_two_turns_when_roll_to_prison() throws Exception {
        map = new GameMap(startPoint, endPoint);
        Player player = Player.createPlayerWithStart(1, map, dice, startPoint);

        player.roll();

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
        assertThat(player.getWaitTurn(), is(2));
    }
}
