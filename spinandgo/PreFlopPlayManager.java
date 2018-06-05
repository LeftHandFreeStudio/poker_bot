package spinAndGo;

public class PreFlopPlayManager {
	private GameState gs;
	private ActionPerformer action;
	private CardRangeEstimator cr;
	private SituationEstimator estimator;
	public PreFlopPlayManager(GameState gs, ActionPerformer actionPerformer, SituationEstimator situationEstimator){
		this.gs = gs;
		action = actionPerformer;
		estimator = situationEstimator;
		cr = new CardRangeEstimator(gs);
	}
	public void play(){ // 0 dealer, 1 small blind, 2 big blind | plays only when is action required
		adjustStackSize();
		gs.toString();
		if(!specialEventOccured()){
			System.out.println("play normal");
			playUnderNormalConditions();
		}
	}
	private boolean specialEventOccured() {
		boolean specialEvent = false;
		if(gs.getBigBlinds() <= 12 && estimator.isFoldButtonAvailable() && gs.getCurrentNumberOfPlayersHoldingCards() == 1){
				defPOFNash11BB();
		}else if(gs.getBigBlinds() <= 8 && gs.getBigBlinds() > 3){
				playPOFNash8BB();
		}else if(gs.getBigBlinds() <= 3){
			System.out.println("play very low");
			specialEvent = true;
			playWithVeryLowStack();
		}else if(sitouts()){
			specialEvent = true;
		}
		return specialEvent;
	}
	private boolean sitouts(){
		boolean sitouts = false;
		if(gs.getLeftPlayer().isInGame() && gs.getRightPlayer().isInGame()){
			if(gs.getLeftPlayer().isSitout() && gs.getRightPlayer().isSitout()){
				action.minRaise();
				sitouts = true;
			}
		}else if(gs.getLeftPlayer().isInGame() && !gs.getRightPlayer().isInGame()){
			if(gs.getLeftPlayer().isSitout()){
				action.minRaise();
				sitouts = true;
			}
		}else if(!gs.getLeftPlayer().isInGame() && gs.getRightPlayer().isInGame()){
			if(gs.getRightPlayer().isSitout()){
				action.minRaise();
				sitouts = true;
			}
		}
		return sitouts;
	}
	private void playPOFNash8BB() {
		boolean nash = cr.nashRangePush8BB();
		if(nash){
			goAllIn();
		}else{
			checkOrFold();
		}
	}

