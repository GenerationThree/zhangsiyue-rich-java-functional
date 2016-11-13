package rich.environment;

import org.junit.Test;
import rich.Player;

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
}
