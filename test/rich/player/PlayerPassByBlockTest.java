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
    private Land blockPoint;
    private Land endPoint;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        startPoint = mock(Land.class);
        blockPoint = mock(Land.class);
        endPoint = mock(Land.class);

        when(dice.next()).thenReturn(2);
    }

    @Test
    public void should_stop_when_pass_by_block() throws Exception {
        map = GameMap.createGameMapWithBlock(1, startPoint, blockPoint, endPoint);
        Player player = Player.createPlayerWithStart(1, map, dice, startPoint);

        player.roll();

        assertThat(player.getCurrent(), is(blockPoint));
    }
}