	private void defPOFNash11BB() {
		boolean nash = cr.nashRangeDef11BB();
		if(nash){
			goAllIn();
		}else{
			checkOrFold();
		}
	}
	private void adjustStackSize() {
		if(gs.getInitialPlayerNumber() == 2){
			if(gs.getLeftPlayer().hasCards() && !gs.getRightPlayer().hasCards()){
					if(gs.getLeftPlayer().getInitialStackSize() < gs.getPlayersStaringStackSize()){
						gs.setBigBlinds(gs.getLeftPlayer().getInitialStackSize()/(gs.getBigBlindValue()));
						System.out.println(gs.getLeftPlayer().getInitialStackSize());
						System.out.println("adjuste to left 3 w right fold");
					}
			}else{
				if(gs.getLeftPlayer().getInitialStackSize() >= gs.getRightPlayer().getInitialStackSize()){
					if(gs.getLeftPlayer().getInitialStackSize() < gs.getPlayersStaringStackSize()){
						gs.setBigBlinds(gs.getLeftPlayer().getInitialStackSize()/(gs.getBigBlindValue()));
						System.out.println(gs.getLeftPlayer().getInitialStackSize());
						System.out.println("adjuste to left 3 w");
					}
				}else{
					if(gs.getRightPlayer().getInitialStackSize() < gs.getPlayersStaringStackSize()){
						gs.setBigBlinds(gs.getRightPlayer().getInitialStackSize()/(gs.getBigBlindValue()));
						System.out.println(gs.getRightPlayer().getInitialStackSize());
						System.out.println("adjuste to right 3 w");
					}
				}
			}
		}else if(gs.getInitialPlayerNumber() == 1){
			boolean leftPlayer = true;
			if(estimator.isPlayerOnTheLeftHoldingCards()){
				if(gs.getLeftPlayer().getCurrentStackSize() < gs.getPlayersStaringStackSize()){
					gs.setBigBlinds(gs.getLeftPlayer().getInitialStackSize()/(gs.getBigBlindValue()));
					System.out.println(gs.getLeftPlayer().getInitialStackSize());
					System.out.println("adjuste to left hu");
				}
			}else{
				leftPlayer = false;
				if(gs.getRightPlayer().getCurrentStackSize() < gs.getPlayersStaringStackSize()){
					gs.setBigBlinds(gs.getRightPlayer().getInitialStackSize()/(gs.getBigBlindValue()));
					System.out.println(gs.getRightPlayer().getInitialStackSize());
					System.out.println("adjuste to right hu");
				}
			}
			gs.setIsLeftPlayerInHU(leftPlayer);
		}
	}
	private void playWithVeryLowStack() {
		goAllIn();
	}
	private void playUnderNormalConditions() {
		if(gs.getInitialPlayerNumber() == 2){ // 3 way
			System.out.println("play 3way");
			if(gs.getPreFlopActionsPerformed() == 0){
				System.out.println("playOUPotWithoutReraise3Players");
				playOpenedOrUnopenedPotWithoutReraise3Players();
				//gs.increasePreFlopActionsPerformed();
			}else{
				System.out.println("playOPotReraise3Players");
				playOpenedPotReraised3Players();
			}
		}else if(gs.getInitialPlayerNumber() == 1){ // HU
			System.out.println("play HU");
			if(gs.getPreFlopActionsPerformed() == 0){
				System.out.println("play HU first action");
				playHU_firstAction();
				//gs.increasePreFlopActionsPerformed();
			}else{
				System.out.println("play HU next actions");
				playHU_reraised();
			}
		}else{
			System.out.println("play without players");
			action.minRaise();
		}
	}
	private void playHU_reraised() {
		if(gs.getPosition() == 0){ // button by player -> sb
			System.out.println("hu sb vs reraise");
			playHU_ReraisedSB();
		}else{ // button not by player -> bb
			System.out.println("hu bb vs reraise");
			playHU_ReraisedBB();
		}
	}
	private void playHU_ReraisedBB() {
		if(gs.isPreFlopAgressor()){
			if(isMinReRaisedAfterMinRaise()){
				call();
			}else{
				playSimplePushFold();
			}
		}else{
			playSimplePushFold();
		}
	}
	private void playHU_ReraisedSB() {
		if(gs.isWasPlayerLimping()){
			System.out.println("reraise sb after limp hu");
			if(isMinRaisedAfterLimpHUPot()){ // min raise after limp
				playHU_vs_MinRaiseAfterLimpingFromSB();
				System.out.println("reraise sb after limp hu min");
			}else{ //bigger than min raise
				playHU_vs_biggerReraiseAfterLimpingFromSB();
				System.out.println("reraise sb after limp hu big");
			}
		}else{// reraised sb no limping
			System.out.println("reraise sb after no limp hu");
			if(isPotSizeLessOrEqual(5)){
				System.out.println("reraise sb after no limp hu min");
				call();
			}else{
				System.out.println("reraise sb after no limp hu big re");
				playSimplePushFold();
			}
		}
	}
	private void playHU_vs_biggerReraiseAfterLimpingFromSB() {
		boolean limpAllinvsMinRaise = cr.defendSB_vs_Allin_afterLimping_lessThan10BB();
		if(limpAllinvsMinRaise){
			goAllIn();
		}else{
			checkOrFold();
		}
	}
	private void playHU_vs_MinRaiseAfterLimpingFromSB() {
		boolean limpCallMinRaise = cr.defendSB_vs_MinRaise_afterLimping();
		boolean limpAllinvsMinRaise = cr.defendSB_vs_Allin_afterLimping_lessThan10BB();
		if(limpCallMinRaise){
			call();
		}else if(limpAllinvsMinRaise){
			goAllIn();
		}else{
			checkOrFold();
		}
		
	}
	private void playHU_firstAction() {
		if(gs.getPosition() == 0){ // button by player -> sb
			System.out.println("hu sb first in");
			playHU_SB_firstIn();
		}else{ // button not by player -> bb
			if(isSBLimperInHUPot()){
				System.out.println("hu bb vs limper");
				playHU_BB_vsSBLimp();
			}else{
				System.out.println("hu bb vs open");
				playHU_BB_vsSBOpen();
			}
		}
	}
	private void playHU_BB_vsSBOpen() {
		if(isOpeningHURaiseUpTo3BB()){
			System.out.println("hu bb vs open up to 3 bb");
			boolean playAllin = cr.openFromSB_Allin();
			boolean play3Bet = cr.openFromSB_3bet();
			boolean playCall = cr.openFromSB_Call();
			boolean pof10bb = cr.pushOrFoldWith10BB();
			boolean pof5bb = cr.pushOrFoldWith5BB();
			if(playAllin){
				goAllIn();
			}else if(play3Bet){
				raiseInBB(3);
			}else if(playCall){
				call();
			}else if(pof10bb){
				goAllIn();
			}else if(pof5bb){
				goAllIn();
			}else{
				checkOrFold();
			}
		}else{
			System.out.println("hu bb big open");
			if(gs.isLeftPlayerInHU()){
				if(gs.getLeftPlayer().isAllin()){
					boolean simplePushFold = cr.simplePushFold();
					boolean defendAllin = cr.defendHU_AsBB_With10BB_vs_SB_Allin();
					if(simplePushFold){
						goAllIn();
					}else if(defendAllin){
						goAllIn();
					}else{
						checkOrFold();
					}
				}else{
					playSimplePushFold();
				}
			}else{
				if(gs.getRightPlayer().isAllin()){
					boolean simplePushFold = cr.simplePushFold();
					boolean defendAllin = cr.defendHU_AsBB_With10BB_vs_SB_Allin();
					if(simplePushFold){
						goAllIn();
					}else if(defendAllin){
						goAllIn();
					}else{
						checkOrFold();
					}
				}else{
					playSimplePushFold();
				}
			}
		}
	}
	private void playHU_BB_vsSBLimp() {
		boolean playVSLimper = cr.bbDefend_vs_limped_BTN_Allin();
		boolean playVSLimperCall3X = cr.limped_SB_RaiseCall3X();
		boolean playVSLimperRC2_5X = cr.limped_SB_RaiseCall2_5X();
		boolean playVSLimperRF2_5X = cr.limped_SB_RaiseFold2_5X();
		boolean playVSLimperRF3X = cr.limped_SB_RaiseFold3X();
		boolean playPOF = cr.pushOrFoldWith10BB();
		if(playVSLimper){
			goAllIn();
		}else if(playVSLimperCall3X){
			raiseInBB(3);
		}else if(playVSLimperRC2_5X){
			raise2_5BB();
		}else if(playVSLimperRF2_5X){
			raise2_5BB();
		}else if(playVSLimperRF3X){
			raiseInBB(3);
		}else if(playPOF){
			goAllIn();
		}else{
			limp();
		}
	}
	private void playHU_SB_firstIn() {
		boolean playSBAllIn = cr.unopenedPot_AllInFromSB();
		boolean playSB = cr.unopenedPot_MinRaiseFromSB();
		boolean playSBLimp = cr.unopenedHUPotSBLimp();
		if(playSBAllIn){
			goAllIn();
		}else if(playSB){
			minRaise();
		}else if(playSBLimp){
			limp();
		}else{
			checkOrFold();
		}
	}
	private void playOpenedOrUnopenedPotWithoutReraise3Players(){
		if(isPotOpened()){
			System.out.println("opened pot");
			playOpenedPotWithoutReraise();
		}else{
			System.out.println("unopened pot");
			playUnopenedPot(); // CHECKED
		}
	}
	private void playOpenedPotReraised3Players(){
		switch (gs.getPosition()) {//without bb, impossible to have unopened pot when starting on bb (without limpers)
		case 0:// btn
			playBTN_vs_Reraise();
			break;
		case 1: // small blind
			playSB_vs_Reraise();
			break;
		case 2: // big blind
			playBB_vs_Reraise();
			break;
		}
	}
	private void playBB_vs_Reraise() {
		playSimplePushFold();
		
	}
	private void playSB_vs_Reraise() {
		if(estimator.isPlayerOnTheLeftHoldingCards() && estimator.isPlayerOnTheRightHoldingCards()){ // minraise reraise to 3 bb and  1 call
			if(isPotSizeLessOrEqual(8) || isPotSizeLessOrEqual(7)){
				call();
			}else{
				playSimplePushFold();
			}
		}else if(!estimator.isPlayerOnTheLeftHoldingCards() && estimator.isPlayerOnTheRightHoldingCards()){
			if(isPotSizeLessOrEqual(6)){
				call();
			}else{
				playSimplePushFold();
			}
		}else if(estimator.isPlayerOnTheLeftHoldingCards() && !estimator.isPlayerOnTheRightHoldingCards()){
			if(isPotSizeLessOrEqual(6) || isPotSizeLessOrEqual(5)){
				call();
			}else{
				playSimplePushFold();
			}
		}else{
			playSimplePushFold();
		}
	}
	private void playBTN_vs_Reraise() {
		if(estimator.isPlayerOnTheLeftHoldingCards() && estimator.isPlayerOnTheRightHoldingCards()){ // minraise reraise to 3 bb and  1 call
			if(isPotSizeLessOrEqual(8)){
				call();
			}else{
				playSimplePushFold();
			}
		}else if(!estimator.isPlayerOnTheLeftHoldingCards() && estimator.isPlayerOnTheRightHoldingCards()){
			if(isPotSizeLessOrEqual(5.5f)){
				call();
			}else{
				playSimplePushFold();
			}
		}else if(estimator.isPlayerOnTheLeftHoldingCards() && !estimator.isPlayerOnTheRightHoldingCards()){
			if(isPotSizeLessOrEqual(6)){
				call();
			}else{
				playSimplePushFold();
			}
		}else{
			playSimplePushFold();
		}
	}
	private void playUnopenedPot() {// limpers doesnt count
		switch (gs.getPosition()) {//without bb, impossible to have unopened pot when starting on bb (without limpers)
		case 0:// btn
			System.out.println("btn unop play");
			playBTNUnopenedPot();
			break;
		case 1: // small blind
			System.out.println("sb unop play");
			playSBUnopenedPot();
			break;
		}
	}
	private void playSBUnopenedPot() {
		boolean playMinRaiseFromSB = cr.unopenedPot_MinRaiseFromSB();
		boolean playLimpFromSB = cr.unopenedPot_LimpFromSB();
		boolean playAllInFromSB = cr.unopenedPot_AllInFromSB();
		if(playMinRaiseFromSB){
			minRaise();
		}else if(playLimpFromSB){
			limp();
		}else if(playAllInFromSB){
			goAllIn();
		}else{
			checkOrFold();
		}
	}
	private void playBTNUnopenedPot() {
		boolean playMinRaiseFromBTN = cr.unopenedPot_MinRaiseFromBTN();
		boolean playAllInPlayBTN = cr.unopenedPot_AllinFromBTN();
		if(playMinRaiseFromBTN){
			minRaise();
		}else if(playAllInPlayBTN){
			goAllIn();
		}else{
			checkOrFold();
		}
	}
	private void playOpenedPotWithoutReraise() {
		switch (gs.getPosition()) {//without button, impossible to have opened pot when starting on button
		case 1: // small blind
			System.out.println("sb");
			if(isOneLimperInPot()){
				System.out.println("sb limp");
				playSB_vs_BTNlimp();
			}else{
				if(isOpeningRaiseUpTo3BB()){
					System.out.println("sb vs btn up to 3 bb open");
					playSB_vs_BTN_3BB_Open();
				}else{ // bigger open
					System.out.println("sb vs btn bigegr than 3 bb open");
					playSB_vs_BTN_biggerThan3BB_Open();
				}
			}
			break;
		case 2: //big blind
			System.out.println("bb");
			if(estimator.isPlayerOnTheLeftHoldingCards() && estimator.isPlayerOnTheRightHoldingCards()){ //vs both
				if(areTwoLimpers()){
					System.out.println("bb vs 2 limper  2 players in pot");
					playBB_vs_2Limpers();
				}else{// at least one bet, 2 players in pot, pot != 1,5 bb && != 3 bb
					System.out.println("bb vs 1 open 2 players in pot ");
					playBB_vs_atLeast_1Open();
				}
			}else if(estimator.isPlayerOnTheLeftHoldingCards() && !estimator.isPlayerOnTheRightHoldingCards()){// vs btn
				if(isOneLimperInPot()){
					System.out.println("bb vs btn limp  1 player in pot");
					playBB_vs_BTN_Limp();
				}else{
					if(isOpeningRaiseUpTo3BB()){
						System.out.println("bb vs btn max 3x raise  1 player in pot");
						bbPlay_vs_BTN_Open();
					}else{ // bigger open
						System.out.println("bb vs btn big bet  1 player in pot");
						bbPlay_vs_bigBTNOpen();
					}
				}
			}else if(!estimator.isPlayerOnTheLeftHoldingCards() && estimator.isPlayerOnTheRightHoldingCards()){ // vs sb
				if(isSBLimperInHUPot()){
					System.out.println("bb vs sb limp  1 player in pot");
					playBB_vs_SBlimp();
				}else{ // sb open
					if(isOpeningRaiseUpTo3BB()){
						System.out.println("bb vs sb max 3x raise  1 player in pot");
						bbPlay_vs_SB_Open();
					}else{ // bigger open
						System.out.println("bb vs sb big bet  1 player in pot");
						bbPlay_vs_bigSBOpen();
					}
				}
			}
			break;
		}
	}
	private void bbPlay_vs_bigSBOpen() {
		playSimplePushFold();
	}
	private void bbPlay_vs_SB_Open() {
		boolean playAllin_vs_btnOpen = cr.defendBB_Allinn();
		boolean play3B_vs_btnOpen = cr.defendBB_3B();
		boolean playCall_vs_btnOpen = cr.defendBB_Call();
		boolean playPOF = cr.pushOrFoldWith10BB();
		if(playAllin_vs_btnOpen){
			goAllIn();
		}else if(play3B_vs_btnOpen){
			raiseInBB(3);
		}else if(playCall_vs_btnOpen){
			call();
		}else if(playPOF){
			goAllIn();
		}else{
			checkOrFold();
		}
	}
	private void playBB_vs_SBlimp() {
		boolean playVSLimper = cr.bbDefend_vs_limped_BTN_Allin();
		boolean playVSLimperCall3X = cr.limped_SB_RaiseCall3X();
		boolean playVSLimperRC2_5X = cr.limped_SB_RaiseCall2_5X();
		boolean playVSLimperRF2_5X = cr.limped_SB_RaiseFold2_5X();
		boolean playVSLimperRF3X = cr.limped_SB_RaiseFold3X();
		boolean playPOF = cr.pushOrFoldWith10BB();
		if(playVSLimper){
			goAllIn();
		}else if(playVSLimperCall3X){
			raiseInBB(3);
		}else if(playVSLimperRC2_5X){
			raise2_5BB();
		}else if(playVSLimperRF2_5X){
			raise2_5BB();
		}else if(playVSLimperRF3X){
			raiseInBB(3);
		}else if(playPOF){
			goAllIn();
		}else{
			limp();
		}
	}
	private void bbPlay_vs_bigBTNOpen() {
		playSimplePushFold();
	}
	private void bbPlay_vs_BTN_Open() {
		boolean playAllin_vs_btnOpen = cr.defendBB_Allinn();
		boolean play3B_vs_btnOpen = cr.defendBB_3B();
		boolean playCall_vs_btnOpen = cr.defendBB_Call();
		boolean playPOF = cr.pushOrFoldWith10BB();
		if(playAllin_vs_btnOpen){
			goAllIn();
		}else if(play3B_vs_btnOpen){
			raiseInBB(3);
		}else if(playCall_vs_btnOpen){
			call();
		}else if(playPOF){
			goAllIn();
		}else{
			checkOrFold();
		}
	}
	private void playBB_vs_BTN_Limp() {
		boolean playVSLimper = cr.bbDefend_vs_limped_BTN_Allin();
		boolean playVSLimperCall3X = cr.limped_SB_RaiseCall3X();
		boolean playVSLimperRC2_5X = cr.limped_SB_RaiseCall2_5X();
		boolean playVSLimperRF2_5X = cr.limped_SB_RaiseFold2_5X();
		boolean playVSLimperRF3X = cr.limped_SB_RaiseFold3X();
		boolean playPOF = cr.pushOrFoldWith10BB();
		if(playVSLimper){
			goAllIn();
		}else if(playVSLimperCall3X){
			raiseInBB(3);
		}else if(playVSLimperRC2_5X){
			raise2_5BB();
		}else if(playVSLimperRF2_5X){
			raise2_5BB();
		}else if(playVSLimperRF3X){
			raiseInBB(3);
		}else if(playPOF){
			goAllIn();
		}else{
			limp();
		}
	}
	private void playBB_vs_atLeast_1Open() {
		double leftPlayerMoneyInPot = gs.getLeftPlayer().getInitialStackSize() - gs.getLeftPlayer().getCurrentStackSize();
		double rightPlayerMoneyInPot = gs.getRightPlayer().getInitialStackSize() - gs.getRightPlayer().getCurrentStackSize();
		
		if(leftPlayerMoneyInPot <= 3 * gs.getBigBlindValue() && leftPlayerMoneyInPot == rightPlayerMoneyInPot){ // raise up to 3 bb 2 calls
			playBB_vs_BTNRaise_SbCalls();
		}else if(leftPlayerMoneyInPot == gs.getBigBlindValue() && rightPlayerMoneyInPot < 3.5f*gs.getBigBlindValue() ){ //btn limp sb raise
			playBB_vs_BTNRaise_SbCalls();
		}else if(rightPlayerMoneyInPot > 3*gs.getBigBlindValue() || leftPlayerMoneyInPot > 3*gs.getBigBlindValue() ){ //big bet from one of players
			playVsBigBet();
		}else{
			checkOrFold();
		}
	}
	private void playVsBigBet() {
		boolean playOpenedPotAllIn = cr.simplePushFold();
		if(playOpenedPotAllIn){
			goAllIn();
		}else{
			checkOrFold();
		}
	}
	private void playBB_vs_BTNRaise_SbCalls() {
		boolean playOpenedPotCall = cr.defendBB_Call();
		boolean playOpenedPot3Bet = cr.defendBB_3B();
		boolean playOpenedPotAllIn = cr.defendBB_Allinn();
		boolean playPOF = cr.pushOrFoldWith10BB();
		if(playOpenedPotCall){
			call();
		}else if(playOpenedPot3Bet){
			raiseInBB(4);
		}else if(playOpenedPotAllIn){
			goAllIn();
		}else if(playPOF){
			goAllIn();
		}else{
			checkOrFold();
		}
	}
	private void playBB_vs_2Limpers() {
		boolean playVSLimpers = cr.limpedBothSBAndBTN3XRaiseCall();
		boolean playVSLimpersRaiseF = cr.limpedBothSBAndBTN3XRaiseFold();
		boolean playVSLimpersRaiseC = cr.limpedBothSBAndBTNAllin();
		boolean playPOF = cr.pushOrFoldWith10BB();
		if(playVSLimpers){
			raiseInBB(3);
		}else if(playVSLimpersRaiseF){
			raiseInBB(3);
		}else if(playVSLimpersRaiseC){
			goAllIn();
		}else if(playPOF){
			goAllIn();
		}else{
			limp();
		}
	}
	private void playSB_vs_BTN_biggerThan3BB_Open() {
		playSimplePushFold();
	}
	private void playSB_vs_BTN_3BB_Open() {
		boolean playCallFromSbVsBtnNormalRaise = cr.normalRaiseOpenedPotSBCall();
		boolean play3BetFromSbVsBtnNormalRaise = cr.normalRaiseOpenedPotSB3Bet();
		boolean playAllinFromSbVsBtnNormalRaise = cr.normalRaiseOpenedPotSBAllin();
		if(playCallFromSbVsBtnNormalRaise){
			call();
		}else if(play3BetFromSbVsBtnNormalRaise){
			raiseInBB(3);
		}else if(playAllinFromSbVsBtnNormalRaise){
			goAllIn();
		}else{
			checkOrFold();
		}
		
	}
	private void playSB_vs_BTNlimp() {
		boolean playLimpFromSBVsBtnLimp = cr.sb_limp_vs_BTNlimp();
		boolean playAllinFromSBVsBtnLimp = cr.sb_allIn_vs_BTNliimp();
		boolean playRaiseFromSbVsBtnLimp = cr.sb_Raise_vs_BTNlimp();
		if(playLimpFromSBVsBtnLimp){
			limp();
		}else if(playAllinFromSBVsBtnLimp){
			goAllIn();
		}else if(playRaiseFromSbVsBtnLimp){
			raiseInBB(3);
		}else{
			checkOrFold();
		}
	}

