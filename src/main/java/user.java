import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;



public class user {
    private String firstName;
    private String secondName;
    private String familyName;
    private int age;


    public user(String firstName, String secondName, String familyName, int age) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.familyName = familyName;
        this.age = age;
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

    public static user createUser() {
        Scanner sc = new Scanner(System.in);
        user us = new user();
        userValidatorName validator = new userValidatorName();
        userNumberValidator validator2 = new userNumberValidator();

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

        }
        catch (Exception ex) {
            ex.getMessage();
        }
        userDB.userToDB(us);
        return us;
    }

    @Override
    public String toString() {
        return familyName + " " + firstName + " " + secondName + " , age: " + age
                ;
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
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connected database successfully...");

            // STEP 3: Execute a query

            String sql = "INSERT INTO USERS (FIRST_NAME, SECOND_NAME, LAST_NAME, AGE) " +
                    "VALUES (?, ?, ?, ?)";

            st1 = conn.prepareStatement(sql);
            st1.setString(1, newUser.getFirstName());
            st1.setString(2, newUser.getSecondName());
            st1.setString(3, newUser.getFamilyName());
            st1.setInt(4, newUser.getAge());

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
        System.out.println("Goodbye!");

    }
}
