package rich;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerRollToEmptyTest {


    private GameMap map;
    private Dice dice;
    private Land startPoint;
    private Land emptyLand;

    @Before
    public void setUp() throws Exception {
        map = mock(GameMap.class);
        dice = mock(Dice.class);
        startPoint = mock(Land.class);
        emptyLand = mock(Land.class);
        when(emptyLand.getOwner()).thenReturn(null);
        when(dice.next()).thenReturn(1);
        when(map.move(eq(startPoint), eq(1))).thenReturn(emptyLand);
    }

    @Test
    public void should_wait_response_after_roll_to_empty() throws Exception {
        Player player = Player.createPlayerWithStart(1, map, dice, startPoint);

        player.roll();

        assertThat(player.getStatus(), is(Player.Status.WAIT_RESPONSE));
    }

    @Test
    public void should_end_turn_when_say_no_at_empty() throws Exception {
        Player player = Player.createPlayerWithStart(1, map, dice, startPoint);

        player.roll();
        player.sayNo();

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }
}
