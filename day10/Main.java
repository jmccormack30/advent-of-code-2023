import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {

	private static String PART_1 = "PART1";
	private static String PART_2 = "PART2";

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
			int value = 0;
			if (PART_1.equalsIgnoreCase(args[0])) {
				value = decodeFile_Part1(br);
			}
			else if (PART_2.equalsIgnoreCase(args[0])) {
				value = decodeFile_Part2(br);
			}
			System.out.println("\n" + value);
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
		Map.Entry<Integer, Integer> startCoords = populateTileArray(br, tileArr);
		int startRow = startCoords.getKey();
		int startColumn = startCoords.getValue();
		Tile startTile = tileArr[startRow][startColumn];

		Map<Integer, Set<Integer>> tilesOnPathMap = new HashMap<>();
		String nextDirection = getFirstTileDirection(tileArr, startRow, startColumn);
		int curRow = updateRowValue(startRow, nextDirection);
		int curColumn = updateColumnValue(startColumn, nextDirection);
		addToMap(tilesOnPathMap, curRow, curColumn);
		Tile currentTile = tileArr[curRow][curColumn];
		String fromDirection = updateFromDirection(nextDirection);

		updateTilesOnPathMap(tilesOnPathMap, tileArr, startTile, currentTile, nextDirection, fromDirection, curRow, curColumn);
		int totalPathTiles = tilesOnPathMap.values().stream().mapToInt(Set::size).sum();

		printMap(tileArr, tilesOnPathMap);
		return (int) Math.floor((totalPathTiles) / 2);
	}

	private static int decodeFile_Part2(BufferedReader br) throws Exception {
		Tile[][] tileArr = new Tile[140][140];
		Map.Entry<Integer, Integer> startCoords = populateTileArray(br, tileArr);
		int startRow = startCoords.getKey();
		int startColumn = startCoords.getValue();
		Tile startTile = tileArr[startRow][startColumn];

		Map<Integer, Set<Integer>> tilesOnPathMap = new HashMap<>();
		String nextDirection = getFirstTileDirection(tileArr, startRow, startColumn);
		int curRow = updateRowValue(startRow, nextDirection);
		int curColumn = updateColumnValue(startColumn, nextDirection);
		addToMap(tilesOnPathMap, curRow, curColumn);
		Tile currentTile = tileArr[curRow][curColumn];
		String fromDirection = updateFromDirection(nextDirection);

		updateTilesOnPathMap(tilesOnPathMap, tileArr, startTile, currentTile, nextDirection, fromDirection, curRow, curColumn);
		int tiles = calculateEnclosedTiles(tilesOnPathMap, tileArr);
		printMap(tileArr, tilesOnPathMap);
		return tiles;
	}

	private static int updateRowValue(int row, String direction) {
		if (Main.SOUTH.equals(direction)) return row + 1;
		if (Main.NORTH.equals(direction)) return row - 1;
		return row;
	}

	private static int updateColumnValue(int column, String direction) {
		if (Main.WEST.equals(direction)) return column - 1;
		if (Main.EAST.equals(direction)) return column + 1;
		return column;
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
		if (Main.NORTH.equals(direction)) curX -= 1;
		if (Main.SOUTH.equals(direction)) curX += 1;
		if (Main.EAST.equals(direction)) curY += 1;
		if (Main.WEST.equals(direction)) curY -= 1;

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
		Tile nextTile = null;
		if (startX - 1 >= 0) {
			nextTile = arr[startX - 1][startY];
			if (Main.SOUTH.equals(nextTile.getDirection1()) || Main.SOUTH.equals(nextTile.getDirection2())) return Main.NORTH;
		}
		if (startX + 1 < arr.length) {
			nextTile = arr[startX + 1][startY];
			if (Main.NORTH.equals(nextTile.getDirection1()) || Main.NORTH.equals(nextTile.getDirection2())) return Main.SOUTH;
		}
		if (startY - 1 >= 0) {
			nextTile = arr[startX][startY - 1];
			if (Main.EAST.equals(nextTile.getDirection1()) || Main.EAST.equals(nextTile.getDirection2())) return Main.WEST;
		}
		if (startY + 1 < arr[0].length) {
			nextTile = arr[startX][startY + 1];
			if (Main.WEST.equals(nextTile.getDirection1()) || Main.WEST.equals(nextTile.getDirection2())) return Main.EAST;
		}
		throw new RuntimeException("No valid next direction!");
	}

	private static Tile getFirstTile(Tile[][] arr, int startX, int startY, String fromDirection) {
		Tile nextTile = arr[startX - 1][startY];
		if (Main.SOUTH.equals(nextTile.getDirection1()) || Main.SOUTH.equals(nextTile.getDirection2())) {
			fromDirection = Main.SOUTH;
			return nextTile;
		}
		nextTile = arr[startX + 1][startY];
		if (Main.NORTH.equals(nextTile.getDirection1()) || Main.NORTH.equals(nextTile.getDirection2())) {
			fromDirection = Main.NORTH;
			return nextTile;
		}
		nextTile = arr[startX][startY - 1];
		if (Main.EAST.equals(nextTile.getDirection1()) || Main.EAST.equals(nextTile.getDirection2())) {
			fromDirection = Main.EAST;
			return nextTile;
		}
		nextTile = arr[startX][startY + 1];
		if (Main.WEST.equals(nextTile.getDirection1()) || Main.WEST.equals(nextTile.getDirection2())) {
			startX += 1;
			fromDirection = Main.WEST;
			return nextTile;
		}
		throw new RuntimeException("There is a dead end in the loop!");
	}

	private static Map.Entry<Integer, Integer> populateTileArray(BufferedReader br, Tile[][] tileArr) throws Exception {
		Map<Character, Tile> tileMap = getTileMap();

		Integer startRow = 0;
		Integer startColumn = 0;

		String line;
		int row = 0;
		while ((line = br.readLine()) != null) {
			int column = 0;
			for (char c: line.toCharArray()) {
				Tile tile = tileMap.get(c);
				if (Tile.START == tile) {
					startRow = row;
					startColumn = column;
				}
				tileArr[row][column] = tile;
				column += 1;
			}
			row += 1;
		}

		return new AbstractMap.SimpleEntry<>(startRow, startColumn);
	}

	private static void updateTilesOnPathMap(Map<Integer, Set<Integer>> tilesOnPathMap, Tile[][] tileArr, Tile startTile, Tile currentTile, String nextDirection, String fromDirection, int curRow, int curColumn) throws Exception {
		boolean loopComplete = false;

		while (!loopComplete) {
			nextDirection = getNextDirection(currentTile, fromDirection);
			curRow = updateRowValue(curRow, nextDirection);
			curColumn = updateColumnValue(curColumn, nextDirection);
			addToMap(tilesOnPathMap, curRow, curColumn);
			currentTile = tileArr[curRow][curColumn];
			fromDirection = updateFromDirection(nextDirection);
			if (currentTile == startTile) loopComplete = true;
		}
	}

	private static int calculateEnclosedTiles(Map<Integer, Set<Integer>> tilesOnPathMap, Tile[][] tileArr) throws Exception {
		int count = 0;
		int borders;
		Tile border = null;
		for (int row = 0; row < tileArr.length; row++) {
			borders = 0;
			for (int column = 0; column < tileArr[0].length; column++) {
				boolean isOnPath = isTileOnPath(tilesOnPathMap, row, column);
				Tile current = tileArr[row][column];
				if (isOnPath) {
					if (Tile.VERTICAL == current) {
						borders += 1;
						border = null;
					}
					else if (Tile.HORIZONTAL != current) {
						if (border != null) {
							borders += getBordersCrossed(border, current);
							border = null;
						}
						else {
							border = current;
						}
					}
				}
				else {
					if (borders % 2 == 1) {
						count += 1;
						tileArr[row][column] = Tile.ENCLOSED;
					}
					else {
						tileArr[row][column] = Tile.OUTSIDE;
					}
				}
			}
		}
		return count;
	}

	private static Map<Character, Tile> getTileMap() {
		return Arrays.stream(Tile.values()).collect(Collectors.toMap(Tile::getValue, obj -> obj));
	}

	private static void addToMap(Map<Integer, Set<Integer>> tilesOnPathMap, int row, int column) {
		tilesOnPathMap.computeIfAbsent(row, k -> new HashSet<>()).add(column);
	}

	private static boolean isTileOnPath(Map<Integer, Set<Integer>> tilesOnPathMap, int row, int column) {
		Set<Integer> set = tilesOnPathMap.get(row);
		return (set != null && !set.isEmpty()) ? set.contains(column) : false;
	}

	private static int getBordersCrossed(Tile startTile, Tile endTile) {
		return (startTile.getDirection1() == endTile.getDirection1() || startTile.getDirection2() == endTile.getDirection2()) ? 2 : 1;
	}

	private static void printMap(Tile[][] tileArr, Map<Integer, Set<Integer>> tilesOnPathMap) {
		for (int row = 0; row < tileArr.length; row++) {
			for (int column = 0; column < tileArr[0].length; column++) {
				boolean isOnPath = isTileOnPath(tilesOnPathMap, row, column);
				Tile currentTile = tileArr[row][column];
				if (isOnPath || Tile.ENCLOSED == currentTile || Tile.OUTSIDE == currentTile) {
					System.out.print(currentTile.getValue());
				}
				else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
}
