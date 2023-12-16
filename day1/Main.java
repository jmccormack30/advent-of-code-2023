import java.io.*;
import java.util.*;

class Main {

	private static final Map<String, String> numbersMap = new HashMap<>();

	static {
		numbersMap.put("one", "1");
		numbersMap.put("two", "2");
		numbersMap.put("three", "3");
		numbersMap.put("four", "4");
		numbersMap.put("five", "5");
		numbersMap.put("six", "6");
		numbersMap.put("seven", "7");
		numbersMap.put("eight", "8");
		numbersMap.put("nine", "9");
	}

	public static void main(String[] args) throws Exception {
		String fileName = "input.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			int value = decodeFile(br);
			System.out.println(value);
		}
		catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}
		catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	private static int decodeFile(BufferedReader br) throws Exception {
		int sum = 0;
		String line;

		while ((line = br.readLine()) != null) {
			int value = decodeLine_Part2_V2(line);
			sum += value;
		}

		return sum;
	}

	/* Solution to Part 1 using a for loop */
	private static int decodeLine_Part1_V1(String line) throws Exception {
		Character firstNum = null;
		Character lastNum = null;

		for (char c: line.toCharArray()) {
			if (Character.isDigit(c)) {
				if (firstNum == null) firstNum = c;
				lastNum = c;
			}
		}

		String valueStr = ("" + firstNum + lastNum);
		int value = Integer.parseInt(valueStr);
		return value;
	}

	/* Solution to Part 1 using replaceAll */
	private static int decodeLine_Part1_V2(String line) throws Exception {
		String replaced = line.replaceAll("[a-zA-Z]", "");
		String valueStr = ("" + replaced.charAt(0) + replaced.charAt(replaced.length()-1));
		int value = Integer.parseInt(valueStr);
		return value;
	}

	/* Solution to Part 2 using recursion. This solution does not work. This will translate "eighthree" -> "8hree". The test expects "eighthree" -> "83" */
	private static int decodeLine_Part2_V1(String line) throws Exception {
		int index = Integer.MAX_VALUE;
		String number = null;

		for (Map.Entry<String, String> entry: numbersMap.entrySet()) {
			int i = line.indexOf(entry.getKey());
			if (i >= 0 && i < index) {
				index = i;
				number = entry.getKey();
			}
		}

		if (number == null) {
			return decodeLine_Part1_V2(line);
		}

		int startIndex = index;
		int endIndex = startIndex + number.length();

		line = line.substring(0, startIndex) + numbersMap.get(number) + line.substring(endIndex);
		return decodeLine_Part2_V1(line);
	}

	/* Solution to Part 2 that works. Using a TreeMap to keep an ordered map of indices to number occurrences in the string */
	private static int decodeLine_Part2_V2(String line) throws Exception {
		TreeMap<Integer, String> numberSubstringMap = new TreeMap<>();

		char[] charArr = line.toCharArray();
		for (int i = 0; i < charArr.length; i++) {
			char c = charArr[i];
			if (Character.isDigit(c)) {
				numberSubstringMap.put(i, String.valueOf(c));
			}
		}

		for (Map.Entry<String, String> entry: numbersMap.entrySet()) {
			String subString = entry.getKey();
			int index = line.indexOf(subString);
			while (index >= 0) {
				numberSubstringMap.put(index, entry.getValue());
				index = line.indexOf(subString, index + 1);
			}
		}

		StringBuilder sb = new StringBuilder();
		for (String s: numberSubstringMap.values()) {
			sb.append(s);
		}

		return decodeLine_Part1_V2(sb.toString());
	}
}
