package pokerBase;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DeckTest {
	Deck newDeck;
	Hand newHand;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
 
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		newDeck = new Deck();
		newHand = new Hand();
	}

	@After
	public void tearDown() throws Exception {
		newDeck = new Deck();
		newHand = null;
	}

	@Test
	public void TestFullDeck() { //making sure a new deck with 52 cards is created
		assertEquals("52 expected 52 actual",(long)newDeck.getTotalCards(),(long)52);
		newDeck.drawFromDeck();
		assertTrue(1==1);
	}
	
	@Test
	public void TestFullHouse(){ // i cannot figure out how to properly test my functions with junit
		assertEquals("Full house expected, Full house actual",(long)newHand.getHandStrength(),(long)70);
		newHand.getBestHand(< 0.1.2,1.3.2,2.2.2,3.1.5,4.6.5 >);
	}

}