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

/**
 *
 * @author geddyS
 */
public class DirtyRead {
    public void main() {
        // Maak en start thread 1
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // Schrijf hier je eigen code
                    // Random wachttijd
                    try{
                        String dbUrl = "jdbc:mysql://localhost:3307/infanl08";
                        Connection con = DriverManager.getConnection(dbUrl,"root","root");
                        con.setAutoCommit(false);
                        con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                        Statement query = con.createStatement();
                        System.out.println("Verbinding gemaakt");
                        String sql = "UPDATE product SET instock = 501 WHERE productname = 'Macbook';";
//                        String sql = "INSERT INTO product VALUES ('Samsung s4',40);";
                        query.execute(sql);
                        
                        
                        // Genereer een getal tussen de 0 t/m 10.
                        int wachtTijd = 5;
                        System.out.println(Thread.currentThread().getName() + ": Slaap " +
                                wachtTijd + " sec");
                        // Slaap wachtTijd seconden
                        Thread.sleep(wachtTijd * 1000);
                        con.commit();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }, "Thread 1").start();
        
        // Maak en start thread 2. Deze draait tegelijkertijd met thread 1
        new Thread(new Runnable() {

            public void run() {
                while (true) {
                    // Schrijf hier je eigen code
                        

                    // Random wachttijd
                    try {
                        Thread.sleep(1000);
                        String dbUrl = "jdbc:mysql://localhost:3307/infanl08";
                        Connection con = DriverManager.getConnection(dbUrl,"root","root");
                        con.setAutoCommit(false);
                        con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                        System.out.println("Verbinding gemaakt");
                        Statement query = con.createStatement();
                        String sql = "Select * from product";
                        ResultSet a = query.executeQuery(sql);
                        
                            while (a.next()) {
                              String userName = a.getString(1);
                              int firstName = a.getInt(2);
                              System.out.println(userName+":  "+firstName);
                              // ... do something with these variables ...
                            }
                        // Genereer een getal tussen de 0 t/m 10.
                        int wachtTijd = 5;
                        System.out.println(Thread.currentThread().getName() + ": Slaap " +
                                wachtTijd + " sec");
                        System.out.println("#######################################################");
                        // Slaap wachtTijd seconden
                        Thread.sleep(wachtTijd * 1000);
                    } catch (Exception e) {
                    }
                }
            }
        }, "Thread 2").start();
    }
    
}
