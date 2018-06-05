package spinAndGo;

import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.time.Instant;


public class GameManager {
	private GameState gs;
	private SituationEstimator situationEstimator;
	private ActionPerformer actionPerformer;
	private TableReader tableReader;
	private PreFlopPlayManager preFlopPlayManager;
	private FlopPlayManager flopPlayManager;
	private TurnPlayManager turnPlayManager;
	private RiverPlayManager riverPlayManager;
	private TableCoordinates coordinates;

	private long timer = 0;
	private boolean stopGatheringEvents = false;
	
	public GameManager(Robot robot, TableCoordinates coordinates) throws Exception{
		gs = new GameState();
		this.coordinates = coordinates;
		situationEstimator = new SituationEstimator(coordinates);
		actionPerformer = new ActionPerformer(robot,coordinates,situationEstimator,gs);
		gs.getTasksManager().setActionpreformer(actionPerformer);
		tableReader = new TableReader(gs,coordinates);
		preFlopPlayManager = new PreFlopPlayManager(gs,actionPerformer,situationEstimator);
		flopPlayManager = new FlopPlayManager(gs,actionPerformer,situationEstimator);
		turnPlayManager = new TurnPlayManager(gs,actionPerformer, situationEstimator);
		riverPlayManager = new RiverPlayManager(gs,actionPerformer,situationEstimator);
	}
	public void playAGame(BufferedImage ss){ // ss for screenshot, game
			System.out.println("processing loop" + "  " + gs.getNo());
			//System.out.println(Instant.now().toEpochMilli());
			passScreenShotToOTherObjects(ss);
			if(situationEstimator.isPlayerInGame() && !tableReader.isSitout()){// cards are dealt
				System.out.println("player in game and no sitout" + "  " + gs.getNo());
				System.out.println("choosing action" + "  " + gs.getNo());
				readPotAndCheckForValuesReseting();
				System.out.println("read pot" + "  " + gs.getNo());
				updateCurrentAndInitialGameInfo();
				System.out.println("info updated" + "  " + gs.getNo());
				checkForRequiredAcionAndAct();
				System.out.println("actio checked" + "  " + gs.getNo());
			}else{// dont have cards
				System.out.println("without cards" + "  " + gs.getNo());
				resetGameState();
			}
			System.out.println("check next t" + "  " + gs.getNo());
			registerToAnotherGameWhenCurrentIsOver();
			System.out.println("checked next t" + "  " + gs.getNo());
			System.out.println("ext so" + "  " + gs.getNo());
			exitSitout();
			System.out.println("exited" + "  " + gs.getNo());
			System.out.println("up tasks" + "  " + gs.getNo());

			if(gs.getTasksManager().upadeTasks()){
				System.out.println("tasks updated, ready for new" + "  " + gs.getNo());
				stopGatheringEvents = false;
			}
			System.out.println("updated" + "  " + gs.getNo());
	}
	private void updateCurrentAndInitialGameInfo() {
		savePlayersInitialStacks();
		countInitialPlayerNumberAndBBValue();
		readInitialStreetValues();
		updateCurretPlayersAndTableInformations();
	}
	private void readInitialStreetValues() {
		readFlopInitialInformation();
		readTurnInitialInformation();
		readRiverInitialInformation();
	}
	private void savePlayersInitialStacks() {
		if(!gs.isInitialStacksCounted()){
			gs.setInitialStacksCounted(true);
			savePlayersInitialInformation();
		}
	}
	private void readPotAndCheckForValuesReseting() {
		gs.setCurrentPotSize(tableReader.readPotValue());
	}
	private void printCurrentGameStateEvert1Sec() {
		if(Instant.now().toEpochMilli() - timer > 1000){
			timer = Instant.now().toEpochMilli();
			gs.toString();
			System.out.println("\\/ \\/ DATA DATA DATA DATA DATA DATA DATA DATA \\/ \\/");
		}
	}
	private void updateCurretPlayersAndTableInformations() {
		//player
		if(!tableReader.isPlayerAllin() && !tableReader.isSitout()){
			gs.setCurrentPlayerStack(tableReader.readStackSize());
		}
		//left player 
			gs.getLeftPlayer().setInGame(tableReader.isPlayerOnTheLeftInGame());
			if(gs.getLeftPlayer().isInGame()){
				gs.getLeftPlayer().setSitout(tableReader.isLeftPlayerOnSitout());
				if(situationEstimator.isPlayerOnTheLeftHoldingCards() && !gs.getLeftPlayer().isSitout()){
					gs.getLeftPlayer().setAllin(tableReader.isPlayerOnTheLeftAllin());
					if(!gs.getLeftPlayer().isAllin()){
						gs.getLeftPlayer().setCurrentStackSize(tableReader.readPlayerOnTheLeftStackSize());
					}
				}
			}
		//right player 
		gs.getRightPlayer().setInGame(tableReader.isPlayerOnTheRightInGame());
		if(gs.getRightPlayer().isInGame()){
			gs.getRightPlayer().setSitout(tableReader.isRightPlayerOnSitout());
			if(situationEstimator.isPlayerOnTheRightHoldingCards() && !gs.getRightPlayer().isSitout()){
				gs.getRightPlayer().setAllin(tableReader.isPlayerOnTheRightAllin());
				if(!gs.getRightPlayer().isAllin()){
					gs.getRightPlayer().setCurrentStackSize(tableReader.readPlayerOnTheRightStackSize());
				}
			}
		}
		
		if(gs.getLeftPlayer().isInGame() && gs.getRightPlayer().isInGame()){
			gs.setCurrentPlayersNumber(2);
		}else if(gs.getLeftPlayer().isInGame() || gs.getRightPlayer().isInGame()){
			gs.setCurrentPlayersNumber(1);
		}else{
			gs.setCurrentPlayersNumber(0);
		}

		gs.setCurrentNumberOfPlayersHoldingCards(0);
		if(gs.getLeftPlayer().isInGame()){
			if(situationEstimator.isPlayerOnTheLeftHoldingCards()){
				gs.getLeftPlayer().setHasCards(true);
				gs.setCurrentNumberOfPlayersHoldingCards(gs.getCurrentNumberOfPlayersHoldingCards() +1);
			}else{
				gs.getLeftPlayer().setHasCards(false);
			}
		}
		if(gs.getRightPlayer().isInGame()){
			if(situationEstimator.isPlayerOnTheRightHoldingCards()){
				gs.getRightPlayer().setHasCards(true);
				gs.setCurrentNumberOfPlayersHoldingCards(gs.getCurrentNumberOfPlayersHoldingCards() +1);
			}else{
				gs.getRightPlayer().setHasCards(false);
			}
		}
	}
	private void passScreenShotToOTherObjects(BufferedImage ss) {
	situationEstimator.setScreenShot(ss); // pass screenshot to situation estimator
	tableReader.setScreenShot(ss); // pass screenshot to table reader
	}
	private void checkForRequiredAcionAndAct() {
		if(situationEstimator.isActionRequired() && !stopGatheringEvents){// time to play
			playAccordingToSituation();
			stopGatheringEvents = true;
		}
	}
	private void countInitialPlayerNumberAndBBValue() {
		if(!gs.isSizeOfTheBigBlindCounted()){
			System.out.println("count initial bb");
			countBBSizeAndPlayerBBs();
			gs.setSizeOfTheBigBlindCounted(true);
		}
	}
	private void playAccordingToSituation() {
		readPlayerNumber_Positions_Cards();
		System.out.println("play");
		if(situationEstimator.isRiverAvailable()){
			gs.setStreet(3);
			riverPlayManager.play();
		}else if(situationEstimator.isTurnAvailable()){
			gs.setStreet(2);
			turnPlayManager.play();
		}else if(situationEstimator.isFlopAvailable()){
			gs.setStreet(1);
			flopPlayManager.play();
		}else{
			gs.setStreet(0);
			preFlopPlayManager.play();
		}
	}
	private void readRiverInitialInformation() {
		if(situationEstimator.isRiverAvailable()){
			tableReader.readRiverCard();
			if(!gs.isStartingRiverPotCounted()){
				saveOtherPlayersInitialRiverInfo();
				gs.setStartingPotSizeOnTheRiver(tableReader.readPotValue());
				gs.setStartingRiverPotCounted(true);
			}
		}
	}
	private void saveOtherPlayersInitialRiverInfo() {
		if(!tableReader.isSitout()){
			gs.setInitialPlayerRiverStackSize(tableReader.readStackSize());
		}
		//left player 
		gs.getLeftPlayer().setInGame(tableReader.isPlayerOnTheLeftInGame());
		if(gs.getLeftPlayer().isInGame()){
			gs.getLeftPlayer().setSitout(tableReader.isLeftPlayerOnSitout());
			if(situationEstimator.isPlayerOnTheLeftHoldingCards() && !gs.getLeftPlayer().isSitout()){
				gs.getLeftPlayer().setAllin(tableReader.isPlayerOnTheLeftAllin());
				if(!gs.getLeftPlayer().isAllin()){
					gs.getLeftPlayer().setInitialRiverStack(tableReader.readPlayerOnTheLeftStackSize());
				}
			}
		}
		//right player 
		gs.getRightPlayer().setInGame(tableReader.isPlayerOnTheRightInGame());
		if(gs.getRightPlayer().isInGame()){
			gs.getRightPlayer().setSitout(tableReader.isRightPlayerOnSitout());
			if(situationEstimator.isPlayerOnTheRightHoldingCards() && !gs.getRightPlayer().isSitout()){
				gs.getRightPlayer().setAllin(tableReader.isPlayerOnTheRightAllin());
				if(!gs.getRightPlayer().isAllin()){
					gs.getRightPlayer().setInitialRiverStack(tableReader.readPlayerOnTheRightStackSize());
				}
			}
		}
	}
	private void readTurnInitialInformation() {
		if(situationEstimator.isTurnAvailable()){
			tableReader.readTurnCard();
			if(!gs.isStartingTurnPotCounted()){
				saveOtherPlayersInitialTurnInfo();
				gs.setStartingPotSizeOnTheTurn(tableReader.readPotValue());
				gs.setStartingTurnPotCounted(true);
			}
		}	
	}
	private void saveOtherPlayersInitialTurnInfo() {
		if(!tableReader.isSitout()){
			gs.setInitialPlayerTurnStackSize(tableReader.readStackSize());
		}
		//left player 
				gs.getLeftPlayer().setInGame(tableReader.isPlayerOnTheLeftInGame());
				if(gs.getLeftPlayer().isInGame()){
					gs.getLeftPlayer().setSitout(tableReader.isLeftPlayerOnSitout());
					if(situationEstimator.isPlayerOnTheLeftHoldingCards() && !gs.getLeftPlayer().isSitout()){
						gs.getLeftPlayer().setAllin(tableReader.isPlayerOnTheLeftAllin());
						if(!gs.getLeftPlayer().isAllin()){
							gs.getLeftPlayer().setInitialTurnStack(tableReader.readPlayerOnTheLeftStackSize());
						}
					}
				}
		//right player 
			gs.getRightPlayer().setInGame(tableReader.isPlayerOnTheRightInGame());
			if(gs.getRightPlayer().isInGame()){
				gs.getRightPlayer().setSitout(tableReader.isRightPlayerOnSitout());
				if(situationEstimator.isPlayerOnTheRightHoldingCards() && !gs.getRightPlayer().isSitout()){
					gs.getRightPlayer().setAllin(tableReader.isPlayerOnTheRightAllin());
					if(!gs.getRightPlayer().isAllin()){
						gs.getRightPlayer().setInitialTurnStack(tableReader.readPlayerOnTheRightStackSize());
					} 
				}
			}
	}
	private void readFlopInitialInformation() {
		if(situationEstimator.isFlopAvailable()){
			tableReader.readAndSetFlopCards();
			if(!gs.isStartingFlopPotCounted()){
				saveOtherPlayersInitialFlopInfo();
				gs.setStartingPotSizeOnTheFlop(tableReader.readPotValue());
				gs.setStartingFlopPotCounted(true);
			}
		}
	}
	private void saveOtherPlayersInitialFlopInfo() {
		if(!tableReader.isSitout()){
			gs.setInitialPlayerFlopStackSize(tableReader.readStackSize());
		}
		//left player 
		gs.getLeftPlayer().setInGame(tableReader.isPlayerOnTheLeftInGame());
		if(gs.getLeftPlayer().isInGame()){
			gs.getLeftPlayer().setSitout(tableReader.isLeftPlayerOnSitout());
			if(!gs.getLeftPlayer().isSitout() && situationEstimator.isPlayerOnTheLeftHoldingCards()){
				gs.getLeftPlayer().setAllin(tableReader.isPlayerOnTheLeftAllin());
				if(!gs.getLeftPlayer().isAllin()){
					gs.getLeftPlayer().setInitialFlopStack(tableReader.readPlayerOnTheLeftStackSize());
				}
			}
		}
		//right player 
			gs.getRightPlayer().setInGame(tableReader.isPlayerOnTheRightInGame());
			if(gs.getRightPlayer().isInGame()){
				gs.getRightPlayer().setSitout(tableReader.isRightPlayerOnSitout());
				if(!gs.getRightPlayer().isSitout() && situationEstimator.isPlayerOnTheRightHoldingCards()){
					gs.getRightPlayer().setAllin(tableReader.isPlayerOnTheRightAllin());
					if(!gs.getRightPlayer().isAllin()){
						gs.getRightPlayer().setInitialFlopStack(tableReader.readPlayerOnTheRightStackSize());
					}
				}
			}
	}
	private void readPlayerNumber_Positions_Cards() {
		tableReader.readButtonPosition();
		tableReader.readPlayerCards();
	}
	private void resetGameState() {
		gs.resetValues();
	}
	private void registerToAnotherGameWhenCurrentIsOver() {
		if(situationEstimator.isGameOver() && SpinAndGoBot.autoRegister && !stopGatheringEvents){
			actionPerformer.waitWith25PercentDeviation(1500);
			actionPerformer.registerToAnotherTournament();
			actionPerformer.waitWith25PercentDeviation(5000);
			stopGatheringEvents = true;
		}
	}
	private void savePlayersInitialInformation() {
		gs.setInitialPlayerStackSize(tableReader.readStackSize());
		//left player 
		gs.getLeftPlayer().setInGame(tableReader.isPlayerOnTheLeftInGame());
		if(gs.getLeftPlayer().isInGame()){
			gs.getLeftPlayer().setAllin(tableReader.isPlayerOnTheLeftAllin());
			gs.getLeftPlayer().setSitout(tableReader.isLeftPlayerOnSitout());
			if(!gs.getLeftPlayer().isSitout() && situationEstimator.isPlayerOnTheLeftHoldingCards()){
				if(gs.getLeftPlayer().isAllin()){
					gs.getLeftPlayer().setInitialStackSize(gs.getBigBlindValue());
				}else{
					gs.getLeftPlayer().setInitialStackSize(tableReader.readPlayerOnTheLeftStackSize());
				}
			}
		}
		//right player 
		gs.getRightPlayer().setInGame(tableReader.isPlayerOnTheRightInGame());
		if(gs.getRightPlayer().isInGame()){
			gs.getRightPlayer().setAllin(tableReader.isPlayerOnTheRightAllin());
			gs.getRightPlayer().setSitout(tableReader.isRightPlayerOnSitout());
			if(!gs.getRightPlayer().isSitout() && situationEstimator.isPlayerOnTheRightHoldingCards()){
				if(gs.getRightPlayer().isAllin()){
					gs.getRightPlayer().setInitialStackSize(gs.getBigBlindValue());
				}else{
					gs.getRightPlayer().setInitialStackSize(tableReader.readPlayerOnTheRightStackSize());
				}
			}
		}
		gs.setInitialPlayerNumber(0);
		if(gs.getLeftPlayer().isInGame()){
			gs.setInitialPlayerNumber(gs.getInitialPlayerNumber()+1);
		}
		if(gs.getRightPlayer().isInGame()){
			gs.setInitialPlayerNumber(gs.getInitialPlayerNumber()+1);
		}
	}
	private void countBBSizeAndPlayerBBs() {
		double bigBlindSize = (gs.getCurrentPotSize()/3)*2;
		gs.setBigBlindValue(bigBlindSize);
		gs.setBigBlinds(gs.getPlayersStaringStackSize()/bigBlindSize);
	}
	private void exitSitout() {
		if(tableReader.isSitout() && !stopGatheringEvents){
			actionPerformer.exitSitOut();
			actionPerformer.waitWith25PercentDeviation(500);
			stopGatheringEvents = true;
		}
	}
}
