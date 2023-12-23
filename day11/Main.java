import java.io.*;
import java.util.*;
import java.util.stream.*;

class Main {

	private static String PART_1 = "PART1";
	private static String PART_2 = "PART2";

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
		Tile[][] mapArr = getMapArray(br);
		Map<Tile, Map.Entry<Integer, Integer>> galaxyLocationMap = getGalaxyLocationMap(mapArr);
		Tile[] galaxies = galaxyLocationMap.keySet().toArray(new Tile[0]);
		System.out.println("\nGalaxies: " + galaxies + "\n");

		for (Tile[] row: mapArr) {
			for (Tile column: row) {
				System.out.print(column);
			}
			System.out.println();
		}
		System.out.println();
		for (Map.Entry<Tile, Map.Entry<Integer, Integer>> entry: galaxyLocationMap.entrySet()) {
			Map.Entry<Integer, Integer> coords = entry.getValue();
			System.out.println("Galaxy " + entry.getKey() + ": Row = " + coords.getKey() + ", Column = " + coords.getValue());
		}

		int sum = 0;
		for (int i = 0; i < galaxies.length; i++) {
			Tile galaxy1 = galaxies[i];
			for (int j = i + 1; j < galaxies.length; j++) {
				Tile galaxy2 = galaxies[j];
				int distance = getShortestDistance(galaxy1, galaxy2, galaxyLocationMap);
				sum += distance;
			}
		}

		return sum;
	}

	private static int decodeFile_Part2(BufferedReader br) throws Exception {
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
		return -1;
	}

	private static int getShortestDistance(Tile galaxy1, Tile galaxy2, Map<Tile, Map.Entry<Integer, Integer>> galaxyLocationMap) {
		Map.Entry<Integer, Integer> startCoords = galaxyLocationMap.get(galaxy1);
		int startX = startCoords.getKey();
		int startY = startCoords.getValue();

		Map.Entry<Integer, Integer> endCoords = galaxyLocationMap.get(galaxy2);
		int endX = endCoords.getKey();
		int endY = endCoords.getValue();

		return (int) (Math.abs(endX - startX) + Math.abs(endY - startY));
	}

	private static Map<Tile, Map.Entry<Integer, Integer>> getGalaxyLocationMap(Tile[][] mapArr) {
		Map<Tile, Map.Entry<Integer, Integer>> galaxyLocationMap = new HashMap<>();

		for (int row = 0; row < mapArr.length; row++) {
			Tile[] rowArr = mapArr[row];
			for (int column = 0; column < rowArr.length; column++) {
				Tile tile = rowArr[column];
				if (tile.isGalaxy()) {
					galaxyLocationMap.put(tile, new AbstractMap.SimpleEntry<>(row, column));
				}
			}
		}
		return galaxyLocationMap;
	}

	private static Tile[][] getMapArray(BufferedReader br) throws Exception {
		Map<Integer, char[]> rowsMap = new HashMap<>();
		Map<Integer, char[]> columnsMap = new HashMap<>();

		String line;
		int row = 0;
		while ((line = br.readLine()) != null) {
			char[] cArr = line.toCharArray();
			rowsMap.put(row++, cArr);
			if (!line.contains("#")) {
				rowsMap.put(row++, cArr);
			}
		}

		int columnIndex = 0;
		int columnLength = rowsMap.size();

		for (int column = 0; column < 140; column++) {
			StringBuilder columnStrBuilder = new StringBuilder();
			for (row = 0; row < columnLength; row++) {
				columnStrBuilder.append(String.valueOf(rowsMap.get(row)[column]));
			}
			String columnStr = columnStrBuilder.toString();
			char[] columnArr = columnStr.toCharArray();
			columnsMap.put(columnIndex++, columnArr);
			if (!columnStr.contains("#")) {
				columnsMap.put(columnIndex++, columnArr);
			}
		}

		Tile[][] mapArr = new Tile[columnLength][columnsMap.size()];
		int galaxyNum = 1;

		for (int column = 0; column < columnsMap.size(); column++) {
			char[] columnArr = columnsMap.get(column);
			for (row = 0; row < columnLength; row++) {
				char c = columnArr[row];
				Tile tile;
				if ('#' == c) {
					tile = new Tile(true, galaxyNum++);
				}
				else {
					tile = new Tile(false, 0);
				}
				mapArr[row][column] = tile;
			}
		}

		return mapArr;
	}
}
