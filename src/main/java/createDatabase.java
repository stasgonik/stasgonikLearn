import java.sql.*;

public class createDatabase {
    static void creation() {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String[] sqlArray = new String[10];
            stmt = conn.createStatement();

            sqlArray[0] = "CREATE TABLE USERS(ID INT PRIMARY KEY AUTO_INCREMENT, FIRST_NAME VARCHAR(255)," +
                    " SECOND_NAME VARCHAR(255), LAST_NAME VARCHAR(255), AGE INT, NUMBER NUMERIC(13,0));";
            sqlArray[1] = "CREATE TABLE CURRENCY(ID INT PRIMARY KEY AUTO_INCREMENT," +
                    " NAME VARCHAR(255), VALUE NUMERIC(7,3));";
            sqlArray[2] = "CREATE TABLE ACCOUNT(ID INT PRIMARY KEY AUTO_INCREMENT, USID INT, CRID INT," +
                    " MONEY NUMERIC(12,2), LOAN NUMERIC(12,2));";
            sqlArray[3] = "ALTER TABLE ACCOUNT ADD FOREIGN KEY (USID) REFERENCES USERS(ID);";
            sqlArray[4] = "ALTER TABLE ACCOUNT ADD FOREIGN KEY (CRID) REFERENCES CURRENCY(ID);";
            sqlArray[5] = "CREATE VIEW ACC_VIEW AS SELECT ACCOUNT.ID, USERS.LAST_NAME, USERS.FIRST_NAME," +
                    " USERS.SECOND_NAME, USERS.AGE, USERS.NUMBER, MONEY, LOAN, CURRENCY.NAME ,CURRENCY.VALUE," +
                    " (ACCOUNT.MONEY * CURRENCY.VALUE) AS SUM_UAH FROM ACCOUNT JOIN CURRENCY" +
                    " ON ACCOUNT.CRID = CURRENCY.ID JOIN USERS ON ACCOUNT.USID = USERS.ID ;";
            sqlArray[6] = "CREATE TABLE OPERATIONS (ID INT PRIMARY KEY AUTO_INCREMENT, ACID_FROM INT," +
                    " ACID_TO INT, TYPE VARCHAR(255), SUBTYPE VARCHAR(255), SUM NUMERIC(12,2)," +
                    " CURRENCY_NAME VARCHAR(255), CURRENCY_VALUE NUMERIC(7,3), CRID INT, DATETIME OPERATION_TIME);";
            sqlArray[7] = "ALTER TABLE OPERATIONS ADD FOREIGN KEY (ACID_FROM) REFERENCES ACCOUNT(ID);";
            sqlArray[8] = "ALTER TABLE OPERATIONS ADD FOREIGN KEY (ACID_TO) REFERENCES ACCOUNT(ID);";
            sqlArray[9] = "ALTER TABLE OPERATIONS ADD FOREIGN KEY (CRID) REFERENCES ACCOUNT(ID);";

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
