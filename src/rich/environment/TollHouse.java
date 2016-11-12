package rich.environment;

import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import rich.Player;

public class TollHouse implements Land {
    public static final int LOW_LIMIT = 30;

    public Tool getTool(int choice) {
        switch (choice){
            case 1:
                return new Tool(Tool.Type.BLOCK);
            case 2:
                return new Tool(Tool.Type.ROBOT);
            case 3:
                return new Tool(Tool.Type.BOMB);
            default:
                return null;

        }
    }
}
