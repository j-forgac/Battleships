package game;

import java.util.Scanner;

public class MyGame{
    public static void main(String[] Args) {
        Scanner mySc = new Scanner(System.in);
        int size = 0;
        while(size < 1 || size > 26) {
            System.out.println("Zadejte výšku pole");
            size = mySc.nextInt();
        }

        boolean notWon = true;
        Battlefield pole1 = new Battlefield(size);
        Battlefield pole2 = new Battlefield(size);
        String tileUnderFire;

        pole1.placeShips();
        pole2.placeShips();


        while (notWon) {
            System.out.println("Střelba na pole!:");
            mySc.nextLine(); // just to clean the buffer
            tileUnderFire = mySc.nextLine();
            //hráč jedna střílí na hráče dva
            notWon = pole2.evaluateAttack(tileUnderFire);
            pole2.drawBattlefield();
            if(!notWon){
                System.out.println("HRÁČ 1 VYHRÁL!");
                break;
            }

            System.out.println("Střelba na pole!:");
            tileUnderFire = mySc.nextLine();
            //hráč jedna střílí na hráče dva
            notWon = pole1.evaluateAttack(tileUnderFire);
            pole1.drawBattlefield();
            if(!notWon){
                System.out.println("HRÁČ 2 VYHRÁL!");
                break;
            }
        }
    }



}
