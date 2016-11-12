package rich.environment;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class GameMap implements Map {
    private List<Land> landList;

    public GameMap(Land ...lands) {
        this.landList = new ArrayList<Land>(){{
            addAll(asList(lands));
        }};
    }

    @Override
    public Land move(Land start, int step) {
        Land current = start;
        int statIndex = landList.indexOf(start);
        for (int i = 1; i <= step; i++){
            current = landList.get((statIndex + i) % landList.size());
            if (current instanceof Block)
                return current;
        }
        return current;
    }
}
