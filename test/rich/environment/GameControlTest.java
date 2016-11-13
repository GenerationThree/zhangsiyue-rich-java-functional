package rich.environment;

import org.junit.Test;
import rich.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GameControlTest {

    @Test
    public void should_add_player_and_get_player_list() throws Exception {
        GameControl game = new Game();
        assertThat(game.getPlayerList().size(), is(0));

        game.addPlayer(1);

        assertThat(game.getPlayerList().size(), is(1));
    }
}
