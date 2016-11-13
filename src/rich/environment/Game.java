package rich.environment;

import rich.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if (id < 1 || id > 4)
            return false;
        Optional<Player> player = playerList
                .stream()
                .filter(p -> p.getId() == id)
                .findAny();
        if (player.isPresent())
            return false;
        playerList.add(new Player(id, gameMap, gameDice));
        return true;
    }
}
