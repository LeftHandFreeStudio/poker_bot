package spinAndGo;

public class ClickEvent extends TableEvent {
	protected int x,y;
	public ClickEvent(int posX, int posY){
		x = posX;
		y = posY;
	}
	@Override
	public void execute(ActionPerformer a){
		a.clickAtCoordinates(x, y);
		finished = true;
	}
	
}
