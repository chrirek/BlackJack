import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class DeckTest {

	Deck deck = new Deck();
	ArrayList<Card> deckList = new ArrayList<>();
	
	@Before
	public void setUp() throws Exception {
	
		
	}

	@Test
	public void testDeckSize() {
		
		deckList.add(new Card("HEARTS","10"));
		assertEquals(1, deckList.size());
	}
	
	
	public void testcreateFullDeck () {
		String endpoint = "http://nav-deckofcards.herkuapp.com/shuffle";
		deckList = deck.createFullDeck(endpoint);
		assertEquals(52, deckList.size());
	}
	
	@Test
	public void testCardValue () {
		deckList.add(new Card("HEARTS","K"));
		String value = deckList.get(0).getValue();
		assertEquals("K", value);
	}

}
