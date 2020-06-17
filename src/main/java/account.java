import java.awt.*;
import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class account {


    private user master;
    private currency accountCurrency;
    private double money;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }



    public user getMaster() {
        return master;
    }

    public void setMaster(user master) {
        this.master = master;
    }

    public currency getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(currency accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    public account(user master, currency accountCurrency, double money) {

        this.master = master;
        this.accountCurrency = accountCurrency;
        this.money = money;
    }


    public account() {
    }

    public double currentSum() {
        return money * accountCurrency.getValue();

    }
    public static account createAccount() {
        System.out.println("Starting creation of new account.");
        Scanner sc = new Scanner(System.in);
        account Cr = new account();
        try {
            currency cur = currency.chooseCurrency();
            user user1 = user.createUser();
            numberValidator validator = new numberValidator();
            int i =0;
            do {
                System.out.println("Enter sum of money for test account:");
                String temp = sc.nextLine();
                if (validator.validate(temp)) {
                    int tempInt = Integer.parseInt (temp);
                    Cr.setMoney(tempInt);
                    i++;
                }
                else {
                    System.out.println("Incorrect sum format. Use only numbers!");
                }
            }
            while (i==0);
            Cr.setAccountCurrency(cur);
            Cr.setMaster(user1);
            accountDB.accountToDB(Cr);
        }
        catch (Exception ex) {
            ex.getMessage();
        }

        return Cr;
    }

    @Override
    public String toString() {
        return "Master of account: "  + master +
                ", currency of account is " + accountCurrency +
                ", sum of money on account = " + money + ""
                + accountCurrency.getName() + " ( In UAH : " +
                currentSum() + " )";
    }
}
class numberValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String NAMES_PATTERN =
            "^[_0-9\\+]+$";
    public numberValidator() {
        pattern = Pattern.compile(NAMES_PATTERN);
    }
    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);

        return matcher.matches();
    }
}
class accountDB {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test2";


    static final String USER = "sa";
    static final String PASS = "";

    static void accountToDB(account newAccount) {
        Connection conn = null;
        PreparedStatement st1 = null;
        PreparedStatement st2 = null;
        System.out.println("Start saving account to DB!");

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            // STEP 3: Execute a query

            String sql = "INSERT INTO ACCOUNT (USID, CRID, MONEY, SUM) " +
                    "VALUES (?, ?, ?, ?)";
            String sqlSearch1 = "SELECT ID FROM USERS WHERE FIRST_NAME=? AND SECOND_NAME=? AND LAST_NAME=?" +
                    " AND AGE=?";
            String sqlSearch2 = "SELECT ID FROM CURRENCY WHERE NAME=?";

            st1 = conn.prepareStatement(sql);

            st2 = conn.prepareStatement(sqlSearch1);

            st2.setString(1, newAccount.getMaster().getFirstName());
            st2.setString(2, newAccount.getMaster().getSecondName());
            st2.setString(3, newAccount.getMaster().getFamilyName());
            st2.setInt(4, newAccount.getMaster().getAge());

            ResultSet rs1 = st2.executeQuery();
            int USID = 0;
            while (rs1.next()) {
                USID = rs1.getInt("id");
            }

            st1.setInt(1, USID);

            st2 = conn.prepareStatement(sqlSearch2);

            st2.setString(1, newAccount.getAccountCurrency().getName());

            ResultSet rs2 = st2.executeQuery();
            int CRID = 0;
            while (rs2.next()) {
                CRID = rs2.getInt("id");
            }

            st1.setInt(2, CRID);

            st1.setDouble(3, newAccount.getMoney());
            st1.setDouble(4, newAccount.currentSum());

            st1.execute();

            // STEP 4: Clean-up environment
            st1.close();
            st2.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (st1 != null) st1.close();
                if (st2 != null) st2.close();
            } catch (SQLException se2) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
        System.out.println("Attempt end!");
    }
    static void accountFromDB (int acid) {
        //account search =new account();
        //user uSearch = new user();
        //currency cSearch = new currency();
        Connection conn = null;
        PreparedStatement st1 = null;
        PreparedStatement st2 = null;
        PreparedStatement st3 = null;
        //Statement stmt = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
            System.out.println("Connected database successfully...");
            String sql = "SELECT USID, CRID, MONEY FROM ACCOUNT WHERE ID=?";
            //stmt = conn.createStatement();
            //ResultSet rs = stmt.executeQuery(sql);
            st1 = conn.prepareStatement(sql);
            st1.setInt(1, acid);
            ResultSet rs = st1.executeQuery();

            

            // STEP 4: Extract data from result set
            while(rs.next()) {
                // Retrieve by column name

                int usid = rs.getInt("USID");
                int crid = rs.getInt("CRID");
                double money = rs.getDouble("MONEY");

                String sqlUser = "SELECT FIRST_NAME, SECOND_NAME, LAST_NAME, AGE, NUMBER FROM USERS WHERE ID=?";
                st3 = conn.prepareStatement(sqlUser);
                st3.setInt(1, crid);
                ResultSet rsU = st3.executeQuery();
                //String fName = null;
                //String sName = null;
                //String lName = null;
                //int age = 0;
                //double number = 0;
                while (rsU.next()) {
                    String fName = rsU.getString("FIRST_NAME");
                    String sName = rsU.getString("SECOND_NAME");
                    String lName = rsU.getString("LAST_NAME");
                    int age = rsU.getInt("AGE");
                    double number = rsU.getDouble("NUMBER");

                    System.out.println("Master of account: "  + lName + " " + fName + " " + sName + " , age: " + age +
                            ", phone number: " + number);


                }


                String sqlCurrency = "SELECT NAME, VALUE FROM CURRENCY WHERE ID=?";
                st2 = conn.prepareStatement(sqlCurrency);
                st2.setInt(1, usid);
                ResultSet rsC = st2.executeQuery();
                //String curName = null;
                //double value = 0;
                while (rsC.next()) {
                    String curName = rsC.getString("NAME");
                    double value = rsC.getDouble("VALUE");
                    double currentSum = value* money;
                    System.out.println(", currency of account is " + curName + " (" +
                            "id of currency is " + crid + ", value of this currency is " + value + ")" +
                            ", sum of money on account = " + money + "" + curName + " ( In UAH : " + currentSum + " )");
                }


                //search.setMoney(money);
                //cSearch = new currency(crid, curName, value);
                //search.setAccountCurrency(cSearch);
                //uSearch = new user(fName, sName, lName, age, number);
                //search.setMaster(uSearch);
                //double currentSum = money * value;

                //search.toString();
                rsC.close();
                rsU.close();
            }
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
                if(st2!=null) st1.close();
                if(st3!=null) st1.close();
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
