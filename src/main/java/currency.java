import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class currency {
    private int id;
    private String name;
    private double value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public currency(int id, String name, double value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public currency() {
    }

    static currency UAH = new currency(1, "UAH", 1);
    static currency USD = new currency(2, "USD", 27);
    static currency EUR = new currency(3, "EUR", 30);
    static currency GBP = new currency(4, "GBP", 35);



    public static currency chooseCurrency() {
        System.out.println("Enter account currency (UAH, USD, EUR, GBP):");
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
        return  name + " (" +
                "id of currency is " + id +
                ", value of this currency is " + value +
                ")";
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

            String sql = "UPDATE CURRENCY " + "SET VALUE=? WHERE id=?";

            st1 = conn.prepareStatement(sql);
            st1.setDouble(1, cur.getValue());
            st1.setInt(2, cur.getId());
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
}



