package rich.environment;

import rich.Player;

import java.util.List;

public interface GameControl {
    List<Player> getPlayerList();

    boolean addPlayer(int id);

    Map getMap();

    boolean setInitBalance(double balance);

    Player chooseCurrentPlayer();
}
