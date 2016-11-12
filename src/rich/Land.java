package rich;

public interface Land {
    Player getOwner();
    void buy(Player player);
    double getPrice();
    Estate.Level getLevel();
    boolean promote();
}
