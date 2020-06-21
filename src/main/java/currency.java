import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class currency {
    private String name;
    private double value;

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
        currencyDB.viewCurrency();
        System.out.println("Enter currency ID :");
        Scanner sc = new Scanner(System.in);
        int crid = sc.nextInt();
        currency cur = currencyDB.currencyFromDB(crid);
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
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            String sql = "UPDATE CURRENCY " + "SET VALUE=? WHERE NAME=?";

            st1 = conn.prepareStatement(sql);
            st1.setDouble(1, cur.getValue());
            st1.setString(2, cur.getName());
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
    static currency currencyFromDB (int crid) {
        currency cur = new currency();
        Connection conn = null;
        PreparedStatement st1 = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            String sql = "SELECT NAME, VALUE FROM CURRENCY WHERE ID=?";
            st1 = conn.prepareStatement(sql);
            st1.setInt(1, crid);
            ResultSet rs = st1.executeQuery();

            String[] stringsTemp = new String[1];
            double[] doublesTemp = new double[1];

            while(rs.next()) {
                String curName = rs.getString("NAME");
                stringsTemp[0] = curName;
                double value = rs.getDouble("VALUE");
                doublesTemp[0] = value;
            }
            rs.close();
            cur.setName(stringsTemp[0]);
            cur.setValue(doublesTemp[0]);

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
        return cur;
    }
    static void currencyToDB (currency newCurrency) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            String sql = "INSERT INTO CURRENCY (NAME, VALUE) " + "VALUES (?, ?)";

            st1 = conn.prepareStatement(sql);

            st1.setString(1, newCurrency.getName());
            st1.setDouble(2, newCurrency.getValue());

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
    static void viewCurrency () {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            stmt = conn.createStatement();
            String sql = "SELECT ID, NAME, VALUE FROM CURRENCY";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int id  = rs.getInt("ID");
                String name = rs.getString("NAME");
                double value = rs.getDouble("VALUE");

                System.out.print("CurrencyID: " + id);
                System.out.print(", Name: " + name);
                System.out.println(", Value of currency: " + value);
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
    static void deleteCurrency (int crid) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            String delete = "DELETE FROM CURRENCY " + "WHERE ID = ?";
            st1 = conn.prepareStatement(delete);
            st1.setInt(1, crid);
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



