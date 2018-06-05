package spinAndGo;

public class Card {
	private int cardValue;
	private String cardColor = "";
	public Card(){
		
	}
	public Card(int cardValue, String cardColor){
		this.cardValue = cardValue;
		this.cardColor = cardColor;
	}
	public int getCardValue() {
		return cardValue;
	}
	public void setCardValue(int cardValue) {
		this.cardValue = cardValue;
	}
	public String getCardColor() {
		return cardColor;
	}
	public void setCardColor(String cardColor) {
		this.cardColor = cardColor;
	}
	@Override
	public String toString(){
		return "" + cardValue + " " + cardColor;
	}

}
