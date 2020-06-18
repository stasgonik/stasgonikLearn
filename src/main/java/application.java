import java.util.*;


public class application {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int i = 0;
        try {
            do {
                System.out.println("Please choose Your next action(type number):");
                System.out.println("11. Create new account.");
                System.out.println("12. Change currency value.");
                System.out.println("13. Update account detail.");
                System.out.println("14. Delete account.");
                System.out.println("15. Add new currency to DB.");
                System.out.println("16. View all currencies.");
                System.out.println("1Q. Exit.");
                switch (sc.nextLine().toLowerCase()) {
                    case "11" :
                        account newAccount = account.createAccount();
                        System.out.println(newAccount.toString());
                        break;

                    case "12" :
                        currency cur = currency.chooseCurrency();
                        System.out.println("Set new value for this currency:");
                        cur.setValue(sc.nextDouble());
                        currencyDB.currencyUpdate(cur);
                        break;

                    case "13" :
                        accountDB.viewAccounts();
                        System.out.println("Set ID for update:");
                        int acid = sc.nextInt();
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
                            else {
                                int usidDel = accountDB.usidFromDB(acid);
                                accountDB.deleteAccount(acid);
                                userDB.deleteUser(usidDel);

                            }
                        }
                        while (k==0);
                        break;

                    case "15" :
                        System.out.println("List of currencies in DB:");
                        currencyDB.viewCurrency();
                        System.out.println("Set new currency!");
                        currency.createCurrency();
                        break;
                    case "16" :
                        System.out.println("List of currencies in DB:");
                        currencyDB.viewCurrency();
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

