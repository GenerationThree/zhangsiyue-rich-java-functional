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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerUseBombTest {
    private Map map;
    private Dice dice;
    private Player player;
    private Land startPoint;
    private Tool bomb;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(Map.class);
        startPoint = mock(Land.class);
        bomb = new Tool(Tool.Type.BOMB);
        player = Player.createPlayerWithTool(1, map, dice, startPoint, bomb);
        when(map.setTool(any(), anyInt(), any())).thenReturn(true);
    }

    @Test
    public void should_wait_command_after_use_bomb() throws Exception {
        player.useTool(Tool.Type.BOMB, 2);

        assertThat(player.getStatus(), is(Player.Status.WAIT_COMMAND));

    }

    @Test
    public void should_remove_bomb_after_use() throws Exception {
        int preToolSum = player.getTools().size();
        player.useTool(Tool.Type.BOMB, 2);

        assertThat(player.getTools().size(), is(preToolSum - 1));
    }

    @Test
    public void should_not_use_bomb_without_bomb() throws Exception {
        player = Player.createPlayerSpecifiedStatus(1, map, dice, 0, Player.Status.WAIT_COMMAND, 0, 0);
        int preToolSum = player.getTools().size();

        player.useTool(Tool.Type.BOMB, 2);

        assertThat(player.getTools().size(), is(preToolSum));
    }

    @Test
    public void should_not_remove_bomb_when_use_block_failed() throws Exception {
        when(map.setTool(any(), anyInt(), any())).thenReturn(false);
        int preToolSum = player.getTools().size();
        player.useTool(Tool.Type.BOMB, 2);

        assertThat(player.getTools().size(), is(preToolSum));
    }
}
