package rich.environment;

import org.junit.Before;
import org.junit.Test;
import rich.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameControlHandleCommandTest {

    private Game game;
    private Command startGameCommand;
    private Command rollCommand;
    private Command addPlayerCommand;
    private Command setBalanceCommand;

    private static final String START_BALANCE = "2000";
    private Command sayYesCommand;
    private Command sayNoCommand;
    private Command selectGiftCommand;
    private Command buyToolCommand;
    private Command sellEstateCommand;
    private Command sellToolCommand;

    @Before
    public void setUp() throws Exception {
        game = new Game();
        startGameCommand = new Command(Command.Type.START_GAME, "");
        rollCommand = new Command(Command.Type.ROLL, "");
        addPlayerCommand = new Command(Command.Type.ADD_PLAYER, "1");
        setBalanceCommand = new Command(Command.Type.SET_INIT_BALANCE, START_BALANCE);
        sayYesCommand = new Command(Command.Type.SAY_YES, "");
        sayNoCommand = new Command(Command.Type.SAY_NO, "");
        selectGiftCommand = new Command(Command.Type.SELECT_GIFT, "1");
        buyToolCommand = new Command(Command.Type.BUY_TOOL, "1");
        sellEstateCommand = new Command(Command.Type.SELL_ESTATE, "1");
        sellToolCommand = new Command(Command.Type.SELL_TOOL, "1");
    }

    @Test
    public void should_handle_set_init_balance_command() throws Exception {
        game.handleCommand(setBalanceCommand);
        game.addPlayer(1);

        Player player = game.getPlayerList().get(0);
        assertThat(player.getBalance(), is(Double.valueOf(START_BALANCE)));
    }

    @Test
    public void should_handle_add_player_command() throws Exception {
        assertThat(game.getPlayerList().size(), is(0));

        game.handleCommand(addPlayerCommand);
        assertThat(game.getPlayerList().size(), is(1));
    }

    @Test
    public void should_handle_start_game_test() throws Exception {
        assertThat(game.getCurrentPlayer(), is(nullValue()));
        game.addPlayer(1);

        game.handleCommand(startGameCommand);

        assertThat(game.getCurrentPlayer(), notNullValue());
        assertThat(game.getCurrentPlayer().getId(), is(1));
    }

    @Test
    public void should_handle_roll_command() throws Exception {
        Map map = mock(Map.class);
        Dice dice = mock(Dice.class);
        when(dice.next()).thenReturn(1);
        Land destination = mock(Land.class);
        when(map.move(any(), eq(1), any())).thenReturn(destination);
        GameControl game = Game.createGameWithSpecifiedMapAndDice(map, dice);
        game.addPlayer(1);
        game.handleCommand(startGameCommand);
        Player player = game.getCurrentPlayer();

        game.handleCommand(rollCommand);

        assertThat(player.getCurrent(), is(destination));
    }

    @Test
    public void should_handle_say_yes_after_roll_to_empty() throws Exception {
        Map map = mock(Map.class);
        Dice dice = mock(Dice.class);
        when(dice.next()).thenReturn(1);
        Estate destination = new Estate(null, 200);
        when(map.move(any(), eq(1), any())).thenReturn(destination);
        GameControl game = Game.createGameWithSpecifiedMapAndDice(map, dice);
        game.handleCommand(setBalanceCommand);
        game.addPlayer(1);
        game.handleCommand(startGameCommand);
        Player player = game.getCurrentPlayer();

        game.handleCommand(rollCommand);
        game.handleCommand(sayYesCommand);

        assertThat(player.getCurrent(), is(destination));
        assertThat(destination.getOwner(), is(player));
        assertThat(player.getBalance(), is(Double.valueOf(START_BALANCE)-200));
    }

    @Test
    public void should_handle_say_no_command_when_roll_to_empty() throws Exception {
        Map map = mock(Map.class);
        Dice dice = mock(Dice.class);
        when(dice.next()).thenReturn(1);
        Estate destination = new Estate(null, 200);
        when(map.move(any(), eq(1), any())).thenReturn(destination);
        GameControl game = Game.createGameWithSpecifiedMapAndDice(map, dice);
        String balance = "2000";
        game.handleCommand(setBalanceCommand);
        game.addPlayer(1);
        game.handleCommand(startGameCommand);
        Player player = game.getCurrentPlayer();

        game.handleCommand(rollCommand);
        game.handleCommand(sayNoCommand);

        assertThat(player.getCurrent(), is(destination));
        assertThat(destination.getOwner(), is(nullValue()));
        assertThat(player.getBalance(), is(Double.valueOf(balance)));
    }

    @Test
    public void should_handle_select_gift_command() throws Exception {
        Map map = mock(Map.class);
        Dice dice = mock(Dice.class);
        when(dice.next()).thenReturn(1);
        GiftHouse giftHouse = new GiftHouse();
        when(map.move(any(), eq(1), any())).thenReturn(giftHouse);
        GameControl game = Game.createGameWithSpecifiedMapAndDice(map, dice);
        game.handleCommand(setBalanceCommand);
        game.addPlayer(1);
        game.handleCommand(startGameCommand);
        Player player = game.getCurrentPlayer();

        game.handleCommand(rollCommand);
        game.handleCommand(selectGiftCommand);

        assertThat(player.getBalance(), is(Double.valueOf(START_BALANCE) + GiftHouse.BONUS));
    }

    @Test
    public void should_handle_buy_tool_command() throws Exception {
        Map map = mock(Map.class);
        Dice dice = mock(Dice.class);
        when(dice.next()).thenReturn(1);
        Mine minePoint = new Mine(50);
        ToolHouse toolHouse = new ToolHouse();
        when(map.move(any(), eq(1), any())).thenReturn(minePoint);
        GameControl game = Game.createGameWithSpecifiedMapAndDice(map, dice);
        game.handleCommand(setBalanceCommand);
        game.addPlayer(1);
        game.handleCommand(startGameCommand);
        Player player = game.getCurrentPlayer();

        game.handleCommand(rollCommand);
        int prePoints = player.getPoints();
        assertThat(prePoints, is(50));
        assertThat(player.getTools().size(), is(0));
        when(map.move(any(), eq(1), any())).thenReturn(toolHouse);
        game.handleCommand(rollCommand);

        game.handleCommand(buyToolCommand);

        assertThat(player.getTools().size(), is(1));
    }

    @Test
    public void should_handle_sell_estate_command() throws Exception {
        Map map = mock(Map.class);
        Dice dice = mock(Dice.class);
        when(dice.next()).thenReturn(1);
        Estate estate = new Estate(null, 200);
        when(map.move(any(), eq(1), any())).thenReturn(estate);
        GameControl game = Game.createGameWithSpecifiedMapAndDice(map, dice);
        game.handleCommand(setBalanceCommand);
        game.addPlayer(1);
        game.handleCommand(startGameCommand);
        Player player = game.getCurrentPlayer();

        game.handleCommand(rollCommand);
        game.handleCommand(sayYesCommand);
        double preBalance = player.getBalance();
        assertThat(preBalance, is(Double.valueOf(START_BALANCE) - 200));

        game.handleCommand(rollCommand);
        when(map.sellEstate(any(), anyInt())).thenReturn(estate);
        game.handleCommand(sellEstateCommand);
        assertThat(player.getBalance(), is(Double.valueOf(START_BALANCE) + 200));
        assertThat(player.getLands().size(), is(0));
    }

    @Test
    public void should_handle_buy_and_sell_tool_command() throws Exception {
        Dice dice = mock(Dice.class);
        when(dice.next()).thenReturn(1);
        Land startPoint = new StartPoint();
        Land giftHouse = new GiftHouse();
        Land toolHouse = new ToolHouse();
        Map map = new GameMap(startPoint, giftHouse, toolHouse);
        GameControl game = Game.createGameWithSpecifiedMapAndDice(map, dice);
        selectGiftCommand = new Command(Command.Type.SELECT_GIFT, "2");

        game.handleCommand(setBalanceCommand);
        game.addPlayer(1);
        game.handleCommand(startGameCommand);
        Player player = game.getCurrentPlayer();

        game.handleCommand(rollCommand);
        game.handleCommand(selectGiftCommand);
        assertThat(player.getPoints(), is(200));

        game.handleCommand(rollCommand);
        game.handleCommand(buyToolCommand);
        assertThat(player.getTools().size(), is(1));
        assertThat(player.getPoints(), is(200 - Tool.Type.BLOCK.getPointPrice()));

        game.handleCommand(sellToolCommand);
        assertThat(player.getTools().size(), is(0));
        assertThat(player.getPoints(), is(200));
    }
}
