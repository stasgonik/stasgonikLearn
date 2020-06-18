import java.sql.*;
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
                System.out.println("3. Search account by ID.");
                System.out.println("4. Update account detail.");
                System.out.println("Q. Exit.");
                switch (sc.nextLine()) {
                    case "1" :
                        account newAccount = account.createAccount();
                        System.out.println(newAccount.toString());
                        break;

                    case "2" :
                        currency cur = currency.chooseCurrency();
                        System.out.println("Set new value for this currency:");
                        cur.setValue(sc.nextDouble());
                        currencyDB.currencyUpdate(cur);
                        break;

                    case "3" :
                        System.out.println("Set new ID for search:");
                        account search = accountDB.accountFromDB(sc.nextInt());
                        System.out.println(search.toString());
                        break;

                    case "4" :
                        System.out.println("Set new ID for search:");
                        int acid = sc.nextInt();
                        account change = accountDB.accountFromDB(acid);
                        System.out.println(change.toString());
                        int k = 0;
                        int usid = accountDB.usidFromDB(acid);
                        do {
                            account changed = accountDB.accountFromDB(acid);
                            System.out.println(changed.toString());
                            System.out.println("What do you want to change(type number):");
                            System.out.println("1. First name.");
                            System.out.println("2. Second name.");
                            System.out.println("3. Last name.");
                            System.out.println("4. Age.");
                            System.out.println("5. Phone number.");
                            System.out.println("6. Money.");
                            System.out.println("Q. Exit.");
                            switch (sc.nextLine()) {
                                case "1" :
                                    System.out.println("Set new first name:");
                                    userDB.updateFName(usid, sc.nextLine());
                                    break;
                                case "2" :
                                    System.out.println("Set new second name:");
                                    userDB.updateSName(usid, sc.nextLine());
                                    break;
                                case "3" :
                                    System.out.println("Set new last name:");
                                    userDB.updateLName(usid, sc.nextLine());
                                    break;
                                case "4" :
                                    System.out.println("Set new age:");
                                    userDB.updateAge(usid, sc.nextInt());
                                    break;
                                case "5" :
                                    System.out.println("Set new phone number:");
                                    userDB.updateNumber(usid, sc.nextDouble());
                                    break;
                                case "6" :
                                    System.out.println("Set new money value:");
                                    double newM = sc.nextDouble();
                                    accountDB.updateMoney(acid, newM);
                                    break;
                                case "q" :
                                    k++;
                                    break;
                                default  :
                                    System.out.println("Unknown action. Please, try again.");
                                    break;
                            }
                        }
                        while (k==0);
                        break;

                    case "q" :
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

