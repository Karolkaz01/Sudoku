package Game;

import java.util.Comparator;

public class CompareByMistakes implements Comparator<Game>{

    @Override
    public int compare(Game o1, Game o2) {
        return (o1.getMistakes()-o2.getMistakes());
    }
}
