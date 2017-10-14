package saveYourLife.loader;

import saveYourLife.model.map.Area;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
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

    public static Area[][] loadLevel() {
        System.out.println("size " + levels.keySet().size());
        int rand = random.nextInt(levels.keySet().size());
        System.out.println("rand " + rand);
        int level = new ArrayList<>(levels.keySet()).get(rand);
        levels.remove(level);
        System.out.println("level " + level);
        System.out.println("rest levels: " + levels.size());
        return FileLoader.loadLevel(level);
    }

}
