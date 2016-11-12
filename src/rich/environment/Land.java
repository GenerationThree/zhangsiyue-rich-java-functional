package rich.environment;

import rich.Player;

public interface Land {
    Player getOwner();
    void buy(Player player);
    double getPrice();
    Estate.Level getLevel();
    boolean promote();
}
