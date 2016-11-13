package rich.environment;

import rich.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Game implements GameControl {
    private List<Player> playerList;
    private Map gameMap;
    private Dice gameDice;
    private double initBalance;

    public Game() {
        playerList = new ArrayList<>();
        gameDice = new GameDice();
        gameMap = initMap();
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
        initBalance = balance;
        return true;
    }
}
