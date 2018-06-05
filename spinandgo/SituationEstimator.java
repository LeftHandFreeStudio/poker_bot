package spinAndGo;

import java.awt.Color;
import java.awt.image.BufferedImage;

import test.GameState;

public class SituationEstimator {
	private int initialTablePositionX;
	private int initialTablePositionY;
	private int firstCardPosX;
	private int firstCardPosY;
	private BufferedImage ss;
	
	public SituationEstimator(TableCoordinates coordinates){
		initialTablePositionX = coordinates.getTableX();
		initialTablePositionY = coordinates.getTableY();
		firstCardPosX = initialTablePositionX + 215;
		firstCardPosY = initialTablePositionY + 232;
	}

	public boolean isPlayerInGame(){
		Color cardCornerColor = new Color(ss.getRGB(firstCardPosX, firstCardPosY));
		 if(cardCornerColor.getRed() == 255){
	        	return true;
	        }else{
	        	return false;
	        }
	}
	public boolean isPlayerOnTheLeftHoldingCards(){
		Color cardCornerColor = new Color(ss.getRGB(initialTablePositionX + 28, initialTablePositionY + 73));
		 if(cardCornerColor.getRed() == 165){
	        	return true;
	        }else{
	        	return false;
	        }
	}
	public boolean isPlayerOnTheRightHoldingCards(){
		Color cardCornerColor = new Color(ss.getRGB(initialTablePositionX + 405, initialTablePositionY + 73));
		 if(cardCornerColor.getRed() == 165){
	        	return true;
	        }else{
	        	return false;
	        }
	}

	public boolean isCheckButtonAvailable(){
		Color checkButtonColor = new Color(ss.getRGB(initialTablePositionX + 326, initialTablePositionY + 331));
		if(checkButtonColor.getRed() == 59 && checkButtonColor.getGreen() == 9){
			return true;
		}else{
			return false;
		}
	}
	public boolean isActionRequired(){
		boolean actionRequired = false;
		//fold button
		Color col = new Color(ss.getRGB(initialTablePositionX + 240, initialTablePositionY + 331));
		Color col1 = new Color(ss.getRGB(initialTablePositionX + 241, initialTablePositionY + 332));
		if(col.getRed() == 59 && col.getGreen() == 9 && col1.getRed() == 168){
			actionRequired = true;
		}
		//check button
		col = new Color(ss.getRGB(initialTablePositionX + 326, initialTablePositionY + 331));
		col1 = new Color(ss.getRGB(initialTablePositionX + 327, initialTablePositionY + 332));
		if(col.getRed() == 59 && col.getGreen() == 9 && col1.getRed() == 168){
			actionRequired = true;
		}
		//raise button
		col = new Color(ss.getRGB(initialTablePositionX + 412, initialTablePositionY + 331));
		col1 = new Color(ss.getRGB(initialTablePositionX + 413, initialTablePositionY + 332));
		if(col.getRed() == 59 && col.getGreen() == 9 && col1.getRed() == 168){
			actionRequired = true;
		}
		return actionRequired;
	}
	public boolean isTableOpened(){
		int startX = initialTablePositionX - 5;
		int startY = initialTablePositionY + 3;
		int redCount = 0;
		for(int i = 0; i < 10; i++){
			startX ++;
			Color fieldColor = new Color(ss.getRGB(startX, startY));
			//System.out.println(fieldColor.getRed());
			if(fieldColor.getRed() > 200 && fieldColor.getBlue() < 100){
				redCount ++;
			}
		}
		if(redCount >= 9){
			return true;
		}else{
			return false;
		}
	}
	public boolean isFlopAvailable(){
		Color flopCardWhiteCorner = new Color(ss.getRGB(initialTablePositionX + 165, initialTablePositionY + 133));
		if(flopCardWhiteCorner.getRed() == 255){
			return true;
		}else{
			return false;
		}
	}
	public boolean isTurnAvailable(){
		Color flopCardWhiteCorner = new Color(ss.getRGB(initialTablePositionX + 264, initialTablePositionY + 133));
		if(flopCardWhiteCorner.getRed() == 255){
			return true;
		}else{
			return false;
		}
	}
	public boolean isRiverAvailable(){
		Color flopCardWhiteCorner = new Color(ss.getRGB(initialTablePositionX + 297, initialTablePositionY + 133));
		if(flopCardWhiteCorner.getRed() == 255){
			return true;
		}else{
			return false;
		}
	}
	public boolean isFoldButtonAvailable(){
		Color foldButtonColor = new Color(ss.getRGB(initialTablePositionX + 240, initialTablePositionY + 331));
		if(foldButtonColor.getRed() == 59){
			return true;
		}else{
			return false;
		}
	}
	public boolean isGameOver(){
		int startX = initialTablePositionX + 150;
		int startY = initialTablePositionY + 234;
		int greenCount = 0;
		for(int i = 0; i < 20; i++){
			startX ++;
			Color fieldColor = new Color(ss.getRGB(startX, startY));
			if(fieldColor.getRed() == 0){
				greenCount ++;
			}
		}
		if(greenCount >= 15){
			return true;
		}else{
			return false;
		}
	}
	public void setScreenShot(BufferedImage ss) {
		this.ss = ss;
	}
	
}
