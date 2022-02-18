package Game;

import java.util.Comparator;

public class CompareByDifficulty implements Comparator<Game> {


    @Override
    public int compare(Game o1, Game o2) {
        int o1Dif=0;
        if(o1.getDifficulty().equals("easy")) o1Dif=0;
        else if(o1.getDifficulty().equals("medium")) o1Dif=1;
        else if(o1.getDifficulty().equals("hard")) o1Dif=2;

        int o2Dif=0;
        if(o2.getDifficulty().equals("easy")) o2Dif=0;
        else if(o2.getDifficulty().equals("medium")) o2Dif=1;
        else if(o2.getDifficulty().equals("hard")) o2Dif=2;

        return (o1Dif-o2Dif);
    }
}
