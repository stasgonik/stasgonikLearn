import org.jetbrains.annotations.NotNull;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.Scanner;

public class account {
    private static final Logger log = Logger.getLogger(account.class);

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

    public  double loanBase() {
        return loan / accountCurrency.getValue();
    }

    @NotNull
    public static account createAccountAndUser() {
        log.info("Starting creation of new account for new user.");
        Scanner sc = new Scanner(System.in);
        account Cr = new account();
        try {
            int crid = currency.chooseCurrency();
            currency cur = currencyDB.currencyFromDB(crid);
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
                    log.warn("Incorrect sum format set.");
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
            log.error("Exception occurred " , ex);
        }
        log.debug("Account creation end.");
        return Cr;
    }
    @NotNull
    public static account createAccount(user selected) {
        log.info("Starting creation of new account.");
        Scanner sc = new Scanner(System.in);
        account Cr = new account();
        try {
            int crid = currency.chooseCurrency();
            currency cur = currencyDB.currencyFromDB(crid);
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
                    log.warn("Incorrect sum format set.");
                    System.out.println("Incorrect sum format. Use only numbers!");
                }
            }
            while (i==0);
            Cr.setAccountCurrency(cur);
            Cr.setMaster(selected);
            accountDB.accountToDB(Cr);
        }
        catch (Exception ex) {
            ex.getMessage();
            log.error("Exception occurred " , ex);
        }
        log.debug("Account creation end.");
        return Cr;
    }

    @Override
    public String toString() {
        return "Master of account: "  + master + ",%n" + "currency of account is " + accountCurrency +
                ",%n" + "sum of money on account = %10.2f" + " " + accountCurrency.getName() +
                " ( In " + currencyDB.currencyFromDB(1).getName() +" : %10.2f" +
                " ),%n" + "your loan is %10.2f" + " " + currencyDB.currencyFromDB(1).getName() +
                " ( In " + accountCurrency.getName() + " : %10.2f" + " )%n";
    }
    public void printToConsole() {
        try {
            System.out.printf(this.toString(), money, currentSum(), loan, loanBase());
        }
        catch (Exception ex) {
            ex.getMessage();
            log.error("Exception occurred " , ex);
        }
    }

    public static void commission (double com, int acidFrom) {
        try {
            log.debug("Registering of new commission operations.");
            account bank = accountDB.accountFromDB(constants.bank);
            bank.setMoney(bank.getMoney() + com);
            accountDB.updateMoney(constants.bank , bank.getMoney());
            operation.createOperation(operationType.Commission, subtype.Withdraw,
                    accountDB.accountFromDB(acidFrom).getAccountCurrency(),
                    com / accountDB.accountFromDB(acidFrom).getAccountCurrency().getValue(),
                    acidFrom, constants.bank);
            operation.createOperation(operationType.Commission, subtype.Charge, bank.getAccountCurrency(),
                    com, acidFrom, constants.bank);
        }
        catch (Exception ex) {
            ex.getMessage();
            log.error("Exception occurred " , ex);
        }
    }
    public static void transferMoney (double transferMoney, int acidFrom, int acidTo) {
        try {
            log.debug("Transfer function start. " +
                    "Account initiator ID is " + acidFrom + "." +
                    " Account recipient ID is " + acidTo + ".");
            account transferFrom = accountDB.accountFromDB(acidFrom);
            account transferTo = accountDB.accountFromDB(acidTo);
            double transferSum = transferMoney * transferFrom.getAccountCurrency().getValue();
            double commit = 0;
            double exchangeCommit = 0;
            double outMoney = transferSum / transferTo.getAccountCurrency().getValue();

            if (transferSum < 10000){
                commit = 1 + transferSum * 0.009;
                exchangeCommit = 0.5 + transferSum * 0.0045;
            }
            else if(transferSum >= 10000 && transferSum < 100000) {
                commit = 5 + transferSum * 0.007;
                exchangeCommit = 2.5 + transferSum * 0.0035;
            }
            else if(transferSum >= 100000 && transferSum < 500000){
                commit = 10 + transferSum * 0.005;
                exchangeCommit = 5 + transferSum * 0.0025;
            }
            else if( transferSum >= 500000){
                commit = 20 + transferSum * 0.004;
                exchangeCommit = 10 + transferSum * 0.002;
            }

            if (accountDB.usidFromDB(acidTo) == accountDB.usidFromDB(acidFrom)) {
                commit = 0;
            }

            if (currencyDB.currencyGetID(transferFrom.getAccountCurrency()) ==
                    currencyDB.currencyGetID(transferTo.getAccountCurrency())) {
                exchangeCommit = 0;
            }

            if (transferFrom.getMoney() < transferMoney + commit / transferFrom.getAccountCurrency().getValue() &&
                    accountDB.usidFromDB(acidFrom) != accountDB.usidFromDB(acidTo)) {
                System.out.println("Insufficient sum on account.");
                log.info("Insufficient sum on account during transfer.");
            }
            else if(transferFrom.getMoney() < transferMoney + (commit * exchangeCommit) /
                    transferFrom.getAccountCurrency().getValue() &&
                    currencyDB.currencyGetID(transferFrom.getAccountCurrency()) !=
                            currencyDB.currencyGetID(transferTo.getAccountCurrency()) &&
                    accountDB.usidFromDB(acidFrom) != accountDB.usidFromDB(acidTo)) {
                System.out.println("Insufficient sum on account.");
                log.info("Insufficient sum on account during transfer.");
            }
            else if (transferFrom.getMoney() < transferMoney + exchangeCommit /
                    transferFrom.getAccountCurrency().getValue() &&
                    accountDB.usidFromDB(acidFrom) == accountDB.usidFromDB(acidTo) &&
                    currencyDB.currencyGetID(transferFrom.getAccountCurrency()) !=
                            currencyDB.currencyGetID(transferTo.getAccountCurrency())) {
                System.out.println("Insufficient sum on account.");
                log.info("Insufficient sum on account during transfer.");
            }
            else if (transferFrom.getMoney() < transferMoney &&
                    accountDB.usidFromDB(acidFrom) == accountDB.usidFromDB(acidTo) &&
                    currencyDB.currencyGetID(transferFrom.getAccountCurrency()) ==
                            currencyDB.currencyGetID(transferTo.getAccountCurrency())) {
                System.out.println("Insufficient sum on account.");
                log.info("Insufficient sum on account during transfer.");
            }
            else  if (transferMoney == 0) {
                System.out.println("Restricted operation : Transfer '0'.");
                log.warn("Attempt to transfer sum 0.");
            }
            else if (transferMoney < 0) {
                System.out.println("Restricted operation : Transfer negative sum.");
                log.warn("Attempt to transfer negative sum.");
            }
            else if (acidFrom == acidTo) {
                System.out.println("Restricted operation : Transfer to self.");
                log.warn("Self transfer attempt.");
            }
            else if (acidFrom <= 0 || acidTo <= 0) {
                System.out.println("Restricted operation : Transfer by not existing accounts.");
                log.warn("Transfer attempt by not existing accounts.");
            }
            else if (acidTo == constants.bank || acidFrom == constants.bank) {
                System.out.println("Restricted operation : Administration account involved.");
                log.warn("Transfer attempt with administration account.");
            }

            else {
                log.debug("Registering of new transfer operations.");
                transferFrom.setMoney(transferFrom.getMoney() -
                        transferMoney - (commit + exchangeCommit) / transferFrom.getAccountCurrency().getValue());
                operation.createOperation(operationType.Transfer, subtype.Withdraw, transferFrom.getAccountCurrency(),
                        transferMoney, acidFrom, acidTo);

                if (currencyDB.currencyGetID(transferFrom.getAccountCurrency()) !=
                        currencyDB.currencyGetID(transferTo.getAccountCurrency())) {
                    log.debug("Transferring money between accounts with" +
                            " different currency.");
                    log.debug("Registering of new exchange operations.");
                    operation.createOperation(operationType.Exchange, subtype.Withdraw,
                            transferFrom.getAccountCurrency(), transferMoney, acidFrom, 0);
                    operation.createOperation(operationType.Exchange, subtype.Charge,
                            transferTo.getAccountCurrency(), outMoney, 0, acidFrom);
                    commission(exchangeCommit, acidFrom);
                }

                transferTo.setMoney(transferTo.getMoney() + outMoney);
                operation.createOperation(operationType.Transfer, subtype.Charge,
                        transferTo.getAccountCurrency(), outMoney, acidFrom, acidTo);

                if (accountDB.usidFromDB(acidFrom) != accountDB.usidFromDB(acidTo)) {
                    commission(commit, acidFrom);
                }

                accountDB.updateMoney(acidFrom, transferFrom.getMoney());
                accountDB.updateMoney(acidTo, transferTo.getMoney());
                log.debug("Transfer operations end.");
            }
        }
        catch (Exception ex) {
            ex.getMessage();
            log.error("Exception occurred " , ex);
        }

    }
    public static void takeCredit (int acid, double credit) {
        try {
            log.debug("Take credit function start. Credited account ID is " + acid +".");
            account bank = accountDB.accountFromDB(constants.bank);
            account credited = accountDB.accountFromDB(acid);
            double alreadyLoan = credited.getLoan();
            double newLoan = credit * credited.getAccountCurrency().getValue();
            if (alreadyLoan > 600000) {
                System.out.println("Attention user! Your loan to Our bank are already over limit 600000 UAH.");
                System.out.printf("Your loan on today is %.2f UAH.%n", alreadyLoan);
                log.info("Credit take attempt with loan higher then 600000.");
                System.out.println("Operation restricted. For more details, please, contact our support line.");
            }
            else if (newLoan > 500000){
                System.out.println("Attention user! Maximum credit limit in Our internet-bank is 500000 UAH.");
                log.info("Credit take attempt with over limit 500000.");
                System.out.println("Operation restricted. For more details, please, contact our support line.");
            }
            else if (alreadyLoan + newLoan > 700000) {
                System.out.println("Attention user! Maximum credit capacity in Our internet-bank is 700000 UAH.");
                System.out.printf("Your loan on today is %.2f UAH.%n", alreadyLoan);
                log.info("Credit take attempt with end loan more then 700000.");
                System.out.println("Operation restricted. For more details, please, contact our support line.");
            }
            else if (credit == 0) {
                System.out.println("Restricted operation : Take '0'credit.");
                log.warn("Attempt to take credit of sum 0.");
            }
            else  if (credit < 0) {
                System.out.println("Restricted operation : Take credit of negative sum.");
                log.warn("Attempt to take credit of negative sum.");
            }
            else if (acid == constants.bank) {
                System.out.println("Restricted operation : Administration account involved.");
                log.warn("Credit attempt with administration account.");
            }
            else if (bank.getMoney() < 2000000) {
                System.out.println("Attention user! Our bank credit line unavailable.");
                System.out.println("We will contact You when its will be available.");
                log.error("WARNING! Credit operation was restricted. Bank account need to be recharged!");
                System.out.println("Operation restricted. For more details, please, contact our support line.");
            }
            else  if (acid <= 0) {
                System.out.println("Restricted operation : Account with negative or 0 ID do not exist.");
                log.warn("Credit attempt by not existing accounts.");
            }
            else {
                log.debug("Registering of new credit operations.");
                double percent = 1.08;
                if (currencyDB.currencyGetID(credited.getAccountCurrency()) == 1) {
                    percent = 1.17;
                }
                double endLoan = alreadyLoan + (newLoan * percent);
                double newMoney = credited.getMoney() + credit;
                bank.setMoney(bank.getMoney() - newLoan);
                accountDB.updateMoney(constants.bank, bank.getMoney());
                operation.createOperation(operationType.Credit,
                        subtype.Withdraw, bank.getAccountCurrency(), newLoan, constants.bank, acid);
                accountDB.updateMoney(acid, newMoney);
                accountDB.updateLoan(acid, endLoan);
                operation.createOperation(operationType.Credit,
                        subtype.Charge, credited.getAccountCurrency(), credit, constants.bank, acid);
                log.debug("Credit operations end.");
            }
        }
        catch (Exception ex) {
            ex.getMessage();
            log.error("Exception occurred " , ex);
        }

    }
    public static void payCredit (int acid, double payment) {
        try {
            log.debug("Start of loan repayment function. Credited account ID is " + acid + ".");
            account credited = accountDB.accountFromDB(acid);
            if (credited.getLoan() <= 0) {
                System.out.println("Attention user! You don`t have loan in Our bank. Payment not required.");
                System.out.println("Operation terminated.");
                log.info("Attempt to repay for 0 loan sum.");
            }
            else  if (payment > credited.getMoney()) {
                System.out.println("Warning user! Your payment is larger than sum of money on your account.");
                System.out.println("Operation terminated.");
                log.warn("Attempt to repay loan with sum larger then sum on account.");
            }
            else if(credited.loanBase() < payment) {
                System.out.println("Warning user! Your payment is larger than sum of Your loan.");
                log.info("Attempt to make negative loan with large repayment sum.");
                log.debug("Registering of new loan repayment operations..");
                System.out.println("Surplus money will be transferred back to your account.");
                double fullToBank = credited.getLoan();
                double fullPayment = fullToBank / credited.getAccountCurrency().getValue();
                credited.setLoan(0);
                credited.setMoney(credited.getMoney() - fullPayment);
                accountDB.updateLoan(acid, credited.getLoan());
                accountDB.updateMoney(acid, credited.getMoney());
                operation.createOperation(operationType.Loan_repayment, subtype.Withdraw,
                        credited.getAccountCurrency(), fullPayment, acid, constants.bank);
                account bank = accountDB.accountFromDB(constants.bank);
                bank.setMoney(bank.getMoney() + fullToBank);
                accountDB.updateMoney(constants.bank, bank.getMoney());
                operation.createOperation(operationType.Loan_repayment, subtype.Charge,
                        bank.getAccountCurrency(), fullToBank, acid, constants.bank);
            }
            else if (acid == constants.bank) {
                System.out.println("Restricted operation : Administration account involved.");
                log.warn("Loan repayment attempt with administration account.");
            }
            else if (acid <= 0) {
                System.out.println("Restricted operation : Account with negative or 0 ID do not exist.");
                log.warn("Loan repayment attempt by not existing accounts.");
            }
            else {
                log.debug("Registering of new loan repayment operations..");
                double toBank = payment * credited.getAccountCurrency().getValue();
                credited.setMoney(credited.getMoney() - payment);
                credited.setLoan(credited.getLoan() - toBank);
                accountDB.updateLoan(acid, credited.getLoan());
                accountDB.updateMoney(acid, credited.getMoney());
                operation.createOperation(operationType.Loan_repayment, subtype.Withdraw,
                        credited.getAccountCurrency(),  payment, acid, constants.bank);
                account bank = accountDB.accountFromDB(constants.bank);
                bank.setMoney(bank.getMoney() + toBank);
                accountDB.updateMoney(constants.bank, bank.getMoney());
                operation.createOperation(operationType.Loan_repayment, subtype.Charge, bank.getAccountCurrency(),
                        toBank, acid, constants.bank);
                log.debug("Loan repayment operations end.");
            }
        }
        catch (Exception ex) {
            ex.getMessage();
            log.error("Exception occurred " , ex);
        }
    }
    public static void extraction (int acid, double extract) {
        try {
            log.debug("Starting of extraction function. Account ID is " + acid + ".");
            account sender = accountDB.accountFromDB(acid);
            double payBase = extract * sender.getAccountCurrency().getValue();
            double commit = 0;
            if (payBase < 5000) {
                commit = payBase * 0.005;
            }
            else if (payBase >= 5000 && payBase < 100000) {
                commit = payBase * 0.003;
            }
            else if (payBase >= 100000) {
                commit = payBase * 0.002;
            }
            if (sender.getMoney() < extract + commit / sender.getAccountCurrency().getValue() &&
                    currencyDB.currencyGetID(sender.getAccountCurrency()) == 1) {
                System.out.println("Warning user! Your extraction sum is larger than sum of money on your account.");
                System.out.println("Operation terminated.");
                log.warn("Attempt to extract sum larger then sum on account.");
            }
            else  if (sender.getMoney() < extract + commit * 3 / sender.getAccountCurrency().getValue() &&
                    currencyDB.currencyGetID(sender.getAccountCurrency()) != 1) {
                System.out.println("Warning user! Your extraction sum is larger than sum of money on your account.");
                System.out.println("Operation terminated.");
                log.warn("Attempt to extract sum larger then sum on account.");
            }
            else if (acid == constants.bank) {
                System.out.println("Restricted operation : Administration account involved.");
                log.warn("Extraction attempt with administration account.");
            }
            else if (acid <= 0) {
                System.out.println("Restricted operation : Account with negative or 0 ID do not exist.");
                log.warn("Extraction attempt by not existing accounts.");
            }
            else {
                log.debug("Registering of new extract operation.");
                commission(commit, acid);
                if (currencyDB.currencyGetID(sender.getAccountCurrency()) != 1) {
                    log.debug("Registering of new exchange operations.");
                    operation.createOperation(operationType.Exchange,subtype.Withdraw, sender.getAccountCurrency(),
                            extract, acid, 0);
                    operation.createOperation(operationType.Exchange, subtype.Charge, currencyDB.currencyFromDB(1),
                            payBase, 0, acid);
                    commission(commit*2, acid);
                }
                sender.setMoney(sender.getMoney() - extract - commit / sender.getAccountCurrency().getValue());
                accountDB.updateMoney(acid, sender.getMoney());
                operation.createOperation(operationType.Output, subtype.Withdraw, currencyDB.currencyFromDB(1),
                        payBase, acid, 0);
                log.debug("Extract operation end.");
            }
        }
        catch (Exception ex) {
            ex.getMessage();
            log.error("Exception occurred " , ex);
        }

    }
    public static void charging (int acid, double charge) {
        try {
            log.debug("Start of charging function. Account id is " + acid + ".");
            account recipient = accountDB.accountFromDB(acid);
            double chargeInCur = charge / recipient.getAccountCurrency().getValue();
            double commit = 0;
            if (charge < 5000) {
                commit = charge * 0.003;
            }
            else if (charge >= 5000 && charge < 100000) {
                commit = charge * 0.002;
            }
            else if (charge >= 100000) {
                commit = charge * 0.001;
            }
            if (acid == constants.bank) {
                log.warn("Charge of administration account detected.");
            }
            if (acid <= 0) {
                System.out.println("Restricted operation : Account with negative or 0 ID do not exist.");
                log.warn("Charge attempt by not existing accounts.");
            }

            else {
                commission(commit, acid);
                recipient.setMoney(recipient.getMoney() + chargeInCur - commit / recipient.getAccountCurrency().getValue());
                accountDB.updateMoney(acid, recipient.getMoney());
                operation.createOperation(operationType.Input, subtype.Charge, recipient.getAccountCurrency(),
                        chargeInCur, 0, acid);
            }
        }
        catch (Exception ex) {
            ex.getMessage();
            log.error("Exception occurred " , ex);
        }
    }
}
class accountDB {
    private static final Logger log = Logger.getLogger(accountDB.class);
    static void accountToDB(account newAccount) {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        try {
            log.info("Starting to add new account to database.");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "INSERT INTO ACCOUNT (USID, CRID, MONEY, LOAN) " +
                    "VALUES (?, ?, ?, ?)";
            String sqlSearch1 = "SELECT ID FROM USERS WHERE FIRST_NAME=? AND SECOND_NAME=? AND LAST_NAME=?" +
                    " AND AGE=?";
            String sqlSearch2 = "SELECT ID FROM CURRENCY WHERE NAME=?";

            stmt1 = conn.prepareStatement(sql);

            stmt2 = conn.prepareStatement(sqlSearch1);

            stmt2.setString(1, newAccount.getMaster().getFirstName());
            stmt2.setString(2, newAccount.getMaster().getSecondName());
            stmt2.setString(3, newAccount.getMaster().getLastName());
            stmt2.setInt(4, newAccount.getMaster().getAge());

            ResultSet rs1 = stmt2.executeQuery();
            int USID = 0;
            while (rs1.next()) {
                USID = rs1.getInt("id");
            }

            stmt1.setInt(1, USID);

            stmt2 = conn.prepareStatement(sqlSearch2);

            stmt2.setString(1, newAccount.getAccountCurrency().getName());

            ResultSet rs2 = stmt2.executeQuery();
            int CRID = 0;
            while (rs2.next()) {
                CRID = rs2.getInt("id");
            }
            stmt1.setInt(2, CRID);

            stmt1.setDouble(3, newAccount.getMoney());
            stmt1.setDouble(4, newAccount.getLoan());

            stmt1.execute();

            rs1.close();
            rs2.close();

            stmt1.close();
            stmt2.close();
            conn.close();
        } catch (Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
        } finally {
            try {
                if (stmt1 != null) stmt1.close();
                if (stmt2 != null) stmt2.close();
            } catch (SQLException ignored) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred " , se);
            }
        }
    }
    @NotNull
    static account accountFromDB (int acid) {
        account search = new account();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            log.debug("Retrieving of account from database. Account ID is " + acid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "SELECT USID, CRID, MONEY, LOAN FROM ACCOUNT WHERE ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, acid);
            ResultSet rs = stmt.executeQuery();

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
            stmt.close();
            conn.close();

        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred " , se);
            }
        }
        return search;
    }
    static int usidFromDB (int acid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int usid = 0;
        try {
            log.debug("Retrieving user ID from account database. Account ID is " + acid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "SELECT USID FROM ACCOUNT WHERE ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, acid);
            ResultSet rs = stmt.executeQuery();

            int[] intsTemp = new int[1];

            while(rs.next()) {
                usid = rs.getInt("USID");
                intsTemp[0] = usid;
            }
            usid = intsTemp[0];

            rs.close();
            stmt.close();
            conn.close();

        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred " , se);
            }
        }
        return usid;
    }
    static void updateMoney (int acid, double newMoney) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.debug("Updating value of money in account database. Account ID is " + acid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "UPDATE ACCOUNT " + "SET MONEY=? WHERE ID=?";

            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, newMoney);
            stmt.setInt(2, acid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred " , se);
            }
        }
    }
    static void deleteAccount (int acid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            log.info("Attempt to delete account. Account ID is " + acid +".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String delete = "DELETE FROM ACCOUNT " + "WHERE ID=?";
            stmt = conn.prepareStatement(delete);
            stmt.setInt(1, acid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred " , se);
            }
        }
    }
    static void updateLoan (int acid, double newLoan) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            log.debug("Updating value of loan in account database. Account ID is " + acid + ".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "UPDATE ACCOUNT " + "SET LOAN=? WHERE ID=?";

            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, newLoan);
            stmt.setInt(2, acid);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred " , se);
            }
        }
    }
    static void viewAccounts () {
        Connection conn = null;
        Statement stmt = null;
        try {
            log.warn("Viewing all accounts function activated!");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            stmt = conn.createStatement();
            String sql = "SELECT ID, LAST_NAME, FIRST_NAME, SECOND_NAME, AGE," +
                    " NUMBER, MONEY, LOAN, NAME, VALUE, CURRENT_SUM FROM ACC_VIEW WHERE NOT ID=42";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int id  = rs.getInt("ID");
                String last = rs.getString("LAST_NAME");
                String first = rs.getString("FIRST_NAME");
                String second = rs.getString("SECOND_NAME");
                int age = rs.getInt("AGE");
                String number = rs.getString("NUMBER");
                double money = rs.getDouble("MONEY");
                double loan = rs.getDouble("LOAN");
                String curName = rs.getString("NAME");
                double value = rs.getDouble("VALUE");
                double sumBase = rs.getDouble("CURRENT_SUM");

                System.out.println("                                              | _---_ |");
                System.out.print("AccountID: " + id);
                System.out.print("; Last name: " + last);
                System.out.print("; First name: " + first);
                System.out.print("; Second name: " + second);
                System.out.print("; Age: " + age);
                System.out.println("; Phone number: " + number);
                System.out.printf("     Money: %10.2f" , money);
                System.out.printf("; Loan:  %10.2f %S", loan, currencyDB.currencyFromDB(1).getName());
                System.out.print("; Currency name: " + curName);
                System.out.printf("; Currency value: %6.2f", value);
                System.out.printf("; Sum in %s: %10.2f%n", currencyDB.currencyFromDB(1).getName() ,sumBase);
                System.out.println(" ");

            }

            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred " , se);
            }
        }
    }
    @org.jetbrains.annotations.NotNull
    static int[] searchUserAccounts (int usid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int[] acids = new int[20];
        try {
            log.info("Viewing all accounts of selected user. User ID is " + usid +".");
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "SELECT ID FROM ACCOUNT WHERE USID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usid);
            ResultSet rs = stmt.executeQuery();
            int i = 0;

            while(rs.next()) {
                int acid = rs.getInt("ID");
                acids[i] = acid;
                i++;
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
                log.error("Exception occurred " , se);
        }
        }
        return acids;
    }
    static boolean countAccountCheck (int usid) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean checkLess20 = true;
        int count = 0;
        try {

            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "SELECT COUNT(ID) FROM ACCOUNT WHERE USID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usid);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                count = rs.getInt("COUNT(ID)");
                System.out.println(count);
            }
            checkLess20 = count < 20;
            if (!checkLess20) {
                log.warn("User with ID " + usid + " already have 20 accounts.");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            log.error("Exception occurred " , se);
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
        return checkLess20;
    }
}
