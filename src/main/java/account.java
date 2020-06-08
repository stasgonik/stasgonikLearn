import java.util.Scanner;

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
            currency cur = new currency();
            switch (sc.nextLine().toLowerCase()) {
                case "uah" :
                    cur = currency.UAH;
                    break;
                case "usd" :
                    cur = currency.USD;
                    break;

                case "eur" :
                    cur = currency.EUR;
                    break;

                default:
                    System.out.println("Unknown currency, set to default currency (UAH)!");
                    cur = currency.UAH;
            }
            user user1 = user.createUser();
            System.out.println("Enter new id:");
            Cr.setId(sc.nextInt());
            Cr.setAccountCurrency(cur);
            Cr.setMaster(user1);
            System.out.println("Enter number of money:");
            Cr.setMoney(sc.nextDouble());
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
