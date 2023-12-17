import java.io.*;
import java.util.*;

class Main {
	public static void main(String[] args) throws Exception {
		String fileName = "input.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			int value = decodeFile_Part2(br);
			System.out.println(value);
		}
		catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}
		catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	private static int decodeFile_Part1(BufferedReader br) throws Exception {
		char[][] schematic = new char[140][140];
		String line;

		int i = 0;
		while ((line = br.readLine()) != null) {
			char[] arr = line.toCharArray();
			schematic[i] = arr;
			i++;
		}

		int sum = 0;
		StringBuilder number = new StringBuilder();
		boolean foundNum = false;
		int start = -1;
		int end = -1;

		for (i = 0; i < schematic.length; i++) {
			char[] row = schematic[i];
			for (int j = 0; j < row.length; j++) {
				char c = row[j];

				if (Character.isDigit(c)) {
					if (!foundNum) {
						start = j;
						foundNum = true;
					}
					number.append(String.valueOf(c));
				}

				if (!Character.isDigit(c) || j == row.length - 1) {
					boolean isValid = false;
					int num = 0;

					if (foundNum) {
						num = Integer.parseInt(number.toString());
						end = (j == row.length - 1) ? j : (j - 1);
						isValid = isValid_Part1(schematic, row, i, start - 1, end + 1);
					}

					number = new StringBuilder();
					foundNum = false;
					start = -1;
					end = -1;

					if (isValid) {
						sum += num;
						isValid = false;
					}
				}
			}
		}

		return sum;
	}

	private static boolean isValid_Part1(char[][] schematic, char[] row, int r, int first, int last) {
		if (first >= 0 && '.' != row[first]) {
			return true;
		}
		if (last < row.length && '.' != row[last]) {
			return true;
		}

		if (first < 0) first = 1;
		if (last >= row.length) last = row.length - 1;
		int above = r - 1;
		int below = r + 1;
		if (above >= 0) {
			char[] aboveRow = schematic[above];
			for (int i = first; i <= last; i++) {
				char c = aboveRow[i];
				if (!Character.isDigit(c) && '.' != c) {
					return true;
				}
			}
		}
		if (below < schematic.length) {
			char[] belowRow = schematic[below];
			for (int i = first; i <= last; i++) {
				char c = belowRow[i];
				if (!Character.isDigit(c) && '.' != c) {
					return true;
				}
			}
		}
		return false;
	}

	private static int decodeFile_Part2(BufferedReader br) throws Exception {
		char[][] schematic = new char[140][140];
		String line;

		int i = 0;
		while ((line = br.readLine()) != null) {
			char[] arr = line.toCharArray();
			schematic[i] = arr;
			i++;
		}

		int sum = 0;
		StringBuilder number = new StringBuilder();
		boolean foundNum = false;
		int start = -1;
		int end = -1;

		for (i = 0; i < schematic.length; i++) {
			char[] row = schematic[i];
		}

		for (i = 0; i < schematic.length; i++) {
			char[] row = schematic[i];
			for (int j = 0; j < row.length; j++) {
				char c = row[j];

				if ('*' == c) {
					int product = calculateValue_Part2(schematic, row, i, j);
					sum += product;
				}
			}
		}

		return sum;
	}

	private static int calculateValue_Part2(char[][] schematic, char[] row, int r, int j) {
		int product = 1;
		Map<String, Integer> numberLocationMap = new HashMap<>();

		Map.Entry<String, Integer> leftNum = getNumber_Part2(row, r, j - 1, true, false);
		Map.Entry<String, Integer> rightNum = getNumber_Part2(row, r, j + 1, false, true);
		addToMap_Part2(numberLocationMap, leftNum);
		addToMap_Part2(numberLocationMap, rightNum);

		char[] above = (r - 1 >= 0) ? schematic[r - 1] : null;
		Map.Entry<String, Integer> topLeftNum = getNumber_Part2(above, r - 1, j - 1, true, true);
		Map.Entry<String, Integer> topMiddleNum = getNumber_Part2(above, r - 1, j, true, true);
		Map.Entry<String, Integer> topRightNum = getNumber_Part2(above, r - 1, j + 1, true, true);
		addToMap_Part2(numberLocationMap, topLeftNum);
		addToMap_Part2(numberLocationMap, topMiddleNum);
		addToMap_Part2(numberLocationMap, topRightNum);

		char[] below = (r + 1 < schematic.length) ? schematic[r + 1] : null;
		Map.Entry<String, Integer> bottomLeftNum = getNumber_Part2(below, r + 1, j - 1, true, true);
		Map.Entry<String, Integer> bottomMiddleNum = getNumber_Part2(below, r + 1, j, true, true);
		Map.Entry<String, Integer> bottomRightNum = getNumber_Part2(below, r + 1, j + 1, true, true);
		addToMap_Part2(numberLocationMap, bottomLeftNum);
		addToMap_Part2(numberLocationMap, bottomMiddleNum);
		addToMap_Part2(numberLocationMap, bottomRightNum);

		if (numberLocationMap.size() != 2) {
			return 0;
		}
		else {
			return getProduct_Part2(numberLocationMap);
		}
	}

	private static Map.Entry<String, Integer> getNumber_Part2(char[] row, int r, int j, boolean searchLeft, boolean searchRight) {
		StringBuilder number = new StringBuilder();
		int firstIndex = j;
		if (row == null || j < 0 || j >= row.length || !Character.isDigit(row[j])) {
			return null;
		}
		number.append(String.valueOf(row[j]));
		int i = j;
		while (searchLeft && i - 1 >= 0 && Character.isDigit(row[i - 1])) {
			number.insert(0, String.valueOf(row[i - 1]));
			i--;
			firstIndex = i;
		}
		i = j;
		while (searchRight && i + 1 < row.length && Character.isDigit(row[i + 1])) {
			number.append(String.valueOf(row[i + 1]));
			i++;
		}
		String location = String.valueOf(r) + String.valueOf(firstIndex);
		return new AbstractMap.SimpleImmutableEntry<>(location, Integer.parseInt(number.toString()));
	}

	private static void addToMap_Part2(Map<String, Integer> numberLocationMap, Map.Entry<String, Integer> entry) {
		if (entry != null) {
			numberLocationMap.put(entry.getKey(), entry.getValue());
		}
	}

	private static int getProduct_Part2(Map<String, Integer> numberLocationMap) {
		int product = 1;
		for (Map.Entry<String, Integer> entry : numberLocationMap.entrySet()) {
			product *= entry.getValue();
		}
		return product;
	}
}
