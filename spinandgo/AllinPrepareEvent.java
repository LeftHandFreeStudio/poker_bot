package spinAndGo;

public class AllinPrepareEvent extends ClickEvent {

	public AllinPrepareEvent(int posX, int posY) {
		super(posX, posY);
	}

	@Override
	public void execute(ActionPerformer a){
		a.prepareAllin();
		finished = true;
	}
}
