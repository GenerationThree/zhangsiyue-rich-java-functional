package rich;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerRollToOwnedEstateTest {
    private GameMap map;
    private Dice dice;
    private Land startPoint;
    private Land ownedEstate;
    private static final double START_BALANCE = 10000;
    private static final double IN_BALANCE = 200;
    private static final double OUT_OF_BALANCE = 10001;
    private Player player;

    @Before
    public void setUp() throws Exception {
        map = mock(GameMap.class);
        dice = mock(Dice.class);
        startPoint = mock(Land.class);
        ownedEstate = mock(Land.class);
        player = Player.createPlayerWithBalance(1, map, dice, startPoint, START_BALANCE);
        when(ownedEstate.getOwner()).thenReturn(player);
        when(dice.next()).thenReturn(1);
        when(map.move(eq(startPoint), eq(1))).thenReturn(ownedEstate);
    }

    @Test
    public void should_wait_response_after_roll_to_owned_estate() throws Exception {
        player.roll();

        assertThat(player.getStatus(), is(Player.Status.WAIT_RESPONSE));
    }

    @Test
    public void should_end_turn_when_say_no_at_owned_estate() throws Exception {
        player.roll();
        player.sayNo();

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }
}
