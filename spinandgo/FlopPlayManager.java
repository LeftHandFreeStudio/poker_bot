package spinAndGo;

import java.util.Arrays;
import java.util.Random;

import org.omg.CosNaming._BindingIteratorImplBase;

public class FlopPlayManager {
	private GameState gs;
	private ActionPerformer action;
	private SituationEstimator se;
	
	//own card info
	private int ownCard1Value = 0;
	private int ownCard2Value = 0;
	private String ownCard1Color = "";
	private String ownCard2Color = "";
	private int kicker = 0;
	//flop card info
	private int[] flopCardValues;
	private String[] flopCardColors;
	//whole hand info
	private int[] hand;
	private String[] handColors;
	
	//flop info
	private boolean isFlopPaired = false;
	private boolean isFlopTripled = false;
	private boolean isFlopMono = false;
	private boolean isFlushPossible = false;
	private int pairedCard = 0;
	private int tripledCard = 0;

	// hand info
	private boolean isSecondPair = false;
	private boolean isTopPair = false;
	private boolean isOverpair = false;
	private boolean isOESD = false;
	private boolean isFD = false;
	private boolean isTwoPair = false;
	private boolean isTrips = false;
	private boolean isSet = false;
	private boolean isStraight = false;
	private boolean isFlush = false;
	private boolean isFullHouse = false;
	private boolean isQuads = false;

	// situational booleans and values
	private boolean anyHand = false;
	private boolean drawsAndSecondPairs = false;
	private boolean topPairOrBetter = false;
	private boolean topPairWithGoodKickerOrBetter = false;
	private boolean draws = false;
	private boolean secondPairAndBetter = false;
	
	private double opponentBet = 0;
	private Random r;
	private boolean isBottomPair;
	
	public FlopPlayManager(GameState gs, ActionPerformer actionPerformer, SituationEstimator se){
		this.gs = gs;
		action = actionPerformer;
		this.se = se;
		r = new Random();
	}
	public void play(){
		resetValues();
		readValuesFromGameState();
		sortCardValuesInCollections();
		checkIfFlopIsPairedOrTripled();
		checkForStraight();
		checkFor2or3SameColorsOnBoard();
		checkForFlushOrFlushDraw();
		estimateHandStrength();
		assignSituationalBooleans();
		countBetSizeIfOccured();
		
		actDependingOnSituation();
	}
	
	private void countBetSizeIfOccured() {
		if(didEnemyPerformedABet()){
			if(gs.getCurrentNumberOfPlayersHoldingCards() == 2){
				opponentBet = countEnemyBetSize3Way();
			}else if(gs.getCurrentNumberOfPlayersHoldingCards() == 1){
				opponentBet = countEnemyBetSizeHU();
			}else{
				opponentBet = 0;
			}
		}
	}
	private void resetValues() {
		 kicker = 0;
		 isFlopPaired = false;
		 isFlopTripled = false;
		 isFlopMono = false;
		 isFlushPossible = false;
		 pairedCard = 0;
		 tripledCard = 0;
		 opponentBet = 0;

		// hand info
		 isBottomPair = false;
		 isSecondPair = false;
		 isTopPair = false;
		 isOverpair = false;
		 isOESD = false;
		 isFD = false;
		 isTwoPair = false;
		 isTrips = false;
		 isSet = false;
		 isStraight = false;
		 isFlush = false;
		 isFullHouse = false;
		 isQuads = false;
		

		// situational s
		 anyHand = false;
		 drawsAndSecondPairs = false;
		 topPairOrBetter = false;
		 topPairWithGoodKickerOrBetter = false;
		 draws = false;
	}
	private void playHU() {
		if(isInPosition()){
			System.out.println("flop hu in pos");
			playHUInPosition();
		}else{
			System.out.println("flop hu oop");
			playHUOutOfPosition();
		}
	}

