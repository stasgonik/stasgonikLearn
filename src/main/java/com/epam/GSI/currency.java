package com.epam.GSI;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Objects;
import java.util.Scanner;


public class currency {
    private static final Logger log = Logger.getLogger(currency.class);
    private String name;
    private double value;

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

    public currency (String name, double value) {
        this.name = name;
        this.value = value;
    }

    public currency() {
    }

    public static void createCurrency() {
        log.warn("Starting to create new currency.");
        Scanner sc = new Scanner(System.in);
        currency cur = new currency();
        validators.NameValidator nameValidator = new validators.NameValidator();
        validators.NumberValidator numberValidator = new validators.NumberValidator();
        int i = 0;
        try {
            do {
                System.out.println("Enter currency name:");
                String temp = sc.nextLine();
                if (nameValidator.validate(temp)) {
                    cur.setName(temp);
                    i++;
                }
                else {
                    System.out.println("Incorrect name format. Use only latin letters or {-} !");
                    log.warn("Incorrect name format set.");
                }
            }
            while (i==0);
            do {
                System.out.println("Enter value of currency:");
                String temp = sc.nextLine();
                if (numberValidator.validate(temp)) {
                    double tempInt = Double.parseDouble (temp);
                    if (tempInt <= 0) {
                        System.out.println("Prohibited negative or 0 value of currency.");
                        log.warn("Attempt to set below 0 value of currency.");
                    }
                    else {
                        cur.setValue(tempInt);
                        i++;
                    }
                }
                else {
                    System.out.println("Incorrect value format. Use only numbers!");
                    log.warn("Incorrect currency value format set.");
                }
            }
            while (i==1);
        }
        catch (Exception ex) {
            ex.getMessage();
            log.error("Exception occurred ", ex);
        }
        currencyDB.currencyToDB(cur);
    }

    public static int chooseCurrency(){
        int crid = 0;
        try {
            log.debug("Choosing currency for action.");
            currencyDB.viewCurrency();
            Scanner sc = new Scanner(System.in);
            validators.NumberValidator numberValidator = new validators.NumberValidator();
            int i = 0;
            do {
                System.out.println("Enter ID of currency:");
                String temp = sc.nextLine();
                if (numberValidator.validate(temp)) {
                    crid = Integer.parseInt (temp);
                    if (crid <= 0){
                        System.out.println("There is no currency with negative ID. Please, repeat your set.");
                        log.warn("Attempt to choose not existing currency.");
                    }
                    else {
                        i++;
                    }
                }
                else {
                    System.out.println("Incorrect ID format. Use only numbers!");
                    log.warn("Incorrect ID format set.");
                }
            }
            while (i==0);
        }
        catch (Exception ex) {
            ex.getMessage();
            log.error("Exception occurred ", ex);
        }
        return crid;
    }

    @Override
    public String toString() {
        return  name + " (" + "Value of this currency is " + value + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof currency)) return false;
        currency currency = (currency) o;
        return Double.compare(currency.getValue(), getValue()) == 0 &&
                Objects.equals(getName(), currency.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getValue());
    }
}



