import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.*;


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

    public static void createCurrency() {
        Scanner sc = new Scanner(System.in);
        currency cur = new currency();
        validators.NameValidator nameValidator = new validators.NameValidator();
        validators.NumberValidator numberValidator = new validators.NumberValidator();
        int i = 0;
        try {
            do {
                System.out.println("Enter currency name:");
                String temp = sc.nextLine();
                if (nameValidator.validate(temp)) {
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
                if (numberValidator.validate(temp)) {
                    double tempInt = Double.parseDouble (temp);
                    if (tempInt <= 0) {
                        System.out.println("Prohibited negative or 0 value of currency.");
                    }
                    else {
                        cur.setValue(tempInt);
                        i++;
                    }
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
    }

    public static int chooseCurrency() {
        int crid = 0;
        try {
            currencyDB.viewCurrency();
            Scanner sc = new Scanner(System.in);
            validators.NumberValidator numberValidator = new validators.NumberValidator();
            int i = 0;
            do {
                System.out.println("Enter ID of currency:");
                String temp = sc.nextLine();
                if (numberValidator.validate(temp)) {
                    crid = Integer.parseInt (temp);
                    if (crid <= 0){
                        System.out.println("There is no currency with negative ID. Please, repeat your set.");
                    }
                    else {
                        i++;
                    }
                }
                else {
                    System.out.println("Incorrect ID format. Use only numbers!");
                }
            }
            while (i==0);
        }
        catch (Exception ex) {
            ex.getMessage();
        }
        return crid;
    }

    @Override
    public String toString() {
        return  name + " (" + "Value of this currency is " + value + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof currency)) return false;
        currency currency = (currency) o;
        return Double.compare(currency.getValue(), getValue()) == 0 &&
                Objects.equals(getName(), currency.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getValue());
    }
}
class currencyDB {
    static void currencyUpdateValue(int crid, double newValue) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "UPDATE CURRENCY " + "SET VALUE=? WHERE ID=?";

            st1 = conn.prepareStatement(sql);
            st1.setDouble(1, newValue);
            st1.setInt(2, crid);
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
    @NotNull
    static currency currencyFromDB (int crid) {
        currency cur = new currency();
        Connection conn = null;
        PreparedStatement st1 = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

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
    static int currencyGetID (currency search) {
        int crid = 0;
        Connection conn = null;
        PreparedStatement st1 = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "SELECT ID FROM CURRENCY WHERE NAME=?";
            st1 = conn.prepareStatement(sql);
            st1.setString(1, search.getName());
            ResultSet rs = st1.executeQuery();

            int[] intsTemp = new int[1];

            while(rs.next()) {
                int id = rs.getInt("ID");
                intsTemp[0] = id;
            }
            rs.close();

            crid = intsTemp[0];

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
        return crid;
    }
    static void currencyToDB (currency newCurrency) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

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
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

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
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

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
    static void currencyUpdateName (int crid, String newName) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try{
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "UPDATE CURRENCY " + "SET NAME=? WHERE ID=?";

            st1 = conn.prepareStatement(sql);
            st1.setString(1, newName);
            st1.setInt(2, crid);
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
}



