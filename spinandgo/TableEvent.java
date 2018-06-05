package spinAndGo;

public abstract class TableEvent{
	protected boolean finished = false;
	public void execute(){
		
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	public void execute(ActionPerformer a) {
		// TODO Auto-generated method stub
		
	}
	
}
