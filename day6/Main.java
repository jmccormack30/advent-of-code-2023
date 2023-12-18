import java.io.*;
import java.util.*;

class Main {
	public static void main(String[] args) throws Exception {
		String fileName = "input.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			long startTime = System.nanoTime();
			long value = decodeFile_Part2(br);
			long endTime = System.nanoTime();
			long elapsedTimeInNanoseconds = endTime - startTime;
        	double elapsedTimeInSeconds = (double) elapsedTimeInNanoseconds / 1_000_000_000;
			double minutes = Math.floor(elapsedTimeInSeconds / 60);
			double seconds = (elapsedTimeInSeconds % 60);
			System.out.println("\n" + value);
			System.out.println("Elapsed time: " + (int)minutes + " minutes " + (int)seconds + " seconds.");
		}
		catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}
		catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	private static int decodeFile_Part1(BufferedReader br) throws Exception {
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			line = line.split(":")[1].trim();
			builder.append(line + "|");
		}
		int product = 1;
		String[] rows = builder.toString().split("\\|");
		String[] timeValues = rows[0].split("\\s+");
		String[] distanceValues = rows[1].split("\\s+");
		for (int i = 0; i < timeValues.length; i++) {
			int time = Integer.parseInt(timeValues[i].trim());
			int distance = Integer.parseInt(distanceValues[i].trim());
			int successful = 0;
			for (int j = 0; j <= time; j++) {
				int speed = j;
				int travelTime = time - j;
				int travelDistance = speed * travelTime;
				if (travelDistance > distance) successful++;
			}
			product *= successful;
		}
		return product;
	}

	/*  PART 2 TIME
		Elapsed time: 1 minutes 55 seconds.
	*/
	private static int decodeFile_Part2(BufferedReader br) throws Exception {
		long time = 55999793L;
		long distance = 401148522741405L;
		int solutions = 0;
		for (long i = 0; i <= time; i++) {
			long speed = i;
			long travelTime = time - i;
			long travelDistance = speed * travelTime;
			if (travelDistance > distance) {
				System.out.println(travelDistance);
				solutions++;
			}
		}
		return solutions;
	}
}