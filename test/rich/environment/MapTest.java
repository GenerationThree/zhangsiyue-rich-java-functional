package rich.environment;

import org.junit.Before;
import org.junit.Test;
import rich.Player;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapTest {
    private Land startPoint;
    private Land endPoint;
    private Player player;

    @Before
    public void setUp() throws Exception {
        startPoint = mock(Land.class);
        endPoint = mock(Land.class);
        player = mock(Player.class);

    }

    @Test
    public void should_move_to_right_place() throws Exception {
        Land passByPoint = mock(Land.class);
        Map map = new GameMap(startPoint, passByPoint, endPoint);

        assertThat(map.move(startPoint, 2, player), is(endPoint));
    }

    @Test
    public void should_stop_at_block_when_pass_by_block() throws Exception {
        Land blockPoint = mock(Land.class);
        Map map = GameMap.createGameMapWithBlock(1, startPoint, blockPoint, endPoint);

        assertThat(map.move(startPoint, 2, player), is(blockPoint));
    }

    @Test
    public void should_remove_block_after_player_pass_by() throws Exception {
        Land blockPoint = mock(Land.class);
        Map map = GameMap.createGameMapWithBlock(1, startPoint, blockPoint, endPoint);

        assertThat(map.getTool(blockPoint), notNullValue());

        map.move(startPoint, 2, player);

        assertThat(map.getTool(blockPoint), is(nullValue()));
    }

    @Test
    public void should_remove_bomb_after_player_pass_by() throws Exception {
        Land bombPoint = mock(Land.class);
        Player player = mock(Player.class);
        Map map = GameMap.createGameMapWithBomb(1, startPoint, bombPoint, endPoint);

        assertThat(map.getTool(bombPoint), notNullValue());

        map.move(startPoint, 2, player);
        assertThat(map.getTool(bombPoint), is(nullValue()));
    }

    @Test
    public void should_remove_tool() throws Exception {
        Land blockPoint = mock(Land.class);
        Map gameMap = GameMap.createGameMapWithBlock(1, startPoint, blockPoint, endPoint);

        assertThat(gameMap.getTool(blockPoint), notNullValue());

        gameMap.removeTool(startPoint);

        assertThat(gameMap.getTool(blockPoint), is(nullValue()));
    }

    @Test
    public void should_return_empty_after_sell_estate() throws Exception {
        Player player = mock(Player.class);
        Estate estatePoint = new Estate(player, 200);
        Map gameMap = GameMap.createGameMapWithBlock(1, startPoint, estatePoint, endPoint);

        assertThat(estatePoint.getOwner(), is(player));
        Land sold = gameMap.sellEstate(player, 1);

        assertThat(sold, is(estatePoint));
        assertThat(((Estate)sold).getOwner(), is(nullValue()));
    }

    @Test
    public void should_return_null_when_player_sell_not_owned_estate() throws Exception {
        Player otherPlayer = mock(Player.class);
        Estate estatePoint = new Estate(otherPlayer, 200);
        Map gameMap = GameMap.createGameMapWithBlock(1, startPoint, estatePoint, endPoint);

        assertThat(estatePoint.getOwner(), is(otherPlayer));
        Land sold = gameMap.sellEstate(player, 1);

        assertThat(sold, is(nullValue()));
        assertThat(estatePoint.getOwner(), is(otherPlayer));
    }

    @Test
    public void should_return_null_when_player_sell_non_estate() throws Exception {
        Player player = mock(Player.class);
        Land hospital = new Hospital();
        Map gameMap = GameMap.createGameMapWithBlock(1, startPoint, hospital, endPoint);

        Land sold = gameMap.sellEstate(player, 1);

        assertThat(sold, is(nullValue()));
    }

    @Test
    public void should_set_block_at_specified_position() throws Exception {
        GameControl gameControl = mock(GameControl.class);
        Land targetPoint = mock(Land.class);
        Player otherPlayer = mock(Player.class);
        when(otherPlayer.getCurrent()).thenReturn(endPoint);
        List<Player> players = new ArrayList<Player>(){{
            add(player);
            add(otherPlayer);
        }};
        when(gameControl.getPlayerList()).thenReturn(players);
        Map gameMap = GameMap.createGameMagWithGameControl(gameControl, startPoint, targetPoint, endPoint);

        assertThat(gameMap.setBlock(startPoint, 1), is(true));
        Tool tool = gameMap.getTool(targetPoint);
        assertThat(tool, notNullValue());
        assertThat(tool.getType(), is(Tool.Type.BLOCK));
    }

    @Test
    public void should_not_set_block_when_other_player_at_specified_position_() throws Exception {
        GameControl gameControl = mock(GameControl.class);
        Land targetPoint = mock(Land.class);
        Player otherPlayer = mock(Player.class);
        when(otherPlayer.getCurrent()).thenReturn(targetPoint);
        when(player.getCurrent()).thenReturn(startPoint);
        List<Player> players = new ArrayList<Player>(){{
            add(player);
            add(otherPlayer);
        }};
        when(gameControl.getPlayerList()).thenReturn(players);
        Map gameMap = GameMap.createGameMagWithGameControl(gameControl, startPoint, targetPoint, endPoint);

        assertThat(gameMap.setBlock(startPoint, -5), is(false));
        Tool tool = gameMap.getTool(targetPoint);
        assertThat(tool, is(nullValue()));
    }
}
