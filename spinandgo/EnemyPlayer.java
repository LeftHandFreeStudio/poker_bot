package spinAndGo;

public class EnemyPlayer {
	private boolean isInGame = false;;
	private boolean isSitout = false;
	private boolean isAllin = false;
	private boolean hasCards = false;
	private double initialStackSize = 0;
	private double initialFlopStack = 0;
	private double initialTurnStack = 0;
	private double initialRiverStack = 0;
	private double currentStackSize = 0;
	public void resetValues(){
		isInGame = false;
		isSitout = false;
		isAllin = false;
		hasCards = false;
		initialStackSize = 0;
		currentStackSize = 0;
		initialFlopStack = 0;
		initialTurnStack = 0;
		initialRiverStack = 0;
	}
	public boolean isInGame() {
		return isInGame;
	}
	
	public boolean hasCards() {
		return hasCards;
	}
	public void setHasCards(boolean hasCards) {
		this.hasCards = hasCards;
	}
	public double getInitialFlopStack() {
			return initialFlopStack;
	}

	public void setInitialFlopStack(double initialFlopStack) {
		this.initialFlopStack = initialFlopStack;
	}

	public double getInitialTurnStack() {
			return initialTurnStack;
	}

	public void setInitialTurnStack(double initialTurnStack) {
		this.initialTurnStack = initialTurnStack;
	}

	public double getInitialRiverStack() {
			return initialRiverStack;
	}

	public void setInitialRiverStack(double initialRiverStack) {
		this.initialRiverStack = initialRiverStack;
	}

	public void setInGame(boolean isInGame) {
		this.isInGame = isInGame;
	}
	public boolean isSitout() {
		return isSitout;
	}
	public void setSitout(boolean isSitout) {
		this.isSitout = isSitout;
	}
	public double getInitialStackSize() {
		return initialStackSize;
	}
	public void setInitialStackSize(double initialStackSize) {
		this.initialStackSize = initialStackSize;
	}
	public double getCurrentStackSize() {
		if(isAllin){
			return 0;
		}else{
			return currentStackSize;
		}
	}
	public void setCurrentStackSize(double currentStackSize) {
		this.currentStackSize = currentStackSize;
	}
	public boolean isAllin() {
		return isAllin;
	}
	public void setAllin(boolean isAllin) {
		this.isAllin = isAllin;
	}
	
}
