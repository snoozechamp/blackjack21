package blackjack;

import java.util.*;
import javax.swing.JOptionPane;

public class Game {
	
	private boolean state;			// State of the game, if false game over
	private boolean turn;			// Determines whose turn it is, true if player's turn
	public double money;			// Represents the amount of money the player has
	private ArrayList<Cards> deck;	// Represents a deck of 52 playing cards
	private String[] suits;			// Represents each suit in a deck of cards
	private String[] cards;			// Represents each type of card in a deck
	public ArrayList<Cards> player; // Represents the cards that the player has been dealt
	public ArrayList<Cards> dealer; // Represents the cards that the dealer has been dealt


	public Game (int amount){
		// Initialize all the variables
		state = true;
		money = amount;
		player = new ArrayList<Cards>();
		dealer = new ArrayList<Cards>();
		deck = new ArrayList<Cards>();
		suits = new String[]{"Clubs", "Diamonds", "Hearts", "Spades"};
		cards = new String[]{"2","3","4","5","6","7","8","9","10",
							 "Jack", "Queen", "King", "Ace"};
		
		// Builds deck of cards
		build(deck, suits, cards);
		
		// Deals two cards to the dealer and two to the player
		for(int i = 0; i < 4; i++){
			deal(deck.size(), turn);
			if(turn)
				turn = false;
			else 
				turn = true;
		}
		if(score(player) > 21){
			state = false;
		}
		if(score(dealer) > 21){
			state = false;
		}
		
		if(state){
			String choose = JOptionPane.showInputDialog("Would you like to hit that?");
			if(choose.equalsIgnoreCase("yes")){
				System.out.println(score(player));
				deal(deck.size(), true);
				System.out.println("New score: " + score(player) + " and card drawn: " + player.get(player.size() - 1).getName());
				turn = false;
				System.out.println(score(dealer));
			}
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
		int index = rand.nextInt(seed);
		
		// Checks if it is the player's turn or the dealer's turn to be dealt a card.
		// True means it's the player's turn and false means it's the dealers turn. 
		if(turn)
			player.add(deck.get(rand.nextInt(index)));
		else 
			dealer.add(deck.get(rand.nextInt(index)));
		// Removes the cards from the deck of 52 as they are dealt to player/dealer
		deck.remove(index);
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
}
