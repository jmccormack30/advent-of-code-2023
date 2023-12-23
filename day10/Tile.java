public enum Tile {
	VERTICAL('|', Main.NORTH, Main.SOUTH),
	HORIZONTAL('-', Main.EAST, Main.WEST),
	NORTH_EAST('L', Main.NORTH, Main.EAST),
	NORTH_WEST('J', Main.NORTH, Main.WEST),
	SOUTH_WEST('7', Main.SOUTH, Main.WEST),
	SOUTH_EAST('F', Main.SOUTH, Main.EAST),
	GROUND('.', null, null),
	START('S', null, null),
	ENCLOSED('I', null, null),
	OUTSIDE('0', null, null);

	char value;
	private final String direction1;
	private final String direction2;

	Tile(char value, String direction1, String direction2) {
		this.value = value;
		this.direction1 = direction1;
		this.direction2 = direction2;
	}

	public char getValue() {
		return value;
	}

	public String getDirection1() {
		return direction1;
	}

	public String getDirection2() {
		return direction2;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
