package saveYourLife.main;

import saveYourLife.enums.Tower;
import saveYourLife.image.ImageFactory;
import saveYourLife.loader.LevelFactory;
import saveYourLife.model.level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

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
            System.out.println("ANGLE: "+angle);
            int r = 50;
            double[] v = {0, -1};
            for(Tower tower: Tower.values()){
                g.drawImage(imageFactory.getMinis().get(tower.getImageNo()), (int) (v[0]*r+x), (int) (v[1]*r+y), null);
                System.out.println(tower.getImageNo()+" "+v[0]+" "+v[1]);
                v[0] = v[0]*Math.cos(Math.toRadians(angle))-v[1]*Math.sin(Math.toRadians(angle));
                v[1] = v[0]*Math.sin(Math.toRadians(angle))+v[1]*Math.cos(Math.toRadians(angle));

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
        if(level.getGrid()[indY][indX].isTowerArea.getAsBoolean()){
            menuX=indX-1;
            menuY=indY-1;
        }else{
            menuX=-1;
            menuY=-1;
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
