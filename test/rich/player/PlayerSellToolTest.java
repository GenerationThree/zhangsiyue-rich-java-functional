package rich.player;

import org.junit.Before;
import org.junit.Test;
import rich.Player;
import rich.environment.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class PlayerSellToolTest {
    private Map map;
    private Dice dice;
    private Land startPoint;

    @Before
    public void setUp() throws Exception {
        map = mock(Map.class);
        dice = mock(Dice.class);
        startPoint = mock(Land.class);
    }

    @Test
    public void should_wait_command_when_sell_tool() throws Exception {
        Player player = Player.createPlayerWithTool(1, map, dice, startPoint, new Tool(Tool.Type.ROBOT));

        player.sellTool(Tool.Type.ROBOT);

        assertThat(player.getStatus(), is(Player.Status.WAIT_COMMAND));
    }

    @Test
    public void should_decrease_tool_and_increase_points_when_sell_tool() throws Exception {
        Player player = Player.createPlayerWithTool(1, map, dice, startPoint, new Tool(Tool.Type.ROBOT));
        int preToolSum = player.getTools().size();
        int prePoints = player.getPoints();
        player.sellTool(Tool.Type.ROBOT);

        assertThat(player.getTools().size(), is(preToolSum - 1));
        assertThat(player.getPoints(), is(prePoints + Tool.Type.ROBOT.getPointPrice()));
    }

    @Test
    public void should_not_sell_not_owned_tool() throws Exception {
        Player player = Player.createPlayerWithTool(1, map, dice, startPoint, new Tool(Tool.Type.ROBOT));
        int preToolSum = player.getTools().size();
        int prePoints = player.getPoints();
        player.sellTool(Tool.Type.BLOCK);

        assertThat(player.getTools().size(), is(preToolSum));
        assertThat(player.getPoints(), is(prePoints));

    }
}
