package rich.environment;

public class Command {
    private Type type;
    private String parameter;

    public Command(Type type, String parameter) {
        this.type = type;
        this.parameter = parameter;
    }

    public Type getType() {
        return type;
    }

    public String getParameter() {
        return parameter;
    }

    public enum Type{
        SET_INIT_BALANCE,
        ADD_PLAYER,
        START_GAME,
        ROLL,
        SAY_YES
    }
}
