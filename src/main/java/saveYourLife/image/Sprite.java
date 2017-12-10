package saveYourLife.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import saveYourLife.enums.MoveDir;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sprite {

    @JsonIgnore
    private BufferedImage image;
    private Map<MoveDir, ArrayList<Frame>> frames;

    public Sprite() {
        frames = new HashMap<>();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Map<MoveDir, ArrayList<Frame>> getFrames() {
        return frames;
    }

    public void setFrames(Map<MoveDir, ArrayList<Frame>> frames) {
        this.frames = frames;
    }
}
