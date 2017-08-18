import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Deck {

	private ArrayList<Card> cardDeck;
	private ObjectMapper mapper;
	private Scanner userInput;
	//private static String endpoint = "http://nav-deckofcards.herkuapp.com/shuffle";
	
	//Construct
	Deck () {
		this.cardDeck = new ArrayList<>();
		this. mapper = new ObjectMapper();
	}


	//Create deck from endpoint
	public ArrayList<Card> createFullDeck (String endpoint) {

		try {
			//Gets the card deck from the endpoint
			Card[] usrPost = mapper.readValue(new URL(endpoint), Card[].class);
			for(int i = 0; i < usrPost.length; i++) {
				this.cardDeck.add(new Card (usrPost[i].getSuit(),usrPost[i].getValue()));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return cardDeck;
	}


	public void moveAllToDeck(Deck moveTo){
		int thisDeckSize = this.cardDeck.size();

		//put cards in moveTo deck
		for(int i = 0; i < thisDeckSize; i++){
			moveTo.addCard(this.getCard(i));
		}
		//empty out the deck
		for(int i = 0; i < thisDeckSize; i++){
			this.removeCard(0);
		}
	}

	//Calculate the value of deck
	public int cardsValue () {
		int totalValue = 0;
		int aces = 0;

		//For every card in the deck
		for(Card aCard : this.cardDeck) {
			switch(aCard.getValue()) {

			//Switch of possible values
			case "2": totalValue += 2; break;
			case "3": totalValue += 3; break;
			case "4": totalValue += 4; break;
			case "5": totalValue += 5; break;
			case "6": totalValue += 6; break;
			case "7": totalValue += 7; break;
			case "8": totalValue += 8; break;
			case "9": totalValue += 9; break;
			case "10": totalValue += 10; break;
			case "J": totalValue += 10; break;
			case "Q": totalValue += 10; break;
			case "K": totalValue += 10; break;
			case "A": aces += 1; break;
			}
		}

		//Determine the total current value with aces
		//Aces worth 11 or 1 - if 11 would go over 21 make it worth 1
		for(int i = 0; i <aces; i++) {

			//If they're already at over 10 getting an ace valued at 11 would put them up to 22, so make ace worth one
			if(totalValue > 10) {
				totalValue +=1;
			}else {
				totalValue += 11;
			}	
		}
		//return
		return totalValue;
	}

	public String changeEndpoint (String endpoint) {
		
		userInput = new Scanner(System.in);
		System.out.println("The default Endpoints is");
		System.out.println("Do you want to change endpoint (1)Yes (2)No");
		int response = userInput.nextInt();

		if(response == 1) {
			//System.out.println("Please sumbit the new endpoint:");
			endpoint = userInput.nextLine().toString();
		}
		else {
			System.out.println("Endpoint not changed");
		}
		return endpoint;
		
	}
	
	//print method
	@Override
	public String toString() {
		String cardListOutput = "";

		for (Card card : this.cardDeck) {
			cardListOutput += "\n" + "-" + card.toString();
		}

		return cardListOutput;
	}

	//Remove a card from the deck
	public void removeCard(int i){
		this.cardDeck.remove(i);
	}

	//Get card from deck
	public Card getCard(int i){
		return this.cardDeck.get(i);
	}

	//Add card to deck
	public void addCard(Card addCard){
		this.cardDeck.add(addCard);
	}

	//Draw card from deck
	public void draw(Deck comingFrom) {

		//Add card to this deck from whatever deck its coming from
		this.cardDeck.add(comingFrom.getCard(0));

		//Remove the card in the deck its coming from
		comingFrom.removeCard(0);
	}

	//returns the size of the deck
	public int deckSize () {
		return this.cardDeck.size();
	}
}