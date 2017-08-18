
//Class to hold the cards that are in the deck
public class Card {
	
	private String suit;
	private String value;
	
	public Card () {
		
	}
	
	public Card(String suit, String value) {
		setSuit(suit);
		setValue(value);
	}
	
	
	@Override
	public String toString() {
		return this.suit.toString() + "-" + this.value.toString();
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
