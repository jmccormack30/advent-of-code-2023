import java.io.*;
import java.util.*;

class Main {
	public static void main(String[] args) throws Exception {
		String fileName = "input.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			int value = decodeFile_Part1(br);
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
						isValid = isValid(schematic, row, i, start - 1, end + 1);
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
				int product = calculateValue(schematic, row, i, j);
				sum += product;
			}
		}
	}

	return sum;
}

private static int calculateValue_Part2(char[][] schematic, char[] row, int r, int j) {
	int totalNums = 0;
	int product = 1;

	int leftNum = getNumber(row, j - 1, true, false);
	int rightNum = getNumber(row, j + 1, false, true);
	if (leftNum != 0) {
		product *= leftNum;
		totalNums++;
	}
	if (rightNum != 0) {
		product *= rightNum
		totalNums++;
	}

	return product;
}

private static int getNumber_Part2(char[] row, int j, boolean searchLeft, boolean searchRight) {
	StringBuilder number = new StringBuilder();
	if (j < 0 || j >= row.length || !Character.isDigit(row[j])) {
		return 0;
	}
	number.append(String.valueOf(row[j]));
	int i = j;
	while (searchLeft && i - 1 >= 0 && Character.isDigit(row[i - 1])) {
		number.insert(0, String.valueOf(row[i - 1]));
		i--;
	}
	i = j;
	while (searchRight && i + 1 < row.length && Character.isDigit(row[i + 1])) {
		number.append(String.valueOf(row[i + 1]));
		i++;
	}
	return Integer.parseInt(number.toString());
}
