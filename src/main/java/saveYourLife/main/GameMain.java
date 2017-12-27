package saveYourLife.main;

import saveYourLife.enums.Tower;
import saveYourLife.image.ImageFactory;
import saveYourLife.loader.LevelFactory;
import saveYourLife.model.level.Area;
import saveYourLife.model.level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.Collectors;

public class GameMain extends JPanel implements Runnable, java.awt.event.MouseListener{

    private static GameMain instance;

    private static final long serialVersionUID = -2318489391810394155L;

    public final int WIDTH = 800;
    public final int HEIGHT = 600;

    private Thread thread;
    private boolean running;

    private BufferedImage image;
    private Graphics2D g;

    private final int FPS = 60;
    private double averageFPS;

    private static Level level;
    private ImageFactory imageFactory;

    private int menuX = -1;
    private int menuY = -1;
    private boolean buildMenu;

    private GameMain() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    public static synchronized GameMain getInstance() {
        return instance == null ? instance = new GameMain() : instance;
    }

    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void init() {
        imageFactory = ImageFactory.getInstance();
        level = LevelFactory.loadLevel();
        this.addMouseListener(this);
    }

    public void run() {
        running = true;
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        init();

        long startTime;
        long URDTimeMillis;
        long waitTime;

        long targetTime = 1000 / FPS;
        while (running) {
            int frameCount = 0;
            long totalTime = 0;
            startTime = System.nanoTime();

            gameUpdate();
            gameRender();
            gameDraw();

            URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - URDTimeMillis;

            try {
                Thread.sleep(waitTime);
            } catch (Exception e) {
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == FPS)
                averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
        }
    }

    private synchronized void gameUpdate() {
        level.update();
    }

    private synchronized void gameDraw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

    private synchronized void gameRender() {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        level.draw(g);
        renderBuildMenu();
    }

    private void renderBuildMenu() {
        if(menuX>=0 && menuY>=0){
            int x = menuX*50-35;
            int y = menuY*50+50-35;
            g.drawImage(imageFactory.getMenu(), x, y, null);
            x+=45;
            y+=45;
            int angle = 360/Tower.values().length;
            int r = 50;
            double[] v = {0, -1};
            for(Tower tower: Tower.values()){
                if(tower.isEnabled()) {
                    g.drawImage(imageFactory.getMinis().get(tower.getImageNo()), (int) (v[0] * r + x), (int) (v[1] * r + y), null);
                    double tmpX = v[0];
                    double tmpY = v[1];
                    v[0] = tmpX * Math.cos(Math.toRadians(angle)) - tmpY * Math.sin(Math.toRadians(angle));
                    v[1] = tmpX * Math.sin(Math.toRadians(angle)) + tmpY * Math.cos(Math.toRadians(angle));
                }
            }
        }
    }

    public static Level getLevel(){
        return level;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY()-50;
        int indX = x/50+1;
        int indY = y/50+1;
        if(!buildMenu){
            if(level.getGrid()[indY][indX].isTowerArea.getAsBoolean()){
                menuX=indX-1;
                menuY=indY-1;
                buildMenu=true;
            }else{
                menuX=-1;
                menuY=-1;
                buildMenu=false;
            }
        }else{
            if(level.getGrid()[indY][indX].isTowerArea.getAsBoolean()){
                menuX=indX-1;
                menuY=indY-1;
            }else{
                clickTower(e.getX(), e.getY());
                menuX=-1;
                menuY=-1;
                buildMenu=false;
            }
        }

    }

    private void clickTower(int mx, int my) {
        int x = menuX*50+10;
        int y = menuY*50+60;
        int angle = 360/Tower.values().length;
        int r = 50;
        double[] v = {0, -1};
        for(Tower tower: Tower.values()){
            if(tower.isEnabled()) {
                int towerX = (int) (v[0] * r + x);
                int towerY = (int) (v[1] * r + y);
                double tmpX = v[0];
                double tmpY = v[1];
                v[0] = tmpX * Math.cos(Math.toRadians(angle)) - tmpY * Math.sin(Math.toRadians(angle));
                v[1] = tmpX * Math.sin(Math.toRadians(angle)) + tmpY * Math.cos(Math.toRadians(angle));
                if (Math.abs(towerX - mx) <= 20 && Math.abs(towerY - my) <= 20) {
                    int indX = x / 50 + 1;
                    int indY = (y - 50) / 50 + 1;
                    Area area = level.getGrid()[indY][indX];
                    area.setTower(tower);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseClicked(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
