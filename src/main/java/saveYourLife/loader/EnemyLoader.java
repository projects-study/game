package saveYourLife.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import saveYourLife.model.enemy.Enemy;

import java.io.File;
import java.io.IOException;

public class EnemyLoader {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Enemy loadEnemy(int id, float hpMultiplier){
        try {
            Enemy enemy = objectMapper.readValue(new File("./src/main/resources/enemies/"+ id +".json"), Enemy.class);
            enemy.setHp((int)(enemy.getHp()*hpMultiplier));
            return enemy;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
