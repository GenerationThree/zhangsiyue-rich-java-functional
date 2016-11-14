package rich.environment;

public class Command {
    private Type type;
    private String firstParameter;
    private String lastParameter;

    public Command(Type type, String firstParameter, String lastParameter) {
        this.type = type;
        this.firstParameter = firstParameter;
        this.lastParameter = lastParameter;
    }

    public Type getType() {
        return type;
    }

    public String getFirstParameter() {
        return firstParameter;
    }

    public String getLastParameter() {
        return lastParameter;
    }

    public enum Type{
        SET_INIT_BALANCE,
        ADD_PLAYER,
        START_GAME,
        ROLL,
        SAY_YES,
        SAY_NO,
        SELECT_GIFT,
        BUY_TOOL,
        SELL_ESTATE,
        SELL_TOOL,
        USE_TOOL
    }
}
