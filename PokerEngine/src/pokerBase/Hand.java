/**
 * 
 * 
 * @author Tim Tanzilli and Daniel Zelo
 * 
 */

package pokerBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import pokerEnums.eCardNo;
import pokerEnums.eHandStrength;
import pokerEnums.eRank;

public class Hand {
	private UUID playerID;
	@XmlElement
	private ArrayList<Card> CardsInHand;
	private ArrayList<Card> BestCardsInHand;

	@XmlElement
	private int HandStrength;
	@XmlElement
	private int HiHand;
	@XmlElement
	private int LoHand;
	@XmlElement
	private int Kicker;

	private boolean bScored = false;

	private boolean Flush;
	private boolean Straight;
	private boolean Ace;
	private boolean FullHouse;
	// the following data fields I made to save us time in the long run
	private boolean Pair12;
	private boolean Pair23;
	private boolean Pair34;
	private boolean Pair45;
	private boolean ThreeKind13;
	private boolean ThreeKind24;
	private boolean ThreeKind35;
	
	private static Deck dJoker = new Deck();

	public Hand()
	{
		
	}
	public void  AddCardToHand(Card c)
	{
		if (this.CardsInHand == null)
		{
			CardsInHand = new ArrayList<Card>();
		}
		this.CardsInHand.add(c);
	}
	
	public Card  GetCardFromHand(int location)
	{
		return CardsInHand.get(location);
	}
	
	public Hand(Deck d) {
		ArrayList<Card> Import = new ArrayList<Card>();
		for (int x = 0; x < 5; x++) {
			Import.add(d.drawFromDeck());
		}
		CardsInHand = Import;


	}


	public Hand(ArrayList<Card> setCards) {
		this.CardsInHand = setCards;
	}

	public ArrayList<Card> getCards() {
		return CardsInHand;
	}

	public ArrayList<Card> getBestHand() {
		return BestCardsInHand;
	}

	public void setPlayerID(UUID playerID)
	{
		this.playerID = playerID;
	}
	public UUID getPlayerID()
	{
		return playerID;
	}
	public void setBestHand(ArrayList<Card> BestHand) {
		this.BestCardsInHand = BestHand;
	}

	public int getHandStrength() {
		return HandStrength;
	}


	public int getKicker() {
		return Kicker;
	}

	public int getHighPairStrength() {
		return HiHand;
	}

	public int getLowPairStrength() {
		return LoHand;
	}

	public boolean getAce() {
		return Ace;
	}

	public static Hand EvalHand(ArrayList<Card> SeededHand) {
		
		Deck d = new Deck();
		Hand h = new Hand(d);
		h.CardsInHand = SeededHand;

		return h;
	}

	public void EvalHand() {
		// Evaluates if the hand is a flush and/or straight then figures out
		// the hand's strength attributes

		// Sort the cards!
		Collections.sort(CardsInHand, Card.CardRank);

		// Ace Evaluation
		if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.ACE) {
			Ace = true;
		}

