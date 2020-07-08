package com.epam.GSI;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class accountDB {
    private static final Logger log = Logger.getLogger(accountDB.class);
    static void accountToDB(account newAccount) {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        try {
            log.info("Starting to add new account to database.");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "INSERT INTO ACCOUNT (USID, CRID, MONEY, LOAN) " +
                    "VALUES (?, ?, ?, ?)";
            String sqlSearch1 = "SELECT ID FROM USERS WHERE FIRST_NAME=? AND SECOND_NAME=? AND LAST_NAME=?" +
                    " AND AGE=?";
            String sqlSearch2 = "SELECT ID FROM CURRENCY WHERE NAME=?";

            stmt1 = conn.prepareStatement(sql);

            stmt2 = conn.prepareStatement(sqlSearch1);

            stmt2.setString(1, newAccount.getMaster().getFirstName());
            stmt2.setString(2, newAccount.getMaster().getSecondName());
            stmt2.setString(3, newAccount.getMaster().getLastName());
            stmt2.setInt(4, newAccount.getMaster().getAge());

            ResultSet rs1 = stmt2.executeQuery();
            int USID = 0;
            while (rs1.next()) {
                USID = rs1.getInt("id");
            }

            stmt1.setInt(1, USID);

            stmt2 = conn.prepareStatement(sqlSearch2);

            stmt2.setString(1, newAccount.getAccountCurrency().getName());

            ResultSet rs2 = stmt2.executeQuery();
            int CRID = 0;
            while (rs2.next()) {
                CRID = rs2.getInt("id");
            }
            stmt1.setInt(2, CRID);

            stmt1.setDouble(3, newAccount.getMoney());
            stmt1.setDouble(4, newAccount.getLoan());

            stmt1.execute();

            rs1.close();
            rs2.close();

            stmt1.close();
            stmt2.close();
            conn.close();
        } catch (Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
        } finally {
            try {
                if (stmt1 != null) stmt1.close();
                if (stmt2 != null) stmt2.close();
            } catch (SQLException ignored) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred " , se);
            }
        }
    }
    @NotNull
    static account accountFromDB (int acid) {
        account search = new account();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            log.debug("Retrieving of account from database. Account ID is " + acid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "SELECT USID, CRID, MONEY, LOAN FROM ACCOUNT WHERE ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, acid);
            ResultSet rs = stmt.executeQuery();

            int[] intsTemp = new int[2];
            double[] doublesTemp = new double[2];

            while(rs.next()) {
                int usid = rs.getInt("USID");
                intsTemp[0] = usid;
                int crid = rs.getInt("CRID");
                intsTemp[1] = crid;
                double money = rs.getDouble("MONEY");
                doublesTemp[0] = money;
                double loan = rs.getDouble("LOAN");
                doublesTemp[1] = loan;
            }
            search.setMoney(doublesTemp[0]);
            search.setLoan(doublesTemp[1]);
            search.setMaster(userDB.userFromDB(intsTemp[0]));
            search.setAccountCurrency(currencyDB.currencyFromDB(intsTemp[1]));

            rs.close();
            stmt.close();
            conn.close();

        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred " , se);
            }
        }
        return search;
    }
    static int usidFromDB (int acid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int usid = 0;
        try {
            log.debug("Retrieving user ID from account database. Account ID is " + acid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "SELECT USID FROM ACCOUNT WHERE ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, acid);
            ResultSet rs = stmt.executeQuery();

            int[] intsTemp = new int[1];

            while(rs.next()) {
                usid = rs.getInt("USID");
                intsTemp[0] = usid;
            }
            usid = intsTemp[0];

            rs.close();
            stmt.close();
            conn.close();

        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred " , se);
            }
        }
        return usid;
    }
    static void updateMoney (int acid, double newMoney) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.debug("Updating value of money in account database. Account ID is " + acid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "UPDATE ACCOUNT " + "SET MONEY=? WHERE ID=?";

            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, newMoney);
            stmt.setInt(2, acid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred " , se);
            }
        }
    }
    static void deleteAccount (int acid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            log.info("Attempt to delete account. Account ID is " + acid +".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String delete = "DELETE FROM ACCOUNT " + "WHERE ID=?";
            stmt = conn.prepareStatement(delete);
            stmt.setInt(1, acid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred " , se);
            }
        }
    }
    static void updateLoan (int acid, double newLoan) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.debug("Updating value of loan in account database. Account ID is " + acid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "UPDATE ACCOUNT " + "SET LOAN=? WHERE ID=?";

            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, newLoan);
            stmt.setInt(2, acid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred " , se);
            }
        }
    }
    static void viewAccounts () {
        Connection conn = null;
        Statement stmt = null;
        try {
            log.warn("Viewing all accounts function activated!");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            stmt = conn.createStatement();
            String sql = "SELECT ID, LAST_NAME, FIRST_NAME, SECOND_NAME, AGE," +
                    " NUMBER, MONEY, LOAN, NAME, VALUE, CURRENT_SUM FROM ACC_VIEW WHERE NOT ID=42";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int id  = rs.getInt("ID");
                String last = rs.getString("LAST_NAME");
                String first = rs.getString("FIRST_NAME");
                String second = rs.getString("SECOND_NAME");
                int age = rs.getInt("AGE");
                String number = rs.getString("NUMBER");
                double money = rs.getDouble("MONEY");
                double loan = rs.getDouble("LOAN");
                String curName = rs.getString("NAME");
                double value = rs.getDouble("VALUE");
                double sumBase = rs.getDouble("CURRENT_SUM");

                System.out.println("                                              | _---_ |");
                System.out.print("AccountID: " + id);
                System.out.print("; Last name: " + last);
                System.out.print("; First name: " + first);
                System.out.print("; Second name: " + second);
                System.out.print("; Age: " + age);
                System.out.println("; Phone number: " + number);
                System.out.printf("     Money: %10.2f" , money);
                System.out.printf("; Loan:  %10.2f", loan);
                System.out.print("; Currency name: " + curName);
                System.out.printf("; Currency value: %6.2f", value);
                System.out.printf("; Sum in %s: %10.2f%n", currencyDB.currencyFromDB(1).getName() ,sumBase);
                System.out.println(" ");

            }

            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred " , se);
            }
        }
    }
    @NotNull
    static int[] searchUserAccounts (int usid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int[] acids = new int[20];
        try {
            log.info("Viewing all accounts of selected user. User ID is " + usid +".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "SELECT ID FROM ACCOUNT WHERE USID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usid);
            ResultSet rs = stmt.executeQuery();
            int i = 0;

            while(rs.next()) {
                int acid = rs.getInt("ID");
                acids[i] = acid;
                i++;
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred " , se);
        }
        }
        return acids;
    }
    static boolean countAccountCheck (int usid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean checkLess20 = true;
        int count = 0;
        try {

            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "SELECT COUNT(ID) FROM ACCOUNT WHERE USID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usid);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                count = rs.getInt("COUNT(ID)");
                System.out.println(count);
            }
            checkLess20 = count < 20;
            if (!checkLess20) {
                log.warn("User with ID " + usid + " already have 20 accounts.");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred ", se);

            }

        }
        return checkLess20;
    }
}
