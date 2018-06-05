package spinAndGo;

import java.util.Arrays;

public class StraightEstimator {
	//FLOP
	private static boolean isFlopStraight = false;
	private static boolean isFlopOESD = false;
	//TURN
	private static boolean isTurnStraight = false;
	private static boolean isTurnOESD = false;
	private static boolean isTurnStraightOnTable = false;
	//RIVER
	private static boolean isRiverStraight = false;
	private static boolean isRiverStraightOnTable = false;
	public static boolean getIsFlopStraight(){
		boolean result = isFlopStraight;
		isFlopStraight = false;
		return result;
	}
	public static boolean getIsFlopOESD(){
		boolean result = isFlopOESD;
		isFlopOESD = false;
		return result;
	}
	public static void checkFlopHandForStraightOrOESD(int[] flop, int[] ownCards){
		int[] f = flop.clone();
		int[] o = ownCards.clone();
		int[] h = {f[0],f[1],f[2],o[0],o[1]};
		Arrays.sort(f);
		Arrays.sort(h);
		int neighbours = 0;
		
		for(int i = 1; i < 5; i++){
			if(h[i] - h[i-1] == 1){
				neighbours++;
			}else if(h[i] - h[i-1] == 0){
				continue;
			}else{
				if(neighbours < 3){
					neighbours = 0;
				}
			}
		}
		if(neighbours == 3){
			isFlopOESD = true;
		}else if(neighbours == 4){
			isFlopStraight = true;
		}else{
		}

		if(h[0] == 2 && h[1] == 3 && h[2] == 4 && h[3] == 5 && h[4] == 14){  // WHEEL
			isFlopStraight = true;
		}
		if(isFlopOESD){
			if((h[4] == 14 && h[3] == 13 && h[2] == 12) || (h[4] == 14 && h[3] == 14 && h[2] == 13) || (h[4] == 14 && h[3] == 13 && h[2] == 13)){
				isFlopOESD = false;
			}
			if(o[0] == o[1] && f[0] - o[1] == 1){
				isFlopOESD = false;
			}
			if(o[0] - f[0] == -1 && o[1] - f[2] > 1){
				isFlopOESD = false;
			}
			if(o[1] - f[0] == -1 && o[0] - f[2] > 1){
				isFlopOESD = false;
			}
		}
		if(isFlopStraight){
			isFlopOESD = false;
		}/*

		if(isFlopOESD){
			System.out.println("OESD found: " + isFlopOESD);
		}else if(isFlopStraight){
			System.out.println("Straight found: " + isFlopStraight);
		}else{
			System.out.println("found nothing");
		}*/
	}

