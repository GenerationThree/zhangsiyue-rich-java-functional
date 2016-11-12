package rich.player;

import org.junit.Before;
import org.junit.Test;
import rich.Player;
import rich.environment.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerPassByBombTest {
    private Map map;
    private Dice dice;
    private Land startPoint;
    private Land bombPoint;
    private Land endPoint;
    private Land hospital;
    private Player player;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        startPoint = mock(Land.class);
        bombPoint = mock(Land.class);
        endPoint = mock(Land.class);
        hospital = mock(Hospital.class);
        map = GameMap.createGameMapWithBomb(1, startPoint, bombPoint, endPoint, hospital);
        player = Player.createPlayerWithStart(1, map, dice, startPoint);

        when(dice.next()).thenReturn(2);
    }

    @Test
    public void should_end_turn_when_pass_by_bomb() throws Exception {
        player.roll();

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
        assertThat(player.getCurrent(), is(hospital));
    }

    @Test
    public void should_stop_three_turn_when_pass_by_bomb() throws Exception {
        player.roll();

        assertThat(player.getWaitTurn(), is(3));
    }
}
