package com.epam.GSI;

import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.Scanner;


public class currency {
    private static final Logger log = Logger.getLogger(currency.class);
    private String name;
    private double course_buy;
    private double course_sell;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCourse_buy() {
        return course_buy;
    }

    public void setCourse_buy(double course_buy) {
        this.course_buy = course_buy;
    }

    public double getCourse_sell() {
        return course_sell;
    }

    public void setCourse_sell(double course_sell) {
        this.course_sell = course_sell;
    }

    public currency(String name, double course_buy, double course_sell) {
        this.name = name;
        this.course_buy = course_buy;
        this.course_sell = course_sell;
    }


    public currency() {
    }

    /*public static void createCurrency() {
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
    }*/

    public static int chooseCurrency(){
        int crid = 0;
        try {
            log.debug("Choosing currency for action.");
            currencyDB.viewCurrencies();
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
        return  name + " (" + "Buy course of this currency is " + course_buy +
                ", sell course of this currency is " + course_sell + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof currency)) return false;
        currency currency = (currency) o;
        return Double.compare(currency.getCourse_buy(), getCourse_buy()) == 0 &&
                Double.compare(currency.getCourse_sell(), getCourse_sell()) == 0 &&
                getName().equals(currency.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCourse_buy(), getCourse_sell());
    }
}



