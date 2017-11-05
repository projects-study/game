package saveYourLife.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import saveYourLife.model.level.Area;
import saveYourLife.model.level.Level;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FileLoader {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Level loadLevel(int lev) {
        try {
            JsonLevel jsonMap = objectMapper.readValue(new File("./src/main/resources/levels/"+ lev +".json"), JsonLevel.class);
            return new Level(jsonMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
