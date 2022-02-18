package Game;

import java.util.Comparator;

public class CompareByName implements Comparator<Game> {


    @Override
    public int compare(Game o1, Game o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
