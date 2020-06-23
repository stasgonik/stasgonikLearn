import java.util.*;

public class application {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int i = 0;
        try {
            do {
                System.out.println("Please choose Your next action(type number):");
                System.out.println("1. Create new account.");
                System.out.println("2. Change currency value.");
                System.out.println("3. Update account detail.");
                System.out.println("4. Delete account and user.");
                System.out.println("5. Add new currency to DB.");
                System.out.println("6. View all currencies.");
                System.out.println("7. Delete currency.");
                System.out.println("8. Transfer money.");
                System.out.println("9. Take credit.");
                System.out.println("10. Pay credit.");
                System.out.println("1Q. Exit.");
                switch (sc.nextLine().toLowerCase()) {
                    case "1" :
                        account newAccount = account.createAccount();
                        System.out.printf(newAccount.toString());
                        break;

                    case "2" :
                        int crid;
                        int m = 0;
                        do {
                            crid = currency.chooseCurrency();
                            if (crid == 1) {
                                System.out.println("Restricted operation: You cannot change value of base currency.");
                            }
                            else if (crid <= 0) {
                                System.out.println("Currency ID 0 or Negative: Currency do not exist." +
                                        " Please repeat your set.");
                            }
                            else {
                                m++;
                            }
                        }
                        while (m==0);
                        currency cur = currencyDB.currencyFromDB(crid);
                        validators.NumberValidator numberValidator = new validators.NumberValidator();
                        double newValue = 0;
                        do {
                            System.out.println("Set new value for this currency:");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                newValue = Double.parseDouble(temp);
                                if (newValue <= 0) {
                                    System.out.println("Restricted operation : Negative or 0 value currency.");
                                }
                                else {
                                    m++;
                                }

                            }
                            else {
                                System.out.println("Incorrect currency value format. Use only numbers!");
                            }
                        }
                        while (m==1);
                        cur.setValue(newValue);
                        currencyDB.currencyUpdate(cur);
                        currencyDB.viewCurrency();
                        break;

                    case "3" :
                        accountDB.viewAccounts();
                        int acid = 0;
                        numberValidator = new validators.NumberValidator();
                        validators.NameValidator nameValidator = new validators.NameValidator();
                        m = 0;
                        do {
                            System.out.println("Set ID for update:");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                acid = Integer.parseInt (temp);
                                if (acid == 42) {
                                    System.out.println("Restricted operation : Restricted access to account.");
                                }
                                else if (acid <= 0) {
                                    System.out.println("There is no users with negative or 0 ID." +
                                            " Please, repeat your set.");
                                }
                                else {
                                    m++;
                                }

                            }
                            else {
                                System.out.println("Incorrect ID format. Use only numbers!");
                            }
                        }
                        while (m==0);

                            int k = 0;
                            int usid = accountDB.usidFromDB(acid);
                            do {
                                account changed = accountDB.accountFromDB(acid);
                                System.out.printf(changed.toString());
                                System.out.println("What do you want to change(type number):");
                                System.out.println("131. First name.");
                                System.out.println("132. Second name.");
                                System.out.println("133. Last name.");
                                System.out.println("134. Age.");
                                System.out.println("135. Phone number.");
                                System.out.println("13Q. Exit.");
                                switch (sc.nextLine().toLowerCase()) {
                                    case "131" :
                                        m = 0;
                                        do {
                                            System.out.println("Set new first name:");
                                            String temp = sc.nextLine();
                                            if (nameValidator.validate(temp)) {
                                                userDB.updateFName(usid, temp);
                                                m++;
                                            }
                                            else {
                                                System.out.println("Incorrect name format." +
                                                        " Use only latin letters or {-} !");
                                            }
                                        }
                                        while (m==0);
                                        break;
                                    case "132" :
                                        m = 0;
                                        do {
                                            System.out.println("Set new second name:");
                                            String temp = sc.nextLine();
                                            if (nameValidator.validate(temp)) {
                                                userDB.updateSName(usid, temp);
                                                m++;
                                            }
                                            else {
                                                System.out.println("Incorrect name format." +
                                                        " Use only latin letters or {-} !");
                                            }
                                        }
                                        while (m==0);
                                        break;
                                    case "133" :
                                        m = 0;
                                        do {
                                            System.out.println("Set new last name:");
                                            String temp = sc.nextLine();
                                            if (nameValidator.validate(temp)) {
                                                userDB.updateLName(usid, temp);
                                                m++;
                                            }
                                            else {
                                                System.out.println("Incorrect name format." +
                                                        " Use only latin letters or {-} !");
                                            }
                                        }
                                        while (m==0);
                                        break;
                                    case "134" :
                                        m = 0;
                                        do {
                                            System.out.println("Set new age:");
                                            String temp = sc.nextLine();
                                            if (numberValidator.validate(temp)) {
                                                int tempInt = Integer.parseInt (temp);
                                                if (tempInt < 0) {
                                                    System.out.println("Attention user: Negative age is prohibited.");
                                                }
                                                else {
                                                    userDB.updateAge(usid, tempInt);
                                                    m++;
                                                }
                                            }
                                            else {
                                                System.out.println("Incorrect age format. Use only numbers!");
                                            }
                                        }
                                        while (m==0);
                                        break;
                                    case "135" :
                                        m = 0;
                                        do {
                                            System.out.println("Set new phone number (Format: 380971234567):");
                                            String temp = sc.nextLine();
                                            if (numberValidator.validate(temp)) {
                                                long tempLong = Long.parseLong (temp);
                                                double check = 100000000000L;
                                                if (tempLong < check) {
                                                    System.out.println("Need your Ukrainian" +
                                                            " number in 12 digit format!");
                                                }
                                                else if (tempLong > check*10) {
                                                    System.out.println("Need your Ukrainian" +
                                                            " number in 12 digit format!");
                                                }
                                                else {
                                                    userDB.updateNumber(usid, tempLong);
                                                    i++;
                                                }
                                                m++;
                                            }
                                            else {
                                                System.out.println("Incorrect phone number format." +
                                                        " Must be 12-digit number!");
                                            }
                                        }
                                        while (m==0);
                                        break;
                                    case "13q" :
                                        k++;
                                        break;
                                    default  :
                                        System.out.println("Unknown action. Please, try again.");
                                        break;
                                }
                            }
                            while (k==0);
                        break;
                    case "4" :
                        acid = 0;
                        numberValidator = new validators.NumberValidator();
                        accountDB.viewAccounts();
                        m = 0;
                        do {
                            System.out.println("Set ID of account to delete (or type 0 to quit):");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                acid = Integer.parseInt(temp);
                                if (acid == 42) {
                                    System.out.println("Restricted operation : Restricted access.");
                                }
                                else if (acid < 0){
                                    System.out.println("There is no users with negative ID." +
                                            " Please, repeat your set.");
                                }
                                else {
                                    m++;
                                }
                            }
                            else {
                                System.out.println("Incorrect ID format. Use only numbers!");
                            }
                        }
                        while (m==0);
                        int usidDel = accountDB.usidFromDB(acid);
                        accountDB.deleteAccount(acid);
                        userDB.deleteUser(usidDel);
                        accountDB.viewAccounts();
                        break;

