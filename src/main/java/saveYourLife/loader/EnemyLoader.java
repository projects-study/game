package saveYourLife.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import saveYourLife.model.enemy.Enemy;
import saveYourLife.model.level.Level;

import java.io.File;
import java.io.IOException;

public class EnemyLoader {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Enemy loadEnemy(int id){
        try {
            return objectMapper.readValue(new File("./src/main/resources/enemies/"+ id +".json"), Enemy.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
