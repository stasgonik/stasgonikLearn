import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class account {

    private int id;
    private user master;
    private currency accountCurrency;
    private double money;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public account(int id, user master, currency accountCurrency, double money) {
        this.id = id;
        this.master = master;
        this.accountCurrency = accountCurrency;
        this.money = money;
    }


    public account() {
    }

    public double currentSum() {
        double sum = money * accountCurrency.getValue();
        return sum;
    }
    public static account createAccount() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter account currency (UAH, USD, EUR):");
        account Cr = new account();
        try {
            currency cur = currency.chooseCurrency();
            user user1 = user.createUser();
            numberValidator validator = new numberValidator();
            int i =0;
            do {
                System.out.println("Enter new id:");
                String temp = sc.nextLine();
                if (validator.validate(temp)) {
                    int tempInt = Integer.parseInt (temp);
                    Cr.setId(tempInt);
                    i++;
                }
                else {
                    System.out.println("Incorrect id format. Use only numbers!");
                }
            }
            while (i==0);
            do {
                System.out.println("Enter sum of money for test account:");
                String temp = sc.nextLine();
                if (validator.validate(temp)) {
                    int tempInt = Integer.parseInt (temp);
                    Cr.setMoney(tempInt);
                    i++;
                }
                else {
                    System.out.println("Incorrect sum format. Use only numbers!");
                }
            }
            while (i==1);
            Cr.setAccountCurrency(cur);
            Cr.setMaster(user1);

        }
        catch (Exception ex) {
            ex.getMessage();
        }

        return Cr;
    }

    @Override
    public String toString() {
        return
                "Id of account is " + id +
                ", master of account " + master.toString() +
                ", currency of account is " + accountCurrency.toString() +
                ", number of money on account is " + money + " " + accountCurrency.getName() +
                ", sum in UAH is " + this.currentSum() + " UAH"
                ;
    }

}
class numberValidator {
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
}
