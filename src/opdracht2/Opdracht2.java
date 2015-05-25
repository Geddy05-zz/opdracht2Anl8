/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Opdracht2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Make a choose:");
        System.out.println("    1 for Dirtyread");
        System.out.println("    2 for Phantom");
        System.out.println("    3 for DeadLock");
        System.out.println("    4 for Unrepeatable");
        int i = Integer.parseInt(br.readLine());
        
        if(i==1){
            DirtyRead dr = new DirtyRead();
            dr.main();
        }else if (i ==2){
            Phantom ph = new Phantom();
            ph.main();
        }else if (i == 3){
            Deadlock de = new Deadlock();
            de.main();
        }else if( i == 4){
            Unrepeatable un = new Unrepeatable();
            un.main();
        }else{
            System.out.println("Wrong input: choose a number");
        }
    }
}
