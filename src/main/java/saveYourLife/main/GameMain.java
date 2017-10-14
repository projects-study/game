package saveYourLife.main;

import saveYourLife.listener.mouse.MouseListener;
import saveYourLife.model.map.Level;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GameMain extends JPanel implements Runnable {

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

    private Level level;

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
        level = Level.getInstance();
        this.addMouseListener(new MouseListener());
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
    }


}
