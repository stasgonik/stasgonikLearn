import java.sql.*;

public class createDatabase {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test2";
    static final String USER = "sa";
    static final String PASS = "";
    static void creation() {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            String[] sqlArray = new String[16];
            stmt = conn.createStatement();

            sqlArray[0] = "CREATE TABLE USERS(ID INT PRIMARY KEY AUTO_INCREMENT, FIRST_NAME VARCHAR(255)," +
                    " SECOND_NAME VARCHAR(255), LAST_NAME VARCHAR(255), AGE INT, NUMBER NUMERIC(13,0))";
            sqlArray[1] = "CREATE TABLE CURRENCY(ID INT PRIMARY KEY AUTO_INCREMENT," +
                    " NAME VARCHAR(255), VALUE NUMERIC(7,3))";
            sqlArray[2] = "CREATE TABLE ACCOUNT(ID INT PRIMARY KEY AUTO_INCREMENT, USID INT, CRID INT," +
                    " MONEY NUMERIC(12,2), LOAN NUMERIC(12,2))";
            sqlArray[3] = "ALTER TABLE ACCOUNT ADD FOREIGN KEY (USID) REFERENCES USERS(ID)";
            sqlArray[4] = "ALTER TABLE ACCOUNT ADD FOREIGN KEY (CRID) REFERENCES CURRENCY(ID)";
            sqlArray[5] = "CREATE VIEW ACC_VIEW AS SELECT ACCOUNT.ID, USERS.LAST_NAME, USERS.FIRST_NAME," +
                    " USERS.SECOND_NAME, USERS.AGE, USERS.NUMBER, MONEY, LOAN, CURRENCY.NAME ,CURRENCY.VALUE," +
                    " (ACCOUNT.MONEY * CURRENCY.VALUE) AS SUM_UAH FROM ACCOUNT JOIN CURRENCY" +
                    " ON ACCOUNT.CRID = CURRENCY.ID JOIN USERS ON ACCOUNT.USID = USERS.ID ";
            sqlArray[6] = "CREATE TABLE OPERATIONS (ID INT PRIMARY KEY AUTO_INCREMENT, ACID_FROM INT," +
                    " ACID_TO INT, TYPE VARCHAR(255), SUBTYPE VARCHAR(255), SUM NUMERIC(12,2)," +
                    " CURRENCY_NAME VARCHAR(255), CURRENCY_VALUE NUMERIC(7,3), CRID INT, DATETIME OPERATION_TIME)";
            sqlArray[7] = "ALTER TABLE OPERATIONS ADD FOREIGN KEY (ACID_FROM) REFERENCES ACCOUNT(ID)";
            sqlArray[8] = "ALTER TABLE OPERATIONS ADD FOREIGN KEY (ACID_TO) REFERENCES ACCOUNT(ID)";
            sqlArray[9] = "ALTER TABLE OPERATIONS ADD FOREIGN KEY (CRID) REFERENCES ACCOUNT(ID)";
            sqlArray[10] = "INSERT INTO CURRENCY (ID, NAME, VALUE) VALUES (1, UAH, 1)";
            sqlArray[11] = "INSERT INTO USERS (ID, FIRST_NAME, SECOND_NAME, LAST_NAME, AGE, NUMBER) VALUES" +
                    " (42, ADMIN, BANHAMMER, GOD, 9999, 123456789012)";
            sqlArray[12] = "INSERT INTO ACCOUNT (ID, USID, CRID, MONEY, LOAN) VALUES (42, 42, 1, 100000000, 0)";
            sqlArray[13] = "CREATE TABLE LOGIN (ID INT PRIMARY KEY AUTO_INCREMENT, LOGIN VARCHAR(255)," +
                    " PASSWORD VARCHAR(255), USID INT)";
            sqlArray[14] = "ALTER TABLE LOGIN ADD FOREIGN KEY (USID) REFERENCES USERS(ID)";
            sqlArray[15] = "INSERT INTO LOGIN (ID, LOGIN, PASSWORD, USID) VALUES (42, ADMIN, PASSWORD, 42)";

            for (String sql : sqlArray) {
                stmt.executeUpdate(sql);
            }

            stmt.close();
            conn.close();
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
