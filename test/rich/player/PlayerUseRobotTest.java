package rich.player;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import rich.Player;
import rich.environment.Dice;
import rich.environment.GameMap;
import rich.environment.Land;
import rich.environment.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerUseRobotTest {
    private Map map;
    private Dice dice;
    private Player player;
    private Land land;
    private Land startPoint;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(Map.class);
        land = mock(Land.class);
        startPoint = mock(Land.class);
        player = Player.createPlayerWithRobot(1, map, dice, startPoint);
    }

    @Test
    public void should_wait_command_after_use_robot() throws Exception {
        player.useRobot();

        assertThat(player.getStatus(), is(Player.Status.WAIT_COMMAND));
    }

    @Test
    public void should_remove_tool_after_use_robot() throws Exception {
        Land blockPoint = mock(Land.class);
        Land endPoint = mock(Land.class);
        map = GameMap.createGameMapWithBlock(1, startPoint, blockPoint, endPoint);
        player = Player.createPlayerWithRobot(1, map, dice, startPoint);

        assertThat(map.getTool(blockPoint), notNullValue());
        player.useRobot();

        assertThat(map.getTool(blockPoint), is(nullValue()));
    }
}
