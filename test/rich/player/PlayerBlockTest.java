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
    private Tool block;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(Map.class);
        startPoint = mock(Land.class);
        block = new Tool(Tool.Type.BLOCK);
        player = Player.createPlayerWithTool(1, map, dice, startPoint, block);
    }

    @Test
    public void should_wait_command_after_use_block() throws Exception {
        player.useBlock();

        assertThat(player.getStatus(), is(Player.Status.WAIT_COMMAND));

    }

    @Test
    public void should_remove_block_after_use() throws Exception {
        int preToolSum = player.getTools().size();
        player.useBlock();

        assertThat(player.getTools().size(), is(preToolSum - 1));
    }

    @Test
    public void should_not_use_block_without_block() throws Exception {
        player = new Player(1, map, dice);
        int preToolSum = player.getTools().size();

        player.useBlock();

        assertThat(player.getTools().size(), is(preToolSum));
    }
}
