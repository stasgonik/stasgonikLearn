import java.util.*;


public class application {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int i = 0;
        //Integer check = null;
        try {
            do {
                System.out.println("Please choose Your next action(type number):");
                System.out.println("11. Create new account.");
                System.out.println("12. Change currency value.");
                System.out.println("13. Update account detail.");
                System.out.println("14. Delete account and user.");
                System.out.println("15. Add new currency to DB.");
                System.out.println("16. View all currencies.");
                System.out.println("17. Delete currency.");
                System.out.println("18. Transfer money.");
                System.out.println("1Q. Exit.");
                switch (sc.nextLine().toLowerCase()) {
                    case "11" :
                        account newAccount = account.createAccount();
                        System.out.println(newAccount.toString());
                        System.out.println("Type anything to out.");
                        String await = sc.nextLine();
                        break;

                    case "12" :
                        currency cur = currency.chooseCurrency();
                        //if (cur.getValue() == check) {
                        //    System.out.println("Currency do not exist.");
                        //}
                        //else {
                            System.out.println("Set new value for this currency:");
                            double newValue = sc.nextDouble();
                            if (newValue < 0) {
                                System.out.println("Restricted operation : Negative or 0 value currency.");
                            }
                            else {
                                cur.setValue(newValue);
                                currencyDB.currencyUpdate(cur);
                                currencyDB.viewCurrency();
                                System.out.println("Type anything to out.");
                                await = sc.nextLine();
                            }
                        //}
                        break;

                    case "13" :
                        accountDB.viewAccounts();
                        System.out.println("Set ID for update:");
                        int acid = sc.nextInt();
                        //if (accountDB.accountFromDB(acid).getMoney() == check) {
                        //    System.out.println("Account do not exist.");
                        //}
                        //else {
                            int k = 0;
                            int usid = accountDB.usidFromDB(acid);
                            do {
                                account changed = accountDB.accountFromDB(acid);
                                System.out.println(changed.toString());
                                System.out.println("What do you want to change(type number):");
                                System.out.println("131. First name.");
                                System.out.println("132. Second name.");
                                System.out.println("133. Last name.");
                                System.out.println("134. Age.");
                                System.out.println("135. Phone number.");
                                System.out.println("136. Money.");
                                System.out.println("13Q. Exit.");
                                switch (sc.nextLine().toLowerCase()) {
                                    case "131" :
                                        System.out.println("Set new first name:");
                                        userDB.updateFName(usid, sc.nextLine());
                                        break;
                                    case "132" :
                                        System.out.println("Set new second name:");
                                        userDB.updateSName(usid, sc.nextLine());
                                        break;
                                    case "133" :
                                        System.out.println("Set new last name:");
                                        userDB.updateLName(usid, sc.nextLine());
                                        break;
                                    case "134" :
                                        System.out.println("Set new age:");
                                        userDB.updateAge(usid, sc.nextInt());
                                        break;
                                    case "135" :
                                        System.out.println("Set new phone number:");
                                        userDB.updateNumber(usid, sc.nextDouble());
                                        break;
                                    case "136" :
                                        System.out.println("Set new money value:");
                                        double newM = sc.nextDouble();
                                        accountDB.updateMoney(acid, newM);
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
                        //}
                        break;
                    case "14" :
                        k = 0;
                        do {
                            accountDB.viewAccounts();
                            System.out.println("Set ID of account to delete (or type 0 to quit):");
                            acid = sc.nextInt();
                            if (acid == 0) {
                                k++;
                            }
                            //else if (accountDB.accountFromDB(acid).getMoney() == check) {
                            //    System.out.println("This account already do no exist.");
                            //    k++;
                            //}
                            else {
                                int usidDel = accountDB.usidFromDB(acid);
                                accountDB.deleteAccount(acid);
                                userDB.deleteUser(usidDel);
                                accountDB.viewAccounts();
                                System.out.println("Type anything to out.");
                                await = sc.nextLine();

                            }
                        }
                        while (k==0);
                        break;

                    case "15" :
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
                    case "16" :
                        System.out.println("List of currencies in DB:");
                        currencyDB.viewCurrency();
                        System.out.println("Type anything to out.");
                        await = sc.nextLine();
                        break;
                    case  "17" :
                        currencyDB.viewCurrency();
                        System.out.println("Are you sure, that you want to delete currency? (Y/N)");
                        choice = sc.nextLine().toLowerCase();
                        if (choice.equals("y") || choice.equals("yes")) {
                            System.out.println("Set ID of account to delete:");
                            int crid = sc.nextInt();
                            currencyDB.deleteCurrency(crid);
                        }
                        else if (choice.equals("n") || choice.equals("no")) {
                            System.out.println("OK.");
                        }
                        else {
                            System.out.println("We will count this as No.");
                        }
                        break;
                    case "18" :
                        accountDB.viewAccounts();
                        System.out.println("Set id of initial account:");
                        int acidFrom = sc.nextInt();
                        accountDB.viewAccounts();
                        System.out.println("Set id of destination account:");
                        int acidTo = sc.nextInt();
                        System.out.println("Initial account:");
                        System.out.println(accountDB.accountFromDB(acidFrom).toString());
                        System.out.println("Destination account:");
                        System.out.println(accountDB.accountFromDB(acidTo).toString());
                        System.out.println("Set sum of money (in initial currency), which you want to send:");
                        double trMoney = sc.nextDouble();
                        account.transferMoney(trMoney, acidFrom, acidTo);
                        accountDB.viewAccounts();
                        System.out.println("Type anything to out.");
                        await = sc.nextLine();
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