                    case "5" :
                        System.out.println("List of currencies in DB:");
                        currencyDB.viewCurrency();
                        System.out.println("Are you want to create new currency (Y/N)");
                        String choice = sc.nextLine().toLowerCase();
                        if (choice.equals("y") || choice.equals("yes")) {
                            System.out.println("Set new currency!");
                            currency.createCurrency();
                        }
                        else if (choice.equals("n") || choice.equals("no")) {
                            System.out.println("OK.");
                        }
                        else {
                            System.out.println("We will count this as No.");
                        }
                        break;
                    case "6" :
                        System.out.println("List of currencies in DB:");
                        currencyDB.viewCurrency();
                        break;
                    case  "7" :
                        currencyDB.viewCurrency();
                        System.out.println("Are you sure, that you want to delete currency? (Y/N)");
                        choice = sc.nextLine().toLowerCase();
                        if (choice.equals("y") || choice.equals("yes")) {
                            numberValidator = new validators.NumberValidator();
                            crid = 0;
                            m = 0;
                            do {
                                System.out.println("Set ID of currency to delete:");
                                String temp = sc.nextLine();
                                if (numberValidator.validate(temp)) {
                                    crid = Integer.parseInt (temp);
                                    if (crid == 1) {
                                        System.out.println("Restricted operation: Prohibited to delete base currency.");
                                    }
                                    else if (crid <= 0) {
                                        System.out.println("Currency with negative or 0 ID do not exist.");
                                    }
                                    else {
                                        m++;
                                    }
                                }
                                else {
                                    System.out.println("Incorrect ID format. Use only numbers!");
                                }
                            }
                            while (m==0);
                            currencyDB.deleteCurrency(crid);
                        }
                        else if (choice.equals("n") || choice.equals("no")) {
                            System.out.println("OK.");
                        }
                        else {
                            System.out.println("We will count this as No.");
                        }
                        break;

