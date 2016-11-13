package rich.player;

import org.junit.Before;
import org.junit.Test;
import rich.Player;
import rich.environment.Dice;
import rich.environment.Land;
import rich.environment.Map;
import rich.environment.Tool;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class PlayerBlockTest {
    private Map map;
    private Dice dice;
    private Player player;
    private Land startPoint;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(Map.class);
        startPoint = mock(Land.class);
        player = Player.createPlayerWithTool(1, map, dice, startPoint, new Tool(Tool.Type.BLOCK));
    }

    @Test
    public void should_wait_command_after_use_block() throws Exception {
        player.useBlock();

        assertThat(player.getStatus(), is(Player.Status.WAIT_COMMAND));

    }
}
