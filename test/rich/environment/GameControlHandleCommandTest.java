package rich.environment;

import org.junit.Before;
import org.junit.Test;
import rich.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @Test
    public void should_handle_start_game_test() throws Exception {
        assertThat(game.getCurrentPlayer(), is(nullValue()));
        game.addPlayer(1);
        Command startGameCommand = new Command(Command.Type.START_GAME, "");
        game.handleCommand(startGameCommand);
        assertThat(game.getCurrentPlayer(), notNullValue());
        assertThat(game.getCurrentPlayer().getId(), is(1));
    }

    @Test
    public void should_handle_roll_command() throws Exception {
        Map map = mock(Map.class);
        Dice dice = mock(Dice.class);
        when(dice.next()).thenReturn(1);
        Land destination = mock(Land.class);
        when(map.move(any(), eq(1), any())).thenReturn(destination);
        GameControl game = Game.createGameWithSpecifiedMapAndDice(map, dice);
        game.addPlayer(1);
        Command startGameCommand = new Command(Command.Type.START_GAME, "");
        Command rollCommand = new Command(Command.Type.ROLL, "");
        game.handleCommand(startGameCommand);
        Player player = game.getCurrentPlayer();

        game.handleCommand(rollCommand);

        assertThat(player.getCurrent(), is(destination));
    }
}
