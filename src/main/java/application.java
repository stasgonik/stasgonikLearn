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
                System.out.println("Q. Exit.");
                switch (sc.nextLine().toLowerCase()) {
                    case "1" :
                        account newAccount = account.createAccount();
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
                        search.toString();
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

