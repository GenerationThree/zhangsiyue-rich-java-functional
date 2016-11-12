package rich.player;

import org.junit.Before;
import org.junit.Test;
import rich.Player;
import rich.environment.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerRollToTollHouseTest {
    private Map map;
    private Dice dice;
    private Land startPoint;
    private Land toolHouse;

    private static final int LOW_LIMIT_FOR_POINT = TollHouse.LOW_LIMIT;
    private static final int START_POINTS = LOW_LIMIT_FOR_POINT + 1;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        startPoint = mock(Land.class);
        toolHouse = mock(TollHouse.class);
        map = new GameMap(startPoint, toolHouse);

        when(dice.next()).thenReturn(1);
    }

    @Test
    public void should_wait_response_when_roll_to_tool_house() throws Exception {
        Player player = Player.createPlayerWithPoint(2, map, dice, startPoint, START_POINTS);

        player.roll();

        assertThat(player.getStatus(), is(Player.Status.WAIT_RESPONSE));
    }

    @Test
    public void should_end_turn_without_enough_points_when_roll_to_tool_house() throws Exception {
        Player player = Player.createPlayerWithPoint(2, map, dice, startPoint, LOW_LIMIT_FOR_POINT - 1);

        player.roll();

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_add_tool_after_buy_tool_at_tool_house() throws Exception {
        toolHouse = new TollHouse();
        map = new GameMap(startPoint, toolHouse);
        final int startPointSum = Tool.Type.BLOCK.getPointPrice();
        Player player = Player.createPlayerWithPoint(2, map, dice, startPoint, startPointSum);

        player.roll();
        player.buyTool(1);

        assertThat(player.getPoints(), is(startPointSum - Tool.Type.BLOCK.getPointPrice()));
        assertThat(player.getTools().size(), is(1));
    }

    @Test
    public void should_not_buy_tool_when_make_invalid_choice() throws Exception {
        toolHouse = new TollHouse();
        map = new GameMap(startPoint, toolHouse);
        final int startPointSum = Tool.Type.BLOCK.getPointPrice();
        Player player = Player.createPlayerWithPoint(2, map, dice, startPoint, startPointSum);

        player.roll();
        player.buyTool(4);

        assertThat(player.getPoints(), is(startPointSum));
        assertThat(player.getTools().size(), is(0));

    }
}
