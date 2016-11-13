package rich.environment;

import java.util.Random;

public class GameDice implements Dice {
    private Random random;

    public GameDice() {
        random = new Random();
    }

    @Override
    public int next() {
        return random.nextInt(6) + 1;
    }
}
