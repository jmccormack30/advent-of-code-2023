import java.io.*;

class Main {
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
			int value = decodeLine_V2(line);
			sum += value;
		}

		return sum;
	}

	private static int decodeLine_V1(String line) throws Exception {
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

	private static int decodeLine_V2(String line) throws Exception {
		String replaced = line.replaceAll("[a-zA-Z]", "");
		String valueStr = ("" + replaced.charAt(0) + replaced.charAt(replaced.length()-1));
		int value = Integer.parseInt(valueStr);
		return value;
	}
}
