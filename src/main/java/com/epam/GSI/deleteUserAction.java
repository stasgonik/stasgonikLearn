package com.epam.GSI;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class deleteUserAction extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
            response.setContentType("text/html");

            final Logger log = Logger.getLogger(deleteUserAction.class);
            HttpSession session = request.getSession();
            Integer usid = (Integer) session.getAttribute("sID");
            String checkUsidString = request.getParameter("usid");
            Integer checkUsid = Integer.parseInt(checkUsidString);
            String checkString = request.getParameter("check");
            Integer check = Integer.parseInt(checkString);
            if (Objects.equals(usid, checkUsid) && Objects.equals(check, 42)) {
                int[] accountsID = accountDB.searchUserAccounts(usid);
                for (int id : accountsID) {
                    if (id != 0) {
                        accountDB.deleteAccount(id);
                    }
                }
                loginDB.deleteLogin(usid);
                try {
                    wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log.error("Exception occurred", e);
                }
                userDB.deleteUser(usid);
                String path = "/index.jsp";
                response.sendRedirect(path);
            }
            else {
                String path = "/office.jsp";
                response.sendRedirect(path);
            }




    }
}