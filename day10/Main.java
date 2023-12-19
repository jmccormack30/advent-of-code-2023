import java.io.*;
import java.util.*;

public class Main {

	public static final String NORTH = "NORTH";
	public static final String EAST = "EAST";
	public static final String SOUTH = "SOUTH";
	public static final String WEST = "WEST";

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
		Map<Character, Tile> tileMap = getTileMap();
		while ((line = br.readLine()) != null) {
			int j = 0;
			for (char c: line.toCharArray()) {
				tileArr[i][j] = tileMap.get(c);
				j += 1;
			}
			i += 1;
		}
		for (Tile[] arr: tileArr) {
			for (Tile t: arr) {
				System.out.print(t);
			}
			System.out.println();
		}
		return -1;
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
