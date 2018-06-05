package spinAndGo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import spinAndGo.Card;
import spinAndGo.GameState;

public class TableReader {
	private int initialTablePositionX;
	private int initialTablePositionY;
	private int firstCardPosX;
	private int firstCardPosY;
	private int secondCardPosX;
	private int secondCardPosY;
	private BufferedImage ss;
	private GameState gs;
	public TableReader(GameState gs,TableCoordinates coordinates) throws Exception{
		this.gs = gs;
		initialTablePositionX = coordinates.getTableX();
		initialTablePositionY = coordinates.getTableY();
		firstCardPosX = initialTablePositionX + 215;
		firstCardPosY = initialTablePositionY + 232;
		secondCardPosX = initialTablePositionX + 246;
		secondCardPosY = initialTablePositionY + 232;
	}
	public void readPlayerCards(){
		gs.setCards(readCards(firstCardPosX, firstCardPosY), readCards(secondCardPosX, secondCardPosY));

	}
	public void readAndSetFlopCards(){
		gs.setFlop1(readCards(initialTablePositionX + 165, initialTablePositionY + 133));
		gs.setFlop2(readCards(initialTablePositionX + 198, initialTablePositionY + 133));
		gs.setFlop3(readCards(initialTablePositionX + 231, initialTablePositionY + 133));
	}
	public void readTurnCard(){
		gs.setTurn(readCards(initialTablePositionX + 264, initialTablePositionY + 133));
	}
	public void readRiverCard(){
		gs.setRiver(readCards(initialTablePositionX + 297, initialTablePositionY + 133));
	}
	public void readButtonPosition(){
		Color buttonColor = new Color(ss.getRGB(initialTablePositionX + 288, initialTablePositionY + 226));
		if(buttonColor.getRed() == 187 && buttonColor.getGreen() == 0){
			gs.setPosition(0); // btn
		}
		buttonColor = new Color(ss.getRGB(initialTablePositionX + 389, initialTablePositionY + 123));
		if(buttonColor.getRed() == 187 && buttonColor.getGreen() == 0){
			gs.setPosition(1); // sb
		}
		buttonColor = new Color(ss.getRGB(initialTablePositionX + 107, initialTablePositionY + 120));
		if(buttonColor.getRed() == 187 && buttonColor.getGreen() == 0){
			gs.setPosition(2); // bb
		}
		
		
	}
	/*public void readInitialPlayerNumber(){
		int playerNumber = 2; // assume that at the beginig are two players and substract the unavailable
		Color playerColor = new Color(ss.getRGB(initialTablePositionX + 84, initialTablePositionY + 118));
		if(playerColor.getRed() == 53 && playerColor.getGreen() == 53){
			playerNumber --;
		}
		playerColor = new Color(ss.getRGB(initialTablePositionX + 383, initialTablePositionY + 104));
		if(playerColor.getRed() == 43 && playerColor.getGreen() == 43){
			playerNumber --;
		}
		gs.setInitialPlayerNumber(playerNumber);
	}*/
	public boolean isPlayerOnTheRightInGame(){
		boolean isInGame = true;
		Color playerColor = new Color(ss.getRGB(initialTablePositionX + 383, initialTablePositionY + 104));
		if(playerColor.getRed() == 43 && playerColor.getGreen() == 43){
			isInGame = false;
		}
		return isInGame;
	}
	public boolean isPlayerOnTheLeftInGame(){
		boolean isInGame = true;
		Color playerColor = new Color(ss.getRGB(initialTablePositionX + 84, initialTablePositionY + 118));
		if(playerColor.getRed() == 53 && playerColor.getGreen() == 53){
			isInGame = false;
		}
		return isInGame;
	}
	public boolean isPlayerOnTheLeftAllin(){
		boolean isAllin = true;
		int startX = initialTablePositionX + 20;
		int startY = initialTablePositionY + 115;
		for(int i = 0; i < 30; i++){
			Color allinColor = new Color(ss.getRGB(startX + i, startY));
			if(allinColor.getRed() == 192 && allinColor.getGreen() == 248){
				isAllin = false;
				break;
			}
		}
		return isAllin;
	}
	public boolean isPlayerOnTheRightAllin(){
		boolean isAllin = true;
		int startX = initialTablePositionX + 420;
		int startY = initialTablePositionY + 115;
		for(int i = 0; i < 30; i++){
			Color allinColor = new Color(ss.getRGB(startX + i, startY));
			if(allinColor.getRed() == 192 && allinColor.getGreen() == 248){
				isAllin = false;
				break;
			}
		}
		return isAllin;
	}
	public boolean isPlayerAllin(){
		boolean isAllin = true;
		int startX = initialTablePositionX + 200;
		int startY = initialTablePositionY + 284;
		for(int i = 0; i < 30; i++){
			Color allinColor = new Color(ss.getRGB(startX + i, startY));
			if(allinColor.getRed() == 192 && allinColor.getGreen() == 248){
				isAllin = false;
				break;
			}
		}
		return isAllin;
	}
	/*public boolean isPlayerOnSitout(int startingX, int startingY){
		boolean isSitout = false;
		int startX = initialTablePositionX + startingX;
		int startY = initialTablePositionY + startingY;
		for(int i = 0; i < 5; i++){
			Color allinColor = new Color(ss.getRGB(startX + i, startY));
			if((allinColor.getRed() == 192 && allinColor.getGreen() == 248) || (allinColor.getRed() == 96 && allinColor.getGreen() == 139)){
				isSitout = true;
				break;
			}
		}
		return isSitout;
	}*/
	public boolean isLeftPlayerOnSitout(){
		boolean isSitout = false;
		int startX = initialTablePositionX + 13;
		int startY = initialTablePositionY + 113;
		for(int i = 0; i < 5; i++){
			Color allinColor = new Color(ss.getRGB(startX + i, startY));
			if((allinColor.getRed() == 192 && allinColor.getGreen() == 248) || (allinColor.getRed() == 96 && allinColor.getGreen() == 139)){
				isSitout = true;
				break;
			}
		}
		return isSitout;
	}
	public boolean isRightPlayerOnSitout(){
		boolean isSitout = false;
		int startX = initialTablePositionX + 420;
		int startY = initialTablePositionY + 113;
		for(int i = 0; i < 5; i++){
			Color allinColor = new Color(ss.getRGB(startX + i, startY));
			if((allinColor.getRed() == 192 && allinColor.getGreen() == 248) || (allinColor.getRed() == 96 && allinColor.getGreen() == 139)){
				isSitout = true;
				break;
			}
		}
		return isSitout;
	}
	
