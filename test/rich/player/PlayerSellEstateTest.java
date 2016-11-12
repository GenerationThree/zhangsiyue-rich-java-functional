package rich.player;

import org.junit.Before;
import org.junit.Test;
import rich.Player;
import rich.environment.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerSellEstateTest {
    private Map map;
    private Dice dice;
    private Land estate;
    private Land startPoint;
    private Player player;
    private static final double START_BALANCE = 10000;
    private static final double BASE_PRICE = 200;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        estate = mock(Estate.class);
        startPoint = mock(Estate.class);
        map = new GameMap(startPoint, estate);
        player = Player.createPlayerWithBalance(1, map, dice, startPoint, START_BALANCE);

        when(((Estate)estate).getOwner()).thenReturn(player);
        when(((Estate)estate).getPrice()).thenReturn(BASE_PRICE);
        when(((Estate)estate).getLevel()).thenReturn(Estate.Level.TWO);
    }

    @Test
    public void should_wait_command_after_sell_estate() throws Exception {
        player.sell(1);

        assertThat(player.getStatus(), is(Player.Status.WAIT_COMMAND));

    }
}
