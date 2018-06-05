package spinAndGo;

import java.util.Arrays;

public class TurnPlayManager {
	private GameState gs;
	private ActionPerformer action;
	private SituationEstimator se;
	
	//own card info
	private int ownCard1Value = 0;
	private int ownCard2Value = 0;
	private String ownCard1Color = "";
	private String ownCard2Color = "";
	private int kicker = 0;

	//turn card info
	private int[] turnCardValues;
	private String[] turnCardColors;
	//whole hand info
	private int[] hand;
	private String[] handColors;

	// hand info
	private boolean isKickerGood = false;
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

	// situational booleans
	private boolean isFlushOnTable = false;
	private boolean isFourCardsToStraightOnTable = false;
	private boolean anyHand = false;
	private boolean draws;
	private boolean topPairOrBetter = false;
	private boolean topPairGoodKickerOrBetter;
	
	private boolean onePairOnBoard;
	private boolean twoPairsOnBoard;
	private boolean isBoardTripled;
	private boolean quadsOnBoard;
	private int pairedCard;
	private double opponentBet;
	private String kindOfColorOnTable = "";
	private boolean beatingFlush;
	
	
	public TurnPlayManager(GameState gs, ActionPerformer actionPerformer, SituationEstimator se){
		this.gs = gs;
		action = actionPerformer;
		this.se = se;
	}
	
	public void play(){
		resetValues();
		readValuesFromGameState();
		countBetSizeIfOccured();
		sortCardValuesInCollections();
		checkHowManySameCardsOnTheTable();
		checkForStraightsAndStraightDraws();
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
		if(gs.getTurnActionsPerformed() == 0){
			specialEvent = opponentCheckedBehindFlop();
			specialEvent = checkIfFloatingPosible();
		}
		return specialEvent;
	}
	private boolean checkIfFloatingPosible() {
		boolean floating = false;
		if(gs.getCurrentNumberOfPlayersHoldingCards() == 1 && gs.isTryToFloat() && isInPosition() && !didEnemyPerformedABet()){
			raiseHalfPot();
		}
		return floating;
	}

