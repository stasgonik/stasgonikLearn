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
    public double currentSum() {
        double sum = money * accountCurrency.getValue();
        return sum;
    }

    @Override
    public String toString() {
        return "account{" +
                "id=" + id +
                ", master=" + master.toString() +
                ", accountCurrency=" + accountCurrency.toString() +
                ", money=" + money + accountCurrency.getName() +
                ", sum=" + this.currentSum() +
                '}';
    }
}
