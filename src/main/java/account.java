import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class account {

    private user master;
    private currency accountCurrency;
    private double money;
    private double loan = 0;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getLoan() {
        return loan;
    }

    public void setLoan(double loan) {
        this.loan = loan;
    }

    public user getMaster() {
        return master;
    }

    public void setMaster(user master) {
        this.master = master;
    }

    public currency getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(currency accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    public account(user master, currency accountCurrency, double money, double loan) {
        this.master = master;
        this.accountCurrency = accountCurrency;
        this.money = money;
        this.loan = loan;
    }

    public account() {
    }

    public double currentSum() {
        return money * accountCurrency.getValue();
    }

    public  double loanUAH() {
        return loan * accountCurrency.getValue();
    }

    public static account createAccount() {
        System.out.println("Starting creation of new account.");
        Scanner sc = new Scanner(System.in);
        account Cr = new account();
        try {
            currency cur = currency.chooseCurrency();
            user user1 = user.createUser();
            validators.NumberValidator numberValidator = new validators.NumberValidator();
            int i =0;
            do {
                System.out.println("Enter sum of money for test account:");
                String temp = sc.nextLine();
                if (numberValidator.validate(temp)) {
                    int tempInt = Integer.parseInt (temp);
                    Cr.setMoney(tempInt);
                    i++;
                }
                else {
                    System.out.println("Incorrect sum format. Use only numbers!");
                }
            }
            while (i==0);
            Cr.setAccountCurrency(cur);
            Cr.setMaster(user1);
            accountDB.accountToDB(Cr);
        }
        catch (Exception ex) {
            ex.getMessage();
        }
        return Cr;
    }

    @Override
    public String toString() {
        return "Master of account: "  + master + ",%n" + "currency of account is " + accountCurrency +
                ",%n" + "sum of money on account = " + money + " " + accountCurrency.getName() + " ( In UAH : " +
                currentSum() + " ),%n" + "your loan is " + loan + " " + accountCurrency.getName() + " ( In UAH : " +
                loanUAH() + " )%n";
    }

    public static void transferMoney (double trMoney, int acidFrom, int acidTo) {
        account transferFrom = accountDB.accountFromDB(acidFrom);
        account transferTo = accountDB.accountFromDB(acidTo);
        if (transferFrom.getMoney() < trMoney) {
            System.out.println("Insufficient sum on account.");
        }
        else  if (trMoney == 0) {
            System.out.println("Restricted operation : Transfer '0'.");
        }
        else if (trMoney < 0) {
            System.out.println("Restricted operation : Transfer negative sum.");
        }
        else if (acidFrom == acidTo) {
            System.out.println("Restricted operation : Transfer to self.");
        }
        else {
            double trSum = trMoney * transferFrom.getAccountCurrency().getValue();
            double outMoney = trSum / transferTo.getAccountCurrency().getValue();
            transferTo.setMoney(transferTo.getMoney() + outMoney);
            transferFrom.setMoney(transferFrom.getMoney() - trMoney);
            accountDB.updateMoney(acidFrom, transferFrom.getMoney());
            accountDB.updateMoney(acidTo, transferTo.getMoney());
        }
    }

    public static void takeCredit (int acid, double credit) {
        account credited = accountDB.accountFromDB(acid);
        double alreadyLoan = credited.loanUAH();
        double newLoan = credit*credited.getAccountCurrency().getValue();
        if (alreadyLoan > 600000) {
            System.out.println("Attention user! Your loan to Our bank are already over limit 600000 UAH.");
            System.out.printf("Your loan on today is %.2f UAH.%n", alreadyLoan);
            System.out.println("Operation restricted. For more details, please, contact our support line.");
        }
        else if (newLoan > 500000){
            System.out.println("Attention user! Maximum credit limit in Our internet-bank is 500000 UAH.");
            System.out.println("Operation restricted. For more details, please, contact our support line.");
        }
        else if (alreadyLoan + newLoan > 700000) {
            System.out.println("Attention user! Maximum credit capacity in Our internet-bank is 700000 UAH.");
            System.out.printf("Your loan on today is %.2f UAH.%n", alreadyLoan);
            System.out.println("Operation restricted. For more details, please, contact our support line.");
        }
        else {
           double tempLoan = alreadyLoan + (newLoan * 1.05);
           double endLoan = tempLoan / credited.getAccountCurrency().getValue();
           double newMoney = credited.getMoney() + credit;
           accountDB.updateMoney(acid, newMoney);
           accountDB.updateLoan(acid, endLoan);
        }
    }

    public static void payCredit (int acid, double payment) {
        account credited = accountDB.accountFromDB(acid);
        if (credited.getLoan() <= 0) {
            System.out.println("Attention user! You don`t have loan in Our bank. Payment not required.");
            System.out.println("Operation terminated.");
        }
        else  if (payment > credited.getMoney()) {
            System.out.println("Warning user! Your payment is larger than sum of money on your account.");
            System.out.println("Operation terminated.");
        }
        else if(credited.getLoan() < payment) {
            System.out.println("Warning user! Your payment is larger than sum of Your loan.");
            System.out.println("Surplus money will be transferred back to your account.");
            double nPayment = credited.getLoan();
            credited.setLoan(0);
            credited.setMoney(credited.getMoney() - nPayment);
            accountDB.updateLoan(acid, credited.getLoan());
            accountDB.updateMoney(acid, credited.getMoney());
        }
        else {
            credited.setMoney(credited.getMoney() - payment);
            credited.setLoan(credited.getLoan() - payment);
            accountDB.updateLoan(acid, credited.getLoan());
            accountDB.updateMoney(acid, credited.getMoney());
        }
    }

    public static void extractPayment (int acid, double pay) {
        account credited = accountDB.accountFromDB(acid);
        if (credited.getMoney() < pay) {
            System.out.println("Warning user! Your payment is larger than sum of money on your account.");
            System.out.println("Operation terminated.");
        }
        else {
            credited.setMoney(credited.getMoney() - pay);
            accountDB.updateMoney(acid, credited.getMoney());
        }
    }
}
/*class numberValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String NAMES_PATTERN =
            "^[_0-9\\+]+$";
    public numberValidator() {
        pattern = Pattern.compile(NAMES_PATTERN);
    }
    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);

        return matcher.matches();
    }
}*/
class accountDB {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test2";
    static final String USER = "sa";
    static final String PASS = "";

    static void accountToDB(account newAccount) {
        Connection conn = null;
        PreparedStatement st1 = null;
        PreparedStatement st2 = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "INSERT INTO ACCOUNT (USID, CRID, MONEY, LOAN) " +
                    "VALUES (?, ?, ?, ?)";
            String sqlSearch1 = "SELECT ID FROM USERS WHERE FIRST_NAME=? AND SECOND_NAME=? AND LAST_NAME=?" +
                    " AND AGE=?";
            String sqlSearch2 = "SELECT ID FROM CURRENCY WHERE NAME=?";

            st1 = conn.prepareStatement(sql);

            st2 = conn.prepareStatement(sqlSearch1);

            st2.setString(1, newAccount.getMaster().getFirstName());
            st2.setString(2, newAccount.getMaster().getSecondName());
            st2.setString(3, newAccount.getMaster().getFamilyName());
            st2.setInt(4, newAccount.getMaster().getAge());

            ResultSet rs1 = st2.executeQuery();
            int USID = 0;
            while (rs1.next()) {
                USID = rs1.getInt("id");
            }

            st1.setInt(1, USID);

            st2 = conn.prepareStatement(sqlSearch2);

            st2.setString(1, newAccount.getAccountCurrency().getName());

            ResultSet rs2 = st2.executeQuery();
            int CRID = 0;
            while (rs2.next()) {
                CRID = rs2.getInt("id");
            }
            st1.setInt(2, CRID);

            st1.setDouble(3, newAccount.getMoney());
            st1.setDouble(4, newAccount.getLoan());

            st1.execute();

            st1.close();
            st2.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (st1 != null) st1.close();
                if (st2 != null) st2.close();
            } catch (SQLException se2) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    static account accountFromDB (int acid) {
        account search =new account();
        Connection conn = null;
        PreparedStatement st1 = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            String sql = "SELECT USID, CRID, MONEY, LOAN FROM ACCOUNT WHERE ID=?";
            st1 = conn.prepareStatement(sql);
            st1.setInt(1, acid);
            ResultSet rs = st1.executeQuery();

            int[] intsTemp = new int[2];
            double[] doublesTemp = new double[2];

            while(rs.next()) {
                int usid = rs.getInt("USID");
                intsTemp[0] = usid;
                int crid = rs.getInt("CRID");
                intsTemp[1] = crid;
                double money = rs.getDouble("MONEY");
                doublesTemp[0] = money;
                double loan = rs.getDouble("LOAN");
                doublesTemp[1] = loan;
            }
            search.setMoney(doublesTemp[0]);
            search.setLoan(doublesTemp[1]);
            search.setMaster(userDB.userFromDB(intsTemp[0]));
            search.setAccountCurrency(currencyDB.currencyFromDB(intsTemp[1]));

            rs.close();

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
        return search;
    }
    static int usidFromDB (int acid) {
        Connection conn = null;
        PreparedStatement st1 = null;
        int usid = 0;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            String sql = "SELECT USID FROM ACCOUNT WHERE ID=?";
            st1 = conn.prepareStatement(sql);
            st1.setInt(1, acid);
            ResultSet rs = st1.executeQuery();

            int[] intsTemp = new int[1];

            while(rs.next()) {
                usid = rs.getInt("USID");
                intsTemp[0] = usid;
            }
            usid = intsTemp[0];
            rs.close();

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
        return usid;
    }
    static void updateMoney (int acid, double newMoney) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            String sql = "UPDATE ACCOUNT " + "SET MONEY=? WHERE id=?";

            st1 = conn.prepareStatement(sql);
            st1.setDouble(1, newMoney);
            st1.setInt(2, acid);
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
    /*static void viewAccounts () {
        Connection conn = null;
        Statement stmt = null;
        try {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);

        stmt = conn.createStatement();
        String sql = "SELECT ID, USID, CRID, MONEY FROM ACCOUNT";
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()) {
            int id  = rs.getInt("ID");
            int usid = rs.getInt("USID");
            user usV = userDB.userFromDB(usid);
            int crid = rs.getInt("CRID");
            currency curV = currencyDB.currencyFromDB(crid);
            double money = rs.getDouble("MONEY");

            System.out.print("AccountID: " + id);
            System.out.print(", First name: " + usV.getFirstName());
            System.out.print(", Second name: " + usV.getSecondName());
            System.out.print(", Last name: " + usV.getFamilyName());
            System.out.print(", Age: " + usV.getAge());
            System.out.print(", Phone number: " + usV.getNumber());
            System.out.print(", CurrencyName: " + curV.getName());
            System.out.println(", Money: " + money);
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
}*/
    static void deleteAccount (int acid) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            String delete = "DELETE FROM ACCOUNT " + "WHERE id = ?";
            st1 = conn.prepareStatement(delete);
            st1.setInt(1, acid);
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
    static void updateLoan (int acid, double newLoan) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            String sql = "UPDATE ACCOUNT " + "SET LOAN=? WHERE id=?";

            st1 = conn.prepareStatement(sql);
            st1.setDouble(1, newLoan);
            st1.setInt(2, acid);
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
    static void viewAccounts () {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            stmt = conn.createStatement();
            String sql = "SELECT ID, LAST_NAME, FIRST_NAME, SECOND_NAME, AGE," +
                    " NUMBER, MONEY, LOAN, NAME, VALUE, CURRENT_SUM FROM ACC_VIEW";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int id  = rs.getInt("ID");
                String last = rs.getString("LAST_NAME");
                String first = rs.getString("FIRST_NAME");
                String second = rs.getString("SECOND_NAME");
                int age = rs.getInt("AGE");
                long number = rs.getLong("NUMBER");
                double money = rs.getDouble("MONEY");
                double loan = rs.getDouble("LOAN");
                String curName = rs.getString("NAME");
                double value = rs.getDouble("VALUE");
                double sumUAH = rs.getDouble("CURRENT_SUM");

                System.out.println("                                              | _---_ |");
                System.out.print("AccountID: " + id);
                System.out.print(", Last name: " + last);
                System.out.print(", First name: " + first);
                System.out.print(", Second name: " + second);
                System.out.print(", Age: " + age);
                System.out.println(", Phone number: " + number);
                System.out.print("     Money: " + money);
                System.out.print(", Loan: " + loan);
                System.out.print(", Currency name: " + curName);
                System.out.print(", Currency value: " + value);
                System.out.println(", Sum in UAH: " + sumUAH);
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
