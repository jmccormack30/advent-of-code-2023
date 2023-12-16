import java.io.*;
import java.util.*;

class Main {
	private static final Map<String, Integer> bagMap = new HashMap<>();

	static {
		bagMap.put("red", 12);
		bagMap.put("green", 13);
		bagMap.put("blue", 14);
	}

	public static void main(String[] args) throws Exception {
		String fileName = "input.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			int result = decodeFile(br);
			System.out.println(result);
		}
		catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}
		catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	private static int decodeFile(BufferedReader br) throws Exception {
		String line;
		int result = 0;

		while ((line = br.readLine()) != null) {
			int value = decodeLine_Part2(line);
			result += value;
		}

		return result;
	}

	private static int decodeLine_Part1(String line) {
		String[] arr = line.split(":");
		String gameNumStr = arr[0].split(" ")[1];
		int gameNum = Integer.parseInt(gameNumStr);

		String gameStr  = arr[1];
		String[] gameArr = gameStr.split(";");

		Map<String, Integer> cubeCountMap = new HashMap<>();

		for (String round: gameArr) {
			round = round.trim();
			String[] entryArr = round.split(",");
			for (String entry: entryArr) {
				entry = entry.trim();
				String[] elementArr = entry.split(" ");
				int count = Integer.parseInt(elementArr[0]);
				String color = elementArr[1];
				cubeCountMap.compute(color, (k, v) -> (v == null || count > v) ? count : v);
			}
		}

		boolean isPossible = true;
		int result = 0;

		for (Map.Entry<String, Integer> entry: cubeCountMap.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			isPossible = (isPossible && value <= bagMap.get(key));
		}

		if (isPossible) result = gameNum;
		return result;
	}

	private static int decodeLine_Part2(String line) {
		String[] arr = line.split(":");
		String gameNumStr = arr[0].split(" ")[1];
		int gameNum = Integer.parseInt(gameNumStr);

		String gameStr  = arr[1];
		String[] gameArr = gameStr.split(";");

		Map<String, Integer> cubeCountMap = new HashMap<>();

		for (String round: gameArr) {
			round = round.trim();
			String[] entryArr = round.split(",");
			for (String entry: entryArr) {
				entry = entry.trim();
				String[] elementArr = entry.split(" ");
				int count = Integer.parseInt(elementArr[0]);
				String color = elementArr[1];
				cubeCountMap.compute(color, (k, v) -> (v == null || count > v) ? count : v);
			}
		}

		int result = 1;

		for (Map.Entry<String, Integer> entry: cubeCountMap.entrySet()) {
			Integer value = entry.getValue();
			result *= value;
		}

		return result;
	}
}
