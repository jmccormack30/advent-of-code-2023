import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

class CardHand implements Comparable<CardHand> {
	// The Type of the Hand and its value
	enum Type {
		FIVE_OF_A_KIND("Five of a kind", 6),
		FOUR_OF_A_KIND("Four of a kind", 5),
		FULL_HOUSE("Full house", 4),
		THREE_OF_A_KIND("Three of a kind", 3),
		TWO_PAIR("Two pair", 2),
		ONE_PAIR("One pair", 1),
		HIGH_CARD("High card", 0);

		private final String type;
		private final int value;

		Type(String type, int value) {
			this.type = type;
			this.value = value;
		}

		public String getType() {
			return type;
		}

		public int getValue() {
			return value;
		}
	}

	static Map<Character, Integer> valueMap_Part1 = new HashMap<>();
	static {
		valueMap_Part1.put('A', 14);
		valueMap_Part1.put('K', 13);
		valueMap_Part1.put('Q', 12);
		valueMap_Part1.put('J', 11);
		valueMap_Part1.put('T', 10);
		valueMap_Part1.put('9', 9);
		valueMap_Part1.put('8', 8);
		valueMap_Part1.put('7', 7);
		valueMap_Part1.put('6', 6);
		valueMap_Part1.put('5', 5);
		valueMap_Part1.put('4', 4);
		valueMap_Part1.put('3', 3);
		valueMap_Part1.put('2', 2);
	}

	static Map<Character, Integer> valueMap_Part2 = new HashMap<>();
	static {
		valueMap_Part2.put('A', 14);
		valueMap_Part2.put('K', 13);
		valueMap_Part2.put('Q', 12);
		valueMap_Part2.put('T', 11);
		valueMap_Part2.put('9', 10);
		valueMap_Part2.put('8', 9);
		valueMap_Part2.put('7', 8);
		valueMap_Part2.put('6', 7);
		valueMap_Part2.put('5', 6);
		valueMap_Part2.put('4', 5);
		valueMap_Part2.put('3', 4);
		valueMap_Part2.put('2', 3);
		valueMap_Part2.put('J', 2);
	}

	private String hand;
	private char[] cards;
	private Type type;
	private int bid = -1;

	CardHand(String hand) {
		this.hand = hand;
		setCardsAndBid(hand);
	}

	private void setCardsAndBid(String hand) {
		String[] arr = hand.split("\\s");
		cards = arr[0].toCharArray();
		bid = Integer.parseInt(arr[1]);
	}

	public char[] getCards() {
		return cards;
	}

	public int getBid() {
		return bid;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder cardStr = new StringBuilder();
		if (cards != null) {
			cardStr.append(Arrays.toString(cards));
		}
		if (type != null) {
			cardStr.append(" | Type = " + type.getType());
		}
		if (bid >= 0) {
			cardStr.append(" | Bid = " + bid);
		}
		return cardStr.toString();
	}

	public int compareTo_Part1(CardHand other) {
		int valueDif = (this.getType().getValue() - other.getType().getValue());
		if (valueDif != 0) return valueDif;
		int i = 0;
		char[] thisCards = this.getCards();
		char[] otherCards = other.getCards();
		while (valueDif == 0 && i  < 5) {
			valueDif = (valueMap_Part1.get(thisCards[i]) - valueMap_Part1.get(otherCards[i]));
			i++;
		}
		return valueDif;
	}

	@Override
	public int compareTo(CardHand other) {
		int valueDif = (this.getType().getValue() - other.getType().getValue());
		if (valueDif != 0) return valueDif;
		int i = 0;
		char[] thisCards = this.getCards();
		char[] otherCards = other.getCards();
		while (valueDif == 0 && i  < 5) {
			valueDif = (valueMap_Part2.get(thisCards[i]) - valueMap_Part2.get(otherCards[i]));
			i++;
		}
		return valueDif;
	}
}
