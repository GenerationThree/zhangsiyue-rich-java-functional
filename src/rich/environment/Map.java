package rich.environment;

import rich.Player;

public interface Map {
    Land move(Land start, int step, Player player);

    Tool getTool(Land current);

    void removeTool(Land current);

    Land sellEstate(Player player, int i);
}
