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
		String line;
		int sum = 0;
		while ((line = br.readLine()) != null) {
			line = line.split(":")[1].trim();
			int value = processLine_Part1(line);
			sum += value;
		}
		return sum;
	}

	private static int processLine_Part1(String line) {
		String[] arr = line.split("\\|");
		String winners = arr[0];
		String numbers = arr[1];

		Set<Integer> winnerNums = getNumbers_Part1(winners);
		Set<Integer> cardNums = getNumbers_Part1(numbers);

		return getCardValue_Part1(winnerNums, cardNums);
	}

	private static Set<Integer> getNumbers_Part1(String numbersStr) {
		Set<Integer> numbers = new HashSet<>();
		for (String s: numbersStr.split(" ")) {
			s = s.trim();
			if (s != null && s.length() > 0) {
				int num = Integer.parseInt(s);
				numbers.add(num);
			}
		}
		return numbers;
	}

	private static int getCardValue_Part1(Set<Integer> winnerNums, Set<Integer> cardNums) {
		int matches = 0;
		for (int num: cardNums) {
			if (winnerNums.contains(num)) matches++;
		}
		return (int) Math.pow(2, (matches - 1));
	}

	private static int decodeFile_Part2(BufferedReader br) throws Exception {
		int totalCards = 0;
		String line;
		TreeSet<Integer> cards = new TreeSet<>();
		TreeMap<Integer, Integer> cardToMatchesMap = new TreeMap<>();
		TreeMap<Integer, Integer> cardCountMap = new TreeMap<>();

		while ((line = br.readLine()) != null) {
			String[] arr = line.split(":");
			String cardNumber = arr[0].trim().split("Card")[1].trim();
			int cardNumberInt = Integer.parseInt(cardNumber);
			line = arr[1].trim();
			int cardScore = processLine_Part1(line);
			int matches = cardScore > 0 ? ((int) (Math.log(cardScore) / Math.log(2))) + 1 : 0;
			cards.add(cardNumberInt);
			cardToMatchesMap.put(cardNumberInt, matches);
			cardCountMap.put(cardNumberInt, 1);
		}

		for (Integer card: cards) {
			int count = cardCountMap.get(card);
			totalCards += count;

			int matches = cardToMatchesMap.get(card);
			int j = card;
			for (int i = 1; i < matches + 1; i++) {
				j += 1;
				if (cardCountMap.get(j) != null) {
					cardCountMap.put(j, cardCountMap.get(j) + count);
				}
			}
		}

		return totalCards;
	}
}
