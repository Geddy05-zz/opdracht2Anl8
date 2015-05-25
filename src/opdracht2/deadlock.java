/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 *
 * @author geddyS
 */
public class deadlock {
     public static void main(String[] args) {
        // Maak en start thread 1
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    try{
                        String dbUrl = "jdbc:mysql://localhost:3307/infanl08";
                        Connection con = DriverManager.getConnection(dbUrl,"root","root");
                        con.setAutoCommit(false);
//                        con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                        Statement query = con.createStatement();
                        System.out.println("Verbinding gemaakt");
                        String sql = "UPDATE product SET instock= 37";
                        query.execute(sql);
                        
                        Thread.sleep(3000);
                        
                        sql = "UPDATE mutation SET changevalue = 5";
                        query.execute(sql);
//                        con.commit();
                        
                        
                        int wachtTijd = 5;
                        System.out.println(Thread.currentThread().getName() + ": Slaap " +
                                wachtTijd + " sec");
                        
                        // Slaap wachtTijd seconden
                        Thread.sleep(wachtTijd * 1000);
                        con.commit();
                    } catch (Exception e) {
                        System.out.println("THREAD 1: "+e);
                        }
                }
            }
        }, "Thread 1").start();
        
        // Maak en start thread 2. Deze draait tegelijkertijd met thread 1
        new Thread(new Runnable() {

            public void run() {
                while (true) {
                    try {
                        String dbUrl = "jdbc:mysql://localhost:3307/infanl08";
                        Connection con = DriverManager.getConnection(dbUrl,"root","root");
                        con.setAutoCommit(false);
//                        con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                        System.out.println("Verbinding gemaakt");
                        Statement query = con.createStatement();
                        long retryDate = System.currentTimeMillis();
                        Timestamp original = new Timestamp(retryDate);

                        
                        String sql = "UPDATE product SET instock= 35";
                        query.execute(sql);
                        sql= "UPDATE mutation SET changevalue = 5";
                        query.execute(sql);
                        Thread.sleep(4000);
//                        con.commit();
                        
                        int wachtTijd = 5;
                        System.out.println(Thread.currentThread().getName() + ": Slaap " +
                                wachtTijd + " sec");
                        
                        // Slaap wachtTijd seconden
                        Thread.sleep(wachtTijd * 1000);
                    } catch (Exception e) {
                        System.out.println("THREAD 2: "+e);
                    }
                }
            }
        }, "Thread 2").start();
    }
public static void printResult(ResultSet a){
    try{
        while (a.next()){
            String userName = a.getString(1);
            int firstName = a.getInt(2);
            System.out.println(userName+":  "+firstName);
        }
    }catch(Exception e){
    }
    System.out.println("#######################################################");
    }
    
}
