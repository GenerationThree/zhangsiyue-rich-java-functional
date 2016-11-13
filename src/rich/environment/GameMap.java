package rich.environment;

import rich.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;

public class GameMap implements Map {
    private List<Land> landList;
    private java.util.Map toolSetList;

    public GameMap(Land... lands) {
        this.landList = new ArrayList<Land>() {{
            addAll(asList(lands));
        }};
        this.toolSetList = new HashMap<>();
    }

    public static GameMap createGameMapWithBlock(int position, Land... lands) {
        GameMap gameMap = new GameMap(lands);
        gameMap.toolSetList.put(position, new Tool(Tool.Type.BLOCK));
        return gameMap;
    }

    public static GameMap createGameMapWithBomb(int position, Land... lands) {
        GameMap gameMap = new GameMap(lands);
        gameMap.toolSetList.put(position, new Tool(Tool.Type.BOMB));
        return gameMap;
    }


    @Override
    public Land move(Land start, int step, Player player) {
        Land current = start;
        int statIndex = landList.indexOf(start);
        for (int i = 1; i <= step; i++) {
            current = landList.get((statIndex + i) % landList.size());
            Tool tool = getTool(current);
            if (tool != null) {
                if (tool.getType() == Tool.Type.BLOCK) {
                    toolSetList.remove(statIndex + i);
                    return current;
                }
                if (tool.getType() == Tool.Type.BOMB) {
                    toolSetList.remove(statIndex + i);
                    player.goToHospital();
                    return getHospital();
                }
            }
        }
        return current;
    }

    private Land getHospital() {
        for (Land land : landList) {
            if (land instanceof Hospital)
                return land;
        }
        return null;
    }

    @Override
    public Tool getTool(Land current) {
        int currentPosition = landList.indexOf(current);
        Tool tool = (Tool) toolSetList.getOrDefault(currentPosition, null);
        return tool;
    }

    @Override
    public void removeTool(Land current) {
        int currentIndex = landList.indexOf(current);
        for (int i = 1; i <= 10; i++) {
            Land target = landList.get((currentIndex + i) % landList.size());
            if (getTool(target) != null) {
                toolSetList.remove(currentIndex + i);
            }
        }
    }

    @Override
    public Land sellEstate(Player player, int i) {
        Land land = landList.get(i);
        if(land instanceof Estate) {
            Estate estate = (Estate) land;
            if (estate.getOwner() == player) {
                estate.sell();
                return estate;
            }
        }
        return null;
    }

    @Override
    public boolean setBlock(int distance) {
        return false;
    }

    @Override
    public boolean setBomb(int distance) {
        return false;
    }
}
