package com.epam.GSI;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class createDatabase {
    private static final Logger log = Logger.getLogger(createDatabase.class);
    public static void creation() {
        Connection conn = null;
        Statement stmt = null;
        try {
            log.warn("Creating database!");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String[] sqlArray = new String[16];
            String[] logArray = new String[16];
            int i = 0;
            stmt = conn.createStatement();

            logArray[0] = "Creating table USERS";
            sqlArray[0] = "CREATE TABLE USERS(ID INT PRIMARY KEY AUTO_INCREMENT, FIRST_NAME VARCHAR(255)," +
                    " SECOND_NAME VARCHAR(255), LAST_NAME VARCHAR(255), AGE INT, NUMBER NUMERIC(13,0))";
            logArray[1] = "Creating table CURRENCY";
            sqlArray[1] = "CREATE TABLE CURRENCY(ID INT PRIMARY KEY AUTO_INCREMENT," +
                    " NAME VARCHAR(255), VALUE NUMERIC(7,3))";
            logArray[2] = "Creating table ACCOUNT";
            sqlArray[2] = "CREATE TABLE ACCOUNT(ID INT PRIMARY KEY AUTO_INCREMENT, USID INT, CRID INT," +
                    " MONEY NUMERIC(12,2), LOAN NUMERIC(12,2))";
            logArray[3] = "Adding foreign key of user ID to account.";
            sqlArray[3] = "ALTER TABLE ACCOUNT ADD FOREIGN KEY (USID) REFERENCES USERS(ID)";
            logArray[4] = "Adding foreign key of currency ID to account.";
            sqlArray[4] = "ALTER TABLE ACCOUNT ADD FOREIGN KEY (CRID) REFERENCES CURRENCY(ID)";
            logArray[5] = "Creating combined view for accounts.";
            sqlArray[5] = "CREATE VIEW ACC_VIEW AS SELECT ACCOUNT.ID, USERS.LAST_NAME, USERS.FIRST_NAME," +
                    " USERS.SECOND_NAME, USERS.AGE, USERS.NUMBER, MONEY, LOAN, CURRENCY.NAME ,CURRENCY.VALUE," +
                    " (ACCOUNT.MONEY * CURRENCY.VALUE) AS SUM_UAH FROM ACCOUNT JOIN CURRENCY" +
                    " ON ACCOUNT.CRID = CURRENCY.ID JOIN USERS ON ACCOUNT.USID = USERS.ID ";
            logArray[6] = "Creating table OPERATIONS.";
            sqlArray[6] = "CREATE TABLE OPERATIONS (ID INT PRIMARY KEY AUTO_INCREMENT, ACID_FROM INT," +
                    " ACID_TO INT, TYPE VARCHAR(255), SUBTYPE VARCHAR(255), SUM NUMERIC(12,2)," +
                    " CURRENCY_NAME VARCHAR(255), CURRENCY_VALUE NUMERIC(7,3), CRID INT, DATETIME OPERATION_TIME)";
            logArray[7] = "Adding foreign key of sender account ID to operations.";
            sqlArray[7] = "ALTER TABLE OPERATIONS ADD FOREIGN KEY (ACID_FROM) REFERENCES ACCOUNT(ID)";
            logArray[8] = "Adding foreign key of recipient account ID to operations.";
            sqlArray[8] = "ALTER TABLE OPERATIONS ADD FOREIGN KEY (ACID_TO) REFERENCES ACCOUNT(ID)";
            logArray[9] = "Adding foreign key of currency ID to operations.";
            sqlArray[9] = "ALTER TABLE OPERATIONS ADD FOREIGN KEY (CRID) REFERENCES ACCOUNT(ID)";
            logArray[10] = "Adding base currency to database.";
            sqlArray[10] = "INSERT INTO CURRENCY (ID, NAME, VALUE) VALUES (1, UAH, 1)";
            logArray[11] = "Adding admin into user database.";
            sqlArray[11] = "INSERT INTO USERS (ID, FIRST_NAME, SECOND_NAME, LAST_NAME, AGE, NUMBER) VALUES" +
                    " (42, ADMIN, BANHAMMER, GOD, 9999, 123456789012)";
            logArray[12] = "Adding admin into account database.";
            sqlArray[12] = "INSERT INTO ACCOUNT (ID, USID, CRID, MONEY, LOAN) VALUES (42, 42, 1, 100000000, 0)";
            logArray[13] = "Creating table LOGIN.";
            sqlArray[13] = "CREATE TABLE LOGIN (ID INT PRIMARY KEY AUTO_INCREMENT, LOGIN VARCHAR(255)," +
                    " PASSWORD VARCHAR(255), USID INT)";
            logArray[14] = "Adding foreign key of user ID to login.";
            sqlArray[14] = "ALTER TABLE LOGIN ADD FOREIGN KEY (USID) REFERENCES USERS(ID)";
            logArray[15] = "Adding admin into login database.";
            sqlArray[15] = "INSERT INTO LOGIN (ID, LOGIN, PASSWORD, USID) VALUES (42, ADMIN, PASSWORD, 42)";

            for (String sql : sqlArray) {
                log.info(logArray[i]);
                stmt.executeUpdate(sql);
                i++;
            }

            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred ", se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred ", se);
            }
        }
    }
}
