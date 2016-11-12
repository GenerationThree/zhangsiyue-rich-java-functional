package rich;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerRollOtherEstateTest {

    private GameMap map;
    private Dice dice;
    private Land startPoint;
    private Land otherEstate;
    private static final double START_BALANCE = 10000;
    private static final double ESTATE_PRICE = 200;
    private static final Estate.Level ESTATE_LEVEL = Estate.Level.ZERO;
    private Player player;
    private Player otherPlayer;
    private double fee;

    @Before
    public void setUp() throws Exception {
        map = mock(GameMap.class);
        dice = mock(Dice.class);
        startPoint = mock(Land.class);
        otherPlayer = new Player(2, map, dice);
        otherEstate = Estate.createEstateWithLevel(otherPlayer, ESTATE_PRICE, ESTATE_LEVEL);
        player = Player.createPlayerWithBalance(1, map, dice, startPoint, START_BALANCE);
        fee = ESTATE_PRICE * ESTATE_LEVEL.getTimes();

        when(dice.next()).thenReturn(1);
        when(map.move(eq(startPoint), eq(1))).thenReturn(otherEstate);
    }

    @Test
    public void should_pay_fee_when_roll_to_other_estate () throws Exception {
        player.roll();

        assertThat(player.getBalance(), is(START_BALANCE - fee));
    }

    @Test
    public void should_not_pay_when_free_for_fee_at_other_estate() throws Exception {
        Player playerFree = Player.createPlayerFreeForFee(3, map, dice, startPoint, START_BALANCE);
        playerFree.roll();

        assertThat(player.getBalance(), is(START_BALANCE));
    }

    @Test
    public void should_end_game_without_enough_balance_to_pay_fee_at_other_estate() throws Exception {
        Player playerWithoutEnoughBalance = Player.createPlayerWithBalance(1, map, dice, startPoint, fee-1);
        playerWithoutEnoughBalance.roll();

        assertThat(playerWithoutEnoughBalance.getStatus(), is(Player.Status.END_GAME));
    }
}