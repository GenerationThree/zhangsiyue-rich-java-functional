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

        Player currentPlayer = game.chooseNextPlayer();
        assertThat(currentPlayer.getId(), is(1));
    }

    @Test
    public void should_choose_next_player_when_next_player_not_wait() throws Exception {
        Player playerFirst = Player.createPlayerSpecifiedStatus(1, map, dice, START_BALANCE, Player.Status.END_TURN, 0, 0);
        Player playerTarget = Player.createPlayerSpecifiedStatus(2, map, dice, START_BALANCE, Player.Status.END_TURN, 0, 0);
        GameControl game = Game.createGameWithSpecifiedPlayer(playerFirst, playerTarget);
        Player start = game.chooseNextPlayer();
        assertThat(start, is(playerFirst));

        Player currentPlayer = game.chooseNextPlayer();
        assertThat(currentPlayer, is(playerTarget));
    }

    @Test
    public void should_choose_next_player_after_next_player_when_next_need_wait() throws Exception {
        Player playerStart = Player.createPlayerSpecifiedStatus(1, map, dice, START_BALANCE, Player.Status.END_TURN, 0, 0);
        Player playerWait = Player.createPlayerSpecifiedStatus(2, map, dice, START_BALANCE, Player.Status.END_TURN, 1, 0);
        Player playerTarget = Player.createPlayerSpecifiedStatus(3, map, dice, START_BALANCE, Player.Status.END_TURN, 0, 0);
        GameControl game = Game.createGameWithSpecifiedPlayer(playerStart, playerWait, playerTarget);
        Player start = game.chooseNextPlayer();
        assertThat(start, is(playerStart));

        Player nextPlayer = game.chooseNextPlayer();
        assertThat(nextPlayer, is(playerTarget));
    }

    @Test
    public void should_return_current_player_when_all_player_need_wait() throws Exception {
        Player playerTarget = Player.createPlayerSpecifiedStatus(1, map, dice, START_BALANCE, Player.Status.END_TURN, 1, 0);
        Player playerWait = Player.createPlayerSpecifiedStatus(2, map, dice, START_BALANCE, Player.Status.END_TURN, 1, 0);
        GameControl game = Game.createGameWithSpecifiedPlayer(playerTarget, playerWait);
        Player start = game.chooseNextPlayer();
        assertThat(start, is(playerTarget));

        Player nextPlayer = game.chooseNextPlayer();
        assertThat(nextPlayer, is(playerTarget));
    }
}
