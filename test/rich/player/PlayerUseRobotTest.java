package rich.player;

import org.junit.Before;
import org.junit.Test;
import rich.Player;
import rich.environment.Dice;
import rich.environment.Land;
import rich.environment.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class PlayerUseRobotTest {
    private Map map;
    private Dice dice;
    private Player player;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(Map.class);
        player = new Player(1, map, dice);
    }

    @Test
    public void should_wait_command_after_use_robot() throws Exception {
        player.useRobot();

        assertThat(player.getStatus(), is(Player.Status.WAIT_COMMAND));
    }
}
