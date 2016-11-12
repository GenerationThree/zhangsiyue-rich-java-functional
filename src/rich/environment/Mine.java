package rich.environment;

import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import rich.Player;

public class Mine implements Land{

    private int points;

    public Mine(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
