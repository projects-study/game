package someGame.player;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player {
	private int x;
	private int y;
	private MoveDir move;
	private Color color;

	public Player() {
		move = MoveDir.NONE;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void update() {
		move.move(this);
	}

	public void render(Graphics2D g) {
		g.setColor(color);
		g.fillRect(x, y, 30, 30);
	}

	public MoveDir getMove() {
		return move;
	}

	public void setMove(MoveDir move) {
		this.move = move;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
