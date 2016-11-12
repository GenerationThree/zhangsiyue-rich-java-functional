package rich;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerRollToEmptyTest {
    @Test
    public void should_wait_response_after_roll_to_empty() throws Exception {
        GameMap map = mock(GameMap.class);
        Dice dice = mock(Dice.class);
        Land startPoint = mock(Land.class);
        Land emptyLand = mock(Land.class);
        when(emptyLand.getOwner()).thenReturn(null);
        when(dice.next()).thenReturn(1);
        Player player = Player.createPlayerWithStart(1, map, dice, startPoint);
        when(map.move(eq(startPoint), eq(1))).thenReturn(emptyLand);

        player.roll();

        assertThat(player.getStatus(), is(Player.Status.WAIT_RESPONSE));
    }
}
