package someGame.loader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileLoader {

    public static void loadLevel(){
        File directory = new File("./");
        System.out.println(directory.getAbsolutePath());
        try (Stream<String> stream = Files.lines(Paths.get("./src/main/resources/levels/1.txt"))) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
