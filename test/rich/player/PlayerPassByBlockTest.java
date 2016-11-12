package rich.player;

import org.junit.Before;
import org.junit.Test;
import rich.*;
import rich.environment.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerPassByBlockTest {
    private Map map;
    private Dice dice;
    private Land startPoint;
    private Block block;
    private Land endPoint;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        startPoint = mock(Land.class);
        block = new Block();
        endPoint = mock(Land.class);

        when(dice.next()).thenReturn(2);
    }

    @Test
    public void should_end_turn_when_pass_by_block() throws Exception {
        map = new GameMap(startPoint, block, endPoint);
        Player player = Player.createPlayerWithStart(1, map, dice, startPoint);

        player.roll();

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
        assertThat(player.getCurrent(), is(block));
    }
}
