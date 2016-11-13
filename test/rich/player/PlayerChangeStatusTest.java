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
    public void should_change_to_wait_command_with_player_not_wait_when_start_his_turn() throws Exception {
        Player player = new Player(1, map, dice, START_BALANCE);
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
        assertThat(player.getWaitTurn(), is(0));

        boolean isStart = player.startTurn();

        assertThat(isStart, is(true));
        assertThat(player.getStatus(), is(Player.Status.WAIT_COMMAND));
    }

    @Test
    public void should_end_turn_and_decrease_wait_turns_with_player_is_wait_when_start_his_turn() throws Exception {
        int waitTurn = 1;
        Player player = Player.createPlayerSpecifiedStatus(1,map, dice, START_BALANCE, Player.Status.END_TURN, waitTurn, 0);

        boolean isStart = player.startTurn();

        assertThat(isStart, is(false));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
        assertThat(player.getWaitTurn(), is(waitTurn - 1));
    }

    @Test
    public void should_decrease_free_turn_when_start_his_turn() throws Exception {
        int freeTurn = 1;
        Player player = Player.createPlayerSpecifiedStatus(1,map, dice, START_BALANCE, Player.Status.END_TURN, 0, freeTurn);

        player.startTurn();

        assertThat(player.getFreeTurn(), is(freeTurn - 1));

    }
}
