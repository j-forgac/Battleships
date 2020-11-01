package game;

import java.io.OutputStream;

public interface BattlefieldCommon {
	default int[] transferToNums(String tile, int size) {

		int[] outputArr = new int[2];
		int myCastedString;
		outputArr[0] = tile.charAt(0) - 97;
		if(tile.length() > 2){
			myCastedString = Integer.parseInt(tile.substring(1,3));
		} else {
			myCastedString = size - Integer.parseInt(tile.substring(1,2));
		}
		outputArr[1] = myCastedString - 1;
		System.out.println(outputArr[0]);
		System.out.println(outputArr[1]);
		return outputArr;

	}
}
