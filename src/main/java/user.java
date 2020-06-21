import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;



public class user {
    private String firstName;
    private String secondName;
    private String familyName;
    private int age;
    private double number;

    public user(String firstName, String secondName, String familyName, int age, double number) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.familyName = familyName;
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

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public static user createUser() {
        Scanner sc = new Scanner(System.in);
        user us = new user();
        userValidatorName validator = new userValidatorName();
        userNumberValidator validator2 = new userNumberValidator();
        userPhoneValidator validator3 = new userPhoneValidator();

        int i = 0;
        try {
            do {
                System.out.println("Enter first name:");
                String temp = sc.nextLine();
                if (validator.validate(temp)) {
                    us.setFirstName(temp);
                    i++;
                }
                else {
                    System.out.println("Incorrect name format. Use only latin letters or {-} !");
                }
            }
            while (i==0);
            do {
                System.out.println("Enter second name:");
                String temp = sc.nextLine();
                if (validator.validate(temp)) {
                    us.setSecondName(temp);
                    i++;
                }
                else {
                    System.out.println("Incorrect name format. Use only latin letters or {-} !");
                }
            }
            while (i==1);
            do {
                System.out.println("Enter family name:");
                String temp = sc.nextLine();
                if (validator.validate(temp)) {
                    us.setFamilyName(temp);
                    i++;
                }
                else {
                    System.out.println("Incorrect name format. Use only latin letters or {-} !");
                }
            }
            while (i==2);
            do {
                System.out.println("Enter your age:");
                String temp = sc.nextLine();
                if (validator2.validate(temp)) {
                    int tempInt = Integer.parseInt (temp);
                    us.setAge(tempInt);
                    i++;
                }
                else {
                    System.out.println("Incorrect age format. Use only numbers!");
                }
            }
            while (i==3);
            do {
                System.out.println("Enter your phone number (Format: 3806788888888):");
                String temp = sc.nextLine();
                if (validator3.validate(temp)) {
                    double tempInt = Double.parseDouble (temp);
                    double check = 100000000000L;
                    if (tempInt < check) {
                        System.out.println("Need your Ukrainian number in 12 digit format!");
                    }
                    else if (tempInt/10 > check) {
                        System.out.println("Need your Ukrainian number in 12 digit format!");
                    }
                    else {
                        us.setNumber(tempInt);
                        i++;
                    }

                }
                else {
                    System.out.println("Incorrect number format. Use only numbers!");
                }
            }
            while (i==4);

        }
        catch (Exception ex) {
            ex.getMessage();
        }
        userDB.userToDB(us);
        return us;
    }

    @Override
    public String toString() {
        return familyName + " " + firstName + " " + secondName + " , age: " + age +
             ", phone number: " + number ;
    }
}
class userValidatorName {
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
}
class userDB {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test2";


    static final String USER = "sa";
    static final String PASS = "";

