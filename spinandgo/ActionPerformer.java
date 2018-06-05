package spinAndGo;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Random;

import spinAndGo.GameState;


public class ActionPerformer {	
	private Robot bot;
	private TableCoordinates coordinates;
	private Random random;
	private SituationEstimator est;
	private GameState gs;
	
	public ActionPerformer(Robot robot, TableCoordinates coordinates, SituationEstimator est, GameState gs){
		bot = robot;
		this.coordinates = coordinates;
		random = new Random();
		this.est = est;
		this.gs = gs;
	}
	void clickAtCoordinates(int posX, int posY){
		bot.mouseMove(posX,posY);
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		bot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	public void raiseUsingBetSliderPreFlopInBB(int bbPreFlop){
		if(bbPreFlop <= 2){
			waitWith25PercentDeviation(1000);
			gs.getTasksManager().addTask(new ClickEvent(posX() + 412, posY()  + 331));
		}else{
			bbPreFlop = bbPreFlop-2;
			for(int i = 0; i < bbPreFlop; i++){
				waitWith25PercentDeviation(1000);
				gs.getTasksManager().addTask(new ClickEvent(posX() + 420, posY()  + 316));
				int delay = 100 -  random.nextInt(50);
				gs.getTasksManager().addTask(new WaitEvent(delay));
				}
			gs.getTasksManager().addTask(new ClickEvent(posX() + 412, posY()  + 331));
			waitWith25PercentDeviation(500);
		}
	}
	public void foldIfPossibleCheckIfNot(){
		if(est.isFoldButtonAvailable()){
			System.out.println("fold");
			gs.resetValues();
			int buttonCoordinateX = posX() + 312 - random.nextInt(70);
			int buttonCoordinateY = posY() + 331 + random.nextInt(20);
			waitWith25PercentDeviation(1000);
			gs.getTasksManager().addTask(new ClickEvent(buttonCoordinateX, buttonCoordinateY));
			waitWith25PercentDeviation(500);
		}else{
			System.out.println("check or call");
			callOrCheck();
		}
	}
	public void limp(){
		System.out.println("limp");
		int buttonCoordinateX = posX() + 326 + random.nextInt(70);
		int buttonCoordinateY = posY() + 331 + random.nextInt(20);
		waitWith25PercentDeviation(1000);
		gs.getTasksManager().addTask(new ClickEvent(buttonCoordinateX, buttonCoordinateY));
		waitWith25PercentDeviation(500);
	}
	public void registerToAnotherTournament(){
		System.out.println("reg");
		int buttonCoordinateX = posX() + 190;
		int buttonCoordinateY = posY() + 226;
		waitWith25PercentDeviation(1000);
		gs.getTasksManager().addTask(new RegisterClickEvent(buttonCoordinateX, buttonCoordinateY));
		waitWith25PercentDeviation(500);
	}
	public void exitSitOut(){
		System.out.println("ext so");
		int buttonCoordinateX = posX() + 420;
		int buttonCoordinateY = posY() + 325;
		waitWith25PercentDeviation(1000);
		gs.getTasksManager().addTask(new ClickEvent(buttonCoordinateX, buttonCoordinateY));
		waitWith25PercentDeviation(500);
	}
	public void minRaise(){
		System.out.println("min raise");
		int buttonCoordinateX = posX() + 412 + random.nextInt(70);
		int buttonCoordinateY = posY() + 331 + random.nextInt(20);
		waitWith25PercentDeviation(1000);
		gs.getTasksManager().addTask(new ClickEvent(buttonCoordinateX, buttonCoordinateY));
		waitWith25PercentDeviation(500);
	}
	public void callOrCheck(){ // 0 pre 1 f 2 t 3 r
		if(est.isFoldButtonAvailable()){
			if(est.isCheckButtonAvailable()){   // call
				System.out.println("call");
				int buttonCoordinateX = posX() + 326 + random.nextInt(70);
				int buttonCoordinateY = posY() + 331 + random.nextInt(20);
				waitWith25PercentDeviation(1000);
				gs.getTasksManager().addTask(new ClickEvent(buttonCoordinateX, buttonCoordinateY));
				waitWith25PercentDeviation(500);
				switch (gs.getStreet()) {
				case 0:
					gs.increasePreFlopActionsPerformed();
					break;
				case 1:
					gs.increaseFlopActionsPerformed();
					break;
				case 2:
					gs.increaseTurnActionsPerformed();
					break;
				case 3:
					gs.increaseRiverActionsPerformed();
					break;
				}
			}else{
				System.out.println("call all in");
				int buttonCoordinateX = posX() + 412 + random.nextInt(70);
				int buttonCoordinateY = posY() + 331 + random.nextInt(20);
				waitWith25PercentDeviation(1000);
				gs.getTasksManager().addTask(new ClickEvent(buttonCoordinateX, buttonCoordinateY)); // calling all in

				waitWith25PercentDeviation(500);
				switch (gs.getStreet()) {
				case 0:
					gs.increasePreFlopActionsPerformed();
					break;
				case 1:
					gs.increaseFlopActionsPerformed();
					break;
				case 2:
					gs.increaseTurnActionsPerformed();
					break;
				case 3:
					gs.increaseRiverActionsPerformed();
					break;
				}
			}
		}else{
			System.out.println("check");
			int buttonCoordinateX = posX() + 326 + random.nextInt(70);
			int buttonCoordinateY = posY() + 331 + random.nextInt(20);
			waitWith25PercentDeviation(1000);
			gs.getTasksManager().addTask(new ClickEvent(buttonCoordinateX, buttonCoordinateY));
			waitWith25PercentDeviation(500);
		}
	}
	public void prepareAllin(){
		bot.mouseMove(posX() + 366,posY() + 315);
	    bot.mousePress(InputEvent.BUTTON1_MASK);
		bot.mouseMove(posX() + 490, posY() + 315);
	    bot.mouseRelease(InputEvent.BUTTON1_MASK);
		System.out.println("all in prepared");
	}
	public void allIn(){
		System.out.println("all inn");
		waitWith25PercentDeviation(1000);
		int buttonCoordinateX = posX() + 412;
		int buttonCoordinateY = posY() + 331;
		gs.getTasksManager().addTask(new AllinPrepareEvent(buttonCoordinateX, buttonCoordinateY));
		gs.getTasksManager().addTask(new ClickEvent(buttonCoordinateX, buttonCoordinateY));
		waitWith25PercentDeviation(500);
	}
	public void raiseUsingFirstDefaultButton() {
		waitWith25PercentDeviation(1000);
		int buttonCoordinateX = posX() + 322 + random.nextInt(30);
		int buttonCoordinateY = posY() + 292 + random.nextInt(10);
		gs.getTasksManager().addTask(new ClickEvent(buttonCoordinateX, buttonCoordinateY));
		buttonCoordinateX = posX() + 412 + random.nextInt(70);
		buttonCoordinateY = posY() + 331 + random.nextInt(20);
		waitWith25PercentDeviation(500);
		gs.getTasksManager().addTask(new ClickEvent(buttonCoordinateX, buttonCoordinateY));
		waitWith25PercentDeviation(500);
	}
	public void raiseUsingSecondDefaultButton() {
		waitWith25PercentDeviation(1000);
		int buttonCoordinateX = posX() + 370;
		int buttonCoordinateY = posY() + 300;
		gs.getTasksManager().addTask(new ClickEvent(buttonCoordinateX, buttonCoordinateY));
		waitWith25PercentDeviation(500);
		buttonCoordinateX = posX() + 412 + random.nextInt(70);
		buttonCoordinateY = posY() + 331 + random.nextInt(20);
		gs.getTasksManager().addTask(new ClickEvent(buttonCoordinateX, buttonCoordinateY));
		waitWith25PercentDeviation(500);
	}
	
	public void raiseUsingThirdDefaultButton() {
		waitWith25PercentDeviation(1000);
		int buttonCoordinateX = posX() + 408 + random.nextInt(30);
		int buttonCoordinateY = posY() + 292 + random.nextInt(10);
		gs.getTasksManager().addTask(new ClickEvent(buttonCoordinateX, buttonCoordinateY));
		buttonCoordinateX = posX() + 412 + random.nextInt(70);
		buttonCoordinateY = posY() + 331 + random.nextInt(20);
		waitWith25PercentDeviation(500);
		gs.getTasksManager().addTask(new ClickEvent(buttonCoordinateX, buttonCoordinateY));
		waitWith25PercentDeviation(500);
	}
	public void raiseUsingFourthDefaultButton() {
		waitWith25PercentDeviation(1000);
		int buttonCoordinateX = posX() + 451 + random.nextInt(30);
		int buttonCoordinateY = posY() + 292 + random.nextInt(10);
		gs.getTasksManager().addTask(new ClickEvent(buttonCoordinateX, buttonCoordinateY));
		buttonCoordinateX = posX() + 412 + random.nextInt(70);
		buttonCoordinateY = posY() + 331 + random.nextInt(20);
		waitWith25PercentDeviation(500);
		gs.getTasksManager().addTask(new ClickEvent(buttonCoordinateX, buttonCoordinateY));
		waitWith25PercentDeviation(500);
	}
	public void waitWith25PercentDeviation(int millis){
		int dev = random.nextInt((int) (millis*0.25f));
		boolean flip = random.nextBoolean();
		if(flip){
			gs.getTasksManager().addTask(new WaitEvent(millis + dev));
		}else{
			gs.getTasksManager().addTask(new WaitEvent(millis - dev));
		}
	}
	public void delayProgram(int ms){
		bot.delay(ms);
	}
	
	private int posX(){
		return coordinates.getTableX() + 160;
	}
	private int posY(){
		return coordinates.getTableY();
	}

}
