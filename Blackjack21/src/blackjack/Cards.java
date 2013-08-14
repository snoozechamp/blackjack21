package blackjack;

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
	public String getFullInfo(){
		return "Your card is: \nsuit-" + 
				this.suit +" \nname-" + 
				this.name + " \nvalue-" + 
				this.value;
	}
	
	/**
	 * Retrieves the value of the card.
	 * @return - Numerical value of the card, such as King = 10.
	 */
	public int getValue(){
		return this.value;
	}
	/**
	 * Retrieves the name of the card.
	 * @return - String value of the card, such as 'King'
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * Retrieves the suit the card belongs to. 
	 * @return - String value of the card, such as 'Spades'
	 */
	public String getSuit(){
		return this.suit;
	}

}
