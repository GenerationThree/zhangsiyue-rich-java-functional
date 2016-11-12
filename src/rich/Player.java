package rich;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final int id;
    private Land current;
    private Status status;
    private double balance;

    private List<Land> lands;
    private GameMap map;
    private Dice dice;


    private Player(int id, GameMap map, Dice dice) {
        this.id = id;
        this.map = map;
        this.dice = dice;
        status = Status.WAIT_COMMAND;
        this.lands = new ArrayList<>();
    }

    public static Player createPlayerWithStart(int id, GameMap map, Dice dice, Land start) {
        Player player = new Player(id, map, dice);
        player.current = start;
        return player;
    }

    public static Player createPlayerWithBalance(int id, GameMap map, Dice dice, Land start, double balance){
        Player player = new Player(id, map, dice);
        player.current = start;
        player.balance = balance;
        return player;
    }

    public void roll() {
        current = map.move(current, dice.next());
        if(current.getOwner() == null || current.getOwner() == this)
            status = Status.WAIT_RESPONSE;
    }

    public Status getStatus() {
        return status;
    }

    public void sayNo() {
        if(current.getOwner() == null);
            status = Status.END_TURN;
    }

    public void sayYes(){
        buy();
        status = Status.END_TURN;
    }

    public void buy(){
        if (balance >= current.getPrice()) {
            balance -= current.getPrice();
            current.buy(this);
            lands.add(current);
        }
    }


    public List<Land> getLands() {
        return lands;
    }

    public double getBalance() {
        return balance;
    }

    public enum Status {WAIT_COMMAND, WAIT_RESPONSE, END_TURN,}
}
