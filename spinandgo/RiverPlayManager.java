package spinAndGo;

import java.util.Arrays;

public class RiverPlayManager {
	private GameState gs;
	private ActionPerformer action;
	private SituationEstimator s;
	

	//own card info
	private int ownCard1Value = 0;
	private int ownCard2Value = 0;
	private String ownCard1Color = "";
	private String ownCard2Color = "";
	private int kicker = 0;
	

	//river card info
	private int[] riverCardValues;
	private String[] riverCardColors;
	//whole hand info
	private int[] hand;
	private String[] handColors;
	

	// hand info
	private boolean isKickerGood = false;
	private boolean isSecondPair = false;
	private boolean isTopPair = false;
	private boolean isOverpair = false;
	private boolean isTwoPair = false;
	private boolean isTrips = false;
	private boolean isSet = false;
	private boolean isStraight = false;
	private boolean isFlush = false;
	private boolean isFullHouse = false;
	private boolean isQuads = false;
	
	// situational booleans
	private boolean isFlushOnTable = false;
	private boolean isFourCardsToStraightOnTable = false;
	private boolean anyHand = false;
	private boolean topPairOrBetter = false;
	private boolean topPairGoodKickerOrBetter = false;
	private boolean beatingFlush = false;
	
	private boolean onePairOnBoard;
	private boolean twoPairsOnBoard;
	private boolean isBoardTripled;
	private boolean fullHouseOnBoard;
	private boolean quadsOnBoard;
	private int pairedCard;
	private double opponentBet;
	private String kindOfColorOnTable = "";
	
