package rich.environment;

import rich.Player;

import java.util.ArrayList;
import java.util.List;

public class Game implements GameControl {
    List<Player> playerList;
    Map gameMap;
    Dice gameDice;

    public Game() {
        playerList = new ArrayList<>();
        gameMap = new GameMap();
        gameDice = new GameDice();
    }

    @Override
    public List<Player> getPlayerList() {
        return playerList;
    }

    @Override
    public boolean addPlayer(int id) {
        playerList.add(new Player(id, gameMap, gameDice));
        return true;
    }
}
