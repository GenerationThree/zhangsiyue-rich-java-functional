package rich;

import rich.environment.*;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final int id;
    private Land current;
    private Status status;
    private double balance;
    private List<Land> lands;
    private List<Tool> tools;
    private int freeTurn;
    private int waitTurn;
    private Map map;
    private Dice dice;
    private int points;


    public Player(int id, Map map, Dice dice) {
        this.id = id;
        this.map = map;
        this.dice = dice;
        status = Status.WAIT_COMMAND;
        lands = new ArrayList<>();
        tools = new ArrayList<>();
        freeTurn = 0;
        waitTurn = 0;
    }

    public static Player createPlayerWithStart(int id, Map map, Dice dice, Land start) {
        Player player = new Player(id, map, dice);
        player.current = start;
        return player;
    }

    public static Player createPlayerWithBalance(int id, Map map, Dice dice, Land start, double balance) {
        Player player = new Player(id, map, dice);
        player.current = start;
        player.balance = balance;
        return player;
    }

    public static Player createPlayerFreeForFee(int id, Map map, Dice dice, Land start, double balance, int freeTurns) {
        Player player = new Player(id, map, dice);
        player.current = start;
        player.balance = balance;
        player.freeTurn = freeTurns;
        return player;
    }

    public static Player createPlayerWithPoint(int id, Map map, Dice dice, Land start, int points) {
        Player player = new Player(id, map, dice);
        player.current = start;
        player.points = points;
        return player;
    }

    public void roll() {
        current = map.move(current, dice.next(), this);

        if (current instanceof Estate) {
            if (((Estate)current).getOwner() == null || ((Estate)current).getOwner() == this)
                status = Status.WAIT_RESPONSE;
            else if (((Estate)current).getOwner() != this) {
                payFee(((Estate)current).getOwner());
            }
        }
        if (current instanceof Prison) {
            status = Status.END_TURN;
            waitTurn = 2;
        }
        if (current instanceof GiftHouse) {
            status = Status.WAIT_RESPONSE;
        }
        if (current instanceof TollHouse) {
            if (points >= TollHouse.LOW_LIMIT)
                status = Status.WAIT_RESPONSE;
            else
                status = Status.END_TURN;
        }
        if(current instanceof Mine) {
            points += ((Mine) current).getPoints();
            status = Status.END_TURN;
        }
        if (freeTurn > 0)
            freeTurn--;
    }

    private void payFee(Player owner) {
        if (freeTurn <= 0) {
            double fee = ((Estate)current).getPrice() * ((Estate)current).getLevel().getTimes();
            if (balance >= fee) {
                balance -= fee;
                owner.gain(fee);
            } else {
                status = Status.END_GAME;
                return;
            }
        }
        status = Status.END_TURN;
    }

    public void gain(double sum) {
        balance += sum;
    }

    public Status getStatus() {
        return status;
    }

    public void sayNo() {
        if (((Estate)current).getOwner() == null || ((Estate)current).getOwner() == this)
            status = Status.END_TURN;
    }

    public void sayYes() {
        if (((Estate)current).getOwner() == null)
            buy();
        else if (((Estate)current).getOwner() == this)
            promote();
        status = Status.END_TURN;
    }

    private void buy() {
        if (balance >= ((Estate)current).getPrice()) {
            balance -= ((Estate)current).getPrice();
            ((Estate)current).buy(this);
            lands.add(current);
        }
    }

    private void promote() {
        if (balance >= ((Estate)current).getPrice()) {
            if (((Estate)current).promote())
                balance -= ((Estate)current).getPrice();
        }
    }

    public void addPoint(int points) {
        this.points += points;
    }

    public void haveFreeTurn(int freeTurn) {
        this.freeTurn = freeTurn;
    }

    public void selectGift(int i) {
        ((GiftHouse)current).getGift(i, this);
        status = Status.END_TURN;
    }

    public void buyTool(String response) {
        char[] choiceStr = response.toCharArray();
        if (choiceStr.length > 1)
            return;
        if((choiceStr[0] < '1' || choiceStr[0] > '9') && choiceStr[0] != 'F' )
            return;
        if (choiceStr[0] == 'F'){
            status = Status.END_TURN;
            return;
        }
        Tool tool = ((TollHouse)current).getTool(choiceStr[0] - '0');
        if (tool != null) {
            int toolPointPrice = tool.getPointPrice();
            if (points >= toolPointPrice) {
                tools.add(tool);
                points -= toolPointPrice;
            }
        }
        if(points < TollHouse.LOW_LIMIT)
            status = Status.END_TURN;
    }

    public void goToHospital(){
        status = Status.END_TURN;
    }

    public Land getCurrent() {
        return current;
    }

    public List<Land> getLands() {
        return lands;
    }

    public double getBalance() {
        return balance;
    }

    public int getWaitTurn() {
        return waitTurn;
    }

    public int getFreeTurn() {
        return freeTurn;
    }

    public int getPoints() {
        return points;
    }


    public List<Tool> getTools() {
        return tools;
    }

    public enum Status {WAIT_COMMAND, WAIT_RESPONSE, END_TURN, END_GAME,}
}