	public RiverPlayManager(GameState gs, ActionPerformer actionPerformer, SituationEstimator se){
		this.gs = gs;
		action = actionPerformer;
		s = se;
	}
	public void play(){
		resetValues();
		readValuesFromGameState();
		countBetSizeIfOccured();
		sortCardValuesInCollections();
		checkHowManySameCardsOnTheTable();
		checkForStraight();
		checkForFlushAndFlushdraw();
		assignHandStrength();
		assignSituationalBooleans();
		chooseActionAcordingToSituation();
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
	private boolean specialEventOccured() {
		boolean specialEvent = false;
		specialEvent = fourCardFlushOnTableEvent();
		return specialEvent;
	}
	private boolean fourCardFlushOnTableEvent() {
		boolean occured = false;
		if(isFlushOnTable){
			occured = true;
			if((ownCard1Color.equals(kindOfColorOnTable) && ownCard1Value >=8) || (ownCard2Color.equals(kindOfColorOnTable) && ownCard1Value >=8)){
				if(isInPosition()){
					if(didEnemyPerformedABet()){
						if(opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
							checkOrCall();
						}else{
							fold();
						}
					}else{
						raise33PercentOfPot();
					}
				}else{
					fold();
				}
			}else if(beatingFlush){
				if(isInPosition()){
					raise66PercentOfPot();
				}else{
					if(didEnemyPerformedABet()){
						raise66PercentOfPot();
					}else{
						fold();
					}
				}
			}else{
				if(isInPosition()){
					if(didEnemyPerformedABet()){
						fold();
					}else{
						raise33PercentOfPot();
					}
				}else{
					fold();
				}
			}
		}
		return occured;
	}

	private void chooseActionAcordingToSituation() {
		if(!specialEventOccured()){
			playNormally();
		}
	}
	private void playNormally() {
		System.out.println("river normal play");
		if(playersHoldingCards() == 2){
			System.out.println("river 3 way");
			playVS2Players();
		}else if(playersHoldingCards() == 1){
			System.out.println("river hu");
			playHU();
		}else{
			System.out.println("river no way");
			action.minRaise();
		}
	}
	private void playVS2Players() {
		System.out.println("river 3way play");
		if(isInPosition()){
			System.out.println("river 3 way in pos");
			playVs2PlayersInPosition();
		}else{
			System.out.println("river 3 way oop");
			playVs2PlayersOutOfPosition();
		}
	}
	private void playHU() {
		System.out.println("river HU play");
		if(isInPosition()){
			System.out.println("river hu in pos");
			playHUInPosition();
		}else{
			System.out.println("river hu oop");
			playHUOutOfPosition();
		}
	}
	private void playHUOutOfPosition() {
		if(gs.isPreFlopAgressor()){
			System.out.println("river hu oop agg");
			playHUOutOfPositionAsAggressor();
		}else{
			System.out.println("river hu oop no agg");
			playHUOutOfPositionAsDefender();
		}
	}
	private void playHUOutOfPositionAsDefender() {
		if(isPotReraised()){
			System.out.println("river hu oop no agg re");
			if(topPairGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{ 
			if(topPairGoodKickerOrBetter){
				raise33PercentOfPot();
			}else{
				fold();
			}
		}
	}
	private void playHUOutOfPositionAsAggressor() {
		if(isPotReraised()){
			System.out.println("river hu oop agg re");
			if(topPairGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			if(topPairOrBetter){
				raise66PercentOfPot();
			}else{
				fold();
			}
		}
	}
	private void playHUInPosition() {
		if(gs.isPreFlopAgressor()){
			System.out.println("river hu in pos agg");
			playHUInPositionAsAggressor();
		}else{
			System.out.println("river hu in pos no agg");
			playHUInPositionAsDefender();
		}
	}
	private void playHUInPositionAsDefender() {
		if(isPotReraised()){
			System.out.println("river hu in pos no agg re");
			if(topPairGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			if(didEnemyPerformedABet()){
				System.out.println("river hu in pos no agg no re");
				if(opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(topPairOrBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else if(opponentBet/(gs.getCurrentPotSize()-opponentBet) > 0.2f
						&& opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.6f){
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(topPairOrBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else{
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else{
						fold();
					}
				}
			}else{
				if(topPairGoodKickerOrBetter){
					raiseHalfPot();
				}else if(topPairOrBetter){
					checkOrCall();
				}else{
					fold();
				}
			}
		}
	}
	private void playHUInPositionAsAggressor() {
		if(isPotReraised()){
			System.out.println("river hu po agg re");
			if(topPairGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			System.out.println("river hu pos agg no re");
			if(didEnemyPerformedABet()){
				System.out.println("river hu pos agg no re donk");
				if(opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(topPairOrBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else if(opponentBet/(gs.getCurrentPotSize()-opponentBet) > 0.2f
						&& opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.6f){
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(topPairOrBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else{
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else{
						fold();
					}
				}
			}else{
				System.out.println("river hu pos agg no re  no donk");
				if(topPairOrBetter){
					raiseHalfPot();
				}else{
					fold();
				}
			}
		}
	}
	private void playVs2PlayersOutOfPosition() {
		if(gs.isPreFlopAgressor()){
			System.out.println("river 3 way oop agg");
			playVs2PlayersOutOfPositionAsAggressor();
		}else{
			System.out.println("river 3 way oop no agg");
			playVs2PlayersOutOfPositionAsDefender();
		}
	}
	private void playVs2PlayersOutOfPositionAsDefender() {
		if(isPotReraised()){
			System.out.println("river 3 way oop no agg re");
			if(topPairGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			System.out.println("river 3 way oop def no re");
			if(didEnemyPerformedABet()){
				System.out.println("river 3 way oop def no re somebody bet");
				if(opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(topPairOrBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else if(opponentBet/(gs.getCurrentPotSize()-opponentBet) > 0.2f
						&& opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.6f){
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(topPairOrBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else{
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else{
						fold();
					}
				}
			}else{
				System.out.println("river 3 way oop def no re no bet");
				if(topPairOrBetter){
					raiseHalfPot();
				}else{
					fold();
				}
			}
		}
	}
	private void playVs2PlayersOutOfPositionAsAggressor() {
		if(isPotReraised()){
			System.out.println("river 3 way oop agg re");
			if(topPairGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			System.out.println("river 3 way oop agg no re");
			if(didEnemyPerformedABet()){
				System.out.println("river 3 way oop agg no re somebody bet");
				if(opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(topPairOrBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else if(opponentBet/(gs.getCurrentPotSize()-opponentBet) > 0.2f
						&& opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.6f){
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(topPairOrBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else{
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else{
						fold();
					}
				}
			}else{
				System.out.println("river 3 way oop agg no re no bet");
				if(topPairOrBetter){
					raiseHalfPot();
				}else{
					fold();
				}
			}
			
		}
	}
	private void playVs2PlayersInPosition() {
		if(gs.isPreFlopAgressor()){
			System.out.println("river 3 way in pos agg");
			playVs2PlayersInPositionAsAggressor();
		}else{
			System.out.println("river 3 way in pos no agg");
			playVs2PlayersInPositionAsDefender();
		}
	}
	private void playVs2PlayersInPositionAsDefender() {
		if(isPotReraised()){
			System.out.println("river 3 way in pos def re");
			if(topPairGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			System.out.println("river 3 way in pos def no re");
			if(didEnemyPerformedABet()){
				System.out.println("river 3 way oop def no re somebody bet");
				if(opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(topPairOrBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else if(opponentBet/(gs.getCurrentPotSize()-opponentBet) > 0.2f
						&& opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.6f){
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(topPairOrBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else{
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else{
						fold();
					}
				}
			}else{
				System.out.println("river 3 way oop def no re no bet");
				if(topPairOrBetter){
					raiseHalfPot();
				}else{
					fold();
				}
			}
		}
	}
	private void playVs2PlayersInPositionAsAggressor() {
		if(isPotReraised()){
			System.out.println("river 3 way in pos re");
			if(topPairGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			System.out.println("river 3 way in pos no re");

			if(didEnemyPerformedABet()){
				System.out.println("river 3 way pos agg no re somebody bet");
				if(opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(topPairOrBetter){
						checkOrCall();
					}else if(anyHand){
						fold();
					}
				}else if(opponentBet/(gs.getCurrentPotSize()-opponentBet) > 0.2f
						&& opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.6f){
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(topPairOrBetter){
						checkOrCall();
					}else{
						fold();
					}
				}else{
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else{
						fold();
					}
				}
			}else{
				System.out.println("river 3 way pos agg no re no bet");
				if(topPairOrBetter){
					raiseHalfPot();
				}else{
					fold();
				}
			}
		}
	}
	private void checkHowManySameCardsOnTheTable() {
		// one pair
		int turn = gs.getTurn().getCardValue();
		int river = gs.getRiver().getCardValue();
		int[] tmpFlop = {gs.getFlop1().getCardValue(),gs.getFlop2().getCardValue(),gs.getFlop3().getCardValue()};
		int[] tmpTurn = {gs.getFlop1().getCardValue(),gs.getFlop2().getCardValue(),gs.getFlop3().getCardValue(),turn};

		Arrays.sort(tmpFlop);
		Arrays.sort(tmpTurn);
		boolean triple = false;
		boolean doubled = false;
		if(tmpFlop[0] == tmpFlop[1] && tmpFlop[1] == tmpFlop[2]){
			triple = true;
		}else if (tmpFlop[0] == tmpFlop[1]  ||  tmpFlop[1] == tmpFlop[2]) {
			doubled = true;
			pairedCard = tmpFlop[1];
		}
		if(triple){
			if(turn == tmpFlop[0]){
				quadsOnBoard = true;
			}else{
				isBoardTripled = true;
			}
		}else if(doubled){
			if(tmpFlop[0] == tmpFlop[1]){
				if(turn == tmpFlop[0]){
					isBoardTripled = true;
				}else if(turn == tmpFlop[2]){
					twoPairsOnBoard = true;
				}else{
					onePairOnBoard = true;
				}
			}else{
				if(turn == tmpFlop[1]){
					isBoardTripled = true;
				}else if(turn == tmpFlop[0]){
					twoPairsOnBoard = true;
				}else{
					onePairOnBoard = true;
				}
			}
		}else{
			if(turn == tmpFlop[0]){
				onePairOnBoard = true;
				pairedCard = turn;
			}else if(turn == tmpFlop[1]){
				onePairOnBoard = true;
				pairedCard = turn;
			}else if(turn == tmpFlop[2]){
				onePairOnBoard = true;
				pairedCard = turn;
			}
		}
		//+ river
		if(onePairOnBoard){
			if(river == pairedCard){
				isBoardTripled = true;
			}else{
				for(int i = 0; i < 4; i++){
					if(river == tmpTurn[i]){
						twoPairsOnBoard = true;
					}
				}
			}
		}else if(twoPairsOnBoard){
			if(river == tmpTurn[0]){
				fullHouseOnBoard = true;
			}else if(river == tmpTurn[3]){
				fullHouseOnBoard = true;
			}
		}else if(isBoardTripled){
			if(tmpTurn[0] == tmpTurn[1]){ // first 3 cards tripled
				if(river == tmpTurn[0]){
					quadsOnBoard = true;
				}else if(river == tmpTurn[3]){
					fullHouseOnBoard = true;
				}
			}else{// last 3 cards tripled
				if(river == tmpTurn[3]){
					quadsOnBoard = true;
				}else if(river == tmpTurn[0]){
					fullHouseOnBoard = true;
				}
			}
		}
	}
	private int playersHoldingCards(){

		return gs.getCurrentNumberOfPlayersHoldingCards();
	
	}
	private void assignSituationalBooleans() {
		anyHand = false;
		topPairOrBetter = false;
		topPairGoodKickerOrBetter = false;
		
		if(isSecondPair  || isTopPair  || isOverpair || isTwoPair  || isTrips 
				 || isSet  || isStraight  || isFlush  || isFullHouse  || isQuads ){
			 anyHand = true;
		 }
		if(isTopPair  || isOverpair|| isTwoPair  || isTrips 
				 || isSet  || isStraight  || isFlush  || isFullHouse  || isQuads ){
			topPairOrBetter = true;
		 }
		if((isTopPair  && isKickerGood) || isOverpair || isTwoPair  || isTrips 
				 || isSet  || isStraight  || isFlush  || isFullHouse  || isQuads ){
			topPairGoodKickerOrBetter = true;
		 }

		if(isFlush || (isStraight && isFlush) || isFullHouse  || isQuads ){
			beatingFlush  = true;
		 }
	}
	private void assignHandStrength() {

		if(ownCard1Value == ownCard2Value){
			if(ownCard1Value > riverCardValues[4]){
				isOverpair = true;
			}else{
				for(int i = 0; i < 5; i ++){
					if(ownCard1Value == riverCardValues[i]){
						isSet = true;
					}
				}
			}
		}else{
			int pair1Count = 0;
			int pair2Count = 0;
			for(int i = 0; i < 5; i ++){
				if(ownCard1Value == riverCardValues[i]){
					pair1Count++;
				}
				if(ownCard2Value == riverCardValues[i]){
					pair2Count++;
				}
			}
			if(pair1Count == 1 && pair2Count == 0){
				if(ownCard1Value == riverCardValues[4]){
					isTopPair = true;
					if(ownCard2Value >= 10){
						isKickerGood = true;
					}
				}
				
			}else if(pair1Count == 0 && pair2Count == 1){
				if(ownCard2Value == riverCardValues[4]){
					isTopPair = true;
					if(ownCard1Value >= 10){
						isKickerGood = true;
					}
				}
			}else if(pair1Count == 1 && pair2Count == 1){
				isTwoPair = true;
			}else if(pair1Count == 2 && pair2Count == 0){
				isTrips = true;
				if(ownCard2Value >= 12){
					isKickerGood = true;
				}
			}else if(pair2Count == 2 && pair1Count == 0){
				isTrips = true;
				if(ownCard1Value >= 12){
					isKickerGood = true;
				}
			}else if((pair1Count == 2 && pair2Count == 1) || (pair1Count == 1 && pair2Count == 2)){
				isFullHouse = true;
			}else if(pair1Count == 3 || pair2Count == 3){
				isQuads = true;
			}
			
		}
		
		if(riverCardValues[0] == riverCardValues[1] && riverCardValues[2] == riverCardValues[3] && (ownCard1Value == riverCardValues[3] || ownCard2Value == riverCardValues[3])){
			isFullHouse = true;
		}else if(riverCardValues[0] == riverCardValues[1] && riverCardValues[2] == riverCardValues[3] && (ownCard1Value == riverCardValues[1] || ownCard2Value == riverCardValues[1])){
			isFullHouse = false;
		}else if(riverCardValues[1] == riverCardValues[2] && riverCardValues[3] == riverCardValues[4] && (ownCard1Value == riverCardValues[3] || ownCard2Value == riverCardValues[3])){
			isFullHouse = true;
		}else if(riverCardValues[1] == riverCardValues[2] && riverCardValues[3] == riverCardValues[4] && (ownCard1Value == riverCardValues[1] || ownCard2Value == riverCardValues[1])){
			isFullHouse = false;
		}else if(riverCardValues[0] == riverCardValues[1] && riverCardValues[3] == riverCardValues[4] && (ownCard1Value == riverCardValues[3] || ownCard2Value == riverCardValues[3])){
			isFullHouse = true;
		}else if(riverCardValues[0] == riverCardValues[1] && riverCardValues[3] == riverCardValues[4] && (ownCard1Value == riverCardValues[1] || ownCard2Value == riverCardValues[1])){
			isFullHouse = false;
		}
	}
	private void checkForFlushAndFlushdraw() {
		int spadesCount = 0;
		int heartsCount = 0;
		int diamondsCount = 0;
		int clubsCount = 0;

		for(int i = 0; i < 5; i++){
			if(riverCardColors[i].equals("s")){
				spadesCount ++;
				if(spadesCount >= 4){
					kindOfColorOnTable = "s";
				}
			}else if(riverCardColors[i].equals("h")){
				heartsCount ++;
				if(heartsCount >= 4){
					kindOfColorOnTable = "h";
				}
			}else if(riverCardColors[i].equals("c")){
				clubsCount ++;
				if(clubsCount >= 4){
					kindOfColorOnTable = "c";
				}
			}else if(riverCardColors[i].equals("d")){
				diamondsCount ++;
				if(diamondsCount >= 4){
					kindOfColorOnTable = "d";
				}
			}
		}
			/*if(spadesCount == 3 || heartsCount == 3 || clubsCount == 3 || diamondsCount == 3){
				isFlushPossible = true;
			}else */if(spadesCount >= 4 || heartsCount >= 4 || clubsCount >= 4 || diamondsCount >= 4){
				isFlushOnTable = true;
				if(ownCard1Value == 14){
					if(ownCard1Color.equals("s") && spadesCount >= 4){
						isFlushOnTable = false;
						isFlush = true;
					}else if(ownCard1Color.equals("h") && heartsCount >= 4){
						isFlushOnTable = false;
						isFlush = true;
					}else if(ownCard1Color.equals("d") && diamondsCount >= 4){
						isFlushOnTable = false;
						isFlush = true;
					}else if(ownCard1Color.equals("c") && clubsCount >= 4){
						isFlushOnTable = false;
						isFlush = true;
					}
				}else if(ownCard2Value == 14){
					if(ownCard2Color.equals("s") && spadesCount >= 4){
						isFlushOnTable = false;
						isFlush = true;
					}else if(ownCard2Color.equals("h") && heartsCount >= 4){
						isFlushOnTable = false;
						isFlush = true;
					}else if(ownCard2Color.equals("d") && diamondsCount >= 4){
						isFlushOnTable = false;
						isFlush = true;
					}else if(ownCard2Color.equals("c") && clubsCount >= 4){
						isFlushOnTable = false;
						isFlush = true;
					}
				}
			}
			
			if(!isFlushOnTable){
				int count = 0;
				if(handColors[5].equals(handColors[6])){
					for(int i = 0; i < 5; i++){
						if(handColors[5].equals(handColors[i])){
							count ++;
						}
					}
					if (count == 3){
						isFlush = true;
					}
				}
			}

			if(quadsOnBoard || isBoardTripled || twoPairsOnBoard){
				isFlush = false;
			}
	}
	private void checkForStraight() {
		int[] ownC = {ownCard1Value,ownCard2Value};
		StraightEstimator.checkRiverHandForStraightOrOESD(riverCardValues, ownC);
		isStraight = StraightEstimator.getIsRiverStraight();
		isFourCardsToStraightOnTable = StraightEstimator.getIsRiverStraightOnTable();
	}
	private void resetValues() {

		 kicker = 0;
		 opponentBet = 0;/*
		 isFlopPaired = false;
		 isFlopTripled = false;
		 isFlopMono = false;
		 isFlushPossible = false;
		 pairedCard = 0;
		 tripledCard = 0;*/

		// hand info
		 isKickerGood = false;
		 isSecondPair = false;
		 isTopPair = false;
		 isOverpair = false;
		 isTwoPair = false;
		 isTrips = false;
		 isSet = false;
		 isStraight = false;
		 isFlush = false;
		 isFullHouse = false;
		 isQuads = false;
		

		// situational s
		 anyHand = false;
		 topPairOrBetter = false;
		 topPairGoodKickerOrBetter = false;
		 isFourCardsToStraightOnTable = false;
		 isFlushOnTable = false;
		 beatingFlush = false;
		 
		 //
		 pairedCard = 0;
		 onePairOnBoard = false;
		 twoPairsOnBoard = false;
		 isBoardTripled = false;
		 quadsOnBoard = false;
		 fullHouseOnBoard = false;
	}
	private void sortCardValuesInCollections() {
		Arrays.sort(hand);
		Arrays.sort(riverCardValues);
	}
	private void readValuesFromGameState() {
		ownCard1Value = gs.getFirstCard().getCardValue();
		ownCard2Value = gs.getSecondCard().getCardValue();
		
		ownCard1Color = gs.getFirstCard().getCardColor();
		ownCard2Color = gs.getSecondCard().getCardColor();
		
		riverCardValues = new int[5];
		riverCardValues[0] = gs.getFlop1().getCardValue();
		riverCardValues[1] = gs.getFlop2().getCardValue();
		riverCardValues[2] = gs.getFlop3().getCardValue();
		riverCardValues[3] = gs.getTurn().getCardValue();
		riverCardValues[4] = gs.getRiver().getCardValue();
		riverCardColors = new String[5];
		riverCardColors[0] = gs.getFlop1().getCardColor();
		riverCardColors[1] = gs.getFlop2().getCardColor();
		riverCardColors[2] = gs.getFlop3().getCardColor();
		riverCardColors[3] = gs.getTurn().getCardColor();
		riverCardColors[4] = gs.getRiver().getCardColor();
		hand = new int[7];
		hand[0] = gs.getFlop1().getCardValue();
		hand[1] = gs.getFlop2().getCardValue();
		hand[2] = gs.getFlop3().getCardValue();
		hand[3] = gs.getTurn().getCardValue();
		hand[4] = gs.getRiver().getCardValue();
		hand[5] = ownCard1Value;
		hand[6] = ownCard2Value;
		handColors =  new String[7];
		handColors[0] = gs.getFlop1().getCardColor();
		handColors[1] = gs.getFlop2().getCardColor();
		handColors[2] = gs.getFlop3().getCardColor();
		handColors[3] = gs.getTurn().getCardColor();
		handColors[4] = gs.getRiver().getCardColor();
		handColors[5] = ownCard1Color;
		handColors[6] = ownCard2Color;
	}
	private boolean isPotReraised(){
		return gs.getRiverActionsPerformed() >= 1;
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
		return s.isFoldButtonAvailable();
	}
	private void fold(){
		action.foldIfPossibleCheckIfNot();
	}
	private void raise33PercentOfPot(){
		action.raiseUsingFirstDefaultButton();
		gs.increaseRiverActionsPerformed();
	}
	private void raiseHalfPot(){
		action.raiseUsingSecondDefaultButton();
		gs.increaseRiverActionsPerformed();
	}
	private void raise66PercentOfPot(){
		action.raiseUsingThirdDefaultButton();
		gs.increaseRiverActionsPerformed();
	}
	private void checkOrCall(){
		if(didEnemyPerformedABet()){
			action.callOrCheck();
			gs.increaseRiverActionsPerformed();
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
			
				leftPlayerInitialStack =  gs.getLeftPlayer().getInitialRiverStack();
				System.out.println("lp init stack "+ leftPlayerInitialStack);
				System.out.println("lp curr stack "+ gs.getLeftPlayer().getCurrentStackSize());
				double lPlayerBet = leftPlayerInitialStack - gs.getLeftPlayer().getCurrentStackSize();
				
				rightPlayerInitialStack = gs.getRightPlayer().getInitialRiverStack();
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
			bet = gs.getCurrentPotSize() - gs.getStartingPotSizeOnTheRiver();
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
	
}
