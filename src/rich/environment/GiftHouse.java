package rich.environment;

import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import rich.Player;

public class GiftHouse implements Land {
    public static final double BONUS = 2000;
    public static final int POINTS = 200;
    public static final int FREE_TURN = 5;

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
            case 2:
                player.addPoint(POINTS);
                break;
            case 3:
                player.haveFreeTurn(FREE_TURN);
                break;
            default:
                break;
        }
    }
}
