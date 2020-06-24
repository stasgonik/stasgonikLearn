import java.time.LocalDateTime;

public class operation {
    private operationType type;
    private subtype sub;
    private LocalDateTime dateTime = LocalDateTime.now();
    private currency operationCurrency;
    private double sum;

    public operationType getType() {
        return type;
    }

    public void setType(operationType type) {
        this.type = type;
    }

    public subtype getSub() {
        return sub;
    }

    public void setSub(subtype sub) {
        this.sub = sub;
    }

    public currency getOperationCurrency() {
        return operationCurrency;
    }

    public void setOperationCurrency(currency operationCurrency) {
        this.operationCurrency = operationCurrency;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public operation() {
    }

    public operation(operationType type, subtype sub, currency operationCurrency, double sum) {
        this.type = type;
        this.sub = sub;
        this.operationCurrency = operationCurrency;
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "operation" +
                " type : " + type + ", subtype of operation : " + sub + ", time of operation : " + dateTime +
                ", currency of operation : " + operationCurrency + ", sum : " + sum +
                " " + operationCurrency.getName() + " ";
    }
}
enum operationType {
    Transfer,
    Credit,
    Output,
    Input,
    Exchange
}
enum subtype {
    Charge,
    Withdraw
}
