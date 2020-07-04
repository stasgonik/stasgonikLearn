import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;
import java.sql.*;

public class user {
    private static final Logger log = Logger.getLogger(user.class);
    private String firstName;
    private String secondName;
    private String lastName;
    private int age;
    private String number;

    public user(String firstName, String secondName, String familyName, int age, String number) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = familyName;
        this.age = age;
        this.number = number;
    }

    public user() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @NotNull
    public static user createUser() {
        log.info("Starting to create new user.");
        Scanner sc = new Scanner(System.in);
        user us = new user();
        validators.NameValidator nameValidator = new validators.NameValidator();
        validators.NumberValidator numberValidator = new validators.NumberValidator();
        validators.PhoneValidator phoneValidator = new validators.PhoneValidator();

        int i = 0;
        try {
            do {
                System.out.println("Enter first name:");
                String temp = sc.nextLine();
                if (nameValidator.validate(temp)) {
                    us.setFirstName(temp);
                    i++;
                }
                else {
                    System.out.println("Incorrect name format. Use only latin letters or {-} !");
                    log.warn("Incorrect name format set.");
                }
            }
            while (i==0);
            do {
                System.out.println("Enter second name:");
                String temp = sc.nextLine();
                if (nameValidator.validate(temp)) {
                    us.setSecondName(temp);
                    i++;
                }
                else {
                    System.out.println("Incorrect name format. Use only latin letters or {-} !");
                    log.warn("Incorrect name format set.");
                }
            }
            while (i==1);
            do {
                System.out.println("Enter last name:");
                String temp = sc.nextLine();
                if (nameValidator.validate(temp)) {
                    us.setLastName(temp);
                    i++;
                }
                else {
                    System.out.println("Incorrect name format. Use only latin letters or {-} !");
                    log.warn("Incorrect name format set.");
                }
            }
            while (i==2);
            do {
                System.out.println("Enter your age:");
                String temp = sc.nextLine();
                if (numberValidator.validate(temp)) {
                    int tempInt = Integer.parseInt (temp);
                    us.setAge(tempInt);
                    i++;
                }
                else {
                    System.out.println("Incorrect age format. Use only numbers!");
                    log.warn("Incorrect age format set.");
                }
            }
            while (i==3);
            do {
                System.out.println("Enter your phone number with country code:");
                System.out.println("Preferred format: +38 123 123-4567");
                String temp = sc.nextLine();
                if (phoneValidator.validate(temp)) {
                    if (userDB.checkNumber(temp)) {
                        System.out.println("We already have this number in database.");
                        System.out.println("Please, use another number for new user.");
                        log.warn("Attempt to set phone number, that already in database.");
                    }
                    else {
                        us.setNumber(temp);
                        i++;
                    }
                }
                else {
                    System.out.println("Incorrect number format. Please, repeat again.");
                    log.warn("Incorrect phone number format set.");
                }
            }
            while (i==4);
        }
        catch (Exception ex) {
            ex.getMessage();
            log.error("Exception occurred.");
            log.error(ex.getMessage(), ex);
        }
        userDB.userToDB(us);
        int usid = userDB.getUSID(us);
        login usLogin = login.createLogin(usid);
        loginDB.loginToDB(usLogin);
        return us;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + secondName + " , age: " + age + ", phone number: " + number ;
    }
}
/*class userValidatorName {
    private Pattern pattern;
    private Matcher matcher;

    private static final String NAMES_PATTERN =
            "^[_A-Za-z-\\+]+$";
    public userValidatorName() {
        pattern = Pattern.compile(NAMES_PATTERN);
    }
    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);

        return matcher.matches();
    }
}
class userNumberValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String NAMES_PATTERN =
            "^[_0-9\\+]+$";
    public userNumberValidator() {
        pattern = Pattern.compile(NAMES_PATTERN);
    }
    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);

        return matcher.matches();
    }
}
class userPhoneValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String NAMES_PATTERN =
            "^[_0-9\\+}]+$";
    public userPhoneValidator() {
        pattern = Pattern.compile(NAMES_PATTERN);
    }
    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);

        return matcher.matches();
    }
}*/
class userDB {
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
    static void userToDB(user newUser) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.info("Adding new user to database.");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

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
            log.error("Exception occurred.");
            log.error(se.getMessage(), se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred.");
                log.error(se.getMessage(), se);
            }
        }
    }
    @NotNull
    static user userFromDB (int usid) {
        user searchUser = new user();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            log.debug("Retrieving user from database. User ID is " + usid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

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
            log.error("Exception occurred.");
            log.error(se.getMessage(), se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred.");
                log.error(se.getMessage(), se);
            }
        }
        return searchUser;
    }
    static void updateFName (int usid, String fName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.info("Updating first name of user. User ID is " + usid +".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "UPDATE USERS " + "SET FIRST_NAME=? WHERE id=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fName);
            stmt.setInt(2, usid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred.");
            log.error(se.getMessage(), se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred.");
                log.error(se.getMessage(), se);
            }
        }
    }
    static void updateSName (int usid, String sName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.info("Updating second name of user. User ID is " + usid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "UPDATE USERS " + "SET SECOND_NAME=? WHERE id=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, sName);
            stmt.setInt(2, usid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred.");
            log.error(se.getMessage(), se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred.");
                log.error(se.getMessage(), se);
            }
        }
    }
    static void updateLName (int usid, String lName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.info("Updating last name of user. User ID is " + usid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "UPDATE USERS " + "SET LAST_NAME=? WHERE id=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, lName);
            stmt.setInt(2, usid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred.");
            log.error(se.getMessage(), se);
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred.");
                log.error(se.getMessage(), se);
            }
        }
    }
    static void updateAge (int usid, int age) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.info("Updating age od user. User ID is " + usid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "UPDATE USERS " + "SET AGE=? WHERE id=?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, age);
            stmt.setInt(2, usid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred.");
            log.error(se.getMessage(), se);
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
                log.error("Exception occurred.");
                log.error(se.getMessage(), se);
            }
        }
    }
    static void updateNumber (int usid, String number) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.info("Updating phone number of user. User ID is " + usid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "UPDATE USERS " + "SET NUMBER=? WHERE id=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, number);
            stmt.setInt(2, usid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred.");
            log.error(se.getMessage(), se);
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred.");
                log.error(se.getMessage(), se);
            }
        }
    }
    static void deleteUser (int usid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            log.warn("Deleting user from database. User ID is " + usid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String delete = "DELETE FROM USERS " + "WHERE ID = ?";
            stmt = conn.prepareStatement(delete);
            stmt.setInt(1, usid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred.");
            log.error(se.getMessage(), se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred.");
                log.error(se.getMessage(), se);
            }
        }
    }
    static void viewUsers () {
        Connection conn = null;
        Statement stmt = null;
        try {
            log.warn("View users function activated.");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

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
            log.error("Exception occurred.");
            log.error(se.getMessage(), se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred.");
                log.error(se.getMessage(), se);
            }
        }
    }
    static int getUSID (user searchUser) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int usid = 0;
        try{
            log.debug("Retrieving user ID from database.");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

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
            log.error("Exception occurred.");
            log.error(se.getMessage(), se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred.");
                log.error(se.getMessage(), se);
            }
        }
        return usid;
    }
    static boolean checkNumber (String number) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean check = false;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

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
            log.error("Exception occurred.");
            log.error(se.getMessage(), se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred.");
                log.error(se.getMessage(), se);
            }
        }
        return check;
    }
}

