package rich.environment;

import rich.Player;

import java.util.ArrayList;
import java.util.List;

public class Game implements GameControl {
    List<Player> playerList;

    public Game() {
        this.playerList = new ArrayList<>();
    }

    @Override
    public List<Player> getPlayerList() {
        return playerList;
    }

    @Override
    public boolean addPlayer(Player player) {
        playerList.add(player);
        return true;
    }
}
