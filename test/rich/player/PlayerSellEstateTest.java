package rich.player;

import org.junit.Before;
import org.junit.Test;
import rich.Player;
import rich.environment.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
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
    private double income;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        estate = mock(Estate.class);
        startPoint = mock(Estate.class);
        map = mock(Map.class);
        when(((Estate)estate).getPrice()).thenReturn(BASE_PRICE);
        when(((Estate)estate).getLevel()).thenReturn(Estate.Level.TWO);
        income = ((Estate)estate).getPrice() * (((Estate)estate).getLevel().ordinal() + 1) * 2;
        when(map.sellEstate(any(), anyInt())).thenReturn(estate);
        player = Player.createPlayerWithEstate(1, map, dice, startPoint, START_BALANCE, estate);
    }

    @Test
    public void should_wait_command_after_sell_estate() throws Exception {
        player.sell(1);

        assertThat(player.getStatus(), is(Player.Status.WAIT_COMMAND));
    }

    @Test
    public void should_increase_and_change_owner_when_sell_estate() throws Exception {
        int estateSum = player.getLands().size();
        assertThat(estateSum, is(1));
        player.sell(1);

        assertThat(player.getLands().size(), is(estateSum - 1));
        assertThat(player.getBalance(), is(START_BALANCE + income));
    }

    @Test
    public void should_not_sell_not_owned_estate() throws Exception {
        when(map.sellEstate(any(), anyInt())).thenReturn(null);
        int estateSum = player.getLands().size();
        player = Player.createPlayerWithEstate(1, map, dice, startPoint, START_BALANCE, estate);

        assertThat(estateSum, is(1));

        player.sell(1);

        assertThat(player.getLands().size(), is(estateSum));
        assertThat(player.getBalance(), is(START_BALANCE));
    }
}