		// Flush Evaluation
		if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand
				.get(eCardNo.SecondCard.getCardNo()).getSuit()
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getSuit()
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getSuit()
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getSuit()) {
			Flush = true;
		} else {
			Flush = false;
		}

		// five of a Kind

		if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FiveOfAKind,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0, 0);
		}

		// Straight Evaluation
		else if (Ace) {
			// Looks for Ace, King, Queen, Jack, 10
			if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.KING
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.QUEEN
					&& CardsInHand.get(eCardNo.FourthCard.getCardNo())
							.getRank() == eRank.JACK
					&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TEN) {
				Straight = true;
				// Looks for Ace, 2, 3, 4, 5
			} else if (CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TWO
					&& CardsInHand.get(eCardNo.FourthCard.getCardNo())
							.getRank() == eRank.THREE
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.FOUR
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank() == eRank.FIVE) {
				Straight = true;
			} else {
				Straight = false;
			}
			// Looks for straight without Ace
		} else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
				.getRank() == CardsInHand.get(eCardNo.SecondCard.getCardNo())
				.getRank().getRank() + 1
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
						.getRank() == CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getRank().getRank() + 2
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
						.getRank() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank()
						.getRank() + 3
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
						.getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank().getRank() + 4) {
			Straight = true;
		} else {
			Straight = false;
		}

		// Evaluate Royal Flush
		if (Straight == true
				&& Flush == true
				&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TEN
				&& Ace) {
			ScoreHand(eHandStrength.RoyalFlush, 0, 0, 0);
		}

		// Straight Flush
		else if (Straight == true && Flush == true) {
			ScoreHand(eHandStrength.StraightFlush,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0, 0);
		}
		// Four of a Kind
		if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.FourthCard.getCardNo()).getRank()) { // testing to see if the first card is the same number as the fourth
			ScoreHand(eHandStrength.FourOfAKind,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0, CardsInHand.get(eCardNo.FifthCard.getCardNo())
							.getRank().getRank());
		}
		
		else if  (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.FifthCard.getCardNo()).getRank()) { // testing to see if the second card is the same number as the fifth
			ScoreHand(eHandStrength.FourOfAKind,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank(), 0, CardsInHand.get(eCardNo.FirstCard.getCardNo())
								.getRank().getRank());
		}

		// Full House
		// testing for all possible pairs
		if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == 
				CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()){
			Pair12 = true;} else { Pair12 =false;}
		
		if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == 
				CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()){
			Pair23 = true;} else { Pair23 = false;}
		
		if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == 
				CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()){
			Pair34 = true;} else { Pair34 = false;}
		
		if (CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == 
				CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()){
			Pair45 = true;} else { Pair45 = false; }
		
		// testing for all possible three-of-a-kinds
		if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == 
				CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()) {
			ThreeKind13 = true;} else { ThreeKind13 = false;}
		
		if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == 
				CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ThreeKind35 = true;} else { ThreeKind35 = false; }
		
		if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == 
				CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()) {
			ThreeKind24 = true;} else { ThreeKind24 = false;}
		
		if (Pair12 == true && ThreeKind35 == true){ // checking for a pair and then and three-of-a-kind
			ScoreHand(eHandStrength.FullHouse,CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
					.getRank() , 
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
					.getRank(), 0);}
		else if (Pair45 == true && ThreeKind13 == true){ // checking for a three-of-a-kind and a pair
			ScoreHand(eHandStrength.FullHouse,CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
					.getRank() , 
					CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()
					.getRank(), 0);}


		// Flush
		if (Flush == true // checking only for a flush excluding royals and straights
				&& Ace == false
				&& Straight == false){
			ScoreHand(eHandStrength.Flush,CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
					.getRank() , 0, 0);
		}
		
		// Straight
		if (Straight == true //checking for a straight excluding flushes and royals
				&& Flush == false
				&& Ace == false){
			ScoreHand(eHandStrength.Straight,CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
					.getRank() , 0, 0);
		}
		
		// Three of a Kind
		// each one of these checks to make sure its not also a fullhouse
		if (Pair12 != true && ThreeKind35 == true){
			ScoreHand(eHandStrength.ThreeOfAKind,CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
					.getRank() , 0,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
					.getRank());}
		else if (Pair45 != true && ThreeKind13 == true){
			ScoreHand(eHandStrength.ThreeOfAKind,CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
					.getRank() , 0,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
					.getRank());}
		else if (ThreeKind24 == true){
			ScoreHand(eHandStrength.ThreeOfAKind,CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
					.getRank() , 0,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
					.getRank());}

		
		// Two Pair
		if (Pair12 == true && Pair34 == true){
			ScoreHand(eHandStrength.TwoPair,CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
					.getRank() , 
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
					.getRank(),
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
					.getRank());}
		else if (Pair23 == true && Pair34 == true){
			ScoreHand(eHandStrength.TwoPair,CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
					.getRank() , 
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
					.getRank(),
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
					.getRank());}
		else if (Pair34 == true && Pair45 == true){
			ScoreHand(eHandStrength.TwoPair,CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()
					.getRank() , 
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
					.getRank(),
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
					.getRank());}

		// Pair
		// checks to make sure there are no other pairs or three-of-a-kinds
		if (Pair12 == true && Pair23 != true && Pair34 != true && Pair45 != true && ThreeKind35 != true){
			ScoreHand(eHandStrength.Pair,CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
					.getRank() , 0,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
					.getRank());}
		else if (Pair12 != true && Pair23 == true && Pair34 != true && Pair45 != true){
			ScoreHand(eHandStrength.Pair,CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
					.getRank() , 0,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
					.getRank());}
		else if (Pair12 != true && Pair23 != true && Pair34 == true && Pair45 != true){
			ScoreHand(eHandStrength.Pair,CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
					.getRank() , 0,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
					.getRank());}
		else if (Pair12 != true && Pair23 != true && Pair34 != true && Pair45 == true){
			ScoreHand(eHandStrength.Pair,CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()
					.getRank() , 0,
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
					.getRank());}
		
		// High Card
		else {
			ScoreHand(eHandStrength.HighCard,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank());
		}
	}


	private void ScoreHand(eHandStrength hST, int HiHand, int LoHand, int Kicker) {
		this.HandStrength = hST.getHandStrength();
		this.HiHand = HiHand;
		this.LoHand = LoHand;
		this.Kicker = Kicker;
		this.bScored = true;

	}

	/**
	 * Custom sort to figure the best hand in an array of hands
	 */
	public static Comparator<Hand> HandRank = new Comparator<Hand>() {

		public int compare(Hand h1, Hand h2) {

			int result = 0;

			result = h2.getHandStrength() - h1.getHandStrength();

			if (result != 0) {
				return result;
			}

			result = h2.getHighPairStrength() - h1.getHighPairStrength();
			if (result != 0) {
				return result;
			}

			result = h2.getLowPairStrength() - h1.getLowPairStrength();
			if (result != 0) {
				return result;
			}

			result = h2.getKicker() - h1.getKicker();
			if (result != 0) {
				return result;
			}

			return 0;
		}
	};
}
