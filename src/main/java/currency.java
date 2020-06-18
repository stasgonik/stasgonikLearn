import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class currency {
    //private int id;
    private String name;
    private double value;

    //public int getId() {
      //  return id;
    //}

    //public void setId(int id) {
    //    this.id = id;
    //}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public currency (String name, double value) {
        this.name = name;
        this.value = value;
    }

    public currency() {
    }

    static currency UAH = new currency( "UAH", 1);
    static currency USD = new currency( "USD", 26);
    static currency EUR = new currency( "EUR", 29);
    static currency GBP = new currency( "GBP", 34);

    public static currency createCurrency() {
        Scanner sc = new Scanner(System.in);
        currency cur = new currency();
        currencyNameValidator validator = new currencyNameValidator();
        currencyNumberValidator validator2 = new currencyNumberValidator();

        int i = 0;
        try {
            do {
                System.out.println("Enter currency name:");
                String temp = sc.nextLine();
                if (validator.validate(temp)) {
                    cur.setName(temp);
                    i++;
                }
                else {
                    System.out.println("Incorrect name format. Use only latin letters or {-} !");
                }
            }
            while (i==0);
            do {
                System.out.println("Enter value of currency:");
                String temp = sc.nextLine();
                if (validator2.validate(temp)) {
                    double tempInt = Double.parseDouble (temp);
                    cur.setValue(tempInt);
                    i++;
                }
                else {
                    System.out.println("Incorrect value format. Use only numbers!");
                }
            }
            while (i==1);


        }
        catch (Exception ex) {
            ex.getMessage();
        }
        currencyDB.currencyToDB(cur);
        return cur;
    }


    public static currency chooseCurrency() {
        System.out.println("Enter currency (UAH, USD, EUR, GBP):");
        Scanner sc = new Scanner(System.in);
        currency cur = new currency();
        int i = 0;
        try {
            do {
                switch (sc.nextLine().toLowerCase()) {
                    case "uah" :
                        cur = currency.UAH;
                        i++;
                        break;

                    case "usd" :
                        cur = currency.USD;
                        i++;
                        break;

                    case "eur" :
                        cur = currency.EUR;
                        i++;
                        break;

                    case "gbp" :
                        cur = currency.GBP;
                        i++;
                        break;

                    default:
                        System.out.println("Unknown currency. Please, try again.");
                        break;
                }
            }
            while (i==0);

        }
        catch (Exception ex) {
            ex.getMessage();
        }
        return cur;
    }

    @Override
    public String toString() {
        return  name + " (" + "Value of this currency is " + value + ")";
    }
}
class currencyDB {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test2";


    static final String USER = "sa";
    static final String PASS = "";

    static void currencyUpdate(currency cur) {
        Connection conn = null;
        PreparedStatement st1 = null;
        System.out.println("Starting update of currency to DB!");

        try{
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connected database successfully...");

            // STEP 3: Execute a query

            String sql = "UPDATE CURRENCY " + "SET VALUE=? WHERE NAME=?";

            st1 = conn.prepareStatement(sql);
            st1.setDouble(1, cur.getValue());
            st1.setString(2, cur.getName());
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
        System.out.println("Attempt end!");
    }
    static currency currencyFromDB (int crid) {
        currency cur = new currency();
        Connection conn = null;
        PreparedStatement st1 = null;

        //Statement stmt = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
            //System.out.println("Connected database successfully...");
            String sql = "SELECT NAME, VALUE FROM CURRENCY WHERE ID=?";
            //stmt = conn.createStatement();
            //ResultSet rs = stmt.executeQuery(sql);
            st1 = conn.prepareStatement(sql);
            st1.setInt(1, crid);
            ResultSet rs = st1.executeQuery();


            String[] stringsTemp = new String[1];
            double[] doublesTemp = new double[1];

            // STEP 4: Extract data from result set
            while(rs.next()) {
                // Retrieve by column name

                String curName = rs.getString("NAME");
                stringsTemp[0] = curName;
                double value = rs.getDouble("VALUE");
                doublesTemp[0] = value;

            }
            rs.close();
            cur.setName(stringsTemp[0]);
            cur.setValue(doublesTemp[0]);
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
        //System.out.println("Goodbye!");
        return cur;
    }
    static void currencyToDB (currency newCurrency) {
        Connection conn = null;
        PreparedStatement st1 = null;
        System.out.println("Start saving currency to DB!");

        try{
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connected database successfully...");

            // STEP 3: Execute a query

            String sql = "INSERT INTO CURRENCY (NAME, VALUE) " +
                    "VALUES (?, ?)";

            st1 = conn.prepareStatement(sql);

            st1.setString(1, newCurrency.getName());
            st1.setDouble(2, newCurrency.getValue());


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
        System.out.println("Attempt end!");
    }
    static void viewCurrency () {
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
            System.out.println("Connected database successfully...");
            stmt = conn.createStatement();
            String sql = "SELECT ID, NAME, VALUE FROM CURRENCY";
            ResultSet rs = stmt.executeQuery(sql);

            // STEP 4: Extract data from result set
            while(rs.next()) {
                // Retrieve by column name
                int id  = rs.getInt("ID");
                String name = rs.getString("NAME");
                double value = rs.getDouble("VALUE");


                // Display values
                System.out.print("CurrencyID: " + id);
                System.out.print(", Name: " + name);
                System.out.println(", Value of currency: " + value);
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
                if(stmt!=null) stmt.close();
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
class currencyNameValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String NAMES_PATTERN =
            "^[_A-Za-z-\\+]+$";
    public currencyNameValidator() {
        pattern = Pattern.compile(NAMES_PATTERN);
    }
    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);

        return matcher.matches();
    }
}
class currencyNumberValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String NAMES_PATTERN =
            "^[_0-9\\+]+$";
    public currencyNumberValidator() {
        pattern = Pattern.compile(NAMES_PATTERN);
    }
    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);

        return matcher.matches();
    }
}



