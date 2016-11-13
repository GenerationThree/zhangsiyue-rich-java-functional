package rich.environment;

import org.junit.Test;
import rich.Player;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GameControlInitTest {

    @Test
    public void should_add_player_and_get_player_list() throws Exception {
        GameControl game = new Game();
        assertThat(game.getPlayerList().size(), is(0));

        assertThat(game.addPlayer(1), is(true));

        assertThat(game.getPlayerList().size(), is(1));
    }

    @Test
    public void should_not_add_player_with_invalid_id() throws Exception {
        GameControl game = new Game();
        assertThat(game.getPlayerList().size(), is(0));

        assertThat(game.addPlayer(0), is(false));

        assertThat(game.getPlayerList().size(), is(0));

        assertThat(game.addPlayer(7), is(false));

        assertThat(game.getPlayerList().size(), is(0));
    }

    @Test
    public void should_not_add_player_with_same_id() throws Exception {
        GameControl game = new Game();
        assertThat(game.getPlayerList().size(), is(0));

        assertThat(game.addPlayer(2), is(true));

        assertThat(game.getPlayerList().size(), is(1));

        assertThat(game.addPlayer(2), is(false));

        assertThat(game.getPlayerList().size(), is(1));
    }

    @Test
    public void should_init_map_and_no_player_when_game_start() throws Exception {
        GameControl game = new Game();

        Map gameMap = game.getMap();
        assertThat(gameMap.getLanList().size(), is(70));
    }

    @Test
    public void should_set_player_init_balance() throws Exception {
        GameControl game = new Game();
        final double initBalance = 40000;
        assertThat(game.setInitBalance(initBalance), is(true));
        game.addPlayer(1);

        List<Player> players = game.getPlayerList();
        assertThat(players.size(), is(1));
        assertThat(players.get(0).getBalance(), is(initBalance));
    }

    @Test
    public void should_not_set_player_init_balance_out_of_limit() throws Exception {
        GameControl game = new Game();
        final double initBalance = 100000;
        final double defaultBalance = 10000;
        assertThat(game.setInitBalance(initBalance), is(false));
        game.addPlayer(1);

        List<Player> players = game.getPlayerList();
        assertThat(players.size(), is(1));
        assertThat(players.get(0).getBalance(), is(defaultBalance));
    }

    @Test
    public void should_player_be_right_state() throws Exception {
        GameControl game = new Game();
        final double initBalance = 40000;
        assertThat(game.setInitBalance(initBalance), is(true));
        game.addPlayer(1);

        List<Player> players = game.getPlayerList();
        assertThat(players.size(), is(1));
        Player player = players.get(0);
        assertThat(player.getBalance(), is(initBalance));
        assertThat(player.getStatus(), is(Player.Status.WAIT_TURN));
        assertThat(player.getPoints(), is(0));
        assertThat(player.getLands().size(), is(0));
        assertThat(player.getTools().size(), is(0));
        assertThat(player.getWaitTurn(), is(0));
        assertThat(player.getFreeTurn(), is(0));
        assertThat(player.getCurrent() instanceof StartPoint, is(true));
    }
}
