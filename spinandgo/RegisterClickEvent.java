package spinAndGo;

public class RegisterClickEvent extends ClickEvent {

	public RegisterClickEvent(int posX, int posY) {
		super(posX, posY);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionPerformer a){
		a.clickAtCoordinates(x, y);
		a.delayProgram(1000);
		finished = true;
	}
}
