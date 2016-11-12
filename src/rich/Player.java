package rich;

public class Player {

    private final int id;
    private Land current;
    private GameMap map;
    private Dice dice;
    private Status status;


    private Player(int id, GameMap map, Dice dice) {
        this.id = id;
        this.map = map;
        this.dice = dice;
        status = Status.WAIT_COMMAND;
    }

    public static Player createPlayerWithStart(int id, GameMap map, Dice dice, Land start) {
        Player player = new Player(id, map, dice);
        player.current = start;
        return player;
    }

    public void roll() {
        current = map.move(current, dice.next());
        status = Status.WAIT_RESPONSE;
    }

    public Status getStatus() {
        return status;
    }


    public enum Status {WAIT_COMMAND, WAIT_RESPONSE}
}
