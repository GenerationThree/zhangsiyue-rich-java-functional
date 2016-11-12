package rich.environment;

import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import rich.Player;

public class TollHouse implements Land {
    public static final int LOW_LIMIT = 30;
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
}
