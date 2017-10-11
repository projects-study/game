package someGame.player;

public enum MoveDir implements MoveDirInterface {
	RIGHT {

		@Override
		public void move(Player player) {
			player.setX(player.getX() + 5);
		}

	},
	LEFT {

		@Override
		public void move(Player player) {
			player.setX(player.getX() - 5);
		}

	},
	UP {
		
		@Override
		public void move(Player player) {
			player.setY(player.getY() - 5);
		}

	},
	DOWN {
		
		@Override
		public void move(Player player) {
			player.setY(player.getY() + 5);
		}
		
	},
	NONE {

		@Override
		public void move(Player player) {
		}

	}
}
