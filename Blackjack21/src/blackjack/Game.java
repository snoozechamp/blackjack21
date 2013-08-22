package blackjack;

import java.util.*;
import javax.swing.JOptionPane;
/**
 * @author Umair Naveed
 */
/**TODO - Add the ability to split. 
 * Hide the dealers card
 * @author umairnaveed
 *
 */
public class Game {
	
	private int state;					// Represents the state of the game and determines who wins the round
	private boolean turn;				// Determines whose turn it is, true if player's turn
	private boolean hitDoubleStand;		// Determines players choice to hit, double, or stand
	private double money;				// Represents the amount of money the player has
	private double bet;					// Represents the player's bet
	private ArrayList<Cards> deck;		// Represents a deck of 52 playing cards
	private String[] suits;				// Represents each suit in a deck of cards
	private String[] cards;				// Represents each type of card in a deck
	private ArrayList<Cards> player; 	// Represents the cards that the player has been dealt
	private ArrayList<Cards> dealer; 	// Represents the cards that the dealer has been dealt
	private int size;


	public Game (int amount){
		// Initialize all the variables
		state = 0;
		money = amount;
		hitDoubleStand = true;
		player = new ArrayList<Cards>();
		dealer = new ArrayList<Cards>();
		deck = new ArrayList<Cards>();
		suits = new String[]{"Clubs", "Diamonds", "Hearts", "Spades"};
		cards = new String[]{"2","3","4","5","6","7","8","9","10",
							 "Jack", "Queen", "King", "Ace"};
		
		// Builds deck of cards
		build(deck, suits, cards);
		size = deck.size();
		
		// Deals two cards to the dealer and two to the player
		String sBet = JOptionPane.showInputDialog("How much would you like to bet?");
		double bet = Double.parseDouble(sBet);
		for(int i = 0; i < 4; i++){
			deal(size, turn);
			if(turn)
				turn = false;
			else 
				turn = true;
		}
		System.out.println("Player score: " + score(player));
		System.out.println("Dealer score: " + score(dealer));

		int initial = 0;
		
		// The player loop
		while(state == 0){
			String choose;
			if(initial == 0)
				choose = JOptionPane.showInputDialog("Would you like to hit, double, or stand?");
			
			else 
				choose = JOptionPane.showInputDialog("Would you like to hit or stand?");

			// If the player chooses to hit, they receive another card, hitDoubleStand allows
			// them to have the option to hit again provided they haven't busted.
			if(choose.equalsIgnoreCase("hit") && hitDoubleStand){
				hitDoubleStand = true;
				deal(size, true);
				System.out.println("New score: " + score(player) + " and card drawn: " + player.get(player.size() - 1).getName());
				turn = false;
			}
			// Only available after the first two cards are dealt, the player is allowed
			// to double their bet and receive only one card. After which, they are forced
			// stand on this card. 
			else if (choose.equalsIgnoreCase("double") && initial == 0){
				hitDoubleStand = false;
				bet *= 2;
				deal(size, true);
			}
			// Receive no more cards and determine the score. 
			else {
				hitDoubleStand = false;
			}
			
			// Increment initial, indicating that the first two cards have been dealt to both
			// dealer and player.
			initial++;
			System.out.println("Player score: " + score(player));
			if(score(player) >= 21 || !hitDoubleStand){
				dealerLogic();
				checkState();
			}
		}
		System.out.println("Player score: " + score(player) +
				   "\nDealer score: " + score(dealer));
		if(state == 1){
			money += bet;
			System.out.println("You won $ " + bet);
		}
		else if(state == 2){
			money -= bet;
			System.out.println("Dealer wins! You lost $ " +bet);
		}
		else if(state == 3){
			System.out.println("Push!");
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Game g = new Game(500);
	}

	/**
	 * Builds a deck of playing cards, excluding jokers.
	 */
	public void build(ArrayList<Cards> deck, String[] suits, String[] cards){
		// Adds a unique card by suit and name
		for(int i = 0; i < suits.length; i++){
			// Counter to add values to each card
			int k = 2;			
			for(int j = 0; j < cards.length; j++){
				deck.add(new Cards(k, cards[j], suits[i]));
				// Check to ensure that cards 2-9 have their face value, while
				// cards 10, Jack, Queen, King have a value of 10 and Ace is 11.
				if(k < 10 || j == 11){ 
					k++;
				}
			}
		}
	}
	/**
	 * Shuffles the cards in the deck.
	 */
	public void shuffle(){
	}
	/**
	 * Deals one card from the deck to either the player or the dealer
	 * depending on what the value of the turn variable is. Uses seed
	 * variable to generate a random number, within the range of cards
	 * available. 
	 * @param seed - The number of cards available in the deck, so that the random number
	 * generated is not out of bounds. 
	 * @param turn - If true a card is dealt to player, otherwise to the dealer
	 */
	public void deal(int seed, boolean turn){
		// Used to deal cards randomly
		Random rand = new Random();
		
		// Keeps track of the random number, to get and remove card from deck.
		int index = 0;

		// Prevents a non-positive random number from being generated. 
		while(index <= 0){
			index = rand.nextInt(seed);
		}
		
		// Checks if it is the player's turn or the dealer's turn to be dealt a card.
		// True means it's the player's turn and false means it's the dealers turn. 
			if(turn)
				player.add(deck.get(rand.nextInt(index)));
			else 
				dealer.add(deck.get(rand.nextInt(index)));
			// Removes the cards from the deck of 52 as they are dealt to player/dealer
			deck.remove(index);
			size--;
	}
	
	/**
	 * Adds the value of the parameter cards together and returns the 
	 * result. Accounts for aces, which can have a value of either 1 
	 * or 11.
	 * @param operator - An ArrayList which represents the cards 
	 * held by the player/dealer. 
	 * @return - The combined value of cards held by the player/dealer.
	 */
	private int score(ArrayList<Cards> operator){
		int score = 0;
		// Counter for the number of aces in the hand
		int aces = 0;
			for(int j = 0; j < operator.size(); j++){
				// Check for aces, increments the counter if present
				if(operator.get(j).getName().equals("Ace")){
					aces++;
				}
				// Adds up the value of the cards if no aces present.
				else{
					score += operator.get(j).getValue();
				}
			}
		// Checks if aces are present in the hand. Aces are
	    // assigned either a 1 or 11, depending on how they
		// effect the hand. Aces alone cannot cause a bust. 
		if(score == 10 && aces == 1){
			return score + 11;
		}
		if(score == 10 && aces > 1){
			return score + (aces*1);
		}
		if(score < 10 && aces > 0){
			return score + 11 + ((aces-1)*1);
		}
		if(score > 10 && aces > 0){
			return score + 1;
		}
		return score;
	}
	
	/**
	 * Changes the state of the game by checking to see what cards the
	 * dealer and the player hold. A state equaling 0 means the game
	 * has started/is in progress and there are no wins/loses/ties. A
	 * score of 1 means the player wins, while a score of 2 means the
	 * dealer wins. A score of 3 means it is a tie game. Also provides
	 * checks for true blackjacks. True blackjack is when someone has
	 * an Ace plus a 10 or a face card. 
	 */
	private void checkState(){
		// Checks for when both the player and the dealer have a hand that is greater than
		// or equal to 21. 
		if(score(player) <= 21 && score(dealer) <= 21){
			// Various checks for when the player and dealer have the same score
			if(score(player) == score(dealer)){
				
				// A check for a true blackjack
				if(score(player) == 21){
					if(dealer.size() == 2 && player.size() == 2)
						state = 3;
					else if(dealer.size() == 2 && player.size() != 2)
						state = 2;
					// Pays the player 3:2 if they have true blackjack
					else {
						state = 1;
						bet *= 1.5;
					}
				}
				state = 3;
			}
			// Checks for when the player has a better hand than the dealer
			else if(score(player) > score(dealer)){
				
				if(score(player) == 21){
					// Pays the player 3:2 if they have true blackjack.
					if(player.size() == 2)
						bet *= 1.5;
				state = 1;
				}
			}
			// If the dealer has a better hand than the player, the dealer wins
			else
				state = 2;
		}
		// Checks for situations in which either the dealer or the player have a 
		// score greater than 21. 
		if(score(player) > 21 || score(dealer) > 21){
			if(score(player) > 21 && score(dealer) > 21)
				state = 2;
			else if(score(player) > 21)
				state = 2;
			else 
				state = 1;
		}
	}
	/**
	 * Determines whether the dealer will hit or
	 * stand. Dealer stands at 17 in this game. 
	 */
	private void dealerLogic(){
		if(score(dealer) < 17){
			deal(size, false);
			size--;
		}
	}
}