	public int readStackSize(){
		int startX = initialTablePositionX + 250;
		int startY = initialTablePositionY + 275;
		int hStartX = startX;
		int startingY = initialTablePositionY + 276;
		Color dollarColor;
		ArrayList<Integer> digits = new ArrayList<>();
		do{
			hStartX--;
			if(hStartX < initialTablePositionX){
				hStartX = startX;
			}
			dollarColor = new Color(ss.getRGB(hStartX, startY));
		}while(dollarColor.getRed() != 192 && dollarColor.getGreen() != 248);
		int endX = hStartX;
		hStartX = endX + 4; // set the cursor to first digit position

		if(isColumnBlank(hStartX, startingY)){
			hStartX++;
		}
		int counter = 0;
		boolean finished = false;
		do{
			readDigitFromPosition(hStartX, startingY, digits);
			if(digits.get(digits.size()-1) == 1){
				hStartX += 5;
			}else if(digits.get(digits.size()-1) == -1){
				hStartX += 3;
			}else{
				hStartX += 7;
			}
			while(isColumnBlank(hStartX, startingY)){
				hStartX++;
				counter ++;
				if(counter>10){
					finished = true;
					break;
				}
			}
		}while(!finished);
		//System.out.println("Stack size : " + giveStackSizeFromArray(digits));
		int stackSize = giveStackSizeFromArray(digits);
		//System.out.println("Stack size : " + stackSize);
		return stackSize;
	}
	public int readPlayerOnTheRightStackSize(){
				int startX = initialTablePositionX + 471;
				int startY = initialTablePositionY + 105;
				int hStartX = startX;
				int startingY = startY+1;
				Color dollarColor;
				ArrayList<Integer> digits = new ArrayList<>();
				do{
					hStartX--;
					if(hStartX < initialTablePositionX){
						hStartX = startX;
					}
					dollarColor = new Color(ss.getRGB(hStartX, startY));
				}while(dollarColor.getRed() != 192 && dollarColor.getGreen() != 248);
				int endX = hStartX;
				hStartX = endX + 4; // set the cursor to first digit position
		
				if(isColumnBlank(hStartX, startingY)){
					hStartX++;
				}
				int counter = 0;
				boolean finished = false;
				do{
					readDigitFromPosition(hStartX, startingY, digits);
					if(digits.get(digits.size()-1) == 1){
						hStartX += 5;
					}else if(digits.get(digits.size()-1) == -1){
						hStartX += 3;
					}else{
						hStartX += 7;
					}
					while(isColumnBlank(hStartX, startingY)){
						hStartX++;
						counter ++;
						if(counter>10){
							finished = true;
							break;
						}
					}
				}while(!finished);
				//System.out.println("Stack size : " + giveStackSizeFromArray(digits));
				int stackSize = giveStackSizeFromArray(digits);
				//System.out.println("Stack size : " + stackSize);
				return stackSize;
	}
	public int readPlayerOnTheLeftStackSize(){
				int startX = initialTablePositionX + 65;
				int startY = initialTablePositionY + 105;
				int hStartX = startX;
				int startingY = startY+1;
				Color dollarColor;
				ArrayList<Integer> digits = new ArrayList<>();
				do{
					hStartX--;
					if(hStartX < initialTablePositionX){
						hStartX = startX;
					}
					dollarColor = new Color(ss.getRGB(hStartX, startY));
				}while(dollarColor.getRed() != 192 && dollarColor.getGreen() != 248);
				int endX = hStartX;
				hStartX = endX + 4; // set the cursor to first digit position
		
				if(isColumnBlank(hStartX, startingY)){
					hStartX++;
				}
				int counter = 0;
				boolean finished = false;
				do{
					readDigitFromPosition(hStartX, startingY, digits);
					if(digits.get(digits.size()-1) == 1){
						hStartX += 5;
					}else if(digits.get(digits.size()-1) == -1){
						hStartX += 3;
					}else{
						hStartX += 7;
					}
					while(isColumnBlank(hStartX, startingY)){
						hStartX++;
						counter ++;
						if(counter>10){
							finished = true;
							break;
						}
					}
				}while(!finished);
				//System.out.println("Stack size : " + giveStackSizeFromArray(digits));
				int stackSize = giveStackSizeFromArray(digits);
				//System.out.println("Stack size : " + stackSize);
				return stackSize;
	}
	private int giveStackSizeFromArray(ArrayList<Integer> digits){
		int stackSize = 0;
		if(digits.size() > 2){
			if(digits.get(1) < 0){
				digits.remove(1);
			}
		}
		for(int i =0; i < digits.size(); i++){
			stackSize += (digits.get(i) * Math.pow(10, digits.size()-1-i));
		}
		return stackSize;
	}
	private boolean isColumnBlank(int posX, int posY){
		int tmpY = posY;
		boolean isOne = true;
		for(int i = 0; i < 8; i++){
			Color digitColor = new Color(ss.getRGB(posX, tmpY));
			if(digitColor.getRed() == 192 && digitColor.getGreen() == 248){
				isOne = false;
			}
			tmpY++;
		}
		return isOne;
	}
	
