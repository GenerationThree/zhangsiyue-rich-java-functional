package rich.environment;

import rich.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class Game implements GameControl {
    private List<Player> playerList;
    private Map gameMap;
    private Dice gameDice;
    private double initBalance;
    private Player currentPlayer;

    public Game() {
        playerList = new ArrayList<>();
        gameDice = new GameDice();
        gameMap = initMap();
        initBalance = 10000;
        currentPlayer = null;
    }

    public static Game createGameWithSpecifiedPlayer(Player...players){
        Game game = new Game();
        game.playerList.addAll(asList(players));
        return game;
    }

    private Map initMap(){
        List<Land> landList = new ArrayList<Land>(){{
            add(new StartPoint());
        }};

        for(int i = 0; i < 13; i++){
            landList.add(new Estate(null, 200));
        }

        landList.add(new Hospital());

        for(int i = 0; i < 13; i++){
            landList.add(new Estate(null, 200));
        }

        landList.add(new TollHouse());

        for(int i = 0; i < 6; i++){
            landList.add(new Estate(null, 500));
        }

        landList.add(new GiftHouse());

        for(int i = 0; i < 13; i++){
            landList.add(new Estate(null, 300));
        }

        landList.add(new Prison());

        for(int i = 0; i < 13; i++){
            landList.add(new Estate(null, 300));
        }

        landList.add(new MagicHouse());

        landList.add(new Mine(20));
        landList.add(new Mine(80));
        landList.add(new Mine(100));
        landList.add(new Mine(40));
        landList.add(new Mine(80));
        landList.add(new Mine(60));

        Map gameMap =  new GameMap(landList);

        return gameMap;
    }

    @Override
    public List<Player> getPlayerList() {
        return playerList;
    }

    @Override
    public boolean addPlayer(int id) {
        if (id < 1 || id > 4)
            return false;
        Optional<Player> player = playerList
                .stream()
                .filter(p -> p.getId() == id)
                .findAny();
        if (player.isPresent())
            return false;
        playerList.add(new Player(id, gameMap, gameDice, initBalance));
        return true;
    }

    @Override
    public Map getMap() {
        return gameMap;
    }

    @Override
    public boolean setInitBalance(double balance) {
        if(balance < 1000 || balance > 50000)
            return false;
        initBalance = balance;
        return true;
    }

    @Override
    public Player chooseNextPlayer() {
        if(currentPlayer == null)
            currentPlayer = playerList.get(0);
        else {
            int startIndex = playerList.indexOf(currentPlayer) + 1;
            for (int i = 0; i < playerList.size()-1 ; i ++){
                Player checkPlayer = playerList.get((i + startIndex) % playerList.size());
                if(checkPlayer.startTurn()) {
                    currentPlayer = checkPlayer;
                    break;
                }
            }
        }
        return currentPlayer;
    }

    @Override
    public void handleCommand(Command command) {
        switch (command.getType()){
            case SET_INIT_BALANCE:
                setInitBalance(Double.valueOf(command.getParameter()));
                break;
            case ADD_PLAYER:
                addPlayer(Integer.valueOf(command.getParameter()));
                break;
            case START_GAME:
                chooseNextPlayer();
                break;
            default:
                return;
        }
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
