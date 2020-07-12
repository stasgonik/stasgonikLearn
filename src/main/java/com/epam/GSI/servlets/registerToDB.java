package com.epam.GSI.servlets;

import com.epam.GSI.login;
import com.epam.GSI.loginDB;
import com.epam.GSI.user;
import com.epam.GSI.userDB;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class registerToDB extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
            response.setContentType("text/html");

            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String first_name = request.getParameter("first_name");
            String second_name = request.getParameter("second_name");
            String last_name = request.getParameter("last_name");
            String ageString = request.getParameter("age");
            int age = Integer.parseInt (ageString);
            String number = request.getParameter("number");
            if (userDB.checkNumber(number)) {
                String path = "/registerUser.jsp?number_check=1";
                response.sendRedirect(path);
            }
            else if (loginDB.checkLogin(login)) {
                String path = "/registerUser.jsp?login_check=1";
                response.sendRedirect(path);
            }
            else if (loginDB.checkLogin(login) && userDB.checkNumber(number)) {
                String path = "/registerUser.jsp?login_check=1&number_check=1";
                response.sendRedirect(path);
            }
            else {
                user newUser = new user(first_name, second_name, last_name, age, number);
                userDB.userToDB(newUser);
                int usid = userDB.getUSID(newUser);
                com.epam.GSI.login newLogin = new login(login, password, usid);
                loginDB.loginToDB(newLogin);
                String path = "/index.jsp?reg=1";
                response.sendRedirect(path);
            }


    }
}