	private void playSimplePushFold(){
		boolean playSimplePushFold = cr.simplePushFold();
		if(playSimplePushFold){
			goAllIn();
		}else{
			checkOrFold();
		}
	}
	private boolean isOpeningHURaiseUpTo3BB(){
		if(gs.getCurrentPotSize() <= gs.getBigBlindValue() * 4){
			return true;
		}else{
			return false;
		}
	}
	private boolean isOpeningRaiseUpTo3BB(){
		if(gs.getCurrentPotSize() <= gs.getBigBlindValue() * 4.5f){
			//System.out.println("Pot size: " + gs.getCurrentPotSize());
			//System.out.println("BB value * 4,5 = " + gs.getBigBlindValue() * 4.5f);
			return true;
		}else{
			return false;
		}
	}
	private boolean areTwoLimpers(){
		if(gs.getBigBlindValue() * 3 == gs.getCurrentPotSize()){
			return true;
		}else{
			return false;
		}
	}
	private boolean isPotOpened(){
		if(gs.getBigBlindValue() * 1.5f == gs.getCurrentPotSize()){
			return false;
		}else{
			return true;
		}
	}
	private boolean isOneLimperInPot(){
		if(gs.getBigBlindValue() * 2.5f == gs.getCurrentPotSize()){
			return true;
		}else{
			return false;
		}
	}

