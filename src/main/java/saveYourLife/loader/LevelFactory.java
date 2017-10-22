package saveYourLife.loader;

import saveYourLife.model.level.Level;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class LevelFactory {

    private static Map<Integer, Boolean> levels;
    private static Random random;

    static {
        random = new Random();
        levels = new HashMap<>();
        int countLevels = new File("./src/main/resources/levels").list().length;
        IntStream.range(1, countLevels + 1).forEach(i -> levels.put(i, false));
    }

    public static Level loadLevel() {
        int rand = random.nextInt(levels.keySet().size());
        int level = new ArrayList<>(levels.keySet()).get(rand);
        levels.remove(level);
        return FileLoader.loadLevel(level);
    }

}
