package saveYourLife.loader;

import saveYourLife.model.map.Area;
import saveYourLife.model.map.Level;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileLoader {

    public static Area[][] loadLevel(int level) {
        Area[][] grid = new Area[Level.HEIGHT][Level.WIDTH];
        try (Stream<String> stream = Files.lines(Paths.get("./src/main/resources/levels/" + level + ".txt"))) {
            stream.forEach(line -> {
                List<Area> areas = List.of(line.split("\\|")).stream().map(type -> new Area(Integer.parseInt(type))).collect(Collectors.toList());
                int gridRow = getNextRowIndex(grid);
                for (int i = 0; i < areas.size(); i++)
                    grid[gridRow][i] = areas.get(i);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return grid;
    }

    private static int getNextRowIndex(Area[][] grid) {
        for (int i = 0; i < Level.HEIGHT; i++)
            if (grid[i][0] == null) {
                return i;
            }
        return 0;
    }

}
