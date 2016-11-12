package rich.environment;

import org.junit.Test;
import rich.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ToolHouseTest {

    @Test
    public void should_return_block_when_choose_one() throws Exception {
        TollHouse toolHouse = new TollHouse();

        assertThat(toolHouse.getTool(1).getType(), is(Tool.Type.BLOCK));

    }

    @Test
    public void should_return_robot_when_choose_two() throws Exception {
        TollHouse toolHouse = new TollHouse();

        assertThat(toolHouse.getTool(2).getType(), is(Tool.Type.ROBOT));

    }

    @Test
    public void should_return_bomb_when_choose_three() throws Exception {
        TollHouse toolHouse = new TollHouse();

        assertThat(toolHouse.getTool(3).getType(), is(Tool.Type.BOMB));
    }

    @Test
    public void should_return_null_when_get_invalid_choice() throws Exception {
        TollHouse toolHouse = new TollHouse();

        assertThat(toolHouse.getTool(4), is(nullValue()));
    }
}
