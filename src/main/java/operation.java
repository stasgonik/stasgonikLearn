import java.sql.*;
import java.time.LocalDateTime;

public class operation {
    private operationType type;
    private subtype subtype;
    private final LocalDateTime dateTime = LocalDateTime.now();
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
    static void operationToDB_2acc(operation newOperation, int acidFrom, int acidTo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "INSERT INTO OPERATIONS (ACID_FROM, ACID_TO, TYPE, SUBTYPE, SUM, CURRENCY_NAME," +
                    " CURRENCY_VALUE, CRID, OPERATION_TIME) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";


            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, acidFrom);
            stmt.setInt(2, acidTo);
            stmt.setString(3, newOperation.getType().name());
            stmt.setString(4, newOperation.getSubtype().name());
            stmt.setDouble(5, newOperation.getSum());
            stmt.setString(6, newOperation.getOperationCurrency().getName());
            stmt.setDouble(7, newOperation.getOperationCurrency().getValue());
            stmt.setInt(8, currencyDB.currencyGetID(newOperation.getOperationCurrency()));
            stmt.setTimestamp(9, Timestamp.valueOf(newOperation.getDateTime()));

            stmt.execute();

            stmt.close();
            conn.close();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {
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
        PreparedStatement stmt = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "INSERT INTO OPERATIONS (ACID_FROM, TYPE, SUBTYPE, SUM, CURRENCY_NAME," +
                    " CURRENCY_VALUE, CRID, OPERATION_TIME) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, acidFrom);
            stmt.setString(2, newOperation.getType().name());
            stmt.setString(3, newOperation.getSubtype().name());
            stmt.setDouble(4, newOperation.getSum());
            stmt.setString(5, newOperation.getOperationCurrency().getName());
            stmt.setDouble(6, newOperation.getOperationCurrency().getValue());
            stmt.setInt(7, currencyDB.currencyGetID(newOperation.getOperationCurrency()));
            stmt.setTimestamp(8, Timestamp.valueOf(newOperation.getDateTime()));

            stmt.execute();

            stmt.close();
            conn.close();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {
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
        PreparedStatement stmt = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "INSERT INTO OPERATIONS (ACID_TO, TYPE, SUBTYPE, SUM, CURRENCY_NAME," +
                    " CURRENCY_VALUE, CRID, OPERATION_TIME) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, acidTo);
            stmt.setString(2, newOperation.getType().name());
            stmt.setString(3, newOperation.getSubtype().name());
            stmt.setDouble(4, newOperation.getSum());
            stmt.setString(5, newOperation.getOperationCurrency().getName());
            stmt.setDouble(6, newOperation.getOperationCurrency().getValue());
            stmt.setInt(7, currencyDB.currencyGetID(newOperation.getOperationCurrency()));
            stmt.setTimestamp(8, Timestamp.valueOf(newOperation.getDateTime()));

            stmt.execute();

            stmt.close();
            conn.close();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static void viewOperationsAll(int acid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "SELECT ID, ACID_FROM, ACID_TO, TYPE, SUBTYPE, SUM, CURRENCY_NAME," +
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
                String subType = rs.getString("SUBTYPE");
                double sum = rs.getDouble("SUM");
                String currencyName = rs.getString("CURRENCY_NAME");
                double currencyValue = rs.getDouble("CURRENCY_VALUE");
                LocalDateTime operationTime = rs.getTimestamp("OPERATION_TIME").toLocalDateTime();


                System.out.println("                                              | _---_ |");
                System.out.print("ID of operation : " + id);
                System.out.println(", Operation type: " + type + " ( " + subType + " );");
                if (acidFrom == constants.bank) {
                    System.out.println("Sender account: GSI Bank Administration.");
                }
                else if (acidFrom == 0) {
                    System.out.println(" ");
                }
                else {
                    System.out.print("ID of sender account: " + acidFrom);
                    System.out.println(", owner of this account is: " + from.getLastName() + " " + from.getFirstName() +
                            " " + from.getSecondName() + " ;");
                }
                if (acidTo == constants.bank) {
                    System.out.println("Recipient account: GSI Bank Administration.");
                }
                else if (acidTo == 0) {
                    System.out.println(" ");
                }
                else {
                    System.out.print("ID of recipient account: " + acidTo);
                    System.out.println(", owner of this account is: " + to.getLastName() + " " + to.getFirstName() +
                            " " + to.getSecondName() + " ;");
                }
                System.out.println("Sum of operation : " + sum + " " + currencyName +
                        " ( Currency course " + currencyValue + ") ;" );
                System.out.println("Operation registered : " + operationTime + ".");
                System.out.println(" ");

            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static void viewOperationsSender(int acidFrom) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "SELECT ID, ACID_FROM, ACID_TO, TYPE, SUBTYPE, SUM, CURRENCY_NAME," +
                    " CURRENCY_VALUE, OPERATION_TIME FROM OPERATIONS WHERE ACID_FROM=?";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, acidFrom);


            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int id  = rs.getInt("ID");
                acidFrom = rs.getInt("ACID_FROM");
                user from = userDB.userFromDB(accountDB.usidFromDB(acidFrom));
                int acidTo = rs.getInt("ACID_TO");
                user to = userDB.userFromDB(accountDB.usidFromDB(acidTo));
                String type = rs.getString("TYPE");
                String subType = rs.getString("SUBTYPE");
                double sum = rs.getDouble("SUM");
                String currencyName = rs.getString("CURRENCY_NAME");
                double currencyValue = rs.getDouble("CURRENCY_VALUE");
                LocalDateTime operationTime = rs.getTimestamp("OPERATION_TIME").toLocalDateTime();


                System.out.println("                                              | _---_ |");
                System.out.print("ID of operation : " + id);
                System.out.println(", Operation type: " + type + " ( " + subType + " );");
                if (acidFrom == constants.bank) {
                    System.out.println("Sender account: GSI Bank Administration.");
                }
                else if (acidFrom == 0) {
                    System.out.println(" ");
                }
                else {
                    System.out.print("ID of sender account: " + acidFrom);
                    System.out.println(", owner of this account is: " + from.getLastName() + " " + from.getFirstName() +
                            " " + from.getSecondName() + " ;");
                }
                if (acidTo == constants.bank) {
                    System.out.println("Recipient account: GSI Bank Administration.");
                }
                else if (acidTo == 0) {
                    System.out.println(" ");
                }
                else {
                    System.out.print("ID of recipient account: " + acidTo);
                    System.out.println(", owner of this account is: " + to.getLastName() + " " + to.getFirstName() +
                            " " + to.getSecondName() + " ;");
                }
                System.out.println("Sum of operation : " + sum + " " + currencyName +
                        " ( Currency course " + currencyValue + ") ;" );
                System.out.println("Operation registered : " + operationTime + ".");
                System.out.println(" ");

            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static void viewOperationsRecipient(int acidTo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "SELECT ID, ACID_FROM, ACID_TO, TYPE, SUBTYPE, SUM, CURRENCY_NAME," +
                    " CURRENCY_VALUE, OPERATION_TIME FROM OPERATIONS WHERE ACID_TO=?";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, acidTo);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int id  = rs.getInt("ID");
                int acidFrom = rs.getInt("ACID_FROM");
                user from = userDB.userFromDB(accountDB.usidFromDB(acidFrom));
                acidTo = rs.getInt("ACID_TO");
                user to = userDB.userFromDB(accountDB.usidFromDB(acidTo));
                String type = rs.getString("TYPE");
                String subType = rs.getString("SUBTYPE");
                double sum = rs.getDouble("SUM");
                String currencyName = rs.getString("CURRENCY_NAME");
                double currencyValue = rs.getDouble("CURRENCY_VALUE");
                LocalDateTime operationTime = rs.getTimestamp("OPERATION_TIME").toLocalDateTime();


                System.out.println("                                              | _---_ |");
                System.out.print("ID of operation : " + id);
                System.out.println(", Operation type: " + type + " ( " + subType + " );");
                if (acidFrom == constants.bank) {
                    System.out.println("Sender account: GSI Bank Administration.");
                }
                else if (acidFrom == 0) {
                    System.out.println(" ");
                }
                else {
                    System.out.print("ID of sender account: " + acidFrom);
                    System.out.println(", owner of this account is: " + from.getLastName() + " " + from.getFirstName() +
                            " " + from.getSecondName() + " ;");
                }
                if (acidTo == constants.bank) {
                    System.out.println("Recipient account: GSI Bank Administration.");
                }
                else if (acidTo == 0) {
                    System.out.println(" ");
                }
                else {
                    System.out.print("ID of recipient account: " + acidTo);
                    System.out.println(", owner of this account is: " + to.getLastName() + " " + to.getFirstName() +
                            " " + to.getSecondName() + " ;");
                }
                System.out.println("Sum of operation : " + sum + " " + currencyName +
                        " ( Currency course " + currencyValue + ") ;" );
                System.out.println("Operation registered : " + operationTime + ".");
                System.out.println(" ");

            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static void viewOperationsTyped(int acid, operationType opType) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "SELECT ID, ACID_FROM, ACID_TO, TYPE, SUBTYPE, SUM, CURRENCY_NAME," +
                    " CURRENCY_VALUE, OPERATION_TIME FROM OPERATIONS WHERE TYPE=? AND (ACID_FROM=? OR ACID_TO=?)";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, opType.name());
            stmt.setInt(2, acid);
            stmt.setInt(3, acid);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int id  = rs.getInt("ID");
                int acidFrom = rs.getInt("ACID_FROM");
                user from = userDB.userFromDB(accountDB.usidFromDB(acidFrom));
                int acidTo = rs.getInt("ACID_TO");
                user to = userDB.userFromDB(accountDB.usidFromDB(acidTo));
                String type = rs.getString("TYPE");
                String subType = rs.getString("SUBTYPE");
                double sum = rs.getDouble("SUM");
                String currencyName = rs.getString("CURRENCY_NAME");
                double currencyValue = rs.getDouble("CURRENCY_VALUE");
                LocalDateTime operationTime = rs.getTimestamp("OPERATION_TIME").toLocalDateTime();


                System.out.println("                                              | _---_ |");
                System.out.print("ID of operation : " + id);
                System.out.println(", Operation type: " + type + " ( " + subType + " );");
                if (acidFrom == constants.bank) {
                    System.out.println("Sender account: GSI Bank Administration.");
                }
                else if (acidFrom == 0) {
                    System.out.println(" ");
                }
                else {
                    System.out.print("ID of sender account: " + acidFrom);
                    System.out.println(", owner of this account is: " + from.getLastName() + " " + from.getFirstName() +
                            " " + from.getSecondName() + " ;");
                }
                if (acidTo == constants.bank) {
                    System.out.println("Recipient account: GSI Bank Administration.");
                }
                else if (acidTo == 0) {
                    System.out.println(" ");
                }
                else {
                    System.out.print("ID of recipient account: " + acidTo);
                    System.out.println(", owner of this account is: " + to.getLastName() + " " + to.getFirstName() +
                            " " + to.getSecondName() + " ;");
                }
                System.out.println("Sum of operation : " + sum + " " + currencyName +
                        " ( Currency course " + currencyValue + ") ;" );
                System.out.println("Operation registered : " + operationTime + ".");
                System.out.println(" ");

            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static void viewOperationsTypedSender(int acidFrom, operationType opType) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "SELECT ID, ACID_FROM, ACID_TO, TYPE, SUBTYPE, SUM, CURRENCY_NAME," +
                    " CURRENCY_VALUE, OPERATION_TIME FROM OPERATIONS WHERE TYPE=? AND ACID_FROM=?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, opType.name());
            stmt.setInt(2, acidFrom);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int id  = rs.getInt("ID");
                acidFrom = rs.getInt("ACID_FROM");
                user from = userDB.userFromDB(accountDB.usidFromDB(acidFrom));
                int acidTo = rs.getInt("ACID_TO");
                user to = userDB.userFromDB(accountDB.usidFromDB(acidTo));
                String type = rs.getString("TYPE");
                String subType = rs.getString("SUBTYPE");
                double sum = rs.getDouble("SUM");
                String currencyName = rs.getString("CURRENCY_NAME");
                double currencyValue = rs.getDouble("CURRENCY_VALUE");
                LocalDateTime operationTime = rs.getTimestamp("OPERATION_TIME").toLocalDateTime();


                System.out.println("                                              | _---_ |");
                System.out.print("ID of operation : " + id);
                System.out.println(", Operation type: " + type + " ( " + subType + " );");
                if (acidFrom == constants.bank) {
                    System.out.println("Sender account: GSI Bank Administration.");
                }
                else if (acidFrom == 0) {
                    System.out.println(" ");
                }
                else {
                    System.out.print("ID of sender account: " + acidFrom);
                    System.out.println(", owner of this account is: " + from.getLastName() + " " + from.getFirstName() +
                            " " + from.getSecondName() + " ;");
                }
                if (acidTo == constants.bank) {
                    System.out.println("Recipient account: GSI Bank Administration.");
                }
                else if (acidTo == 0) {
                    System.out.println(" ");
                }
                else {
                    System.out.print("ID of recipient account: " + acidTo);
                    System.out.println(", owner of this account is: " + to.getLastName() + " " + to.getFirstName() +
                            " " + to.getSecondName() + " ;");
                }
                System.out.println("Sum of operation : " + sum + " " + currencyName +
                        " ( Currency course " + currencyValue + ") ;" );
                System.out.println("Operation registered : " + operationTime + ".");
                System.out.println(" ");

            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static void viewOperationsTypedRecipient(int acidTo, operationType opType) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "SELECT ID, ACID_FROM, ACID_TO, TYPE, SUBTYPE, SUM, CURRENCY_NAME," +
                    " CURRENCY_VALUE, OPERATION_TIME FROM OPERATIONS WHERE TYPE=? AND ACID_TO=?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, opType.name());
            stmt.setInt(2, acidTo);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int id  = rs.getInt("ID");
                int acidFrom = rs.getInt("ACID_FROM");
                user from = userDB.userFromDB(accountDB.usidFromDB(acidFrom));
                acidTo = rs.getInt("ACID_TO");
                user to = userDB.userFromDB(accountDB.usidFromDB(acidTo));
                String type = rs.getString("TYPE");
                String subType = rs.getString("SUBTYPE");
                double sum = rs.getDouble("SUM");
                String currencyName = rs.getString("CURRENCY_NAME");
                double currencyValue = rs.getDouble("CURRENCY_VALUE");
                LocalDateTime operationTime = rs.getTimestamp("OPERATION_TIME").toLocalDateTime();


                System.out.println("                                              | _---_ |");
                System.out.print("ID of operation : " + id);
                System.out.println(", Operation type: " + type + " ( " + subType + " );");
                if (acidFrom == constants.bank) {
                    System.out.println("Sender account: GSI Bank Administration.");
                }
                else if (acidFrom == 0) {
                    System.out.println(" ");
                }
                else {
                    System.out.print("ID of sender account: " + acidFrom);
                    System.out.println(", owner of this account is: " + from.getLastName() + " " + from.getFirstName() +
                            " " + from.getSecondName() + " ;");
                }
                if (acidTo == constants.bank) {
                    System.out.println("Recipient account: GSI Bank Administration.");
                }
                else if (acidTo == 0) {
                    System.out.println(" ");
                }
                else {
                    System.out.print("ID of recipient account: " + acidTo);
                    System.out.println(", owner of this account is: " + to.getLastName() + " " + to.getFirstName() +
                            " " + to.getSecondName() + " ;");
                }
                System.out.println("Sum of operation : " + sum + " " + currencyName +
                        " ( Currency course " + currencyValue + ") ;" );
                System.out.println("Operation registered : " + operationTime + ".");
                System.out.println(" ");

            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static void viewOperationsAllFiltered(int acid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "SELECT ID, ACID_FROM, ACID_TO, TYPE, SUBTYPE, SUM, CURRENCY_NAME," +
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
                String subType = rs.getString("SUBTYPE");
                double sum = rs.getDouble("SUM");
                String currencyName = rs.getString("CURRENCY_NAME");
                double currencyValue = rs.getDouble("CURRENCY_VALUE");
                LocalDateTime operationTime = rs.getTimestamp("OPERATION_TIME").toLocalDateTime();

                if ((type.equals(operationType.Input.name()) || type.equals(operationType.Output.name()) ||
                type.equals(operationType.Exchange.name())) || ((type.equals(operationType.Commission.name()) ||
                type.equals(operationType.Loan_repayment.name()) || type.equals(operationType.Transfer.name())) &&
                acid == acidFrom && subType.equals(subtype.Withdraw.name())) ||
                ((type.equals(operationType.Transfer.name()) || type.equals(operationType.Credit.name())) &&
                acid == acidTo && subType.equals(subtype.Charge.name())))
                {
                    System.out.println("                                              | _---_ |");
                    System.out.print("ID of operation : " + id);
                    System.out.println(", Operation type: " + type + " ( " + subType + " );");
                    if (acidFrom == constants.bank) {
                        System.out.println("Sender account: GSI Bank Administration.");
                    }
                    else if (acidFrom == 0) {
                        // do nothing
                        System.out.println(" ");
                    }
                    else {
                        System.out.print("ID of sender account: " + acidFrom);
                        System.out.println(", owner of this account is: " + from.getLastName() + " " + from.getFirstName() +
                                " " + from.getSecondName() + " ;");
                    }
                    if (acidTo == constants.bank) {
                        System.out.println("Recipient account: GSI Bank Administration.");
                    }
                    else if (acidTo == 0) {
                        // do nothing
                        System.out.println(" ");
                    }
                    else {
                        System.out.print("ID of recipient account: " + acidTo);
                        System.out.println(", owner of this account is: " + to.getLastName() + " " + to.getFirstName() +
                                " " + to.getSecondName() + " ;");
                    }
                    System.out.println("Sum of operation : " + sum + " " + currencyName +
                            " ( Currency course " + currencyValue + ") ;" );
                    System.out.println("Operation registered : " + operationTime + ".");
                    System.out.println(" ");
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static void viewOperationsAllFilteredSender(int acid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "SELECT ID, ACID_FROM, ACID_TO, TYPE, SUBTYPE, SUM, CURRENCY_NAME," +
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
                String subType = rs.getString("SUBTYPE");
                double sum = rs.getDouble("SUM");
                String currencyName = rs.getString("CURRENCY_NAME");
                double currencyValue = rs.getDouble("CURRENCY_VALUE");
                LocalDateTime operationTime = rs.getTimestamp("OPERATION_TIME").toLocalDateTime();

                if (((type.equals(operationType.Input.name()) & acid == acidFrom) ||
                        (type.equals(operationType.Output.name())& acid == acidFrom) ||
                        (type.equals(operationType.Exchange.name())) & acid == acidFrom) ||
                        ((type.equals(operationType.Commission.name()) ||
                        type.equals(operationType.Loan_repayment.name()) ||
                        type.equals(operationType.Transfer.name())) && acid == acidFrom &&
                        subType.equals(subtype.Withdraw.name())))
                {
                    System.out.println("                                              | _---_ |");
                    System.out.print("ID of operation : " + id);
                    System.out.println(", Operation type: " + type + " ( " + subType + " );");
                    if (acidFrom == constants.bank) {
                        System.out.println("Sender account: GSI Bank Administration.");
                    }
                    else if (acidFrom == 0) {
                        // do nothing
                        System.out.println(" ");
                    }
                    else {
                        System.out.print("ID of sender account: " + acidFrom);
                        System.out.println(", owner of this account is: " + from.getLastName() + " " + from.getFirstName() +
                                " " + from.getSecondName() + " ;");
                    }
                    if (acidTo == constants.bank) {
                        System.out.println("Recipient account: GSI Bank Administration.");
                    }
                    else if (acidTo == 0) {
                        // do nothing
                        System.out.println(" ");
                    }
                    else {
                        System.out.print("ID of recipient account: " + acidTo);
                        System.out.println(", owner of this account is: " + to.getLastName() + " " + to.getFirstName() +
                                " " + to.getSecondName() + " ;");
                    }
                    System.out.println("Sum of operation : " + sum + " " + currencyName +
                            " ( Currency course " + currencyValue + ") ;" );
                    System.out.println("Operation registered : " + operationTime + ".");
                    System.out.println(" ");
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static void viewOperationsAllFilteredRecipient(int acid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "SELECT ID, ACID_FROM, ACID_TO, TYPE, SUBTYPE, SUM, CURRENCY_NAME," +
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
                String subType = rs.getString("SUBTYPE");
                double sum = rs.getDouble("SUM");
                String currencyName = rs.getString("CURRENCY_NAME");
                double currencyValue = rs.getDouble("CURRENCY_VALUE");
                LocalDateTime operationTime = rs.getTimestamp("OPERATION_TIME").toLocalDateTime();

                if (((type.equals(operationType.Input.name()) && acid == acidTo) ||
                        (type.equals(operationType.Output.name()) && acid == acidTo) ||
                        ((type.equals(operationType.Exchange.name())) && acid == acidTo)) ||
                        ((type.equals(operationType.Transfer.name()) || type.equals(operationType.Credit.name())) &&
                                acid == acidTo && subType.equals(subtype.Charge.name())))
                {
                    System.out.println("                                              | _---_ |");
                    System.out.print("ID of operation : " + id);
                    System.out.println(", Operation type: " + type + " ( " + subType + " );");
                    if (acidFrom == constants.bank) {
                        System.out.println("Sender account: GSI Bank Administration.");
                    }
                    else if (acidFrom == 0) {
                        // do nothing
                        System.out.println(" ");
                    }
                    else {
                        System.out.print("ID of sender account: " + acidFrom);
                        System.out.println(", owner of this account is: " + from.getLastName() + " " + from.getFirstName() +
                                " " + from.getSecondName() + " ;");
                    }
                    if (acidTo == constants.bank) {
                        System.out.println("Recipient account: GSI Bank Administration.");
                    }
                    else if (acidTo == 0) {
                        // do nothing
                        System.out.println(" ");
                    }
                    else {
                        System.out.print("ID of recipient account: " + acidTo);
                        System.out.println(", owner of this account is: " + to.getLastName() + " " + to.getFirstName() +
                                " " + to.getSecondName() + " ;");
                    }
                    System.out.println("Sum of operation : " + sum + " " + currencyName +
                            " ( Currency course " + currencyValue + ") ;" );
                    System.out.println("Operation registered : " + operationTime + ".");
                    System.out.println(" ");
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
