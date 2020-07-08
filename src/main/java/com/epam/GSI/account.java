package com.epam.GSI;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

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
        return loan * accountCurrency.getValue();
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
                " ),%n" + "your loan is %10.2f" + " " + accountCurrency.getName() +
                " ( In " + currencyDB.currencyFromDB(1).getName() + " : %10.2f" + " )%n";
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
            double alreadyLoan = credited.getLoan() * credited.getAccountCurrency().getValue();
            double newLoan = credit * credited.getAccountCurrency().getValue();
            if (alreadyLoan > 900000) {
                System.out.println("Attention user! Your loan to Our bank are already over limit 600000 UAH.");
                System.out.printf("Your loan on today is %.2f UAH.%n", alreadyLoan);
                log.info("Credit take attempt with loan higher then 600000.");
                System.out.println("Operation restricted. For more details, please, contact our support line.");
            }
            else if (newLoan > 700000){
                System.out.println("Attention user! Maximum credit limit in Our internet-bank is 500000 UAH.");
                log.info("Credit take attempt with over limit 500000.");
                System.out.println("Operation restricted. For more details, please, contact our support line.");
            }
            else if (alreadyLoan + newLoan > 1000000) {
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
                double endLoan = credited.getLoan() + (newLoan * percent) / credited.getAccountCurrency().getValue();
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
            else if(credited.getLoan() < payment) {
                System.out.println("Warning user! Your payment is larger than sum of Your loan.");
                log.info("Attempt to make negative loan with large repayment sum.");
                log.debug("Registering of new loan repayment operations..");
                System.out.println("Surplus money will be transferred back to your account.");
                double fullToBank = credited.getLoan() * credited.getAccountCurrency().getValue();
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
                    operation.createOperation(operationType.Exchange, subtype.Withdraw, sender.getAccountCurrency(),
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

