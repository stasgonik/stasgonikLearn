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

            String[] sqlArray1 = new String[7];
            String[] logArray1 = new String[6];
            String[] sqlArray2 = new String[9];
            String[] logArray2 = new String[9];
            String[] sqlArray3 = new String[6];
            String[] logArray3 = new String[6];
            int i = 0;
            stmt = conn.createStatement();

            logArray1[0] = "Creating table USERS";
            sqlArray1[0] = "CREATE TABLE USERS(ID INT PRIMARY KEY AUTO_INCREMENT, FIRST_NAME VARCHAR(255)," +
                    " SECOND_NAME VARCHAR(255), LAST_NAME VARCHAR(255), AGE INT, NUMBER NUMERIC(13,0))";
            logArray1[1] = "Creating table CURRENCY";
            sqlArray1[1] = "CREATE TABLE CURRENCY(ID INT PRIMARY KEY AUTO_INCREMENT," +
                    " NAME VARCHAR(255), VALUE NUMERIC(7,3))";
            logArray1[2] = "Creating table ACCOUNT";
            sqlArray1[2] = "CREATE TABLE ACCOUNT(ID INT PRIMARY KEY AUTO_INCREMENT, USID INT, CRID INT," +
                    " MONEY NUMERIC(12,2), LOAN NUMERIC(12,2))";
            logArray1[3] = "Adding foreign key of user ID to account.";
            sqlArray1[3] = "ALTER TABLE ACCOUNT ADD FOREIGN KEY (USID) REFERENCES USERS(ID)";
            logArray1[4] = "Adding foreign key of currency ID to account.";
            sqlArray1[4] = "ALTER TABLE ACCOUNT ADD FOREIGN KEY (CRID) REFERENCES CURRENCY(ID)";
            logArray1[5] = "Creating combined view for accounts.";
            sqlArray1[5] = "CREATE VIEW ACC_VIEW AS SELECT ACCOUNT.ID, USERS.LAST_NAME, USERS.FIRST_NAME," +
                    " USERS.SECOND_NAME, USERS.AGE, USERS.NUMBER, MONEY, LOAN, CURRENCY.NAME ,CURRENCY.VALUE," +
                    " (ACCOUNT.MONEY * CURRENCY.VALUE) AS SUM_UAH FROM ACCOUNT JOIN CURRENCY" +
                    " ON ACCOUNT.CRID = CURRENCY.ID JOIN USERS ON ACCOUNT.USID = USERS.ID ";
            sqlArray1[6] = "SET foreign_key_checks = 0;";
            logArray3[0] = "Creating table OPERATIONS.";
            sqlArray3[0] = "CREATE TABLE OPERATIONS (ID INT PRIMARY KEY AUTO_INCREMENT, ACID_FROM INT," +
                    " ACID_TO INT, TYPE VARCHAR(255), SUBTYPE VARCHAR(255), SUM NUMERIC(12,2)," +
                    " CURRENCY_NAME VARCHAR(255), CURRENCY_VALUE NUMERIC(7,3), CRID INT, OPERATION_TIME DATETIME)";
            logArray3[1] = "Adding foreign key of sender account ID to operations.";
            sqlArray3[1] = "ALTER TABLE OPERATIONS ADD FOREIGN KEY (ACID_FROM) REFERENCES ACCOUNT(ID)";
            logArray3[2] = "Adding foreign key of recipient account ID to operations.";
            sqlArray3[2] = "ALTER TABLE OPERATIONS ADD FOREIGN KEY (ACID_TO) REFERENCES ACCOUNT(ID)";
            logArray3[3] = "Adding foreign key of currency ID to operations.";
            sqlArray3[3] = "ALTER TABLE OPERATIONS ADD FOREIGN KEY (CRID) REFERENCES ACCOUNT(ID)";
            logArray2[0] = "Adding base currency to database.";
            sqlArray2[0] = "INSERT INTO CURRENCY (ID, NAME, VALUE) VALUES ('1', 'UAH', '1')";
            logArray2[1] = "Adding admin into user database.";
            sqlArray2[1] = "INSERT INTO USERS (ID, FIRST_NAME, SECOND_NAME, LAST_NAME, AGE, NUMBER) VALUES" +
                    " ('42', 'ADMIN', 'BANHAMMER', 'GOD', '9999', '123456789012')";
            logArray2[2] = "Adding admin into account database.";
            sqlArray2[2] = "INSERT INTO ACCOUNT (ID, USID, CRID, MONEY, LOAN) VALUES ('42', '42', '1', '100000000', '0')";
            logArray3[4] = "Creating table LOGIN.";
            sqlArray3[4] = "CREATE TABLE LOGIN (ID INT PRIMARY KEY AUTO_INCREMENT, LOGIN VARCHAR(255)," +
                    " PASSWORD VARCHAR(255), USID INT)";
            logArray3[5] = "Adding foreign key of user ID to login.";
            sqlArray3[5] = "ALTER TABLE LOGIN ADD FOREIGN KEY (USID) REFERENCES USERS(ID)";
            logArray2[3] = "Adding admin into login database.";
            sqlArray2[3] = "INSERT INTO LOGIN (ID, LOGIN, PASSWORD, USID) VALUES ('42', 'ADMIN', 'PASSWORD', '42')";
            logArray2[4] = "Adding new currency.";
            sqlArray2[4] = "INSERT INTO CURRENCY (ID, NAME, VALUE) VALUES ('2', 'USD', '27')";
            logArray2[5] = "Adding new currency.";
            sqlArray2[5] = "INSERT INTO CURRENCY (ID, NAME, VALUE) VALUES ('3', 'EURO', '30')";
            logArray2[6] = "Adding new currency.";
            sqlArray2[6] = "INSERT INTO CURRENCY (ID, NAME, VALUE) VALUES ('4', 'RUB', '0.40')";
            logArray2[7] = "Adding new currency.";
            sqlArray2[7] = "INSERT INTO CURRENCY (ID, NAME, VALUE) VALUES ('5', 'PLZ', '4')";
            logArray2[8] = "Adding new currency.";
            sqlArray2[8] = "INSERT INTO CURRENCY (ID, NAME, VALUE) VALUES ('6', 'GBP', '32')";

            for (String sql : sqlArray1) {
                log.info(logArray1[i]);
                stmt.execute(sql);
                i++;
            }
            for (String sql : sqlArray3) {
                log.info(logArray3[i]);
                stmt.execute(sql);
                i++;
            }
            for (String sql : sqlArray2) {
                log.info(logArray2[i]);
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
