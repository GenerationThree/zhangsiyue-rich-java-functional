package rich.environment;

import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import rich.Player;

public class Mine implements Land{

    private int points;

    public Mine(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public Player getOwner() {
        throw new UnsupportedMediaException();
    }

    @Override
    public void buy(Player player) {
        throw new UnsupportedMediaException();
    }

    @Override
    public double getPrice() {
        throw new UnsupportedMediaException();

    }

    @Override
    public Estate.Level getLevel() {
        throw new UnsupportedMediaException();

    }

    @Override
    public boolean promote() {
        throw new UnsupportedMediaException();

    }

    @Override
    public void getGift(int choice, Player player) {
        throw new UnsupportedMediaException();
    }

    @Override
    public Tool getTool(int choice) {
        throw new UnsupportedMediaException();
    }
}
