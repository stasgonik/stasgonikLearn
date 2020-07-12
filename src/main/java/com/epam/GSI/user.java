package com.epam.GSI;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import java.util.Scanner;

public class user {
    private static final Logger log = Logger.getLogger(user.class);
    private String firstName;
    private String secondName;
    private String lastName;
    private int age;
    private String number;

    public user(String firstName, String secondName, String lastName, int age, String number) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.age = age;
        this.number = number;
    }

    public user() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @NotNull
    public static user createUser() {
        log.info("Starting to create new user.");
        Scanner sc = new Scanner(System.in);
        user us = new user();
        validators.NameValidator nameValidator = new validators.NameValidator();
        validators.NumberValidator numberValidator = new validators.NumberValidator();
        validators.PhoneValidator phoneValidator = new validators.PhoneValidator();

        int i = 0;
        try {
            do {
                System.out.println("Enter first name:");
                String temp = sc.nextLine();
                if (nameValidator.validate(temp)) {
                    us.setFirstName(temp);
                    i++;
                }
                else {
                    System.out.println("Incorrect name format. Use only latin letters or {-} !");
                    log.warn("Incorrect name format set.");
                }
            }
            while (i==0);
            do {
                System.out.println("Enter second name:");
                String temp = sc.nextLine();
                if (nameValidator.validate(temp)) {
                    us.setSecondName(temp);
                    i++;
                }
                else {
                    System.out.println("Incorrect name format. Use only latin letters or {-} !");
                    log.warn("Incorrect name format set.");
                }
            }
            while (i==1);
            do {
                System.out.println("Enter last name:");
                String temp = sc.nextLine();
                if (nameValidator.validate(temp)) {
                    us.setLastName(temp);
                    i++;
                }
                else {
                    System.out.println("Incorrect name format. Use only latin letters or {-} !");
                    log.warn("Incorrect name format set.");
                }
            }
            while (i==2);
            do {
                System.out.println("Enter your age:");
                String temp = sc.nextLine();
                if (numberValidator.validate(temp)) {
                    int tempInt = Integer.parseInt (temp);
                    us.setAge(tempInt);
                    i++;
                }
                else {
                    System.out.println("Incorrect age format. Use only numbers!");
                    log.warn("Incorrect age format set.");
                }
            }
            while (i==3);
            do {
                System.out.println("Enter your phone number with country code:");
                System.out.println("Preferred format: +38 123 123-4567");
                String temp = sc.nextLine();
                if (phoneValidator.validate(temp)) {
                    if (userDB.checkNumber(temp)) {
                        System.out.println("We already have this number in database.");
                        System.out.println("Please, use another number for new user.");
                        log.warn("Attempt to set phone number, that already in database.");
                    }
                    else {
                        us.setNumber(temp);
                        i++;
                    }
                }
                else {
                    System.out.println("Incorrect number format. Please, repeat again.");
                    log.warn("Incorrect phone number format set.");
                }
            }
            while (i==4);
        }
        catch (Exception ex) {
            ex.getMessage();
            log.error("Exception occurred ", ex);
        }
        userDB.userToDB(us);
        int usid = userDB.getUSID(us);
        login usLogin = login.createLogin(usid);
        loginDB.loginToDB(usLogin);
        return us;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + secondName + " , age: " + age + ", phone number: " + number ;
    }
}