	private boolean isSBLimperInHUPot(){
		if(gs.getBigBlindValue() * 2 == gs.getCurrentPotSize()){
			return true;
		}else{
			return false;
		}
	}
	private double countEnemyBetSize3Way(){
		double bet = 0;
		if(didEnemyPerformedABet()){
			double leftPlayerInitialStack;
			double rightPlayerInitialStack;
			double currentPlayerStack = gs.getCurrentPlayerStack();
			
				leftPlayerInitialStack =  gs.getLeftPlayer().getInitialStackSize();
				System.out.println("lp init stack "+ leftPlayerInitialStack);
				System.out.println("lp curr stack "+ gs.getLeftPlayer().getCurrentStackSize());
				double lPlayerBet = leftPlayerInitialStack - gs.getLeftPlayer().getCurrentStackSize();
				
				rightPlayerInitialStack = gs.getRightPlayer().getInitialStackSize();
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
			bet = gs.getCurrentPotSize() - (gs.getBigBlindValue()*1.5d);
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
	
	private boolean didEnemyPerformedABet() {
		return estimator.isFoldButtonAvailable();
	}
	private boolean isPotSizeEqual(double bigBlinds){
		if(gs.getCurrentPotSize() == gs.getBigBlindValue() * bigBlinds){
			return true;
		}else{
			return false;
		}
	}
	private boolean isPotSizeLessOrEqual(double bigBlinds){
		if(gs.getCurrentPotSize() <= gs.getBigBlindValue() * bigBlinds){
			return true;
		}else{
			return false;
		}
	}
	private boolean isMinRaisedAfterLimpHUPot(){
		if(gs.getCurrentPotSize() <= gs.getBigBlindValue() * 3){
			return true;
		}else{
			return false;
		}
	}
	private boolean isMinReRaisedAfterMinRaise(){
		if(gs.getCurrentPotSize() <= gs.getBigBlindValue() * 5){
			return true;
		}else{
			return false;
		}
	}
	private void raise2_5BB(){
		action.raiseUsingFirstDefaultButton();
		gs.increasePreFlopActionsPerformed();
		gs.setPreFlopAgressor(true);
	}
	private void minRaise(){
		action.minRaise();
		gs.setPreFlopAgressor(true);
		gs.increasePreFlopActionsPerformed();
	}
	private void raiseInBB(int bb){
		action.raiseUsingBetSliderPreFlopInBB(3);
		gs.setPreFlopAgressor(true);
		gs.increasePreFlopActionsPerformed();
	}
	private void goAllIn(){
		action.allIn();
	}
	private void checkOrFold(){
		action.foldIfPossibleCheckIfNot();
	}
	private void call(){
		action.callOrCheck();
	}
	private void limp(){
		action.limp();
		gs.setWasPlayerLimping(true);
		gs.increasePreFlopActionsPerformed();
	}
}
