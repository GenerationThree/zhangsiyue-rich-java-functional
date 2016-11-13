package rich.environment;

import rich.Player;

import java.util.List;

public interface Map {
    List<Land> getLanList();

    Land move(Land start, int step, Player player);

    Tool getTool(Land current);

    void removeTool(Land current);

    Land sellEstate(Player player, int i);

    boolean setTool(Land current, int distance, Tool.Type type);

    void putInGame(GameControl gameControl);
}
