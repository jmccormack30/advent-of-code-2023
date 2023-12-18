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

	private static long decodeFile_Part1(BufferedReader br) throws Exception {
		StringBuilder fileStrBuilder = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			fileStrBuilder.append(line);
			fileStrBuilder.append(System.lineSeparator());
		}
		String fileStr = fileStrBuilder.toString();
		String[] parts = fileStr.split("\\n\\s*\\n");

		Set<Long> seeds = getSeeds_Part1(parts[0]);
		Map<Long, Long[]> seedToSoilMap = processMap_Part1(parts[1]);
		Map<Long, Long[]> soilToFertilizerMap = processMap_Part1(parts[2]);
		Map<Long, Long[]> fertilizerToWaterMap = processMap_Part1(parts[3]);
		Map<Long, Long[]> waterToLightMap = processMap_Part1(parts[4]);
		Map<Long, Long[]> lightToTemperatureMap = processMap_Part1(parts[5]);
		Map<Long, Long[]> temperatureToHumidityMap = processMap_Part1(parts[6]);
		Map<Long, Long[]> humidityToLocationMap = processMap_Part1(parts[7]);
		Map[] mapArr = {seedToSoilMap, soilToFertilizerMap, fertilizerToWaterMap, waterToLightMap, lightToTemperatureMap, temperatureToHumidityMap, humidityToLocationMap};

		long minLoc = Integer.MAX_VALUE;
		for (Long seed: seeds) {
			long value = seed;
			for (int i = 0; i < 7; i++) {
				value = getValue_Part1(value, mapArr[i]);
			}
			if (value < minLoc) minLoc = value;
		}
		return minLoc;
	}

	private static Set<Long> getSeeds_Part1(String input) {
		Set<Long> seeds = new HashSet<>();
		String[] arr = input.split(":")[1].trim().split(" ");
		for (String s: arr) {
			long x = Long.parseLong(s);
			seeds.add(x);
		}
		return seeds;
	}

	private static Long[] getSeeds_Part2(String input) {
		Set<Long> seeds = new HashSet<>();
		String[] arr = input.split(":")[1].trim().split(" ");
		Long[] seedArr = new Long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			long x = Long.parseLong(arr[i]);
			seedArr[i] = x;
		}
		return seedArr;
	}

	private static Map<Long, Long[]> processMap_Part1(String input) {
		Map<Long, Long[]> map = new HashMap<>();
		String[] arr = input.split("\\n");
		for (int i = 1; i < arr.length; i++) {
			String row = arr[i].trim();
			String[] nums = row.split(" ");
			Long dest = Long.parseLong(nums[0]);
			Long source = Long.parseLong(nums[1]);
			Long range = Long.parseLong(nums[2]);
			Long[] longArr = {dest, range};
			map.put(source, longArr);
		}
		return map;
	}

	private static Map<Long, Long[]> processMap_Part2(String input) {
		Map<Long, Long[]> map = new TreeMap<>();
		String[] arr = input.split("\\n");
		for (int i = 1; i < arr.length; i++) {
			String row = arr[i].trim();
			String[] nums = row.split(" ");
			Long dest = Long.parseLong(nums[0]);
			Long source = Long.parseLong(nums[1]);
			Long range = Long.parseLong(nums[2]);
			Long[] longArr = {source, range};
			map.put(dest, longArr);
		}
		return map;
	}

	private static Long getValue_Part1(Long input, Map<Long, Long[]> map) {
		for (Map.Entry<Long, Long[]> entry: map.entrySet()) {
			Long source = entry.getKey();
			Long dest = entry.getValue()[0];
			Long range = entry.getValue()[1];

			Long difference = (input - source);
			if (difference >= 0 && difference < range) {
				return (dest + difference);
			}
		}
		return input;
	}

	private static Long getSource_Part2(Long input, Map<Long, Long[]> map) {
		for (Map.Entry<Long, Long[]> entry: map.entrySet()) {
			Long dest = entry.getKey();
			Long source = entry.getValue()[0];
			Long range = entry.getValue()[1];

			Long difference = (input - dest);
			if (difference >= 0 && difference < range) {
				return (source + difference);
			}
		}
		return input;
	}

	private static long decodeFile_Part2(BufferedReader br) throws Exception {
		StringBuilder fileStrBuilder = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			fileStrBuilder.append(line);
			fileStrBuilder.append(System.lineSeparator());
		}
		String fileStr = fileStrBuilder.toString();
		String[] parts = fileStr.split("\\n\\s*\\n");

		Long[] seeds = getSeeds_Part2(parts[0]);
		Map<Long, Long[]> seedToSoilMap = processMap_Part2(parts[1]);
		Map<Long, Long[]> soilToFertilizerMap = processMap_Part2(parts[2]);
		Map<Long, Long[]> fertilizerToWaterMap = processMap_Part2(parts[3]);
		Map<Long, Long[]> waterToLightMap = processMap_Part2(parts[4]);
		Map<Long, Long[]> lightToTemperatureMap = processMap_Part2(parts[5]);
		Map<Long, Long[]> temperatureToHumidityMap = processMap_Part2(parts[6]);
		Map<Long, Long[]> humidityToLocationMap = processMap_Part2(parts[7]);
		Map[] mapArr = {seedToSoilMap, soilToFertilizerMap, fertilizerToWaterMap, waterToLightMap, lightToTemperatureMap, temperatureToHumidityMap, humidityToLocationMap};

		long minLoc = 0;
		boolean foundLocation = false;
		while (!foundLocation && minLoc <= Long.MAX_VALUE) {
			long value = minLoc;
			for (int i = 6; i >= 0; i--) {
				value = getSource_Part2(value, mapArr[i]);
			}
			boolean seedExists = doesSeedExists(value, seeds);
			if (seedExists) {
				foundLocation = true;
			}
			else {
				minLoc++;
			}
		}

		return minLoc;
	}

	private static boolean doesSeedExists(long input, Long[] arr) {
		for (int i = 0; i < arr.length; i += 2) {
			Long start = arr[i];
			Long range = arr[i + 1];
			if (input >= start && input < start + range) {
				return true;
			}
		}
		return false;
	}
}
