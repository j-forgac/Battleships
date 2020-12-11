package game;

import java.io.FileReader;
import java.util.Scanner;

public class Battlefield {
	static int height;
	private Field[][] battlefield;
	boolean ifWon = false;

	public Battlefield(int dimensions) {
		height = dimensions;
		battlefield = new Field[height][height];

		myFor(new CallBack() {
			public void call(int x, int y) {
				battlefield[x][y] = Field.createWater();
			}
		});
	}

	public void placeShips() {
		String position;
		String direction;
		Scanner mySc = new Scanner(System.in);
		for (int boatSize = 1; boatSize <= height; boatSize++) {
			System.out.println("Pozice levého horního rohu " + boatSize + " políčkové lodi");
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
			if (this.battlefield[y][cooX].getType() != "WATER") {
				return false;
			}
		}
		for (int x = cooX; x < cooX + boatLength && x < height; x++) {
			if (this.battlefield[cooY][x].getType() != "WATER") {
				return false;
			}
		}
		return true;
	}

	public void buildShipH(int cooX, int cooY, int boatLength) {
		for (int x = cooX; x < cooX + boatLength; x++) {
			this.battlefield[cooY][x] = Field.createShip(boatLength);
		}
	}

	public void buildShipV(int cooX, int cooY, int boatLength) {
		for (int y = cooY; y < cooY + boatLength; y++) {
			this.battlefield[y][cooX] = Field.createShip(boatLength);
		}
	}

	public boolean evaluateAttack(String tileCode) {
		int attackCooX = transferToNums(tileCode)[0];
		int attackCooY = transferToNums(tileCode)[1];
		switch (battlefield[attackCooY][attackCooX].getType()) {
			case "WATER":
				battlefield[attackCooY][attackCooX] = Field.createMiss();
				break;
			case "SHIP":
				battlefield[attackCooY][attackCooX] = Field.createHit();
				break;
			case "MISS":
				System.out.println("nemůžete sem střílet, toto pole už bylo dřív MISS!");
				break;
			case "HIT":
				System.out.println("nemůžete sem střílet, toto pole už bylo dřív HIT!");
				break;
		}


		for (int x = 0; x < height; x++) {
			for (int y = 0; y < height; y++) {
				if(battlefield[x][y].getType() == "SHIP"){
					return true;
				}
			}
		}
		return false;
	}

	public void drawBattlefield() {
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < height; y++) {
				switch (battlefield[x][y].getType()) {
					case "WATER":
						System.out.print(" 0 ");
						break;
					case "SHIP":
						System.out.print(" " + battlefield[x][y].getDimensions() + " ");
						break;
					case "MISS":
						System.out.print(" X ");
						break;
					case "HIT":
						System.out.print(" * ");
						break;
					default:
						System.out.print(" 0 ");
						break;
				}
			}
			System.out.println("");
		}
	}

	private int[] transferToNums(String tile) {
		if(tile.length() == 0){
			return null;
		}
		int[] outputArr = new int[2];
		int myCastedString;
		outputArr[0] = tile.charAt(0) - 97;
		if(tile.length() > 2){
			myCastedString = Integer.parseInt(tile.substring(1,3));
		} else {
			myCastedString = height - Integer.parseInt(tile.substring(1,2));
		}
		outputArr[1] = myCastedString;
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
