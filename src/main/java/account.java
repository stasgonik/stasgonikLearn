import java.sql.*;
import java.util.Scanner;

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

    public  double loanBase() {
        return loan * accountCurrency.getValue();
    }

    public static account createAccountAndUser() {
        System.out.println("Starting creation of new account.");
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
    public static account createAccount(user selected) {
        System.out.println("Starting creation of new account.");
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
        }
        return Cr;
    }

    @Override
    public String toString() {
        return "Master of account: "  + master + ",%n" + "currency of account is " + accountCurrency +
                ",%n" + "sum of money on account = %10.2f" + " " + accountCurrency.getName() +
                " ( In " + currencyDB.currencyFromDB(1).getName() +" : %10.2f" +
                " ),%n" + "your loan is %10.2f" + " " + accountCurrency.getName() +
                " ( In " + currencyDB.currencyFromDB(1).getName() + " : %10.2f" + " )%n";
    }
    public void printToConsole() {
        System.out.printf(this.toString(), money, currentSum(), loan, loanBase());
    }

    public static void commission (double com, int acidFrom) {
        account bank = accountDB.accountFromDB(constants.bank);
        bank.setMoney(bank.getMoney() + com);
        accountDB.updateMoney(constants.bank , bank.getMoney());
        operation commissionFrom = new operation (operationType.Commission, subtype.Withdraw,
                accountDB.accountFromDB(acidFrom).getAccountCurrency(),
                com / accountDB.accountFromDB(acidFrom).getAccountCurrency().getValue());
        operationDB.operationToDB_2acc(commissionFrom, acidFrom, constants.bank);
        operation commissionBank = new operation (operationType.Commission, subtype.Charge,
                bank.getAccountCurrency(), com);
        operationDB.operationToDB_2acc(commissionBank, acidFrom, constants.bank);

    }
    public static void transferMoney (double transferMoney, int acidFrom, int acidTo) {
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
        }
        else if(transferFrom.getMoney() < transferMoney + (commit * exchangeCommit) /
                transferFrom.getAccountCurrency().getValue() &&
                currencyDB.currencyGetID(transferFrom.getAccountCurrency()) !=
                currencyDB.currencyGetID(transferTo.getAccountCurrency()) &&
                accountDB.usidFromDB(acidFrom) != accountDB.usidFromDB(acidTo)) {
            System.out.println("Insufficient sum on account.");
        }
        else if (transferFrom.getMoney() < transferMoney + exchangeCommit /
                transferFrom.getAccountCurrency().getValue() &&
                accountDB.usidFromDB(acidFrom) == accountDB.usidFromDB(acidTo) &&
                currencyDB.currencyGetID(transferFrom.getAccountCurrency()) !=
                currencyDB.currencyGetID(transferTo.getAccountCurrency())) {
            System.out.println("Insufficient sum on account.");
        }
        else if (transferFrom.getMoney() < transferMoney &&
                accountDB.usidFromDB(acidFrom) == accountDB.usidFromDB(acidTo) &&
                currencyDB.currencyGetID(transferFrom.getAccountCurrency()) ==
                currencyDB.currencyGetID(transferTo.getAccountCurrency())) {
            System.out.println("Insufficient sum on account.");
        }
        else  if (transferMoney == 0) {
            System.out.println("Restricted operation : Transfer '0'.");
        }
        else if (transferMoney < 0) {
            System.out.println("Restricted operation : Transfer negative sum.");
        }
        else if (acidFrom == acidTo) {
            System.out.println("Restricted operation : Transfer to self.");
        }
        else if (acidFrom <= 0 || acidTo <= 0) {
            System.out.println("Restricted operation : Transfer by not existing accounts.");
        }
        else if (acidTo == constants.bank || acidFrom == constants.bank) {
            System.out.println("Restricted operation : Administration account involved.");
        }

        else {

            transferFrom.setMoney(transferFrom.getMoney() -
                    transferMoney - (commit + exchangeCommit) / transferFrom.getAccountCurrency().getValue());
            operation from = new operation(operationType.Transfer, subtype.Withdraw,
                    transferFrom.getAccountCurrency(), transferMoney);
            operationDB.operationToDB_2acc(from, acidFrom, acidTo);

            if (currencyDB.currencyGetID(transferFrom.getAccountCurrency()) !=
                    currencyDB.currencyGetID(transferTo.getAccountCurrency())) {
                System.out.println("Transferring money between accounts with" +
                        " different currency requires exchange operation.");
                System.out.println("Warning user! For this exchange operation you need to pay commission.");
                operation exFrom = new operation(operationType.Exchange, subtype.Withdraw,
                        transferFrom.getAccountCurrency(), transferMoney);
                operationDB.operationToDB_From(exFrom, acidFrom);
                operation exTo = new operation(operationType.Exchange, subtype.Charge,
                        transferTo.getAccountCurrency(), outMoney);
                operationDB.operationToDB_To(exTo, acidFrom);
                commission(exchangeCommit, acidFrom);
            }

            transferTo.setMoney(transferTo.getMoney() + outMoney);
            operation to = new operation(operationType.Transfer, subtype.Charge,
                    transferTo.getAccountCurrency(), outMoney);
            operationDB.operationToDB_2acc(to, acidFrom, acidTo);

            if (accountDB.usidFromDB(acidFrom) != accountDB.usidFromDB(acidTo)) {
                commission(commit, acidFrom);
            }

            accountDB.updateMoney(acidFrom, transferFrom.getMoney());
            accountDB.updateMoney(acidTo, transferTo.getMoney());

        }
    }
    public static void takeCredit (int acid, double credit) {
        account bank = accountDB.accountFromDB(constants.bank);
        account credited = accountDB.accountFromDB(acid);
        double alreadyLoan = credited.loanBase();
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
        else if (acid == constants.bank) {
            System.out.println("Restricted operation : Administration account involved.");
        }
        else if (bank.getMoney() < 2000000) {
            System.out.println("Attention user! Our bank credit line unavailable.");
            System.out.println("We will contact You when its will be available.");
            System.out.println("Operation restricted. For more details, please, contact our support line.");
        }
        else  if (acid <= 0) {
            System.out.println("Restricted operation : Account with negative or 0 ID do not exist.");
        }
        else {
           double percent = 1.08;
           if (currencyDB.currencyGetID(credited.getAccountCurrency()) == 1) {
               percent = 1.17;
           }
           double tempLoan = alreadyLoan + (newLoan * percent);
           double endLoan = tempLoan / credited.getAccountCurrency().getValue();
           double newMoney = credited.getMoney() + credit;
           bank.setMoney(bank.getMoney() - newLoan);
           accountDB.updateMoney(constants.bank, bank.getMoney());
           operation creditBank = new operation(operationType.Credit,
                    subtype.Withdraw, bank.getAccountCurrency(), newLoan);
           operationDB.operationToDB_2acc(creditBank, constants.bank, acid);
           accountDB.updateMoney(acid, newMoney);
           accountDB.updateLoan(acid, endLoan);
           operation creditUser = new operation(operationType.Credit,
                    subtype.Charge, credited.getAccountCurrency(), credit);
           operationDB.operationToDB_2acc(creditUser, constants.bank, acid);
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
            double fullPayment = credited.getLoan();
            double fullToBank = fullPayment * credited.getAccountCurrency().getValue();
            credited.setLoan(0);
            credited.setMoney(credited.getMoney() - fullPayment);
            accountDB.updateLoan(acid, credited.getLoan());
            accountDB.updateMoney(acid, credited.getMoney());
            operation from = new operation(operationType.Loan_repayment, subtype.Withdraw,
                    credited.getAccountCurrency(), fullPayment);
            operationDB.operationToDB_2acc(from, acid, constants.bank);
            account bank = accountDB.accountFromDB(constants.bank);
            bank.setMoney(bank.getMoney() + fullToBank);
            accountDB.updateMoney(constants.bank, bank.getMoney());
            operation to = new operation(operationType.Loan_repayment, subtype.Charge,
                    bank.getAccountCurrency(), fullToBank);
            operationDB.operationToDB_2acc(to, acid, constants.bank);
        }
        else if (acid == constants.bank) {
            System.out.println("Restricted operation : Administration account involved.");
        }
        else if (acid <= 0) {
            System.out.println("Restricted operation : Account with negative or 0 ID do not exist.");
        }
        else {
            double toBank = payment * credited.getAccountCurrency().getValue();
            credited.setMoney(credited.getMoney() - payment);
            credited.setLoan(credited.getLoan() - payment);
            accountDB.updateLoan(acid, credited.getLoan());
            accountDB.updateMoney(acid, credited.getMoney());
            operation from = new operation(operationType.Loan_repayment, subtype.Withdraw,
                    credited.getAccountCurrency(),  payment);
            operationDB.operationToDB_2acc(from, acid, constants.bank);
            account bank = accountDB.accountFromDB(constants.bank);
            bank.setMoney(bank.getMoney() + toBank);
            accountDB.updateMoney(constants.bank, bank.getMoney());
            operation to = new operation(operationType.Loan_repayment, subtype.Charge,
                    bank.getAccountCurrency(), toBank);
            operationDB.operationToDB_2acc(to, acid, constants.bank);
        }
    }
    public static void extraction (int acid, double extract) {
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
        }
        else  if (sender.getMoney() < extract + commit * 3 / sender.getAccountCurrency().getValue() &&
                currencyDB.currencyGetID(sender.getAccountCurrency()) != 1) {
            System.out.println("Warning user! Your extraction sum is larger than sum of money on your account.");
            System.out.println("Operation terminated.");
        }
        else if (acid == constants.bank) {
            System.out.println("Restricted operation : Administration account involved.");
        }
        else if (acid <= 0) {
            System.out.println("Restricted operation : Account with negative or 0 ID do not exist.");
        }
        else {
            commission(commit, acid);
            if (currencyDB.currencyGetID(sender.getAccountCurrency()) != 1) {
                operation exFrom = new operation(operationType.Exchange, subtype.Withdraw,
                        sender.getAccountCurrency(), extract);
                operationDB.operationToDB_From(exFrom, acid);
                operation exTo = new operation(operationType.Exchange, subtype.Charge,
                        currencyDB.currencyFromDB(1), payBase);
                operationDB.operationToDB_To(exTo, acid);
                commission(commit*2, acid);
            }
            sender.setMoney(sender.getMoney() - extract - commit / sender.getAccountCurrency().getValue());
            accountDB.updateMoney(acid, sender.getMoney());
            operation extraction = new operation(operationType.Output, subtype.Withdraw,
                    currencyDB.currencyFromDB(1), payBase);
            operationDB.operationToDB_From(extraction, acid);
        }
    }
    public static void charging (int acid, double charge) {
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
            System.out.println("Restricted operation : Administration account involved.");
        }
        else if (acid <= 0) {
            System.out.println("Restricted operation : Account with negative or 0 ID do not exist.");
        }
        else {
            commission(commit, acid);
            recipient.setMoney(recipient.getMoney() + chargeInCur - commit / recipient.getAccountCurrency().getValue());
            accountDB.updateMoney(acid, recipient.getMoney());
            operation adding = new operation(operationType.Input, subtype.Charge,
                    recipient.getAccountCurrency(), chargeInCur);
            operationDB.operationToDB_To(adding, acid);
        }
    }
        
}
class accountDB {
    static void accountToDB(account newAccount) {
        Connection conn = null;
        PreparedStatement st1 = null;
        PreparedStatement st2 = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "INSERT INTO ACCOUNT (USID, CRID, MONEY, LOAN) " +
                    "VALUES (?, ?, ?, ?)";
            String sqlSearch1 = "SELECT ID FROM USERS WHERE FIRST_NAME=? AND SECOND_NAME=? AND LAST_NAME=?" +
                    " AND AGE=?";
            String sqlSearch2 = "SELECT ID FROM CURRENCY WHERE NAME=?";

            st1 = conn.prepareStatement(sql);

            st2 = conn.prepareStatement(sqlSearch1);

            st2.setString(1, newAccount.getMaster().getFirstName());
            st2.setString(2, newAccount.getMaster().getSecondName());
            st2.setString(3, newAccount.getMaster().getLastName());
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
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

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
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

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
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "UPDATE ACCOUNT " + "SET MONEY=? WHERE ID=?";

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
    static void deleteAccount (int acid) {
        Connection conn = null;
        PreparedStatement st1 = null;
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String delete = "DELETE FROM ACCOUNT " + "WHERE ID=?";
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
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "UPDATE ACCOUNT " + "SET LOAN=? WHERE ID=?";

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
                long number = rs.getLong("NUMBER");
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
                System.out.printf("; Loan:  %10.2f", loan);
                System.out.print("; Currency name: " + curName);
                System.out.printf("; Currency value: %6.2f", value);
                System.out.printf("; Sum in %s: %10.2f%n", currencyDB.currencyFromDB(1).getName() ,sumBase);
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
    static int[] searchUserAccounts (int usid) {
        Connection conn = null;
        PreparedStatement st1 = null;
        int[] acids = new int[20];
        try {
            Class.forName(constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(constants.DB_URL,constants.USER,constants.PASS);

            String sql = "SELECT ID FROM ACCOUNT WHERE USID=?";
            st1 = conn.prepareStatement(sql);
            st1.setInt(1, usid);
            ResultSet rs = st1.executeQuery();
            int i = 0;

            while(rs.next()) {
                int acid = rs.getInt("ID");
                acids[i] = acid;
                i++;
            }
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
        return acids;
    }
}
