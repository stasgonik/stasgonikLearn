package com.epam.GSI;

import org.apache.log4j.Logger;

import java.sql.*;

public class loginDB {
    public loginDB() {
    }

    private static final Logger log = Logger.getLogger(loginDB.class);
    private static boolean intToBoolean(int input) {
        if((input==0)||(input==1)) {
            return input!=0;
        }
        else
            {
                log.error("Error occurred when intToBoolean function activated. Input > 1.");
            throw new IllegalArgumentException("Входное значение может быть равно только 0 или 1 !");

        }
    }
    public static void loginToDB (login newLogin) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.info("Adding new login to database.");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);


            String sql = "INSERT INTO LOGIN (LOGIN, PASSWORD, USID) " +
                    "VALUES (?, ?, ?)";

            stmt = conn.prepareStatement(sql);

            stmt.setString(1, newLogin.getLogin());
            stmt.setString(2, newLogin.getPassword());
            stmt.setInt(3, newLogin.getUsid());


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
    public static boolean checkLoginPassword (String login, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean check = false;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "SELECT COUNT(ID) FROM LOGIN WHERE LOGIN=? AND PASSWORD=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, password);
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
    public static boolean checkLogin (String login) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean check = false;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "SELECT COUNT(ID) FROM LOGIN WHERE LOGIN=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);

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
    public static int getID (String login, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int usid = 0;
        try{
            log.debug("Getting user ID from login database.");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "SELECT USID FROM LOGIN WHERE LOGIN=? AND PASSWORD=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                usid = rs.getInt("USID");
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
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred ", se);
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred ", se);
            }
        }
        return usid;
    }
    public static void deleteLogin (int usid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            log.warn("Deleting login of user with ID " + usid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String delete = "DELETE FROM LOGIN " + "WHERE USID = ?";
            stmt = conn.prepareStatement(delete);
            stmt.setInt(1, usid);
            stmt.executeUpdate();

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
    public static void updatePassword (int usid, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.info("Changing password for user with ID " + usid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL, constants.USER, constants.PASS);

            String sql = "UPDATE LOGIN " + "SET PASSWORD=? WHERE USID=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, password);
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
}
