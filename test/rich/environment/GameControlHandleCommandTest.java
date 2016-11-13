package rich.environment;

import org.junit.Before;
import org.junit.Test;
import rich.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GameControlHandleCommandTest {

    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game();
    }

    @Test
    public void should_handle_set_init_balance_command() throws Exception {
        String balance = "2000";
        Command setBalanceCommand = new Command(Command.Type.SET_INIT_BALANCE, balance);
        game.handleCommand(setBalanceCommand);
        game.addPlayer(1);

        Player player = game.getPlayerList().get(0);
        assertThat(player.getBalance(), is(Double.valueOf(balance)));
    }

    @Test
    public void should_handle_add_player_command() throws Exception {
        String id = "1";
        Command addPlayerCommand = new Command(Command.Type.ADD_PLAYER, id);

        assertThat(game.getPlayerList().size(), is(0));
        game.handleCommand(addPlayerCommand);
        assertThat(game.getPlayerList().size(), is(1));
    }
}
