import java.util.Currency;

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

    @Override
    public String toString() {
        return  name + " (" +
                "id of currency is " + id +
                ", value of this currency is " + value +
                ")";
    }
}
