package rich.environment;

public interface Map {
    Land move(Land start, int step);
    Tool getTool(Land current);
}
