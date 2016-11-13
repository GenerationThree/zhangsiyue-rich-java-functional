package rich.environment;

import rich.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class GameMap implements Map {
    private List<Land> landList;
    private java.util.Map toolSetList;
    private GameControl gameControl;

    public GameMap(List<Land> lands){
        landList = new ArrayList<Land>(){{
           addAll(lands);
        }};
        toolSetList = new HashMap<>();
    }

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

    public static GameMap createGameMagWithGameControl(GameControl gameControl, Land... lands){
        GameMap gameMap = new GameMap(lands);
        gameMap.gameControl = gameControl;
        return gameMap;
    }

    @Override
    public List<Land> getLanList() {
        return landList;
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
    public boolean setTool(Land current, int distance, Tool.Type type) {
        int length = landList.size();
        int targetPosition = distance + landList.indexOf(current);

        if(targetPosition < 0)
            targetPosition = targetPosition % length + length;
        else
            targetPosition %= length;

        Land targetLand = landList.get(targetPosition);
        if(getTool(targetLand) != null)
            return false;
        for(Player player : gameControl.getPlayerList()){
            if (player.getCurrent().equals(targetLand))
                return false;
        }
        toolSetList.put(targetPosition, new Tool(type));
        return true;
    }

    @Override
    public void putInGame(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    @Override
    public Land getStartPoint() {
        Optional<Land> start = landList
                .stream()
                .filter(land -> land instanceof StartPoint)
                .findAny();
        return start.orElse(null);
    }
}
