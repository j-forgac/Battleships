package game;

import java.util.Scanner;

import static game.Battlefield.TileType.HIT;

public class Battlefield {
	static int height;
	private TileType[][] battlefield;

	enum TileType {
		WATER,
		SHIP,
		MISS,
		HIT
	}

	public Battlefield(int dimensions) {
		height = dimensions;
		battlefield = new TileType[height][height];

		myFor(new CallBack() {
			public void call(int x, int y) {
				battlefield[x][y] = TileType.WATER;
			}
		});
	}

	public void placeShips() {
		String position;
		String direction;
		Scanner mySc = new Scanner(System.in);
		for (int boatSize = 1; boatSize <= height; boatSize++) {
			System.out.println("Pozice pravého horního rohu " + boatSize + " políčkové lodi");
			position = mySc.nextLine();

			int cooX = transferToNums(position)[0];
			int cooY = transferToNums(position)[1];
			if (fitsInMap(cooX, boatSize) && notCollidingWithOtherShips(cooX, cooY, boatSize)) {
				if (fitsInMap(cooY, boatSize) && notCollidingWithOtherShips(cooX, cooY, boatSize)) {
					System.out.println("Loď povede z pole " + position + " vertikálně, nebo horizontálně?(V/H)");
					direction = mySc.nextLine();
					if (direction.equals("V")) {
						buildShipV(cooX, cooY, boatSize);
					} else {
						buildShipH(cooX, cooY, boatSize);
					}
				} else {
					System.out.println("Loď povede horizontálně");
					//automaticky vybran vhodny smer, je jen jeden mozny
					buildShipH(cooX, cooY, boatSize);
				}
			} else {
				if (fitsInMap(cooY, boatSize) && notCollidingWithOtherShips(cooX, cooY, boatSize)) {
					System.out.println("Loď povede vertikálně");
					//automaticky vybran vhodny smer, je jen jeden mozny
					buildShipV(cooX, cooY, boatSize);
				} else {
					System.out.println("Zvolte jiné pole. Loď se nevejde!");
				}
			}
			drawBattlefield();
		}
	}

	public boolean fitsInMap(int coos, int boatLength) {
		int boundaries = this.battlefield.length;
		if (coos + boatLength <= boundaries) {
			return true;
		} else {
			return false;
		}
	}

	public boolean notCollidingWithOtherShips(int cooX, int cooY, int boatLength) {
		for (int y = cooY; y < cooY + boatLength && y < height; y++) {
			if (this.battlefield[y][cooX] != TileType.WATER) {
				return false;
			}
		}
		for (int x = cooX; x < cooX + boatLength && x < height; x++) {
			if (this.battlefield[cooY][x] != TileType.WATER) {
				return false;
			}
		}
		return true;
	}

	public void buildShipH(int cooX, int cooY, int boatLength) {
		for (int x = cooX; x < cooX + boatLength; x++) {
			this.battlefield[cooY][x] = TileType.SHIP;
		}
	}

	public void buildShipV(int cooX, int cooY, int boatLength) {
		for (int y = cooY; y < cooY + boatLength; y++) {
			this.battlefield[y][cooX] = TileType.SHIP;
		}
	}

	public boolean evaluateAttack(String tileCode) {
		boolean ifWon = false;

		return ifWon;
	}

	public void drawBattlefield() {
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < height; y++) {
				switch (battlefield[x][y]) {
					case WATER:
						System.out.print("0");
						break;
					case SHIP:
						System.out.print("1");
						break;
					case MISS:
						System.out.print("X");
						break;
					case HIT:
						System.out.print("*");
						break;
				}
			}
			System.out.println("");
		}
	}

	private int[] transferToNums(String tile) {

		int[] outputArr = new int[2];
		int myCastedString;
		outputArr[0] = tile.charAt(0) - 97;
		if(tile.length() > 2){
			myCastedString = Integer.parseInt(tile.substring(1,3));
		} else {
			myCastedString = height - Integer.parseInt(tile.substring(1,2));
		}
		outputArr[1] = myCastedString;
		System.out.println(outputArr[0]);
		System.out.println(outputArr[1]);
		return outputArr;

	}


	public static void myFor(CallBack callBack) {
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < height; y++) {
				callBack.call(x, y);
			}
		}
	}

	public interface CallBack {
		public void call(int x, int y);
	}
}
