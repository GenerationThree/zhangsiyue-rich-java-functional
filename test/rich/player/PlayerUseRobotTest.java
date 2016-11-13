package rich.player;

import org.junit.Before;
import org.junit.Test;
import rich.Player;
import rich.environment.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class PlayerUseRobotTest {
    private Map map;
    private Dice dice;
    private Player player;
    private Land startPoint;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(Map.class);
        startPoint = mock(Land.class);
        player = Player.createPlayerWithTool(1, map, dice, startPoint, new Tool(Tool.Type.ROBOT));
    }

    @Test
    public void should_wait_command_after_use_robot() throws Exception {
        player.useTool(Tool.Type.ROBOT, 0);

        assertThat(player.getStatus(), is(Player.Status.WAIT_COMMAND));
    }

    @Test
    public void should_remove_tool_after_use_robot() throws Exception {
        Land blockPoint = mock(Land.class);
        Land endPoint = mock(Land.class);
        map = GameMap.createGameMapWithBlock(1, startPoint, blockPoint, endPoint);
        player = Player.createPlayerWithTool(1, map, dice, startPoint, new Tool(Tool.Type.ROBOT));

        assertThat(map.getTool(blockPoint), notNullValue());
        player.useTool(Tool.Type.ROBOT, 0);

        assertThat(map.getTool(blockPoint), is(nullValue()));
        assertThat(player.getTools().size(), is(0));
    }


    @Test
    public void should_not_use_robot_without_robot() throws Exception {
        Land blockPoint = mock(Land.class);
        Land endPoint = mock(Land.class);
        map = GameMap.createGameMapWithBlock(1, startPoint, blockPoint, endPoint);
        player = Player.createPlayerWithStart(1, map, dice, startPoint);

        assertThat(map.getTool(blockPoint), notNullValue());

        player.useTool(Tool.Type.ROBOT, 0);

        assertThat(map.getTool(blockPoint), notNullValue());
    }
}
