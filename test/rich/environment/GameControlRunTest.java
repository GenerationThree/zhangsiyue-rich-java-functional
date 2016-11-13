package rich.environment;

import org.junit.Before;
import org.junit.Test;
import rich.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class GameControlRunTest {

    private Map map;
    private Dice dice;
    private final static double START_BALANCE = 10000;

    @Before
    public void setUp() throws Exception {
        map = mock(Map.class);
        dice = mock(Dice.class);
    }

    @Test
    public void should_choose_first_user_when_game_start() throws Exception {
        GameControl game = new Game();
        game.addPlayer(1);
        game.addPlayer(2);
        game.addPlayer(3);

        Player currentPlayer = game.chooseCurrentPlayer();
        assertThat(currentPlayer.getId(), is(1));
    }

    @Test
    public void should_choose_current_player_when_current_player_not_wait() throws Exception {
        Player playerFirst = Player.createPlayerSpecifiedStatus(1, map, dice, START_BALANCE, Player.Status.END_TURN, 0, 0);
        Player playerTarget = Player.createPlayerSpecifiedStatus(2, map, dice, START_BALANCE, Player.Status.END_TURN, 0, 0);
        GameControl game = Game.createGameWithSpecifiedPlayer(playerFirst, playerTarget);
        Player start = game.chooseCurrentPlayer();
        System.out.println(start.getId());
        assertThat(start, is(playerFirst));

        Player currentPlayer = game.chooseCurrentPlayer();
        System.out.println(currentPlayer.getId());
        assertThat(currentPlayer, is(playerTarget));
    }
}
