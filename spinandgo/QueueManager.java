package spinAndGo;

import java.time.Instant;
import java.util.Queue;

public class QueueManager {
	private Queue<TableEvent> q;
	private int duration = 0;
	private	long startingTime = 0;
	private boolean isWaiting = false;
	private WaitEvent we;
	private ActionPerformer a;
	public QueueManager(Queue<TableEvent> queue){
		q = queue;
	}
	public void setActionpreformer(ActionPerformer a){
		this.a = a;
	}
	public boolean upadeTasks(){
		boolean moreEventsPlx = false;
		if(!isWaiting){
			if(!q.isEmpty()){
				if(q.peek() instanceof ClickEvent){
					q.peek().execute(a);
					System.out.println("execute");
					q.poll();
				}else if(q.peek() instanceof WaitEvent){
					we = (WaitEvent) q.peek();
					if(we.isFinished()){
						q.poll();
					}else{
						duration = we.getDuration();
						isWaiting = true;
						startingTime = Instant.now().toEpochMilli();
					}
				}else{
					q.poll();
				}
				
			}else{
				moreEventsPlx = true;
			}
		}else{
			if(nowMinusStartingTime() >= duration){
				isWaiting = false;
				we.setFinished(true);
			}
		}
		return moreEventsPlx;
	}
	public boolean addTask(TableEvent event){
			return q.add(event);
	}
	
	private long nowMinusStartingTime(){
		return Instant.now().toEpochMilli() - startingTime;
	}
}