	private boolean opponentCheckedBehindFlop() {
		boolean checkBack = false;
		if(gs.getCurrentNumberOfPlayersHoldingCards() == 1){
			if(gs.getLeftPlayer().hasCards()){
				if(!gs.isPreFlopAgressor()){
					if(isInPosition()){
						if((gs.getLeftPlayer().getInitialFlopStack() == gs.getLeftPlayer().getInitialTurnStack())){
							if(!didEnemyPerformedABet()){
								checkBack = true;
								System.out.println("play against Checkback");
								raise33PercentOfPot();
							}
						}
					}else{
						if((gs.getLeftPlayer().getInitialFlopStack() == gs.getLeftPlayer().getInitialTurnStack())){
								checkBack = true;
								System.out.println("play against Checkback");
								raise33PercentOfPot();
						}
					}
				}
			}else{
				if(!gs.isPreFlopAgressor()){
					if(isInPosition()){
						if((gs.getRightPlayer().getInitialFlopStack() == gs.getRightPlayer().getInitialTurnStack())){
							if(!didEnemyPerformedABet()){
								checkBack = true;
								System.out.println("play against Checkback");
								raise33PercentOfPot();
							}
						}
					}else{
						if((gs.getRightPlayer().getInitialFlopStack() == gs.getRightPlayer().getInitialTurnStack())){
								checkBack = true;
								System.out.println("play against Checkback");
								raise33PercentOfPot();
						}
					}
				}
			}
		}
		return checkBack;
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
						fold();
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
				fold();
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
		System.out.println("turn normal play");
		if(playersHoldingCards() == 2){
			System.out.println("turn 3 way");
			playVS2Players();
		}else if(playersHoldingCards() == 1){
			System.out.println("turn hu");
			playHU();
		}else{
			System.out.println("turn no way");
			action.minRaise();
		}
	}
	private void playVS2Players() {
		System.out.println("turn 3way play");
		if(isInPosition()){
			System.out.println("turn 3 way in pos");
			playVs2PlayersInPosition();
		}else{
			System.out.println("turn 3 way oop");
			playVs2PlayersOutOfPosition();
		}
	}
	private void playHU() {
		System.out.println("turn HU play");
		if(isInPosition()){
			System.out.println("turn hu in pos");
			playHUInPosition();
		}else{
			System.out.println("turn hu oop");
			playHUOutOfPosition();
		}
	}
	private void playHUOutOfPosition() {
		if(gs.isPreFlopAgressor()){
			System.out.println("turn hu oop agg");
			playHUOutOfPositionAsAggressor();
		}else{
			System.out.println("turn hu oop no agg");
			playHUOutOfPositionAsDefender();
		}
	}
	private void playHUOutOfPositionAsDefender() {
		if(isPotReraised()){
			System.out.println("turn hu oop no agg re");
			if(topPairGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{ 
			if(topPairGoodKickerOrBetter || draws){
				raise33PercentOfPot();
			}else{
				fold();
			}
		}
	}
	private void playHUOutOfPositionAsAggressor() {
		if(isPotReraised()){
			System.out.println("turn hu oop agg re");
			if(topPairGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			if(topPairOrBetter){
				raise66PercentOfPot();
			}else if(anyHand){
				raise33PercentOfPot();
			}else{
				fold();
			}
		}
	}
	private void playHUInPosition() {
		if(gs.isPreFlopAgressor()){
			System.out.println("turn hu in pos agg");
			playHUInPositionAsAggressor();
		}else{
			System.out.println("turn hu in pos no agg");
			playHUInPositionAsDefender();
		}
	}
	private void playHUInPositionAsDefender() {
		if(isPotReraised()){
			System.out.println("turn hu in pos no agg re");
			if(topPairGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			if(didEnemyPerformedABet()){
				System.out.println("turn hu in pos no agg no re");
				if(opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
					if(topPairGoodKickerOrBetter){
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
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(draws || topPairOrBetter){
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
				fold();
			}
		}
	}
	private void playHUInPositionAsAggressor() {
		if(isPotReraised()){
			System.out.println("turn hu po agg re");
			if(topPairGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			System.out.println("turn hu pos agg no re");
			if(didEnemyPerformedABet()){
				System.out.println("turn hu pos agg no re donk");
				if(opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
					if(topPairGoodKickerOrBetter){
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
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(draws || topPairOrBetter){
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
				System.out.println("turn hu pos agg no re  no donk");
				if(topPairOrBetter){
					raiseHalfPot();
				}else if(anyHand){
					raise33PercentOfPot();
				}else{
					fold();
				}
			}
		}
	}
	private void playVs2PlayersOutOfPosition() {
		if(gs.isPreFlopAgressor()){
			System.out.println("turn 3 way oop agg");
			playVs2PlayersOutOfPositionAsAggressor();
		}else{
			System.out.println("turn 3 way oop no agg");
			playVs2PlayersOutOfPositionAsDefender();
		}
	}
	private void playVs2PlayersOutOfPositionAsDefender() {
		if(isPotReraised()){
			System.out.println("turn 3 way oop no agg re");
			if(topPairGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			System.out.println("turn 3 way oop def no re");
			if(didEnemyPerformedABet()){
				System.out.println("turn 3 way oop def no re somebody bet");
				if(opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
					if(topPairGoodKickerOrBetter){
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
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(draws || topPairOrBetter){
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
				System.out.println("turn 3 way oop def no re no bet");
				if(topPairOrBetter){
					raiseHalfPot();
				}else if(anyHand){
					raise33PercentOfPot();
				}else{
					fold();
				}
			}
		}
	}
	private void playVs2PlayersOutOfPositionAsAggressor() {
		if(isPotReraised()){
			System.out.println("turn 3 way oop agg re");
			if(topPairGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			System.out.println("turn 3 way oop agg no re");
			if(didEnemyPerformedABet()){
				System.out.println("turn 3 way oop agg no re somebody bet");
				if(opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
					if(topPairGoodKickerOrBetter){
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
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(draws || topPairOrBetter){
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
				System.out.println("turn 3 way oop agg no re no bet");
				if(topPairOrBetter){
					raiseHalfPot();
				}else if(anyHand){
					raise33PercentOfPot();
				}else{
					fold();
				}
			}
			
		}
	}
	private void playVs2PlayersInPosition() {
		if(gs.isPreFlopAgressor()){
			System.out.println("turn 3 way in pos agg");
			playVs2PlayersInPositionAsAggressor();
		}else{
			System.out.println("turn 3 way in pos no agg");
			playVs2PlayersInPositionAsDefender();
		}
	}
	private void playVs2PlayersInPositionAsDefender() {
		if(isPotReraised()){
			System.out.println("turn 3 way in pos def re");
			if(topPairGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			System.out.println("turn 3 way in pos def no re");
			if(didEnemyPerformedABet()){
				System.out.println("turn 3 way oop def no re somebody bet");
				if(opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
					if(topPairGoodKickerOrBetter){
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
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(draws || topPairOrBetter){
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
				System.out.println("turn 3 way oop def no re no bet");
				if(topPairOrBetter){
					raiseHalfPot();
				}else if(anyHand){
					raise33PercentOfPot();
				}else{
					raise33PercentOfPot();
				}
			}
		}
	}
	private void playVs2PlayersInPositionAsAggressor() {
		if(isPotReraised()){
			System.out.println("turn 3 way in pos re");
			if(topPairGoodKickerOrBetter){
				raiseHalfPot();
			}else{
				fold();
			}
		}else{
			System.out.println("turn 3 way in pos no re");

			if(didEnemyPerformedABet()){
				System.out.println("turn 3 way pos agg no re somebody bet");
				if(opponentBet/(gs.getCurrentPotSize()-opponentBet) <= 0.2f){
					if(topPairGoodKickerOrBetter){
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
					if(topPairGoodKickerOrBetter){
						raiseHalfPot();
					}else if(draws || topPairOrBetter){
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
				System.out.println("turn 3 way pos agg no re no bet");
				if(topPairOrBetter){
					raiseHalfPot();
				}else if(anyHand){
					raise33PercentOfPot();
				}else{
					fold();
				}
			}
		}
	}
	private void checkHowManySameCardsOnTheTable() {
		// one pair
		int[] tmpFlop = {gs.getFlop1().getCardValue(),gs.getFlop2().getCardValue(),gs.getFlop3().getCardValue()};
		int turn = gs.getTurn().getCardValue();
		Arrays.sort(tmpFlop);
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
	}
	private int playersHoldingCards(){
		return gs.getCurrentNumberOfPlayersHoldingCards();
	}
	private void readValuesFromGameState(){
		ownCard1Value = gs.getFirstCard().getCardValue();
		ownCard2Value = gs.getSecondCard().getCardValue();
		ownCard1Color = gs.getFirstCard().getCardColor();
		ownCard2Color = gs.getSecondCard().getCardColor();
		turnCardValues = new int[4];
		turnCardValues[0] = gs.getFlop1().getCardValue();
		turnCardValues[1] = gs.getFlop2().getCardValue();
		turnCardValues[2] = gs.getFlop3().getCardValue();
		turnCardValues[3] = gs.getTurn().getCardValue();
		turnCardColors = new String[4];
		turnCardColors[0] = gs.getFlop1().getCardColor();
		turnCardColors[1] = gs.getFlop2().getCardColor();
		turnCardColors[2] = gs.getFlop3().getCardColor();
		turnCardColors[3] = gs.getTurn().getCardColor();
		hand = new int[6];
		hand[0] = gs.getFlop1().getCardValue();
		hand[1] = gs.getFlop2().getCardValue();
		hand[2] = gs.getFlop3().getCardValue();
		hand[3] = gs.getTurn().getCardValue();
		hand[4] = ownCard1Value;
		hand[5] = ownCard2Value;
		handColors =  new String[6];
		handColors[0] = gs.getFlop1().getCardColor();
		handColors[1] = gs.getFlop2().getCardColor();
		handColors[2] = gs.getFlop3().getCardColor();
		handColors[3] = gs.getTurn().getCardColor();
		handColors[4] = ownCard1Color;
		handColors[5] = ownCard2Color;
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
		 topPairOrBetter = false;
		 topPairGoodKickerOrBetter = false;
		 draws = false;
		 isFourCardsToStraightOnTable = false;
		 isFlushOnTable = false;
		 beatingFlush = false;
		 
		 //
		 pairedCard = 0;
		 onePairOnBoard = false;
		 twoPairsOnBoard = false;
		 isBoardTripled = false;
		 quadsOnBoard = false;
		 kindOfColorOnTable = "";
	}
	private void sortCardValuesInCollections() {
		Arrays.sort(turnCardValues);
		Arrays.sort(hand);
	}
	private boolean isPotReraised(){
		return gs.getTurnActionsPerformed() >= 1;
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
	private void fold(){
		action.foldIfPossibleCheckIfNot();
	}
	private void raise33PercentOfPot(){
		action.raiseUsingFirstDefaultButton();
		gs.increaseTurnActionsPerformed();
	}
	private void raiseHalfPot(){
		action.raiseUsingSecondDefaultButton();
		gs.increaseTurnActionsPerformed();
	}
	private void raise66PercentOfPot(){
		action.raiseUsingThirdDefaultButton();
		gs.increaseTurnActionsPerformed();
	}
	private void checkOrCall(){
		if(didEnemyPerformedABet()){
			action.callOrCheck();
			gs.increaseTurnActionsPerformed();
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
			
				leftPlayerInitialStack =  gs.getLeftPlayer().getInitialTurnStack();
				System.out.println("lp init stack "+ leftPlayerInitialStack);
				System.out.println("lp curr stack "+ gs.getLeftPlayer().getCurrentStackSize());
				double lPlayerBet = leftPlayerInitialStack - gs.getLeftPlayer().getCurrentStackSize();
				
				rightPlayerInitialStack = gs.getRightPlayer().getInitialTurnStack();
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
			bet = gs.getCurrentPotSize() - gs.getStartingPotSizeOnTheTurn();
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
	private void assignSituationalBooleans() {
		anyHand = false;
		draws = false;
		topPairOrBetter = false;
		topPairGoodKickerOrBetter = false;
		
		if(isSecondPair  || isTopPair  || isOverpair  || isOESD  || isFD  || isTwoPair  || isTrips 
				 || isSet  || isStraight  || isFlush  || isFullHouse  || isQuads ){
			 anyHand = true;
		 }
		if(isOESD  || isFD){
			 draws = true;
		 }
		if(isTopPair  || isOverpair  || (isOESD  && isFD)  || isTwoPair  || isTrips 
				 || isSet  || isStraight  || isFlush  || isFullHouse  || isQuads ){
			topPairOrBetter = true;
		 }
		if((isTopPair  && isKickerGood) || isOverpair  || (isOESD  && isFD ) || isTwoPair  || isTrips 
				 || isSet  || isStraight  || isFlush  || isFullHouse  || isQuads ){
			topPairGoodKickerOrBetter = true;
		 }
		if(isFlush || (isStraight && isFlush) || isFullHouse  || isQuads ){
			beatingFlush = true;
		 }

		if(topPairOrBetter && (isOESD || isFD)){
			topPairGoodKickerOrBetter = true;
		}
	}
	private void checkForFlushAndFlushdraw() {
		int spadesCount = 0;
		int heartsCount = 0;
		int diamondsCount = 0;
		int clubsCount = 0;
			for(int i = 0; i < 4; i++){
				if(turnCardColors[i].equals("s")){
					spadesCount ++;
					if(spadesCount == 4){
						kindOfColorOnTable = "s";
					}
				}else if(turnCardColors[i].equals("h")){
					heartsCount ++;
					if(heartsCount == 4){
						kindOfColorOnTable = "h";
					}
				}else if(turnCardColors[i].equals("c")){
					clubsCount ++;
					if(clubsCount == 4){
						kindOfColorOnTable = "c";
					}
				}else if(turnCardColors[i].equals("d")){
					diamondsCount ++;
					if(diamondsCount == 4){
						kindOfColorOnTable = "d";
					}
				}
			}
		/*if(spadesCount == 3 || heartsCount == 3 || clubsCount == 3 || diamondsCount == 3){
			//isFlushPossible = true;
		}else */
			if(spadesCount == 4 || heartsCount == 4 || clubsCount == 4 || diamondsCount == 4){
			isFlushOnTable = true;
			if(ownCard1Value == 14 || ownCard1Value == 13){
				if(ownCard1Color.equals("s") && spadesCount == 4){
					isFlushOnTable = false;
					isFlush = true;
				}else if(ownCard1Color.equals("h") && heartsCount == 4){
					isFlushOnTable = false;
					isFlush = true;
				}else if(ownCard1Color.equals("d") && diamondsCount == 4){
					isFlushOnTable = false;
					isFlush = true;
				}else if(ownCard1Color.equals("c") && clubsCount == 4){
					isFlushOnTable = false;
					isFlush = true;
				}
			}else if(ownCard2Value == 14 || ownCard2Value == 13){
				if(ownCard2Color.equals("s") && spadesCount == 4){
					isFlushOnTable = false;
					isFlush = true;
				}else if(ownCard2Color.equals("h") && heartsCount == 4){
					isFlushOnTable = false;
					isFlush = true;
				}else if(ownCard2Color.equals("d") && diamondsCount == 4){
					isFlushOnTable = false;
					isFlush = true;
				}else if(ownCard2Color.equals("c") && clubsCount == 4){
					isFlushOnTable = false;
					isFlush = true;
				}
			}
		}
		

		if(!isFlushOnTable){
		int count = 0;
			if(handColors[4].equals(handColors[5])){
				for(int i = 0; i < 4; i++){
					if(handColors[4].equals(handColors[i])){
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
		if(quadsOnBoard || isBoardTripled || twoPairsOnBoard){
			isFD = false;
			isFlush = false;
		}
		if(isFlush && isFD){ // if flush, no fd
			isFD = false;
		}
	}
	private void checkForStraightsAndStraightDraws() {
		int[] ownC = {ownCard1Value,ownCard2Value};
		StraightEstimator.checkTurnHandForStraightOrOESD(turnCardValues, ownC);
		isStraight = StraightEstimator.getIsTurnStraight();
		isOESD = StraightEstimator.getIsTurnOESD();
		isFourCardsToStraightOnTable = StraightEstimator.getIsTurnStraightOnTable();
	}
	private void assignHandStrength() {
		if(ownCard1Value == ownCard2Value){
			if(ownCard1Value > turnCardValues[3]){
				isOverpair = true;
			}else{
				for(int i = 0; i < 4; i ++){
					if(ownCard1Value == turnCardValues[i]){
						isSet = true;
					}
				}
				if(isSet == false){
					if(ownCard1Value < turnCardValues[3] && ownCard1Value > turnCardValues[2]){
						isSecondPair = true;
					}
				}
			}
		}else{
			int pair1Count = 0;
			int pair2Count = 0;
			for(int i = 0; i < 4; i ++){
				if(ownCard1Value == turnCardValues[i]){
					pair1Count++;
				}
				if(ownCard2Value == turnCardValues[i]){
					pair2Count++;
				}
			}
			if(pair1Count == 1 && pair2Count == 0){
				if(ownCard1Value == turnCardValues[3]){
					isTopPair = true;
					if(ownCard2Value >= 8){
						isKickerGood = true;
					}
				}else if(ownCard1Value == turnCardValues[2]){
					isSecondPair = true;
				}
				
			}else if(pair1Count == 0 && pair2Count == 1){
				if(ownCard2Value == turnCardValues[3]){
					isTopPair = true;
					if(ownCard1Value >= 8){
						isKickerGood = true;
					}
				}else if(ownCard2Value == turnCardValues[2]){
					isSecondPair = true;
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
		if(turnCardValues[0] == turnCardValues[1] && turnCardValues[2] == turnCardValues[3] && (ownCard1Value == turnCardValues[3] || ownCard2Value == turnCardValues[3])){
			isFullHouse = true;
		}else if(turnCardValues[0] == turnCardValues[1] && turnCardValues[2] == turnCardValues[3] && (ownCard1Value == turnCardValues[1] || ownCard2Value == turnCardValues[1])){
			isFullHouse = false;
		}
		if(quadsOnBoard && (ownCard1Value == 14 || ownCard2Value == 14)){
			isQuads = true;
		}else{
			isQuads = false;
		}
		
	}
}
