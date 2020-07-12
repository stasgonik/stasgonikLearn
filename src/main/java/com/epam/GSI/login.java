package com.epam.GSI;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import java.util.Scanner;


public class login {
    private static final Logger log = Logger.getLogger(login.class);
    private String login;
    private String password;
    private int usid;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUsid() {
        return usid;
    }

    public void setUsid(int usid) {
        this.usid = usid;
    }

    public login() {
    }

    public login(String login, String password, int usid) {
        this.login = login;
        this.password = password;
        this.usid = usid;
    }

    @NotNull
    public static login createLogin(int usid) {
        log.debug("Creating login for user with ID " + usid + ".");
        Scanner sc = new Scanner(System.in);
        validators.LoginValidator loginValidator = new validators.LoginValidator();
        int i = 0;
        login newUser = new login();

        try {
            do {
                System.out.println("Enter login:");
                String temp = sc.nextLine();
                if (loginValidator.validate(temp)) {
                    if (loginDB.checkLogin(temp)) {
                        System.out.println("User with this login already exist.");
                        System.out.println("Please, choose another login.");
                        log.warn("Attempt to create login, that already exist in database.");
                    }
                    else {
                        newUser.setLogin(temp);
                        i++;
                    }
                }
                else {
                    System.out.println("Incorrect login format. Use only latin letters or numbers!");
                    log.warn("Incorrect login format set.");
                }
            }
            while (i==0);
            do {
                System.out.println("Enter password:");
                String temp = sc.nextLine();
                if (loginValidator.validate(temp)) {
                    newUser.setPassword(temp);
                    i++;
                }
                else {
                    System.out.println("Incorrect password format. Use only latin letters or numbers!");
                    log.warn("Incorrect password format set.");
                }
            }
            while (i==1);
        }
        catch (Exception ex) {
            ex.getMessage();
            log.error("Exception occurred ", ex);
        }
        newUser.setUsid(usid);
        return newUser;
    }
}


