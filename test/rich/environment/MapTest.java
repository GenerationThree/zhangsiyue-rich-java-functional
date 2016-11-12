package rich.environment;

import org.junit.Test;
import rich.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class MapTest {
    @Test
    public void should_move_to_right_place() throws Exception {
        Land startPoint = mock(Land.class);
        Land passByPoint = mock(Land.class);
        Land endPoint = mock(Land.class);
        Player player = mock(Player.class);
        Map map = new GameMap(startPoint, passByPoint, endPoint);

        assertThat(map.move(startPoint, 2, player), is(endPoint));
    }

    @Test
    public void should_stop_at_block_when_pass_by_block() throws Exception {
        Land startPoint = mock(Land.class);
        Land blockPoint = mock(Land.class);
        Land endPoint = mock(Land.class);
        Player player = mock(Player.class);
        Map map = GameMap.createGameMapWithBlock(1, startPoint, blockPoint, endPoint);

        assertThat(map.move(startPoint, 2, player), is(blockPoint));
    }

    @Test
    public void should_remove_block_after_player_pass_by() throws Exception {
        Land startPoint = mock(Land.class);
        Land blockPoint = mock(Land.class);
        Land endPoint = mock(Land.class);
        Player player = mock(Player.class);
        Map map = GameMap.createGameMapWithBlock(1, startPoint, blockPoint, endPoint);
        assertThat(map.getTool(blockPoint), notNullValue());
        map.move(startPoint, 2, player);
        assertThat(map.getTool(blockPoint), is(nullValue()));
    }

    @Test
    public void should_remove_bomb_after_player_pass_by() throws Exception {
        Land startPoint = mock(Land.class);
        Land bombPoint = mock(Land.class);
        Land endPoint = mock(Land.class);
        Player player = mock(Player.class);
        Map map = GameMap.createGameMapWithBomb(1, startPoint, bombPoint, endPoint);

        assertThat(map.getTool(bombPoint), notNullValue());

        map.move(startPoint, 2, player);
        assertThat(map.getTool(bombPoint), is(nullValue()));
    }
}
