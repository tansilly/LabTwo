package pokerBase;

public class Play  {
	
	Deck newDeck; 
	Hand newHand;
	
	public Play(){
		newDeck = new pokerBase.Deck(); // create a new deck
		newHand = new pokerBase.Hand(); // create a hand
		newHand.EvalHand(); // evaluate the hand
		
	}
	
	
	

}
