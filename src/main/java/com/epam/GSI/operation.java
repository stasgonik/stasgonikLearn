package com.epam.GSI;

import org.apache.log4j.Logger;

import java.time.LocalDateTime;

public class operation {
    private static final Logger log = Logger.getLogger(operation.class);
    private operationType type;
    private subtype subtype;
    private LocalDateTime dateTime = LocalDateTime.now();
    private currency operationCurrency;
    private double sum;

    public operationType getType() {
        return type;
    }

    public void setType(operationType type) {
        this.type = type;
    }

    public subtype getSubtype() {
        return subtype;
    }

    public void setSubtype(subtype subtype) {
        this.subtype = subtype;
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

    public operation(operationType type, subtype subtype, currency operationCurrency, double sum) {
        this.type = type;
        this.subtype = subtype;
        this.operationCurrency = operationCurrency;
        this.sum = sum;
    }

    public operation(operationType type, subtype subtype, LocalDateTime dateTime, currency operationCurrency, double sum) {
        this.type = type;
        this.subtype = subtype;
        this.dateTime = dateTime;
        this.operationCurrency = operationCurrency;
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Operation" + " type : " + type + ", Subtype of operation : " + subtype + ", Time of operation : " +
                dateTime + ", Currency of operation : " + operationCurrency + ", Sum : " + sum +
                " " + operationCurrency.getName() + ".";
    }

    public static void createOperation (operationType type, subtype subtype, currency operationCurrency,
                                        double sum, int acidFrom, int acidTo) {
        try {
            operation op = new operation();
            op.setType(type);
            op.setSubtype(subtype);
            op.setOperationCurrency(operationCurrency);
            op.setSum(sum);
            if (acidTo != 0 && acidFrom != 0) {
                operationDB.operationToDB_2acc(op, acidFrom, acidTo);
            }
            else if (acidFrom <= 0 && acidTo <= 0) {
                System.out.println("Unknown operation. Account IDs are not detected.");
                System.out.println("Operation cannot be registered.");
                log.warn("Attempt to create operation without or incorrect account IDs.");

            }
            else if (acidFrom <= 0) {
                operationDB.operationToDB_To(op, acidTo);
            }
            else  {
                operationDB.operationToDB_From(op, acidFrom);
            }
        }
        catch (Exception ex) {
            ex.getMessage();
            log.error("Exception occurred ", ex);
        }
    }
}