	private void playHUOutOfPosition() {
		if(gs.isPreFlopAgressor()){
			System.out.println("flop hu oop agg");
			playHUOutOfPositionAsAggressor();
		}else{
			System.out.println("flop hu oop no agg");
			playHUOutOfPositionAsDefender();
		}
	}
	private void playHUOutOfPositionAsDefender() {
		if(isPotReraised()){
			System.out.println("flop hu oop no agg re");
			if(topPairWithGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{ 
			if(topPairWithGoodKickerOrBetter || draws){
				raise33PercentOfPot();
			}else{
				fold();
			}
		}
	}
	private void playHUOutOfPositionAsAggressor() {
		if(isPotReraised()){
			System.out.println("flop hu oop agg re");
			if(topPairWithGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			if(topPairOrBetter){
				raise66PercentOfPot();
			}else if(draws){
				gs.setPreparingFlopCheckRaise(true);
				checkOrCall();
			}else{
				raise33PercentOfPot();
			}
		}
	}
	private void playHUInPosition() {
		if(gs.isPreFlopAgressor()){
			System.out.println("flop hu in pos agg");
			playHUInPositionAsAggressor();
		}else{
			System.out.println("flop hu in pos no agg");
			playHUInPositionAsDefender();
		}
	}
	private void playHUInPositionAsDefender() {
		if(isPotReraised()){
			System.out.println("flop hu in pos no agg re");
			if(topPairWithGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			if(didEnemyPerformedABet()){
				System.out.println("flop hu in pos no agg no re");
				if(opponentBet/((gs.getCurrentPotSize()-opponentBet)-opponentBet) <= 0.25f){
					if(topPairWithGoodKickerOrBetter){
						raise66PercentOfPot();
					}else if(topPairOrBetter){
						raiseHalfPot();
					}else{
						gs.setTryToFloat(true);
						checkOrCall();
					}
				}else if(opponentBet/(gs.getCurrentPotSize()-opponentBet) > 0.25f
						&& opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.5f){
					if(topPairWithGoodKickerOrBetter){
						raise66PercentOfPot();
					}else if(draws || topPairOrBetter){
						raiseHalfPot();
					}else if(anyHand){
						checkOrCall();
					}else{
						fold();
					}
				}else if(opponentBet/(gs.getCurrentPotSize()-opponentBet) > 0.5f
						&& opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 1){
					if(topPairWithGoodKickerOrBetter){
						raise66PercentOfPot();
					}else if(draws || topPairOrBetter){
						raiseHalfPot();
					}else if(secondPairAndBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else{
					if(topPairWithGoodKickerOrBetter){
						raiseHalfPot();
					}else{
						fold();
					}
				}
			}else{
				raise33PercentOfPot();
			}
		}
	}
	private void playHUInPositionAsAggressor() {
		if(isPotReraised()){
			System.out.println("flop hu po agg re");
			if(topPairWithGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			System.out.println("flop hu pos agg no re");
			if(didEnemyPerformedABet()){
				System.out.println("flop hu pos agg no re donk");
				if(opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
					if(topPairWithGoodKickerOrBetter){
						raiseHalfPot();
					}else if(topPairOrBetter){
						raiseHalfPot();
					}else{
						int ran = r.nextInt(3);
						if(ran == 1){
							raise33PercentOfPot();
						}else if(ran == 2){
							checkOrCall();
						}else{
							fold();
						}
					}
				}else if(opponentBet/(gs.getCurrentPotSize()-opponentBet) > 0.2f
						&& opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.6f){
					if(topPairWithGoodKickerOrBetter){
						raiseHalfPot();
					}else if(draws || topPairOrBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else{
					if(topPairWithGoodKickerOrBetter){
						raiseHalfPot();
					}else{
						fold();
					}
				}
			}else{
				System.out.println("flop hu pos agg no re  no donk");
				if(topPairOrBetter){
					raiseHalfPot();
				}else if(drawsAndSecondPairs){
					raise33PercentOfPot();
				}else{
					raise33PercentOfPot();
				}
			}
		}
	}
	private void playVS2Players() {
		if(isInPosition()){
			System.out.println("flop 3 way in pos");
			playVs2PlayersInPosition();
		}else{
			System.out.println("flop 3 way oop");
			playVs2PlayersOutOfPosition();
		}
	}
	private void playVs2PlayersOutOfPosition() {
		if(gs.isPreFlopAgressor()){
			System.out.println("flop 3 way oop agg");
			playVs2PlayersOutOfPositionAsAggressor();
		}else{
			System.out.println("flop 3 way oop no agg");
			playVs2PlayersOutOfPositionAsDefender();
		}
	}
	private void playVs2PlayersOutOfPositionAsDefender() {
		if(isPotReraised()){
			System.out.println("flop 3 way oop no agg re");
			if(topPairWithGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			System.out.println("flop 3 way oop def no re");
			if(didEnemyPerformedABet()){
				System.out.println("flop 3 way oop def no re somebody bet");
				if(opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
					if(topPairWithGoodKickerOrBetter){
						raiseHalfPot();
					}else if(topPairOrBetter){
						raiseHalfPot();
					}else if(anyHand){
						checkOrCall();
					}else{
						fold();
					}
				}else if(opponentBet/(gs.getCurrentPotSize()-opponentBet) > 0.2f
						&& opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.6f){
					if(topPairWithGoodKickerOrBetter){
						raiseHalfPot();
					}else if(draws || topPairOrBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else{
					if(topPairWithGoodKickerOrBetter){
						raiseHalfPot();
					}else{
						fold();
					}
				}
			}else{
				System.out.println("flop 3 way oop def no re no bet");
				if(topPairOrBetter){
					raiseHalfPot();
				}else if(drawsAndSecondPairs){
					raise33PercentOfPot();
				}else{
					fold();
				}
			}
		}
	}
	private void playVs2PlayersOutOfPositionAsAggressor() {
		if(isPotReraised()){
			System.out.println("flop 3 way oop agg re");
			if(topPairWithGoodKickerOrBetter){
				action.raiseUsingSecondDefaultButton();
				gs.increaseFlopActionsPerformed();
			}else{
				fold();
			}
		}else{
			System.out.println("flop 3 way oop agg no re");
			if(didEnemyPerformedABet()){
				System.out.println("flop 3 way oop agg no re somebody bet");
				if(opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
					if(topPairWithGoodKickerOrBetter){
						raiseHalfPot();
					}else if(topPairOrBetter){
						raiseHalfPot();
					}else if(anyHand){
						checkOrCall();
					}else{
						fold();
					}
				}else if(opponentBet/(gs.getCurrentPotSize()-opponentBet) > 0.2f
						&& opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.6f){
					if(topPairWithGoodKickerOrBetter){
						raiseHalfPot();
					}else if(draws || topPairOrBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else{
					if(topPairWithGoodKickerOrBetter){
						raiseHalfPot();
					}else{
						fold();
					}
				}
			}else{
				System.out.println("flop 3 way oop agg no re no bet");
				if(topPairOrBetter){
					raiseHalfPot();
				}else if(drawsAndSecondPairs){
					raise33PercentOfPot();
				}else{
					raise33PercentOfPot();
				}
			}
			
		}
	}
	private void playVs2PlayersInPosition() {
		if(gs.isPreFlopAgressor()){
			System.out.println("flop 3 way in pos agg");
			playVs2PlayersInPositionAsAggressor();
		}else{
			System.out.println("flop 3 way in pos no agg");
			playVs2PlayersInPositionAsDefender();
		}
	}
	private void playVs2PlayersInPositionAsDefender() {
		if(isPotReraised()){
			System.out.println("flop 3 way in pos def re");
			if(topPairWithGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			System.out.println("flop 3 way in pos def no re");
			if(didEnemyPerformedABet()){
				if(opponentBet/((gs.getCurrentPotSize()-opponentBet)-opponentBet) <= 0.25f){
					if(topPairWithGoodKickerOrBetter){
						raise66PercentOfPot();
					}else if(topPairOrBetter){
						raiseHalfPot();
					}else{
						gs.setTryToFloat(true);
						checkOrCall();
					}
				}else if(opponentBet/(gs.getCurrentPotSize()-opponentBet) > 0.25f
						&& opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.5f){
					if(topPairWithGoodKickerOrBetter){
						raise66PercentOfPot();
					}else if(draws || topPairOrBetter){
						raiseHalfPot();
					}else if(anyHand){
						checkOrCall();
					}else{
						fold();
					}
				}else if(opponentBet/(gs.getCurrentPotSize()-opponentBet) > 0.5f
						&& opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 1){
					if(topPairWithGoodKickerOrBetter){
						raise66PercentOfPot();
					}else if(draws || topPairOrBetter){
						raiseHalfPot();
					}else if(secondPairAndBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else{
					if(topPairWithGoodKickerOrBetter){
						raiseHalfPot();
					}else{
						fold();
					}
				}
			}else{
				System.out.println("flop 3 way oop def no re no bet");
				if(topPairOrBetter){
					raiseHalfPot();
				}else if(drawsAndSecondPairs){
					raise33PercentOfPot();
				}else{
					raise33PercentOfPot();
				}
			}
		}
	}
	private void playVs2PlayersInPositionAsAggressor() {
		if(isPotReraised()){
			System.out.println("flop 3 way in pos re");
			if(topPairWithGoodKickerOrBetter){
				action.raiseUsingSecondDefaultButton();
				gs.increaseFlopActionsPerformed();
			}else{
				fold();
			}
		}else{
			System.out.println("flop 3 way in pos no re");

			if(didEnemyPerformedABet()){
				System.out.println("flop 3 way pos agg no re somebody bet");
				if(opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
					if(topPairWithGoodKickerOrBetter){
						raiseHalfPot();
					}else if(topPairOrBetter){
						raiseHalfPot();
					}else{
						int ran = r.nextInt(3);
						if(ran == 1){
							raise33PercentOfPot();
						}else if(ran == 2){
							checkOrCall();
						}else{
							fold();
						}
					}
				}else if(opponentBet/(gs.getCurrentPotSize()-opponentBet) > 0.2f
						&& opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.6f){
					if(topPairWithGoodKickerOrBetter){
						raiseHalfPot();
					}else if(draws || topPairOrBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else{
					if(topPairWithGoodKickerOrBetter){
						raiseHalfPot();
					}else{
						fold();
					}
				}
			}else{
				System.out.println("flop 3 way pos agg no re no bet");
				if(topPairOrBetter){
					raiseHalfPot();
				}else if(drawsAndSecondPairs){
					raise33PercentOfPot();
				}else{
					raise33PercentOfPot();
				}
			}
		}
	}
	private void actDependingOnSituation() {
		if(!specialEventOccured()){
			playNormally();
		}
	}

	private void playNormally() {
		System.out.println("flop normal play");
		if(playersHoldingCards() == 2){
			System.out.println("flop 3 way");
			playVS2Players();
		}else if(playersHoldingCards() == 1){
			System.out.println("flop hu");
			playHU();
		}else{
			System.out.println("flop no way");
			action.minRaise();
		}
	}
	private boolean specialEventOccured() {
		boolean specialEvent = false;
		if(limping()){
			specialEvent = true;
		}else if(checkRaise()){
			specialEvent = true;
		}
		return specialEvent;
	}
	private boolean checkRaise() {
		boolean checkRaise = false;
		if(gs.isPreparingFlopCheckRaise()){
			checkRaise = true;
			if(didEnemyPerformedABet()){
				raiseHalfPot();
			}else{
				fold();
			}
		}
		return checkRaise;
	}
	private boolean limping() {
		boolean limping = false;
		if(gs.isWasPlayerLimping()){
			limping = true;
			System.out.println("flop limping raise");

			if(didEnemyPerformedABet()){
				System.out.println("flop liimp sb bet");
				if(countEnemyBetSizeHU()/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
					if(topPairWithGoodKickerOrBetter){
						raiseHalfPot();
						gs.setPreFlopAgressor(true);
					}else if(topPairOrBetter){
						raiseHalfPot();
						gs.setPreFlopAgressor(true);
					}else{
						int ran = r.nextInt(3);
						if(ran == 1){
							raise33PercentOfPot();
						}else if(ran == 2){
							checkOrCall();
						}else{
							fold();
						}
					}
				}else if(countEnemyBetSizeHU()/(gs.getCurrentPotSize()-opponentBet) > 0.2f
						&& countEnemyBetSizeHU()/(gs.getCurrentPotSize()-opponentBet) <= 0.6f){
					if(topPairWithGoodKickerOrBetter){
						raiseHalfPot();
						gs.setPreFlopAgressor(true);
					}else if(draws || topPairOrBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else{
					if(gs.getCurrentPlayerStack()/gs.getBigBlindValue() <= 12){
						if(topPairOrBetter){
							raiseHalfPot();
							gs.setPreFlopAgressor(true);
						}else{
							fold();
						}
					}else{
						if(topPairWithGoodKickerOrBetter){
							raiseHalfPot();
							gs.setPreFlopAgressor(true);
						}else{
							fold();
						}
					}
				}
			}else{
				System.out.println("flop limp no bet");
				if(topPairOrBetter){
					raiseHalfPot();
					gs.setPreFlopAgressor(true);
				}else if(drawsAndSecondPairs){
					raise33PercentOfPot();
					gs.setPreFlopAgressor(true);
				}else{
					if(gs.getBigBlinds() >= 7){
						raise33PercentOfPot();
					}else{
						fold();
					}
				}
			}
		}
		return limping;
	}
	private void estimateHandStrength() {
		if(ownCard1Value == ownCard2Value){ // check when hand is a pair
			if(ownCard1Value > flopCardValues[2]){
				isOverpair = true;
			}else if(ownCard1Value > flopCardValues[1]){
				isSecondPair = true;
			}
				for(int i = 0; i < 3; i ++){ // check for set
					if(ownCard1Value == flopCardValues[i]){
						isSecondPair = false;
						isSet = true;
					}
				}
			
		}else{
			int pair1Count = 0;
			int pair2Count = 0;
			for(int i = 0; i < 3; i ++){
				if(ownCard1Value == flopCardValues[i]){
					pair1Count++;
				}
				if(ownCard2Value == flopCardValues[i]){
					pair2Count++;
				}
			}
			if(pair1Count == 1 && pair2Count == 0){
				if(ownCard1Value == flopCardValues[2]){
					isTopPair = true;
					kicker = ownCard2Value;
				}else if(ownCard1Value == flopCardValues[1]){
					isSecondPair = true;
					kicker = ownCard2Value;
				}else if(ownCard1Value == flopCardValues[0]){
					isBottomPair = true;
				}
			}else if(pair1Count == 0 && pair2Count == 1){
				if(ownCard2Value == flopCardValues[2]){
					isTopPair = true;
					kicker = ownCard1Value;
				}else if(ownCard2Value == flopCardValues[1]){
					isSecondPair = true;
					kicker = ownCard1Value;
				}else if(ownCard2Value == flopCardValues[0]){
					isBottomPair = true;
				}
			}else if(pair1Count == 1 && pair2Count == 1){
				isTwoPair = true;
			}else if((pair1Count == 2 && pair2Count == 0) || (pair1Count == 0 && pair2Count == 2)){
				isTrips = true;
			}else if((pair1Count == 2 && pair2Count == 1) || (pair1Count == 1 && pair2Count == 2)){
				isFullHouse = true;
			}else if(pair1Count == 3 || pair2Count == 3){
				isQuads = true;
			}
			
		}
		if(isFlopTripled && ownCard1Value == ownCard2Value && ownCard1Value < 10){
			isFullHouse = false;
		}
	}
	private void checkForFlushOrFlushDraw() {
		int count = 0;
		if(handColors[3].equals(handColors[4])){
			for(int i = 0; i < 3; i++){
				if(handColors[3].equals(handColors[i])){
					count ++;
				}
			}
			if(count == 2){
				isFD = true;
			}else if (count == 3){
				isFlush = true;
			}
			
		}
	}
	private void checkFor2or3SameColorsOnBoard() {
		if(flopCardColors[0].equals(flopCardColors[1]) && flopCardColors[1].equals(flopCardColors[2])){
			isFlopMono = true;
		}else{
			if(flopCardColors[0].equals(flopCardColors[1]) || flopCardColors[1].equals(flopCardColors[2])  || flopCardColors[0].equals(flopCardColors[2])){
				isFlushPossible = true;
			}
		}
	}
	private void checkForStraight() {
		int[] ownC = {ownCard1Value,ownCard2Value};
		StraightEstimator.checkFlopHandForStraightOrOESD(flopCardValues, ownC);
		isStraight = StraightEstimator.getIsFlopStraight();
		isOESD = StraightEstimator.getIsFlopOESD();
		
	}
	private void readValuesFromGameState(){
		ownCard1Value = gs.getFirstCard().getCardValue();
		ownCard2Value = gs.getSecondCard().getCardValue();
		ownCard1Color = gs.getFirstCard().getCardColor();
		ownCard2Color = gs.getSecondCard().getCardColor();
		flopCardValues = new int[3];
		flopCardValues[0] = gs.getFlop1().getCardValue();
		flopCardValues[1] = gs.getFlop2().getCardValue();
		flopCardValues[2] = gs.getFlop3().getCardValue();
		flopCardColors = new String[3];
		flopCardColors[0] = gs.getFlop1().getCardColor();
		flopCardColors[1] = gs.getFlop2().getCardColor();
		flopCardColors[2] = gs.getFlop3().getCardColor();
		hand = new int[5];
		hand[0] = gs.getFlop1().getCardValue();
		hand[1] = gs.getFlop2().getCardValue();
		hand[2] = gs.getFlop3().getCardValue();
		hand[3] = ownCard1Value;
		hand[4] = ownCard2Value;
		handColors =  new String[5];
		handColors[0] = gs.getFlop1().getCardColor();
		handColors[1] = gs.getFlop2().getCardColor();
		handColors[2] = gs.getFlop3().getCardColor();
		handColors[3] = ownCard1Color;
		handColors[4] = ownCard2Color;
	}
	private void sortCardValuesInCollections() {
		Arrays.sort(flopCardValues);
		Arrays.sort(hand);
	}
	private void checkIfFlopIsPairedOrTripled() {
		System.out.println(flopCardValues[0] + "  " + flopCardValues[1] + "  " + flopCardValues[2] + "  " );
		System.out.println("flop paired check");
		if(flopCardValues[0] == flopCardValues[1] && flopCardValues[1] == flopCardValues[2]){
			tripledCard = flopCardValues[0];
			isFlopTripled = true;
		}else if(flopCardValues[0] == flopCardValues[1]){
			System.out.println("flop paired");
			pairedCard = flopCardValues[0];
			isFlopPaired = true;
			System.out.println(isFlopPaired);
		}else if(flopCardValues[1] == flopCardValues[2]){
			System.out.println("flop paired");
			pairedCard = flopCardValues[1];
			isFlopPaired = true;
			System.out.println(isFlopPaired);
		}
	}
	private boolean isPotReraised(){
		return gs.getFlopActionsPerformed() >= 1;
	}
	private boolean isInPosition(){
		boolean inPostion = false;
		if(gs.getCurrentPlayersNumber() == 2){
			if(gs.getPosition() == 0){
				inPostion = true;
			}else if(gs.getPosition() == 2){
				if(!gs.getLeftPlayer().hasCards()){
					inPostion = true;
				}
			}
		}else if(gs.getCurrentPlayersNumber() == 1){
			if(gs.getPosition() == 0){
				inPostion = true;
			}
		}else{
			inPostion = true;
		}
		return inPostion;
	}
	private boolean didEnemyPerformedABet(){
		return se.isFoldButtonAvailable();
	}
	private void assignSituationalBooleans() {
		if(isBottomPair ||isSecondPair  || isTopPair  || isOverpair  || isOESD  || isFD  || isTwoPair  || isTrips 
				 || isSet  || isStraight  || isFlush  || isFullHouse  || isQuads ){
			 anyHand = true;
		 }
		if(isSecondPair  || isTopPair  || isOverpair  || isOESD  || isFD  || isTwoPair  || isTrips 
				 || isSet  || isStraight  || isFlush  || isFullHouse  || isQuads ){
			 secondPairAndBetter = true;
		 }
		if(isSecondPair || isOESD  || isFD){
			 drawsAndSecondPairs = true;
		 }
		if(isOESD  || isFD){
			 draws = true;
		 }
		
		if(isTopPair  || isOverpair  || (isOESD  && isFD)  || isTwoPair  || isTrips 
				 || isSet  || isStraight  || isFlush  || isFullHouse  || isQuads ){
			topPairOrBetter = true;
		 }
		if((isTopPair  && kicker >=8) || isOverpair  || (isOESD  && isFD) || isTwoPair  || isTrips 
				 || isSet  || isStraight  || isFlush  || isFullHouse  || isQuads ){
			topPairWithGoodKickerOrBetter = true;
		 }
		if(topPairOrBetter && (isOESD || isFD)){
			topPairWithGoodKickerOrBetter = true;
		}
	}
	private void fold(){
		action.foldIfPossibleCheckIfNot();
	}
	private void raise33PercentOfPot(){
		action.raiseUsingFirstDefaultButton();
		gs.increaseFlopActionsPerformed();
	}
	private void raiseHalfPot(){
		action.raiseUsingSecondDefaultButton();
		gs.increaseFlopActionsPerformed();
	}
	private void raise66PercentOfPot(){
		action.raiseUsingThirdDefaultButton();
		gs.increaseFlopActionsPerformed();
	}
	private void checkOrCall(){
		if(didEnemyPerformedABet()){
			action.callOrCheck();
			gs.increaseFlopActionsPerformed();
		}else{
			action.callOrCheck();
		}
	}
	private double countEnemyBetSize3Way(){
		double bet = 0;
		if(didEnemyPerformedABet()){
			double leftPlayerInitialStack;
			double rightPlayerInitialStack;
			double currentPlayerStack = gs.getCurrentPlayerStack();
			
				leftPlayerInitialStack =  gs.getLeftPlayer().getInitialFlopStack();
				System.out.println("lp init stack "+ leftPlayerInitialStack);
				System.out.println("lp curr stack "+ gs.getLeftPlayer().getCurrentStackSize());
				double lPlayerBet = leftPlayerInitialStack - gs.getLeftPlayer().getCurrentStackSize();
				
				rightPlayerInitialStack = gs.getRightPlayer().getInitialFlopStack();
				System.out.println("rp init stack "+ rightPlayerInitialStack);
				System.out.println("rp curr stack "+ gs.getRightPlayer().getCurrentStackSize());
				double rPlayerBet = rightPlayerInitialStack - gs.getRightPlayer().getCurrentStackSize();
				if(lPlayerBet >= rPlayerBet){
					bet = lPlayerBet;
				}else{
					bet = rPlayerBet;
				}
				System.out.println("3 way incoming rbet = " + rPlayerBet);
				System.out.println("3 way incoming lbet = " + lPlayerBet);
				if(bet >= currentPlayerStack){
					bet = currentPlayerStack;
				}
				System.out.println("3 way incoming bet = " + bet);
				if(bet <= 0){
					bet = 1;
				}
		}
		System.out.println("3 way incoming corrected bet = " + bet);
		System.out.println("% of pot = " + bet/(gs.getCurrentPotSize() - bet));
		return bet;
	}
	private double countEnemyBetSizeHU(){
		double bet = 0;
		if(didEnemyPerformedABet()){
			double currentPlayerStack = gs.getCurrentPlayerStack();
			bet = gs.getCurrentPotSize() - gs.getStartingPotSizeOnTheFlop();
			if(bet >= currentPlayerStack){
				bet = currentPlayerStack;
			}
			System.out.println("3 way incoming bet = " + bet);
			if(bet <= 0){
				bet = 1;
			}
		}
		System.out.println("HU incoming bet = " + bet);
		System.out.println("% of pot = " + bet/(gs.getCurrentPotSize() - bet));
		return bet;
	}
	private double countEnemyReraiseSize3Way(){
		double bet = 0;
		if(didEnemyPerformedABet() && gs.getFlopActionsPerformed() > 0){
			double currentPlayerStack = gs.getCurrentPlayerStack();
			double initialPlayerStack = gs.getInitialPlayerFlopStackSize();
			double initialPlayerBet = initialPlayerStack - currentPlayerStack;
			double leftPlayerInitialStack;
			double rightPlayerInitialStack;
			
				leftPlayerInitialStack =  gs.getLeftPlayer().getInitialFlopStack();
				System.out.println("lp init stack "+ leftPlayerInitialStack);
				System.out.println("lp curr stack "+ gs.getLeftPlayer().getCurrentStackSize());
				double lPlayerBet = leftPlayerInitialStack - gs.getLeftPlayer().getCurrentStackSize();
				
				rightPlayerInitialStack = gs.getRightPlayer().getInitialFlopStack();
				System.out.println("rp init stack "+ rightPlayerInitialStack);
				System.out.println("rp curr stack "+ gs.getRightPlayer().getCurrentStackSize());
				double rPlayerBet = rightPlayerInitialStack - gs.getRightPlayer().getCurrentStackSize();
				if(lPlayerBet >= rPlayerBet){
					bet = lPlayerBet;
				}else{
					bet = rPlayerBet;
				}
				System.out.println("3 way incoming rearaise = " + rPlayerBet);
				System.out.println("3 way incoming reraise = " + lPlayerBet);
				if(bet >= currentPlayerStack){
					bet = currentPlayerStack;
				}
				bet -= initialPlayerBet;
				if(bet <= 0){
					bet = 1;
				}
				System.out.println("3 way incoming reraise = " + bet);
				System.out.println("% of initial bet = " + bet/initialPlayerBet);
		}
		return bet;
	}
	private double countEnemyReraiseSizeHU(){
		double bet = 0;
		if(didEnemyPerformedABet() && gs.getFlopActionsPerformed() > 0){
			double currentPlayerStack = gs.getCurrentPlayerStack();
			double initialPlayerStack = gs.getInitialPlayerFlopStackSize();
			if(gs.getLeftPlayer().hasCards()){
				double initialLStack = gs.getLeftPlayer().getInitialFlopStack();
				double currentLStack = gs.getLeftPlayer().getCurrentStackSize();
				bet = initialLStack-currentLStack - (initialPlayerStack - currentPlayerStack);
			}else{
				double initialLStack = gs.getRightPlayer().getInitialFlopStack();
				double currentLStack = gs.getRightPlayer().getCurrentStackSize();
				bet = initialLStack-currentLStack - (initialPlayerStack - currentPlayerStack);
			}
			if(bet >= currentPlayerStack){
				bet = currentPlayerStack;
			}
			System.out.println("3 way incoming bet = " + bet);
			if(bet <= 0){
				bet = 1;
			}
		}
		System.out.println("HU incoming reraise = " + bet);
		System.out.println("% of pot = " + bet/(gs.getCurrentPotSize() - bet));
		return bet;
	}
	private int playersHoldingCards(){
		return gs.getCurrentNumberOfPlayersHoldingCards();
	}
}
