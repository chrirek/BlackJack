import java.util.Scanner;


public class BlackJack {

	
	@SuppressWarnings("unused")
	private static Scanner userInput;
	private String endpoint;

	public static void main(String[] args) {
		
		System.out.println("Welcome to Blackjack!");

		//playingDeck will be the deck Magnus holds
		Deck playingDeck = new Deck();
		
		playingDeck.createFullDeck("http://nav-deckofcards.herokuapp.com/shuffle");

		//playerCards will be the cards the player has in their hand
		Deck playerCards = new Deck();

		//playerMoney holds players cash $$
		double playerMoney = 100.0;

		//dealerCards will be the cards Magnus has in his hand
		Deck dealerCards = new Deck();

		//Scanner for user input
		Scanner userInput = new Scanner(System.in);

		//Play the game while the player has money
		//Game loop
		while(playerMoney>0){

			//Take Bet
			System.out.println("You have $" + playerMoney + ", how much would you like to bet?");
			double playerBet = userInput.nextDouble();
			boolean endRound = false;

			if(playerBet > playerMoney){
				//Break if they bet too much
				System.out.println("You cannot bet more than you have.");
				break;
			}

			System.out.println("Dealing...");
			//Player gets two cards
			playerCards.draw(playingDeck);
			playerCards.draw(playingDeck);

			//Magnus gets two cards
			dealerCards.draw(playingDeck);
			dealerCards.draw(playingDeck);

			//While loop for drawing new cards
			while(true) {

				//Display player cards
				System.out.println("Your Hand:" + playerCards.toString());

				//Display Value
				System.out.println("Your hand is currently valued at: " + playerCards.cardsValue());

				//Display Magnus cards
				System.out.println("Magnus Hand: " + dealerCards.getCard(0).toString() + " and [hidden]");

				//What do they want to do
				System.out.println("Would you like to (1)Hit or (2)Stand");
				int response = userInput.nextInt();	
				
				//They hit
				if(response == 1) {
					playerCards.draw(playingDeck);
					System.out.println("You draw a:" + playerCards.getCard(playerCards.deckSize()-1).toString());
					
					//Bust if they go over 21
					if(playerCards.cardsValue() > 21){
						System.out.println("Bust. Currently valued at: " + playerCards.cardsValue());
						playerMoney -= playerBet;
						endRound = true;
						break;
					}
				}

				//Stand
				if(response == 2) {
					break;
				}

			}

			//Reveal Magnus's Cards
			System.out.println("Magnus's Cards:" + dealerCards.toString());
			
			//See if Magnus has more points than player
			if((dealerCards.cardsValue() > playerCards.cardsValue())&&endRound == false) {
				System.out.println("Magnus beats you " + dealerCards.cardsValue() + " to " + playerCards.cardsValue());
				playerMoney -= playerBet;
				endRound = true;
			}
			//Magnus hits until he beats the player, modify to 17 for more realistic gameplay
			while((dealerCards.cardsValue() <  playerCards.cardsValue()) && endRound == false) {
				dealerCards.draw(playingDeck);
				System.out.println("Magnus draws: " + dealerCards.getCard(dealerCards.deckSize()-1).toString());
			}
			//Display value of Magnus's cards
			System.out.println("Magnus hand value: " + dealerCards.cardsValue());

			//Determine if Magnus busted
			if((dealerCards.cardsValue()>21)&& endRound == false) {
				System.out.println("Magnus Busts. You win!");
				playerMoney += playerBet;
				endRound = true;
			}
			//Determine if push
			if((dealerCards.cardsValue() == playerCards.cardsValue()) && endRound == false){
				System.out.println("Push.");
				endRound = true;
			}
			//Determine if player wins
			if((playerCards.cardsValue() > dealerCards.cardsValue()) && endRound == false){
				System.out.println("You win the hand.");
				playerMoney += playerBet;
				endRound = true;
			}
			else if(endRound == false) //dealer wins
			{
				System.out.println("Magnus wins.");
				playerMoney -= playerBet;
			}

			//End of hand - put cards back in deck
			playerCards.moveAllToDeck(playingDeck);
			dealerCards.moveAllToDeck(playingDeck);
			System.out.println("End of Hand.");

		}
		//Game is over
		System.out.println("Game over!");

		//Close Scanner
		userInput.close();

	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
}


