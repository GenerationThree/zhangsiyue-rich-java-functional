package rich.player;

import org.junit.Before;
import org.junit.Test;
import rich.*;
import rich.environment.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerRollOtherEstateTest {

    private Map map;
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
        map = mock(Map.class);
        dice = mock(Dice.class);
        startPoint = mock(Land.class);
        otherPlayer = Player.createPlayerStartTurn(2, map, dice, 0);
        otherEstate = Estate.createEstateWithLevel(otherPlayer, ESTATE_PRICE, ESTATE_LEVEL);
        player = Player.createPlayerWithBalance(1, map, dice, startPoint, START_BALANCE);
        fee = ESTATE_PRICE * ESTATE_LEVEL.getTimes();

        when(dice.next()).thenReturn(1);
        when(map.move(eq(startPoint), eq(1), any())).thenReturn(otherEstate);
    }

    @Test
    public void should_pay_fee_when_roll_to_other_estate () throws Exception {
        player.roll();

        assertThat(player.getBalance(), is(START_BALANCE - fee));
    }

    @Test
    public void should_not_pay_when_free_for_fee_at_other_estate() throws Exception {
        final int freeTurn = 1;
        Player playerFree = Player.createPlayerFreeForFee(3, map, dice, startPoint, START_BALANCE, freeTurn);

        System.out.println(playerFree.getFreeTurn());
        playerFree.roll();

        assertThat(playerFree.getBalance(), is(START_BALANCE));
    }

    @Test
    public void should_end_game_without_enough_balance_to_pay_fee_at_other_estate() throws Exception {
        Player playerWithoutEnoughBalance = Player.createPlayerWithBalance(1, map, dice, startPoint, fee-1);
        playerWithoutEnoughBalance.roll();

        assertThat(playerWithoutEnoughBalance.getStatus(), is(Player.Status.END_GAME));
    }

    @Test
    public void should_end_turn_after_pay_fee_at_other_estate() throws Exception {
        player.roll();

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_not_pay_fee_when_owner_is_in_hospital_at_other_estate() throws Exception {
        otherPlayer = mock(Player.class);
        Land hospital = mock(Hospital.class);
        when(otherPlayer.getCurrent()).thenReturn(hospital);
        otherEstate = Estate.createEstateWithLevel(otherPlayer, ESTATE_PRICE, ESTATE_LEVEL);
        when(map.move(eq(startPoint), eq(1), any())).thenReturn(otherEstate);

        player.roll();
        assertThat(player.getBalance(), is(START_BALANCE));
    }

    @Test
    public void should_not_pay_fee_when_owner_is_in_prison_at_other_estate() throws Exception {
        otherPlayer = mock(Player.class);
        Land prison = mock(Prison.class);
        when(otherPlayer.getCurrent()).thenReturn(prison);
        otherEstate = Estate.createEstateWithLevel(otherPlayer, ESTATE_PRICE, ESTATE_LEVEL);
        when(map.move(eq(startPoint), eq(1), any())).thenReturn(otherEstate);

        player.roll();
        assertThat(player.getBalance(), is(START_BALANCE));
    }
}