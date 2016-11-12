package rich.player;

import org.junit.Before;
import org.junit.Test;
import rich.Player;
import rich.environment.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerRollToGiftHouseTest {
    private Map map;
    private Dice dice;
    private Land startPoint;
    private Land giftHouse;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        startPoint = mock(Land.class);
        giftHouse = mock(GiftHouse.class);
        map = new GameMap(startPoint, giftHouse);

        when(dice.next()).thenReturn(1);
    }
    
    @Test
    public void should_wait_response_when_roll_to_gift_house() throws Exception {
        Player player = Player.createPlayerWithStart(1, map, dice, startPoint);

        player.roll();

        assertThat(player.getStatus(), is(Player.Status.WAIT_RESPONSE));
    }

    @Test
    public void should_end_turn_after_select_gift() throws Exception {
        Player player = Player.createPlayerWithStart(1, map, dice, startPoint);

        player.roll();
        player.selectGift(1);

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }
}