	private void readDigitFromPosition(int startingX, int startingY, ArrayList<Integer> digits){
		//starting upper left x,y and identifying digit
		int tmpY = startingY;
		int tmpX = startingX;

		Color digitColor = new Color(ss.getRGB(tmpX, tmpY));
		if(digitColor.getRed() == 192 && digitColor.getGreen() == 248){ //7
			digits.add(7);
		}else{//1,2,3,4,5,6,8,9,0
			tmpY += 7;
			digitColor = new Color(ss.getRGB(tmpX, tmpY));
			if(digitColor.getRed() == 192 && digitColor.getGreen() == 248){ //1,2, ','
				tmpX = startingX;
				tmpY = startingY;
				tmpY += 8;
				digitColor = new Color(ss.getRGB(tmpX, tmpY));
				if(digitColor.getRed() == 192 && digitColor.getGreen() == 248){ //','
					// do sth with ','
					digits.add(-1); // means ','
				}else{//1,2
					tmpX = startingX;
					tmpY = startingY;
					tmpY += 6;
					digitColor = new Color(ss.getRGB(tmpX, tmpY));
					if(digitColor.getRed() == 192 && digitColor.getGreen() == 248){ //2
						digits.add(2);
					}else{ //1
						digits.add(1);
					}	
				}
			}else{//3,4,5,6,8,9,0
				tmpX = startingX;
				tmpY = startingY;
				tmpY+=7;
				tmpX ++;
				digitColor = new Color(ss.getRGB(tmpX, tmpY));
				if(digitColor.getRed() != 192 && digitColor.getGreen() != 248){ //4
						digits.add(4);
				}else{//3,5,6,8,9,0
					tmpX = startingX;
					tmpY = startingY;
					tmpY +=2;
					digitColor = new Color(ss.getRGB(tmpX, tmpY));
					if(digitColor.getRed() != 192 && digitColor.getGreen() != 248){ //5, 3
						tmpX = startingX;
						tmpY = startingY;
						tmpY++;
						digitColor = new Color(ss.getRGB(tmpX, tmpY));
						if(digitColor.getRed() != 192 && digitColor.getGreen() != 248){ //5
							digits.add(5);
						}else{
							digits.add(3);
						}
					}else{//6,8,9,0
						tmpX = startingX;
						tmpY = startingY;
						tmpX +=5;
						tmpY ++;
						digitColor = new Color(ss.getRGB(tmpX, tmpY));
						if(digitColor.getRed() != 192 && digitColor.getGreen() != 248){ //6
							digits.add(6);
						}else{//8,9,0
								tmpX = startingX;
								tmpY = startingY;
								tmpY +=5;
								digitColor = new Color(ss.getRGB(tmpX, tmpY));
								if(digitColor.getRed() != 192 && digitColor.getGreen() != 248){ //9
									digits.add(9);
								}else{//8,0
									tmpX = startingX;
									tmpY = startingY;
									tmpY +=3;
									digitColor = new Color(ss.getRGB(tmpX, tmpY));
									if(digitColor.getRed() != 192 && digitColor.getGreen() != 248){ //8
										digits.add(8);
									}else{//0
									    digits.add(0);
									}
								}
						}
					}
				}
				
			}
		} // end of condtionion
	}
	public int readPotValue(){
		int potSize = 0;
		int initialSearchingX = initialTablePositionX + 210;   //426x135
		int initialSearchingY = initialTablePositionY + 121;
		
		Color pixelColor = new Color(ss.getRGB(initialSearchingX, initialSearchingY));
		for(int i = 1; i < 100; i ++){
			if(pixelColor.getRed() != 255){
				initialSearchingX ++;
				pixelColor = new Color(ss.getRGB(initialSearchingX, initialSearchingY));
			}else{
				break;
			}
		}
		
			// po tym etapie mamy pozycjê x dolnego ogonka dolara w pocie
			int currentSearchingX = initialSearchingX + 4; // przesuwamy ¿eby przeskoczyæ dolara
			boolean isThereMoreDigits = true;
			boolean digitFound = false;
			int currentSearchingY = initialTablePositionY + 112;
			int startingY = currentSearchingY;
			int spacesDetected = 0;

			//int count = 0;
			ArrayList<Integer> digits = new ArrayList<>();

			while(isThereMoreDigits){
				int spaces = 0;
				while(!digitFound){
					boolean found = false;
					currentSearchingY = startingY;
					for(int i = 0; i < 12; i++){
						pixelColor = new Color(ss.getRGB(currentSearchingX, currentSearchingY));
						if(pixelColor.getRed() == 255){
							found = true;
							break;
						}else{
							currentSearchingY ++;
						}
					}
					if(found){
						digitFound = true;
						currentSearchingY = initialTablePositionY + 120;
						break;
					}else{
						currentSearchingX ++;
						currentSearchingY = startingY;
						spaces++;
						if(spaces > 10){
							isThereMoreDigits = false;
							break;
						}
					}
				}
					if(digitFound == true){
						pixelColor = new Color(ss.getRGB(currentSearchingX, currentSearchingY));
						if(pixelColor.getRed() == 255){
							pixelColor = new Color(ss.getRGB(currentSearchingX + 1, currentSearchingY - 3));
							if(pixelColor.getRed() == 255){
								digits.add(1);
								currentSearchingX +=5;
							}else{
								pixelColor = new Color(ss.getRGB(currentSearchingX + 1, currentSearchingY - 2));
								if(pixelColor.getRed() == 255){
									digits.add(2);   //// dot in cash game or colon in play money
									currentSearchingX +=6;
								}else{
									digits.add(-1);
									currentSearchingX +=2;
								}
							}
						}else{
							pixelColor = new Color(ss.getRGB(currentSearchingX, currentSearchingY - 2));
							if(pixelColor.getRed() == 255){
								pixelColor = new Color(ss.getRGB(currentSearchingX + 4, currentSearchingY -5));
								if(pixelColor.getRed() == 17 || pixelColor.getRed() == 18){
									digits.add(6);
									currentSearchingX +=6;
								}else{
									pixelColor = new Color(ss.getRGB(currentSearchingX, currentSearchingY -5));
									if(pixelColor.getRed() == 17 || pixelColor.getRed() == 18){
										digits.add(4);
										currentSearchingX +=6;
									}else{
										pixelColor = new Color(ss.getRGB(currentSearchingX + 2, currentSearchingY -4));
										if(pixelColor.getRed() == 255){
											digits.add(8);
											currentSearchingX +=6;
										}else{
											digits.add(0);
											currentSearchingX +=6;
										}
									}
								}
							}else{
								pixelColor = new Color(ss.getRGB(currentSearchingX + 1, currentSearchingY));
								if(pixelColor.getRed() == 17 || pixelColor.getRed() == 18){
									digits.add(7);
									currentSearchingX +=6;
								}else{
									pixelColor = new Color(ss.getRGB(currentSearchingX, currentSearchingY - 4));
									if(pixelColor.getRed() == 255){
										digits.add(9);
										currentSearchingX +=6;
									}else{
										pixelColor = new Color(ss.getRGB(currentSearchingX + 1, currentSearchingY - 4));
										if(pixelColor.getRed() == 255){
											digits.add(5);
											currentSearchingX +=6;
										}else{
											digits.add(3);
											currentSearchingX +=6;
										}
									}
								}
							}
						}
						digitFound = false;
					}
			}
			//System.out.println("Pot value : " + digits);
			//System.out.println("Pot value : " + giveStackSizeFromArray(digits));
			potSize = giveStackSizeFromArray(digits);
			return potSize;
		
		
		
	}
	
