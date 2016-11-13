package rich.player;

import org.junit.Before;
import org.junit.Test;
import rich.Player;
import rich.environment.Dice;
import rich.environment.GameMap;
import rich.environment.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class PlayerChangeStatusTest {

    private Map map;
    private Dice dice;
    private static final double START_BALANCE = 10000;

    @Before
    public void setUp() throws Exception {
        map = mock(Map.class);
        dice = mock(Dice.class);
    }

    @Test
    public void should_change_to_wait_command_when_player_not_wait_start_his_turn() throws Exception {
        Player player = new Player(1, map, dice, START_BALANCE);
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
        assertThat(player.getWaitTurn(), is(0));

        boolean isStart = player.startTurn();

        assertThat(isStart, is(true));
        assertThat(player.getStatus(), is(Player.Status.WAIT_COMMAND));
    }
}
