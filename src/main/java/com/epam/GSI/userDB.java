package com.epam.GSI;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class userDB {
    public userDB() {
    }

    private static final Logger log = Logger.getLogger(userDB.class);
    private static boolean intToBoolean(int input) {
        if((input==0)||(input==1)) {
            return input!=0;
        }else
            {
                log.error("Error occurred when intToBoolean function activated. Input > 1.");
            throw new IllegalArgumentException("Входное значение может быть равно только 0 или 1 !");
        }
    }
    public static void userToDB(user newUser) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.info("Adding new user to database.");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "INSERT INTO USERS (FIRST_NAME, SECOND_NAME, LAST_NAME, AGE, NUMBER) " +
                    "VALUES (?, ?, ?, ?, ?)";

            stmt = conn.prepareStatement(sql);

            stmt.setString(1, newUser.getFirstName());
            stmt.setString(2, newUser.getSecondName());
            stmt.setString(3, newUser.getLastName());
            stmt.setInt(4, newUser.getAge());
            stmt.setString(5, newUser.getNumber());

            stmt.execute();

            stmt.close();
            conn.close();

        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred ", se);
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
    }
    @NotNull
    public static user userFromDB (int usid) {
        user searchUser = new user();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            log.debug("Retrieving user from database. User ID is " + usid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "SELECT FIRST_NAME, SECOND_NAME, LAST_NAME, AGE, NUMBER FROM USERS WHERE ID=?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usid);
            ResultSet rs = stmt.executeQuery();

            String[] stringsTemp = new String[4];
            int[] intsTemp = new int[1];

            while (rs.next()) {
                String fName = rs.getString("FIRST_NAME");
                stringsTemp[0] = fName;
                String sName = rs.getString("SECOND_NAME");
                stringsTemp[1] = sName;
                String lName = rs.getString("LAST_NAME");
                stringsTemp[2] = lName;
                int age = rs.getInt("AGE");
                intsTemp[0] = age;
                String number = rs.getString("NUMBER");
                stringsTemp[3] = number;
            }
            searchUser.setAge(intsTemp[0]);
            searchUser.setLastName(stringsTemp[2]);
            searchUser.setFirstName(stringsTemp[0]);
            searchUser.setSecondName(stringsTemp[1]);
            searchUser.setNumber(stringsTemp[3]);

            rs.close();
            stmt.close();
            conn.close();

        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred ", se);
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
        return searchUser;
    }
    public static void updateFName (int usid, String fName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.info("Updating first name of user. User ID is " + usid +".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "UPDATE USERS " + "SET FIRST_NAME=? WHERE id=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fName);
            stmt.setInt(2, usid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred ", se);
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
    }
    public static void updateSName (int usid, String sName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.info("Updating second name of user. User ID is " + usid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "UPDATE USERS " + "SET SECOND_NAME=? WHERE id=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, sName);
            stmt.setInt(2, usid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred ", se);
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
    }
    public static void updateLName (int usid, String lName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.info("Updating last name of user. User ID is " + usid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "UPDATE USERS " + "SET LAST_NAME=? WHERE id=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, lName);
            stmt.setInt(2, usid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred ", se);
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred ", se);
            }
        }
    }
    public static void updateAge (int usid, int age) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.info("Updating age od user. User ID is " + usid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "UPDATE USERS " + "SET AGE=? WHERE id=?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, age);
            stmt.setInt(2, usid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred ", se);
        } finally {
            // finally block used to close resources
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
    }
    /*public static void updateNumber (int usid, String number) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.info("Updating phone number of user. User ID is " + usid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "UPDATE USERS " + "SET NUMBER=? WHERE id=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, number);
            stmt.setInt(2, usid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred ", se);
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred ", se);
            }
        }
    }*/
    public static void deleteUser (int usid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            log.warn("Deleting user from database. User ID is " + usid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String delete = "DELETE FROM USERS " + "WHERE ID = ?";
            stmt = conn.prepareStatement(delete);
            stmt.setInt(1, usid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred ", se);
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
    }
    /*public static void viewUsers () {
        Connection conn = null;
        Statement stmt = null;
        try {
            log.warn("View users function activated.");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            stmt = conn.createStatement();
            String sql = "SELECT ID, LAST_NAME, FIRST_NAME, SECOND_NAME, AGE, NUMBER FROM USERS WHERE NOT ID=42";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int id  = rs.getInt("ID");
                String last = rs.getString("LAST_NAME");
                String first = rs.getString("FIRST_NAME");
                String second = rs.getString("SECOND_NAME");
                int age = rs.getInt("AGE");
                String number = rs.getString("NUMBER");

                System.out.println("                                              | _---_ |");
                System.out.print("UserID: " + id);
                System.out.print("; Last name: " + last);
                System.out.print("; First name: " + first);
                System.out.print("; Second name: " + second);
                System.out.print("; Age: " + age);
                System.out.println("; Phone number: " + number);
                System.out.println(" ");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred ", se);
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
    }*/
    public static int getUSID (user searchUser) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int usid = 0;
        try{
            log.debug("Retrieving user ID from database.");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "SELECT ID FROM USERS WHERE FIRST_NAME=? AND SECOND_NAME=?" +
                    " AND LAST_NAME=? AND AGE=? AND NUMBER=?";

            stmt = conn.prepareStatement(sql);

            stmt.setString(1, searchUser.getFirstName());
            stmt.setString(2, searchUser.getSecondName());
            stmt.setString(3, searchUser.getLastName());
            stmt.setInt(4, searchUser.getAge());
            stmt.setString(5, searchUser.getNumber());

            int[] intsTemp = new int[1];

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                intsTemp[0] = id;
            }
            usid = intsTemp[0];
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred ", se);
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
        return usid;
    }
    public static boolean checkNumber (String number) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean check = false;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "SELECT COUNT(ID) FROM USERS WHERE NUMBER=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, number);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("COUNT(ID)");
                check = intToBoolean(id);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred ", se);
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
        return check;
    }
}
