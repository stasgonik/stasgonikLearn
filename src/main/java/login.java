import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Scanner;

public class login {
    private String login;
    private String password;
    private int usid;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUsid() {
        return usid;
    }

    public void setUsid(int usid) {
        this.usid = usid;
    }

    public login() {
    }

    public login(String login, String password, int usid) {
        this.login = login;
        this.password = password;
        this.usid = usid;
    }

    @NotNull
    public static login createLogin(int usid) {
        Scanner sc = new Scanner(System.in);
        validators.LoginValidator loginValidator = new validators.LoginValidator();
        int i = 0;
        login newUser = new login();

        try {
            do {
                System.out.println("Enter login:");
                String temp = sc.nextLine();
                if (loginValidator.validate(temp)) {
                    if (loginDB.checkLogin(temp)) {
                        System.out.println("User with this login already exist.");
                        System.out.println("Please, choose another login.");
                    }
                    else {
                        newUser.setLogin(temp);
                        i++;
                    }
                }
                else {
                    System.out.println("Incorrect login format. Use only latin letters or numbers!");
                }
            }
            while (i==0);
            do {
                System.out.println("Enter password:");
                String temp = sc.nextLine();
                if (loginValidator.validate(temp)) {
                    newUser.setPassword(temp);
                    i++;
                }
                else {
                    System.out.println("Incorrect password format. Use only latin letters or numbers!");
                }
            }
            while (i==1);
        }
        catch (Exception ex) {
            ex.getMessage();
        }
        newUser.setUsid(usid);
        return newUser;
    }
}
class loginDB {
    private static boolean intToBoolean(int input) {
        if((input==0)||(input==1)) {
            return input!=0;
        }else {
            throw new IllegalArgumentException("Входное значение может быть равно только 0 или 1 !");
        }
    }
    static void loginToDB (login newLogin) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);


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
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static boolean checkLoginPassword (String login, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean check = false;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

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
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
        return check;
    }
    static boolean checkLogin (String login) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean check = false;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

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
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
        return check;
    }
    static int getID (String login, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int usid = 0;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "SELECT USID FROM LOGIN WHERE LOGIN=? AND PASSWORD=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(1, password);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                usid = rs.getInt("USID");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
        } finally {
            if(stmt!=null) stmt.close();
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
        return usid;
    }
    static void deleteLogin (int usid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String delete = "DELETE FROM LOGIN " + "WHERE USID = ?";
            stmt = conn.prepareStatement(delete);
            stmt.setInt(1, usid);
            stmt.executeUpdate();

        } catch(Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static void updatePassword (int usid, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "UPDATE LOGIN " + "SET PASSWORD=? WHERE USID=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, password);
            stmt.setInt(2, usid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
}


