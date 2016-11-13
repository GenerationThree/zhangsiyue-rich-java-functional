package rich.environment;

import org.junit.Test;
import rich.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class GameControlTest {

    @Test
    public void should_add_player_and_get_player_list() throws Exception {
        Player player = mock(Player.class);
        GameControl game = new Game();
        assertThat(game.getPlayerList().size(), is(0));

        game.addPlayer(player);

        assertThat(game.getPlayerList().size(), is(1));
    }
}
