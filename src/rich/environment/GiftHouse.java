package rich.environment;

import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import rich.Player;

public class GiftHouse implements Land {
    private static final double BONUS = 2000;

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
        switch (choice){
            case 1:
                player.gain(BONUS);
                break;
            default:
                break;
        }
    }
}
