package rich.environment;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class MapTest {
    @Test
    public void should_move_to_right_place() throws Exception {
        Land startPoint = mock(Land.class);
        Land passByPoint = mock(Land.class);
        Land endPoint = mock(Land.class);
        Map map = new GameMap(startPoint, passByPoint, endPoint);

        assertThat(map.move(startPoint, 2), is(endPoint));
    }

    @Test
    public void should_stop_at_block_when_pass_by_block() throws Exception {
        Land startPoint = mock(Land.class);
        Land blockPoint = mock(Land.class);
        Land endPoint = mock(Land.class);
        Map map = GameMap.createGameMapWithBlock(1, startPoint, blockPoint, endPoint);

        assertThat(map.move(startPoint, 2), is(blockPoint));
    }
}
