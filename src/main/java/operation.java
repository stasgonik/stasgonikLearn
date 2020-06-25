import java.sql.*;
import java.time.LocalDateTime;

public class operation {
    private operationType type;
    private subtype subtype;
    private LocalDateTime dateTime = LocalDateTime.now();
    private currency operationCurrency;
    private double sum;

    public operationType getType() {
        return type;
    }

    public void setType(operationType type) {
        this.type = type;
    }

    public subtype getSubtype() {
        return subtype;
    }

    public void setSubtype(subtype subtype) {
        this.subtype = subtype;
    }

    public currency getOperationCurrency() {
        return operationCurrency;
    }

    public void setOperationCurrency(currency operationCurrency) {
        this.operationCurrency = operationCurrency;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public operation() {
    }

    public operation(operationType type, subtype subtype, currency operationCurrency, double sum) {
        this.type = type;
        this.subtype = subtype;
        this.operationCurrency = operationCurrency;
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Operation" + " type : " + type + ", Subtype of operation : " + subtype + ", Time of operation : " +
                dateTime + ", Currency of operation : " + operationCurrency + ", Sum : " + sum +
                " " + operationCurrency.getName() + ".";
    }
}
enum operationType {
    Transfer,
    Credit,
    Output,
    Input,
    Exchange,
    Commission,
    Loan_repayment
}
enum subtype {
    Charge,
    Withdraw
}
class operationDB {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test2";
    static final String USER = "sa";
    static final String PASS = "";
    
    static void operationToDB_2acc(operation newOperation, int acidFrom, int acidTo) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "INSERT INTO OPERATIONS (ACID_FROM, ACID_TO, TYPE, SUBTYPE, SUM, CURRENCY_NAME," +
                    " CURRENCY_VALUE, CRID, OPERATION_TIME) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";


            st1 = conn.prepareStatement(sql);

            st1.setInt(1, acidFrom);
            st1.setInt(2, acidTo);
            st1.setString(3, newOperation.getType().name());
            st1.setString(4, newOperation.getSubtype().name());
            st1.setDouble(5, newOperation.getSum());
            st1.setString(6, newOperation.getOperationCurrency().getName());
            st1.setDouble(7, newOperation.getOperationCurrency().getValue());
            st1.setInt(8, currencyDB.currencyGetID(newOperation.getOperationCurrency()));
            st1.setTimestamp(9, Timestamp.valueOf(newOperation.getDateTime()));

            st1.execute();

            st1.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
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
    static void operationToDB_From(operation newOperation, int acidFrom) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "INSERT INTO OPERATIONS (ACID_FROM, TYPE, SUBTYPE, SUM, CURRENCY_NAME," +
                    " CURRENCY_VALUE, CRID, OPERATION_TIME) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


            st1 = conn.prepareStatement(sql);

            st1.setInt(1, acidFrom);
            st1.setString(2, newOperation.getType().name());
            st1.setString(3, newOperation.getSubtype().name());
            st1.setDouble(4, newOperation.getSum());
            st1.setString(5, newOperation.getOperationCurrency().getName());
            st1.setDouble(6, newOperation.getOperationCurrency().getValue());
            st1.setInt(7, currencyDB.currencyGetID(newOperation.getOperationCurrency()));
            st1.setTimestamp(8, Timestamp.valueOf(newOperation.getDateTime()));

            st1.execute();

            st1.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
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
    static void operationToDB_To(operation newOperation, int acidTo) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "INSERT INTO OPERATIONS (ACID_TO, TYPE, SUBTYPE, SUM, CURRENCY_NAME," +
                    " CURRENCY_VALUE, CRID, OPERATION_TIME) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


            st1 = conn.prepareStatement(sql);

            st1.setInt(1, acidTo);
            st1.setString(2, newOperation.getType().name());
            st1.setString(3, newOperation.getSubtype().name());
            st1.setDouble(4, newOperation.getSum());
            st1.setString(5, newOperation.getOperationCurrency().getName());
            st1.setDouble(6, newOperation.getOperationCurrency().getValue());
            st1.setInt(7, currencyDB.currencyGetID(newOperation.getOperationCurrency()));
            st1.setTimestamp(8, Timestamp.valueOf(newOperation.getDateTime()));

            st1.execute();

            st1.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
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
    static void viewOperations (int acid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);


            String sql = "SELECT ID, ACID_FROM, ACID_TO, TYPE, SUM, CURRENCY_NAME," +
                    " CURRENCY_VALUE, OPERATION_TIME FROM OPERATIONS WHERE ACID_FROM=? OR ACID_TO=?";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, acid);
            stmt.setInt(2, acid);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int id  = rs.getInt("ID");
                int acidFrom = rs.getInt("ACID_FROM");
                user from = userDB.userFromDB(accountDB.usidFromDB(acidFrom));
                int acidTo = rs.getInt("ACID_TO");
                user to = userDB.userFromDB(accountDB.usidFromDB(acidTo));
                String type = rs.getString("TYPE");
                double sum = rs.getDouble("SUM");
                String currencyName = rs.getString("CURRENCY_NAME");
                double currencyValue = rs.getDouble("CURRENCY_VALUE");
                LocalDateTime operationTime = rs.getTimestamp("OPERATION_TIME").toLocalDateTime();


                System.out.println("                                              | _---_ |");
                System.out.print("ID of operation : " + id);
                System.out.println(", Operation type: " + type + " ;");
                System.out.print("ID of sender account: " + acidFrom);
                System.out.println(", owner of this account is: " + from.getLastName() + " " + from.getFirstName() +
                        " " + from.getSecondName() + " ;");
                System.out.print("ID of recipient account: " + acidTo);
                System.out.println(", owner of this account is: " + to.getLastName() + " " + to.getFirstName() +
                        " " + to.getSecondName() + " ;");
                System.out.println("Sum of operation : " + sum + " " + currencyName +
                        " ( Currency course " + currencyValue + ") ;" );
                System.out.println("Operation registered : " + operationTime + ".");
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
