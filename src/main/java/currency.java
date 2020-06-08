import java.util.Scanner;

public class currency {
    private int id;
    private String name;
    private double value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public currency(int id, String name, double value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public currency() {
    }

    public static currency UAH = new currency(1, "Grind", 1);
    public static currency USD = new currency(2, "Dollar", 28);
    public static currency EUR = new currency(3, "Euro", 35);

    public static currency chooseCurrency() {
        Scanner sc = new Scanner(System.in);
        currency cur = new currency();
        int i = 0;
        try {
            do {
                switch (sc.nextLine().toLowerCase()) {
                    case "uah" :
                        cur = currency.UAH;
                        i++;
                        break;
                    case "usd" :
                        cur = currency.USD;
                        i++;
                        break;

                    case "eur" :
                        cur = currency.EUR;
                        i++;
                        break;

                    default:
                        System.out.println("Unknown currency. Please, try again.");
                        break;
                }
            }
            while (i==0);

        }
        catch (Exception ex) {
            ex.getMessage();
        }
        return cur;
    }

    @Override
    public String toString() {
        return  name + " (" +
                "id of currency is " + id +
                ", value of this currency is " + value +
                ")";
    }
}
