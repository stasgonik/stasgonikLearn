package com.epam.GSI;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginToOffice extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

            response.setContentType("text/html");
            String login = request.getParameter("login");
            String password = request.getParameter("password");

            if (loginDB.checkLoginPassword(login, password)) {
                int usid = loginDB.getID(login, password);
                String path = "/office.jsp";
                HttpSession session = request.getSession();
                Integer sID = (Integer) session.getAttribute("sID");
                if (sID != null) {
                    session.removeAttribute("sID");
                }
                session.setAttribute("sID", usid);
                response.sendRedirect(path);
            }

            else {
                String path = "/login.jsp?check=1";
                response.sendRedirect(path);
            }
    }
}