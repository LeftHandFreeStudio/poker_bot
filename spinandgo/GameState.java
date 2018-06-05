package spinAndGo;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class GameState {
	private int no = 0;
	private static int count = 0;
	// current game numbers
	private int numberOfHands = 0;
	
	
	//queue
	private QueueManager qManager;
	private Queue<TableEvent> tasks;
	
	// players 
	private EnemyPlayer leftPlayer;
	private EnemyPlayer rightPlayer;
	
	private Card firstCard,secondCard;
	private Card flop1,flop2,flop3;
	private Card turn;
	private Card river;
	private int street = 0;   // 0pf  1f  2t  3r
	private int position = 0;  // 0 dealer, 1 small blind, 2 big blind
	private int currentPlayerNumber = 0;
	private int initialPlayerNumber = 0;
	private double bigBlindValue = 0;
	private double bigBlinds = 0;
	private int preFlopActionsPerformed = 0;
	private int flopActionsPerformed = 0;
	private int turnActionsPerformed = 0;
	private int riverActionsPerformed = 0;
	private double currentPotSize = 0;
	private double currentPlayerStack = 0;
	private double initialPlayerStackSize = 0;
	private double initialPotSizeOnTheFlop = 0;
	private double initialPotSizeOnTheTurn = 0;
	private double initialPotSizeOnTheRiver = 0;
	private boolean isPlayerAllin = false;
	private boolean isPlayerSitOut = false;
	private boolean preFlopAgressor = false;
	private boolean wasPlayerLimping = false;
	private boolean isLeftPlayerInHU = false;
	
	// flags for counting
	private boolean initialStacksCounted;
	private boolean sizeOfTheBigBlindCounted = false;
	private boolean startingFlopPotCounted = false;
	private boolean startingTurnPotCounted = false;
	private boolean startingRiverPotCounted = false;
	private boolean preparingFlopCheckRaise = false;
	
	private double lastPotRead = 0;
	private int currentNumberOfPlayersHoldingCards;


	private boolean tryToFloat;


	private double initialPlayerFlopStackSize;
	private double initialPlayerTurnStackSize;
	private double initialPlayerRiverStackSize;


	public GameState(){
		count ++;
		no = count;
		leftPlayer = new EnemyPlayer();
		rightPlayer = new EnemyPlayer();
		tasks  = new LinkedList<TableEvent>();
		qManager = new QueueManager(tasks);
	}
	
	public int getNo() {
		return no;
	}

	public void resetCurrentGameValues(){
		
	}
	public void resetValues(){
		//System.out.println("reset values");
		//flags
		initialStacksCounted = false;
		sizeOfTheBigBlindCounted = false;
		startingFlopPotCounted = false;
		startingTurnPotCounted = false;
		startingRiverPotCounted = false;
		tryToFloat = false;
		preparingFlopCheckRaise = false;
		
		isLeftPlayerInHU = false;
		wasPlayerLimping = false;
		isPlayerAllin = false;
		isPlayerSitOut = false;
		setPreFlopAgressor(false);
		
		leftPlayer.resetValues();
		rightPlayer.resetValues();
		
		initialPlayerStackSize = 0;
		initialPlayerFlopStackSize = 0;
		initialPlayerTurnStackSize = 0;
		initialPlayerRiverStackSize = 0;
		initialPotSizeOnTheFlop = 0;
		initialPotSizeOnTheTurn = 0;
		initialPotSizeOnTheRiver = 0;
		currentPlayerStack = 0;
		
		preFlopActionsPerformed = 0;
		flopActionsPerformed = 0;
		turnActionsPerformed = 0;
		riverActionsPerformed = 0;
		
		currentPlayerNumber = 0;
		currentNumberOfPlayersHoldingCards = 0;
		initialPlayerNumber = 0;
		bigBlinds = 0;
		bigBlindValue = 0;
		position = 0;
	}
	
	public boolean isPreparingFlopCheckRaise() {
		return preparingFlopCheckRaise;
	}
	public void setPreparingFlopCheckRaise(boolean preparingFlopCheckRaise) {
		this.preparingFlopCheckRaise = preparingFlopCheckRaise;
	}
	public double getInitialPlayerFlopStackSize() {
		return initialPlayerFlopStackSize;
	}
	public void setInitialPlayerFlopStackSize(double initialPlayerFlopStackSize) {
		this.initialPlayerFlopStackSize = initialPlayerFlopStackSize;
	}
	public double getInitialPlayerTurnStackSize() {
		return initialPlayerTurnStackSize;
	}
	public void setInitialPlayerTurnStackSize(double initialPlayerTurnStackSize) {
		this.initialPlayerTurnStackSize = initialPlayerTurnStackSize;
	}
	public double getInitialPlayerRiverStackSize() {
		return initialPlayerRiverStackSize;
	}
	public void setInitialPlayerRiverStackSize(double initialPlayerRiverStackSize) {
		this.initialPlayerRiverStackSize = initialPlayerRiverStackSize;
	}
	public boolean isTryToFloat() {
		return tryToFloat;
	}
	public void setTryToFloat(boolean tryToFloat) {
		this.tryToFloat = tryToFloat;
	}
	public int getStreet() {
		return street;
	}
	public void setStreet(int street) {
		this.street = street;
	}
	public boolean isInitialStacksCounted() {
		return initialStacksCounted;
	}
	public void setInitialStacksCounted(boolean initialStacksCounted) {
		this.initialStacksCounted = initialStacksCounted;
	}
	public int getCurrentNumberOfPlayersHoldingCards() {
		return currentNumberOfPlayersHoldingCards;
	}
	public void setCurrentNumberOfPlayersHoldingCards(int currentNumberOfPlayersHoldingCards) {
		this.currentNumberOfPlayersHoldingCards = currentNumberOfPlayersHoldingCards;
	}
	public boolean isPlayerAllin() {
		return isPlayerAllin;
	}
	public void setPlayerAllin(boolean isPlayerAllin) {
		this.isPlayerAllin = isPlayerAllin;
	}
	public boolean isPlayerSitOut() {
		return isPlayerSitOut;
	}
	public void setPlayerSitOut(boolean isPlayerSitOut) {
		this.isPlayerSitOut = isPlayerSitOut;
	}
	public double getCurrentPlayerStack() {
		return currentPlayerStack;
	}
	public void setCurrentPlayerStack(int currentPlayerStack) {
		this.currentPlayerStack = currentPlayerStack;
	}
	public QueueManager getTasksManager() {
		return qManager;
	}
	public boolean isWasPlayerLimping() {
		return wasPlayerLimping;
	}
	public void setWasPlayerLimping(boolean wasPlayerLimping) {
		this.wasPlayerLimping = wasPlayerLimping;
	}
	public EnemyPlayer getLeftPlayer() {
		return leftPlayer;
	}
	public EnemyPlayer getRightPlayer() {
		return rightPlayer;
	}
	public boolean isLeftPlayerInHU() {
		return isLeftPlayerInHU;
	}
	public void setIsLeftPlayerInHU(boolean isLeftPlayerInHU) {
		this.isLeftPlayerInHU = isLeftPlayerInHU;
	}
	public int getInitialPlayerNumber() {
		return initialPlayerNumber;
	}
	public void setInitialPlayerNumber(int initialPlayerNumber) {
		this.initialPlayerNumber = initialPlayerNumber;
	}
	public double getPlayersStaringStackSize() {
		return initialPlayerStackSize;
	}
	public void setInitialPlayerStackSize(int playersStaringStackSize) {
		this.initialPlayerStackSize = playersStaringStackSize;
	}
	public double getStartingPotSizeOnTheFlop() {
		return initialPotSizeOnTheFlop;
	}
	public void setStartingPotSizeOnTheFlop(int startingPotSizeOnTheFlop) {
		this.initialPotSizeOnTheFlop = startingPotSizeOnTheFlop;
	}
	public double getStartingPotSizeOnTheTurn() {
		return initialPotSizeOnTheTurn;
	}
	public void setStartingPotSizeOnTheTurn(int startingPotSizeOnTheTurn) {
		this.initialPotSizeOnTheTurn = startingPotSizeOnTheTurn;
	}
	public double getStartingPotSizeOnTheRiver() {
		return initialPotSizeOnTheRiver;
	}
	public void setStartingPotSizeOnTheRiver(int startingPotSizeOnTheRiver) {
		this.initialPotSizeOnTheRiver = startingPotSizeOnTheRiver;
	}
	public boolean isPreFlopAgressor() {
		return preFlopAgressor;
	}
	public void setPreFlopAgressor(boolean preFlopAgressor) {
		this.preFlopAgressor = preFlopAgressor;
	}
	public int getPreFlopActionsPerformed() {
		return preFlopActionsPerformed;
	}

	public void increasePreFlopActionsPerformed() {
		System.out.println("action performed");
		preFlopActionsPerformed++;
	}
	public int getFlopActionsPerformed() {
		return flopActionsPerformed;
	}

	public void increaseFlopActionsPerformed() {
		flopActionsPerformed++;
	}
	public int getTurnActionsPerformed() {
		return turnActionsPerformed;
	}

	public void increaseTurnActionsPerformed() {
		turnActionsPerformed++;
	}
	public int getRiverActionsPerformed() {
		return riverActionsPerformed;
	}

	public void increaseRiverActionsPerformed() {
		riverActionsPerformed++;
	}
	public double getCurrentPotSize() {
		return currentPotSize;
	}
	public void setCurrentPotSize(int potSize) {
		if(potSize >= lastPotRead){
			lastPotRead = potSize;
		}else{
			lastPotRead = 0;
			resetValues();
		}
		this.currentPotSize = potSize;
	}
	
	public double getBigBlindValue() {
		return bigBlindValue;
	}
	public void setBigBlindValue(double bigBlindValue) {
		this.bigBlindValue = bigBlindValue;
	}
	public double getBigBlinds() {
		return bigBlinds;
	}
	public void setBigBlinds(double bigBlinds) {
		this.bigBlinds = bigBlinds;
	}
	public void setCards(Card card1, Card card2){
		firstCard = card1;
		secondCard = card2;
	}
	public void setPosition(int position){
		this.position = position;
		//System.out.println("position    " + position);
	}
	public int getPosition() { // 0 dealer, 1 small blind, 2 big blind
		return position;
	}
	public int getCurrentPlayersNumber() {
		return currentPlayerNumber;
	}
	public void setCurrentPlayersNumber(int playerNumber) {
		this.currentPlayerNumber = playerNumber;
		//System.out.println("players number    " + playerNumber);
	}
	public Card getFirstCard() {
		return firstCard;
	}
	public Card getSecondCard() {
		return secondCard;
	}
	public Card getFlop1() {
		return flop1;
	}
	public void setFlop1(Card flop1) {
		this.flop1 = flop1;
	}
	public Card getFlop2() {
		return flop2;
	}
	public void setFlop2(Card flop2) {
		this.flop2 = flop2;
	}
	public Card getFlop3() {
		return flop3;
	}
	public void setFlop3(Card flop3) {
		this.flop3 = flop3;
	}
	public Card getTurn() {
		return turn;
	}
	public void setTurn(Card turn) {
		this.turn = turn;
	}
	public Card getRiver() {
		return river;
	}
	public void setRiver(Card river) {
		this.river = river;
	}
	public boolean isStartingFlopPotCounted() {
		return startingFlopPotCounted;
	}
	public void setStartingFlopPotCounted(boolean startingFlopPotCounted) {
		this.startingFlopPotCounted = startingFlopPotCounted;
	}
	public boolean isStartingTurnPotCounted() {
		return startingTurnPotCounted;
	}
	public void setStartingTurnPotCounted(boolean startingTurnPotCounted) {
		this.startingTurnPotCounted = startingTurnPotCounted;
	}
	public boolean isStartingRiverPotCounted() {
		return startingRiverPotCounted;
	}
	public void setStartingRiverPotCounted(boolean startingRiverPotCounted) {
		this.startingRiverPotCounted = startingRiverPotCounted;
	}
	public boolean isSizeOfTheBigBlindCounted() {
		return sizeOfTheBigBlindCounted;
	}
	public void setSizeOfTheBigBlindCounted(boolean sizeOfTheBigBlindCounted) {
		this.sizeOfTheBigBlindCounted = sizeOfTheBigBlindCounted;
	}
	@Override
	public String toString(){
		String stateString = "";
		System.out.println("------------------------------------------------------------------");
		System.out.println("Game state :");
		System.out.println("Cards : " + firstCard.toString() + "  " + secondCard.toString());
		System.out.println("Players : " + getCurrentPlayersNumber());
		System.out.println("Pot size : " + getCurrentPotSize());
		System.out.println("BB size : " + getBigBlindValue());
		System.out.println(" pf Actions performed : " + getPreFlopActionsPerformed());
		System.out.println(" f Actions performed : " + getFlopActionsPerformed());
		System.out.println(" t Actions performed : " + getTurnActionsPerformed());
		System.out.println(" r Actions performed : " + getRiverActionsPerformed());
		System.out.println("Player BB's : " + getBigBlinds() + " Limping : " + wasPlayerLimping + " Agg : " + preFlopAgressor);
		System.out.println("Left Player : Allin : " + leftPlayer.isAllin() + " SitOut : " + leftPlayer.isSitout() + " Stack size : " + leftPlayer.getCurrentStackSize());
		System.out.println("Right Player : Allin : " + rightPlayer.isAllin() + " SitOut : " + rightPlayer.isSitout() + " Stack size : " + rightPlayer.getCurrentStackSize());
		System.out.println("------------------------------------------------------------------");
		return stateString;
	}
}