    static void userToDB(user newUser) {
        Connection conn = null;
        PreparedStatement st1 = null;

        try{
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query

            String sql = "INSERT INTO USERS (FIRST_NAME, SECOND_NAME, LAST_NAME, AGE, NUMBER) " +
                    "VALUES (?, ?, ?, ?, ?)";

            st1 = conn.prepareStatement(sql);

            st1.setString(1, newUser.getFirstName());
            st1.setString(2, newUser.getSecondName());
            st1.setString(3, newUser.getFamilyName());
            st1.setInt(4, newUser.getAge());
            st1.setDouble(5, newUser.getNumber());

            st1.execute();

            // STEP 4: Clean-up environment
            st1.close();
            conn.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if(st1!=null) st1.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
    }
    static user userFromDB (int usid) {
        user sUser = new user();
        Connection conn = null;
        PreparedStatement st1 = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
            String sql = "SELECT FIRST_NAME, SECOND_NAME, LAST_NAME, AGE, NUMBER FROM USERS WHERE ID=?";
            st1 = conn.prepareStatement(sql);
            st1.setInt(1, usid);
            ResultSet rs = st1.executeQuery();


            String[] stringsTemp = new String[3];
            int[] intsTemp = new int[1];
            double[] doublesTemp = new double[1];

            // STEP 4: Extract data from result set

            while (rs.next()) {
                String fName = rs.getString("FIRST_NAME");
                stringsTemp[0] = fName;
                String sName = rs.getString("SECOND_NAME");
                stringsTemp[1] = sName;
                String lName = rs.getString("LAST_NAME");
                stringsTemp[2] = lName;
                int age = rs.getInt("AGE");
                intsTemp[0] = age;
                double number = rs.getDouble("NUMBER");
                doublesTemp[0] = number;
            }
            sUser.setAge(intsTemp[0]);
            sUser.setFamilyName(stringsTemp[2]);
            sUser.setFirstName(stringsTemp[0]);
            sUser.setSecondName(stringsTemp[1]);
            sUser.setNumber(doublesTemp[0]);

            // STEP 5: Clean-up environment


            rs.close();

        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if(st1!=null) st1.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
        return sUser;
    }
    static void updateFName (int usid, String fName) {
        Connection conn = null;
        PreparedStatement st1 = null;

        try{
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query

            String sql = "UPDATE USERS " + "SET FIRST_NAME=? WHERE id=?";

            st1 = conn.prepareStatement(sql);
            st1.setString(1, fName);
            st1.setInt(2, usid);
            st1.executeUpdate();

            // STEP 4: Clean-up environment
            st1.close();
            conn.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if(st1!=null) st1.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
    }
    static void updateSName (int usid, String sName) {
        Connection conn = null;
        PreparedStatement st1 = null;

        try{
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query

            String sql = "UPDATE USERS " + "SET SECOND_NAME=? WHERE id=?";

            st1 = conn.prepareStatement(sql);
            st1.setString(1, sName);
            st1.setInt(2, usid);
            st1.executeUpdate();

            // STEP 4: Clean-up environment
            st1.close();
            conn.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if(st1!=null) st1.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try

    }
    static void updateLName (int usid, String lName) {
        Connection conn = null;
        PreparedStatement st1 = null;


        try{
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection

            conn = DriverManager.getConnection(DB_URL,USER,PASS);


            // STEP 3: Execute a query

            String sql = "UPDATE USERS " + "SET LAST_NAME=? WHERE id=?";

            st1 = conn.prepareStatement(sql);
            st1.setString(1, lName);
            st1.setInt(2, usid);
            st1.executeUpdate();

            // STEP 4: Clean-up environment
            st1.close();
            conn.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if(st1!=null) st1.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try

    }
    static void updateAge (int usid, int age) {
        Connection conn = null;
        PreparedStatement st1 = null;


        try{
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query

            String sql = "UPDATE USERS " + "SET AGE=? WHERE id=?";

            st1 = conn.prepareStatement(sql);
            st1.setInt(1, age);
            st1.setInt(2, usid);
            st1.executeUpdate();

            // STEP 4: Clean-up environment
            st1.close();
            conn.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if(st1!=null) st1.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
    }
    static void updateNumber (int usid, double number) {
        Connection conn = null;
        PreparedStatement st1 = null;

        try{
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query

            String sql = "UPDATE USERS " + "SET NUMBER=? WHERE id=?";

            st1 = conn.prepareStatement(sql);
            st1.setDouble(1, number);
            st1.setInt(2, usid);
            st1.executeUpdate();

            // STEP 4: Clean-up environment
            st1.close();
            conn.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if(st1!=null) st1.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
    }
    static void deleteUser (int usid) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query

            String delete = "DELETE FROM USERS " + "WHERE ID = ?";
            st1 = conn.prepareStatement(delete);
            st1.setInt(1, usid);
            st1.executeUpdate();
            // STEP 5: Clean-up environment

        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if(st1!=null) st1.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
    }
}
