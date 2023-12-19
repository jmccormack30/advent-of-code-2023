import java.io.*;
import java.util.*;

class Main {
	public static void main(String[] args) throws Exception {
		String fileName = "input.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			long startTime = System.nanoTime();
			int value = decodeFile_Part2(br);
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
		String line;
		int sum = 0;
		while ((line = br.readLine()) != null) {
			String[] arr = line.trim().split(" ");
			int[] intArr = new int[arr.length];
			for (int i = 0; i < arr.length; i++) intArr[i] = Integer.parseInt(arr[i]);
			int value = calculateNextValue(intArr);
			sum += value;
		}
		return sum;
	}

	private static int calculateNextValue(int[] arr) {
		LinkedList<int[]> list = new LinkedList<>();
		list.addFirst(arr);
		int[] diffArr = getDifferenceArray(arr);

		while (!isArrayAllZeroes(diffArr)) {
			int[] copy = Arrays.copyOf(diffArr, diffArr.length);
			list.addFirst(copy);
			diffArr = getDifferenceArray(diffArr);
		}
		int sum = 0;
		for (int[] intArr: list) {
			sum = sum + intArr[intArr.length - 1];
		}
		return sum;
	}

	private static int[] getDifferenceArray(int[] arr) {
		int[] diffArr = new int[arr.length - 1];
		for (int i = 0; i < arr.length - 1; i++) {
			int diff = (arr[i + 1] - arr[i]);
			diffArr[i] = diff;
		}
		return diffArr;
	}

	private static boolean isArrayAllZeroes(int[] arr) {
		return Arrays.stream(arr).allMatch(num -> num == 0);
	}

	private static int decodeFile_Part2(BufferedReader br) throws Exception {
		String line;
		int sum = 0;
		while ((line = br.readLine()) != null) {
			String[] arr = line.trim().split(" ");
			int[] intArr = new int[arr.length];
			for (int i = 0; i < arr.length; i++) intArr[i] = Integer.parseInt(arr[i]);
			int value = calculateFirstValue(intArr);
			sum += value;
		}
		return sum;
	}

	private static int calculateFirstValue(int[] arr) {
		LinkedList<int[]> list = new LinkedList<>();
		list.addFirst(arr);
		int[] diffArr = getDifferenceArray(arr);

		while (!isArrayAllZeroes(diffArr)) {
			int[] copy = Arrays.copyOf(diffArr, diffArr.length);
			list.addFirst(copy);
			diffArr = getDifferenceArray(diffArr);
		}
		int sum = 0;
		for (int[] intArr: list) {
			sum = (intArr[0] - sum);
		}
		return sum;
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
