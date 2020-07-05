import org.apache.log4j.Logger;

import java.util.*;

public class application {

    public static void main(String[] args) {
        final Logger log = Logger.getLogger(application.class);
        Scanner sc = new Scanner(System.in);
        int i = 0;
        try {
            do {
                System.out.println("Please choose Your next action(type number):");
                System.out.println("1. Create new account/user pair.");
                System.out.println("1A. Create new account for existing user.");
                System.out.println("2. Change currency value.");
                System.out.println("3. Update account detail.");
                System.out.println("4. Delete account and user.");
                System.out.println("4A. Delete account of selected user.");
                System.out.println("5. Add new currency to DB.");
                System.out.println("6. View all currencies.");
                System.out.println("7. Delete currency.");
                System.out.println("8. Transfer money.");
                System.out.println("9. Take credit.");
                System.out.println("10. Pay credit.");
                System.out.println("11. Change currency name.");
                System.out.println("12. Operation view.");
                System.out.println("13. Charge account.");
                System.out.println("14. Extract money from account.");
                System.out.println("1Q. Exit.");
                switch (sc.nextLine().toLowerCase()) {
                    case "1" :
                        account newAccount = account.createAccountAndUser();
                        newAccount.printToConsole();
                        break;
                    case "1a":
                        userDB.viewUsers();
                        int usid = 0;
                        validators.NumberValidator numberValidator = new validators.NumberValidator();
                        int m = 0;
                        do {
                            System.out.println("Type your user ID:");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                usid = Integer.parseInt(temp);
                                if (usid <= 0) {
                                    System.out.println("Restricted operation : Negative or 0 ID.");
                                }
                                else if (!accountDB.countAccountCheck(usid)) {
                                    System.out.println("Restricted operation : This user already have 20 accounts.");
                                }
                                else {
                                    m++;
                                }

                            }
                            else {
                                System.out.println("Incorrect currency value format. Use only numbers!");
                            }
                        }
                        while (m == 0);
                        user selectedUser = userDB.userFromDB(usid);
                        account newForUser = account.createAccount(selectedUser);
                        newForUser.printToConsole();
                        break;
                    case "2" :
                        int crid;
                        m = 0;
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
                        while (m == 0);
                        numberValidator = new validators.NumberValidator();
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
                        while (m == 1);
                        currencyDB.currencyUpdateValue(crid, newValue);
                        break;

                    case "3" :
                        userDB.viewUsers();
                        usid = 0;
                        numberValidator = new validators.NumberValidator();
                        validators.NameValidator nameValidator = new validators.NameValidator();
                        validators.PhoneValidator phoneValidator = new validators.PhoneValidator();
                        m = 0;
                        do {
                            System.out.println("Set ID for update:");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                usid = Integer.parseInt (temp);
                                if (usid == constants.bank) {
                                    System.out.println("Restricted operation : Restricted access to account.");
                                }
                                else if (usid <= 0) {
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
                        while (m == 0);
                            int k = 0;
                            do {
                                user changed = userDB.userFromDB(usid);
                                System.out.println(changed.toString());
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
                                        while (m == 0);
                                        break;
                                    case "135" :
                                        m = 0;
                                        do {
                                            System.out.println("Set new phone number:");
                                            String temp = sc.nextLine();
                                            if (phoneValidator.validate(temp)) {
                                                if (userDB.checkNumber(temp)) {
                                                    System.out.println("We already have this number in database.");
                                                    System.out.println("Please, use another number for new user.");
                                                }
                                                else {
                                                    userDB.updateNumber(usid, temp);
                                                    m++;
                                                }
                                            }
                                            else {
                                                System.out.println("Incorrect phone number format.");
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
                            while (k == 0);
                        break;
                    case "4" :
                        int usidDel = 0;
                        numberValidator = new validators.NumberValidator();
                        userDB.viewUsers();
                        m = 0;
                        do {
                            System.out.println("Set ID of user to delete (or type 0 to quit):");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                usidDel = Integer.parseInt(temp);
                                if (usidDel == constants.bank) {
                                    System.out.println("Restricted operation : Restricted access.");
                                }
                                else if (usidDel < 0){
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
                        while (m == 0);
                        int[] accountsID = accountDB.searchUserAccounts(usidDel);
                        for (int id : accountsID) {
                            if (id != 0) {
                                accountDB.deleteAccount(id);
                            }
                        }
                        loginDB.deleteLogin(usidDel);
                        userDB.deleteUser(usidDel);
                        break;

                    case "4a" :
                        usidDel = 0;
                        numberValidator = new validators.NumberValidator();
                        userDB.viewUsers();
                        m = 0;
                        do {
                            System.out.println("Set ID of user(or type 0 to quit):");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                usidDel = Integer.parseInt(temp);
                                if (usidDel == constants.bank) {
                                    System.out.println("Restricted operation : Restricted access.");
                                }
                                else if (usidDel < 0){
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
                        while (m == 0);
                        accountsID = accountDB.searchUserAccounts(usidDel);
                        for (int id : accountsID) {
                            if (id != 0) {
                                System.out.println("Account ID: " + id);
                                accountDB.accountFromDB(id).printToConsole();
                            }
                        }
                        int acid = 0;
                        do {
                            System.out.println("Set ID of account to delete (or type 0 to quit):");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                acid = Integer.parseInt(temp);
                                if (acid == constants.bank) {
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
                        while (m == 1);
                        accountDB.deleteAccount(acid);
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
                            while (m == 0);
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
                                if (acidFrom == constants.bank) {
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
                        while (m == 0);
                        int acidTo = 0;
                        do {
                            System.out.println("Set id of destination account:");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                acidTo = Integer.parseInt (temp);
                                if (acidTo == constants.bank) {
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
                        while (m == 1);
                        System.out.println("Initial account:");
                        accountDB.accountFromDB(acidFrom).printToConsole();
                        System.out.println("Destination account:");
                        accountDB.accountFromDB(acidTo).printToConsole();
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
                        while (m == 2);
                        account.transferMoney(trMoney, acidFrom, acidTo);
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
                                if (acid == constants.bank) {
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
                        while (m == 0);
                        account credited = accountDB.accountFromDB(acid);
                        credited.printToConsole();
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
                        while (m == 1);
                        account.takeCredit(acid, credit);
                        credited = accountDB.accountFromDB(acid);
                        credited.printToConsole();
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
                                if (acid == constants.bank) {
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
                        while (m == 0);
                        credited = accountDB.accountFromDB(acid);
                        credited.printToConsole();
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
                        while (m == 1);
                        account.payCredit(acid, payment);
                        credited = accountDB.accountFromDB(acid);
                        credited.printToConsole();
                        break;

                    case "11" :
                        m = 0;
                        do {
                            crid = currency.chooseCurrency();
                            if (crid <= 0) {
                                System.out.println("Currency ID 0 or Negative: Currency do not exist." +
                                        " Please repeat your set.");
                            }
                            else {
                                m++;
                            }
                        }
                        while (m==0);
                        nameValidator = new validators.NameValidator();
                        String newName = "";
                        do {
                            System.out.println("Set new name for this currency:");
                            String temp = sc.nextLine();
                            if (nameValidator.validate(temp)) {
                                newName = temp ;
                                m++;
                            }
                            else {
                                System.out.println("Incorrect name format. Use only latin letters or {-} !");
                            }
                        }
                        while (m==1);
                        currencyDB.currencyUpdateName(crid, newName);
                        currencyDB.viewCurrency();
                        break;
                    case "12" :
                        numberValidator = new validators.NumberValidator();
                        m = 0;
                        do {
                            System.out.println("Please choose Your next action(type number):");
                            System.out.println("121. Search all operations with selected account ID.");
                            System.out.println("122. Search all sender operations with selected account ID.");
                            System.out.println("123. Search all recipient operations with selected account ID.");
                            System.out.println("124. Search operations of specific type with selected account ID.");
                            System.out.println("125. Search filtered operations with selected account ID.");
                            System.out.println("12Q. Exit");
                            switch (sc.nextLine().toLowerCase()) {
                                case "121" :
                                    acid = 0;
                                    k = 0;
                                    do {
                                        System.out.println("Set account ID for operation view:");
                                        String temp = sc.nextLine();
                                        if (numberValidator.validate(temp)) {
                                            acid = Integer.parseInt (temp);
                                            if (acid == constants.bank) {
                                                System.out.println("Restricted operation : Restricted access.");
                                            }
                                            else if (acid <= 0) {
                                                System.out.println("Account with negative or 0 ID do not exist.");
                                            }
                                            else {
                                                k++;
                                            }
                                        }
                                        else {
                                            System.out.println("Incorrect ID format. Use only numbers!");
                                        }
                                    }
                                    while (k == 0);
                                    operationDB.viewOperationsAll(acid);
                                    break;
                                case "122" :
                                    acidFrom = 0;
                                    k = 0;
                                    do {
                                        System.out.println("Set sender account ID for operation view:");
                                        String temp = sc.nextLine();
                                        if (numberValidator.validate(temp)) {
                                            acidFrom = Integer.parseInt (temp);
                                            if (acidFrom == constants.bank) {
                                                System.out.println("Restricted operation : Restricted access.");
                                            }
                                            else if (acidFrom <= 0) {
                                                System.out.println("Account with negative or 0 ID do not exist.");
                                            }
                                            else {
                                                k++;
                                            }
                                        }
                                        else {
                                            System.out.println("Incorrect ID format. Use only numbers!");
                                        }
                                    }
                                    while (k == 0);
                                    operationDB.viewOperationsSender(acidFrom);
                                    break;
                                case "123" :
                                    acidTo = 0;
                                    k = 0;
                                    do {
                                        System.out.println("Set recipient account ID for operation view:");
                                        String temp = sc.nextLine();
                                        if (numberValidator.validate(temp)) {
                                            acidTo = Integer.parseInt (temp);
                                            if (acidTo == constants.bank) {
                                                System.out.println("Restricted operation : Restricted access.");
                                            }
                                            else if (acidTo <= 0) {
                                                System.out.println("Account with negative or 0 ID do not exist.");
                                            }
                                            else {
                                                k++;
                                            }
                                        }
                                        else {
                                            System.out.println("Incorrect ID format. Use only numbers!");
                                        }
                                    }
                                    while (k == 0);
                                    operationDB.viewOperationsRecipient(acidTo);
                                    break;
                                case "124" :
                                    acid = 0;
                                    k = 0;
                                    do {
                                        System.out.println("Set account ID for operation view:");
                                        String temp = sc.nextLine();
                                        if (numberValidator.validate(temp)) {
                                            acid = Integer.parseInt (temp);
                                            if (acid == constants.bank) {
                                                System.out.println("Restricted operation : Restricted access.");
                                            }
                                            else if (acid <= 0) {
                                                System.out.println("Account with negative or 0 ID do not exist.");
                                            }
                                            else {
                                                k++;
                                            }
                                        }
                                        else {
                                            System.out.println("Incorrect ID format. Use only numbers!");
                                        }
                                    }
                                    while (k == 0);
                                    do {
                                        System.out.println(accountDB.accountFromDB(acid).toString());
                                        System.out.println("Please choose type of operation(type number):");
                                        System.out.println("1. Transfer.");
                                        System.out.println("2. Exchange.");
                                        System.out.println("3. Credit.");
                                        System.out.println("4. Commission.");
                                        System.out.println("5. Loan repayment.");
                                        System.out.println("6. Input.");
                                        System.out.println("7. Output.");
                                        System.out.println("Q. Back.");
                                        switch (sc.nextLine().toLowerCase()) {
                                            case "1" :
                                                operationDB.viewOperationsTyped(acid, operationType.Transfer);
                                                break;
                                            case "2" :
                                                operationDB.viewOperationsTyped(acid, operationType.Exchange);
                                                break;
                                            case "3" :
                                                operationDB.viewOperationsTyped(acid, operationType.Credit);
                                                break;
                                            case "4" :
                                                operationDB.viewOperationsTyped(acid, operationType.Commission);
                                                break;
                                            case "5" :
                                                operationDB.viewOperationsTyped(acid, operationType.Loan_repayment);
                                                break;
                                            case "6" :
                                                operationDB.viewOperationsTyped(acid, operationType.Input);
                                                break;
                                            case "7" :
                                                operationDB.viewOperationsTyped(acid, operationType.Output);
                                                break;
                                            case "q" :
                                                k++;
                                                break;
                                            default :
                                                System.out.println("Unknown type. Please, try again.");
                                                break;
                                        }
                                    }
                                    while (k == 1);
                                    break;
                                case "125" :
                                    acid = 0;
                                    k = 0;
                                    do {
                                        System.out.println("Set account ID for operation view:");
                                        String temp = sc.nextLine();
                                        if (numberValidator.validate(temp)) {
                                            acid = Integer.parseInt (temp);
                                            if (acid == constants.bank) {
                                                System.out.println("Restricted operation : Restricted access.");
                                            }
                                            else if (acid <= 0) {
                                                System.out.println("Account with negative or 0 ID do not exist.");
                                            }
                                            else {
                                                k++;
                                            }
                                        }
                                        else {
                                            System.out.println("Incorrect ID format. Use only numbers!");
                                        }
                                    }
                                    while (k == 0);
                                    operationDB.viewOperationsAllFiltered(acid);
                                    break;
                                case "12q" :
                                    m++;
                                    break;
                                default:
                                    System.out.println("Unknown action. Please, try again.");
                                    break;
                            }

                        }
                        while (m == 0);
                        break;
                    case "13" :
                        accountDB.viewAccounts();
                        numberValidator = new validators.NumberValidator();
                        acid = 0;
                        k = 0;
                        do {
                        System.out.println("Set account ID for charging:");
                        String temp = sc.nextLine();
                        if (numberValidator.validate(temp)) {
                            acid = Integer.parseInt (temp);
                            if (acid == constants.bank) {
                                System.out.println("Restricted operation : Restricted access.");
                            }
                            else if (acid <= 0) {
                                System.out.println("Account with negative or 0 ID do not exist.");
                            }
                            else {
                                k++;
                            }
                        }
                        else {
                            System.out.println("Incorrect ID format. Use only numbers!");
                        }
                        }
                        while (k == 0);
                        double sum = 0;
                        do {
                            System.out.println("Set sum  for charging account:");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                sum = Double.parseDouble (temp);
                                if (sum <= 0) {
                                    System.out.println("Charging with negative or 0 sum impossible.");
                                }
                                else {
                                    k++;
                                }
                            }
                            else {
                                System.out.println("Incorrect sum format. Use only numbers!");
                            }
                        }
                        while (k == 1);
                        account.charging(acid, sum);
                        break;
                    case "14" :
                        accountDB.viewAccounts();
                        numberValidator = new validators.NumberValidator();
                        acid = 0;
                        k = 0;
                        do {
                            System.out.println("Set account ID for extraction:");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                acid = Integer.parseInt (temp);
                                if (acid == constants.bank) {
                                    System.out.println("Restricted operation : Restricted access.");
                                }
                                else if (acid <= 0) {
                                    System.out.println("Account with negative or 0 ID do not exist.");
                                }
                                else {
                                    k++;
                                }
                            }
                            else {
                                System.out.println("Incorrect ID format. Use only numbers!");
                            }
                        }
                        while (k == 0);
                        sum = 0;
                        do {
                            System.out.println("Set sum for extraction from account:");
                            String temp = sc.nextLine();
                            if (numberValidator.validate(temp)) {
                                sum = Double.parseDouble (temp);
                                if (sum <= 0) {
                                    System.out.println("Extraction with negative or 0 sum impossible.");
                                }
                                else {
                                    k++;
                                }
                            }
                            else {
                                System.out.println("Incorrect sum format. Use only numbers!");
                            }
                        }
                        while (k == 1);
                        account.extraction(acid, sum);
                        break;

                    case "1q" :
                        i++;
                        break;

                    default:
                        System.out.println("Unknown action. Please, try again.");
                        break;
                }
            }
            while (i == 0);
        }
        catch (Exception ex) {
            ex.getMessage();
            log.error("Exception occurred " , ex);
        }
    }
}

