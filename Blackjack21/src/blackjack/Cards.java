package blackjack;

import java.util.*;

public class Cards {

	private int value;		// Holds the face value of each card.
	private String name;	// Holds the name of the card, i.e. King, Queen, etc.
	private String suit;	// Holds the name of the suit.	
	
	// Represents each individual card
	public Cards (int i, String name, String s) {
		this.value = i;
		this.name = name;
		this.suit = s;
	}

	/**
	 * Getter that allows easy displaying of card data.
	 * @return String with information about cards.
	 */
	public String get(){
		return "Your card is: \nsuit-" + 
				this.suit +" \nname-" + 
				this.name + " \nvalue-" + 
				this.value;
	}
	
	/**
	 * Specific getter, retrieves the value of the card.
	 * @return - Integer value of the card.
	 */
	public int getValue(){
		return this.value;
	}

}
