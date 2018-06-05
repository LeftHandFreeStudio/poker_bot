package spinAndGo;

public class CardRangeEstimator {
	private GameState gs;
	int card1Value;
	int card2Value;
	String card1Color;
	String card2Color;
	public CardRangeEstimator(GameState gs){
		this.gs = gs;
	}
	public boolean defendSB_vs_Allin_afterLimping_lessThan10BB(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() <= 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 7);
				findingsInRange += cardsAreInGivenRange(12, 11, 9);
				findingsInRange += cardsAreInGivenRange(11, 10, 10);
				findingsInRange += cardsAreInGivenRange(10, 9, 9);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 11);
				findingsInRange += cardsAreInGivenRange(13, 12, 10);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean defendSB_vs_MinRaise_afterLimping(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(13, 5, 2);
				findingsInRange += cardsAreInGivenRange(12, 6, 2);
				findingsInRange += cardsAreInGivenRange(11, 6, 3);
				findingsInRange += cardsAreInGivenRange(10, 6, 5);
				findingsInRange += cardsAreInGivenRange(9, 6, 5);
				findingsInRange += cardsAreInGivenRange(8, 5, 5);
				findingsInRange += cardsAreInGivenRange(7, 4, 4);
				findingsInRange += cardsAreInGivenRange(6, 4, 3);
				findingsInRange += cardsAreInGivenRange(5, 3, 3);
				findingsInRange += cardsAreInGivenRange(4, 3, 3);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(13, 7, 4);
				findingsInRange += cardsAreInGivenRange(12, 8, 5);
				findingsInRange += cardsAreInGivenRange(11, 8, 6);
				findingsInRange += cardsAreInGivenRange(10, 9, 7);
				findingsInRange += cardsAreInGivenRange(9, 8, 7);
				findingsInRange += cardsAreInGivenRange(8, 7, 5);
				findingsInRange += cardsAreInGivenRange(7, 6, 4);
				findingsInRange += cardsAreInGivenRange(6, 5, 4);
				findingsInRange += cardsAreInGivenRange(5, 4, 4);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(13, 8, 2);
				findingsInRange += cardsAreInGivenRange(12, 8, 2);
				findingsInRange += cardsAreInGivenRange(11, 8, 4);
				findingsInRange += cardsAreInGivenRange(10, 8, 5);
				findingsInRange += cardsAreInGivenRange(9, 8, 5);
				findingsInRange += cardsAreInGivenRange(8, 7, 5);
				findingsInRange += cardsAreInGivenRange(7, 6, 5);
				findingsInRange += cardsAreInGivenRange(6, 5, 3);
				findingsInRange += cardsAreInGivenRange(5, 4, 3);
				findingsInRange += cardsAreInGivenRange(4, 3, 3);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(13, 9, 4);
				findingsInRange += cardsAreInGivenRange(12, 9, 5);
				findingsInRange += cardsAreInGivenRange(11, 9, 6);
				findingsInRange += cardsAreInGivenRange(10, 9, 6);
				findingsInRange += cardsAreInGivenRange(9, 8, 6);
				findingsInRange += cardsAreInGivenRange(8, 7, 5);
				findingsInRange += cardsAreInGivenRange(7, 6, 4);
				findingsInRange += cardsAreInGivenRange(6, 5, 4);
				findingsInRange += cardsAreInGivenRange(5, 4, 4);
			}
		}else{
			if(card1Value == card2Value){
				
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(13, 6, 4);
				findingsInRange += cardsAreInGivenRange(13, 2, 2);
				findingsInRange += cardsAreInGivenRange(12, 8, 2);
				findingsInRange += cardsAreInGivenRange(11, 10, 8);
				findingsInRange += cardsAreInGivenRange(11, 9, 8);
				findingsInRange += cardsAreInGivenRange(11, 5, 2);
				findingsInRange += cardsAreInGivenRange(10, 9, 2);
				findingsInRange += cardsAreInGivenRange(9, 8, 2);
				findingsInRange += cardsAreInGivenRange(8, 7, 4);
				findingsInRange += cardsAreInGivenRange(7, 4, 4);
				findingsInRange += cardsAreInGivenRange(6, 4, 4);
				findingsInRange += cardsAreInGivenRange(5, 3, 3);
				findingsInRange += cardsAreInGivenRange(4, 3, 3);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(13, 8, 4);
				findingsInRange += cardsAreInGivenRange(12, 10, 6);
				findingsInRange += cardsAreInGivenRange(11, 10, 8);
				findingsInRange += cardsAreInGivenRange(10, 9, 8);
				findingsInRange += cardsAreInGivenRange(9, 8, 8);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean defendWith5BB(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() <= 5){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 2);
				findingsInRange += cardsAreInGivenRange(12, 11, 2);
				findingsInRange += cardsAreInGivenRange(11, 10, 2);
				findingsInRange += cardsAreInGivenRange(10, 9, 4);
				findingsInRange += cardsAreInGivenRange(9, 8, 5);
				findingsInRange += cardsAreInGivenRange(8, 7, 6);
				findingsInRange += cardsAreInGivenRange(7, 6, 6);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 2);
				findingsInRange += cardsAreInGivenRange(12, 11, 2);
				findingsInRange += cardsAreInGivenRange(11, 10, 5);
				findingsInRange += cardsAreInGivenRange(10, 9, 7);
				findingsInRange += cardsAreInGivenRange(9, 8, 7);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean pushOrFoldWith5BB(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() <= 5){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 2);
				findingsInRange += cardsAreInGivenRange(12, 11, 2);
				findingsInRange += cardsAreInGivenRange(11, 10, 2);
				findingsInRange += cardsAreInGivenRange(10, 9, 2);
				findingsInRange += cardsAreInGivenRange(9, 8, 8);
				findingsInRange += cardsAreInGivenRange(8, 7, 4);
				findingsInRange += cardsAreInGivenRange(7, 6, 4);
				findingsInRange += cardsAreInGivenRange(6, 5, 3);
				findingsInRange += cardsAreInGivenRange(5, 4, 3);
				findingsInRange += cardsAreInGivenRange(4, 3, 3);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 2);
				findingsInRange += cardsAreInGivenRange(12, 11, 2);
				findingsInRange += cardsAreInGivenRange(11, 10, 3);
				findingsInRange += cardsAreInGivenRange(10, 9, 6);
				findingsInRange += cardsAreInGivenRange(9, 8, 6);
				findingsInRange += cardsAreInGivenRange(8, 7, 6);
				findingsInRange += cardsAreInGivenRange(7, 6, 6);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean pushOrFoldWith10BB(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() <= 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 5);
				findingsInRange += cardsAreInGivenRange(12, 11, 6);
				findingsInRange += cardsAreInGivenRange(11, 10, 8);
				findingsInRange += cardsAreInGivenRange(10, 9, 8);
				findingsInRange += cardsAreInGivenRange(9, 8, 8);
				findingsInRange += cardsAreInGivenRange(8, 7, 7);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 2);
				findingsInRange += cardsAreInGivenRange(12, 11, 10);
				findingsInRange += cardsAreInGivenRange(11, 10, 10);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean defendHU_AsBB_With10BB_vs_SB_Allin(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() <= 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 2);
				findingsInRange += cardsAreInGivenRange(12, 11, 7);
				findingsInRange += cardsAreInGivenRange(11, 10, 8);
				findingsInRange += cardsAreInGivenRange(10, 9, 9);
				findingsInRange += cardsAreInGivenRange(13, 12, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 2);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 5);
				findingsInRange += cardsAreInGivenRange(12, 11, 10);
				findingsInRange += cardsAreInGivenRange(11, 10, 9);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean defendBB_Allinn(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(11, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 7);
				findingsInRange += cardsAreInGivenRange(13, 12, 12);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 12);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 11);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 12);
				findingsInRange += cardsAreInGivenRange(13, 12, 112);
			}
		}else{
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14,13, 2);
				findingsInRange += cardsAreInGivenRange(13,12, 6);
				findingsInRange += cardsAreInGivenRange(12,11, 8);
				findingsInRange += cardsAreInGivenRange(11,10, 8);
				findingsInRange += cardsAreInGivenRange(10,9, 7);
				findingsInRange += cardsAreInGivenRange(9,8, 7);
				findingsInRange += cardsAreInGivenRange(8,7, 6);
				findingsInRange += cardsAreInGivenRange(7,6, 6);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13,2);
				findingsInRange += cardsAreInGivenRange(13, 12,10);
				findingsInRange += cardsAreInGivenRange(12, 11,10);
				findingsInRange += cardsAreInGivenRange(11, 10,10);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean defendBB_3B(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 12);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean defendBB_Call(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(13, 11, 5);
				findingsInRange += cardsAreInGivenRange(12, 11, 6);
				findingsInRange += cardsAreInGivenRange(11, 10, 7);
				findingsInRange += cardsAreInGivenRange(10, 9, 6);
				findingsInRange += cardsAreInGivenRange(9, 8, 6);
				findingsInRange += cardsAreInGivenRange(8, 7, 5);
				findingsInRange += cardsAreInGivenRange(7, 6, 4);
				findingsInRange += cardsAreInGivenRange(6, 5, 4);
				findingsInRange += cardsAreInGivenRange(5, 4, 4);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 6, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 6);
				findingsInRange += cardsAreInGivenRange(12, 11, 7);
				findingsInRange += cardsAreInGivenRange(11, 10, 8);
				findingsInRange += cardsAreInGivenRange(10, 9, 7);
				findingsInRange += cardsAreInGivenRange(9, 8, 7);
				findingsInRange += cardsAreInGivenRange(8, 7, 6);
				findingsInRange += cardsAreInGivenRange(7, 6, 6);
				findingsInRange += cardsAreInGivenRange(6, 5, 5);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(13, 10, 5);
				findingsInRange += cardsAreInGivenRange(12, 11, 6);
				findingsInRange += cardsAreInGivenRange(11, 10, 7);
				findingsInRange += cardsAreInGivenRange(10, 9, 7);
				findingsInRange += cardsAreInGivenRange(9, 8, 7);
				findingsInRange += cardsAreInGivenRange(8, 7, 6);
				findingsInRange += cardsAreInGivenRange(7, 6, 5);
				findingsInRange += cardsAreInGivenRange(6, 5, 5);
				findingsInRange += cardsAreInGivenRange(5, 4, 5);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(13, 12, 7);
				findingsInRange += cardsAreInGivenRange(12, 11, 8);
				findingsInRange += cardsAreInGivenRange(11, 10, 8);
				findingsInRange += cardsAreInGivenRange(10, 9, 8);
				findingsInRange += cardsAreInGivenRange(9, 8, 8);
				findingsInRange += cardsAreInGivenRange(8, 7, 7);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean simplePushFold(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 10);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 11);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 12);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 11);
			}
		}else if(gs.getBigBlinds() <= 10 && gs.getBigBlinds() > 5){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14,13, 2);
				findingsInRange += cardsAreInGivenRange(13,12, 6);
				findingsInRange += cardsAreInGivenRange(12,11, 8);
				findingsInRange += cardsAreInGivenRange(11,10, 8);
				findingsInRange += cardsAreInGivenRange(10,9, 7);
				findingsInRange += cardsAreInGivenRange(9,8, 7);
				findingsInRange += cardsAreInGivenRange(8,7, 6);
				findingsInRange += cardsAreInGivenRange(7,6, 6);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13,2);
				findingsInRange += cardsAreInGivenRange(13, 12,10);
				findingsInRange += cardsAreInGivenRange(12, 11,10);
				findingsInRange += cardsAreInGivenRange(11, 10,10);
			}
		}else{
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 2);
				findingsInRange += cardsAreInGivenRange(12, 11, 2);
				findingsInRange += cardsAreInGivenRange(11, 10, 2);
				findingsInRange += cardsAreInGivenRange(10, 9, 2);
				findingsInRange += cardsAreInGivenRange(9, 8, 8);
				findingsInRange += cardsAreInGivenRange(8, 7, 4);
				findingsInRange += cardsAreInGivenRange(7, 6, 4);
				findingsInRange += cardsAreInGivenRange(6, 5, 3);
				findingsInRange += cardsAreInGivenRange(5, 4, 3);
				findingsInRange += cardsAreInGivenRange(4, 3, 3);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 2);
				findingsInRange += cardsAreInGivenRange(12, 11, 2);
				findingsInRange += cardsAreInGivenRange(11, 10, 3);
				findingsInRange += cardsAreInGivenRange(10, 9, 6);
				findingsInRange += cardsAreInGivenRange(9, 8, 6);
				findingsInRange += cardsAreInGivenRange(8, 7, 6);
				findingsInRange += cardsAreInGivenRange(7, 6, 6);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean openFromSB_Call(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 7, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 5);
				findingsInRange += cardsAreInGivenRange(12, 11, 6);
				findingsInRange += cardsAreInGivenRange(11, 10, 7);
				findingsInRange += cardsAreInGivenRange(10, 9, 6);
				findingsInRange += cardsAreInGivenRange(9, 8, 6);
				findingsInRange += cardsAreInGivenRange(8, 7, 5);
				findingsInRange += cardsAreInGivenRange(7, 6, 5);
				findingsInRange += cardsAreInGivenRange(6, 5, 4);
				findingsInRange += cardsAreInGivenRange(5, 4, 4);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(13, 12, 5);
				findingsInRange += cardsAreInGivenRange(12, 11, 6);
				findingsInRange += cardsAreInGivenRange(11, 10, 8);
				findingsInRange += cardsAreInGivenRange(10, 9, 8);
				findingsInRange += cardsAreInGivenRange(9, 8, 7);
				findingsInRange += cardsAreInGivenRange(8, 7, 7);
				findingsInRange += cardsAreInGivenRange(7, 6, 6);
				findingsInRange += cardsAreInGivenRange(6, 5, 5);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(13, 9, 2);
				findingsInRange += cardsAreInGivenRange(12, 10, 4);
				findingsInRange += cardsAreInGivenRange(11, 10, 6);
				findingsInRange += cardsAreInGivenRange(10, 9, 6);
				findingsInRange += cardsAreInGivenRange(9, 8, 6);
				findingsInRange += cardsAreInGivenRange(8, 7, 5);
				findingsInRange += cardsAreInGivenRange(7, 6, 5);
				findingsInRange += cardsAreInGivenRange(6, 5, 4);
				findingsInRange += cardsAreInGivenRange(5, 4, 4);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(13, 10, 5);
				findingsInRange += cardsAreInGivenRange(12, 10, 6);
				findingsInRange += cardsAreInGivenRange(11, 10, 8);
				findingsInRange += cardsAreInGivenRange(10, 9, 8);
				findingsInRange += cardsAreInGivenRange(9, 8, 8);
				findingsInRange += cardsAreInGivenRange(8, 7, 7);
				findingsInRange += cardsAreInGivenRange(7, 6, 6);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean openFromSB_3bet(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(13, 12);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(13, 12);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean openFromSB_Allin(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 8);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 10);
				findingsInRange += cardsAreInGivenRange(12, 11, 11);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 11);
				findingsInRange += cardsAreInGivenRange(12, 11, 11);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean limped_SB_RaiseFold2_5X(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(13, 3, 2);
				findingsInRange += cardsAreInGivenRange(12, 3, 2);
				findingsInRange += cardsAreInGivenRange(11, 8, 8);
				findingsInRange += cardsAreInGivenRange(10, 7, 6);
				findingsInRange += cardsAreInGivenRange(9, 6, 6);
				findingsInRange += cardsAreInGivenRange(8, 6, 5);
				findingsInRange += cardsAreInGivenRange(7, 5, 4);
				findingsInRange += cardsAreInGivenRange(6, 4, 4);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean limped_SB_RaiseFold3X(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(13, 3, 2);
				findingsInRange += cardsAreInGivenRange(12, 3, 2);
				findingsInRange += cardsAreInGivenRange(11, 8, 8);
				findingsInRange += cardsAreInGivenRange(10, 7, 6);
				findingsInRange += cardsAreInGivenRange(9, 6, 6);
				findingsInRange += cardsAreInGivenRange(8, 6, 5);
				findingsInRange += cardsAreInGivenRange(7, 5, 4);
				findingsInRange += cardsAreInGivenRange(6, 4, 4);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean limped_SB_RaiseCall3X(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 9);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 9);
				findingsInRange += cardsAreInGivenRange(13, 12, 10);
				findingsInRange += cardsAreInGivenRange(12, 11, 10);
				findingsInRange += cardsAreInGivenRange(11, 10, 10);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(13, 12, 10);
				findingsInRange += cardsAreInGivenRange(12, 11, 11);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean limped_SB_RaiseCall2_5X(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 11);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 11);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean bbDefend_vs_limped_BTN_Allin(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(8, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 8, 2);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(10, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 10, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 10);
				findingsInRange += cardsAreInGivenRange(12, 11, 10);
				findingsInRange += cardsAreInGivenRange(11, 10, 10);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 10);
			}
		}else{
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 5);
				findingsInRange += cardsAreInGivenRange(12, 11, 6);
				findingsInRange += cardsAreInGivenRange(11, 10, 8);
				findingsInRange += cardsAreInGivenRange(10, 9, 8);
				findingsInRange += cardsAreInGivenRange(9, 8, 8);
				findingsInRange += cardsAreInGivenRange(8, 7, 7);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 2);
				findingsInRange += cardsAreInGivenRange(12, 11, 10);
				findingsInRange += cardsAreInGivenRange(11, 10, 10);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean limpedBothSBAndBTN3XRaiseFold(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(!card1Color.equals(card2Color)){ // no same color
				findingsInRange += cardsAreInGivenRange(7, 5, 5);
				findingsInRange += cardsAreInGivenRange(6, 4, 4);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(!card1Color.equals(card2Color)){ // no same color
				findingsInRange += cardsAreInGivenRange(7, 5, 5);
				findingsInRange += cardsAreInGivenRange(6, 4, 4);
				findingsInRange += cardsAreInGivenRange(5, 4, 4);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean limpedBothSBAndBTN3XRaiseCall(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 9);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 11);
				findingsInRange += cardsAreInGivenRange(13, 12, 12);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 13);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 10);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 12);
				findingsInRange += cardsAreInGivenRange(13, 12, 12);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean limpedBothSBAndBTNAllin(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(8, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 10, 2);
				findingsInRange += cardsAreInGivenRange(8, 7, 7);
				findingsInRange += cardsAreInGivenRange(7, 6, 6);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 12, 2);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(9, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 11, 2);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean normalRaiseOpenedPotSBAllin(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(11, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 9);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(11, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 5);
			}
		}else{
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 10);
				findingsInRange += cardsAreInGivenRange(12, 11, 11);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 6);
				findingsInRange += cardsAreInGivenRange(13, 12, 11);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean normalRaiseOpenedPotSB3Bet(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 12);
			}else if(card1Color.equals(card2Color)){
				
			}else{ // no same color
				
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 12);
			}else if(card1Color.equals(card2Color)){
				
			}else{ // no same color
				
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean normalRaiseOpenedPotSBCall(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(13, 12, 10);
				findingsInRange += cardsAreInGivenRange(12, 11, 9);
				findingsInRange += cardsAreInGivenRange(11, 10, 9);
				findingsInRange += cardsAreInGivenRange(10, 9, 8);
				findingsInRange += cardsAreInGivenRange(9, 8, 8);
				findingsInRange += cardsAreInGivenRange(8, 7, 7);
				findingsInRange += cardsAreInGivenRange(7, 6, 6);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(13, 12, 10);
				findingsInRange += cardsAreInGivenRange(12, 11, 10);
				findingsInRange += cardsAreInGivenRange(11, 10, 10);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(13, 12, 10);
				findingsInRange += cardsAreInGivenRange(12, 11, 10);
				findingsInRange += cardsAreInGivenRange(11, 10, 10);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(13, 12, 10);
				findingsInRange += cardsAreInGivenRange(12, 11, 10);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean sb_Raise_vs_BTNlimp(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 9);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 12);
				findingsInRange += cardsAreInGivenRange(13, 12, 12);
				findingsInRange += cardsAreInGivenRange(7, 5, 4);
				findingsInRange += cardsAreInGivenRange(6, 5, 4);
			}else{ // no same color
				
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 10);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 12);
				findingsInRange += cardsAreInGivenRange(13, 12, 11);
				findingsInRange += cardsAreInGivenRange(12, 11, 11);
			}else{ // no same color
				
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean sb_allIn_vs_BTNliimp(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(8, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 11, 2);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(9, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 11, 2);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 12);
			}
		}else{
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14,13, 2);
				findingsInRange += cardsAreInGivenRange(13,12, 6);
				findingsInRange += cardsAreInGivenRange(12,11, 8);
				findingsInRange += cardsAreInGivenRange(11,10, 8);
				findingsInRange += cardsAreInGivenRange(10,9, 7);
				findingsInRange += cardsAreInGivenRange(9,8, 7);
				findingsInRange += cardsAreInGivenRange(8,7, 6);
				findingsInRange += cardsAreInGivenRange(7,6, 6);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13,2);
				findingsInRange += cardsAreInGivenRange(13, 12,10);
				findingsInRange += cardsAreInGivenRange(12, 11,10);
				findingsInRange += cardsAreInGivenRange(11, 10,10);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean sb_limp_vs_BTNlimp(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(13, 11, 2);
				findingsInRange += cardsAreInGivenRange(12, 11, 2);
				findingsInRange += cardsAreInGivenRange(11, 10, 2);
				findingsInRange += cardsAreInGivenRange(10, 9, 2);
				findingsInRange += cardsAreInGivenRange(9, 8, 5);
				findingsInRange += cardsAreInGivenRange(8, 7, 4);
				findingsInRange += cardsAreInGivenRange(7, 6, 6);
				findingsInRange += cardsAreInGivenRange(6, 3, 3);
				findingsInRange += cardsAreInGivenRange(5, 4, 2);
				findingsInRange += cardsAreInGivenRange(4, 3, 2);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(13, 11, 2);
				findingsInRange += cardsAreInGivenRange(12, 11, 4);
				findingsInRange += cardsAreInGivenRange(11, 10, 6);
				findingsInRange += cardsAreInGivenRange(10, 9, 6);
				findingsInRange += cardsAreInGivenRange(9, 8, 7);
				findingsInRange += cardsAreInGivenRange(8, 7, 6);
				findingsInRange += cardsAreInGivenRange(7, 6, 5);
				findingsInRange += cardsAreInGivenRange(6, 5, 5);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(13, 11, 2);
				findingsInRange += cardsAreInGivenRange(12, 11, 2);
				findingsInRange += cardsAreInGivenRange(11, 10, 2);
				findingsInRange += cardsAreInGivenRange(10, 9, 4);
				findingsInRange += cardsAreInGivenRange(9, 8, 5);
				findingsInRange += cardsAreInGivenRange(8, 7, 5);
				findingsInRange += cardsAreInGivenRange(7, 6, 4);
				findingsInRange += cardsAreInGivenRange(6, 5, 4);
				findingsInRange += cardsAreInGivenRange(5, 4, 4);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(13, 11, 2);
				findingsInRange += cardsAreInGivenRange(12, 11, 4);
				findingsInRange += cardsAreInGivenRange(11, 10, 7);
				findingsInRange += cardsAreInGivenRange(10, 9, 8);
				findingsInRange += cardsAreInGivenRange(9, 8, 8);
				findingsInRange += cardsAreInGivenRange(8, 7, 7);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean unopenedPot_AllInFromSB(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(11, 2);
			}else if(card1Color.equals(card2Color)){
				
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 8);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(11, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 7, 2);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 10);
				findingsInRange += cardsAreInGivenRange(12, 11, 10);
				findingsInRange += cardsAreInGivenRange(11, 10, 10);
			}
		}else{
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(4, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(7, 6, 4);
				findingsInRange += cardsAreInGivenRange(6, 5, 5);
				findingsInRange += cardsAreInGivenRange(5, 4, 4);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 10, 2);
				findingsInRange += cardsAreInGivenRange(13, 9, 9);
				findingsInRange += cardsAreInGivenRange(12, 11, 11);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean unopenedHUPotSBLimp(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(13, 5, 2);
				findingsInRange += cardsAreInGivenRange(12, 6, 2);
				findingsInRange += cardsAreInGivenRange(11, 6, 3);
				findingsInRange += cardsAreInGivenRange(10, 6, 5);
				findingsInRange += cardsAreInGivenRange(9, 6, 5);
				findingsInRange += cardsAreInGivenRange(8, 5, 5);
				findingsInRange += cardsAreInGivenRange(7, 4, 4);
				findingsInRange += cardsAreInGivenRange(6, 4, 3);
				findingsInRange += cardsAreInGivenRange(5, 3, 3);
				findingsInRange += cardsAreInGivenRange(4, 3, 3);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(13, 7, 4);
				findingsInRange += cardsAreInGivenRange(12, 8, 5);
				findingsInRange += cardsAreInGivenRange(11, 8, 6);
				findingsInRange += cardsAreInGivenRange(10, 9, 7);
				findingsInRange += cardsAreInGivenRange(9, 8, 7);
				findingsInRange += cardsAreInGivenRange(8, 7, 5);
				findingsInRange += cardsAreInGivenRange(7, 6, 4);
				findingsInRange += cardsAreInGivenRange(6, 5, 4);
				findingsInRange += cardsAreInGivenRange(5, 4, 4);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(13, 8, 2);
				findingsInRange += cardsAreInGivenRange(12, 8, 2);
				findingsInRange += cardsAreInGivenRange(11, 8, 4);
				findingsInRange += cardsAreInGivenRange(10, 8, 5);
				findingsInRange += cardsAreInGivenRange(9, 8, 5);
				findingsInRange += cardsAreInGivenRange(8, 7, 5);
				findingsInRange += cardsAreInGivenRange(7, 6, 5);
				findingsInRange += cardsAreInGivenRange(6, 5, 3);
				findingsInRange += cardsAreInGivenRange(5, 4, 3);
				findingsInRange += cardsAreInGivenRange(4, 3, 3);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(13, 9, 4);
				findingsInRange += cardsAreInGivenRange(12, 9, 5);
				findingsInRange += cardsAreInGivenRange(11, 9, 6);
				findingsInRange += cardsAreInGivenRange(10, 9, 6);
				findingsInRange += cardsAreInGivenRange(9, 8, 6);
				findingsInRange += cardsAreInGivenRange(8, 7, 5);
				findingsInRange += cardsAreInGivenRange(7, 6, 4);
				findingsInRange += cardsAreInGivenRange(6, 5, 4);
				findingsInRange += cardsAreInGivenRange(5, 4, 4);
			}
		}else{
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 5);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 3);
				findingsInRange += cardsAreInGivenRange(13, 12, 4);
				findingsInRange += cardsAreInGivenRange(13, 2, 2);
				findingsInRange += cardsAreInGivenRange(12, 11, 2);
				findingsInRange += cardsAreInGivenRange(11, 10, 8);
				findingsInRange += cardsAreInGivenRange(11, 5, 2);
				findingsInRange += cardsAreInGivenRange(10, 9, 2);
				findingsInRange += cardsAreInGivenRange(9, 8, 2);
				findingsInRange += cardsAreInGivenRange(8, 7, 2);
				findingsInRange += cardsAreInGivenRange(7, 4, 2);
				findingsInRange += cardsAreInGivenRange(6, 4, 2);
				findingsInRange += cardsAreInGivenRange(5, 3, 2);
				findingsInRange += cardsAreInGivenRange(4, 3, 2);
				findingsInRange += cardsAreInGivenRange(3, 2, 2);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 11);
				findingsInRange += cardsAreInGivenRange(13, 12, 10);
				findingsInRange += cardsAreInGivenRange(13, 8, 2);
				findingsInRange += cardsAreInGivenRange(12, 10, 2);
				findingsInRange += cardsAreInGivenRange(11, 10, 2);
				findingsInRange += cardsAreInGivenRange(10, 9, 2);
				findingsInRange += cardsAreInGivenRange(9, 8, 2);
				findingsInRange += cardsAreInGivenRange(8, 7, 4);
				findingsInRange += cardsAreInGivenRange(7, 6, 3);
				findingsInRange += cardsAreInGivenRange(6, 5, 3);
				findingsInRange += cardsAreInGivenRange(5, 4, 3);
				findingsInRange += cardsAreInGivenRange(4, 3, 3);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean unopenedPot_LimpFromSB(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(13, 5, 2);
				findingsInRange += cardsAreInGivenRange(12, 6, 2);
				findingsInRange += cardsAreInGivenRange(11, 6, 3);
				findingsInRange += cardsAreInGivenRange(10, 6, 5);
				findingsInRange += cardsAreInGivenRange(9, 6, 5);
				findingsInRange += cardsAreInGivenRange(8, 5, 5);
				findingsInRange += cardsAreInGivenRange(7, 4, 4);
				findingsInRange += cardsAreInGivenRange(6, 4, 3);
				findingsInRange += cardsAreInGivenRange(5, 3, 3);
				findingsInRange += cardsAreInGivenRange(4, 3, 3);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(13, 7, 4);
				findingsInRange += cardsAreInGivenRange(12, 8, 5);
				findingsInRange += cardsAreInGivenRange(11, 8, 6);
				findingsInRange += cardsAreInGivenRange(10, 9, 7);
				findingsInRange += cardsAreInGivenRange(9, 8, 7);
				findingsInRange += cardsAreInGivenRange(8, 7, 5);
				findingsInRange += cardsAreInGivenRange(7, 6, 4);
				findingsInRange += cardsAreInGivenRange(6, 5, 4);
				findingsInRange += cardsAreInGivenRange(5, 4, 4);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(13, 8, 2);
				findingsInRange += cardsAreInGivenRange(12, 8, 2);
				findingsInRange += cardsAreInGivenRange(11, 8, 4);
				findingsInRange += cardsAreInGivenRange(10, 8, 5);
				findingsInRange += cardsAreInGivenRange(9, 8, 5);
				findingsInRange += cardsAreInGivenRange(8, 7, 5);
				findingsInRange += cardsAreInGivenRange(7, 6, 5);
				findingsInRange += cardsAreInGivenRange(6, 5, 3);
				findingsInRange += cardsAreInGivenRange(5, 4, 3);
				findingsInRange += cardsAreInGivenRange(4, 3, 3);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(13, 9, 4);
				findingsInRange += cardsAreInGivenRange(12, 9, 5);
				findingsInRange += cardsAreInGivenRange(11, 9, 6);
				findingsInRange += cardsAreInGivenRange(10, 9, 6);
				findingsInRange += cardsAreInGivenRange(9, 8, 6);
				findingsInRange += cardsAreInGivenRange(8, 7, 5);
				findingsInRange += cardsAreInGivenRange(7, 6, 4);
				findingsInRange += cardsAreInGivenRange(6, 5, 4);
				findingsInRange += cardsAreInGivenRange(5, 4, 4);
			}
		}else{
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 5);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 3);
				findingsInRange += cardsAreInGivenRange(13, 12, 4);
				findingsInRange += cardsAreInGivenRange(13, 2, 2);
				findingsInRange += cardsAreInGivenRange(12, 11, 2);
				findingsInRange += cardsAreInGivenRange(11, 10, 8);
				findingsInRange += cardsAreInGivenRange(11, 5, 2);
				findingsInRange += cardsAreInGivenRange(10, 9, 2);
				findingsInRange += cardsAreInGivenRange(9, 8, 2);
				findingsInRange += cardsAreInGivenRange(8, 7, 2);
				findingsInRange += cardsAreInGivenRange(7, 4, 2);
				findingsInRange += cardsAreInGivenRange(6, 4, 2);
				findingsInRange += cardsAreInGivenRange(5, 3, 2);
				findingsInRange += cardsAreInGivenRange(4, 3, 2);
				findingsInRange += cardsAreInGivenRange(3, 2, 2);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 11);
				findingsInRange += cardsAreInGivenRange(13, 12, 10);
				findingsInRange += cardsAreInGivenRange(13, 8, 2);
				findingsInRange += cardsAreInGivenRange(12, 10, 2);
				findingsInRange += cardsAreInGivenRange(11, 10, 2);
				findingsInRange += cardsAreInGivenRange(10, 9, 2);
				findingsInRange += cardsAreInGivenRange(9, 8, 2);
				findingsInRange += cardsAreInGivenRange(8, 7, 4);
				findingsInRange += cardsAreInGivenRange(7, 6, 3);
				findingsInRange += cardsAreInGivenRange(6, 5, 3);
				findingsInRange += cardsAreInGivenRange(5, 4, 3);
				findingsInRange += cardsAreInGivenRange(4, 3, 3);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}

	public boolean unopenedPot_MinRaiseFromSB(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 12);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 6);
				findingsInRange += cardsAreInGivenRange(12, 11, 7);
				findingsInRange += cardsAreInGivenRange(11, 10, 7);
				findingsInRange += cardsAreInGivenRange(10, 9, 7);
				findingsInRange += cardsAreInGivenRange(9, 8, 7);
				findingsInRange += cardsAreInGivenRange(8, 7, 6);
				findingsInRange += cardsAreInGivenRange(7, 6, 5);
				findingsInRange += cardsAreInGivenRange(6, 5, 5);
				findingsInRange += cardsAreInGivenRange(5, 4, 4);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14,7, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 8);
				findingsInRange += cardsAreInGivenRange(12, 11, 9);
				findingsInRange += cardsAreInGivenRange(11, 10, 9);
				findingsInRange += cardsAreInGivenRange(10, 9, 8);
				findingsInRange += cardsAreInGivenRange(9, 8, 8);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 12);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 8);
				findingsInRange += cardsAreInGivenRange(13, 12, 9);
				findingsInRange += cardsAreInGivenRange(12, 11, 9);
				findingsInRange += cardsAreInGivenRange(11, 10, 9);
				findingsInRange += cardsAreInGivenRange(10, 9, 9);
			}else{ // no same color
				
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean unopenedPot_MinRaiseFromBTN(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 11);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 5);
				findingsInRange += cardsAreInGivenRange(12, 11, 6);
				findingsInRange += cardsAreInGivenRange(11, 10, 6);
				findingsInRange += cardsAreInGivenRange(10, 9, 6);
				findingsInRange += cardsAreInGivenRange(9, 8, 6);
				findingsInRange += cardsAreInGivenRange(8, 7, 6);
				findingsInRange += cardsAreInGivenRange(7, 6, 5);
				findingsInRange += cardsAreInGivenRange(6, 5, 4);
				findingsInRange += cardsAreInGivenRange(5, 4, 4);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 9, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 7);
				findingsInRange += cardsAreInGivenRange(12, 11, 8);
				findingsInRange += cardsAreInGivenRange(11, 10, 8);
				findingsInRange += cardsAreInGivenRange(10, 9, 8);
				findingsInRange += cardsAreInGivenRange(9, 8, 8);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 10);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 9);
				findingsInRange += cardsAreInGivenRange(13, 12, 5);
				findingsInRange += cardsAreInGivenRange(12, 11, 6);
				findingsInRange += cardsAreInGivenRange(11, 10, 6);
				findingsInRange += cardsAreInGivenRange(10, 9, 6);
				findingsInRange += cardsAreInGivenRange(9, 8, 6);
				findingsInRange += cardsAreInGivenRange(8, 7, 6);
				findingsInRange += cardsAreInGivenRange(7, 6, 5);
				findingsInRange += cardsAreInGivenRange(6, 5, 4);
				findingsInRange += cardsAreInGivenRange(5, 4, 4);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 6, 2);
				findingsInRange += cardsAreInGivenRange(13, 12, 8);
				findingsInRange += cardsAreInGivenRange(12, 11, 9);
				findingsInRange += cardsAreInGivenRange(11, 10, 9);
				findingsInRange += cardsAreInGivenRange(10, 9, 9);
				findingsInRange += cardsAreInGivenRange(9, 8, 8);
			}
		}

		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean unopenedPot_AllinFromBTN(){
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(10, 2);
			}else if(card1Color.equals(card2Color)){
				
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 10);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(9, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 8, 2);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 7);
			}
		}else{
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14,13, 2);
				findingsInRange += cardsAreInGivenRange(13,12, 6);
				findingsInRange += cardsAreInGivenRange(12,11, 8);
				findingsInRange += cardsAreInGivenRange(11,10, 8);
				findingsInRange += cardsAreInGivenRange(10,9, 7);
				findingsInRange += cardsAreInGivenRange(9,8, 7);
				findingsInRange += cardsAreInGivenRange(8,7, 6);
				findingsInRange += cardsAreInGivenRange(7,6, 6);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13,2);
				findingsInRange += cardsAreInGivenRange(13, 12,10);
				findingsInRange += cardsAreInGivenRange(12, 11,10);
				findingsInRange += cardsAreInGivenRange(11, 10,10);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	private int arePairedCardsInRange(int upperRange, int bottomRange){
		int found = 0;
		if(card1Value <= upperRange && card1Value >= bottomRange){
			found++;
		}
		return found;
	}
	private int cardsAreInGivenRange(int firstCardValue, int secondCardUpperRange, int secondCardBottomRange){
		int found = 0;
		if((card1Value == firstCardValue && (card2Value <= secondCardUpperRange && card2Value >=secondCardBottomRange)) ||
				(card2Value == firstCardValue && (card1Value <= secondCardUpperRange && card1Value >=secondCardBottomRange))){
			found++;
		}
		return found;
	}
	private void assignCardColorsAndValues(){
		card1Value = gs.getFirstCard().getCardValue();
		card2Value = gs.getSecondCard().getCardValue();
		card1Color = gs.getFirstCard().getCardColor();
		card2Color = gs.getSecondCard().getCardColor();
	}
	public boolean nashRangePush() {
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
		if(gs.getBigBlinds() >= 17){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 5);
			}
		}else if(gs.getBigBlinds() < 17 && gs.getBigBlinds() > 10){
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14, 13, 2);
			}
		}else{
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14,13, 2);
				findingsInRange += cardsAreInGivenRange(13,12, 2);
				findingsInRange += cardsAreInGivenRange(12,11, 2);
				findingsInRange += cardsAreInGivenRange(11,10, 3);
				findingsInRange += cardsAreInGivenRange(10,9, 4);
				findingsInRange += cardsAreInGivenRange(9,8, 5);
				findingsInRange += cardsAreInGivenRange(8,7, 4);
				findingsInRange += cardsAreInGivenRange(7,6, 4);
				findingsInRange += cardsAreInGivenRange(6,5, 4);
				findingsInRange += cardsAreInGivenRange(5,4, 4);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14,13, 2);
				findingsInRange += cardsAreInGivenRange(13,12, 2);
				findingsInRange += cardsAreInGivenRange(12,11, 7);
				findingsInRange += cardsAreInGivenRange(11,10, 8);
				findingsInRange += cardsAreInGivenRange(10,9, 8);
				findingsInRange += cardsAreInGivenRange(9,8, 7);
				findingsInRange += cardsAreInGivenRange(8,7, 7);
				findingsInRange += cardsAreInGivenRange(7,6, 6);
			}
		}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}

	public boolean nashRangePush8BB() {
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14,13, 2);
				findingsInRange += cardsAreInGivenRange(13,12, 2);
				findingsInRange += cardsAreInGivenRange(12,11, 2);
				findingsInRange += cardsAreInGivenRange(11,10, 2);
				findingsInRange += cardsAreInGivenRange(10,9, 4);
				findingsInRange += cardsAreInGivenRange(9,8, 5);
				findingsInRange += cardsAreInGivenRange(8,7, 4);
				findingsInRange += cardsAreInGivenRange(7,6, 4);
				findingsInRange += cardsAreInGivenRange(6,5, 4);
				findingsInRange += cardsAreInGivenRange(5,4, 4);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14,13, 2);
				findingsInRange += cardsAreInGivenRange(13,12, 2);
				findingsInRange += cardsAreInGivenRange(12,11, 5);
				findingsInRange += cardsAreInGivenRange(11,10, 7);
				findingsInRange += cardsAreInGivenRange(10,9, 7);
				findingsInRange += cardsAreInGivenRange(9,8, 7);
				findingsInRange += cardsAreInGivenRange(8,7, 7);
				findingsInRange += cardsAreInGivenRange(7,6, 6);
			}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
	public boolean nashRangeDef11BB() {
		boolean cardsArePlayable = false;
		int findingsInRange = 0;
		assignCardColorsAndValues();
			if(card1Value == card2Value){
				findingsInRange += arePairedCardsInRange(14, 2);
			}else if(card1Color.equals(card2Color)){
				findingsInRange += cardsAreInGivenRange(14,13, 2);
				findingsInRange += cardsAreInGivenRange(13,12, 4);
				findingsInRange += cardsAreInGivenRange(12,11, 8);
				findingsInRange += cardsAreInGivenRange(11,10, 9);
				findingsInRange += cardsAreInGivenRange(10,9, 9);
			}else{ // no same color
				findingsInRange += cardsAreInGivenRange(14,13, 2);
				findingsInRange += cardsAreInGivenRange(13,12, 7);
				findingsInRange += cardsAreInGivenRange(12,11, 10);
				findingsInRange += cardsAreInGivenRange(11,10, 10);
			}
		if(findingsInRange >0){
			cardsArePlayable = true;
		}
		return cardsArePlayable;
	}
}
