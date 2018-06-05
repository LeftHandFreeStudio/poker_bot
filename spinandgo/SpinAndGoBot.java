package spinAndGo;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SpinAndGoBot {
	private static Robot playerBot;
	private static int TARGET_NUMBER_OF_GAMES = 0;
	private static GameManager[] gameManagers;
	private static TableCoordinates[] tablesCoordinates = {new TableCoordinates(20, 14),new TableCoordinates(538, 14),new TableCoordinates(20, 398),new TableCoordinates(538, 398)};
	//tables as following
	//    0[180,14]  1[698,14]   -> after correction 0[20,14]  1[538,14]
	//    2[180,398] 3[698,398]  -> after correction 2[20,398] 3[538,398]
	
	private static BufferedImageProvider bufferedImageProvider;
	public static boolean autoRegister = false;
	private static boolean isBotStarted = false;

	public static void main(String[] args) throws AWTException{
		try {
			playerBot = new Robot();
			bufferedImageProvider = new BufferedImageProvider(playerBot);
	    	createUserInterface();
	    	System.out.println("bot started, entering initial loop");
	    	while(!isBotStarted){ // ommited by jvm without anything in body
	    	System.out.println();
	    	}
	    	System.out.println("bot started, entering main loop");
	    	while(isBotStarted){ //main bot loop
	        	//long cur = Instant.now().toEpochMilli();
	    		if(bufferedImageProvider.takeAScreenShot()){
	    			//System.out.println(Instant.now().toEpochMilli() - cur);
	    	    	//System.out.println("updating games");
	    	    	//cur = Instant.now().toEpochMilli();
	    			updateGamesWithNewScreenShot(bufferedImageProvider.getScreenShot());
	    			//long result = Instant.now().toEpochMilli() - cur;
	    			//System.out.println("loop processing time :  " + result);
	    		}
			}
	    	System.out.println("bot terminated");
		} catch (Exception e) {
	        System.err.println("Uncaught exception - " + e.getMessage());
	        e.printStackTrace(System.err);
		}
	}

	
	private static void updateGamesWithNewScreenShot(BufferedImage screenShot){
		for(int i = 0; i < TARGET_NUMBER_OF_GAMES && gameManagers[i] != null; i++){
			gameManagers[i].playAGame(screenShot);
		}
	}

	private static void createUserInterface() {
    	JFrame frame = new JFrame("Spin&Go Bot");
    	JPanel mainPanel = new JPanel();
    	JPanel upperPanel = new JPanel();
    	JPanel bottomPanel = new JPanel();
    	JPanel bottomPanel1 = new JPanel();
    	mainPanel.setSize(200, 300);
    	JCheckBox checkBox = new JCheckBox("Autoregister to next");
    	
        JButton startButton = new JButton("Start Bot");
        JTextField field = new JTextField(2);
        startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{  
				    int input = Integer.parseInt(field.getText());  
					if(TARGET_NUMBER_OF_GAMES == 0 && input > 0 && input <= 4 && !isBotStarted){
						TARGET_NUMBER_OF_GAMES = input;  // set the number of tables from user input
						
				    	gameManagers = new GameManager[TARGET_NUMBER_OF_GAMES]; // initialize game states
						for(int i = 0; i < TARGET_NUMBER_OF_GAMES; i ++){
							gameManagers[i] = new GameManager(playerBot, tablesCoordinates[i]);
						}
						isBotStarted = true; // change the condition for starting a game
						
					}else{
						field.setText("error");
					}
				  }  
				  catch(Exception exception){
					  
				  }
			}
		});
        checkBox.addActionListener(e -> {
        	autoRegister = !autoRegister;
        });
        JLabel label = new JLabel("Enter number of tables: ");
        upperPanel.add(label);
        upperPanel.add(field);
        bottomPanel.add(checkBox);
        bottomPanel.add(startButton);
        field.setSize(100, 25);
        startButton.setSize(100, 50);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(upperPanel);
        mainPanel.add(bottomPanel);
        mainPanel.add(bottomPanel1);
        frame.getContentPane().add(mainPanel);
        frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setPreferredSize(new Dimension(150, 180));
		frame.pack();
		frame.setVisible(true);
	}

}
