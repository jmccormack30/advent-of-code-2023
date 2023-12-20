import java.io.*;
import java.util.*;

public class Main {

	public static final String NORTH = "NORTH";
	public static final String EAST = "EAST";
	public static final String SOUTH = "SOUTH";
	public static final String WEST = "WEST";

	private final static Set<String> directions = new HashSet<>();
	static {
		directions.add(NORTH);
		directions.add(EAST);
		directions.add(WEST);
		directions.add(SOUTH);
	}

	public static void main(String[] args) throws Exception {
		String fileName = "input.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			long startTime = System.nanoTime();
			int value = decodeFile_Part1(br);
			long endTime = System.nanoTime();
			System.out.println("\n" + value);
			System.out.println(getExecutionTimeString(startTime, endTime));
		}
		catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}
		catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	private static int decodeFile_Part1(BufferedReader br) throws Exception {
		Tile[][] tileArr = new Tile[140][140];
		String line;
		int i = 0;
		boolean loopComplete = false;
		Tile start = null;
		int startX = 0;
		int startY = 0;
		Map<Character, Tile> tileMap = getTileMap();
		while ((line = br.readLine()) != null) {
			int j = 0;
			for (char c: line.toCharArray()) {
				if (Tile.START == tileMap.get(c)) {
					start = tileMap.get(c);
					startX = i;
					startY = j;
				}
				tileArr[i][j] = tileMap.get(c);
				j += 1;
			}
			i += 1;
		}
		int steps = 0;
		Tile current = start;
		int curX = startX;
		int curY = startY;
		String fromDirection = null;

		System.out.println("X: " + startX + ", Y: " + startY + "     " + current.getValue());
		String nextDirection = getFirstTileDirection(tileArr, startX, startY);
		curX = updateXValue(curX, nextDirection);
		curY = updateYValue(curY, nextDirection);
		current = tileArr[curX][curY];
		fromDirection = updateFromDirection(nextDirection);
		steps += 1;

		while (!loopComplete) {
			System.out.println("X: " + curX + ", Y: " + curY + "     " + current.getValue());
			nextDirection = getNextDirection(current, fromDirection);
			curX = updateXValue(curX, nextDirection);
			curY = updateYValue(curY, nextDirection);
			current = tileArr[curX][curY];
			fromDirection = updateFromDirection(nextDirection);
			steps += 1;
			if (current == start) loopComplete = true;
		}
		return steps;
	}

	private static int updateXValue(int x, String direction) {
		if (Main.EAST.equals(direction)) return x + 1;
		if (Main.WEST.equals(direction)) return x - 1;
		return x;
	}

	private static int updateYValue(int y, String direction) {
		if (Main.NORTH.equals(direction)) return y - 1;
		if (Main.SOUTH.equals(direction)) return y + 1;
		return y;
	}

	private static String getNextDirection(Tile tile, String fromDirection) throws Exception {
		for (String s: directions) {
			if (!s.equals(fromDirection)) {
				if (s.equals(tile.getDirection1())) {
					return tile.getDirection1();
				}
				if (s.equals(tile.getDirection2())) {
					return tile.getDirection2();
				}
			}
		}
		throw new RuntimeException("There is a dead end in the loop!");
	}

	private static Tile getNextTile(Tile[][] tileArr, int curX, int curY, String direction) {
		if (Main.NORTH.equals(direction)) {
			curY -= 1;
		}
		if (Main.SOUTH.equals(direction)) {
			curY += 1;
		}
		if (Main.EAST.equals(direction)) {
			curX += 1;
		}
		if (Main.WEST.equals(direction)) {
			curX -= 1;
		}
		return tileArr[curX][curY];
	}

	private static String updateFromDirection(String nextDirection) {
		if (Main.NORTH.equals(nextDirection)) return Main.SOUTH;
		if (Main.SOUTH.equals(nextDirection)) return Main.NORTH;
		if (Main.EAST.equals(nextDirection)) return Main.WEST;
		if (Main.WEST.equals(nextDirection)) return Main.EAST;
		throw new RuntimeException("No valid next direction!");
	}

	private static String getFirstTileDirection(Tile[][] arr, int startX, int startY) {
		Tile nextTile = arr[startX][startY - 1];
		if (Main.SOUTH.equals(nextTile.getDirection1()) || Main.SOUTH.equals(nextTile.getDirection2())) return Main.NORTH;
		nextTile = arr[startX][startY + 1];
		if (Main.NORTH.equals(nextTile.getDirection1()) || Main.NORTH.equals(nextTile.getDirection2())) return Main.SOUTH;
		nextTile = arr[startX - 1][startY];
		if (Main.EAST.equals(nextTile.getDirection1()) || Main.EAST.equals(nextTile.getDirection2())) return Main.WEST;
		nextTile = arr[startX + 1][startY];
		if (Main.WEST.equals(nextTile.getDirection1()) || Main.WEST.equals(nextTile.getDirection2())) return Main.EAST;
		throw new RuntimeException("No valid next direction!");
	}

	private static Tile getFirstTile(Tile[][] arr, int startX, int startY, String fromDirection) {
		Tile nextTile = arr[startX][startY - 1];
		if (Main.SOUTH.equals(nextTile.getDirection1()) || Main.SOUTH.equals(nextTile.getDirection2())) {
			fromDirection = Main.SOUTH;
			return nextTile;
		}
		nextTile = arr[startX][startY + 1];
		if (Main.NORTH.equals(nextTile.getDirection1()) || Main.NORTH.equals(nextTile.getDirection2())) {
			fromDirection = Main.NORTH;
			return nextTile;
		}
		nextTile = arr[startX - 1][startY];
		if (Main.EAST.equals(nextTile.getDirection1()) || Main.EAST.equals(nextTile.getDirection2())) {
			fromDirection = Main.EAST;
			return nextTile;
		}
		nextTile = arr[startX + 1][startY];
		if (Main.WEST.equals(nextTile.getDirection1()) || Main.WEST.equals(nextTile.getDirection2())) {
			startX += 1;
			fromDirection = Main.WEST;
			return nextTile;
		}
		throw new RuntimeException("There is a dead end in the loop!");
	}

	private static Map<Character, Tile> getTileMap() {
		Map<Character, Tile> tileMap = new HashMap<>();
		for (Tile tile: Tile.values()) {
			tileMap.put(tile.getValue(), tile);
		}
		return tileMap;
	}

	private static String getExecutionTimeString(long startTime, long endTime) {
		long elapsedTimeInNanoseconds = endTime - startTime;
    	double elapsedTimeInSeconds = (double) elapsedTimeInNanoseconds / 1_000_000_000;
		double minutes = Math.floor(elapsedTimeInSeconds / 60);
		double seconds = (elapsedTimeInSeconds % 60);
		int wholeSeconds = (int) seconds;
		double decimalSeconds = seconds - wholeSeconds;
		double milliseconds = Math.floor(decimalSeconds * 1000);
		return "Elapsed time: " + (int)minutes + " minutes, " + (int)seconds + " seconds, " + (int)milliseconds + " ms.";
	}
}
