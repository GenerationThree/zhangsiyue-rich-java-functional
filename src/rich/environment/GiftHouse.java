package rich.environment;

import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import rich.Player;

public class GiftHouse implements Land {
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
}