                    case "8" :
                        numberValidator = new validators.NumberValidator();
                        accountDB.viewAccounts();
                        int acidFrom = 0;
                        m = 0;
                        do {
                            System.out.println("Set id of initial account:");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                acidFrom = Integer.parseInt (temp);
                                if (acidFrom == 42) {
                                    System.out.println("Restricted operation : Restricted access.");
                                }
                                else if (acidFrom <= 0) {
                                    System.out.println("Account with negative or 0 ID do not exist.");
                                }
                                else {
                                    m++;
                                }
                            }
                            else {
                                System.out.println("Incorrect ID format. Use only numbers!");
                            }
                        }
                        while (m==0);
                        accountDB.viewAccounts();
                        int acidTo = 0;
                        do {
                            System.out.println("Set id of destination account:");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                acidTo = Integer.parseInt (temp);
                                if (acidTo == 42) {
                                    System.out.println("Restricted operation : Restricted access.");
                                }
                                else if (acidTo <= 0) {
                                    System.out.println("Account with negative or 0 ID do not exist.");
                                }
                                else {
                                    m++;
                                }
                            }
                            else {
                                System.out.println("Incorrect ID format. Use only numbers!");
                            }
                        }
                        while (m==1);
                        System.out.println("Initial account:");
                        System.out.printf(accountDB.accountFromDB(acidFrom).toString());
                        System.out.println("Destination account:");
                        System.out.printf(accountDB.accountFromDB(acidTo).toString());
                        double trMoney = 0;
                        do {
                            System.out.println("Set sum of money (in initial currency), which you want to send:");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                trMoney = Double.parseDouble (temp);
                                if (trMoney < 0) {
                                    System.out.println("Restricted operation: Negative or 0 sum of money.");
                                }
                                else {
                                    m++;
                                }
                            }
                            else {
                                System.out.println("Incorrect money format. Use only numbers!");
                            }
                        }
                        while (m==2);
                        account.transferMoney(trMoney, acidFrom, acidTo);
                        accountDB.viewAccounts();
                        break;

                    case "9" :
                        numberValidator = new validators.NumberValidator();
                        accountDB.viewAccounts();
                        acid = 0;
                        m = 0;
                        do {
                            System.out.println("Set ID for credited account:");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                acid = Integer.parseInt (temp);
                                if (acid == 42) {
                                    System.out.println("Restricted operation : Restricted access.");
                                }
                                else if (acid <= 0) {
                                    System.out.println("Account with negative or 0 ID do not exist.");
                                }
                                else {
                                    m++;
                                }
                            }
                            else {
                                System.out.println("Incorrect ID format. Use only numbers!");
                            }
                        }
                        while (m==0);
                        account credited = accountDB.accountFromDB(acid);
                        System.out.printf(credited.toString());
                        double credit = 0;
                        do {
                            System.out.println("Set credit sum (in account currency)");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                credit = Double.parseDouble (temp);
                                if (credit <= 0) {
                                    System.out.println("Restricted operation: Negative or 0 sum of money.");
                                }
                                else {
                                    m++;
                                }
                            }
                            else {
                                System.out.println("Incorrect money format. Use only numbers!");
                            }
                        }
                        while (m==1);
                        account.takeCredit(acid, credit);
                        credited = accountDB.accountFromDB(acid);
                        System.out.printf(credited.toString());
                        break;

                    case "10" :
                        numberValidator = new validators.NumberValidator();
                        accountDB.viewAccounts();
                        acid = 0;
                        m = 0;
                        do {
                            System.out.println("Set ID for credited account:");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                acid = Integer.parseInt (temp);
                                if (acid == 42) {
                                    System.out.println("Restricted operation : Restricted access.");
                                }
                                else if (acid <= 0) {
                                    System.out.println("Account with negative or 0 ID do not exist.");
                                }
                                else {
                                    m++;
                                }
                            }
                            else {
                                System.out.println("Incorrect ID format. Use only numbers!");
                            }
                        }
                        while (m==0);
                        credited = accountDB.accountFromDB(acid);
                        System.out.printf(credited.toString());
                        double payment = 0;
                        do {
                            System.out.println("Set payment sum (in account currency)");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                payment = Double.parseDouble (temp);
                                if (payment <= 0) {
                                    System.out.println("Restricted operation: Negative or 0 sum of money.");
                                }
                                else {
                                    m++;
                                }
                            }
                            else {
                                System.out.println("Incorrect money format. Use only numbers!");
                            }
                        }
                        while (m==1);
                        account.payCredit(acid, payment);
                        credited = accountDB.accountFromDB(acid);
                        System.out.printf(credited.toString());
                        break;

                    case "1q" :
                        i++;
                        break;

                    default:
                        System.out.println("Unknown action. Please, try again.");
                        break;
                }
            }
            while (i==0);
        }
        catch (Exception ex) {
            ex.getMessage();
        }
    }
}

