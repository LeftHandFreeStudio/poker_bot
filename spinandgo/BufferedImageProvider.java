package spinAndGo;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.time.Instant;

public class BufferedImageProvider {
	private long currentTime = 0;
	private BufferedImage screenShot = null;
	private Rectangle area;
	private Robot playerBot;
	public BufferedImageProvider(Robot robot){
		playerBot = robot;
		area = new Rectangle(160,0,1034,768);
    	currentTime = Instant.now().toEpochMilli();
	}
	public boolean takeAScreenShot(){
		if(Instant.now().toEpochMilli() - currentTime > 40){
			screenShot =  playerBot.createScreenCapture(area);
	    	currentTime = Instant.now().toEpochMilli();
			return true;
		}else{
			return false;
		}
	}
	public BufferedImage getScreenShot() {
		return screenShot;
	}
	

}