	public static boolean getIsTurnStraight(){
		boolean result = isTurnStraight;
		isTurnStraight = false;
		return result;
	}
	public static boolean getIsTurnOESD(){
		boolean result = isTurnOESD;
		isTurnOESD = false;
		return result;
	}
	public static boolean getIsTurnStraightOnTable(){
		boolean result = isTurnStraightOnTable;
		isTurnStraightOnTable = false;
		return result;
	}
	public static void checkTurnHandForStraightOrOESD(int[] turn, int[] ownCards){
		int[] t = turn.clone();
		int[] o = ownCards.clone();
		int[] h = {t[0],t[1],t[2],t[3],o[0],o[1]};
		Arrays.sort(t);
		Arrays.sort(h);
		
		isTurnStraightOnTable = true;
		for(int i = 1; i < 4; i ++){
			if(t[i] - t[i-1] != 1){
				isTurnStraightOnTable = false;
			}
		}
		
		int neighbours = 0;
		
		for(int i = 1; i < 6; i++){
			if(h[i] - h[i-1] == 1){
				neighbours++;
			}else if(h[i] - h[i-1] == 0){
				continue;
			}else{
				if(neighbours < 3){
					neighbours = 0;
				}else if(neighbours == 3){
					neighbours = 3;
					break;
				}
			}
		}
		if(neighbours == 3){
			isTurnOESD = true;
		}else if(neighbours >= 4){
			isTurnStraight = true;
		}else{
		}

		if(h[0] == 2 && h[1] == 3 && h[2] == 4 && h[3] == 5 && (h[4] == 14 || h[5] == 14)){  // WHEEL
			isTurnStraight = true;
		}
		if(isTurnOESD){
			if((h[5] == 14 && h[4] == 13 && h[3] == 12) || (h[5] == 14 && h[4] == 14 && h[3] == 13) || (h[5] == 14 && h[4] == 13 && h[3] == 13)){
				isTurnOESD = false;
			}
			if(o[0] == o[1] && t[0] - o[1] == 1){
				isTurnOESD = false;
			}
			if(o[0] - t[0] == -1 && o[1] - t[3] > 1){
				isTurnOESD = false;
			}
			if(o[1] - t[0] == -1 && o[0] - t[3] > 1){
				isTurnOESD = false;
			}
		}
		if(isTurnStraight){
			if(o[0] == o[1] && t[0] - o[1] == 1){
				isTurnStraight = false;
			}
			if(o[0] - t[0] == -1 && o[1] - t[3] > 1){
				isTurnStraight = false;
			}
			if(o[1] - t[0] == -1 && o[0] - t[3] > 1){
				isTurnStraight = false;
			}
			if((o[1] - t[0] == -1 || o[0] - t[0] == -1) && o[0] < t[0] && o[1] < t[0]){
				isTurnStraight = false;
			}
			isTurnOESD = false;
		}
	}
	public static boolean getIsRiverStraightOnTable(){
		boolean result = isRiverStraightOnTable;
		isRiverStraightOnTable = false;
		return result;
	}
	public static boolean getIsRiverStraight(){
		boolean result = isRiverStraight;
		isRiverStraight = false;
		return result;
	}
	public static void checkRiverHandForStraightOrOESD(int[] river, int[] ownCards){
		int[] r = river.clone();
		int[] o = ownCards.clone();
		int[] h = {r[0],r[1],r[2],r[3],r[4],o[0],o[1]};
		Arrays.sort(r);
		Arrays.sort(h);
		int neighbours = 0;

		int count = 0;
		for(int i = 1; i < 5; i ++){
			if(r[i] - r[i-1] == 1){
				count++;
			}else if(r[i] - r[i-1] == 0){
				continue;
			}else{
				if(count < 4){
					count = 0;
				}else if(count == 4){
					count = 4;
					break;
				}
			}
		}
		if(count >=4){
			isRiverStraightOnTable = true;
		}
		
		for(int i = 1; i < 7; i++){
			if(h[i] - h[i-1] == 1){
				neighbours++;
			}else if(h[i] - h[i-1] == 0){
				continue;
			}else{
				if(neighbours < 4){
					neighbours = 0;
				}else if(neighbours == 4){
					neighbours = 4;
					break;
				}
			}
		}
		if(neighbours >= 4){
			isRiverStraight = true;
		}

		if(h[0] == 2 && h[1] == 3 && h[2] == 4 && h[3] == 5 && (h[5] == 14 || h[6] == 14)){  // WHEEL
			isRiverStraight = true;
			if(o[1] == 14 && o[0] != 14 && o[0] > r[4]){
				isRiverStraight = false;
			}
			if(o[0] == 14 && o[1] != 14 && o[1] > r[4]){
				isRiverStraight = false;
			}
		}
		if(isRiverStraight){
			if(o[0] == o[1] && r[0] - o[1] == 1){
				isRiverStraight = false;
			}
			if(o[0] - r[0] == -1 && o[1] - r[4] > 1){
				isRiverStraight = false;
			}
			if(o[1] - r[0] == -1 && o[0] - r[4] > 1){
				isRiverStraight = false;
			}
			if((o[1] - r[0] == -1 || o[0] - r[0] == -1) && o[0] < r[0] && o[1] < r[0]){
				isRiverStraight = false;
			}
		}
		/*if(isRiverStraight){
			System.out.println("Straight found: " + isRiverStraight);
		}else{
			System.out.println("found nothing");
		}*/
	}
}
