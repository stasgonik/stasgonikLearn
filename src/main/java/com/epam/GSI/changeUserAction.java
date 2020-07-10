package com.epam.GSI;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class changeUserAction extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
            response.setContentType("text/html");

            HttpSession session = request.getSession();
            Integer usid = (Integer) session.getAttribute("sID");
            String change = request.getParameter("change");
        switch (change) {
            case "first":
                String first_name = request.getParameter("first_name");
                userDB.updateFName(usid, first_name);
                break;
            case "second":
                String second_name = request.getParameter("second_name");
                userDB.updateSName(usid, second_name);
                break;
            case "last":
                String last_name = request.getParameter("last_name");
                userDB.updateLName(usid, last_name);
                break;
            case "age":
                String ageString = request.getParameter("age");
                int age = Integer.parseInt(ageString);
                userDB.updateAge(usid, age);
                break;
            default:
                break;
        }
            String path = "/office.jsp";
            response.sendRedirect(path);



    }
}