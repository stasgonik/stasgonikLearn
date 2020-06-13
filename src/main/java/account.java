import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class account {


    private user master;
    private currency accountCurrency;
    private double money;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
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

    public account(user master, currency accountCurrency, double money) {

        this.master = master;
        this.accountCurrency = accountCurrency;
        this.money = money;
    }


    public account() {
    }

    public double currentSum() {
        return money * accountCurrency.getValue();

    }
    public static account createAccount() {
        System.out.println("Starting creation of new account.");
        Scanner sc = new Scanner(System.in);
        account Cr = new account();
        try {
            currency cur = currency.chooseCurrency();
            user user1 = user.createUser();
            numberValidator validator = new numberValidator();
            int i =0;
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
            while (i==0);
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
        return "Master of account: "  + master +
                ", currency of account is " + accountCurrency +
                ", sum of money on account = " + money + ""
                + accountCurrency.getName() + " ( In UAH : " +
                currentSum() + " )";
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
