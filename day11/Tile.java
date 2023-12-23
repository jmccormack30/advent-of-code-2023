public class Tile {
	private boolean isGalaxy;
	private int number;

	public Tile(boolean isGalaxy, int number) {
		this.isGalaxy = isGalaxy;
		this.number = number;
	}

	public void setIsGalaxy(boolean isGalaxy) {
		this.isGalaxy = isGalaxy;
	}

	public boolean isGalaxy() {
		return isGalaxy;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	@Override
	public String toString() {
		return (isGalaxy) ? "#" : ".";
	}
}
