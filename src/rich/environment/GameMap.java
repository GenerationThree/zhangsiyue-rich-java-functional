package rich.environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;

public class GameMap implements Map {
    private List<Land> landList;
    private java.util.Map toolSetList;

    public GameMap(Land... lands) {
        this.landList = new ArrayList<Land>(){{
            addAll(asList(lands));
        }};
        this.toolSetList = new HashMap<>();
    }

    public static GameMap createGameMapWithBlock( int position, Land... lands) {
        GameMap gameMap =  new GameMap(lands);
        gameMap.toolSetList.put(position, new Tool(Tool.Type.BLOCK));
        return gameMap;
    }


    @Override
    public Land move(Land start, int step) {
        Land current = start;
        int statIndex = landList.indexOf(start);
        for (int i = 1; i <= step; i++){
            current = landList.get((statIndex + i) % landList.size());
            Tool tool = getTool(current);
            if(tool != null)
                if (tool.getType() == Tool.Type.BLOCK)
                    return current;
        }
        return current;
    }

    @Override
    public Tool getTool(Land current) {
        int currentPosition = landList.indexOf(current);
        Tool tool = (Tool) toolSetList.getOrDefault(currentPosition, null);
        return tool;
    }
}
