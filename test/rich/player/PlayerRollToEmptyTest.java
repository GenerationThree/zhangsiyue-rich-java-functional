package rich.player;

import org.junit.Before;
import org.junit.Test;
import rich.*;
import rich.environment.Dice;
import rich.environment.Estate;
import rich.environment.Land;
import rich.environment.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerRollToEmptyTest {

    private Map map;
    private Dice dice;
    private Land startPoint;
    private Land emptyLand;
    private static final double START_BALANCE = 10000;
    private static final double IN_BALANCE = 200;
    private static final double OUT_OF_BALANCE = 10001;

    @Before
    public void setUp() throws Exception {
        map = mock(Map.class);
        dice = mock(Dice.class);
        startPoint = mock(Land.class);
        emptyLand = new Estate(null, IN_BALANCE);
        when(((Estate)emptyLand).getOwner()).thenReturn(null);
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

    @Test
    public void should_buy_estate_when_say_yes_at_empty() throws Exception {
        Player player = Player.createPlayerWithBalance(1, map, dice, startPoint, START_BALANCE);
        Land emptyLandWithPrice = new Estate(null, IN_BALANCE);
        when(map.move(eq(startPoint), eq(1))).thenReturn(emptyLandWithPrice);
        player.roll();
        player.sayYes();

        assertThat(player.getLands().size(), is(1));
        assertThat(player.getBalance(), is(START_BALANCE - IN_BALANCE));
    }

    @Test
    public void should_not_buy_estate_without_enough_balance_when_say_yes_at_empty() throws Exception {
        Player player = Player.createPlayerWithBalance(1, map, dice, startPoint, START_BALANCE);
        Land emptyLandWithPrice = new Estate(null, OUT_OF_BALANCE);
        when(map.move(eq(startPoint), eq(1))).thenReturn(emptyLandWithPrice);
        player.roll();
        player.sayYes();

        assertThat(player.getLands().size(), is(0));
        assertThat(player.getBalance(), is(START_BALANCE));

    }

    @Test
    public void should_end_turn_after_say_yes_at_empty() throws Exception {
        Player player = Player.createPlayerWithBalance(1, map, dice, startPoint, START_BALANCE);
        Land emptyLandWithPrice = new Estate(null, IN_BALANCE);
        when(map.move(eq(startPoint), eq(1))).thenReturn(emptyLandWithPrice);
        player.roll();
        player.sayYes();

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }
}
