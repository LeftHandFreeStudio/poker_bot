package spinAndGo;

public class TableCoordinates {
	private int x;
	private int y;
	public TableCoordinates(int tablePositionX, int tablePositionY){
		x = tablePositionX;
		y = tablePositionY;
	}
	public int getTableX() {
		return x;
	}
	public void setTableX(int x) {
		this.x = x;
	}
	public int getTableY() {
		return y;
	}
	public void setTableY(int y) {
		this.y = y;
	}
	
}
