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

public class PlayerRollToMagicHouseTest {
    private Map map;
    private Dice dice;
    private Land startPoint;
    private Land magicHouse;

    @Before
    public void setUp() throws Exception {
        startPoint = mock(Land.class);
        magicHouse = mock(MagicHouse.class);
        map = mock(Map.class);
        dice = mock(Dice.class);
        when(dice.next()).thenReturn(1);
        when(map.move(any(), eq(1), any())).thenReturn(magicHouse);
    }

    @Test
    public void should_end_turn_when_roll_to_magic_house() throws Exception {
        Player player = Player.createPlayerStartTurn(1, map, dice, 0);

        player.roll();

        assertThat(player.getStatus(), is(Player.Status.END_TURN));

    }
}