	private Card readCards(int posX, int posY){
		Card card = new Card();
		//identifying card color
		int colorPositionX = posX + 5;
		int colorPositionY = posY + 19;
		Color cardColorRGB = new Color(ss.getRGB(colorPositionX, colorPositionY));
        if(cardColorRGB.getRed() == 77 && cardColorRGB.getGreen() == 77){
        	card.setCardColor("s");
        }else if(cardColorRGB.getRed() == 0 && cardColorRGB.getGreen() == 89){
        	card.setCardColor("d");
        }else if(cardColorRGB.getRed() == 231 && cardColorRGB.getGreen() == 0){
        	card.setCardColor("h");
        }else if(cardColorRGB.getRed() == 48 && cardColorRGB.getGreen() == 161){
        	card.setCardColor("c");
        }
        
        // identify card value
        
        Color cardPixelColor = new Color(ss.getRGB(posX + 5, posY + 3));
        if(cardPixelColor.getRed() == 255){ //2, 3, 5, 6, 7, 8, 9, J, Q
        	cardPixelColor = new Color(ss.getRGB(posX + 2, posY + 12));
        	if(cardPixelColor.getRed() >= 254){ // 6 7 8 9 J
        		cardPixelColor = new Color(ss.getRGB(posX + 3, posY + 4));
            	if(cardPixelColor.getRed() == 255){ // 7 J
            		cardPixelColor = new Color(ss.getRGB(posX + 3, posY + 10));
                	if(cardPixelColor.getRed() == 255){
                		card.setCardValue(7);
                		//7
                	}else{
                		card.setCardValue(11);
                		//J
                	}
            	}else{ // 6 8 9
            		cardPixelColor = new Color(ss.getRGB(posX + 3, posY + 9));
                	if(cardPixelColor.getRed() == 255){
                		card.setCardValue(9);
                		//9
                	}else{
                		cardPixelColor = new Color(ss.getRGB(posX + 7, posY + 4));
                    	if(cardPixelColor.getRed() == 255){
                    		card.setCardValue(6);
                    		// 6
                    	}else{
                    		card.setCardValue(8);
                    		// 8
                    	}
                	}
            	}
        	}else{ // 2 3 5 Q
        		cardPixelColor = new Color(ss.getRGB(posX + 3, posY + 7));
            	if(cardPixelColor.getRed() == 255){ //2 3
            		cardPixelColor = new Color(ss.getRGB(posX + 8, posY + 10));
                	if(cardPixelColor.getRed() == 255){
                		card.setCardValue(2);
                		// 2
                	}else{
                		card.setCardValue(3);
                		// 3
                	}
            	}else{ // 5 Q
            		cardPixelColor = new Color(ss.getRGB(posX + 3, posY + 9));
                	if(cardPixelColor.getRed() == 255){
                		// 5
                		card.setCardValue(5);
                	}else{
                		card.setCardValue(12);
                		// Q
                	}
            	}
        	}
        }else{  // 4, 10, K, A
            cardPixelColor = new Color(ss.getRGB(posX + 4, posY + 3));
            if(cardPixelColor.getRed() == 255){ // K, 10
                cardPixelColor = new Color(ss.getRGB(posX - 1, posY + 2));
            	if(cardPixelColor.getRed() == 255){
            		//K
            		card.setCardValue(13);
            	}else{
            		card.setCardValue(10);
            		//10
            	}
            }else{  // 4, A
                cardPixelColor = new Color(ss.getRGB(posX + 1, posY + 13));
            	if(cardPixelColor.getRed() == 255){
            		//4
            		card.setCardValue(4);
            	}else{
            		card.setCardValue(14);
            		//A
            	}
            }
        }
        return card;
	}
	public boolean isSitout(){
		Color firstPointColor = new Color(ss.getRGB(initialTablePositionX + 202, initialTablePositionY + 276)); // 97 136 90
		boolean isSitoutButtonVisible = false;
		if((firstPointColor.getRed() == 97 && firstPointColor.getGreen() == 136) ||(firstPointColor.getRed() == 192 && firstPointColor.getGreen() == 248)){
			isSitoutButtonVisible = true;
		}
		return isSitoutButtonVisible;
	}
	public void setScreenShot(BufferedImage ss) {
		this.ss = ss;
	}
	
}
