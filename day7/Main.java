import java.io.*;
import java.util.*;

class Main {
	public static void main(String[] args) throws Exception{
		String fileName = "input.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			long startTime = System.nanoTime();
			long value = decodeFile_Part1(br);
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
		Set<CardHand> hands = new HashSet<>();
		String line;
		while ((line = br.readLine()) != null) {
			CardHand hand = new CardHand(line);
			setCardType(hand);
			hands.add(hand);
		}
		List<CardHand> handList = new ArrayList<>(hands);
		Collections.sort(handList);
		int sum = 0;
		for (int i = 0; i < handList.size(); i++) {
			CardHand hand = handList.get(i);
			int strength = (i + 1) * hand.getBid();
			sum += strength;
		}
		return sum;
	}

	private static void setCardType(CardHand cardHand) {
		cardHand.setType(calculateCardType_Part2(cardHand));
	}

	private static CardHand.Type calculateCardType(CardHand cardHand) {
		Map<Character, Integer> cardCountMap = new HashMap<>(); 
		for (char card: cardHand.getCards()) {
			cardCountMap.compute(card, (k, v) -> (v == null) ? 1 : v + 1);
		}
		List<Map.Entry<Character, Integer>> list = new LinkedList<>(cardCountMap.entrySet());
		Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
		Iterator<Map.Entry<Character, Integer>> iterator = list.iterator();
		Map.Entry<Character, Integer> entry = iterator.next();
		int count = entry.getValue();
		if (count == 5) return CardHand.Type.FIVE_OF_A_KIND;
		if (count == 4) return CardHand.Type.FOUR_OF_A_KIND;
		if (count == 3) {
			entry = iterator.next();
			count = entry.getValue();
			if (count == 2) return CardHand.Type.FULL_HOUSE;
			return CardHand.Type.THREE_OF_A_KIND;
		}
		if (count == 2) {
			entry = iterator.next();
			count = entry.getValue();
			if (count == 2) return CardHand.Type.TWO_PAIR;
			return CardHand.Type.ONE_PAIR;
		}
		return CardHand.Type.HIGH_CARD;		
	}

	private static CardHand.Type calculateCardType_Part2(CardHand cardHand) {
		Map<Character, Integer> cardCountMap = new HashMap<>(); 
		for (char card: cardHand.getCards()) {
			cardCountMap.compute(card, (k, v) -> (v == null) ? 1 : v + 1);
		}
		System.out.println(cardCountMap);
		int jokerCount = cardCountMap.get('J') != null ? cardCountMap.get('J') : 0;
		cardCountMap.remove('J');
		System.out.println("Jokers: " + jokerCount + ", Hand: " + cardCountMap);
		System.out.println();
		// List<Map.Entry<Character, Integer>> list = new LinkedList<>(cardCountMap.entrySet());
		// Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
		// Iterator<Map.Entry<Character, Integer>> iterator = list.iterator();
		// Map.Entry<Character, Integer> entry = iterator.next();
		// int count = entry.getValue();
		// if (count == 5) return CardHand.Type.FIVE_OF_A_KIND;
		// if (count == 4) return CardHand.Type.FOUR_OF_A_KIND;
		// if (count == 3) {
		// 	entry = iterator.next();
		// 	count = entry.getValue();
		// 	if (count == 2) return CardHand.Type.FULL_HOUSE;
		// 	return CardHand.Type.THREE_OF_A_KIND;
		// }
		// if (count == 2) {
		// 	entry = iterator.next();
		// 	count = entry.getValue();
		// 	if (count == 2) return CardHand.Type.TWO_PAIR;
		// 	return CardHand.Type.ONE_PAIR;
		// }
		return CardHand.Type.HIGH_CARD;		
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