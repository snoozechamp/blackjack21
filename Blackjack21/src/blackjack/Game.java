package blackjack;

import java.io.Console;
import java.util.*;

import javax.swing.JOptionPane;

public class Game {
	
	private boolean state;			// State of the game, if false game over
	private boolean turn;			// Determines whose turn it is, true if players turn
	public double money;
	public ArrayList<Cards> player;
	public ArrayList<Cards> dealer;
	private ArrayList<Cards> deck;	// Represents a deck of 52 playing cards
	private String[] suits;			// Represents each suit in a deck of cards
	private String[] cards;			// Represents each type of card in a deck

	

	public Game (){
		// Initialize all the variables
		state = true;
		money = 500.00;
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
		if(state){
			String choose = JOptionPane.showInputDialog("Would you like to hit that?");
			if(choose.equalsIgnoreCase("yes")){
				System.out.println(score(player));
			}
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Game g = new Game();
	}

	/**
	 * Builds a deck of playing cards.
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
	public void shuffle(){
	}
	public void deal(int seed, boolean turn){
		// Used to deal cards randomly
		Random rand = new Random();
		// Keeps track of the random number, to get and remove card from deck.
		int index = rand.nextInt(seed);
		
		if(turn)
			player.add(deck.get(rand.nextInt(index)));
		else 
			dealer.add(deck.get(rand.nextInt(index)));
		deck.remove(index);
	}
	private int score(ArrayList<Cards> operator){
		int size = 0;
		int score = 0;
		size = operator.size();
			for(int j = 0; j < size; j++)
				score += operator.get(j).getValue();
		return score;
	}
}
