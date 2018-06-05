package spinAndGo;

public class WaitEvent extends TableEvent {
	private int duration;
	public WaitEvent(int waitingTimeInMillis){
		this.duration = waitingTimeInMillis;
	}
	public int getDuration() {
		return duration;
	}
	@Override
	public int compareTo(TableEvent o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
