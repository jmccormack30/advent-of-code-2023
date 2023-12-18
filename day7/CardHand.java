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

	static Map<Character, Integer> valueMap = new HashMap<>();
	static {
		valueMap.put('A', 14);
		valueMap.put('K', 13);
		valueMap.put('Q', 12);
		valueMap.put('J', 11);
		valueMap.put('T', 10);
		valueMap.put('9', 9);
		valueMap.put('8', 8);
		valueMap.put('7', 7);
		valueMap.put('6', 6);
		valueMap.put('5', 5);
		valueMap.put('4', 4);
		valueMap.put('3', 3);
		valueMap.put('2', 2);
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

	@Override
	public int compareTo(CardHand other) {
		int valueDif = (this.getType().getValue() - other.getType().getValue());
		if (valueDif != 0) return valueDif;
		int i = 0;
		char[] thisCards = this.getCards();
		char[] otherCards = other.getCards();
		while (valueDif == 0 && i  < 5) {
			valueDif = (valueMap.get(thisCards[i]) - valueMap.get(otherCards[i]));
			i++;
		}
		return valueDif;
	}
}