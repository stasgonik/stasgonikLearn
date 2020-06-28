import java.util.Scanner;
import java.sql.*;

public class user {
    private String firstName;
    private String secondName;
    private String lastName;
    private int age;
    private long number;

    public user(String firstName, String secondName, String familyName, int age, long number) {
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

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public static user createUser() {
        Scanner sc = new Scanner(System.in);
        user us = new user();
        validators.NameValidator nameValidator = new validators.NameValidator();
        validators.NumberValidator numberValidator = new validators.NumberValidator();

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
                }
            }
            while (i==3);
            do {
                System.out.println("Enter your phone number (Format: 3806788888888):");
                String temp = sc.nextLine();
                if (numberValidator.validate(temp)) {
                    long tempDouble = Long.parseLong (temp);
                    double check = 100000000000L;
                    if (tempDouble < check) {
                        System.out.println("Need your Ukrainian number in 12 digit format!");
                    }
                    else if (tempDouble > check*10) {
                        System.out.println("Need your Ukrainian number in 12 digit format!");
                    }
                    else {
                        us.setNumber(tempDouble);
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
    static void userToDB(user newUser) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);


            String sql = "INSERT INTO USERS (FIRST_NAME, SECOND_NAME, LAST_NAME, AGE, NUMBER) " +
                    "VALUES (?, ?, ?, ?, ?)";

            st1 = conn.prepareStatement(sql);

            st1.setString(1, newUser.getFirstName());
            st1.setString(2, newUser.getSecondName());
            st1.setString(3, newUser.getLastName());
            st1.setInt(4, newUser.getAge());
            st1.setLong(5, newUser.getNumber());

            st1.execute();

            st1.close();
            conn.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(st1!=null) st1.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static user userFromDB (int usid) {
        user sUser = new user();
        Connection conn = null;
        PreparedStatement st1 = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "SELECT FIRST_NAME, SECOND_NAME, LAST_NAME, AGE, NUMBER FROM USERS WHERE ID=?";

            st1 = conn.prepareStatement(sql);
            st1.setInt(1, usid);
            ResultSet rs = st1.executeQuery();

            String[] stringsTemp = new String[3];
            int[] intsTemp = new int[1];
            long[] longsTemp = new long[1];

            while (rs.next()) {
                String fName = rs.getString("FIRST_NAME");
                stringsTemp[0] = fName;
                String sName = rs.getString("SECOND_NAME");
                stringsTemp[1] = sName;
                String lName = rs.getString("LAST_NAME");
                stringsTemp[2] = lName;
                int age = rs.getInt("AGE");
                intsTemp[0] = age;
                long number = rs.getLong("NUMBER");
                longsTemp[0] = number;
            }
            sUser.setAge(intsTemp[0]);
            sUser.setLastName(stringsTemp[2]);
            sUser.setFirstName(stringsTemp[0]);
            sUser.setSecondName(stringsTemp[1]);
            sUser.setNumber(longsTemp[0]);

            rs.close();

        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(st1!=null) st1.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
        return sUser;
    }
    static void updateFName (int usid, String fName) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "UPDATE USERS " + "SET FIRST_NAME=? WHERE id=?";

            st1 = conn.prepareStatement(sql);
            st1.setString(1, fName);
            st1.setInt(2, usid);
            st1.executeUpdate();

            st1.close();
            conn.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(st1!=null) st1.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static void updateSName (int usid, String sName) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "UPDATE USERS " + "SET SECOND_NAME=? WHERE id=?";

            st1 = conn.prepareStatement(sql);
            st1.setString(1, sName);
            st1.setInt(2, usid);
            st1.executeUpdate();

            st1.close();
            conn.close();

        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(st1!=null) st1.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static void updateLName (int usid, String lName) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "UPDATE USERS " + "SET LAST_NAME=? WHERE id=?";

            st1 = conn.prepareStatement(sql);
            st1.setString(1, lName);
            st1.setInt(2, usid);
            st1.executeUpdate();

            st1.close();
            conn.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (st1 != null) st1.close();
            } catch (SQLException se2) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static void updateAge (int usid, int age) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "UPDATE USERS " + "SET AGE=? WHERE id=?";

            st1 = conn.prepareStatement(sql);
            st1.setInt(1, age);
            st1.setInt(2, usid);
            st1.executeUpdate();

            st1.close();
            conn.close();

        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
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
            }
        }
    }
    static void updateNumber (int usid, long number) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "UPDATE USERS " + "SET NUMBER=? WHERE id=?";

            st1 = conn.prepareStatement(sql);
            st1.setLong(1, number);
            st1.setInt(2, usid);
            st1.executeUpdate();

            st1.close();
            conn.close();

        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (st1 != null) st1.close();
            } catch (SQLException se2) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static void deleteUser (int usid) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String delete = "DELETE FROM USERS " + "WHERE ID = ?";
            st1 = conn.prepareStatement(delete);
            st1.setInt(1, usid);
            st1.executeUpdate();

        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(st1!=null) st1.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static void viewUsers () {
        Connection conn = null;
        Statement stmt = null;
        try {
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
                long number = rs.getLong("NUMBER");


                System.out.println("                                              | _---_ |");
                System.out.print("UserID: " + id);
                System.out.print("; Last name: " + last);
                System.out.print("; First name: " + first);
                System.out.print("; Second name: " + second);
                System.out.print("; Age: " + age);
                System.out.println("; Phone number: " + number);
                System.out.println("");

            }
            rs.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
