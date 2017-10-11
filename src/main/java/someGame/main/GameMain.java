package someGame.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import someGame.player.MoveDir;
import someGame.player.Player;

public class GameMain extends JPanel implements Runnable, KeyListener {

	private static final long serialVersionUID = -2318489391810394155L;

	public static int WIDTH = 800;
	public static int HEIGHT = 600;

	private Thread thread;
	private boolean running;

	private BufferedImage image;
	private Graphics2D g;

	private final int FPS = 60;
	private double averageFPS;

	private Player player1;
	private Player player2;
	private Set<Integer> keyEvents;

	public GameMain() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void init() {
		player1 = new Player();
		player1.setX(200);
		player1.setY(500);
		player1.setColor(Color.ORANGE);
		player2 = new Player();
		player2.setX(200);
		player2.setY(500);
		player2.setColor(Color.BLUE);
		keyEvents = new LinkedHashSet<>();
		this.addKeyListener(this);
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
			if (frameCount == FPS) {
				averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
			}
		}
	}

	private synchronized void gameUpdate() {
		updateKeys();
		player1.update();
		player2.update();
	}

	private synchronized void gameDraw() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}

	private synchronized void gameRender() {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		player1.render(g);
		player2.render(g);
	}

	@Override
	public synchronized void keyPressed(KeyEvent e) {
		keyEvents.add(e.getKeyCode());
	}

	@Override
	public synchronized void keyReleased(KeyEvent e) {
		keyEvents.remove(e.getKeyCode());
	}

	@Override
	public synchronized void keyTyped(KeyEvent e) {
		keyPressed(e);
	}
	
	private void updateKeys(){
		if(keyEvents.stream().filter(e -> e == KeyEvent.VK_RIGHT || e == KeyEvent.VK_LEFT || e == KeyEvent.VK_UP || e == KeyEvent.VK_DOWN).collect(Collectors.toList()).isEmpty())
			player1.setMove(MoveDir.NONE);
		if(keyEvents.stream().filter(e -> e == KeyEvent.VK_D || e == KeyEvent.VK_A || e == KeyEvent.VK_W || e == KeyEvent.VK_S).collect(Collectors.toList()).isEmpty())
			player2.setMove(MoveDir.NONE);
		keyEvents.forEach(e -> {
			if(e == KeyEvent.VK_RIGHT)
				player1.setMove(MoveDir.RIGHT);
			if(e == KeyEvent.VK_LEFT)
				player1.setMove(MoveDir.LEFT);
			if(e == KeyEvent.VK_UP)
				player1.setMove(MoveDir.UP);
			if(e == KeyEvent.VK_DOWN)
				player1.setMove(MoveDir.DOWN);
			if(e == KeyEvent.VK_D)
				player2.setMove(MoveDir.RIGHT);
			if(e == KeyEvent.VK_A)
				player2.setMove(MoveDir.LEFT);
			if(e == KeyEvent.VK_W)
				player2.setMove(MoveDir.UP);
			if(e == KeyEvent.VK_S)
				player2.setMove(MoveDir.DOWN);
		});
	}

}
