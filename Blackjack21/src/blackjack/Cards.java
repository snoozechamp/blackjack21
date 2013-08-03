package blackjack;

import java.util.ArrayList;

public class Cards {

	private int value;		// Holds the face value of each card.
	private String name;	// Holds the name of the card, i.e. King, Queen, etc.
	private String suit;	// Holds the name of the suit.
	static ArrayList<Cards> deck;
	
	 // All the possible card names
	static String[] cards = new String[]{"2", "3", "4", "5", "6", "7",
								  "8", "9", "10", "Jack", "Queen", "King", "Ace"};
	
	// All the possible suits
	static String[] suits = new String[]{"Clubs", "Diamonds", "Hearts", "Spades"};		
	
	// This will be the class representing card objects
	public Cards (int i, String name, String s) {
		this.value = i;
		this.name = name;
		this.suit = s;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		deck = new ArrayList<Cards>();
		for(int i = 0; i < suits.length; i++){
			for(int j = 0; j < cards.length; j++){
				int k = 2;
				deck.add(new Cards(k, cards[j], suits[i]));
				k++;
			}
		}
		System.out.println(deck.get(5).get());

	}
	public static void build (){

	}
	public String get(){
		return "Your card is a " + this.suit +" with this value " + this.name;
		
	}

}
